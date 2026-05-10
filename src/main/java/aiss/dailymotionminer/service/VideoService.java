package aiss.dailymotionminer.service;

import aiss.dailymotionminer.etl.Transformer;
import aiss.dailymotionminer.model.dailymotion.Video;
import aiss.dailymotionminer.model.dailymotion.VideoList;
import aiss.dailymotionminer.model.videominer.VMVideo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class VideoService {

    @Autowired
    RestTemplate restTemplate;

    //https://api.dailymotion.com/user/{channelId}/videos
    //http://localhost:8080/videominer/videos/channels/{channelId}/videos
    //http://localhost:8080/videominer/videos/{id}

    public List<VMVideo> getVideo(String channelId, Integer maxVideo){
        String uri = String.format("https://api.dailymotion.com/user/%s/videos?fields=id,title,description,created_time,tags,owner.id,owner.username,owner.avatar_240_url&limit=%d", channelId, maxVideo);
        VideoList videoList = restTemplate.getForObject(uri, VideoList.class);
        return videoList.getVideos().stream()
                .map(vid -> Transformer.createVMVideo(vid))
                .toList();
    }

    public List<Video> getVideoDailymotion(String channelId, Integer maxVideo){
        String uri = String.format("https://api.dailymotion.com/user/%s/videos?fields=id,title,description,created_time,tags,owner.id,owner.username,owner.avatar_240_url&limit=%d", channelId, maxVideo);
        VideoList videoList = restTemplate.getForObject(uri, VideoList.class);
        return videoList.getVideos();
    }

    public List<VMVideo> postVideo(String channelId, String vmChannelId, Integer maxVideo, String apiKey){
        List<VMVideo> res = new ArrayList<>();
        String getUri = String.format("https://api.dailymotion.com/user/%s/videos?fields=id,title,description,created_time,tags,owner.id,owner.username,owner.avatar_240_url&limit=%d", channelId, maxVideo);
        String postUri = AuxiliarFunction.getVideoMinerUri(String.format("/videos/channels/%s/videos", vmChannelId));
        VideoList videoList = restTemplate.getForObject(getUri, VideoList.class);
        List<VMVideo> videos = videoList.getVideos().stream()
                .map(vid -> Transformer.createVMVideo(vid))
                .toList();
        for (VMVideo vid: videos){
            HttpEntity<VMVideo> request = new HttpEntity<>(vid, AuxiliarFunction.getApiKeyHeader(apiKey));
            ResponseEntity<VMVideo> response = restTemplate.exchange(postUri, HttpMethod.POST, request, VMVideo.class);
            res.add(response.getBody());
        }
        return res;
    }

    public VMVideo postVideo(String vmChannelId, VMVideo vmVideo, String apiKey) {
        String uri = AuxiliarFunction.getVideoMinerUri(String.format("/videos/channels/%s/videos", vmChannelId));
        HttpEntity<VMVideo> request = new HttpEntity<>(vmVideo, AuxiliarFunction.getApiKeyHeader(apiKey));
        ResponseEntity<VMVideo> response = restTemplate.exchange(uri, HttpMethod.POST, request, VMVideo.class);
        return response.getBody();
    }

    public VMVideo updateVideo(VMVideo vmVideo, String apiKey) {
        String uri = AuxiliarFunction.getVideoMinerUri(String.format("/videos/%s", vmVideo.getId()));
        HttpEntity<VMVideo> request = new HttpEntity<>(vmVideo, AuxiliarFunction.getApiKeyHeader(apiKey));
        ResponseEntity<VMVideo> response = restTemplate.exchange(uri, HttpMethod.PUT, request, VMVideo.class);
        return response.getBody();
    }
}
