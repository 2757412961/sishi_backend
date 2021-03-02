package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface TagDao {
    List<Tag> getTag();
}
