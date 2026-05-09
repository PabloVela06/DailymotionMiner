package aiss.dailymotionminer.service;

import aiss.dailymotionminer.model.dailymotion.TokenInfo;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class AuxiliarFunction {

    public static HttpHeaders getApiKeyHeader(String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        return headers;
    }

    public static HttpHeaders getTokenHeader(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", String.format("Bearer %s", token));
        return headers;
    }

    public static String getVideoMinerUri(String path) {
        return String.format("http://localhost:8080/videominer%s", path);
    }

    public static String getDailymotionUri(String path) {
        return String.format("https://partner.api.dailymotion.com%s", path);
    }

    public static String getToken(String apiKey, String secretKey) {
        RestTemplate restTemplate = new RestTemplate();
        String uri = getDailymotionUri("/oauth/v1/token");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "client_credentials");
        body.add("scope", "userinfo");
        body.add("client_id", apiKey);
        body.add("client_secret", secretKey);

        HttpHeaders headers = new HttpHeaders();
        headers.set("Accept", "application/json");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
        ResponseEntity<TokenInfo> response = restTemplate.exchange(uri, HttpMethod.POST, request, TokenInfo.class);
        return response.getBody().getToken();
    }
}
