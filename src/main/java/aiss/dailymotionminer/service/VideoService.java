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

    //https://peertube.cpy.re/api/v1/video-channels/{channelHandle}/videos
    //http://localhost:8080/videominer/videos/channels/{channelId}/videos
    //http://localhost:8080/videominer/videos/{id}

    @Autowired
    RestTemplate restTemplate;

    public List<VMVideo> getVideo(String videoId, Integer maxVideo){
        String uri = String.format("", videoId, maxVideo);
        VideoList videoList = restTemplate.getForObject(uri, VideoList.class);
        return videoList.getVideos().stream()
                .map(vid -> Transformer.createVMVideo(vid))
                .toList();
    }

    public List<Video> getVideoPeerTube(String videoId, Integer maxVideo){
        String uri = String.format("", videoId, maxVideo);
        VideoList videoList = restTemplate.getForObject(uri, VideoList.class);
        return videoList.getVideos();
    }

    public List<VMVideo> postVideo(String videoId, String vmChannelId, Integer maxVideo, String apiKey){
        List<VMVideo> res = new ArrayList<>();
        String getUri = String.format("", videoId, maxVideo);
        String postUri = String.format("", vmChannelId);
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
        String uri = String.format("http://localhost:8080/videominer/videos/channels/%s/videos", vmChannelId);
        HttpEntity<VMVideo> request = new HttpEntity<>(vmVideo, AuxiliarFunction.getApiKeyHeader(apiKey));
        ResponseEntity<VMVideo> response = restTemplate.exchange(uri, HttpMethod.POST, request, VMVideo.class);
        return response.getBody();
    }

    public VMVideo updateVideo(VMVideo vmVideo, String apiKey) {
        String uri = String.format("http://localhost:8080/videominer/videos/%s", vmVideo.getId());
        HttpEntity<VMVideo> request = new HttpEntity<>(vmVideo, AuxiliarFunction.getApiKeyHeader(apiKey));
        ResponseEntity<VMVideo> response = restTemplate.exchange(uri, HttpMethod.PUT, request, VMVideo.class);
        return response.getBody();
    }
}
