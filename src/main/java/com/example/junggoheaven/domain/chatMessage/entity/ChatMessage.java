package com.example.junggoheaven.domain.chatMessage.entity;

import com.example.junggoheaven.domain.chatMessage.enums.MessageType;
import com.example.junggoheaven.domain.chatRoom.entity.ChatRoom;
import com.example.junggoheaven.domain.user.entity.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "chat_message")
@Getter
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_room_id", nullable = false)
    private ChatRoom chatRoom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    private String message;

    private MessageType messageType;

//    @OneToMany(fetch = FetchType.LAZY, mappedBy = "chatMessage")
//    List<ChatImage> chatImages = new ArrayList<>();

    private LocalDateTime sendAt;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;


    public ChatMessage(ChatRoom chatRoom, User sender, String message, MessageType messageType) {
        this.chatRoom = chatRoom;
        this.sender = sender;
        this.message = message;
        this.messageType = messageType;
        this.sendAt = LocalDateTime.now();
        this.isRead = false;
    }

    public void isRead() {
        this.isRead = true;
    }

    public void isDeleted() {
        this.messageType = MessageType.DELETED;
    }
}
