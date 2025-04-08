package com.example.junggoheaven.domain.chatMessage.service;

import com.example.junggoheaven.domain.chatMessage.dto.request.ChatDeleteRequestDto;
import com.example.junggoheaven.domain.chatMessage.dto.request.ChatMessageRequestDto;
import com.example.junggoheaven.domain.chatMessage.dto.request.ChatReadRequestDto;
import com.example.junggoheaven.domain.chatMessage.dto.response.ChatMessageResponseDto;
import com.example.junggoheaven.domain.chatMessage.entity.ChatMessage;
import com.example.junggoheaven.domain.chatMessage.exception.ChatRoomMissMatchException;
import com.example.junggoheaven.domain.chatMessage.exception.NoPermissionToDelete;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageChecker;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageFinder;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageWriter;
import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import com.example.junggoheaven.domain.chatRoom.service.component.ChatRoomFinder;
import com.example.junggoheaven.domain.chatRoom.service.component.ChatRoomWriter;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.product.repository.ProductRepository;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageChecker chatMessageChecker;
    private final ChatMessageFinder chatMessageFinder;
    private final ChatMessageWriter chatMessageWriter;

    private final ProductRepository productRepository; // productFinder가 구현되면 수정 예정

    private final ChatRoomFinder chatRoomFinder;
    private final ChatRoomWriter chatRoomWriter;

    private final UserFinder userFinder;

    public ChatMessageResponseDto createMessage(ChatMessageRequestDto requestDto, Long userId) {
        User user = userFinder.findByUserId(userId);
        ChatRoom chatRoom;
        ChatMessage chatMessage;

        if(requestDto.getChatRoomId() != null) {
            chatRoom = chatRoomFinder.findByChatRoomId(requestDto.getChatRoomId());
            chatMessage = new ChatMessage(chatRoom, user, requestDto.getMessage(), requestDto.getMessageType());
        } else {
            Product product = productRepository.findById(requestDto.getProductId()).orElse(null);
            ChatRoom newChatRoom = new ChatRoom(product, user);
            ChatRoom savedChatRoom = chatRoomWriter.save(newChatRoom);
            chatMessage = new ChatMessage(savedChatRoom, user, requestDto.getMessage(), requestDto.getMessageType());
        }

        ChatMessage savedMessage = chatMessageWriter.save(chatMessage);

        return new ChatMessageResponseDto(
                savedMessage.getChatRoom().getId(),
                savedMessage.getId(),
                savedMessage.getSender().getId(),
                savedMessage.getMessage(),
                savedMessage.getSendAt()
        );
    }

    public void isRead(ChatReadRequestDto requestDto, Long userId) {
        ChatRoom chatRoom = chatRoomFinder.findByChatRoomId(requestDto.getChatRoomId());
        User user = userFinder.findByUserId(userId);
        // 아직 읽지 않았고, 내가 보낸 게 아닌 메시지만 가져옴
        List<ChatMessage> unreadMessages = chatMessageFinder
                .findUnreadMessages(requestDto.getReadMessageIds(), chatRoom.getId(), user.getId());

        for (ChatMessage message : unreadMessages) {
            message.isRead();
        }
    }

    @Transactional
    public void deleteMessage(ChatDeleteRequestDto requestDto, Long userId) {

        List<ChatMessage> messages = chatMessageFinder.findAllById(requestDto.getDeleteMessageIds());

        for(ChatMessage message : messages) {
            if(!message.getSender().getId().equals(userId)) {
                throw new NoPermissionToDelete();
            }
            if (!message.getChatRoom().getId().equals(requestDto.getChatRoomId())) {
                throw new ChatRoomMissMatchException();
            }
            message.isDeleted();
        }
    }
}
