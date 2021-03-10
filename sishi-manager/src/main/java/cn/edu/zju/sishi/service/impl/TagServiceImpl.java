package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.dao.TagDao;
import cn.edu.zju.sishi.entity.Tag;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TagServiceImpl implements TagService {

  @Autowired
  private TagDao tagDao;

  @Override
  @Cacheable(value = "SELECT_TAGS")
  public List<Tag> selectTags() {
    return tagDao.selectTags();
  }

  @Override
  public void addTag(Tag tagEntity) {
    if (tagDao.getTagByTagName(tagEntity.getTagName()) != null) {
      throw new ValidationException(String.format("Tag %s already exist!", tagEntity.getTagName()));
    }
    insertTag(tagEntity);
  }

  @Override
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
  public void dropTag(String tagName) {
    if (tagDao.getTagByTagName(tagName) == null) {
      throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), String.format("Tag %s does not exist!", tagName));
    }
    tagDao.dropTag(tagName);
  }
}
