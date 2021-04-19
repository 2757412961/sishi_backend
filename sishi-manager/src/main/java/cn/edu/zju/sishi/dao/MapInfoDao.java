package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.MapInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper
@Component
public interface MapInfoDao {

    List<MapInfo> getAllMapInfos(@Param("logicSymbol") String logicSymbol);

    MapInfo getMapInfoById(@Param("mapId") String mapId);

    MapInfo getMapInfoByTitle(@Param("mapTitle") String mapTitle);

    List<MapInfo> getMapInfosByTag(@Param("tagName") String tagName,
                                   @Param("logicSymbol") String logicSymbol);

    int addMapInfo(MapInfo mapInfoEntity);

    int deleteMapInfoById(@Param("mapId") String mapId);

    int updateMapInfo(MapInfo mapInfoEntity);

    int updateIsPublicById(@Param("mapId") String mapId);


}
