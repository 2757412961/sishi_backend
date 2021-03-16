package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.commons.utils.BindResultUtils;
import cn.edu.zju.sishi.entity.MapInfo;
import cn.edu.zju.sishi.entity.TagResource;
import cn.edu.zju.sishi.enums.ResourceTypeEnum;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.passport.annotation.AuthController;
import cn.edu.zju.sishi.service.MapInfoService;
import cn.edu.zju.sishi.service.TagResourceService;
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
//@Validated
@RestController
//@AuthController
public class MapInfoController {

    private final String MAP_ID = "mapId";

    @Autowired
    private MapInfoService mapInfoService;

    @Autowired
    private TagResourceService tagResourceService;

    @RequestMapping(value = "mapinfos", method = RequestMethod.GET)
    public List<MapInfo> getAllMapInfo() {
        log.info("Start invoke getAllMapInfo()");
        return mapInfoService.getAllMapInfos();
    }

    @RequestMapping(value = "mapinfo/{mapId}", method = RequestMethod.GET)
    public MapInfo getMapInfoById(@PathVariable(value = "mapId")
        @Size(min = 36, max = 36, message = "mapId length should be 36") String mapId) {
        log.info("Start invoke getMapInfoById()");
        return mapInfoService.getMapInfoById(mapId);
    }

    @RequestMapping(value = "mapinfos/ids", method = RequestMethod.POST)
    public List<MapInfo> getMapInfoByIds(@RequestBody
        @NotEmpty(message = "mapIds can not be empty")
        List<@Size(min = 36, max = 36, message = "mapId length should be 36") String> mapIds) {
        log.info("Start invoke getMapInfoByIds()");
        return mapInfoService.getMapInfosByIds(mapIds);
    }

    @RequestMapping(value = "mapinfos/tagName", method = RequestMethod.GET)
    public List<MapInfo> getMapInfoByTag(@RequestParam("tagName") @NotNull(message = "tagName cannot be null")
        @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName) {
        log.info("Start invoke getMapInfoByTag()");
        return mapInfoService.getMapInfosByTag(tagName);
    }


    @RequestMapping(value = "mapinfo", method = RequestMethod.POST)
    public MapInfo addMapInfo(@RequestBody @Validated MapInfo mapInfo,
        BindingResult bindingResult) {
        BindResultUtils.validData(bindingResult);
        log.info("Start invoke addMapInfo()");
        mapInfoService.addMapInfo(mapInfo);
        return mapInfo;
    }

    @RequestMapping(value = "mapinfo/tagName/{tagName}", method = RequestMethod.POST)
    public MapInfo addMapInfoByTagName(@RequestBody
        @Validated MapInfo mapInfo,
        BindingResult bindingResult,
        @PathVariable("tagName")
        @NotNull(message = "tagName cannot be null")
        @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName) {
        BindResultUtils.validData(bindingResult);
        log.info("Start invoke addMapInfoByTagName()");
        // 先添加资源表的记录
        mapInfoService.addMapInfo(mapInfo);
        // 再添加资源关联表中的记录
        TagResource tagResource = new TagResource("", tagName, mapInfo.getMapId(), ResourceTypeEnum.MAPINFO.getResourceType());
        tagResourceService.addTagResource(tagResource);
        return mapInfo;
    }


    @RequestMapping(value = "mapinfo/{mapId}", method = RequestMethod.DELETE)
    public Map<String, String> deleteMapInfoById(
      @PathVariable(value = "mapId")
      @Size(min = 36, max = 36, message = "mapId length should be 36") String mapId) {
        log.info("Start invoke deleteMapInfoById()");
        mapInfoService.deleteMapInfoById(mapId);
        Map<String, String> result = new HashMap<>();
        result.put(MAP_ID, mapId);
        return result;
    }

    @RequestMapping(value = "mapinfo/{mapId}/tagName/{tagName}", method = RequestMethod.DELETE)
    public Map<String, String> deleteMapInfoByTagName(@PathVariable(value = "mapId")
        @Size(min = 36, max = 36, message = "mapId length should be 36") String mapId,
        @PathVariable("tagName")
        @NotNull(message = "tagName cannot be null")
        @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName) {
        log.info("Start invoke deleteMapInfoByTagName()");
        // 先删除资源关联表中的记录
        tagResourceService.deleteTagResource(tagName, mapId);
        // 再删除资源表的记录
        mapInfoService.deleteMapInfoById(mapId);
        Map<String, String> result = new HashMap<>();
        result.put(MAP_ID, mapId);

        return result;
    }


    @RequestMapping(value = "mapinfo", method = RequestMethod.PUT)
    public Map<String, String> updateMapInfo(
      @RequestBody MapInfo mapInfo, BindingResult bindingResult) {
        BindResultUtils.validData(bindingResult);
        log.info("Start invoke updateMapInfo()");
        mapInfoService.updateMapInfo(mapInfo);
        Map<String, String> result = new HashMap<>();
        result.put(MAP_ID, mapInfo.getMapId());
        return result;
    }

}
