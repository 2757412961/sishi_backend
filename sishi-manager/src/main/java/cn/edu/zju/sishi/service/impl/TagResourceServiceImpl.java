package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.dao.TagDao;
import cn.edu.zju.sishi.dao.TagResourceDao;
import cn.edu.zju.sishi.entity.Tag;
import cn.edu.zju.sishi.entity.TagResource;
import cn.edu.zju.sishi.enums.ResourceTypeEnum;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.TagResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TagResourceServiceImpl implements TagResourceService {

    @Autowired
    private TagResourceDao tagResourceDao;

    @Autowired
    private TagDao tagDao;

    public void checkTagNameEmpty(String tagName) {
        if (StringUtils.isEmpty(tagName)) {
            throw new ValidationException(String.format("tagName '%s' is empty!", tagName));
//            throw new javax.validation.ValidationException(String.format("tagName '%s' is empty!", tagName));
        }
    }

    public void checkResourceIDEmpty(String resourceId) {
        if (StringUtils.isEmpty(resourceId)) {
            throw new ValidationException(String.format("resourceId '%s' is empty!", resourceId));
//            throw new javax.validation.ValidationException(String.format("resourceId '%s' is empty!", resourceId));
        }
    }

    public void checkResourceType(String resourceType) {
        if (!ResourceTypeEnum.hasResource(resourceType)) {
            throw new ValidationException(String.format("resourceType '%s' is empty!", resourceType));
//            throw new javax.validation.ValidationException(String.format("resourceType '%s' is empty!", resourceType));
        }
    }


    @Override
    public String getTagResourceType(String tagName, String resourceId) {
        checkTagNameEmpty(tagName);
        checkResourceIDEmpty(resourceId);

        return tagResourceDao.getTagResourceType(tagName, resourceId);
    }

    @Override
    public List<String> getTagResourceIds(String tagName, String resourceType) {
        checkTagNameEmpty(tagName);
        checkResourceType(resourceType);

        return tagResourceDao.getTagResourceIds(tagName, resourceType);
    }

    @Override
    public int addTagResource(TagResource tagResource) {
        checkTagNameEmpty(tagResource.getTagName());
        checkResourceIDEmpty(tagResource.getResourceId());
        checkResourceType(tagResource.getResourceType());

        Tag tag = tagDao.getTagByTagName(tagResource.getTagName());
        if (tag == null || StringUtils.isEmpty(tag.getTagId())) {
            throw new ValidationException(String.format("tagName '%s' does not exist!", tagResource.getTagName()));
//            throw new javax.validation.ValidationException(String.format("tagName '%s' does not exist!", tagResource.getTagName()));
        }
        tagResource.setTagId(tag.getTagId());

        if (!StringUtils.isEmpty(tagResourceDao.getTagResourceType(tagResource.getTagName(), tagResource.getResourceId()))) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), (String.format("tagResource record already exist!")));
//            throw new javax.validation.ValidationException((String.format("tagResource record already exist!")));
        }

        return tagResourceDao.addTagResource(tagResource);
    }

    @Override
    public int deleteTagResource(String tagName, String resourceId) {
        checkTagNameEmpty(tagName);
        checkResourceIDEmpty(resourceId);

        if (StringUtils.isEmpty(tagResourceDao.getTagResourceType(tagName, resourceId))) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), (String.format("tagResource record does not exist!")));
//            throw new javax.validation.ValidationException(String.format("tagResource record does not exist!"));
        }

        return tagResourceDao.deleteTagResource(tagName, resourceId);
    }

    @Override
    public int deleteByTagName(String tagName) {
        checkTagNameEmpty(tagName);

        return tagResourceDao.deleteByTagName(tagName);
    }
}
