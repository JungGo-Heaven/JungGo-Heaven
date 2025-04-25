package com.example.junggoheaven.domain.chatRoom.service;

import com.example.junggoheaven.domain.chatMessage.dto.response.ChatMessageResponseDto;
import com.example.junggoheaven.domain.chatMessage.entity.ChatMessage;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageFinder;
import com.example.junggoheaven.domain.chatRoom.dto.response.ChatRoomEnterResponseDto;
import com.example.junggoheaven.domain.chatRoom.dto.response.ChatRoomsResponseDto;
import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import com.example.junggoheaven.domain.chatRoom.exception.ChatRoomForbidden;
import com.example.junggoheaven.domain.chatRoom.service.component.ChatRoomFinder;
import com.example.junggoheaven.domain.location.dto.LocationVerificationRequest;
import com.example.junggoheaven.domain.location.exception.LocationVerificationRequiredException;
import com.example.junggoheaven.domain.location.service.LocationVerificationService;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.product.service.component.ProductFinder;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {
    private final ChatRoomFinder chatRoomFinder;

    private final ChatMessageFinder chatMessageFinder;

    private final ProductFinder productFinder;

    private final UserFinder userFinder;

    private final LocationVerificationService locationVerificationService;

    /***
     * 채팅방이 데이터베이스에 저장되는 조건은 채팅방에 입장했을 때가 아니라 처음 메세지를 보냈을 때이다.
     * productId와 요청자(구매자 -> 구매자가 항상 먼저 판매자에게 채팅을 걸 수 있기 때문에)id를 통해 채팅방이 존재하는 지 확인.
     * productId와 buyerId는 유니크키로 설정하여 인덱스를 걸어두었기에 중복이 불가능하고 빠르게 조회가 가능함.
     * 존재한다면 채팅방Id를 통해 채팅메세지 테이블에 접근하여 sendAt(메세지 보낸날짜)을 오름차순으로 정렬하여 채팅 리스트를 가져옴
     * 존재하지 않는다면 빈 리스트를 응답
     */
    @Transactional(readOnly = true)
    public ResponseDto<ChatRoomEnterResponseDto> enterChatRoom(Long productId, AuthUser authUser){
        // '내 동네 인증' 절차 걸쳐야 채팅방 입장 가능!
        User user = userFinder.findByUserId(authUser.getId());
        Product product = productFinder.findProductById(productId);

        LocationVerificationRequest request = LocationVerificationRequest.of(
                product.getLongitude(),
                product.getLatitude(),
                user.getId());

        boolean isVerified = locationVerificationService.authenticateLocation(request);

        if (!isVerified) {
            throw new LocationVerificationRequiredException();
        }

        // 인증 통과 후 lastVerifiedAt 업데이트
        locationVerificationService.updateLastVerified(user);

        Optional<ChatRoom> chatRoom = chatRoomFinder.findByProductIdAndBuyerIdOpt(productId, authUser.getId());

        if(chatRoom.isPresent()){
            List<ChatMessage> messages = chatMessageFinder.findByChatRoomIdOrderBySendAtAsc(chatRoom.get().getId());
            List<ChatMessageResponseDto> responseMessages = messages.stream()
                    .map(m -> new ChatMessageResponseDto(
                            m.getChatRoom().getId(),
                            m.getId(),
                            m.getSender().getId(),
                            m.getMessage(),
                            m.getChatRoomImage() != null ? m.getChatRoomImage().getChatRoomImageUrl() : null,
                            m.getSendAt(),
                            m.getMessageType(),
                            m.getIsRead()
                    ))
                    .toList();

            ChatRoomEnterResponseDto responseDto = new ChatRoomEnterResponseDto(responseMessages);
            return ResponseDto.success(responseDto);
        }
        return ResponseDto.success(new ChatRoomEnterResponseDto()); // 저장된 채팅방이 없으므로 빈 배열 응답
    }

    @Transactional(readOnly = true)
    public ResponseDto<Page<ChatRoomsResponseDto>> getChatRooms(Long authUserId, Pageable pageable){
        Page<ChatRoom> chatRooms = chatRoomFinder.findPagingChatRooms(authUserId, pageable);

        List<Long> chatRoomIds = chatRooms.getContent()
                .stream().map(ChatRoom::getId).toList();

        Map<Long, ChatMessage> latestMessages = chatMessageFinder.findLatestMessagesForChatRooms(chatRoomIds);

        Page<ChatRoomsResponseDto> response = chatRooms.map(chatRoom -> {
            ChatMessage lastMessage = latestMessages.get(chatRoom.getId());
            return ChatRoomsResponseDto.of(chatRoom, lastMessage);
        });

        return ResponseDto.success(response);
    }

    @Transactional
    public void exitChatRoom(Long chatRoomId, AuthUser authUser){
        ChatRoom chatRoom = chatRoomFinder.findByChatRoomId(chatRoomId);

        if(chatRoom.getBuyer().getId().equals(authUser.getId())){
            chatRoom.buyerExited(authUser.getId());
        } else if(chatRoom.getProduct().getUser().getId().equals(authUser.getId())){
            chatRoom.sellerExited(authUser.getId());
        } else{
            throw new ChatRoomForbidden();
        }
    }
}
