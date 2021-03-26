package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.Video;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lemon
 * @date 2021/3/10
 */
@Component
@Mapper
public interface VideoDao {
  void addVideo(@Param("video") Video video);

  List<Video> listVideos(@Param("length") int length,
                         @Param("offset") int offset,
                         @Param("startTime") String startTime,
                         @Param("endTime") String endTime);

  int countVideos();

  int countVideosByTagName(@Param("tagName") String tagName);

  Video getVideo(@Param("videoId") String videoId);

  Video getVideoByTitle(@Param("title") String title);

  List<Video> getVideosByTagName(@Param("tagName") String tagName,
                                 @Param("start") int start,
                                 @Param("length") int length);


  void dropVideo(@Param("videoId") String videoId);

  int updateIsPublicById(@Param("videoId") String videoId);
}
