package aiss.dailymotionminer.service;

import org.springframework.http.HttpHeaders;

public class AuxiliarFunction {

    public static HttpHeaders getApiKeyHeader(String apiKey){
        HttpHeaders headers = new HttpHeaders();
        headers.set("X-API-KEY", apiKey);
        return headers;
    }

    public static String getVideoMinerUri(String path) {
        return String.format("http://localhost:8080/videominer%s", path);
    }
}
