package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.dao.PictureDao;
import cn.edu.zju.sishi.dao.TagDao;
import cn.edu.zju.sishi.entity.Picture;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.PictureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private TagDao tagDao;

    @Override
    public List<Picture> getPicturesAll(String startTime, String endTime) {
        return pictureDao.getPicturesAll(startTime, endTime);
    }

    @Override
    public Picture getPictureById(String pictureId) {
        if (pictureDao.getPictureById(pictureId) == null) {
            throw new ValidationException(String.format("picture %s does not exist!", pictureId));
        }

        return pictureDao.getPictureById(pictureId);
    }

    @Override
    public List<Picture> getPicturesByIds(List<String> pictureIds) {
        return pictureDao.getPicturesByIds(pictureIds);
    }

    @Override
    public Picture getPictureByTitle(String pictureTitle) {
        return pictureDao.getPictureByTitle(pictureTitle);
    }

    @Override
    public List<Picture> getPicturesByTag(String tagName) {
        if (tagDao.getTagByTagName(tagName) == null) {
            throw new ValidationException(String.format("TagName %s does not exist!", tagName));
        }

        return pictureDao.getPicturesByTag(tagName);
    }

    @Override
    public int addPicture(Picture picture) {
        if (pictureDao.getPictureByTitle(picture.getPictureTitle()) != null) {
            throw new ValidationException(String.format("picture %s already exist!", picture.getPictureTitle()));
        }

        picture.setPictureId(UUID.randomUUID().toString());
        picture.setPictureContent(picture.getPictureContent());
        picture.setPicturePublishTime(Instant.now().toEpochMilli());
        picture.setPictureCreateTime(Instant.now().toEpochMilli());

        return pictureDao.addPicture(picture);
    }

    @Override
    public int deletePictureById(String pictureId) {
        if (pictureDao.getPictureById(pictureId) == null) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), String.format("pictureId %s does not exist!", pictureId));
        }

        return pictureDao.deletePictureById(pictureId);
    }

    @Override
    public int updateIsPublicById(String pictureId) {
        return pictureDao.updateIsPublicById(pictureId);
    }
}
