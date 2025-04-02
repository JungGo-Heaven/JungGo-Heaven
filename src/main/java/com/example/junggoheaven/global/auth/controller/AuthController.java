package com.example.junggoheaven.global.auth.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.junggoheaven.global.auth.service.AuthService;
import com.example.junggoheaven.global.common.response.ResponseDto;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class AuthController {

	private final AuthService authService;

}
