package aiss.dailymotionminer.service;

import org.springframework.http.HttpHeaders;

public class AuxiliarFunction {

    public static HttpHeaders getApiKeyHeader(String apiKey) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        return headers;
    }

    public static String getDailymotionUri(String path) {
        return String.format("https://partner.api.dailymotion.com%s", path);
    }
}
