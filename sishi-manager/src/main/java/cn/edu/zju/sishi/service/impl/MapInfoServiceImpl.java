package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.dao.MapInfoDao;
import cn.edu.zju.sishi.dao.TagDao;
import cn.edu.zju.sishi.entity.MapInfo;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.MapInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class MapInfoServiceImpl implements MapInfoService {

    @Autowired
    private MapInfoDao mapInfoDao;

    @Autowired
    private TagDao tagDao;

    @Override
//    @Cacheable(value = "GET_ALL_MAPINFO")
    public List<MapInfo> getAllMapInfos(String logicSymbol) {
        return mapInfoDao.getAllMapInfos(logicSymbol);
    }

    @Override
//    @Cacheable(value = "GET_MAPINFO_BY_ID")
    public MapInfo getMapInfoById(String mapId) {
        MapInfo mapInfo = mapInfoDao.getMapInfoById(mapId);
        if (mapInfo == null) {
            throw new ValidationException(String.format("Mapinfo %s does not exist!", mapId));
        }

        return mapInfo;
    }

    @Override
//    @Cacheable(value = "GET_MAPINFO_BY_IDS")
    public List<MapInfo> getMapInfosByIds(List<String> mapIds) {
        if (CollectionUtils.isEmpty(mapIds)) {
            return null;
        }

        List<MapInfo> mapInfoList = new ArrayList<>();
        for (String mapId : mapIds) {
            MapInfo mapInfoEntity = mapInfoDao.getMapInfoById(mapId);
            if (mapInfoEntity != null) {
                mapInfoList.add(mapInfoEntity);
            } else {
                throw new ValidationException(String.format("Mapinfo %s does not exist!", mapId));
            }
        }

        return mapInfoList;
    }

    @Override
//    @Cacheable(value = "GET_MAPINFO_BY_TAG")
    public List<MapInfo> getMapInfosByTag(String tagName, String logicSymbol) {
        if (tagDao.getTagByTagName(tagName) == null) {
            throw new ValidationException(String.format("TagName %s does not exist!", tagName));
        }

        return mapInfoDao.getMapInfosByTag(tagName, logicSymbol);
    }

    @Override
    @CacheEvict(value = {"SELECT_TAGS", "GET_TAG_BY_TAG_NAME", "GET_TAG_TREE", "GET_CHILD_TAG", "GET_TAG_COMPARE_TIME"}, allEntries = true)
    public int addMapInfo(MapInfo mapInfoEntity) {
        String mapName = mapInfoEntity.getMapTitle();
        if (mapInfoDao.getMapInfoByTitle(mapName) != null) {
            throw new ValidationException(String.format("Mapinfo %s already exist!", mapName));
        }

        mapInfoEntity.setMapId(UUID.randomUUID().toString());
        mapInfoEntity.setMapPublishTime(Instant.now().toEpochMilli());
        mapInfoEntity.setMapCreateTime(Instant.now().toEpochMilli());

        return mapInfoDao.addMapInfo(mapInfoEntity);
    }

    @Override
    @CacheEvict(value = {"SELECT_TAGS", "GET_TAG_BY_TAG_NAME", "GET_TAG_TREE", "GET_CHILD_TAG", "GET_TAG_COMPARE_TIME"}, allEntries = true)
    public int deleteMapInfoById(String mapId) {
        MapInfo mapInfoDelete = mapInfoDao.getMapInfoById(mapId);
        if (mapInfoDelete == null) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), String.format("Mapinfo %s does not exist!", mapId));
        }

        return mapInfoDao.deleteMapInfoById(mapId);
    }

    @Override
    public int updateMapInfo(MapInfo mapInfoEntity) {
        if (StringUtils.isEmpty(mapInfoEntity.getMapId())) {
            throw new ValidationException(String.format("Mapinfo % does not exist!", mapInfoEntity.getMapId()));
        }

        String mapName = mapInfoEntity.getMapTitle();
        if (mapInfoDao.getMapInfoByTitle(mapName) != null) {
            throw new ValidationException(String.format("Mapinfo Name %s already exist!", mapName));
        }

        return mapInfoDao.updateMapInfo(mapInfoEntity);
    }

    @Override
    public int updateIsPublicById(String mapId) {
        return mapInfoDao.updateIsPublicById(mapId);
    }
}
