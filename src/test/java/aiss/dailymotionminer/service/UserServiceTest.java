package aiss.dailymotionminer.service;

import aiss.dailymotionminer.model.videominer.VMUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class UserServiceTest {

    @Autowired
    UserService service;

    String channelId = "x1mbetv";
    String apiKey = "guille6767";

    @Test
    void getUser() {
        System.out.println(service.getUser(channelId));
    }

    @Test
    void postUser() { System.out.println(service.postUser(channelId, apiKey)); }

    @Test
    void testPostUser() {
        VMUser user = service.getUser(channelId).get(0);
        System.out.println(service.postUser(user, apiKey));
    }
}