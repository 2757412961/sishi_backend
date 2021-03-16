package cn.edu.zju.sishi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.ibatis.type.Alias;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Alias("MapInfo")
public class MapInfo implements Serializable {

    @Size(min = 36, max = 36, message = "mapId length should be 36")
    String mapId;

    @NotNull(message = "mapTitle cannot be null")
    @Size(min = 1, max = 50, message = "mapTitle length should be between 1 and 50")
    String mapTitle;

    @NotNull(message = "longitude can not be null")
    Double mapLon;

    @NotNull(message = "latitude can not be null")
    Double mapLat;

    Long mapPublishTime;

    Long mapCreateTime;


}
