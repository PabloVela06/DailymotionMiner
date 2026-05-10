package aiss.dailymotionminer.repositories;

import aiss.dailymotionminer.etl.Transformer;
import aiss.dailymotionminer.model.dailymotion.User;
import aiss.dailymotionminer.model.dailymotion.Video;
import aiss.dailymotionminer.model.videominer.*;
import aiss.dailymotionminer.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.UnknownHttpStatusCodeException;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ChannelRepository {

    @Autowired
    ChannelService channelService;
    @Autowired
    VideoService videoService;
    @Autowired
    CommentService commentService;
    @Autowired
    CaptionService captionService;
    @Autowired
    UserService userService;

    public VMChannel findByChannelId(String channelId, Integer maxVideos, Integer maxComments) {
        List<VMVideo> completeVideos = new ArrayList<>();
        VMChannel channel = channelService.getChannel(channelId);

        List<Video> videos = videoService.getVideoDailymotion(channelId, maxVideos);

        for (Video video : videos) {
            VMVideo vmVideo = Transformer.createVMVideo(video);
            vmVideo.setComments(commentService.getComments(video, maxComments));
            vmVideo.setCaptions(captionService.getCaption(video.getId()));
            User user = new User(video.getUserId(), video.getUserName(), "https://www.dailymotion.com/" + video.getUserName(), video.getUserPictureLink());
            vmVideo.setAuthor(Transformer.createVMUser(user));
            completeVideos.add(vmVideo);
        }
        channel.setVideos(completeVideos);
        return channel;
    }

    public VMChannel create(String channelId, Integer maxVideos, Integer maxComments, String apiKey) {
        try {
            List<VMVideo> completeVideos = new ArrayList<>();
            VMChannel channel = channelService.postChannel(channelId, apiKey);
            List<Video> videos = videoService.getVideoDailymotion(channelId, maxVideos);

            for (Video video : videos) {
                VMVideo createdVideo = videoService.postVideo(channel.getId(), Transformer.createVMVideo(video), apiKey);
                User user = new User(video.getUserId(), video.getUserName(), "https://www.dailymotion.com/" + video.getUserName(), video.getUserPictureLink());
                VMUser author = userService.postUser(Transformer.createVMUser(user), apiKey);
                List<VMComment> comments = commentService.postComments(video, createdVideo.getId(), maxComments, apiKey);
                List<VMCaption> captions = captionService.postCaption(video.getId(), createdVideo.getId(), apiKey);
                createdVideo.setAuthor(author);
                createdVideo.setComments(comments);
                createdVideo.setCaptions(captions);
                videoService.updateVideo(createdVideo, apiKey);
                completeVideos.add(createdVideo);
            }
            channel.setVideos(completeVideos);
            channelService.updateChannel(channel, apiKey);

            return channel;

        }
        catch (HttpClientErrorException error) {
            System.err.println("Client error " + error);
            return null;
        }
        catch (HttpServerErrorException error) {
            System.err.println("Server error " + error);
            return null;
        }
        catch (UnknownHttpStatusCodeException error) {
            System.err.println("Unknown error " + error);
            return null;
        }
    }
}