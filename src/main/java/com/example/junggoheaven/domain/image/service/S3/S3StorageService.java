package com.example.junggoheaven.domain.image.service.S3;

import com.example.junggoheaven.domain.image.dto.ChatRoomUploadResponse;
import com.example.junggoheaven.domain.image.dto.ProductUploadResponse;
import com.example.junggoheaven.domain.image.dto.ProfileUploadResponse;
import com.example.junggoheaven.domain.image.exception.imageException.InvalidFileTypeException;
import com.example.junggoheaven.domain.image.service.StorageService;
import com.example.junggoheaven.domain.image.util.FIleUtils;
import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.S3Client;

import java.util.List;
import java.util.Objects;

import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.MediaType.IMAGE_PNG;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3StorageService implements StorageService {

    private final S3Client s3Client;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.cloud.aws.s3.region}")
    private String region;

    @Override
    public ProfileUploadResponse profileImageUpload(MultipartFile image, String type, AuthUser authUser) {
        checkFileType(image.getContentType());

        // random_UUID + / + System.currentTimeMillis() + "_" + originalFilename + fileExtension
        String filename = FIleUtils.buildUniqueFilename(
                Objects.requireNonNull(image.getOriginalFilename(), authUser.getName()));

        return null;
    }

    @Override
    public ProductUploadResponse productImageUpload(List<MultipartFile> images, String type, AuthUser authUser) {
        return null;
    }

    @Override
    public ChatRoomUploadResponse chatRoomImageUpload(List<MultipartFile> images, String type, AuthUser authUser) {
        return null;
    }

    private void checkFileType(String contentType) {
        if (!IMAGE_PNG.toString().equals(contentType) && !IMAGE_JPEG.toString().equals(contentType)) {
            throw new InvalidFileTypeException();
        }
    }
}
