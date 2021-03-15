package cn.edu.zju.sishi.controller;


import cn.edu.zju.sishi.entity.Video;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.VideoService;
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
public class VideoController {

  private static final String ID = "id";

  private final Logger logger = LoggerFactory.getLogger(this.getClass());
  
  @Autowired
  VideoService videoService;

  @ResponseBody
  @RequestMapping(value = "video",method = RequestMethod.POST)
  public Map<String, String> addVideo(@RequestBody @Validated Video video, BindingResult bindingResult) {
    if (bindingResult.hasErrors()){
      throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
    }
    logger.info("start invoke addVideo()");
    Map<String, String> result = new HashMap<>();
    videoService.addVideo(video);
    result.put(ID, video.getVideoId());
    return result;
  }

  @RequestMapping(value = "videos", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject listVideos(
    @RequestParam(value = "start", required = false, defaultValue = "0")
    @Min(value = 0, message = "start must not be negative") int start,
    @RequestParam(value = "length", required = false, defaultValue = "10")
    @Min(value = 1, message = "length must be larger than 0")
    @Max(value = 1000, message = "the number of return size should be no more than 1000") int length) {
    logger.info("start invoke listVideos()");
    JSONObject result = new JSONObject();
    List<Video> videos = videoService.listVideos(start, length);
    result.put("videos", videos);
    int totalCount = videos.size();
    result.put("total", totalCount);
    return result;
  }

  @ResponseBody
  @RequestMapping(value = "video/{videoId}", method = RequestMethod.GET)
  public Video getVideo(
    @Size(min = 36, max = 36, message = "videoId's length must be 36")
    @PathVariable(value = "videoId") String videoId) {
    logger.info("start invoke getVideo()");
    Video video = videoService.getVideo(videoId);
    if (video != null) {
      return video;
    } else {
      throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(),"video", "id", videoId);
    }
  }


  @RequestMapping(value = "videos/tagName/{tagName}", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getVideosByTagName(
    @PathVariable(value = "tagName") String tagName,
    @RequestParam(value = "start", required = false, defaultValue = "0")
    @Min(value = 0, message = "start must not be negative") int start,
    @RequestParam(value = "length", required = false, defaultValue = "10")
    @Min(value = 1, message = "length must be larger than 0")
    @Max(value = 1000, message = "the number of return size should be no more than 1000") int length
  ) {
    logger.info("start invoke getVideoIdsByTagName()");
    JSONObject result = new JSONObject();
    List<Video> videosByTagName = videoService.getVideosByTagName(tagName,start, length);
    int totalCount = videosByTagName.size();
    result.put("totalCount", totalCount);
    result.put("videos", videosByTagName );
    return result;
  }

  @RequestMapping(value = "video/{videoId}", method = RequestMethod.DELETE)
  public Map<String, String> dropVideo (
    @Size(min = 36, max = 36, message = "videoId's length must be 36")
    @PathVariable(value = "videoId") String videoId) {
    logger.info("start invoke dropVideo()");
    Map<String, String> result = new HashMap<>();
    videoService.dropVideo(videoId);
    result.put(ID, videoId);
    return result;
  }
}
