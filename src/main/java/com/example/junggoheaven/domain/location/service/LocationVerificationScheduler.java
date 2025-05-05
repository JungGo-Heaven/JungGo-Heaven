package com.example.junggoheaven.domain.location.service;

import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.global.message.event.finder.LocationVerificationEvent;
import com.example.junggoheaven.global.message.publisher.EventPublisher;

import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationVerificationScheduler {

    private final UserFinder userFinder;
    private final LocationVerificationService locationVerificationService;
    private final EventPublisher eventPublisher;

    // 매일 자정에 '내 동네 인증' 만료되는 사람들 체크해서 리마인딩 해주기
    @Scheduled(cron = "0 0 0 1/1 * ?" )
    public void verifyUsersLocation() {
        List<User> allUsers = userFinder.findAllUsers();

        for (User user : allUsers) {
            if (locationVerificationService.isLocationVerificationExpired(user.getLastVerifiedAt())) {
                sendLocationVerificationReminder(user);
            }
        }
    }
    // 인증 만료된 사용자에게 알림 보내기 (예: 이메일, SMS)
    private void sendLocationVerificationReminder(User user) {

        eventPublisher.publishEvent(
            new LocationVerificationEvent(
                this,
                user.getId(),
                "User " + user.getId() + " needs location verification."
            )
        );

        System.out.println("User " + user.getId() + " needs location verification.");
    }
}
