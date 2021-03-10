package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.commons.utils.BindResultUtils;
import cn.edu.zju.sishi.entity.MapInfo;
import cn.edu.zju.sishi.passport.annotation.AuthController;
import cn.edu.zju.sishi.service.MapInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController
public class MapInfoController {

    private final String MAP_ID = "mapId";

    @Autowired
    private MapInfoService mapInfoService;

    @RequestMapping(value = "mapinfoAll", method = RequestMethod.GET)
    public List<MapInfo> getAllMapInfo() {
        log.info("Start invoke getAllMapInfo()");

        return mapInfoService.getAllMapInfo();
    }

    @RequestMapping(value = "mapinfo/{mapId}", method = RequestMethod.GET)
    public MapInfo getMapInfoById(@PathVariable(value = "mapId") @NotNull(message = "mapId can not be null") String mapId) {
        log.info("Start invoke getMapInfo()");

        return mapInfoService.getMapInfoById(mapId);
    }

    @RequestMapping(value = "mapinfoList", method = RequestMethod.POST)
    public List<MapInfo> getMapInfoByIds(@RequestBody @NotEmpty(message = "mapIds can not be empty") List<String> mapIds) {
        log.info("Start invoke getMapInfos()");

        List<MapInfo> mapInfos = new ArrayList<>();
        for (String mapId : mapIds) {
            mapInfos.add(mapInfoService.getMapInfoById(mapId));
        }

        return mapInfos;
    }

    @RequestMapping(value = "mapinfoTag", method = RequestMethod.GET)
    public List<MapInfo> getMapInfoByTag(@NotNull(message = "tagName cannot be null") @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName) {
        log.info("Start invoke getMapInfoByTag()");

        return mapInfoService.getMapInfoByTag(tagName);
    }


    @RequestMapping(value = "mapinfo", method = RequestMethod.POST)
    public Map<String, String> addMapInfo(@RequestBody @Validated MapInfo mapInfo, BindingResult bindingResult) {
        BindResultUtils.validData(bindingResult);

        log.info("Start invoke addMapInfo()");
        mapInfoService.addMapInfo(mapInfo);

        Map<String, String> result = new HashMap<>();
        result.put(MAP_ID, mapInfo.getMapId());

        return result;
    }


    @RequestMapping(value = "mapinfo/{mapId}", method = RequestMethod.DELETE)
    public Map<String, String> deleteMapInfo(@PathVariable(value = "mapId") @NotNull(message = "mapId can not be null") String mapId) {
        log.info("Start invoke deleteMapInfo()");
        mapInfoService.deleteMapInfoById(mapId);

        Map<String, String> result = new HashMap<>();
        result.put(MAP_ID, mapId);

        return result;
    }


    @RequestMapping(value = "mapinfo", method = RequestMethod.PUT)
    public Map<String, String> updateMapInfo(@RequestBody MapInfo mapInfo) {
        log.info("Start invoke updateMapInfo()");
        mapInfoService.updateMapInfo(mapInfo);

        Map<String, String> result = new HashMap<>();
        result.put(MAP_ID, mapInfo.getMapId());

        return result;
    }

}
