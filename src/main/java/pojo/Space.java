package pojo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

@Data
@Builder
public class Space {

    private String title;
    @JsonProperty("external_id")
    private String externalId;
    private String description;


//    public String getTitle() {
//        return title;
//    }
//
//    public Space setTitle(String title) {
//        this.title = title;
//        return this;
//    }
//
//    public String getDescription() {
//        return description;
//    }
//
//    public void setDescription(String description) {
//        this.description = description;
//    }
//
//    public String getExternal_id() {
//        return externalId;
//    }
//
//    public Space setExternal_id(String external_id) {
//        this.externalId = external_id;
//        return this;
//    }
}
