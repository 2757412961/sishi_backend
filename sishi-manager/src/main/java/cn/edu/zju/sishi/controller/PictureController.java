package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.commons.utils.BindResultUtils;
import cn.edu.zju.sishi.commons.utils.LogicUtil;
import cn.edu.zju.sishi.config.NginxConfig;
import cn.edu.zju.sishi.entity.Picture;
import cn.edu.zju.sishi.entity.TagResource;
import cn.edu.zju.sishi.enums.ResourceTypeEnum;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.passport.annotation.AuthController;
import cn.edu.zju.sishi.service.AuthorityService;
import cn.edu.zju.sishi.service.PictureService;
import cn.edu.zju.sishi.service.TagResourceService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
//@Validated
@RestController
@AuthController
public class PictureController {

    private static final String PICTURE_ID = "pictureId";
    private static final String PICTURE_FILE = "pictureFile";

    @Autowired
    private NginxConfig nginxConfig;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private TagResourceService tagResourceService;

    @Autowired
    private AuthorityService authorityService;

    @RequestMapping(value = "pictures", method = RequestMethod.GET)
    public JSONObject getPicturesAll(
            @RequestParam(value = "startTime", required = false, defaultValue = "1890-1-1") String startTime,
            @RequestParam(value = "endTime", required = false, defaultValue = "2056-1-1") String endTime,
            HttpServletRequest request) {
        log.info("Start invoke getPicturesAll()");
        boolean isAdministrator = authorityService.isAdamin(request);
        List<Picture> pictures = pictureService.getPicturesAll(startTime, endTime, LogicUtil.getLogicByIsAdmins(isAdministrator));

        JSONObject result = new JSONObject();
        result.put("totalCount", pictures.size());
        result.put("pictures", pictures);

        return result;
    }

    @RequestMapping(value = "picture/{pictureId}", method = RequestMethod.GET)
    public Picture getPictureById(@PathVariable("pictureId")
                                  @Size(min = 36, max = 36, message = "pictureId length should be 36") String pictureId) {
        log.info("Start invoke getPictureById()");
        return pictureService.getPictureById(pictureId);
    }

    @RequestMapping(value = "pictures/ids", method = RequestMethod.POST)
    public JSONObject getPicturesByIds(@RequestBody
                                       @NotEmpty List<@Size(min = 36, max = 36, message = "pictureId length should be 36") String> pictureIds) {
        log.info("Start invoke getPicturesByIds()");
        List<Picture> pictures = pictureService.getPicturesByIds(pictureIds);

        JSONObject result = new JSONObject();
        result.put("totalCount", pictures.size());
        result.put("pictures", pictures);

        return result;
    }

    @RequestMapping(value = "pictures/tagName/{tagName}", method = RequestMethod.GET)
    public JSONObject getPicturesByTag(@PathVariable("tagName")
                                       @NotNull(message = "tagName cannot be null")
                                       @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName,
                                       HttpServletRequest request) {
        log.info("Start invoke getPicturesByTag()");
        boolean isAdministrator = authorityService.isAdamin(request);
        List<Picture> pictures = pictureService.getPicturesByTag(tagName, LogicUtil.getLogicByIsAdmins(isAdministrator));

        JSONObject result = new JSONObject();
        result.put("totalCount", pictures.size());
        result.put("pictures", pictures);

        return result;
    }


    @RequestMapping(value = "picture", method = RequestMethod.POST)
    public Picture addPicture(@RequestBody @Validated Picture picture, BindingResult bindingResult) {
        BindResultUtils.validData(bindingResult);

        log.info("Start invoke addPicture()");
        pictureService.addPicture(picture);

        return picture;
    }

    @Transactional
    @RequestMapping(value = "picture/tagName/{tagName}", method = RequestMethod.POST)
    public Picture addPictureByTagName(@RequestBody
                                       @Validated Picture picture,
                                       BindingResult bindingResult,
                                       @PathVariable("tagName")
                                       @NotNull(message = "tagName cannot be null")
                                       @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName) {
        BindResultUtils.validData(bindingResult);

        log.info("Start invoke addPictureByTagName()");
        // 先添加资源表的记录
        pictureService.addPicture(picture);
        // 再添加资源关联表中的记录
        TagResource tagResource = new TagResource("", tagName, picture.getPictureId(), ResourceTypeEnum.PICTURE.getResourceType());
        tagResourceService.addTagResource(tagResource);

        return picture;
    }

    /**
     * 通过 form 表单上传图片文件，并保存至数据库
     *
     * @param request
     * @return
     */
    @Transactional
    @RequestMapping(value = "picture/form", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Picture addPictureFileByForm(HttpServletRequest request) {
        log.info("Start invoke addPictureFileByForm()");
        Picture picture = new Picture();

        try {
            // 判断属性是否存在
            String pictureTitle = request.getParameter("pictureTitle");
            if (pictureTitle == null) {
                throw new ValidationException("未提交标题");
            }
            String pictureSource = request.getParameter("pictureSource");
            if (pictureSource == null) {
                throw new ValidationException("未提交来源");
            }
            String eventTime = request.getParameter("eventTime");
            if (eventTime == null) {
                throw new ValidationException("未提交事件时间");
            }
            String tagName = request.getParameter("tagName");
            if (tagName == null) {
                throw new ValidationException("未提交标签名");
            }
            // 判断文件是否存在
            MultipartHttpServletRequest multipartHttpServletRequest = ((MultipartHttpServletRequest) request);
            MultipartFile multipartFile = multipartHttpServletRequest.getFile(PICTURE_FILE);
            // 判断空指针的情况
            if (multipartFile == null || multipartFile.isEmpty()) {
                throw new ValidationException("请求不是表单格式，或者未上传文件对象");
            }

            // 文件名称
            picture.setPictureTitle(pictureTitle);
            picture.setPictureSource(pictureSource);
            picture.setEventTime(eventTime);
            String fileName = multipartFile.getOriginalFilename();

            // 保存资源记录
            log.info("Start invoke save record in db");
            picture.setPictureContent(nginxConfig.getHttpHead() + nginxConfig.getPicPath() + fileName);
            pictureService.addPicture(picture);

            // 保存 标签、资源 关联记录
            TagResource tagResource = new TagResource("", tagName, picture.getPictureId(), ResourceTypeEnum.PICTURE.getResourceType());
            tagResourceService.addTagResource(tagResource);

            // 保存到本地
            File localFile = new File(nginxConfig.getLinuxRoot() + nginxConfig.getPicPath() + fileName);
            if (localFile.exists()) {
                throw new ValidationException(String.format("%s 文件已存在，请修改文件名！", fileName));
            }
            multipartFile.transferTo(localFile);
        } catch (Exception e) {
            log.error("addPictureFileByForm Error", e);
            throw new ValidationException(e.toString());
        }

        return picture;
    }


    @RequestMapping(value = "picture/{pictureId}", method = RequestMethod.DELETE)
    public Map<String, String> deletePictureById(@PathVariable("pictureId")
                                                 @Size(min = 36, max = 36, message = "pictureId length should be 36") String pictureId) {
        log.info("Start invoke deletePictureById()");
        pictureService.deletePictureById(pictureId);
        Map<String, String> result = new HashMap<>();
        result.put(PICTURE_ID, pictureId);
        return result;
    }

    @Transactional
    @RequestMapping(value = "picture/{pictureId}/tagName/{tagName}", method = RequestMethod.DELETE)
    public Map<String, String> deletePictureByTagName(@PathVariable("pictureId")
                                                      @Size(min = 36, max = 36, message = "pictureId length should be 36") String pictureId,
                                                      @PathVariable("tagName")
                                                      @NotNull(message = "tagName cannot be null")
                                                      @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName) {
        log.info("Start invoke deletePictureByTagName()");
        // 1. 找到资源
        Picture picture = pictureService.getPictureById(pictureId);
        // 2. 删除资源关联表中的记录
        tagResourceService.deleteTagResource(tagName, pictureId);
        // 3. 再删除资源表的记录
        pictureService.deletePictureById(pictureId);
        // 4. 删除本地资源
        File localFile = new File(nginxConfig.getLinuxRoot() + picture.getPictureContent().substring(nginxConfig.getHttpHead().length()));
        if (localFile.exists()) {
            localFile.delete();
        }

        Map<String, String> result = new HashMap<>();
        result.put(PICTURE_ID, pictureId);

        return result;
    }

    @Transactional
    @RequestMapping(value = "picture/public/{pictureId}", method = RequestMethod.PUT)
    public Map<String, String> updateIsPublicById(@PathVariable("pictureId")
                                                  @Size(min = 36, max = 36, message = "pictureId length should be 36") String pictureId,
                                                  HttpServletRequest request) {
        log.info("Start invoke updateIsPublicById()");
        if (!authorityService.isAdamin(request)) {
            throw new ValidationException("No permission to perform this operation");
        }

        pictureService.updateIsPublicById(pictureId);

        Map<String, String> result = new HashMap<>();
        result.put(PICTURE_ID, pictureId);

        return result;
    }

}
