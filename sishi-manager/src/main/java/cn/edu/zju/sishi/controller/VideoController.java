package cn.edu.zju.sishi.controller;


import cn.edu.zju.sishi.commons.utils.BindResultUtils;
import cn.edu.zju.sishi.commons.utils.LogicUtil;
import cn.edu.zju.sishi.config.NginxConfig;
import cn.edu.zju.sishi.entity.TagResource;
import cn.edu.zju.sishi.entity.Video;
import cn.edu.zju.sishi.enums.ResourceTypeEnum;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.passport.annotation.AuthController;
import cn.edu.zju.sishi.service.AuthorityService;
import cn.edu.zju.sishi.service.TagResourceService;
import cn.edu.zju.sishi.service.VideoService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
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
@AuthController
public class VideoController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private static final String ID = "id";
    private static final String VIDEO_FILE = "videoFile";

    @Autowired
    private NginxConfig nginxConfig;

    @Autowired
    VideoService videoService;

    @Autowired
    TagResourceService tagResourceService;

    @Autowired
    private AuthorityService authorityService;

    @ResponseBody
    @RequestMapping(value = "video", method = RequestMethod.POST)
    public Map<String, String> addVideo(@RequestBody @Validated Video video, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        logger.info("start invoke addVideo()");
        Map<String, String> result = new HashMap<>();
        videoService.addVideo(video);
        result.put(ID, video.getVideoId());
        return result;
    }

    @Transactional
    @RequestMapping(value = "video/tagName/{tagName}", method = RequestMethod.POST)
    public Video addVideoByTagName(@RequestBody
                                   @Validated Video video,
                                   BindingResult bindingResult,
                                   @PathVariable("tagName")
                                   @NotNull(message = "tagName cannot be null")
                                   @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName,
                                   HttpServletRequest request) {
        BindResultUtils.validData(bindingResult);

        logger.info("Start invoke addVideoByTagName()");
        // 先添加资源表的记录
        videoService.addVideo(video);
        // 再添加资源关联表中的记录
        TagResource tagResource = new TagResource("", tagName, video.getVideoId(), ResourceTypeEnum.VIDEO.getResourceType(), authorityService.getUserId(request));
        tagResourceService.addTagResource(tagResource);

        return video;
    }


    /**
     * 通过 form 表单上传视频文件，并保存至数据库
     *
     * @param request
     * @return
     */
    @Transactional
    @RequestMapping(value = "video/form", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Video addVideoFileByForm(HttpServletRequest request) {
        logger.info("Start invoke addVideoFileByForm()");
        Video video = new Video();

        try {
            // 判断属性是否存在
            String videoTitle = request.getParameter("videoTitle");
            if (videoTitle == null) {
                throw new ValidationException("未提交标题");
            }
            String videoSource = request.getParameter("videoSource");
            if (videoSource == null) {
                throw new ValidationException("未提交来源");
            }
            String videoAuthor = request.getParameter("videoAuthor");
            if (videoAuthor == null) {
                throw new ValidationException("未提交作者信息");
            }
            String tagName = request.getParameter("tagName");
            if (tagName == null) {
                throw new ValidationException("未提交标签名");
            }
            // 判断文件是否存在
            MultipartHttpServletRequest multipartHttpServletRequest = ((MultipartHttpServletRequest) request);
            MultipartFile multipartFile = multipartHttpServletRequest.getFile(VIDEO_FILE);
            // 判断空指针的情况
            if (multipartFile == null || multipartFile.isEmpty()) {
                throw new ValidationException("请求不是表单格式，或者未上传文件对象");
            }

            // 文件名称
            video.setVideoTitle(videoTitle);
            video.setVideoAuthor(videoAuthor);
            video.setVideoSource(videoSource);
            String fileName = multipartFile.getOriginalFilename();

            // 保存资源记录
            logger.info("Start invoke save record in db");
            video.setVideoContent(nginxConfig.getHttpHead() + nginxConfig.getVideoPath() + fileName);
            videoService.addVideo(video);

            // 保存 标签、资源 关联记录
            TagResource tagResource = new TagResource("", tagName, video.getVideoId(), ResourceTypeEnum.VIDEO.getResourceType(), authorityService.getUserId(request));
            tagResourceService.addTagResource(tagResource);

            // 保存到本地
            File localFile = new File(nginxConfig.getLinuxRoot() + nginxConfig.getVideoPath() + fileName);
            if (localFile.exists()) {
                throw new ValidationException(String.format("%s 文件已存在，请修改文件名！", fileName));
            }
            multipartFile.transferTo(localFile);
        } catch (Exception e) {
            logger.error("addVideoFileByForm Error", e);
            throw new ValidationException(e.toString());
        }

        return video;
    }

    @RequestMapping(value = "videos", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject listVideos(
            @RequestParam(value = "start", required = false, defaultValue = "0")
            @Min(value = 0, message = "start must not be negative") int start,
            @RequestParam(value = "length", required = false, defaultValue = "10")
            @Min(value = 1, message = "length must be larger than 0")
            @Max(value = 1000, message = "the number of return size should be no more than 1000") int length,
            HttpServletRequest request) {
        logger.info("start invoke listVideos()");
        JSONObject result = new JSONObject();
        boolean isAdministrator = authorityService.isAdamin(request);
        List<Video> videos = videoService.listVideos(start, length, LogicUtil.getLogicByIsAdmins(isAdministrator));
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
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), "video", "id", videoId);
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
            @Max(value = 1000, message = "the number of return size should be no more than 1000") int length,
            HttpServletRequest request) {
        logger.info("start invoke getVideoIdsByTagName()");
        JSONObject result = new JSONObject();
        boolean isAdministrator = authorityService.isAdamin(request);
        List<Video> videosByTagName = videoService.getVideosByTagName(tagName, start, length, LogicUtil.getLogicByIsAdmins(isAdministrator));
        int totalCount = videosByTagName.size();
        result.put("totalCount", totalCount);
        result.put("videos", videosByTagName);
        return result;
    }

    @RequestMapping(value = "video/{videoId}", method = RequestMethod.DELETE)
    public Map<String, String> dropVideo(
            @Size(min = 36, max = 36, message = "videoId's length must be 36")
            @PathVariable(value = "videoId") String videoId) {
        logger.info("start invoke dropVideo()");
        Map<String, String> result = new HashMap<>();
        videoService.dropVideo(videoId);
        result.put(ID, videoId);
        return result;
    }

    @Transactional
    @RequestMapping(value = "video/{videoId}/tagName/{tagName}", method = RequestMethod.DELETE)
    public Map<String, String> deleteVideoByTagName(@PathVariable("videoId")
                                                    @Size(min = 36, max = 36, message = "videoId length should be 36") String videoId,
                                                    @PathVariable("tagName")
                                                    @NotNull(message = "tagName cannot be null")
                                                    @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName) {
        logger.info("Start invoke deleteVideoByTagName()");
        // 1. 找到资源
        Video video = videoService.getVideo(videoId);
        // 2. 删除资源关联表中的记录
        tagResourceService.deleteTagResource(tagName, videoId);
        // 3. 再删除资源表的记录
        videoService.dropVideo(videoId);
        // 4. 删除本地资源
        try {
            File localFile = new File(nginxConfig.getLinuxRoot() + video.getVideoContent().substring(nginxConfig.getHttpHead().length()));
            if (localFile.exists()) {
                localFile.delete();
            }
        } catch (Exception e) {
            logger.error("deleteVideoByTagName Error", e);
        }

        Map<String, String> result = new HashMap<>();
        result.put(ID, videoId);

        return result;
    }

    @Transactional
    @RequestMapping(value = "video/public/{videoId}", method = RequestMethod.PUT)
    public Map<String, String> updateIsPublicById(@PathVariable("videoId")
                                                  @Size(min = 36, max = 36, message = "videoId length should be 36") String videoId,
                                                  HttpServletRequest request) {
        logger.info("Start invoke updateIsPublicById()");
        if (!authorityService.isAdamin(request)) {
            throw new ValidationException("No permission to perform this operation");
        }

        videoService.updateIsPublicById(videoId);

        Map<String, String> result = new HashMap<>();
        result.put(ID, videoId);

        return result;
    }
}
