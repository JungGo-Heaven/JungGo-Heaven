package com.example.junggoheaven.global.websocket;

import com.example.junggoheaven.global.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.util.StringUtils;

import java.security.Principal;

@RequiredArgsConstructor
public class StompJwtChannelInterceptor implements ChannelInterceptor {
    private final JwtUtil jwtUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String tokenValue = accessor.getFirstNativeHeader("Authorization");

            if (!StringUtils.hasText(tokenValue)) {
                throw new IllegalArgumentException("Authorization 헤더가 없습니다.");
            }

            String token = jwtUtil.substringToken(tokenValue);

            if (jwtUtil.isTokenExpired(token)) {
                throw new IllegalArgumentException("만료된 토큰입니다.");
            }

            Claims claims = jwtUtil.extractClaims(token);
            Long userId = Long.parseLong(claims.getSubject());

            // principal에 유저 정보 주입
            Principal principal = new StompPrincipal(userId.toString());
            accessor.setUser(principal);
        }
        return message;
    }
}
