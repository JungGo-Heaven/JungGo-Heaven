package com.example.junggoheaven.domain.image.controller;

import com.example.junggoheaven.domain.image.service.S3.S3StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ImageUploadController {

    private final S3StorageService s3StorageService;
}
