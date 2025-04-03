package com.example.junggoheaven.global.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.junggoheaven.global.auth.dto.user.RefreshCache;

@Repository
public interface RefreshRepository extends CrudRepository<RefreshCache, String> {
}
