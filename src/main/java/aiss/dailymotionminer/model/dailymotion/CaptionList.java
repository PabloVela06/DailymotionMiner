package aiss.dailymotionminer.model.dailymotion;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptionList {

    @JsonProperty("has_more")
    private Boolean hasMore;
    @JsonProperty("captions")
    private List<Caption> captions;

    @JsonProperty("has_more")
    public Boolean getHasMore() {
        return hasMore;
    }

    @JsonProperty("has_more")
    public void setHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
    }

    public CaptionList withHasMore(Boolean hasMore) {
        this.hasMore = hasMore;
        return this;
    }

    @JsonProperty("captions")
    public List<Caption> getCaptions() {
        return captions;
    }

    @JsonProperty("captions")
    public void setCaptions(List<Caption> captions) {
        this.captions = captions;
    }

    public CaptionList withCaptions(List<Caption> captions) {
        this.captions = captions;
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(CaptionList.class.getName()).append('@').append(Integer.toHexString(System.identityHashCode(this))).append('[');
        sb.append("hasMore");
        sb.append('=');
        sb.append(((this.hasMore == null)?"<null>":this.hasMore));
        sb.append(',');
        sb.append("captions");
        sb.append('=');
        sb.append(((this.captions == null)?"<null>":this.captions));
        sb.append(',');
        if (sb.charAt((sb.length()- 1)) == ',') {
            sb.setCharAt((sb.length()- 1), ']');
        } else {
            sb.append(']');
        }
        return sb.toString();
    }

}
