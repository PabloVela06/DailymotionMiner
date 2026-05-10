package aiss.dailymotionminer.service;

import aiss.dailymotionminer.model.videominer.VMCaption;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CaptionServiceTest {

    @Autowired
    CaptionService service;

    String videoId = "xa8wdo0";
    String vmVideoId = "dailymotion-xa8wdo0";
    String apiKey = "guille6767";

    @Test
    void getCaption() {
        System.out.println(service.getCaption(videoId));
    }

    @Test
    void postCaption() {
        System.out.println(service.postCaption(videoId, vmVideoId, apiKey));
    }

    @Test
    void testPostCaption() {
        VMCaption caption = service.getCaption(videoId).getFirst();
        System.out.println(service.postCaption(caption, vmVideoId, apiKey));
    }
}