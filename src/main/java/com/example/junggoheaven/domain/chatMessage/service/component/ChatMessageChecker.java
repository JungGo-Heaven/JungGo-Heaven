package com.example.junggoheaven.domain.chatMessage.service.component;

import com.example.junggoheaven.domain.chatMessage.repository.ChatMessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMessageChecker {
    private final ChatMessageRepository chatMessageRepository;
}
