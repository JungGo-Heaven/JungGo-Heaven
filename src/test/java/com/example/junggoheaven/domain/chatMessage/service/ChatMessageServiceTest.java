package com.example.junggoheaven.domain.chatMessage.service;

import com.example.junggoheaven.domain.chatMessage.dto.request.ChatDeleteRequestDto;
import com.example.junggoheaven.domain.chatMessage.dto.request.ChatMessageRequestDto;
import com.example.junggoheaven.domain.chatMessage.dto.request.ChatReadRequestDto;
import com.example.junggoheaven.domain.chatMessage.dto.response.ChatMessageResponseDto;
import com.example.junggoheaven.domain.chatMessage.entity.ChatMessage;
import com.example.junggoheaven.domain.chatMessage.enums.MessageType;
import com.example.junggoheaven.domain.chatMessage.exception.ChatRoomMissMatchException;
import com.example.junggoheaven.domain.chatMessage.exception.NoPermissionToChatMessage;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageChecker;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageFinder;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageWriter;
import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import com.example.junggoheaven.domain.chatRoom.service.component.ChatRoomFinder;
import com.example.junggoheaven.domain.chatRoom.service.component.ChatRoomWriter;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.product.repository.ProductRepository;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ChatMessageServiceTest {
    @InjectMocks
    private ChatMessageService chatMessageService;

    @Mock
    private ChatMessageChecker chatMessageChecker;

    @Mock
    private ChatMessageFinder chatMessageFinder;

    @Mock
    private ChatMessageWriter chatMessageWriter;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ChatRoomFinder chatRoomFinder;

    @Mock
    private ChatRoomWriter chatRoomWriter;

    @Mock
    private UserFinder userFinder;


    private User buyerUser;
    private User sellerUser;
    private Product product;
    private ChatRoom chatRoom;

    @BeforeEach
    void setUp() {
        buyerUser = User.of("buyer@n.com", "Buyer", "123456789");
        ReflectionTestUtils.setField(buyerUser, "id", 1L);

        sellerUser = User.of("seller@n.com", "Seller", "987654321");
        ReflectionTestUtils.setField(sellerUser, "id", 2L);

        product = new Product(sellerUser, "test name", "Product test", 1000L);
        ReflectionTestUtils.setField(product, "id", 1L);

        chatRoom = ChatRoom.of(product, buyerUser);
        ReflectionTestUtils.setField(chatRoom, "id", 1L);

    }

    @Test
    public void 채팅메시지_생성_채팅방_있을때() {
        // given
        ChatMessageRequestDto requestDto = new ChatMessageRequestDto(1L, 1L, "test message", MessageType.TEXT);
        when(chatRoomFinder.findByChatRoomId(1L)).thenReturn(chatRoom);
        when(chatMessageWriter.save(any(ChatMessage.class))).thenReturn(ChatMessage.of(chatRoom, buyerUser, "test message", MessageType.TEXT));

        // when
        ChatMessageResponseDto response = chatMessageService.createMessage(requestDto, 1L);

        // then
        assertNotNull(response);
        verify(chatRoomWriter, times(0)).save(any(ChatRoom.class));
        verify(chatMessageWriter, times(1)).save(any(ChatMessage.class));
        assertEquals("test message", response.getMessage());
    }

    @Test
    public void 채팅메시지_생성_채팅방_없을때() {
        // given
        ChatMessageRequestDto requestDto = new ChatMessageRequestDto(null, 1L, "test message", MessageType.TEXT);

        when(productRepository.findById(anyLong())).thenReturn(Optional.of(product));
        when(chatMessageWriter.save(any(ChatMessage.class))).thenReturn(ChatMessage.of(ChatRoom.of(product, buyerUser), buyerUser, "test message", MessageType.TEXT));

        // when
        ChatMessageResponseDto response = chatMessageService.createMessage(requestDto, 1L);

        // then
        assertNotNull(response);
        assertEquals("test message", response.getMessage());
        verify(chatRoomWriter, times(1)).save(any(ChatRoom.class));
        verify(chatMessageWriter, times(1)).save(any(ChatMessage.class));
    }

    @Test
    public void 읽지_않은_메시지_읽음처리() {
        // given
        ChatMessage chatMessage = ChatMessage.of(chatRoom, buyerUser, "test message", MessageType.TEXT);
        ReflectionTestUtils.setField(chatMessage, "id", 1L);

        ChatReadRequestDto requestDto = new ChatReadRequestDto(1L, Arrays.asList(1L));

        List<ChatMessage> unreadMessages = Arrays.asList(chatMessage);

        when(chatMessageFinder.findUnreadMessages(anyList(), anyLong(), anyLong())).thenReturn(unreadMessages);
        when(chatRoomFinder.findByChatRoomId(anyLong())).thenReturn(chatRoom);
        when(userFinder.findByUserId(anyLong())).thenReturn(sellerUser);

        // when
        chatMessageService.isRead(requestDto, sellerUser.getId());

        // then
        assertTrue(unreadMessages.get(0).getIsRead());
    }

    @Test
    public void 메시지_삭제_권한없음() {
        // given
        List<Long> deleteMessageIds = Arrays.asList(1L);
        ChatDeleteRequestDto requestDto = new ChatDeleteRequestDto(1L, deleteMessageIds);

        ChatMessage message = ChatMessage.of(chatRoom, buyerUser, "test message", MessageType.TEXT);
        when(chatMessageFinder.findAllById(deleteMessageIds)).thenReturn(Arrays.asList(message));

        // when & then
        assertThrows(NoPermissionToChatMessage.class, () -> chatMessageService.deleteMessage(requestDto, 2L));
    }

    @Test
    public void 메시지_삭제_채팅방_불일치() {
        // given
        List<Long> deleteMessageIds = Arrays.asList(1L);
        ChatDeleteRequestDto requestDto = new ChatDeleteRequestDto(2L, deleteMessageIds);

        ChatMessage message = ChatMessage.of(chatRoom, buyerUser, "test message", MessageType.TEXT);
        when(chatMessageFinder.findAllById(deleteMessageIds)).thenReturn(Arrays.asList(message));

        // when & then
        assertThrows(ChatRoomMissMatchException.class, () -> chatMessageService.deleteMessage(requestDto, 1L));
    }

    @Test
    public void 메시지_삭제_성공() {
        // given
        List<Long> deleteMessageIds = Arrays.asList(1L);
        ChatDeleteRequestDto requestDto = new ChatDeleteRequestDto(1L, deleteMessageIds);

        ChatMessage message = ChatMessage.of(chatRoom, buyerUser, "test message", MessageType.TEXT);
        when(chatMessageFinder.findAllById(deleteMessageIds)).thenReturn(Arrays.asList(message));

        // when
        chatMessageService.deleteMessage(requestDto, 1L);

        // then
        assertTrue(message.getMessageType() == MessageType.DELETED);
    }


}
