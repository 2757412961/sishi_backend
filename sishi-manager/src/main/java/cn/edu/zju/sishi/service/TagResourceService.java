package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.TagResource;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TagResourceService {

    String getTagResourceType(String tagName, String resourceId);

    List<String> getTagResourceIds(String tagName, String resourceType);

    int addTagResource(TagResource tagResource);

    int deleteTagResource(String tagName, String resourceId);

    int deleteByTagName(String tagName);

}
