package aiss.dailymotionminer.service;

import aiss.dailymotionminer.etl.Transformer;
import aiss.dailymotionminer.model.dailymotion.CaptionList;
import aiss.dailymotionminer.model.videominer.VMCaption;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CaptionService {
    //https://peertube.cpy.re/api/v1/videos/{videoId}/captions
    //http://localhost:8080/videominer/captions/videos/{videoId}/captions

    @Autowired
    RestTemplate restTemplate;

    public List<VMCaption> getCaption(String videoId){
        String uri = String.format("", videoId);
        CaptionList captionList = restTemplate.getForObject(uri, CaptionList.class);
        return captionList.getCaptions().stream()
                .map(cap -> Transformer.createVMCaption(cap))
                .toList();
    }

    public List<VMCaption> postCaption(String videoId, String vmVideoId, String apiKey){
        List<VMCaption> res = new ArrayList<>();
        String getUri = String.format("", videoId);
        String postUri = String.format("", vmVideoId);
        CaptionList captionList = restTemplate.getForObject(getUri, CaptionList.class);
        List<VMCaption> captions = captionList.getCaptions().stream()
                .map(cap -> Transformer.createVMCaption(cap))
                .toList();
        for (VMCaption cap: captions){
            HttpEntity<VMCaption> request = new HttpEntity<>(cap, AuxiliarFunction.getApiKeyHeader(apiKey));
            ResponseEntity<VMCaption> response = restTemplate.exchange(postUri, HttpMethod.POST, request, VMCaption.class);
            res.add(response.getBody());
        }
        return res;
    }

}
