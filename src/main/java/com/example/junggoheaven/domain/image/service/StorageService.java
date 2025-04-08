package com.example.junggoheaven.domain.image.service;

import com.example.junggoheaven.domain.image.dto.ChatRoomUploadResponse;
import com.example.junggoheaven.domain.image.dto.ProductUploadResponse;
import com.example.junggoheaven.domain.image.dto.ProfileUploadResponse;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    ProfileUploadResponse profileImageUpload(MultipartFile image, String type, AuthUser authUser);

    ProductUploadResponse productImageUpload(List<MultipartFile> images, String type, AuthUser authUser);

    ChatRoomUploadResponse chatRoomImageUpload(List<MultipartFile> images, String type, AuthUser authUser);
}
