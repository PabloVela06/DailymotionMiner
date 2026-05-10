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

    //https://api.dailymotion.com/user/{channelId}/videos
    //http://localhost:8080/videominer/users

    @Autowired
    RestTemplate restTemplate;

    private User createUser(Video video){
        return new User(video.getUserId(),
                video.getUserName(),
                String.format("https://www.dailymotion.com/%s", video.getUserName()),
                video.getUserPictureLink());
    }

    public List<VMUser> getUser(String channelId){
        String uri = String.format("https://api.dailymotion.com/user/%s/videos?fields=id,title,description,created_time,tags,owner.id,owner.username,owner.avatar_240_url", channelId);
        VideoList videoList = restTemplate.getForObject(uri, VideoList.class);
        return videoList.getVideos().stream()
                .map(vid -> Transformer.createVMUser(createUser(vid)))
                .toList();
    }

    public List<VMUser> postUser(String channelId, String apiKey) {
        List<VMUser> res = new ArrayList<>();
        String getUri = String.format("https://api.dailymotion.com/user/%s/videos?fields=id,title,description,created_time,tags,owner.id,owner.username,owner.avatar_240_url", channelId);
        String postUri = "http://localhost:8080/videominer/users";

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
        String uri = "http://localhost:8080/videominer/users";
        HttpEntity<VMUser> request = new HttpEntity<>(vmUser, AuxiliarFunction.getApiKeyHeader(apiKey));
        ResponseEntity<VMUser> response = restTemplate.exchange(uri, HttpMethod.POST, request, VMUser.class);
        return response.getBody();
    }
}
