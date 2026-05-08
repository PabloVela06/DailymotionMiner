package aiss.dailymotionminer.service;

import aiss.peertubeminer.etl.Transformer;
import aiss.peertubeminer.model.peertube.Channel;
import aiss.peertubeminer.model.videominer.VMChannel;
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
    //https://peertube.cpy.re/api/v1/video-channels/{channelHandle}
    //http://localhost:8080/videominer/channels/{id}

    public VMChannel getChannel(String channelHandle){ //channelHandle is a concatenation of name@host
        String uri = String.format("https://peertube.cpy.re/api/v1/video-channels/%s", channelHandle);
        Channel channel = restTemplate.getForObject(uri,Channel.class);
        VMChannel postChannel = Transformer.createVMChannel(channel);
        return postChannel;
    }

    public VMChannel postChannel(String channelHandle, String apiKey){
        String getUri = String.format("https://peertube.cpy.re/api/v1/video-channels/%s", channelHandle);
        String postUri = "http://localhost:8080/videominer/channels";
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
