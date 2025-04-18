package com.example.junggoheaven.domain.chatMessage.service;

import com.example.junggoheaven.domain.chatMessage.entity.ChatMessage;
import com.example.junggoheaven.domain.chatMessage.enums.MessageType;
import com.example.junggoheaven.domain.chatMessage.repository.ChatMessageRepository;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageWriter;
import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import com.example.junggoheaven.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import static org.hamcrest.Matchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatMessageWriterTest {
    @Mock
    private ChatMessageRepository chatMessageRepository;

    @InjectMocks
    private ChatMessageWriter chatMessageWriter;

    private ChatRoom chatRoom;
    private User sender;
    private ChatMessage chatMessage;

    @BeforeEach
    void setUp() {
        sender = User.of("sender@n.com", "Sender", "123456789");
        ReflectionTestUtils.setField(sender, "id", 1L);

        chatRoom = ChatRoom.of(null, sender);
        ReflectionTestUtils.setField(chatRoom, "id", 1L);

        chatMessage = ChatMessage.of(chatRoom, sender, "test message", MessageType.TEXT);
    }

    @Test
    public void save() {
        // given
        when(chatMessageRepository.save(chatMessage)).thenReturn(chatMessage);

        // when
        ChatMessage savedMessage = chatMessageWriter.save(chatMessage);

        // then
        assertNotNull(savedMessage);
        assertEquals("test message", savedMessage.getMessage());
        verify(chatMessageRepository, times(1)).save(chatMessage);
    }
}
