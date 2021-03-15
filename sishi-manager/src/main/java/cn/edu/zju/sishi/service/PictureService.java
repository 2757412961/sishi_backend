package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.Picture;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PictureService {

    List<Picture> getPictureAll();

    Picture getPictureById(@Param("pictureId") String pictureId);

    List<Picture> getPictureByIds(@Param("pictureIds") List<String> pictureIds);

//    List<Picture> getPictureByPage(@Param("length") int length, @Param("offset") int offset);

    List<Picture> getPictureByTag(@Param("tagName") String tagName);

    int addPicture(Picture picture);

    int deletePictureById(@Param("pictureId") String pictureId);

}
