package cn.edu.zju.sishi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Alias("MapInfo")
public class MapInfo implements Serializable {

    @Size(min = 36, max = 36, message = "mapId length should be 36")
    private String mapId;

    @NotNull(message = "mapTitle cannot be null")
    @Size(min = 1, max = 50, message = "mapTitle length should be between 1 and 50")
    private String mapTitle;

    private Double mapLon;

    private Double mapLat;

    private Long mapPublishTime;

    private Long mapCreateTime;

    @JsonProperty("isPublic")
    private Boolean isPublic;

    @JsonProperty("isPoint")
    private Boolean isPoint;

    private String boundary;
}
