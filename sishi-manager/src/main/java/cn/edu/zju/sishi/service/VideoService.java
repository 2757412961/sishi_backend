package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.Video;

import java.util.List;

/**
 * @author lemon
 * @date 2021/3/10
 */
public interface VideoService {
  void addVideo(Video video);

  List<Video> listVideos(int start, int length, String logicSymbol);

  Video getVideoByTitle(String videoTitle);

  List<Video> getVideosByTagName(String tagName, int start, int length, String logicSymbol);

  int countVideos();

  int countVideosByTagName(String tagName);

  Video getVideo(String videoId);

  void dropVideo(String videoId);

  int updateIsPublicById(String videoId);
}
