package com.example.junggoheaven.domain.image.service;

import com.example.junggoheaven.domain.image.dto.ChatRoomUploadResponse;
import com.example.junggoheaven.domain.image.dto.ProductUploadResponse;
import com.example.junggoheaven.domain.image.dto.ProfileUploadResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface StorageService {

    //검증된 유저 추가해야함
    ProfileUploadResponse profileImageUpload(MultipartFile image, String type);
    //검증된 유저 추가해야함
    ProductUploadResponse productImageUpload(List<MultipartFile> images, String type);
    //검증된 유저 추가해야함
    ChatRoomUploadResponse chatRoomImageUpload(List<MultipartFile> images, String type);
}
