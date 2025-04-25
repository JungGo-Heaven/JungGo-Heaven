package com.example.junggoheaven.domain.chatMessage.entity;

import com.example.junggoheaven.domain.chatMessage.enums.MessageType;
import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import com.example.junggoheaven.domain.image.entity.ChatRoomImage;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.global.common.entity.IdGenerator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.SQLDelete;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_message")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE chat_message SET messageType = 'DELETED' WHERE id = ?")
@Filter(name = "deletedFilter", condition = "messageType <> 'DELETED'")
public class ChatMessage {
    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    @Nullable
    private String message;

    @Enumerated(EnumType.STRING)
    private MessageType messageType;

    @OneToOne(mappedBy = "chatMessage")
    @JsonIgnore
    private ChatRoomImage chatRoomImage;

    private LocalDateTime sendAt;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;


    private ChatMessage(Long id, ChatRoom chatRoom, User sender, String message, MessageType messageType, LocalDateTime sendAt, Boolean isRead) {
        this.id = id;
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.message = message;
        this.messageType = messageType;
        this.sendAt = sendAt;
        this.isRead = isRead;
    }

    private ChatMessage(Long id,ChatRoom chatRoom, User sender, MessageType messageType, LocalDateTime sendAt, Boolean isRead) {
        this.id = id;
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.messageType = messageType;
        this.sendAt = sendAt;
        this.isRead = isRead;
    }

    public static ChatMessage of(Long id, ChatRoom chatRoom, User sender, String message, MessageType messageType, LocalDateTime sendAt, Boolean isRead) {
        return new ChatMessage(id, chatRoom, sender, message, messageType, sendAt, isRead);
    }
    public static ChatMessage of(Long id, ChatRoom chatRoom, User sender, MessageType messageType, LocalDateTime sendAt, Boolean isRead) {
        return new ChatMessage(id, chatRoom, sender, messageType, sendAt, isRead);
    }


    public void isRead() {
        this.isRead = true;
    }

    public void isDeleted() {
        this.messageType = MessageType.DELETED;
    }

    public void updateChatRoomImage(ChatRoomImage chatRoomImage) {
        this.chatRoomImage = chatRoomImage;
    }
}
