package com.example.junggoheaven.domain.chatRoom.controller;

import com.example.junggoheaven.domain.chatRoom.dto.response.ChatRoomEnterResponseDto;
import com.example.junggoheaven.domain.chatRoom.dto.response.ChatRoomsResponseDto;
import com.example.junggoheaven.domain.chatRoom.service.ChatRoomService;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/chat-rooms")
@RequiredArgsConstructor
public class ChatRoomController {
    private final ChatRoomService chatRoomService;

    @GetMapping("/{productId}")
    public ResponseEntity<ResponseDto<ChatRoomEnterResponseDto>> enterChatRoom(
            @PathVariable Long productId,
            @AuthenticationPrincipal AuthUser authUser) {
        return ResponseEntity.ok(chatRoomService.enterChatRoom(productId, authUser));
    }

    @GetMapping
    public ResponseEntity<ResponseDto<Page<ChatRoomsResponseDto>>> getChatRooms(
            @AuthenticationPrincipal AuthUser authUser,
            Pageable pageable
    ){
        return ResponseEntity.ok(chatRoomService.getChatRooms(authUser.getId(), pageable));
    }

    @PutMapping("/{chatRoomId}")
    public void exitChatRoom(
            @PathVariable Long chatRoomId,
            @AuthenticationPrincipal AuthUser authUser
            ) {
        chatRoomService.exitChatRoom(chatRoomId, authUser);
    }




}
