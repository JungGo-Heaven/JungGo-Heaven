package com.example.junggoheaven.domain.location.service;

import com.example.junggoheaven.domain.location.dto.GeoCoordinate;
import com.example.junggoheaven.domain.location.dto.LocationVerificationRequest;
import com.example.junggoheaven.domain.location.exception.LocationVerificationRequiredException;
import com.example.junggoheaven.domain.location.exception.NotInMyTownException;
import com.example.junggoheaven.domain.location.util.GeoUtil;
import com.example.junggoheaven.domain.user.entity.User;
import com.example.junggoheaven.domain.user.service.component.UserFinder;
import com.example.junggoheaven.domain.user.service.component.UserWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class LocationVerificationService {

    private final GeoService geoService;
    private final UserFinder userFinder;
    private final UserWriter userWriter;

    // 내 동네 인증하기
    public void verifyLocation(LocationVerificationRequest request) {
        User user = userFinder.findByUserId(request.getUserId());
        // 회원 가입 시 등록한 주소지를 '내 동네'로 인식
        GeoCoordinate registeredLocation = geoService.getGeoData(user.getAddress());
        double distance = GeoUtil.calculateDistance(
                request.getCurrentLongitude(),
                request.getCurrentLatitude(),
                registeredLocation.getLongitude(),
                registeredLocation.getLatitude()
        );

        // 반경 1km를 초과하면 '내 동네 인증' 불가
        if (distance > 1000.0) {
            throw new NotInMyTownException();
        }
    }

    // 인증 통과 후 lastVerifiedAt 업데이트
    public void updateLastVerified(User user) {
        user.updateLastVerifiedAt(LocalDateTime.now());
        userWriter.saveUser(user);
    }

    // 인증 만료 여부 확인 (30일 이상 지났다면 인증 필요)
    public boolean isLocationVerificationExpired(LocalDateTime lastVerifiedAt) {
        if (lastVerifiedAt == null) return true;  // 처음 인증 안한 경우
        return lastVerifiedAt.isBefore(LocalDateTime.now().minusDays(30));
    }

    // 최종 인증 여부 검사
    public boolean authenticateLocation(LocationVerificationRequest request) {
        try {
            verifyLocation(request);
            return true;  // 인증 통과
        } catch (LocationVerificationRequiredException e) {
            return false; // 인증 실패 시 false 반환
        }
    }
}
