package aiss.dailymotionminer.controller;

import aiss.dailymotionminer.model.dailymotion.Caption;
import aiss.dailymotionminer.model.dailymotion.Video;
import aiss.dailymotionminer.model.videominer.VMChannel;
import aiss.dailymotionminer.repositories.ChannelRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Channel",description = "Dailymotion miner API")
@RestController
@RequestMapping("/dailymotionminer/channel")
public class ChannelController {

    private ChannelRepository channelRepository;

    @Autowired
    public ChannelController(ChannelRepository channelRepository) { this.channelRepository = channelRepository; }

    @Operation(
            summary = "Retrieve everything from a channel",
            description = "Given some maxVideos and maxComments"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200",description = "Listado de detalles de un canal",
                    content = {@Content(schema = @Schema(implementation = Caption.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",description = "Canal no encontrado",
                    content = {@Content(schema = @Schema())})
    })
    @GetMapping("/{channelId}")
    public VMChannel findByChannelId(@PathVariable String channelId,
                                     @RequestParam(defaultValue = "10") int maxVideos,
                                     @RequestParam(defaultValue = "2") int maxComments) {
        return channelRepository.findByChannelId(channelId, maxVideos, maxComments);
    }

    @Operation(
            summary = "Post a channel to videominer",
            description = "Post a channel given his properties"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "201",description = "Posteo de un Canal",
                    content = {@Content(schema = @Schema(implementation = Video.class),mediaType = "application/json")}),
            @ApiResponse(responseCode = "400",description = "Canal no creado",
                    content = {@Content(schema = @Schema())}),
            @ApiResponse(responseCode = "404",description = "Canal no encontrado",
                    content = {@Content(schema = @Schema())})
    })
    @ResponseStatus(HttpStatus.CREATED) // 201
    @PostMapping("/{channelId}")
    public VMChannel create(@PathVariable String channelId,
                            @RequestParam(defaultValue = "10") int maxVideos,
                            @RequestParam(defaultValue = "2") int maxComments,
                            @RequestHeader("X-API-KEY") String apiKey) {
        return channelRepository.create(channelId, maxVideos, maxComments, apiKey);
    }
}
