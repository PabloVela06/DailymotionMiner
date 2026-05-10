package aiss.dailymotionminer.service;

import aiss.dailymotionminer.model.videominer.VMChannel;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ChannelServiceTest {

    @Autowired
    ChannelService service;

    String channelId = "x1mbetv"; //Id de canal "Marca", periódico deportivo español, que así podemos comprobar
                                  //sobre los limit y usamos siempre el mismo canal
    String apiKey = "guille6767";

    @Test
    void postChannel() { System.out.println(service.postChannel(channelId, apiKey)); }

    @Test
    void updateChannel() {
        VMChannel channel = service.getChannel(channelId);
        channel.setName(channel.getName() + "Updated");
        System.out.println(service.updateChannel(channel, apiKey));
    }

}