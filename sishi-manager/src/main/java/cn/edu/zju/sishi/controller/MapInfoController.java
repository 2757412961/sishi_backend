package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.commons.utils.BindResultUtils;
import cn.edu.zju.sishi.commons.utils.LogicUtil;
import cn.edu.zju.sishi.entity.Article;
import cn.edu.zju.sishi.entity.MapInfo;
import cn.edu.zju.sishi.entity.TagResource;
import cn.edu.zju.sishi.enums.ResourceTypeEnum;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.passport.annotation.AuthController;
import cn.edu.zju.sishi.service.AuthorityService;
import cn.edu.zju.sishi.service.MapInfoService;
import cn.edu.zju.sishi.service.TagResourceService;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
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
@AuthController
public class MapInfoController {

    private final String MAP_ID = "mapId";

    @Autowired
    private MapInfoService mapInfoService;

    @Autowired
    private TagResourceService tagResourceService;

    @Autowired
    private AuthorityService authorityService;

    @RequestMapping(value = "mapinfos", method = RequestMethod.GET)
    public JSONObject getAllMapInfo(HttpServletRequest request) {
        log.info("Start invoke getAllMapInfo()");
        boolean isAdministrator = authorityService.isAdamin(request);
        List<MapInfo> mapInfos = mapInfoService.getAllMapInfos(LogicUtil.getLogicByIsAdmins(isAdministrator));

        JSONObject result = new JSONObject();
        result.put("totalCount", mapInfos.size());
        result.put("mapInfos", mapInfos);

        return result;
    }

    @RequestMapping(value = "mapinfo/{mapId}", method = RequestMethod.GET)
    public MapInfo getMapInfoById(@PathVariable(value = "mapId")
                                  @Size(min = 36, max = 36, message = "mapId length should be 36") String mapId) {
        log.info("Start invoke getMapInfoById()");
        return mapInfoService.getMapInfoById(mapId);
    }

    @RequestMapping(value = "mapinfos/ids", method = RequestMethod.POST)
    public JSONObject getMapInfoByIds(@RequestBody
                                      @NotEmpty(message = "mapIds can not be empty")
                                              List<@Size(min = 36, max = 36, message = "mapId length should be 36") String> mapIds) {
        log.info("Start invoke getMapInfoByIds()");
        List<MapInfo> mapInfos = mapInfoService.getMapInfosByIds(mapIds);

        JSONObject result = new JSONObject();
        result.put("totalCount", mapInfos.size());
        result.put("mapInfos", mapInfos);

        return result;
    }

    @RequestMapping(value = "mapinfos/tagName/{tagName}", method = RequestMethod.GET)
    public JSONObject getMapInfoByTag(@PathVariable("tagName") @NotNull(message = "tagName cannot be null")
                                      @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName,
                                      HttpServletRequest request) {
        log.info("Start invoke getMapInfoByTag()");
        boolean isAdministrator = authorityService.isAdamin(request);
        List<MapInfo> mapInfos = mapInfoService.getMapInfosByTag(tagName, LogicUtil.getLogicByIsAdmins(isAdministrator));

        JSONObject result = new JSONObject();
        result.put("totalCount", mapInfos.size());
        result.put("mapInfos", mapInfos);

        return result;
    }


    @RequestMapping(value = "mapinfo", method = RequestMethod.POST)
    public MapInfo addMapInfo(@RequestBody @Validated MapInfo mapInfo,
                              BindingResult bindingResult) {
        BindResultUtils.validData(bindingResult);
        log.info("Start invoke addMapInfo()");
        mapInfoService.addMapInfo(mapInfo);
        return mapInfo;
    }

    @Transactional
    @RequestMapping(value = "mapinfo/tagName/{tagName}", method = RequestMethod.POST)
    public MapInfo addMapInfoByTagName(@RequestBody
                                       @Validated MapInfo mapInfo,
                                       BindingResult bindingResult,
                                       @PathVariable("tagName")
                                       @NotNull(message = "tagName cannot be null")
                                       @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName,
                                       HttpServletRequest request) {
        BindResultUtils.validData(bindingResult);
        log.info("Start invoke addMapInfoByTagName()");
        // 先添加资源表的记录
        mapInfoService.addMapInfo(mapInfo);
        // 再添加资源关联表中的记录
        TagResource tagResource = new TagResource("", tagName, mapInfo.getMapId(), ResourceTypeEnum.MAPINFO.getResourceType(), authorityService.getUserId(request));
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

    @Transactional
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

    @Transactional
    @RequestMapping(value = "mapinfo/public/{mapId}", method = RequestMethod.PUT)
    public Map<String, String> updateIsPublicById(@PathVariable("mapId")
                                                  @Size(min = 36, max = 36, message = "mapId length should be 36") String mapId,
                                                  HttpServletRequest request) {
        log.info("Start invoke updateIsPublicById()");
        if (!authorityService.isAdamin(request)) {
            throw new ValidationException("No permission to perform this operation");
        }

        mapInfoService.updateIsPublicById(mapId);

        Map<String, String> result = new HashMap<>();
        result.put(MAP_ID, mapId);

        return result;
    }

}
