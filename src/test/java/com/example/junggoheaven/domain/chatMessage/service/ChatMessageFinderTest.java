package com.example.junggoheaven.domain.chatMessage.service;

import com.example.junggoheaven.domain.chatMessage.dto.response.LatestChatMessageDto;
import com.example.junggoheaven.domain.chatMessage.entity.ChatMessage;
import com.example.junggoheaven.domain.chatMessage.enums.MessageType;
import com.example.junggoheaven.domain.chatMessage.exception.MessageNotFoundException;
import com.example.junggoheaven.domain.chatMessage.repository.ChatMessageRepository;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageFinder;
import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import com.example.junggoheaven.domain.user.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatMessageFinderTest {
    @Mock
    private ChatMessageRepository chatMessageRepository;

    @InjectMocks
    private ChatMessageFinder chatMessageFinder;

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
    public void findByChatRoomIdOrderBySendAtAsc_성공() {
        // given
        List<ChatMessage> messages = Arrays.asList(chatMessage);
        when(chatMessageRepository.findByChatRoomIdOrderBySendAtAsc(anyLong())).thenReturn(messages);

        // when
        List<ChatMessage> result = chatMessageFinder.findByChatRoomIdOrderBySendAtAsc(1L);

        // then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("test message", result.get(0).getMessage());
    }

    @Test
    public void findByChatRoomIdOrderBySendAtAsc_메시지없음() {
        // given
        when(chatMessageRepository.findByChatRoomIdOrderBySendAtAsc(anyLong())).thenReturn(Arrays.asList());

        // when & then
        assertThrows(MessageNotFoundException.class, () -> chatMessageFinder.findByChatRoomIdOrderBySendAtAsc(1L));
    }

    @Test
    public void findLatestMessagesForChatRooms() {
        // given
        when(chatMessageRepository.findLatestMessagesForChatRooms(anyList())).thenReturn(Arrays.asList(
                new LatestChatMessageDto(1L, chatMessage)
        ));

        // when
        Map<Long, ChatMessage> latestMessages = chatMessageFinder.findLatestMessagesForChatRooms(Arrays.asList(1L));

        // then
        assertNotNull(latestMessages);
        assertTrue(latestMessages.containsKey(1L));
        assertEquals("test message", latestMessages.get(1L).getMessage());
    }

    @Test
    public void findUnreadMessages() {
        // given
        when(chatMessageRepository.findUnreadMessages(anyList(), anyLong(), anyLong())).thenReturn(Arrays.asList(chatMessage));

        // when
        List<ChatMessage> unreadMessages = chatMessageFinder.findUnreadMessages(Arrays.asList(1L), 1L, 1L);

        // then
        assertNotNull(unreadMessages);
        assertEquals(1, unreadMessages.size());
        assertEquals("test message", unreadMessages.get(0).getMessage());
    }

    @Test
    public void findAllById() {
        // given
        when(chatMessageRepository.findAllById(anyList())).thenReturn(Arrays.asList(chatMessage));

        // when
        List<ChatMessage> messages = chatMessageFinder.findAllById(Arrays.asList(1L));

        // then
        assertNotNull(messages);
        assertEquals(1, messages.size());
        assertEquals("test message", messages.get(0).getMessage());
    }

}
