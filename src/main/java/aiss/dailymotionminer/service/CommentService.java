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

    //https://peertube.cpy.re/api/v1/videos/{videoId}/comment-threads
    //http://localhost:8080/videominer/comments/videos/{videoId}/comments

    @Autowired
    RestTemplate restTemplate;

    public List<VMComment> getComment(String videoId, Integer maxComments){
        String uri = String.format("", videoId, maxComments);
        List<Comment> commentList = restTemplate.getForObject(uri, CommentList.class);
        return commentList.getComment().stream()
                .map(com -> Transformer.createVMComment(com))
                .toList();
    }

    public List<VMComment> postComment(String videoId, String vmVideoId, Integer maxComments, String apiKey){
        List<VMComment> res = new ArrayList<>();
        String getUri = String.format("", videoId, maxComments);
        String postUri = String.format("", vmVideoId);
        CommentList commentList = restTemplate.getForObject(getUri, CommentList.class);
        List<VMComment> comments = commentList.getComment().stream()
                .map(com -> Transformer.createVMComment(com))
                .toList();
        for (VMComment com: comments){
            HttpEntity<VMComment> request = new HttpEntity<>(com, AuxiliarFunction.getApiKeyHeader(apiKey));
            ResponseEntity<VMComment> response = restTemplate.exchange(postUri, HttpMethod.POST, request, VMComment.class);
            res.add(response.getBody());
        }
        return res;
    }
}
