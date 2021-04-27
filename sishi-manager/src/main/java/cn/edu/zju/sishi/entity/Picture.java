package cn.edu.zju.sishi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
@Alias("Picture")
public class Picture implements Serializable {

    @Size(min = 36, max = 36, message = "pictureId length should be 36")
    private String pictureId;

    @NotNull(message = "pictureTitle can not be null")
    @Size(min = 1, max = 500, message = "pictureTitle length should be between 1 and 500")
    private String pictureTitle;

    private String pictureAuthor;

    @NotNull(message = "pictureSource can not be null")
    @Size(min = 1, max = 500, message = "pictureSource length should be between 1 and 200")
    private String pictureSource;

    @NotNull(message = "pictureSource can not be null")
    @Size(min = 1, max = 500, message = "pictureContent length should be between 1 and 200")
    private String pictureContent;

    private String pictureLink;

    private long picturePublishTime;

    private long pictureCreateTime;

    @JsonProperty("isPublic")
    private Boolean isPublic;
}
