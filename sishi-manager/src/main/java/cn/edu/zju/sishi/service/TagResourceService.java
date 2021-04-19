package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.TagResource;
import cn.edu.zju.sishi.entity.vo.MediaItem;

import java.util.List;

public interface TagResourceService {

    List<TagResource> getTagResourcesAll();

    List<TagResource> getTagResourcesByResourceId(String resourceId);

    List<TagResource> getTagResourcesByUserId(String userId);

    List<TagResource> getTagResourcesByTagName(String tagName);

    String getTagResourceType(String tagName, String resourceId);

    List<String> getTagResourceIds(String tagName, String resourceType);

    List<MediaItem> getMediaItemsByResourceType(String resourceType);

    int addTagResource(TagResource tagResource);

    int deleteTagResource(String tagName, String resourceId);

    int deleteByTagName(String tagName);

}
