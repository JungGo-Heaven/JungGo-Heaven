package com.example.junggoheaven.domain.chatRoom.service;

import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import com.example.junggoheaven.domain.chatRoom.repository.ChatRoomRepository;
import com.example.junggoheaven.domain.chatRoom.service.component.ChatRoomWriter;
import com.example.junggoheaven.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatRoomWriterTest {
    @InjectMocks
    private ChatRoomWriter chatRoomWriter;

    @Mock
    private ChatRoomRepository chatRoomRepository;

    private ChatRoom chatRoom;

    private User sender;
    @BeforeEach
    void setUp() {
        sender = new User("sender@n.com", "Sender", "123456789");
        ReflectionTestUtils.setField(sender, "id", 1L);

        chatRoom = new ChatRoom(null, sender);
        ReflectionTestUtils.setField(chatRoom, "id", 1L);
    }

    @Test
    public void save() {
        // given
        when(chatRoomRepository.save(any(ChatRoom.class))).thenReturn(chatRoom);

        // when
        ChatRoom result = chatRoomWriter.save(chatRoom);

        // then
        assertNotNull(result);
        assertEquals(chatRoom.getId(), result.getId());
        verify(chatRoomRepository, times(1)).save(any(ChatRoom.class));
    }
}
