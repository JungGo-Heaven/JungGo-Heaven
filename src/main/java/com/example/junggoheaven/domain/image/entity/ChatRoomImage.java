package com.example.junggoheaven.domain.image.entity;

import com.example.junggoheaven.global.common.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "chatroom_images")
public class ChatRoomImage extends TimeStamp {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String chatRoomImageUrl;

    @Column(nullable = false)
    private String keyName;

    public ChatRoomImage(String chatRoomImageUrl, String keyName) {
        this.chatRoomImageUrl = chatRoomImageUrl;
        this.keyName = keyName;
    }
}
