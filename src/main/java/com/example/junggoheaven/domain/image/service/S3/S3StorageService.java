package com.example.junggoheaven.domain.image.service.S3;

import com.example.junggoheaven.domain.image.dto.MultipleUploadResponse;
import com.example.junggoheaven.domain.image.dto.UploadContext;
import com.example.junggoheaven.domain.image.dto.UploadResponse;
import com.example.junggoheaven.domain.image.enums.UploadType;
import com.example.junggoheaven.domain.image.exception.*;
import com.example.junggoheaven.domain.image.service.StorageService;
import com.example.junggoheaven.domain.image.util.FIleUtils;
import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.example.junggoheaven.domain.image.enums.UploadType.*;
import static org.springframework.http.MediaType.IMAGE_JPEG;
import static org.springframework.http.MediaType.IMAGE_PNG;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3StorageService implements StorageService {

    private final S3Client s3Client;
    private final UserFinder userFinder;

    @Value("${cloudfront.domain}")
    private String cloudFrontDomain;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucket;

    @Value("${spring.cloud.aws.region.static}")
    private String region;

    @Override
    public UploadResponse upload(MultipartFile image, String type, AuthUser authUser, Long resourceId) {
        checkFileType(image.getContentType());

        // random_UUID + / + System.currentTimeMillis() + "_" + originalFilename + fileExtension
        String filename = FIleUtils.buildUniqueFilename(
                Objects.requireNonNull(image.getOriginalFilename(), authUser.getName()));

        // UserRole 및 uploadType check
        Long userId = authUser.getId();
        UserRole userRole = userFinder.findByUserId(userId).getRole();
        UploadType uploadType = UploadType.from(type);
        if (!uploadType.isAllowedFor(userRole)) {
            throw new UploadAccessDeniedException();
        }

        // 업로드 타입 별 id도 함께 key 값에 추가
        String key = uploadType.buildKey(resourceId, filename);

        // S3에 upload
        PutObjectRequest request = PutObjectRequest.builder()
                .bucket(bucket)
                .key(key)
                .contentType(image.getContentType())
                .cacheControl("public, max-age=7776000") // 캐시 3개월
                .build();

        try {
            s3Client.putObject(request, RequestBody.fromInputStream(image.getInputStream(), image.getSize()));
            log.info("https://{}.s3.{}.amazonaws.com/{}", bucket, region, key);
            return UploadResponse.of(key);
        } catch (S3Exception | IOException e) {
            log.info("알 수 없는 에러로 인해 파일 업로드를 실패하였습니다.");
            throw new UnexpectedErrorException();
        }
    }

    @Override
    public MultipleUploadResponse productImageUpload(List<MultipartFile> originalImages, String type, AuthUser authUser, UploadContext uploadContext) {

        Set<UploadType> invalidUploadTypes = Set.of(PROFILES, CHAT_ROOMS);
        List<String> responses = multipleUpload(originalImages, type, authUser, invalidUploadTypes, uploadContext);
        return MultipleUploadResponse.of(responses);
    }

    @Override
    public MultipleUploadResponse chatRoomImageUpload(List<MultipartFile> originalImages, String type, AuthUser authUser, UploadContext uploadContext) {

        Set<UploadType> invalidUploadTypes = Set.of(PROFILES, PRODUCTS);
        List<String> responses = multipleUpload(originalImages, type, authUser, invalidUploadTypes, uploadContext);
        return MultipleUploadResponse.of(responses);
    }

    // 사진 파일 외 다른 파일 확장자 예외처리
    private void checkFileType(String contentType) {
        System.out.println(contentType);
        if (!IMAGE_PNG.toString().equals(contentType) && !IMAGE_JPEG.toString().equals(contentType)) {
            throw new InvalidFileTypeException();
        }
    }

    // 다중 이미지 업로드 공통 로직
    private List<String> multipleUpload(List<MultipartFile> images, String type, AuthUser authUser, Set<UploadType> invalidUploadTypes, UploadContext uploadContext) {

        int counter = 0;
        // UserRole 및 uploadType check
        Long userId = authUser.getId();
        UserRole userRole = userFinder.findByUserId(userId).getRole();
        UploadType uploadType = UploadType.from(type);
        if (!uploadType.isAllowedFor(userRole)) {
            throw new UploadAccessDeniedException();
        }

        // 업로드 하고자 하는 타입 외 다른 타입들 예외처리
        if (invalidUploadTypes.contains(uploadType)) {
            throw new InvalidUploadTypeException();
        }

        Long resourceId = uploadContext.getResourceId(uploadType);

        List<String> responses = new ArrayList<>();
        for (MultipartFile image : images) {
            try {
                counter++;
                UploadResponse upload = upload(image, type, authUser, resourceId);
                String fullUrl = buildCloudFrontUrl(upload.getUploadUrl());
                responses.add(fullUrl);
            } catch (Exception e) {
                log.error("{} 번째 업로드 중 예외 발생: {}", counter, e.getLocalizedMessage());
                throw new ImageUploadIOException();
            }
        }
        return responses;
    }

    // S3에 업로드된 객체 URL과 동일한 URL로 변경
    public String buildCloudFrontUrl(String key) {
        return "https://" + cloudFrontDomain + "/" + key;
    }
}
