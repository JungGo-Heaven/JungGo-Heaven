package com.example.junggoheaven.domain.chatRoom.service;

import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import com.example.junggoheaven.domain.chatRoom.exception.ChatRoomNotFound;
import com.example.junggoheaven.domain.chatRoom.repository.ChatRoomRepository;
import com.example.junggoheaven.domain.chatRoom.service.component.ChatRoomFinder;
import com.example.junggoheaven.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatRoomFinderTest {
    @InjectMocks
    private ChatRoomFinder chatRoomFinder;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    private User sender;
    private ChatRoom chatRoom;

    @BeforeEach
    void setUp() {
        sender = new User("sender@n.com", "Sender", "123456789");
        ReflectionTestUtils.setField(sender, "id", 1L);

        chatRoom = new ChatRoom(null, sender);
        ReflectionTestUtils.setField(chatRoom, "id", 1L);
    }

    @Test
    public void findByProductIdAndBuyerIdOpt() {
        // given
        Long productId = 1L;
        Long buyerId = 2L;
        when(chatRoomRepository.findByProductIdAndBuyerId(productId, buyerId))
                .thenReturn(java.util.Optional.of(chatRoom));

        // when
        Optional<ChatRoom> result = chatRoomFinder.findByProductIdAndBuyerIdOpt(productId, buyerId);

        // then
        assertTrue(result.isPresent());
        assertEquals(chatRoom.getId(), result.get().getId());
    }

    @Test
    public void findByChatRoomId_채팅방이_없을때_에러() {
        // given
        Long chatRoomId = 1L;
        when(chatRoomRepository.findByChatRoomId(chatRoomId)).thenReturn(null);

        // when & then
        assertThrows(ChatRoomNotFound.class, () -> chatRoomFinder.findByChatRoomId(chatRoomId));
    }

    @Test
    public void findByChatRoomId() {
        // given
        Long chatRoomId = 1L;
        when(chatRoomRepository.findByChatRoomId(chatRoomId)).thenReturn(chatRoom);

        // when
        ChatRoom result = chatRoomFinder.findByChatRoomId(chatRoomId);

        // then
        assertEquals(chatRoom.getId(), result.getId());
    }

    @Test
    public void findPagingChatRooms() {
        // given
        Long userId = 1L;
        PageRequest pageable = PageRequest.of(0, 10);
        Page<ChatRoom> page = mock(Page.class);
        when(chatRoomRepository.findPagingChatRooms(userId, pageable)).thenReturn(page);

        // when
        Page<ChatRoom> result = chatRoomFinder.findPagingChatRooms(userId, pageable);

        // then
        assertNotNull(result);
        verify(chatRoomRepository, times(1)).findPagingChatRooms(userId, pageable);
    }
}
