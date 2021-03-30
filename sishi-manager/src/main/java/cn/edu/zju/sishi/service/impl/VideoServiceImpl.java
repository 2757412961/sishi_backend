package cn.edu.zju.sishi.service.impl;
import cn.edu.zju.sishi.dao.VideoDao;
import cn.edu.zju.sishi.entity.Video;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * @author lemon
 * @date 2021/3/10
 */
@Service
public class VideoServiceImpl implements VideoService {
  private static final String VIDEO_ALREADY_EXISTS_ERROR_MSG
    = "Video with title %s already exists!";

  @Autowired
  private VideoDao videoDao;

  @Override
  public void addVideo(Video video) {
    String title = video.getVideoTitle();
    Video existVideo = videoDao.getVideoByTitle(title);
    if (existVideo != null) {
      throw new ValidationException(String.format(VIDEO_ALREADY_EXISTS_ERROR_MSG, title));
    }
    video.setVideoId(UUID.randomUUID().toString());
    long publishTime = Instant.now().toEpochMilli();
    long createTime = Instant.now().toEpochMilli();
    video.setVideoPublishTime(publishTime);
    video.setVideoCreateTime(createTime);
    videoDao.addVideo(video);
  }

  @Override
//  @Cacheable(value = "LIST_VIDEOS")
  public List<Video> listVideos(int start, int length, String startTime, String endTime, String logicSymbol) {
    return videoDao.listVideos(length, length*start, startTime, endTime, logicSymbol);
  }

  @Override
  public Video getVideoByTitle(String videoTitle) {
    return videoDao.getVideoByTitle(videoTitle);
  }

  @Override
//  @Cacheable(value = "GET_VIDEOS_BY_NAME")
  public List<Video> getVideosByTagName(String tagName, int start, int length, String logicSymbol) {
    return videoDao.getVideosByTagName(tagName, length, length*start, logicSymbol);
  }

  @Override
  public int countVideos() {
    return videoDao.countVideos();
  }

  @Override
  public int countVideosByTagName(String tagName) {
    return videoDao.countVideosByTagName(tagName);
  }

  @Override
  public Video getVideo(String videoId) {
    return videoDao.getVideo(videoId);
  }

  @Override
  public void dropVideo(String videoId) {
    Video video =videoDao.getVideo(videoId);
    if (video == null) {
      throw new ResourceNotFoundException("video", "id", videoId);
    }
    videoDao.dropVideo(videoId);
  }
  @Override
  public int updateIsPublicById(String videoId) {
    return videoDao.updateIsPublicById(videoId);
  }
}
