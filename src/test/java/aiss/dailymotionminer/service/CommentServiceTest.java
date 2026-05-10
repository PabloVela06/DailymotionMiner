package aiss.dailymotionminer.service;

import aiss.dailymotionminer.model.dailymotion.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CommentServiceTest {

    @Autowired
    CommentService commentService;

    @Autowired
    VideoService videoService;


    String channelId = "x1mbetv";
    String apiKey = "guille6767";

    @Test
    void getComments() {
        Video video = videoService.getVideoDailymotion(channelId, 1).getFirst();
        System.out.println(video.getComments());
    }

    @Test
    void postComments() {
        Video video = videoService.getVideoDailymotion(channelId, 1).get(0);
        String vmVideoId = "dailymotion-" + video.getId();
        System.out.println(commentService.postComments(video, vmVideoId, 5, apiKey));
    }
}