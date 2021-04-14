package cn.edu.zju.sishi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lemon
 * @date 2021/3/5
 */
@Data
public class Article implements Serializable {
  private String articleId;

  @NotNull(message = "article title cannot be null")
  @Size(min = 1, max = 500, message = "article title length should be between 1 and 500")
  private String articleTitle;

  @NotNull(message = "article Author cannot be null")
  @Size(min = 1, max = 200, message = "article Author length should be between 1 and 200")
  private String articleAuthor;

  @NotNull(message = "article Content cannot be null")
  private String articleContent;

  private String articleSource;

  private String articleEventTime;

  private String articleAddress;

  private long articleCreateTime;

  private long articlePublishTime;

  @JsonProperty("isPublic")
  private Boolean isPublic;
}
