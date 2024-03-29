package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.config.NginxConfig;
import cn.edu.zju.sishi.dao.*;
import cn.edu.zju.sishi.entity.*;
import cn.edu.zju.sishi.entity.vo.MediaItem;
import cn.edu.zju.sishi.enums.ResourceTypeEnum;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.TagResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static cn.edu.zju.sishi.enums.ResourceTypeEnum.getTypeByValue;
import static cn.edu.zju.sishi.enums.ResourceTypeEnum.hasResource;

@Service
public class TagResourceServiceImpl implements TagResourceService {

    @Autowired
    private TagResourceDao tagResourceDao;

    @Autowired
    private TagDao tagDao;

    @Autowired
    private ArticleDao articleDao;
    @Autowired
    private MapInfoDao mapInfoDao;
    @Autowired
    private QuestionDao questionDao;
    @Autowired
    private AudioDao audioDao;
    @Autowired
    private VideoDao videoDao;
    @Autowired
    private PictureDao pictureDao;

    @Autowired
    private NginxConfig nginxConfig;

    public void checkTagNameEmpty(String tagName) {
        if (StringUtils.isEmpty(tagName)) {
            throw new ValidationException(String.format("tagName '%s' is empty!", tagName));
        }
    }

    public void checkResourceIDEmpty(String resourceId) {
        if (StringUtils.isEmpty(resourceId)) {
            throw new ValidationException(String.format("resourceId '%s' is empty!", resourceId));
        }
    }

    public void checkResourceType(String resourceType) {
        if (!hasResource(resourceType)) {
            throw new ValidationException(String.format("resourceType '%s' is empty!", resourceType));
        }
    }

    @Override
    public List<TagResource> getTagResourcesAll() {
        return tagResourceDao.getTagResourcesAll();
    }

    @Override
    public List<TagResource> getTagResourcesByResourceId(String resourceId) {
        return tagResourceDao.getTagResourcesByResourceId(resourceId);
    }

    @Override
    public List<TagResource> getTagResourcesByUserId(String userId) {
        return tagResourceDao.getTagResourcesByUserId(userId);
    }

    @Override
    public List<TagResource> getTagResourcesByTagName(String tagName) {
        return tagResourceDao.getTagResourcesByTagName(tagName);
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
    public List<MediaItem> getMediaItemsByResourceType(String resourceType) {
        List<MediaItem> results = new ArrayList<>();

        List<TagResource> tagResources = tagResourceDao.getTagResourceByResourceType(resourceType);

        ResourceTypeEnum typeByValue = getTypeByValue(resourceType);
        for (TagResource tagResource : tagResources) {
            String resourceId = tagResource.getResourceId();
            String name = "";
            String type = "";
            String url = "";

            switch (typeByValue) {
                default:
                    break;
                case VIDEO:
                    Video video = videoDao.getVideo(resourceId);
                    if (video == null) break;

                    name = video.getVideoTitle();
                    type = "VIDEO";
                    url = video.getVideoContent();
                    break;
                case AUDIO:
                    Audio audio = audioDao.getAudio(resourceId);
                    if (audio == null) break;

                    name = audio.getAudioTitle();
                    type = "AUDIO";
                    url = audio.getAudioContent();
                    break;
                case PICTURE:
                    Picture picture = pictureDao.getPictureById(resourceId);
                    if (picture == null) break;

                    name = picture.getPictureTitle();
                    type = "IMAGE";
                    url = picture.getPictureContent();
                    break;
            }

            if (StringUtils.isEmpty(url)) {
                continue;
            }

            results.add(new MediaItem(resourceId, name, type, url));
        }

        return results;
    }

    @Override
    public int addTagResource(TagResource tagResource) {
        checkTagNameEmpty(tagResource.getTagName());
        checkResourceIDEmpty(tagResource.getResourceId());
        checkResourceType(tagResource.getResourceType());

        Tag tag = tagDao.getTagByTagName(tagResource.getTagName());
        if (tag == null || StringUtils.isEmpty(tag.getTagId())) {
            throw new ValidationException(String.format("tagName '%s' does not exist!", tagResource.getTagName()));
        }
        tagResource.setTagId(tag.getTagId());

        if (!StringUtils.isEmpty(tagResourceDao.getTagResourceType(tagResource.getTagName(), tagResource.getResourceId()))) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), (String.format("tagResource record already exist!")));
        }

        return tagResourceDao.addTagResource(tagResource);
    }

    @Override
    public int deleteTagResource(String tagName, String resourceId) {
        checkTagNameEmpty(tagName);
        checkResourceIDEmpty(resourceId);

        if (StringUtils.isEmpty(tagResourceDao.getTagResourceType(tagName, resourceId))) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), (String.format("tagResource record does not exist!")));
        }

        return tagResourceDao.deleteTagResource(tagName, resourceId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public int deleteByTagName(String tagName) {
        checkTagNameEmpty(tagName);
        List<TagResource> resources = tagResourceDao.getTagResourcesByTagName(tagName);

        for (TagResource resource : resources) {
            try {
                String resourceId = resource.getResourceId();
                String resourceType = resource.getResourceType();
                ResourceTypeEnum resourceEnum = getTypeByValue(resourceType);
                switch (resourceEnum) {
                    default:
                        break;
                    case ARTICLE:
                        articleDao.dropArticle(resourceId);
                        break;
                    case AUDIO:
                        Audio audio = audioDao.getAudio(resourceId);
                        if (audio != null) {
                            File localFile = new File(nginxConfig.getLinuxRoot() + audio.getAudioContent().substring(nginxConfig.getHttpHead().length()));
                            if (localFile.exists()) {
                                localFile.delete();
                            }
                        }
                        audioDao.dropAudio(resourceId);
                        break;
                    case PICTURE:
                        Picture picture = pictureDao.getPictureById(resourceId);
                        if (picture != null) {
                            File localFile = new File(nginxConfig.getLinuxRoot() + picture.getPictureContent().substring(nginxConfig.getHttpHead().length()));
                            if (localFile.exists()) {
                                localFile.delete();
                            }
                        }
                        pictureDao.deletePictureById(resourceId);
                        break;
                    case VIDEO:
                        Video video = videoDao.getVideo(resourceId);
                        if (video != null) {
                            File localFile = new File(nginxConfig.getLinuxRoot() + video.getVideoContent().substring(nginxConfig.getHttpHead().length()));
                            if (localFile.exists()) {
                                localFile.delete();
                            }
                        }
                        videoDao.dropVideo(resourceId);
                        break;
                    case MAPINFO:
                        mapInfoDao.deleteMapInfoById(resourceId);
                        break;
                    case QUESTION:
                        questionDao.deleteQuesByID(resourceId);
                        break;
                }
            } catch (Exception e) {
                throw new ValidationException(String.format("删除标签失败!" + e.getMessage()));
            }
        }

        return tagResourceDao.deleteByTagName(tagName);
    }
}
