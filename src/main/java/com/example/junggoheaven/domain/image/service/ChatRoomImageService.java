package com.example.junggoheaven.domain.image.service;

import com.example.junggoheaven.domain.chatMessage.dto.response.ChatMessageResponseDto;
import com.example.junggoheaven.domain.chatMessage.entity.ChatMessage;
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

    private final UserFinder userFinder;

    @Transactional
    public List<ChatMessageResponseDto> saveChatRoomImages(List<String> uploadUrls, List<MultipartFile> originalFiles, AuthUser authUser, Long chatroomId) {
        ChatRoom chatRoom = chatRoomFinder.findByChatRoomId(chatroomId);
        User user = userFinder.findByUserId(authUser.getId());

        List<ChatRoomImage> chatRoomImages = new ArrayList<>();
        List<ChatMessageResponseDto> responseDtos = new ArrayList<>();

        for (int i = 0; i < uploadUrls.size(); i++) {
            ChatMessage chatMessage = new ChatMessage(chatRoom, user);
            ChatMessage savedChatMessage = chatMessageWriter.save(chatMessage);

            String imageUrl = uploadUrls.get(i);
            String originalFilename = originalFiles.get(i).getOriginalFilename();

            String fullUrl = s3StorageService.buildS3Url(imageUrl);

            ChatRoomImage chatRoomImage = new ChatRoomImage(savedChatMessage, fullUrl, originalFilename);
            chatRoomImages.add(chatRoomImage);

            ChatMessageResponseDto dto = new ChatMessageResponseDto(
                    savedChatMessage.getChatRoom().getId(),
                    savedChatMessage.getId(),
                    savedChatMessage.getSender().getId(),
                    savedChatMessage.getMessage(),
                    fullUrl,
                    savedChatMessage.getSendAt(),
                    savedChatMessage.getMessageType()
            );
            responseDtos.add(dto);
        }
        chatroomImageRepository.saveAll(chatRoomImages);
        return responseDtos;
    }
}
