package aiss.dailymotionminer.etl;

import aiss.dailymotionminer.model.dailymotion.*;
import aiss.dailymotionminer.model.videominer.*;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.UUID;

public class Transformer {

    private static String createId(String dailyMotionId) {
        return String.format("dailymotion-%s", dailyMotionId);
    }

    public static VMChannel createVMChannel(Channel channel) {
        return new VMChannel(createId(channel.getId()),
                channel.getName(),
                channel.getDescription(),
                LocalDateTime.ofEpochSecond(channel.getCreatedTime(), 0, java.time.ZoneOffset.UTC).toString());
    }

    public static VMCaption createVMCaption(Caption caption) {
        return new VMCaption(String.format("dailymotion-%s",caption.getId()),
                caption.getLink(),//name en videoMiner
                caption.getLanguage());
    }

    public static VMComment createVMComment(String comment, Integer createdOn) {
        return new VMComment(UUID.randomUUID().toString(),
                comment,
                createdOn.toString());
    }

    public static VMUser createVMUser(User user){
        return new VMUser(user.getName(),
                user.getUserLink(),
                user.getAvatar());
    }

    public static VMVideo createVMVideo(Video video) {
        return new VMVideo(createId(video.getId()),
                video.getName(),
                video.getDescription(),
                video.getReleaseTime().toString());
    }
}
