package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.Tag;

import java.util.List;

public interface TagService {
    List<Tag> selectTags();
    
    void addTag(Tag tag);
    
    void addTags(List<String> tags);
    
    void dropTag(String tagName);
}
