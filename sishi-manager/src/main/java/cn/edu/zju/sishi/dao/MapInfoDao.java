package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.MapInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Mapper
@Component
public interface MapInfoDao {

    List<MapInfo> getAllMapInfo();

    MapInfo getMapInfoById(@Param("mapId") String mapId);

    MapInfo getMapInfoByName(@Param("mapName") String mapName);

    List<MapInfo> getMapInfoByTag(@Param("tagName") String tagName);

    int addMapInfo(MapInfo mapInfoEntity);

    int deleteMapInfoById(@Param("mapId") String mapId);

    int updateMapInfo(MapInfo mapInfoEntity);

}
