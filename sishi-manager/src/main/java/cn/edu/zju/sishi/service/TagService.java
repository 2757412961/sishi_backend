package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.Tag;
import cn.edu.zju.sishi.entity.vo.TagCompareTime;
import cn.edu.zju.sishi.entity.vo.TagTree;

import java.util.List;
import java.util.Set;

public interface TagService {
    List<Tag> selectTags();

    Tag getTagByTagName(String tagName);

    List<TagTree> getTagTree();

    Set<String> getChildTag(String tagName);

    List<TagCompareTime> getTagCompareTime(String tagName);

    void addTag(Tag tag);

    void addTags(List<String> tags);

    void dropTag(String tagName);
}
