package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.TagResource;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TagResourceDao {

    String getTagResourceType(@Param("tagName") String tagName, @Param("resourceId") String resourceId);

    List<String> getTagResourceIds(@Param("tagName") String tagName, @Param("resourceType") String resourceType);

    int addTagResource(TagResource tagResource);

    int deleteTagResource(@Param("tagName") String tagName, @Param("resourceId") String resourceId);

    int deleteByTagName(@Param("tagName") String tagName);

}
