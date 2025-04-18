package com.example.junggoheaven.domain.chatRoom.service;

import com.example.junggoheaven.domain.chatMessage.entity.ChatMessage;
import com.example.junggoheaven.domain.chatMessage.enums.MessageType;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageFinder;
import com.example.junggoheaven.domain.chatRoom.dto.response.ChatRoomEnterResponseDto;
import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import com.example.junggoheaven.domain.chatRoom.exception.ChatRoomForbidden;
import com.example.junggoheaven.domain.chatRoom.service.component.ChatRoomFinder;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.common.response.ResponseDto;
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
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatRoomServiceTest {
    @InjectMocks
    private ChatRoomService chatRoomService;

    @Mock
    private ChatRoomFinder chatRoomFinder;

    @Mock
    private ChatMessageFinder chatMessageFinder;

    User buyerUser;
    User sellerUser;
    Product product;
    ChatRoom chatRoom;
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
    public void 존재하는_채팅방_입장() {
        // given
        List<ChatMessage> messages = Arrays.asList(ChatMessage.of(chatRoom, buyerUser, "testtest", MessageType.TEXT));

        when(chatRoomFinder.findByProductIdAndBuyerIdOpt(anyLong(), anyLong())).thenReturn(Optional.of(chatRoom));
        when(chatMessageFinder.findByChatRoomIdOrderBySendAtAsc(1L)).thenReturn(messages);

        AuthUser authUser = new AuthUser(1L, "buyer@n.com", UserRole.ROLE_USER, "Buyer");

        // when
        ResponseDto<ChatRoomEnterResponseDto> response = chatRoomService.enterChatRoom(1L, authUser);

        // then
        assertNotNull(response);
        assertEquals(1, response.getData().getMessages().size());
        assertEquals("testtest", response.getData().getMessages().get(0).getMessage());
    }

    @Test
    public void 존재하지_않는_채팅방_입장() {
        // given
        when(chatRoomFinder.findByProductIdAndBuyerIdOpt(anyLong(), anyLong())).thenReturn(Optional.empty());

        AuthUser authUser = new AuthUser(1L, "buyer@n.com", UserRole.ROLE_USER, "Buyer");

        // when
        ResponseDto<ChatRoomEnterResponseDto> response = chatRoomService.enterChatRoom(1L, authUser);

        // then
        assertNotNull(response);
        assertTrue(response.getData().getMessages().isEmpty()); //저장되어있지 않으면 빈 리스트 출력
    }

    @Test
    public void 구매자_채팅방_나감() {
        // given
        when(chatRoomFinder.findByChatRoomId(anyLong())).thenReturn(chatRoom);

        AuthUser authUser = new AuthUser(1L, "buyer@n.com", UserRole.ROLE_USER, "Buyer");

        // when
        chatRoomService.exitChatRoom(1L, authUser);

        // then
        assertEquals(authUser.getId(), chatRoom.getBuyerExited());
    }

    @Test
    public void 판매자_채팅방_나감() {
        // given
        when(chatRoomFinder.findByChatRoomId(anyLong())).thenReturn(chatRoom);

        AuthUser authUser = new AuthUser(2L, "seller@n.com", UserRole.ROLE_USER, "Seller");

        // when
        chatRoomService.exitChatRoom(1L, authUser);

        // then
        assertEquals(authUser.getId(), chatRoom.getSellerExited());
    }

    @Test
    public void 채팅방_참여자가_아닌_사용자가_채팅방_접근() {
        // given
        when(chatRoomFinder.findByChatRoomId(anyLong())).thenReturn(chatRoom);

        AuthUser authUser = new AuthUser(3L, "random@n.com", UserRole.ROLE_USER, "Random User");

        // when & then
        assertThrows(ChatRoomForbidden.class, () -> chatRoomService.exitChatRoom(1L, authUser));
    }

}
