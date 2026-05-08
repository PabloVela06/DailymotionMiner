package aiss.dailymotionminer.model.dailymotion;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Video {

    @JsonProperty("id")
    private String id;
    @JsonProperty("title")
    private String name;
    @JsonProperty("description")
    private String description;
    @JsonProperty("created_time")
    private Integer releaseTime;
    @JsonProperty("comments")
    private List<String> comments;
    @JsonProperty("owner.id")
    private String userId;
    @JsonProperty("owner.name")
    private String userName;
    @JsonProperty("owner.avatar_240_url")
    private String userPictureLink;

    @JsonProperty("id")
    public String getId() {
        return id;
    }

    @JsonProperty("id")
    public void setId(String id) {
        this.id = id;
    }

    public Video withId(String id) {
        this.id = id;
        return this;
    }

    @JsonProperty("title")
    public String getName() {
        return name;
    }

    @JsonProperty("title")
    public void setName(String name) {
        this.name = name;
    }

    public Video withName(String name) {
        this.name = name;
        return this;
    }

    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    public Video withDescription(String description) {
        this.description = description;
        return this;
    }

    @JsonProperty("created_time")
    public Integer getReleaseTime() {
        return releaseTime;
    }

    @JsonProperty("created_time")
    public void setReleaseTime(Integer releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Video withReleaseTime(Integer releaseTime) {
        this.releaseTime = releaseTime;
        return this;
    }

    @JsonProperty("comments")
    public List<String> getComments() {
        return comments;
    }

    @JsonProperty("comments")
    public void setComments(List<String> comments) {
        this.comments = comments;
    }

    public Video withComments(List<String> comments) {
        this.comments = comments;
        return this;
    }

    @JsonProperty("owner.id")
    public String getUserId() {
        return userId;
    }

    @JsonProperty("owner.id")
    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Video withUserId(String userId) {
        this.userId = userId;
        return this;
    }

    @JsonProperty("owner.username")
    public String getUserName() {
        return userName;
    }

    @JsonProperty("owner.username")
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Video withUserName(String userName) {
        this.userName = userName;
        return this;
    }

    @JsonProperty("owner.avatar_240_url")
    public String getUserPictureLink() {
        return userPictureLink;
    }

    @JsonProperty("owner.avatar_240_url")
    public void setUserPictureLink(String userPictureLink) {
        this.userPictureLink = userPictureLink;
    }

    public Video withUserPictureLink(String userPictureLink) {
        this.userPictureLink = userPictureLink;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Video.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("id");
        sb.append('=');
        sb.append(((this.id == null) ? "<null>" : this.id));
        sb.append(',');
        sb.append("name");
        sb.append('=');
        sb.append(((this.name == null) ? "<null>" : this.name));
        sb.append(',');
        sb.append("description");
        sb.append('=');
        sb.append(((this.description == null) ? "<null>" : this.description));
        sb.append(',');
        sb.append("createdTime");
        sb.append('=');
        sb.append(((this.releaseTime == null) ? "<null>" : this.releaseTime));
        sb.append(',');
        sb.append("comments");
        sb.append('=');
        sb.append(((this.comments == null) ? "<null>" : this.comments));
        sb.append(',');
        sb.append("ownerId");
        sb.append('=');
        sb.append(((this.userId == null) ? "<null>" : this.userId));
        sb.append(',');
        sb.append("username");
        sb.append('=');
        sb.append(((this.userName == null) ? "<null>" : this.userName));
        sb.append(',');
        sb.append("ownerAvatar");
        sb.append('=');
        sb.append(((this.userPictureLink == null) ? "<null>" : this.userPictureLink));
        sb.append(',');
        if (sb.charAt((sb.length() - 1)) == ',') {
            sb.setCharAt((sb.length() - 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }
}