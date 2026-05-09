package aiss.dailymotionminer.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChannelServiceTest {

    @Autowired
    ChannelService service;

    @Test
    void getChannel() {
        String channelId = "ivan.cano.gomez-201";
        System.out.println(service.getChannel(channelId, AuxiliarTestFunction.apiKey(), AuxiliarTestFunction.secretKey()));
    }
}