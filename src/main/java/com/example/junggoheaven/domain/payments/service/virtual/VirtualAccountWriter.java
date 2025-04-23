package com.example.junggoheaven.domain.payments.service.virtual;

import org.springframework.stereotype.Service;

import com.example.junggoheaven.domain.payments.entity.VirtualAccount;
import com.example.junggoheaven.domain.payments.repository.VirtualAccountRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VirtualAccountWriter {

	private final VirtualAccountRepository virtualAccountRepository;

	public VirtualAccount save(VirtualAccount virtualAccount) {
		return virtualAccountRepository.save(virtualAccount);
	}
}
