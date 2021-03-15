package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.Tag;
import cn.edu.zju.sishi.entity.vo.TagTree;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface TagService {
    List<Tag> selectTags();

    Tag getTagByTagName(String tagName);

    List<TagTree> getTagTree();

    Set<String> getChildTag(String tagName);

    void addTag(Tag tag);

    void addTags(List<String> tags);

    void dropTag(String tagName);
}
