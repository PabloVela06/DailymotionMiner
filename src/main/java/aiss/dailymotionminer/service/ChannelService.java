package aiss.dailymotionminer.service;

import aiss.dailymotionminer.etl.Transformer;
import aiss.dailymotionminer.model.dailymotion.Channel;
import aiss.dailymotionminer.model.videominer.VMChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class ChannelService {

    @Autowired
    RestTemplate restTemplate;

    //http://localhost:8080/videominer/channels
    //https://partner.api.dailymotion.com/rest/user/{channelId}?fields=created_time,description,id,username
    //http://localhost:8080/videominer/channels/{id}

    public VMChannel getChannel(String channelId, String apiKey, String secretkey){
        String uri = AuxiliarFunction.getDailymotionUri(String.format("/rest/user/%s?fields=created_time,description,id,username", channelId));

        HttpEntity<Channel> request = new HttpEntity<>(null, AuxiliarFunction.getTokenHeader(AuxiliarFunction.getToken(apiKey, secretkey)));
        ResponseEntity<Channel> response = restTemplate.exchange(uri, HttpMethod.GET, request, Channel.class);

        return Transformer.createVMChannel(response.getBody());
    }

    public VMChannel postChannel(String channelHandle, String apiKey){
        String getUri = String.format("", channelHandle);
        String postUri = "";
        Channel channel = restTemplate.getForObject(getUri,Channel.class);
        VMChannel postChannel = Transformer.createVMChannel(channel);

        HttpEntity<VMChannel> request = new HttpEntity<>(postChannel, AuxiliarFunction.getApiKeyHeader(apiKey));
        ResponseEntity<VMChannel> response = restTemplate.exchange(postUri, HttpMethod.POST, request, VMChannel.class);
        return response.getBody();
    }

    public VMChannel updateChannel(VMChannel vmChannel, String apiKey) {
        String uri = String.format("http://localhost:8080/videominer/channels/%s", vmChannel.getId());
        HttpEntity<VMChannel> request = new HttpEntity<>(vmChannel, AuxiliarFunction.getApiKeyHeader(apiKey));
        ResponseEntity<VMChannel> response = restTemplate.exchange(uri, HttpMethod.PUT, request, VMChannel.class);
        return response.getBody();
    }
}
