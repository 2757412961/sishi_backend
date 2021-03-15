package cn.edu.zju.sishi.entity;

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


  @NotNull(message = "audio source cannot be null")
  @Size(min = 1, max = 200, message = "audio source length should be between 1 and 200")
  private String audioSource;


  @NotNull(message = "audio content cannot be null")
  private String audioContent;

  private long audioCreateTime;

  private long audioPublishTime;

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