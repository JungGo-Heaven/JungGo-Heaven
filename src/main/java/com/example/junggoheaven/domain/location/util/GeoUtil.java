package com.example.junggoheaven.domain.location.util;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;

public class GeoUtil {

    private static final int EARTH_RADIUS = 6371000; // 지구 반지름(m)
    private static final GeometryFactory geometryFactory = new GeometryFactory();


    // 위도/경도를 이용한 두 지점 간 거리 계산 (하버사인 공식)
    public static Double calculateDistance(double lon1, double lat1, double lon2, double lat2) {

        double dLon = Math.toRadians(lon2 - lon1);
        double dLat = Math.toRadians(lat2 - lat1);

        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
                Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c;
    }

    // 위도, 경도를 이용해 Point 생성
    public static Point createPoint(double longitude, double latitude) {
        Coordinate coordinate = new Coordinate(longitude, latitude);
        Point point = geometryFactory.createPoint(coordinate);
        point.setSRID(4326); // WGS 84 좌표계
        return point;
    }
}
