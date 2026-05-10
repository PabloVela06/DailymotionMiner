package aiss.dailymotionminer.service;

import aiss.dailymotionminer.model.dailymotion.Video;
import aiss.dailymotionminer.model.videominer.VMVideo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
class VideoServiceTest {

    @Autowired
    VideoService service;

    String channelId = "x1mbetv";
    String apiKey = "guille6767";
    String vmChannelId = "dailymotion-x1mbetv";

    @Test
    void getVideo() {
        System.out.println(service.getVideo(channelId, 3));
    }

    @Test
    void getVideoDailymotion() {
        List<Video> videos = service.getVideoDailymotion(channelId, 2);
        System.out.println(videos);
    }

    @Test
    void postVideo() {
        System.out.println(service.postVideo(channelId, vmChannelId, 2, apiKey));
    }

    @Test
    void testPostVideo() {
        VMVideo video = service.getVideo(channelId, 1).get(0);
        System.out.println(service.postVideo(vmChannelId, video, apiKey));
    }

    @Test
    void updateVideo() {
        VMVideo video = service.getVideo(channelId, 1).get(0);
        video.setName(video.getName() + "(Updated)");
        System.out.println(service.updateVideo(video, apiKey));
    }
}