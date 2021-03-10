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

    @Size(min = 36, max = 36, message = "id length should be 36")
    String mapId;

    @NotNull(message = "mapName cannot be null")
    @Size(min = 1, max = 50, message = "mapName length should be between 1 and 50")
    String mapName;

    @NotNull(message = "mapJson can not be null")
    String mapJson;

    Long createTime;

}
