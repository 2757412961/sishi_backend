package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.List;

@Mapper
@Component
public interface PictureDao {

    List<Picture> getPictureAll();

    Picture getPictureById(@Param("pictureId") String pictureId);

    List<Picture> getPictureByIds(@Param("pictureIds") List<String> pictureIds);

    List<Picture> getPictureByPage(@Param("length") int length, @Param("offset") int offset);

    List<Picture> getPictureByTag(@Param("tagName") String tagName);

    int addPicture(Picture picture);

    int deletePictureById(@Param("pictureId") String pictureId);

}
