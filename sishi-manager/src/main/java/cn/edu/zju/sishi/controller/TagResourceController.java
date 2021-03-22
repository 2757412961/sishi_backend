package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.entity.vo.MediaItem;
import cn.edu.zju.sishi.enums.ResourceTypeEnum;
import cn.edu.zju.sishi.passport.annotation.AuthController;
import cn.edu.zju.sishi.service.TagResourceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
//@AuthController
public class TagResourceController {

    @Autowired
    private TagResourceService tagResourceService;

    @RequestMapping(value = "tagResource/mediaItems", method = RequestMethod.GET)
    public List<MediaItem> getMediaItemsByResourceType() {
        List<MediaItem> results = new ArrayList<>();

        results.addAll(tagResourceService.getMediaItemsByResourceType(ResourceTypeEnum.PICTURE.getResourceType()));
        results.addAll(tagResourceService.getMediaItemsByResourceType(ResourceTypeEnum.AUDIO.getResourceType()));
        results.addAll(tagResourceService.getMediaItemsByResourceType(ResourceTypeEnum.VIDEO.getResourceType()));

        return results;
    }

}
