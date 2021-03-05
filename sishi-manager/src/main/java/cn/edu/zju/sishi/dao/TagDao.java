package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TagDao {
  List<Tag> selectTags();
  
  void addTag(@Param("tag") Tag tagEntity);
  
  Tag getTagByTagName(@Param("tagName") String tagName);
  
  void dropTag(@Param("tagName") String tagName);
}
