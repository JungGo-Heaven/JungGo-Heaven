package com.example.junggoheaven.domain.image.repository;

import com.example.junggoheaven.domain.image.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}
