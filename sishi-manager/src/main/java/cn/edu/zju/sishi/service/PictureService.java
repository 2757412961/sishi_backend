package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.Picture;

import java.util.List;

public interface PictureService {

    List<Picture> getPicturesAll(String logicSymbol);

    Picture getPictureById(String pictureId);

    List<Picture> getPicturesByIds(List<String> pictureIds);

//    List<Picture> getPicturesByPage(@Param("length") int length, @Param("offset") int offset);

    Picture getPictureByTitle(String pictureTitle);

    List<Picture> getPicturesByTag(String tagName, String logicSymbol);

    int addPicture(Picture picture);

    int deletePictureById(String pictureId);

    int updateIsPublicById(String pictureId);

}
