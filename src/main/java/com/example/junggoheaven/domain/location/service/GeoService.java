package com.example.junggoheaven.domain.location.service;

import com.example.junggoheaven.domain.location.dto.GeoCoordinate;
import com.example.junggoheaven.domain.location.exception.AddressResponseEmptyException;
import com.example.junggoheaven.domain.location.exception.CoordinateConversionFailedException;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GeoService {

    @Value("${api-key.kakao}")
    private String key;

    private final static String MAP_URL = "http://dapi.kakao.com/v2/local/search/address.json?query=";

    //GeoCoding(주소를 위도/경도로 변환)
    public GeoCoordinate getGeoData(String address) {
        try {
            // 주소에 공백과 한글이 있어서 깨짐 방지용 인코딩 작업
            String encodedAddr = URLEncoder.encode(address, StandardCharsets.UTF_8);
            URL url = new URL(MAP_URL + encodedAddr);

            // HttpURLConnection 객체 생성 및 GET 방식 설정
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // Kakao API 인증을 위해 요청 헤더에 Authorization 추가
            connection.setRequestProperty("Authorization", "KakaoAK " + key);

            // 응답 데이터를 읽어오기 위한 BufferedReader 생성
            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                // 응답을 한 줄로 이어붙여 JSON 문자열로 변환
                String response = br.lines().collect(Collectors.joining());
                // JSON 문자열을 JSONObject로 파싱
                JSONObject obj = (JSONObject) new JSONParser().parse(response);
                // 'documents' 키에 해당하는 JSON 배열 추출 (여기에 주소 결과들이 담겨 있음)
                JSONArray docs = (JSONArray) obj.get("documents");

                if (docs.isEmpty()) throw new AddressResponseEmptyException();

                // 첫 번째 검색 결과를 기준으로 경도(x), 위도(y) 값 추출
                JSONObject first = (JSONObject) docs.get(0);
                // 추출한 좌표를 Double 타입으로 변환하여 객체 생성
                return new GeoCoordinate(Double.parseDouble((String) first.get("x")),Double.parseDouble((String) first.get("y")));
            }
        } catch (Exception e) {
            throw new CoordinateConversionFailedException();
        }
    }
}
