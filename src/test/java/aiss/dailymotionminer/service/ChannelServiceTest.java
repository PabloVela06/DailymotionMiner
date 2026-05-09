package aiss.dailymotionminer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class ChannelServiceTest {

    @Autowired
    ChannelService service;

    @Test
    void getChannel() {
        String apiKey = "f0fe0f3a152189e46bfe";
        String secretKey = "5d12893fbe60b42108269e1857ca70418c850c9b";
        String channelId = "ivan.cano.gomez-201";

        System.out.println(service.getChannel(channelId, apiKey, secretKey));
    }
}