package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.MapInfo;

import java.util.List;

public interface MapInfoService {

    List<MapInfo> getAllMapInfos();

    MapInfo getMapInfoById(String mapId);

    List<MapInfo> getMapInfosByIds(List<String> mapIds);

    List<MapInfo> getMapInfosByTag(String tagName);

    int addMapInfo(MapInfo mapInfoEntity);

    int deleteMapInfoById(String mapId);

    int updateMapInfo(MapInfo mapInfoEntity);

}
