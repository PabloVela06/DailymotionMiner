package aiss.dailymotionminer.model.videominer;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Caption")
public class VMCaption {

    @Id
    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    @Column(name = "name")
    private String name;

    @JsonProperty("language")
    @Column(name = "language")
    private String language;

    public VMCaption(){

    }

    public VMCaption(String id, String name, String language){
        this.id=id;
        this.name=name;
        this.language=language;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    @Override
    public String toString() {
        return "Caption{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", language='" + language + '\'' +
                '}';
    }
}