package com.example.junggoheaven.domain.image;

import com.example.junggoheaven.domain.image.entity.ProductImage;
import com.example.junggoheaven.domain.image.repository.ProductImageRepository;
import com.example.junggoheaven.domain.image.service.ProductImageService;
import com.example.junggoheaven.domain.image.service.S3.S3StorageService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static com.example.junggoheaven.domain.user.enums.UserRole.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProductImageServiceTest {

    @InjectMocks
    private ProductImageService productImageService;

    @Mock
    private ProductImageRepository productImageRepository;

    @Mock
    private S3StorageService s3StorageService;

    @Test
    void productImage가_정상적으로_업로드_되면_productImageRepository에_저장된다() {
        //given
        List<String> uploadUrls = List.of("http://cloudfront.net/image1.jpeg", "http://cloudfront.net/image2.jpeg");

        MultipartFile multipartFile1 = mock(MultipartFile.class);
        MultipartFile multipartFile2 = mock(MultipartFile.class);
        when(multipartFile1.getOriginalFilename()).thenReturn("image1.jpeg");
        when(multipartFile2.getOriginalFilename()).thenReturn("image2.jpeg");

        List<MultipartFile> originalFiles = List.of(multipartFile1,multipartFile2);
        AuthUser authUser = new AuthUser(1L, "a@a.com", ROLE_USER, "test");

        //when
        productImageService.saveProductImages(uploadUrls, originalFiles, authUser);

        //then
        //saveAll에 전달된 객체 검증
        ArgumentCaptor<List<ProductImage>> captor = ArgumentCaptor.forClass(List.class);
        verify(productImageRepository).saveAll(captor.capture());

        List<ProductImage> savedImages = captor.getValue();
        assertEquals(2, savedImages.size());
        assertEquals("http://cloudfront.net/image1.jpeg",savedImages.get(0).getProductImageUrl());
        assertEquals("image1.jpeg", savedImages.get(0).getKeyName());
        assertEquals("http://cloudfront.net/image2.jpeg",savedImages.get(1).getProductImageUrl());
        assertEquals("image2.jpeg", savedImages.get(1).getKeyName());

    }
}
