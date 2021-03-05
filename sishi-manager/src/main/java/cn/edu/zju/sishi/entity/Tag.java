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
  @Size(min = 1, max = 50, message = "tagName length should be between 1 and 50")
  private String tagName;
}
