package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.dao.TagDao;
import cn.edu.zju.sishi.entity.Tag;
import cn.edu.zju.sishi.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TagServiceImpl implements TagService {

    @Autowired
    private TagDao tagDao;
    
    @Cacheable(value = "tag")
    public List<Tag> getTag(){
        return tagDao.getTag();
    }
}
