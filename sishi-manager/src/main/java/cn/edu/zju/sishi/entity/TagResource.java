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
@Alias("TagResource")
public class TagResource implements Serializable {

    @Size(min = 36, max = 36, message = "tagId length should be 36")
    private String tagId;

    @NotNull(message = "tagName cannot be null")
    @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200")
    private String tagName;


    @Size(min = 36, max = 36, message = "resourceId length should be 36")
    private String resourceId;

    @Size(min = 1, max = 20, message = "resourceType length should be between 1 and 200")
    private String resourceType;

    private String userId;

}
