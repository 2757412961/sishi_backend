package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.Audio;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lemon
 * @date 2021/3/9
 */
@Mapper
@Component
public interface AudioDao {
  void addAudio(@Param("audio") Audio audio);

  List<Audio> listAudios(@Param("length") int length,
                         @Param("offset") int offset);

  int countAudios();

  int countAudiosByTagName(@Param("tagName") String tagName);

  Audio getAudio(@Param("audioId") String audioId);

  List<Audio> getAudiosByTagName(@Param("tagName") String tagName,
                                 @Param("start") int start,
                                 @Param("length") int length);


  void dropAudio(@Param("audioId") String audioId);
  Audio getAudioByTitle(@Param("title") String title);


}
