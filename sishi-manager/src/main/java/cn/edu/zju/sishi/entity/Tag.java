package cn.edu.zju.sishi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Tag implements Serializable {

  @Size(min = 36, max = 36, message = "id length should be 36")
  private String tagId;
  
  @NotNull(message = "tagName cannot be null")
  @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200")
  private String tagName;

  @Size(max = 50, message = "property length should not larger than 50")
  private String property;

  private String eventTime;
}
