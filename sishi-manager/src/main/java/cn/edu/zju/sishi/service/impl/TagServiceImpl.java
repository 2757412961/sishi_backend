package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.commons.utils.LogicUtil;
import cn.edu.zju.sishi.dao.TagDao;
import cn.edu.zju.sishi.entity.MapInfo;
import cn.edu.zju.sishi.entity.Picture;
import cn.edu.zju.sishi.entity.Tag;
import cn.edu.zju.sishi.entity.vo.TagCompareTime;
import cn.edu.zju.sishi.entity.vo.TagTree;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.MapInfoService;
import cn.edu.zju.sishi.service.PictureService;
import cn.edu.zju.sishi.service.TagResourceService;
import cn.edu.zju.sishi.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

    private static String DEFAULT_PICTURE_URL = "http://10.13.42.137/media/picture/default_pic.jpg";

    @Autowired
    private TagDao tagDao;

    @Autowired
    private TagResourceService tagResourceService;

    @Autowired
    private MapInfoService mapInfoService;

    @Autowired
    private PictureService pictureService;

    @Override
    @Cacheable(value = "SELECT_TAGS")
    public List<Tag> selectTags() {
        return tagDao.selectTags();
    }

    @Override
    @Cacheable(value = "GET_TAG_BY_TAG_NAME")
    public Tag getTagByTagName(String tagName) {
        Tag tag = tagDao.getTagByTagName(tagName);
        if (tag == null) {
            throw new ValidationException(String.format("Tag %s does not exist!", tagName));
        }

        return tag;
    }

    /**
     * 按照 value 找到集合中的对象，返回 Index
     *
     * @param tagTrees
     * @param value
     * @return
     */
    public int getTagTreeIndexByValue(List<TagTree> tagTrees, String value) {
        int res = -1;

        for (int i = 0; i < tagTrees.size(); i++) {
            if (tagTrees.get(i).getValue().equals(value)) {
                return i;
            }
        }

        return res;
    }

    @Override
    @Cacheable(value = "GET_TAG_TREE")
    public List<TagTree> getTagTree() {
        List<Tag> tags = tagDao.selectTags();

        List<TagTree> results = new ArrayList<>();
        for (Tag tag : tags) {
            List<TagTree> root = results;
            String[] ts = tag.getTagName().split("@");
            for (int i = 0; i < ts.length; i++) {
                String t = ts[i];
                int index = getTagTreeIndexByValue(root, t);
                if (index >= 0) {
                    root = root.get(index).getChildren();
                } else {
                    TagTree newTagTree = new TagTree();
                    newTagTree.setValue(t);
                    newTagTree.setLabel(t);
                    // 添加标签路径、添加地理属性
                    if (i == ts.length - 1) {
                        newTagTree.setTagId(tag.getTagId());
                        newTagTree.setTagName(tag.getTagName());
                        newTagTree.setTime(tag.getEventTime());

                        List<MapInfo> mapInfos = mapInfoService.getMapInfosByTag(tag.getTagName(), LogicUtil.getLogicByIsAdmins(true));
                        if (!mapInfos.isEmpty()) {
                            newTagTree.setGeoCoordinates(
                                    new ArrayList<>(Arrays.asList
                                            (mapInfos.get(0).getMapLon(), mapInfos.get(0).getMapLat())));
                        }
                    }

                    root.add(newTagTree);
                    root = root.get(root.size() - 1).getChildren();
                }
            }
        }

        return results;
    }

    @Override
    @Cacheable(value = "GET_CHILD_TAG")
    public Set<String> getChildTag(String tagName) {
        if (tagDao.getTagByTagName(tagName) == null) {
            throw new ValidationException(String.format("Tag %s does not exist!", tagName));
        }

        Set<String> results = new HashSet<>();

        List<Tag> tagList = tagDao.getTagsByPrefix(tagName);
        for (Tag tag : tagList) {
            String[] split = tag.getTagName().substring(tagName.length()).split("@");
            if (split.length >= 2) {
                results.add(split[1]);
            }
        }

        return results;
    }

    @Override
    @Cacheable(value = "GET_TAG_COMPARE_TIME")
    public List<TagCompareTime> getTagCompareTime(String tagName) {
        List<TagCompareTime> results = new ArrayList<>();
//        List<Tag> tagList = tagDao.getTagsByPrefix(tagName);
//        // 遍历 Tag
//        for (Tag tag : tagList) {
//            List<MapInfo> mapInfos = mapInfoService.getMapInfosByTag(tag.getTagName(), LogicUtil.getLogicByIsAdmins(true));
//            if (!mapInfos.isEmpty() && !StringUtils.isEmpty(tag.getEventTime())) {
//                TagCompareTime tagCompareTime = new TagCompareTime();
//
//                // 截取标签的剩余内容
//                String[] split = tag.getTagName().substring(tagName.length()).split("@", 2);
//                if (split.length >= 2) {
//                    tagCompareTime.setLabel(split[1]);
//                    tagCompareTime.setValue(split[1]);
//                }
//
//                tagCompareTime.setTagId(tag.getTagId());
//                tagCompareTime.setTagName(tag.getTagName());
//                tagCompareTime.setTime(tag.getEventTime());
//                tagCompareTime.setProperty(tag.getProperty());
//                if (!mapInfo.getIsPoint() && null != mapInfo.getBoundary()) {
//                    tagCompareTime.setBoundry(convertString2List(mapInfo.getBoundary()));
//                    tagCompareTime.setPoint(false);
//                } else {
//                    tagCompareTime.setPoint(true);
//                    for (int i = 0; i < mapInfos.size(); i++) {
//                        ArrayList arrayList = new ArrayList<Double>();
//                        arrayList.add(mapInfos.get(i).getMapLon());
//                        arrayList.add(mapInfos.get(i).getMapLat());
//                        tagCompareTime.getGeoCoordinates().add(arrayList);
//                    }
//                }
//                // 添加图片 url
//                List<Picture> pictures = pictureService.getPicturesByTag(tag.getTagName(), LogicUtil.getLogicByIsAdmins(true));
//                if (!pictures.isEmpty()) {
//                    Picture picture = pictures.get(0);
//                    tagCompareTime.setPicUrl(picture.getPictureContent());
//                } else {
//                    tagCompareTime.setPicUrl(DEFAULT_PICTURE_URL);
//                }
//                results.add(tagCompareTime);
//            }
//        }
//
//        // 按照时间排顺序
//        Collections.sort(results);

        return results;
    }

    private List<String> convertString2List(String boundary) {
        if (null == boundary) {
            return Collections.emptyList();
        }
        boundary = boundary.trim();
        if (boundary.equals("[]") || boundary.equals("")) {
            return Collections.emptyList();
        }
        boundary = boundary.substring(1, boundary.length() - 1);
        return Arrays.stream(boundary.split(",")).collect(Collectors.toList());
    }

    @Override
    @CacheEvict(value = {"SELECT_TAGS", "GET_TAG_BY_TAG_NAME", "GET_TAG_TREE", "GET_CHILD_TAG", "GET_TAG_COMPARE_TIME"}, allEntries = true)
    public void addTag(Tag tagEntity) {
        if (tagDao.getTagByTagName(tagEntity.getTagName()) != null) {
            throw new ValidationException(String.format("Tag %s already exist!", tagEntity.getTagName()));
        }
        insertTag(tagEntity);
    }

    @Override
    @CacheEvict(value = {"SELECT_TAGS", "GET_TAG_BY_TAG_NAME", "GET_TAG_TREE", "GET_CHILD_TAG", "GET_TAG_COMPARE_TIME"}, allEntries = true)
    public void addTags(List<String> tags) {
        for (String tagName : tags) {
            if (tagDao.getTagByTagName(tagName) != null) {
                throw new ValidationException(String.format("Tag %s already exist!", tagName));
            }
            Tag tagEntity = new Tag();
            tagEntity.setTagName(tagName);
            insertTag(tagEntity);
        }
    }

    private void insertTag(Tag tag) {
        final String uuid = UUID.randomUUID().toString();
        tag.setTagId(uuid);
        tagDao.addTag(tag);
    }

    @Override
    @CacheEvict(value = {"SELECT_TAGS", "GET_TAG_BY_TAG_NAME", "GET_TAG_TREE", "GET_CHILD_TAG", "GET_TAG_COMPARE_TIME"}, allEntries = true)
    public void dropTag(String tagName) {
        if (tagDao.getTagByTagName(tagName) == null) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), String.format("Tag %s does not exist!", tagName));
        }
        tagResourceService.deleteByTagName(tagName);
        tagDao.dropTag(tagName);
    }
}
