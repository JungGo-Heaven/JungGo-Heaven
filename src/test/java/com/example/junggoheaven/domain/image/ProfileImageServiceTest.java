package com.example.junggoheaven.domain.image;

import com.example.junggoheaven.domain.image.dto.UploadResponse;
import com.example.junggoheaven.domain.image.entity.ProfileImage;
import com.example.junggoheaven.domain.image.repository.ProfileImageRepository;
import com.example.junggoheaven.domain.image.service.ProfileImageService;
import com.example.junggoheaven.domain.image.service.S3.S3StorageService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.util.ReflectionTestUtils;

import static com.example.junggoheaven.domain.user.enums.UserRole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ProfileImageServiceTest {

    @InjectMocks
    private ProfileImageService profileImageService;

    @Mock
    private ProfileImageRepository profileImageRepository;

    @Mock
    private S3StorageService s3StorageService;

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(profileImageService, "cloudFrontDomain", "d111111abcdef8.cloudfront.net");

    }

    @Test
    void uploadProfileImage_정상적으로_업로드되면_ProfileImage_저장됨() {
        //given
        MockMultipartFile mockMultipartFile = new MockMultipartFile(
                "file", "profile.jpeg", "image/jpeg", "fake-image-content".getBytes()
        );
        AuthUser authUser = new AuthUser(1L, "a@a.com", ROLE_USER, "test");
        Long userId = authUser.getId();
        UploadResponse uploadResponse = new UploadResponse("user/1/profile.jpeg");

        when(s3StorageService.upload(mockMultipartFile, "profiles", authUser, userId))
                .thenReturn(uploadResponse);

        // 저장된 profileImage를 검증하기 위해 캡처
        ArgumentCaptor<ProfileImage> profileImageCaptor = ArgumentCaptor.forClass(ProfileImage.class);
        when(profileImageRepository.save(any(ProfileImage.class)))
                .thenAnswer(invocation -> invocation.getArgument(0)); // 저장된 0번째 객체 리턴

        //when : 파일 업로드
        ProfileImage result = profileImageService.uploadProfileImage(mockMultipartFile, authUser, userId);

        //then : S3 업로드 로직 호출 여부 확인
        verify(s3StorageService).upload(mockMultipartFile, "profiles", authUser, userId);
        verify(profileImageRepository).save(profileImageCaptor.capture());

        ProfileImage savedImage = profileImageCaptor.getValue();

        assertNotNull(result);
        assertEquals("profile.jpeg", savedImage.getKeyName());
        assertEquals(savedImage.getProfileImageUrl(), result.getProfileImageUrl());
    }
}
