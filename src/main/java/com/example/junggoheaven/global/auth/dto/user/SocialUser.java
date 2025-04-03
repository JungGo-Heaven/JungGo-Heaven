package com.example.junggoheaven.global.auth.dto.user;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.example.junggoheaven.domain.user.enums.UserRole;

import lombok.Builder;
import lombok.Getter;

@Getter
public class SocialUser implements OAuth2User {

	private Long id;
	private String email;
	private String name;
	private Collection<? extends GrantedAuthority> authorities;
	private Map<String, Object> attributes;

	public SocialUser(Long id, String email, String name, UserRole userRole) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.authorities = List.of(new SimpleGrantedAuthority(userRole.name()));
	}

	public SocialUser(List<SimpleGrantedAuthority> simpleGrantedAuthorities, Map<String, Object> attributes) {
		this.authorities = simpleGrantedAuthorities;
		this.attributes = attributes;
	}

	public Map<String, Object> getAttributes() {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("id", this.id);
		attributes.put("email", this.email);
		attributes.put("name", this.name);
		attributes.put("role", this.authorities);
		return attributes;
	}

	public UserRole getRole() {
		if (authorities != null && !authorities.isEmpty()) {
			String roleString = authorities.iterator().next().getAuthority();
			return UserRole.valueOf(roleString);
		}
		return UserRole.ROLE_GUEST;
	}

	@Override
	public String getName() {
		return this.email;
	}
}
