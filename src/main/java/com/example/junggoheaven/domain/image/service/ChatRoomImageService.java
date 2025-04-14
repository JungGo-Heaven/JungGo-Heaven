package com.example.junggoheaven.domain.image.service;

import com.example.junggoheaven.domain.image.entity.ChatRoomImage;
import com.example.junggoheaven.domain.image.repository.ChatroomImageRepository;
import com.example.junggoheaven.domain.image.service.S3.S3StorageService;
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

    @Transactional
    public void saveChatRoomImages(List<String> uploadUrls, List<MultipartFile> originalFiles, AuthUser authUser) {
        List<ChatRoomImage> chatRoomImages = new ArrayList<>();

        for (int i = 0; i < uploadUrls.size(); i++) {
            String imageUrl = uploadUrls.get(i);
            String originalFilename = originalFiles.get(i).getOriginalFilename();

            String fullUrl = s3StorageService.buildS3Url(imageUrl);

            ChatRoomImage chatRoomImage = new ChatRoomImage(fullUrl, originalFilename);
            chatRoomImages.add(chatRoomImage);
        }
        chatroomImageRepository.saveAll(chatRoomImages);
    }
}
