package cn.edu.zju.sishi.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MediaItem implements Serializable {

    private String id;

    private String name;

    private String type;

    private String url;

}
