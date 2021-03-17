package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.commons.utils.BindResultUtils;
import cn.edu.zju.sishi.config.NginxConfig;
import cn.edu.zju.sishi.entity.Audio;
import cn.edu.zju.sishi.entity.TagResource;
import cn.edu.zju.sishi.enums.ResourceTypeEnum;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.AudioService;
import cn.edu.zju.sishi.service.TagResourceService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
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

  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  private static final String ID = "id";
  private static final String AUDIO_FILE = "audioFile";

  @Autowired
  private NginxConfig nginxConfig;

  @Autowired
  AudioService audioService;

  @Autowired
  TagResourceService tagResourceService;

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

  @RequestMapping(value = "/audio/tagName/{tagName}", method = RequestMethod.POST)
  public Audio addAudioByTagName(@RequestBody
                                     @Validated Audio audio,
                                     BindingResult bindingResult,
                                     @PathVariable("tagName")
                                     @NotNull(message = "tagName cannot be null")
                                     @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName) {
    BindResultUtils.validData(bindingResult);

    logger.info("Start invoke addAudioByTagName()");
    // 先添加资源表的记录
    audioService.addAudio(audio);
    // 再添加资源关联表中的记录
    TagResource tagResource = new TagResource("", tagName, audio.getAudioId(), ResourceTypeEnum.AUDIO.getResourceType());
    tagResourceService.addTagResource(tagResource);

    return audio;
  }


  /**
   * 通过 form 表单上传音频文件，并保存至数据库
   *
   * @param request
   * @return
   */
  @RequestMapping(value = "/audio/form", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
  public Audio addAudioFileByForm(HttpServletRequest request) {
    logger.info("Start invoke addAudioFileByForm()");
    Audio audio = new Audio();

    try {
      // 判断属性是否存在
      String audioTitle = request.getParameter("audioTitle");
      if (audioTitle == null) {
        throw new ValidationException("未提交标题");
      }
      String audioSource = request.getParameter("audioSource");
      if (audioSource == null) {
          throw new ValidationException("未提交来源");
      }
      String tagName = request.getParameter("tagName");
      if (tagName == null) {
        throw new ValidationException("未提交标签名");
      }
      // 判断文件是否存在
      MultipartHttpServletRequest multipartHttpServletRequest = ((MultipartHttpServletRequest) request);
      MultipartFile multipartFile = multipartHttpServletRequest.getFile(AUDIO_FILE);
      // 判断空指针的情况
      if (multipartFile == null || multipartFile.isEmpty()) {
        throw new ValidationException("请求不是表单格式，或者未上传文件对象");
      }

      // 文件名称
      audio.setAudioTitle(audioTitle);
      audio.setAudioSource(audioSource);
      String fileName = multipartFile.getOriginalFilename();

      // 保存到本地
      // TODO 需要将 windows 的路径改为 linux 的路径
      File localFile = new File(nginxConfig.getLinuxRoot() + nginxConfig.getAudioPath() + fileName);
      if (localFile.exists()) {
        throw new ValidationException(String.format("%s 文件已存在，请修改文件名！", fileName));
      }
      multipartFile.transferTo(localFile);

      // 保存资源记录
      logger.info("Start invoke save record in db");
      audio.setAudioContent(nginxConfig.getHttpHead() + nginxConfig.getAudioPath() + fileName);
      audioService.addAudio(audio);

      // 保存 标签、资源 关联记录
      TagResource tagResource = new TagResource("", tagName, audio.getAudioId(), ResourceTypeEnum.AUDIO.getResourceType());
      tagResourceService.addTagResource(tagResource);
    } catch (Exception e) {
      logger.error("addAudioFileByForm Error", e);
      throw new ValidationException(e.toString());
    }

    return audio;
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

  @RequestMapping(value = "/audio/{audioId}/tagName/{tagName}", method = RequestMethod.DELETE)
  public Map<String, String> deleteAudioByTagName(@PathVariable("audioId")
                                                    @Size(min = 36, max = 36, message = "audioId length should be 36") String audioId,
                                                    @PathVariable("tagName")
                                                    @NotNull(message = "tagName cannot be null")
                                                    @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName) {
    logger.info("Start invoke deleteAudioByTagName()");
    // 先删除资源关联表中的记录
    tagResourceService.deleteTagResource(tagName, audioId);
    // 再删除资源表的记录
    audioService.dropAudio(audioId);

    Map<String, String> result = new HashMap<>();
    result.put(ID, audioId);

    return result;
  }

}
