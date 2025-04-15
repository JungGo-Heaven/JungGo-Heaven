package com.example.junggoheaven.domain.image.service;

import com.example.junggoheaven.domain.image.dto.UploadResponse;
import com.example.junggoheaven.domain.image.entity.ProfileImage;
import com.example.junggoheaven.domain.image.repository.ProfileImageRepository;
import com.example.junggoheaven.domain.image.service.S3.S3StorageService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class ProfileImageService {

    private final ProfileImageRepository profileImageRepository;
    private final S3StorageService s3StorageService;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    @Transactional
    public ProfileImage uploadProfileImage(MultipartFile image, AuthUser authUser) {
        UploadResponse uploadResponse = s3StorageService.upload(image, "profiles", authUser);
        String profileImageUrl = "https://" + bucket + ".s3." + region + ".amazonaws.com/" + uploadResponse.getUploadUrl();

        ProfileImage profileImage = new ProfileImage(image.getOriginalFilename(),profileImageUrl);
        return profileImageRepository.save(profileImage);
    }
}
