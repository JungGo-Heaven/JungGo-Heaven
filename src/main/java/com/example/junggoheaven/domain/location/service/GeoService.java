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
            String encodedAddr = URLEncoder.encode(address, "UTF-8");
            URL url = new URL(MAP_URL + encodedAddr);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setRequestProperty("Authorization", "KakaoAK " + key);


            try (BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8))) {
                String response = br.lines().collect(Collectors.joining());
                JSONObject obj = (JSONObject) new JSONParser().parse(response);
                JSONArray docs = (JSONArray) obj.get("documents");

                if (docs.isEmpty()) throw new AddressResponseEmptyException();

                JSONObject first = (JSONObject) docs.get(0);
                return new GeoCoordinate(Double.parseDouble((String) first.get("x")),Double.parseDouble((String) first.get("y")));

            }
        } catch (Exception e) {
            throw new CoordinateConversionFailedException();
        }
    }
}
