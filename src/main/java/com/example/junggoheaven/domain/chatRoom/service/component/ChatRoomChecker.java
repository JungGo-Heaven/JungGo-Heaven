package com.example.junggoheaven.domain.chatRoom.service.component;

import com.example.junggoheaven.domain.chatRoom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatRoomChecker {
    private final ChatRoomRepository chatRoomRepository;
}
