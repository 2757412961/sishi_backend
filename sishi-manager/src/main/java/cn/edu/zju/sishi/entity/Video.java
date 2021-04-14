package cn.edu.zju.sishi.entity;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lemon
 * @date 2021/3/10
 */
public class Video implements Serializable {
  private String videoId;

  @NotNull(message = "video title cannot be null")
  @Size(min = 1, max = 500, message = "video title length should be between 1 and 500")
  private String videoTitle;

  private String videoAuthor;

  @NotNull(message = "video source cannot be null")
  @Size(min = 1, max = 200, message = "video source length should be between 1 and 200")
  private String videoSource;

  @NotNull(message = "video content cannot be null")
  private String videoContent;

  private long videoCreateTime;

  private long videoPublishTime;

  @JsonProperty("isPublic")
  private Boolean isPublic;

  public String getVideoAuthor() {
    return videoAuthor;
  }

  public void setVideoAuthor(String videoAuthor) {
    this.videoAuthor = videoAuthor;
  }

  public Boolean getPublic() {
    return isPublic;
  }

  public void setPublic(Boolean aPublic) {
    isPublic = aPublic;
  }

  public String getVideoId() {
    return videoId;
  }

  public void setVideoId(String videoId) {
    this.videoId = videoId;
  }

  public String getVideoTitle() {
    return videoTitle;
  }

  public void setVideoTitle(String videoTitle) {
    this.videoTitle = videoTitle;
  }

  public String getVideoSource() {
    return videoSource;
  }

  public void setVideoSource(String videoSource) {
    this.videoSource = videoSource;
  }

  public String getVideoContent() {
    return videoContent;
  }

  public void setVideoContent(String videoContent) {
    this.videoContent = videoContent;
  }

  public long getVideoCreateTime() {
    return videoCreateTime;
  }

  public void setVideoCreateTime(long videoCreateTime) {
    this.videoCreateTime = videoCreateTime;
  }

  public long getVideoPublishTime() {
    return videoPublishTime;
  }

  public void setVideoPublishTime(long videoPublishTime) {
    this.videoPublishTime = videoPublishTime;
  }

  @Override
  public String toString() {
    return "Video{" +
      "videoId='" + videoId + '\'' +
      ", videoTitle='" + videoTitle + '\'' +
      ", videoSource='" + videoSource + '\'' +
      ", videoContent='" + videoContent + '\'' +
      ", videoCreateTime=" + videoCreateTime +
      ", videoPublishTime=" + videoPublishTime +
      '}';
  }
}
