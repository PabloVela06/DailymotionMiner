package aiss.dailymotionminer.service;

import aiss.dailymotionminer.etl.Transformer;
import aiss.dailymotionminer.model.dailymotion.Video;
import aiss.dailymotionminer.model.videominer.VMComment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentService {

    // http://localhost:8080/videominer/comments/videos/{videoId}/comments

    @Autowired
    RestTemplate restTemplate;

    public List<VMComment> getComments(Video video, Integer maxComments) {
        return video.getComments().stream()
                .limit(maxComments)
                .map(com -> Transformer.createVMComment(com, video.getReleaseTime()))
                .toList();
    }

    /*String uri = String.format("https://peertube.cpy.re/api/v1/videos/%s/comment-threads?count=%d", videoId, maxComments);
    CommentList commentList = restTemplate.getForObject(uri, CommentList.class);
        return commentList.getComment().stream()
                .map(com -> Transformer.createVMComment(com))
            .toList();*/

    public List<VMComment> postComments(Video video, String vmVideoId, Integer maxComments, String apiKey) {
        List<VMComment> res = new ArrayList<>();
        String postUri = String.format("http://localhost:8080/videominer/comments/videos/%s/comments", vmVideoId);
        List<VMComment> comments = video.getComments().stream()
                .limit(maxComments)
                .map(com -> Transformer.createVMComment(com, video.getReleaseTime()))
                .toList();

        for (VMComment com: comments) {
            HttpEntity<VMComment> request = new HttpEntity<>(com, AuxiliarFunction.getApiKeyHeader(apiKey));
            ResponseEntity<VMComment> response = restTemplate.exchange(postUri, HttpMethod.POST, request, VMComment.class);
            res.add(response.getBody());
        }
        return res;
    }
}
