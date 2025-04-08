package com.example.junggoheaven.domain.image.controller;

import com.example.junggoheaven.domain.image.dto.ProfileUploadResponse;
import com.example.junggoheaven.domain.image.service.S3.S3StorageService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
public class UploadController {

    private final S3StorageService s3StorageService;

    @PostMapping("/profiles")
    public ResponseDto<ProfileUploadResponse> uploadProfile(
            @RequestPart("multipartFile") MultipartFile multipartFile,
            @RequestParam("type") String type,
            @AuthenticationPrincipal AuthUser authUser
    ) {
        ProfileUploadResponse response = s3StorageService.profileImageUpload(multipartFile, type, authUser);
        return ResponseDto.success(response);
    }
}
