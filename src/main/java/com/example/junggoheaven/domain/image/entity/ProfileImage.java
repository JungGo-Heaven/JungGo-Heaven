package com.example.junggoheaven.domain.image.entity;

import com.example.junggoheaven.global.common.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@NoArgsConstructor
@Table(name = "profile_images")
public class ProfileImage extends TimeStamp {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String profileImageUrl;

    @Column(nullable = false)
    private String keyName;

    public ProfileImage(String keyName, String profileImageUrl) {
        this.keyName = keyName;
        this.profileImageUrl = profileImageUrl;
    }
}
