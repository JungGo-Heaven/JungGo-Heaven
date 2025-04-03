package com.example.junggoheaven.domain.image.service.S3;

import com.example.junggoheaven.domain.image.dto.ChatRoomUploadResponse;
import com.example.junggoheaven.domain.image.dto.ProductUploadResponse;
import com.example.junggoheaven.domain.image.dto.ProfileUploadResponse;
import com.example.junggoheaven.domain.image.service.StorageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3StorageService implements StorageService {

    private final S3Client s3Client;

    @Override
    public ProfileUploadResponse profileImageUpload(MultipartFile image, String type) {
        return null;
    }

    @Override
    public ProductUploadResponse productImageUpload(List<MultipartFile> images, String type) {
        return null;
    }

    @Override
    public ChatRoomUploadResponse chatRoomImageUpload(List<MultipartFile> images, String type) {
        return null;
    }
}
