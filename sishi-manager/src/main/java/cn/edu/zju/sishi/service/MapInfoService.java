package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.MapInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MapInfoService {

    List<MapInfo> getAllMapInfo();

    MapInfo getMapInfoById(String mapId);

    List<MapInfo> getMapInfoByIds(List<String> mapIds);

    List<MapInfo> getMapInfoByTag(String tagName);

    int addMapInfo(MapInfo mapInfoEntity);

    int deleteMapInfoById(String mapId);

    int updateMapInfo(MapInfo mapInfoEntity);

}
