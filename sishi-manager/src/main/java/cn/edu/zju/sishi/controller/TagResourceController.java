package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.entity.TagResource;
import cn.edu.zju.sishi.entity.vo.MediaItem;
import cn.edu.zju.sishi.enums.ResourceTypeEnum;
import cn.edu.zju.sishi.passport.annotation.AuthController;
import cn.edu.zju.sishi.service.TagResourceService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@AuthController
public class TagResourceController {

    @Autowired
    private TagResourceService tagResourceService;

    @RequestMapping(value = "tagResources", method = RequestMethod.GET)
    public JSONObject getTagResourcesAll() {
        log.info("Start invoke getTagResourcesAll()");
        List<TagResource> tagResources = tagResourceService.getTagResourcesAll();

        JSONObject result = new JSONObject();
        result.put("totalCount", tagResources.size());
        result.put("tagResources", tagResources);

        return result;
    }

    @RequestMapping(value = "tagResources/{resourceId}", method = RequestMethod.GET)
    public JSONObject getTagResourcesByResourceId(@PathVariable("resourceId")
                                                  @Size(min = 36, max = 36, message = "resourceId length should be 36") String resourceId) {
        log.info("Start invoke getTagResourcesByResourceId()");
        List<TagResource> tagResources = tagResourceService.getTagResourcesByResourceId(resourceId);

        JSONObject result = new JSONObject();
        result.put("totalCount", tagResources.size());
        result.put("tagResources", tagResources);

        return result;
    }

    @RequestMapping(value = "tagResources/user/{userId}", method = RequestMethod.GET)
    public JSONObject getTagResourcesByUserId(@PathVariable("userId")
                                                  @Size(min = 36, max = 36, message = "userId length should be 36") String userId) {
        log.info("Start invoke getTagResourcesByUserId()");
        List<TagResource> tagResources = tagResourceService.getTagResourcesByUserId(userId);

        JSONObject result = new JSONObject();
        result.put("totalCount", tagResources.size());
        result.put("tagResources", tagResources);

        return result;
    }

    @RequestMapping(value = "tagResources/tagName/{tagName}", method = RequestMethod.GET)
    public JSONObject getTagResourcesByTagName(@PathVariable("tagName")
                                               @NotNull(message = "tagName cannot be null")
                                               @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName) {
        log.info("Start invoke getTagResourcesByTagName()");
        List<TagResource> tagResources = tagResourceService.getTagResourcesByTagName(tagName);

        JSONObject result = new JSONObject();
        result.put("totalCount", tagResources.size());
        result.put("tagResources", tagResources);

        return result;
    }

    @RequestMapping(value = "tagResource/mediaItems", method = RequestMethod.GET)
    public List<MediaItem> getMediaItemsByResourceType() {
        log.info("Start invoke getMediaItemsByResourceType()");
        List<MediaItem> results = new ArrayList<>();

        results.addAll(tagResourceService.getMediaItemsByResourceType(ResourceTypeEnum.PICTURE.getResourceType()));
        results.addAll(tagResourceService.getMediaItemsByResourceType(ResourceTypeEnum.AUDIO.getResourceType()));
        results.addAll(tagResourceService.getMediaItemsByResourceType(ResourceTypeEnum.VIDEO.getResourceType()));

        return results;
    }

}
