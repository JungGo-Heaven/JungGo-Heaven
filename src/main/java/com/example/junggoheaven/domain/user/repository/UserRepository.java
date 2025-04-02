package com.example.junggoheaven.domain.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.junggoheaven.domain.user.entity.User;

public interface UserRepository extends JpaRepository<User,Long> {
}
