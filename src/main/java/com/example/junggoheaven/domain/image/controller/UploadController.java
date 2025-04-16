package com.example.junggoheaven.domain.image.controller;

import com.example.junggoheaven.domain.chatMessage.dto.response.ChatMessageResponseDto;
import com.example.junggoheaven.domain.image.dto.MultipleUploadResponse;
import com.example.junggoheaven.domain.image.dto.UploadResponse;
import com.example.junggoheaven.domain.image.entity.ProfileImage;
import com.example.junggoheaven.domain.image.exception.FileUploadLimitExceededException;
import com.example.junggoheaven.domain.image.service.ChatRoomImageService;
import com.example.junggoheaven.domain.image.service.ProductImageService;
import com.example.junggoheaven.domain.image.service.ProfileImageService;
import com.example.junggoheaven.domain.image.service.S3.S3StorageService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/v1/images/s3", method = RequestMethod.POST)
public class UploadController {

    private final S3StorageService s3StorageService;
    private final ProfileImageService profileImageService;
    private final ProductImageService productImageService;
    private final ChatRoomImageService chatRoomImageService;

    private final SimpMessagingTemplate simpMessagingTemplate;


    @PostMapping("/files/profiles")
    public ResponseDto<UploadResponse> uploadFile(
            @RequestPart("multipartFile") MultipartFile multipartFile,
            @RequestPart("type") String type,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        ProfileImage profile = profileImageService.uploadProfileImage(multipartFile, authUser);
        return ResponseDto.success(new UploadResponse(profile.getProfileImageUrl()));
    }

    @PostMapping("/files/multiples/products")
    public ResponseDto<MultipleUploadResponse> uploadProductImages(
            @RequestPart("multipartFiles") List<MultipartFile> multipartFiles,
            @RequestPart("type") String type,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        if (multipartFiles.size() > 3) {
            throw new FileUploadLimitExceededException();
        }
        MultipleUploadResponse response = s3StorageService.productImageUpload(multipartFiles, type, authUser);

        // 업로드된 URL을 기반으로 ProductImage 생성 및 저장
        productImageService.saveProductImages(response.getUploadUrls(), multipartFiles, authUser);
        return ResponseDto.success(response);
    }

    @PostMapping("/files/multiples/chat-rooms/{chatRoomId}")
    public void uploadChatRoomImages(
            @RequestPart("multipartFiles") List<MultipartFile> multipartFiles,
            @RequestPart("type") String type,
            @AuthenticationPrincipal AuthUser authUser,
            @PathVariable Long chatRoomId
            ) {
        if (multipartFiles.size() > 5) {
            throw new FileUploadLimitExceededException();
        }
        MultipleUploadResponse response = s3StorageService.chatRoomImageUpload(multipartFiles, type, authUser);

        List<ChatMessageResponseDto> responseDtos = chatRoomImageService.saveChatRoomImages(
                response.getUploadUrls(), multipartFiles, authUser, chatRoomId
        );

        for (ChatMessageResponseDto dto : responseDtos) {
            simpMessagingTemplate.convertAndSend(
                    "/sub/chat/room/" + chatRoomId,
                    dto
            );
        }
    }
}
