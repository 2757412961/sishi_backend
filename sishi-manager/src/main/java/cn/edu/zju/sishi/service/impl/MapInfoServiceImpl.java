package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.dao.MapInfoDao;
import cn.edu.zju.sishi.entity.MapInfo;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.MapInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class MapInfoServiceImpl implements MapInfoService {

    @Autowired
    private MapInfoDao mapInfoDao;

    @Override
    public List<MapInfo> getAllMapInfo() {
        return mapInfoDao.getAllMapInfo();
    }

    @Override
    public MapInfo getMapInfoById(String mapId) {
        return mapInfoDao.getMapInfoById(mapId);
    }

    @Override
    public List<MapInfo> getMapInfoByIds(List<String> mapIds) {
        if (mapIds == null || mapIds.isEmpty()) {
            return null;
        }

        List<MapInfo> mapInfoList = new ArrayList<>();
        for (String mapId : mapIds) {
            MapInfo mapInfoEntity = mapInfoDao.getMapInfoById(mapId);
//            if (mapInfoEntity != null) {
            mapInfoList.add(mapInfoEntity);
//            } else {
//                throw new ValidationException(String.format("Mapinfo %s is not exist!", mapId));
//            }
        }

        return mapInfoList;
    }

    @Override
    public List<MapInfo> getMapInfoByTag(String tagName) {
        return mapInfoDao.getMapInfoByTag(tagName);
    }

    public int insertMapInfo(MapInfo mapInfoEntity) {
        mapInfoEntity.setMapId(UUID.randomUUID().toString());
        mapInfoEntity.setCreateTime(Instant.now().toEpochMilli());

        return mapInfoDao.addMapInfo(mapInfoEntity);
    }

    @Override
    public int addMapInfo(MapInfo mapInfoEntity) {
        String mapName = mapInfoEntity.getMapName();
        if (mapInfoDao.getMapInfoByName(mapName) != null) {
            throw new ValidationException(String.format("Mapinfo %s already exist!", mapName));
        }

        return insertMapInfo(mapInfoEntity);
    }

    @Override
    public int deleteMapInfoById(String mapId) {
        MapInfo mapInfoDelete = mapInfoDao.getMapInfoById(mapId);
        if (mapInfoDelete == null) {
            throw new ValidationException(String.format("Mapinfo %s is not exist!", mapId));
        }

        return mapInfoDao.deleteMapInfoById(mapId);
    }

    @Override
    public int updateMapInfo(MapInfo mapInfoEntity) {
        if (StringUtils.isEmpty(mapInfoEntity.getMapId())) {
            throw new ValidationException(String.format("Mapinfo is not exist!"));
        }

        String mapName = mapInfoEntity.getMapName();
        if (mapInfoDao.getMapInfoByName(mapName) != null) {
            throw new ValidationException(String.format("Mapinfo %s already exist!", mapName));
        }

        return mapInfoDao.updateMapInfo(mapInfoEntity);
    }
}
