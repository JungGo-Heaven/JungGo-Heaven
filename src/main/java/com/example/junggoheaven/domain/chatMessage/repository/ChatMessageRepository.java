package com.example.junggoheaven.domain.chatMessage.repository;

import com.example.junggoheaven.domain.chatMessage.dto.response.LatestChatMessageDto;
import com.example.junggoheaven.domain.chatMessage.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatRoomIdOrderBySendAtAsc(Long chatRoomId);

//    ChatMessage findFirstByChatRoomIdOrderBySendAtDesc(Long chatRoomId);

    @Query("""
    SELECT new com.example.junggoheaven.domain.chatMessage.dto.response.LatestChatMessageDto(
        cm.chatRoom.id,
        cm
    )
    FROM ChatMessage cm
    WHERE cm.id IN (
        SELECT MAX(cm2.id)
        FROM ChatMessage cm2
        WHERE cm2.chatRoom.id IN :chatRoomIds
        GROUP BY cm2.chatRoom.id
    )
""")
    List<LatestChatMessageDto> findLatestMessagesForChatRooms(@Param("chatRoomIds") List<Long> chatRoomIds);


    @Query("""
        SELECT m 
        FROM ChatMessage m
        WHERE m.id IN :messageIds
          AND m.isRead = false
          AND m.sender.id <> :userId
          AND m.chatRoom.id = :chatRoomId
    """)
    List<ChatMessage> findUnreadMessages(@Param("messageIds") List<Long> messageIds,  @Param("chatRoomId") Long chatRoomId, @Param("userId") Long userId);
}
