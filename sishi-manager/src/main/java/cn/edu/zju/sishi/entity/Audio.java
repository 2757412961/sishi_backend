package cn.edu.zju.sishi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lemon
 * @date 2021/3/9
 */
public class Audio implements Serializable {
  private String audioId;

  @NotNull(message = "audio title cannot be null")
  @Size(min = 1, max = 500, message = "audio title length should be between 1 and 500")
  private String audioTitle;

  private String audioAuthor;

  @NotNull(message = "audio source cannot be null")
  @Size(min = 1, max = 200, message = "audio source length should be between 1 and 200")
  private String audioSource;

  @NotNull(message = "audio content cannot be null")
  private String audioContent;

  private String audioLink;

  private long audioCreateTime;

  private long audioPublishTime;

  @JsonProperty("isPublic")
  private Boolean isPublic;

  public String getAudioLink() {
    return audioLink;
  }

  public void setAudioLink(String audioLink) {
    this.audioLink = audioLink;
  }

  public String getAudioAuthor() {
    return audioAuthor;
  }

  public void setAudioAuthor(String audioAuthor) {
    this.audioAuthor = audioAuthor;
  }

  public Boolean getPublic() {
    return isPublic;
  }

  public void setPublic(Boolean aPublic) {
    isPublic = aPublic;
  }

  public String getAudioId() {
    return audioId;
  }

  public void setAudioId(String audioId) {
    this.audioId = audioId;
  }

  public String getAudioTitle() {
    return audioTitle;
  }

  public void setAudioTitle(String audioTitle) {
    this.audioTitle = audioTitle;
  }

  public String getAudioSource() {
    return audioSource;
  }

  public void setAudioSource(String audioSource) {
    this.audioSource = audioSource;
  }

  public String getAudioContent() {
    return audioContent;
  }

  public void setAudioContent(String audioContent) {
    this.audioContent = audioContent;
  }

  public long getAudioCreateTime() {
    return audioCreateTime;
  }

  public void setAudioCreateTime(long audioCreateTime) {
    this.audioCreateTime = audioCreateTime;
  }

  public long getAudioPublishTime() {
    return audioPublishTime;
  }

  public void setAudioPublishTime(long audioPublishTime) {
    this.audioPublishTime = audioPublishTime;
  }
}
