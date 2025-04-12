package com.example.junggoheaven.domain.chatRoom.service.component;

import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import com.example.junggoheaven.domain.chatRoom.exception.ChatRoomNotFound;
import com.example.junggoheaven.domain.chatRoom.repository.ChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomFinder {
    private final ChatRoomRepository chatRoomRepository;

    public Optional<ChatRoom> findByProductIdAndBuyerIdOpt(Long productId, Long buyerId) {
        return chatRoomRepository.findByProductIdAndBuyerId(productId, buyerId);
    }

    public Page<ChatRoom> findPagingChatRooms(Long userId, Pageable pageable) {
        return chatRoomRepository.findPagingChatRooms(userId, pageable);
    }

    public ChatRoom findByChatRoomId(Long chatRoomId) {
        ChatRoom chatRoom = chatRoomRepository.findByChatRoomId(chatRoomId);
        if(chatRoom == null) {
            throw new ChatRoomNotFound();
        }
        return chatRoom;
    }

}
