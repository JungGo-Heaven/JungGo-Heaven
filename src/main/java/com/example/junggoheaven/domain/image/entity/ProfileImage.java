package com.example.junggoheaven.domain.image.entity;

import com.example.junggoheaven.global.common.entity.TimeStamp;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.IDENTITY;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "profile_images")
public class ProfileImage extends TimeStamp {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String profileImageUrl;

    @Column(nullable = false)
    private String keyName;

    @Builder
    private ProfileImage(String keyName, String profileImageUrl) {
        this.keyName = keyName;
        this.profileImageUrl = profileImageUrl;
    }

    public static ProfileImage of(String profileImageUrl, String keyName) {
        return new ProfileImage(profileImageUrl, keyName);
    }
}
