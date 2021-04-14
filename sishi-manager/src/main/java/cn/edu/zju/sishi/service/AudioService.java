package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.Audio;

import java.util.List;

/**
 * @author lemon
 * @date 2021/3/9
 */
public interface AudioService {
  void addAudio(Audio audio);

  List<Audio> listAudios(int start, int length, String logicSymbol);

  Audio getAudioByTitle(String audioTitle);

  List<Audio> getAudiosByTagName(String tagName, int start, int length, String logicSymbol);

  int countAudios();

  int countAudiosByTagName(String tagName);

  Audio getAudio(String audioId);

  void dropAudio(String audioId);

  int updateIsPublicById(String audioId);


}
