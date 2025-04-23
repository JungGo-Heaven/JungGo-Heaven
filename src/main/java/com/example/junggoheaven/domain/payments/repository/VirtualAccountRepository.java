package com.example.junggoheaven.domain.payments.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.junggoheaven.domain.payments.entity.VirtualAccount;

public interface VirtualAccountRepository extends JpaRepository<VirtualAccount, Long> {
}
