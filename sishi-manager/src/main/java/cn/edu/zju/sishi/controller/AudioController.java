package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.entity.Audio;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.AudioService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lemon
 * @date 2021/3/10
 */
@RestController
@Validated
public class AudioController {
  private static final String ID = "id";

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  AudioService audioService;

  @ResponseBody
  @RequestMapping(value = "audio", method = RequestMethod.POST)
  public Map<String, String> addAudio(@RequestBody @Validated Audio audio, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
    }
    logger.info("start invoke addAudio()");
    Map<String, String> result = new HashMap<>();
    audioService.addAudio(audio);
    result.put(ID, audio.getAudioId());
    return result;
  }

  @RequestMapping(value = "audios", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject listAudios(
    @RequestParam(value = "start", required = false, defaultValue = "0")
    @Min(value = 0, message = "start must not be negative") int start,
    @RequestParam(value = "length", required = false, defaultValue = "10")
    @Min(value = 1, message = "length must be larger than 0")
    @Max(value = 1000, message = "the number of return size should be no more than 1000") int length) {
    logger.info("start invoke listAudios()");
    JSONObject result = new JSONObject();
    List<Audio> audios = audioService.listAudios(start, length);
    result.put("audios", audios);
    int totalCount = audios.size();
    result.put("total", totalCount);
    return result;
  }

  @ResponseBody
  @RequestMapping(value = "audio/{audioId}", method = RequestMethod.GET)
  public Audio getAudio(
    @Size(min = 36, max = 36, message = "audioId's length must be 36")
    @PathVariable(value = "audioId") String audioId) {
    logger.info("start invoke getAudio()");
    Audio audio = audioService.getAudio(audioId);
    if (audio != null) {
      return audio;
    } else {
      throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), "audio", "id", audioId);
    }
  }


  @RequestMapping(value = "audios/tagName/{tagName}", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getAudiosByTagName(
    @PathVariable(value = "tagName") String tagName,
    @RequestParam(value = "start", required = false, defaultValue = "0")
    @Min(value = 0, message = "start must not be negative") int start,
    @RequestParam(value = "length", required = false, defaultValue = "10")
    @Min(value = 1, message = "length must be larger than 0")
    @Max(value = 1000, message = "the number of return size should be no more than 1000") int length
  ) {
    logger.info("start invoke getAudiosByTagName()");
    JSONObject result = new JSONObject();

    List<Audio> audiosByTagName = audioService.getAudiosByTagName(tagName, start, length);
    result.put("audiosByTagName ", audiosByTagName);

    int totalCount = audiosByTagName.size();
    result.put("total", totalCount);
    return result;
  }

  @RequestMapping(value = "audio/{audioId}", method = RequestMethod.DELETE)
  public Map<String, String> dropAudio(
    @Size(min = 36, max = 36, message = "audioId's length must be 36")
    @PathVariable(value = "audioId") String audioId) {
    logger.info("start invoke dropAudio()");
    Map<String, String> result = new HashMap<>();
    audioService.dropAudio(audioId);
    result.put(ID, audioId);
    return result;
  }


}
