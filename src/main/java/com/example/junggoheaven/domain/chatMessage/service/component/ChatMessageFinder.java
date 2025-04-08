package com.example.junggoheaven.domain.chatMessage.service.component;

import com.example.junggoheaven.domain.chatMessage.entity.ChatMessage;
import com.example.junggoheaven.domain.chatMessage.exception.MessageNotFoundException;
import com.example.junggoheaven.domain.chatMessage.repository.ChatMessageRepository;
import com.example.junggoheaven.domain.chatMessage.dto.response.LatestChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatMessageFinder {
    private final ChatMessageRepository chatMessageRepository;

    public List<ChatMessage> findByChatRoomIdOrderBySendAtAsc(Long chatRoomId) {
        List<ChatMessage> messages = chatMessageRepository.findByChatRoomIdOrderBySendAtAsc(chatRoomId);
        if (messages.isEmpty()) {
            throw new MessageNotFoundException();
        }
        return messages;
    }

    public Map<Long, ChatMessage> findLatestMessagesForChatRooms(List<Long> chatRoomIds) {
        List<LatestChatMessageDto> dtoList = chatMessageRepository.findLatestMessagesForChatRooms(chatRoomIds);
        return dtoList.stream()
                .collect(Collectors.toMap(LatestChatMessageDto::chatRoomId, LatestChatMessageDto::chatMessage));
    }


    public List<ChatMessage> findUnreadMessages(List<Long> messageIds, Long chatRoomId, Long userId) {
        return chatMessageRepository.findUnreadMessages(messageIds, chatRoomId, userId);
    }

    public List<ChatMessage> findAllById(List<Long> messageIds) {
        return chatMessageRepository.findAllById(messageIds);
    }
}
