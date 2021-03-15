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
@Alias("Picture")
public class Picture implements Serializable {

    @Size(min = 36, max = 36, message = "pictureId length should be 36")
    String pictureId;

    @NotNull(message = "pictureName can not be null")
    @Size(min = 1, max = 100, message = "pictureName length should be between 1 and 100")
    String pictureName;

    @Size(min = 1, max = 200, message = "pictureUrl length should be between 1 and 200")
    String pictureUrl;

    long createTime;
}
