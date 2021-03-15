package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.commons.utils.BindResultUtils;
import cn.edu.zju.sishi.config.NginxConfig;
import cn.edu.zju.sishi.entity.Picture;
import cn.edu.zju.sishi.entity.TagResource;
import cn.edu.zju.sishi.enums.ResourceTypeEnum;
import cn.edu.zju.sishi.passport.annotation.AuthController;
import cn.edu.zju.sishi.service.PictureService;
import cn.edu.zju.sishi.service.TagResourceService;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.websocket.server.PathParam;
import java.io.File;
import java.util.*;

@Slf4j
@Validated
@RestController
//@AuthController
public class PictureController {

    private static final String PICTURE_ID = "pictureId";
    private static final String PICTURE_FILE = "pictureFile";

    @Autowired
    private NginxConfig nginxConfig;

    @Autowired
    private PictureService pictureService;

    @Autowired
    private TagResourceService tagResourceService;

    @RequestMapping(value = "/pictures", method = RequestMethod.GET)
    public List<Picture> getPictureAll() {
        log.info("Start invoke getPictureAll()");
        return pictureService.getPictureAll();
    }

    @RequestMapping(value = "/picture/{pictureId}", method = RequestMethod.GET)
    public Picture getPictureById(@PathVariable("pictureId")
                                  @Size(min = 36, max = 36, message = "pictureId length should be 36")
                                          String pictureId) {
        log.info("Start invoke getPictureById()");
        return pictureService.getPictureById(pictureId);
    }

    @RequestMapping(value = "/pictures/ids", method = RequestMethod.POST)
    public List<Picture> getPictureByIds(@RequestBody
                                         @NotEmpty
                                                 List<@Size(min = 36, max = 36, message = "pictureId length should be 36") String> pictureIds) {
        log.info("Start invoke getPictureByIds()");
        return pictureService.getPictureByIds(pictureIds);
    }

    @RequestMapping(value = "/pictures/tagName", method = RequestMethod.GET)
    public List<Picture> getPictureByTag(@RequestParam("tagName")
                                         @NotNull(message = "tagName cannot be null")
                                         @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200")
                                                 String tagName) {
        log.info("Start invoke getPictureByTag()");
        return pictureService.getPictureByTag(tagName);
    }


    @RequestMapping(value = "/picture", method = RequestMethod.POST)
    public Picture addPicture(@RequestBody @Validated Picture picture, BindingResult bindingResult) {
        BindResultUtils.validData(bindingResult);

        log.info("Start invoke addPicture()");
        pictureService.addPicture(picture);

        return picture;
    }

    /**
     * 通过 form 表单上传图片文件，并保存至数据库
     *
     * @param request
     * @param pictureName(defalut)
     * @return
     */
    @RequestMapping(value = "/pictureFile", method = RequestMethod.POST, produces = {"application/json;charset=UTF-8"})
    public Picture addPictureFileByTagName(HttpServletRequest request,
                                           @NotNull(message = "tagName cannot be null")
                                           @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200")
                                                   String tagName,
                                           @RequestParam(value = "pictureName", required = false)
                                                   String pictureName) {
        log.info("Start invoke addPictureFileByTagName()");
        Picture picture = new Picture();

        try {
            MultipartHttpServletRequest multipartHttpServletRequest = ((MultipartHttpServletRequest) request);
            MultipartFile multipartFile = multipartHttpServletRequest.getFile(PICTURE_FILE);

            // 判断空指针的情况
            if (multipartFile == null || multipartFile.isEmpty()) {
                throw new ValidationException("请求不是表单格式，或者未上传文件对象");
            }

            // 文件名称加随机数处理，避免重名
            if (StringUtils.isEmpty(pictureName)) {
                picture.setPictureName(UUID.randomUUID().toString() + "-" + multipartFile.getOriginalFilename());
            } else {
                picture.setPictureName(UUID.randomUUID().toString() + "-" + pictureName);
            }

            // 保存到本地
            // TODO 需要将 windows 的路径改为 linux 的路径
            File localFile = new File(nginxConfig.getWinRoot() + nginxConfig.getPicPath() + picture.getPictureName());
            multipartFile.transferTo(localFile);

            // 保存资源记录
            log.info("Start invoke save record in db");
            picture.setPictureUrl(nginxConfig.getHttpHead() + nginxConfig.getPicPath() + picture.getPictureName());
            pictureService.addPicture(picture);

            // 保存 标签、资源 关联记录
            TagResource tagResource = new TagResource("", tagName, picture.getPictureId(), ResourceTypeEnum.PICTURE.getResourceType());
            tagResourceService.addTagResource(tagResource);
        } catch (Exception e) {
            log.error("addPicture Error", e);
            throw new ValidationException(e.toString());
        }

        return picture;
    }


    @RequestMapping(value = "/picture/{pictureId}", method = RequestMethod.DELETE)
    public Map<String, String> deletePictureById(@PathVariable("pictureId")
                                                 @Size(min = 36, max = 36, message = "pictureId length should be 36")
                                                         String pictureId) {
        log.info("Start invoke deletePictureById()");
        pictureService.deletePictureById(pictureId);

        Map<String, String> result = new HashMap<>();
        result.put(PICTURE_ID, pictureId);

        return result;
    }

    @RequestMapping(value = "/picture/{pictureId}/tagName/{tagName}", method = RequestMethod.DELETE)
    public Map<String, String> deletePictureByTagName(@PathVariable("pictureId")
                                                      @Size(min = 36, max = 36, message = "pictureId length should be 36")
                                                              String pictureId,
                                                      @PathVariable("tagName")
                                                      @NotNull(message = "tagName cannot be null")
                                                      @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200")
                                                              String tagName) {
        log.info("Start invoke deletePictureByTagName()");
        // 先删除资源关联表中的记录
        tagResourceService.deleteTagResource(tagName, pictureId);
        // 再删除资源表的记录
        pictureService.deletePictureById(pictureId);

        Map<String, String> result = new HashMap<>();
        result.put(PICTURE_ID, pictureId);

        return result;
    }

}
