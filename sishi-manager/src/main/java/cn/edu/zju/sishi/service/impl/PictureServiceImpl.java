package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.dao.PictureDao;
import cn.edu.zju.sishi.entity.Picture;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
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

    @Override
    public List<Picture> getPictureAll() {
        return pictureDao.getPictureAll();
    }

    @Override
    public Picture getPictureById(String pictureId) {
        return pictureDao.getPictureById(pictureId);
    }

    @Override
    public List<Picture> getPictureByIds(List<String> pictureIds) {
        return pictureDao.getPictureByIds(pictureIds);
    }

    @Override
    public List<Picture> getPictureByTag(String tagName) {
        return pictureDao.getPictureByTag(tagName);
    }

    @Override
    public int addPicture(Picture picture) {
        picture.setPictureId(UUID.randomUUID().toString());
        picture.setCreateTime(Instant.now().toEpochMilli());

        return pictureDao.addPicture(picture);
    }

    @Override
    public int deletePictureById(String pictureId) {
        if (pictureDao.getPictureById(pictureId) == null) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), String.format("pictureId %s does not exist!", pictureId));
        }

        return pictureDao.deletePictureById(pictureId);
    }
}
