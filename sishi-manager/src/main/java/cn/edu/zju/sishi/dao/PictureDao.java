package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.Picture;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface PictureDao {

    List<Picture> getPicturesAll(@Param("logicSymbol") String logicSymbol);

    Picture getPictureById(@Param("pictureId") String pictureId);

    List<Picture> getPicturesByIds(@Param("pictureIds") List<String> pictureIds);

    Picture getPictureByTitle(@Param("pictureTitle") String pictureTitle);

    List<Picture> getPicturesByPage(@Param("length") int length, @Param("offset") int offset);

    List<Picture> getPicturesByTag(@Param("tagName") String tagName, @Param("logicSymbol") String logicSymbol);

    int addPicture(Picture picture);

    int deletePictureById(@Param("pictureId") String pictureId);

    int updateIsPublicById(@Param("pictureId") String pictureId);

}
