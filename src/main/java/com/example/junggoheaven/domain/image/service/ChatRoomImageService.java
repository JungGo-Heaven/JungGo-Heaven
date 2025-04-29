package com.example.junggoheaven.domain.image.service;

import com.example.junggoheaven.domain.chatMessage.dto.response.ChatMessageResponseDto;
import com.example.junggoheaven.domain.chatMessage.entity.ChatMessage;
import com.example.junggoheaven.domain.chatMessage.enums.MessageType;
import com.example.junggoheaven.domain.chatMessage.redis.dto.RedisChatMessageDto;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageWriter;
import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import com.example.junggoheaven.domain.chatRoom.service.component.ChatRoomFinder;
import com.example.junggoheaven.domain.chatRoom.service.component.ChatRoomWriter;
import com.example.junggoheaven.domain.image.entity.ChatRoomImage;
import com.example.junggoheaven.domain.image.repository.ChatroomImageRepository;
import com.example.junggoheaven.domain.image.service.S3.S3StorageService;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.entity.IdGenerator;
import com.example.junggoheaven.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ChatRoomImageService {
    private final ChatroomImageRepository chatroomImageRepository;
    private final S3StorageService s3StorageService;

    private final ChatRoomFinder chatRoomFinder;

    private final ChatMessageWriter chatMessageWriter;

    private final RedisService redisService;

    private final UserFinder userFinder;

    //현재 구조상 첫 채팅을 사진으로 보낼 수 없음(텍스트를 먼저 보내 chatRoom이 생성 된 후 사진 가능)
    @Transactional
    public List<ChatMessageResponseDto> saveChatRoomImages(List<String> uploadUrls, List<MultipartFile> originalFiles, AuthUser authUser, Long chatroomId) {
        ChatRoom chatRoom = chatRoomFinder.findByChatRoomId(chatroomId);
        User user = userFinder.findByUserId(authUser.getId());

        List<ChatMessageResponseDto> responseDtos = new ArrayList<>();

        for (int i = 0; i < uploadUrls.size(); i++) {
            String imageUrl = uploadUrls.get(i);
            String originalFilename = originalFiles.get(i).getOriginalFilename();

            RedisChatMessageDto redisDto = new RedisChatMessageDto(
                    chatRoom.getId(),
                    user.getId(),
                    IdGenerator.generateId(),
                    null,  // 텍스트 메시지는 없음
                    imageUrl,
                    originalFilename,
                    MessageType.IMAGE
            );
            redisService.saveChatMessageToZSet(redisDto);

            ChatMessageResponseDto dto = new ChatMessageResponseDto(
                    redisDto.getChatRoomId(),
                    redisDto.getChatMessageId(),
                    redisDto.getSenderId(),
                    redisDto.getMessage(),
                    imageUrl,
                    redisDto.getSendAt(),
                    redisDto.getMessageType(),
                    false
            );
            responseDtos.add(dto);
        }
        return responseDtos;
    }

    @Transactional
    public void saveAll(List<RedisChatMessageDto> batchImage) {
        List<ChatMessage> chatMessages = new ArrayList<>();

        for (RedisChatMessageDto dto : batchImage) {
            ChatRoom chatRoom = chatRoomFinder.findByChatRoomId(dto.getChatRoomId());
            User sender = userFinder.findByUserId(dto.getSenderId());

            ChatMessage chatMessage = ChatMessage.of(
                    dto.getChatMessageId(),
                    chatRoom,
                    sender,
                    dto.getMessage(),
                    dto.getMessageType(),
                    dto.getSendAt(),
                    dto.getIsRead()
            );

            chatMessages.add(chatMessage);
        }

        // ChatMessage 먼저 저장
        List<ChatMessage> savedMessages = chatMessageWriter.saveAll(chatMessages);

        // ChatRoomImage는 그 다음에 ChatMessage를 참조해서 저장
        List<ChatRoomImage> chatRoomImages = new ArrayList<>();
        for (int i = 0; i < savedMessages.size(); i++) {
            RedisChatMessageDto dto = batchImage.get(i);
            ChatMessage saved = savedMessages.get(i);

            ChatRoomImage image = ChatRoomImage.of(saved, dto.getImageUrl(), dto.getKeyName());
            chatRoomImages.add(image);
        }

        chatroomImageRepository.saveAll(chatRoomImages);
    }
}
