package aiss.dailymotionminer.service;

import aiss.dailymotionminer.etl.Transformer;
import aiss.dailymotionminer.model.dailymotion.User;
import aiss.dailymotionminer.model.dailymotion.Video;
import aiss.dailymotionminer.model.dailymotion.VideoList;
import aiss.dailymotionminer.model.videominer.VMUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    //https://peertube.cpy.re/api/v1/video-channels/{channelHandle}/videos
    //http://localhost:8080/videominer/users

    @Autowired
    RestTemplate restTemplate;

    private User createUser(Video video){
        return new User(video.getUserId(), video.getUserName(), String.format("dailymotion.com/user/%s", video.getUserName()), video.getDescription());
    }

    public List<VMUser> getUser(String userId){
        String uri = String.format("", userId);
        VideoList videoList = restTemplate.getForObject(uri, VideoList.class);
        return videoList.getVideos().stream()
                .map(vid -> Transformer.createVMUser(createUser(vid)))
                .toList();
    }

    public List<VMUser> postUser(String channelHandle, String apiKey) {
        List<VMUser> res = new ArrayList<>();
        String getUri = String.format("", channelHandle);
        String postUri = "";
        VideoList videoList = restTemplate.getForObject(getUri, VideoList.class);
        List<VMUser> users = videoList.getVideos().stream()
                .map(vid -> Transformer.createVMUser(createUser(vid)))
                .toList();
        for (VMUser u : users) {
            HttpEntity<VMUser> request = new HttpEntity<>(u, AuxiliarFunction.getApiKeyHeader(apiKey));
            ResponseEntity<VMUser> response = restTemplate.exchange(postUri, HttpMethod.POST, request, VMUser.class);
            res.add(response.getBody());
        }
        return res;
    }

    public VMUser postUser(VMUser vmUser, String apiKey) {
        String uri = "";
        HttpEntity<VMUser> request = new HttpEntity<>(vmUser, AuxiliarFunction.getApiKeyHeader(apiKey));
        ResponseEntity<VMUser> response = restTemplate.exchange(uri, HttpMethod.POST, request, VMUser.class);
        return response.getBody();
    }
}
