package com.example.junggoheaven.domain.image.service;

import com.example.junggoheaven.domain.image.dto.MultipleUploadResponse;
import com.example.junggoheaven.domain.image.dto.UploadContext;
import com.example.junggoheaven.domain.image.dto.UploadResponse;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    UploadResponse upload(MultipartFile image, String type, AuthUser authUser, Long userId);

    MultipleUploadResponse productImageUpload(List<MultipartFile> images, String type, AuthUser authUser, UploadContext uploadContext);

    MultipleUploadResponse chatRoomImageUpload(List<MultipartFile> images, String type, AuthUser authUser, UploadContext uploadContext);
}
