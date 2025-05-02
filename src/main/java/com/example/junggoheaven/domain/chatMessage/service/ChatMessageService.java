package com.example.junggoheaven.domain.chatMessage.service;

import com.example.junggoheaven.domain.chatMessage.dto.request.ChatDeleteRequestDto;
import com.example.junggoheaven.domain.chatMessage.dto.request.ChatMessageRequestDto;
import com.example.junggoheaven.domain.chatMessage.dto.request.ChatReadRequestDto;
import com.example.junggoheaven.domain.chatMessage.dto.response.ChatMessageResponseDto;
import com.example.junggoheaven.domain.chatMessage.entity.ChatMessage;
import com.example.junggoheaven.domain.chatMessage.enums.MessageType;
import com.example.junggoheaven.domain.chatMessage.exception.ChatRoomMissMatchException;
import com.example.junggoheaven.domain.chatMessage.exception.NoPermissionToChatMessage;
import com.example.junggoheaven.domain.chatMessage.redis.dto.RedisChatMessageDto;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageChecker;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageFinder;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageWriter;
import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import com.example.junggoheaven.domain.chatRoom.service.component.ChatRoomFinder;
import com.example.junggoheaven.domain.chatRoom.service.component.ChatRoomWriter;
import com.example.junggoheaven.domain.product.entity.Product;
import com.example.junggoheaven.domain.product.service.component.ProductFinder;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.common.entity.IdGenerator;
import com.example.junggoheaven.global.redis.RedisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageFinder chatMessageFinder;
    private final ChatMessageWriter chatMessageWriter;

    private  final ProductFinder productFinder;

    private final ChatRoomFinder chatRoomFinder;
    private final ChatRoomWriter chatRoomWriter;

    private final UserFinder userFinder;

    private final RedisService redisService;

    //redis에 저장하는 로직
    @Transactional
    public ChatMessageResponseDto createMessage(ChatMessageRequestDto requestDto, Long userId) {
        RedisChatMessageDto redisChatMessageDto;
        if(requestDto.getChatRoomId() != null) {

            redisChatMessageDto = new RedisChatMessageDto(
                    requestDto.getChatRoomId(),
                    userId,
                    IdGenerator.generateId(),
                    requestDto.getMessage(),
                    null,
                    null,
                    MessageType.TEXT
            );

            chatNotification(chatRoomFinder.findByChatRoomId(requestDto.getChatRoomId()), userId, requestDto.getMessage());
        } else {
            User user = userFinder.findByUserId(userId);
            Product product = productFinder.findProductById(requestDto.getProductId());
            ChatRoom savedChatRoom = chatRoomWriter.save(ChatRoom.of(product, user));

            redisChatMessageDto = new RedisChatMessageDto(
                    savedChatRoom.getId(),
                    userId,
                    IdGenerator.generateId(),
                    requestDto.getMessage(),
                    null,
                    null,
                    MessageType.TEXT
            );
            chatNotification(savedChatRoom, userId, requestDto.getMessage());
        }
        redisService.saveChatMessageToZSet(redisChatMessageDto);

        return new ChatMessageResponseDto(
                redisChatMessageDto.getChatRoomId(),
                redisChatMessageDto.getChatMessageId(),
                redisChatMessageDto.getSenderId(),
                redisChatMessageDto.getMessage(),
                null,
                redisChatMessageDto.getSendAt(),
                redisChatMessageDto.getMessageType(),
                false
        );
    }

    //데이터베이스에 저장하는 로직
    @Deprecated
    @Transactional
    public ChatMessageResponseDto createMessageDB(ChatMessageRequestDto requestDto, Long userId) {
        User user = userFinder.findByUserId(userId);
        ChatMessage chatMessage;

        if(requestDto.getChatRoomId() != null) {
            ChatRoom chatRoom = chatRoomFinder.findByChatRoomId(requestDto.getChatRoomId());
            chatMessage = ChatMessage.of(
                    IdGenerator.generateId(),
                    chatRoom,
                    user,
                    requestDto.getMessage(),
                    MessageType.TEXT,
                    LocalDateTime.now(),
                    false
            );

            chatNotification(chatRoom, userId, requestDto.getMessage());

        } else {
            Product product = productFinder.findProductById(requestDto.getProductId());
            ChatRoom newChatRoom = ChatRoom.of(product, user);
            ChatRoom savedChatRoom = chatRoomWriter.save(newChatRoom);
            chatMessage = ChatMessage.of(
                    IdGenerator.generateId(),
                    savedChatRoom,
                    user,
                    requestDto.getMessage(),
                    MessageType.TEXT,
                    LocalDateTime.now(),
                    false
            );

            chatNotification(savedChatRoom, userId, requestDto.getMessage());
        }
        chatMessageWriter.save(chatMessage);

        return new ChatMessageResponseDto(
                chatMessage.getChatRoom().getId(),
                chatMessage.getId(),
                chatMessage.getSender().getId(),
                chatMessage.getMessage(),
                null,
                chatMessage.getSendAt(),
                chatMessage.getMessageType(),
                false
        );
    }

    @Transactional
    public void isRead(ChatReadRequestDto requestDto, Long userId) {
        ChatRoom chatRoom = chatRoomFinder.findByChatRoomId(requestDto.getChatRoomId());
        User user = userFinder.findByUserId(userId);
        // 아직 읽지 않았고, 내가 보낸 게 아닌 메시지만 가져옴
        List<ChatMessage> unreadMessages = chatMessageFinder
                .findUnreadMessages(requestDto.getReadMessageIds(), chatRoom.getId(), user.getId());

        for (ChatMessage message : unreadMessages) {
            message.isRead();
        }
    }

    @Transactional
    public void deleteMessage(ChatDeleteRequestDto requestDto, Long userId) {

        List<ChatMessage> messages = chatMessageFinder.findAllById(requestDto.getDeleteMessageIds());

        for(ChatMessage message : messages) {
            if(!message.getSender().getId().equals(userId)) {
                throw new NoPermissionToChatMessage();
            }
            if (!message.getChatRoom().getId().equals(requestDto.getChatRoomId())) {
                throw new ChatRoomMissMatchException();
            }
            message.isDeleted();
        }
    }
    @Transactional
    public void saveAll(List<RedisChatMessageDto> batch) {
        List<ChatMessage> messages = batch.stream()
                .map(dto -> ChatMessage.of(
                        dto.getChatMessageId(),
                        chatRoomFinder.findByChatRoomId(dto.getChatRoomId()),
                        userFinder.findByUserId(dto.getSenderId()),
                        dto.getMessage(),
                        dto.getMessageType(),
                        dto.getSendAt(),
                        dto.getIsRead()
                ))
                .toList();
        chatMessageWriter.saveAll(messages);
    }


    private void chatNotification(ChatRoom chatRoom, Long userId, String message) {
        Long sellerId = chatRoom.getProduct().getUser().getId();
        Long buyerId = chatRoom.getBuyer().getId();

        Long receiverId;
        if (buyerId.equals(userId)) {
            receiverId = sellerId;
        } else {
            receiverId = buyerId;
        }

        // todo: 알림 이벤트 추가 알림을 받을 사람 = receiverId 알림 문구는  "sender.getname : message"

    }
}
