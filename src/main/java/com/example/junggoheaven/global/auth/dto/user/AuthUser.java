package com.example.junggoheaven.global.auth.dto.user;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.example.junggoheaven.domain.user.enums.UserRole;
import com.example.junggoheaven.domain.user.exception.InvalidUserRoleException;

import lombok.Getter;

@Getter
public class AuthUser {

	private final Long id;
	private final String email;
	private final String name;
	private final Collection<? extends GrantedAuthority> authorities;

	public AuthUser(Long id, String email, UserRole userRole, String name) {
		this.id = id;
		this.email = email;
		this.authorities = List.of(new SimpleGrantedAuthority(userRole.name()));
		this.name = name;
	}

	public UserRole getRole(){
		return authorities.stream().map(GrantedAuthority::getAuthority)
			.map(UserRole::of).findFirst().orElseThrow(InvalidUserRoleException::new);
	}
}
