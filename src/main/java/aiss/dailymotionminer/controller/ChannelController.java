package aiss.dailymotionminer.controller;

import aiss.dailymotionminer.model.videominer.VMChannel;
import aiss.dailymotionminer.repositories.ChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/dailymotionminer/channel")
public class ChannelController {

    private ChannelRepository channelRepository;

    @Autowired
    public ChannelController(ChannelRepository channelRepository) { this.channelRepository = channelRepository; }

    @GetMapping("/{channelId}")
    public VMChannel findByChannelId(@PathVariable String channelId,
                                     @RequestParam(defaultValue = "10") int maxVideos,
                                     @RequestParam(defaultValue = "2") int maxComments) {
        return channelRepository.findByChannelId(channelId, maxVideos, maxComments);
    }

    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping("/{channelId}")
    public VMChannel create(@PathVariable String channelId,
                            @RequestParam(defaultValue = "10") int maxVideos,
                            @RequestParam(defaultValue = "2") int maxComments,
                            @RequestHeader("X-API-KEY") String apiKey) {
        return channelRepository.create(channelId, maxVideos, maxComments, apiKey);
    }
}
