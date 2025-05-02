package com.example.junggoheaven.domain.image;

import com.example.junggoheaven.domain.chatMessage.dto.response.ChatMessageResponseDto;
import com.example.junggoheaven.domain.chatMessage.entity.ChatMessage;
import com.example.junggoheaven.domain.chatMessage.enums.MessageType;
import com.example.junggoheaven.domain.chatMessage.redis.dto.RedisChatMessageDto;
import com.example.junggoheaven.domain.chatMessage.service.component.ChatMessageWriter;
import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import com.example.junggoheaven.domain.chatRoom.service.component.ChatRoomFinder;
import com.example.junggoheaven.domain.image.repository.ChatroomImageRepository;
import com.example.junggoheaven.domain.image.service.ChatRoomImageService;
import com.example.junggoheaven.domain.image.service.S3.S3StorageService;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.auth.dto.user.AuthUser;
import com.example.junggoheaven.global.redis.RedisService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.assertEquals;



@ExtendWith(MockitoExtension.class)
public class ChatRoomImageServiceTest {

    @InjectMocks
    private ChatRoomImageService chatRoomImageService;

    @Mock
    private ChatroomImageRepository chatroomImageRepository;

    @Mock
    private S3StorageService s3StorageService;

    @Mock
    private ChatRoomFinder chatRoomFinder;

    @Mock
    private ChatMessageWriter chatMessageWriter;

    @Mock
    private RedisService redisService;

    @Mock
    private UserFinder userFinder;

    @Test
    void chatRoomImage를_업로드하면_Redis에도_저장되고_응답DTO를_반환하는가() {
        //given
        Long chatRoomId = 1L;
        Long userId = 1L;
        ChatRoom chatRoom = mock(ChatRoom.class);
        when(chatRoom.getId()).thenReturn(chatRoomId);
        when(chatRoomFinder.findByChatRoomId(chatRoomId)).thenReturn(chatRoom);

        AuthUser authUser = mock(AuthUser.class);
        User user = mock(User.class);
        when(authUser.getId()).thenReturn(userId);
        when(userFinder.findByUserId(userId)).thenReturn(user);

        MultipartFile file1 = mock(MultipartFile.class);
        when(file1.getOriginalFilename()).thenReturn("image1.jpeg");

        MultipartFile file2 = mock(MultipartFile.class);
        when(file2.getOriginalFilename()).thenReturn("image2.jpeg");

        List<String> urls = List.of("http://cloudfront.net/image1.jpeg", "http://cloudfront.net/image2.jpeg");
        List<MultipartFile> files = List.of(file1,file2);

        //when
        List<ChatMessageResponseDto> result = chatRoomImageService.saveChatRoomImages(urls, files, authUser, chatRoomId);

        //then
        verify(chatRoomFinder).findByChatRoomId(chatRoomId);
        verify(userFinder).findByUserId(userId);
        verify(redisService, times(2)).saveChatMessageToZSet(any(RedisChatMessageDto.class));

        assertEquals(2, result.size());
        assertEquals("http://cloudfront.net/image1.jpeg", result.get(0).getImageUrl());
        assertEquals("http://cloudfront.net/image2.jpeg", result.get(1).getImageUrl());
    }

    @Test
    void message와_image를_한번에_같이_저장하기() {
        //given
        ChatRoom chatRoom = mock(ChatRoom.class);
        User user = mock(User.class);

        RedisChatMessageDto dto1 = new RedisChatMessageDto(
                1L, 1L, 1L, null, "http://cloudfront.net/image1.jpeg", "image1.jpeg", MessageType.IMAGE
        );
        RedisChatMessageDto dto2 = new RedisChatMessageDto(
                1L, 1L, 1L, null, "http://cloudfront.net/image2.jpeg", "image2.jpeg", MessageType.IMAGE
        );
        List<RedisChatMessageDto> batchImage = List.of(dto1, dto2);

        when(chatRoomFinder.findByChatRoomId(anyLong())).thenReturn(chatRoom);
        when(userFinder.findByUserId(anyLong())).thenReturn(user);

        ChatMessage message1 = mock(ChatMessage.class);
        ChatMessage message2 = mock(ChatMessage.class);
        when(chatMessageWriter.saveAll(anyList())).thenReturn(List.of(message1, message2));

        //when
        chatRoomImageService.saveAll(batchImage);

        //then
        verify(chatRoomFinder, times(2)).findByChatRoomId(1L);
        verify(userFinder, times(2)).findByUserId(1L);
        verify(chatMessageWriter).saveAll(anyList());
        verify(chatroomImageRepository).saveAll(anyList());
    }
}
