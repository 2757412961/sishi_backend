package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.dao.AudioDao;
import cn.edu.zju.sishi.entity.Audio;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.AudioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * @author lemon
 * @date 2021/3/10
 */
@Service
public class AudioServiceImpl implements AudioService {
  private static final String AUDIO_ALREADY_EXISTS_ERROR_MSG
    = "AUDIO with title %s already exists !";
  @Autowired
  private AudioDao audioDao;

  @Override
  public void addAudio(Audio audio) {
    String title = audio.getAudioTitle();
    Audio existAudio = audioDao.getAudioByTitle(title);
    if (existAudio != null) {
      throw new ValidationException(String.format(AUDIO_ALREADY_EXISTS_ERROR_MSG, title));
    }
    audio.setAudioId(UUID.randomUUID().toString());
    long publishTime = Instant.now().toEpochMilli();
    long createTime = Instant.now().toEpochMilli();
    audio.setAudioPublishTime(publishTime);
    audio.setAudioCreateTime(createTime);
    audioDao.addAudio(audio);
  }

  @Override
  public List<Audio> listAudios(int start, int length, String startTime, String endTime) {
    return audioDao.listAudios(length, length * start, startTime, endTime);
  }

  @Override
  public Audio getAudioByTitle(String audioTitle) {
    return audioDao.getAudioByTitle(audioTitle);
  }

  @Override
  public List<Audio> getAudiosByTagName(String tagName, int start, int length) {
    return audioDao.getAudiosByTagName(tagName, length, length * start);
  }

  @Override
  public int countAudios() {
    return audioDao.countAudios();
  }

  @Override
  public int countAudiosByTagName(String tagName) {
    return audioDao.countAudiosByTagName(tagName);
  }

  @Override
  public Audio getAudio(String audioId) {
    return audioDao.getAudio(audioId);
  }

  @Override
  public void dropAudio(String audioId) {
    Audio audio = audioDao.getAudio(audioId);
    if (audio == null) {
      throw new ResourceNotFoundException("audio", "id", audioId);
    }
    audioDao.dropAudio(audioId);
  }

  public int updateIsPublicById(String audioId) {
    return audioDao.updateIsPublicById(audioId);
  }


}
