package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.entity.Tag;
import cn.edu.zju.sishi.entity.vo.TagCompareTime;
import cn.edu.zju.sishi.entity.vo.TagTree;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.passport.annotation.AuthController;
import cn.edu.zju.sishi.service.TagService;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Size;
import java.util.*;

@RestController
@AuthController
public class TagController {

    private static final String ID = "id";
    private static final String TAG_NAME = "tagName";

    @Autowired
    private TagService tagService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());


    @RequestMapping(value = "tags", method = RequestMethod.GET)
    @ResponseBody
    public List<Tag> selectTags() {
        logger.info("Start invoke selectTags()");
        return tagService.selectTags();
    }

    @RequestMapping(value = "tag/{tagName}", method = RequestMethod.GET)
    @ResponseBody
    public List<Tag> selectTag(
            @PathVariable(value = "tagName")
            @Size(min = 1, max = 200, message = "tagName length should be in 1 and 200") String tagName) {
        logger.info("start invoke selectTag()");

        ArrayList<Tag> tag = new ArrayList<>();
        tag.add(tagService.getTagByTagName(tagName));
        return tag;
    }

    @RequestMapping(value = "tag/tree", method = RequestMethod.GET)
    public List<TagTree> getTagTree() {
        logger.info("Start invoke getTagTree()");
        return tagService.getTagTree();
    }

    @RequestMapping(value = "tag/children/{tagName}", method = RequestMethod.GET)
    public Set<String> getChildTag(@PathVariable(value = "tagName")
                                   @Size(min = 1, max = 200, message = "tagName length should be in 1 and 200") String tagName) {
        return tagService.getChildTag(tagName);
    }

    @RequestMapping(value = "tag/compareTime/{tagName}", method = RequestMethod.GET)
    public List<TagCompareTime> getTagCompareTime(@PathVariable(value = "tagName")
                                   @Size(min = 1, max = 200, message = "tagName length should be in 1 and 200") String tagName) {
        return tagService.getTagCompareTime(tagName);
    }


    @RequestMapping(value = "tag", method = RequestMethod.POST)
    @ResponseBody
    public Tag addTag(@RequestBody @Validated Tag tag, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        }
        logger.info("start invoke addTag()");
        tagService.addTag(tag);
        return tag;
    }

    @RequestMapping(value = "tags", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> addTags(@RequestBody List<String> tags) {
        logger.info("start invoke addTags()");
        Map<String, String> result = new HashMap<>();
        tagService.addTags(tags);
        result.put("msg", "Add tags successfully!");
        return result;
    }


    @RequestMapping(value = "tag/{tagName}", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, String> dropTag(
            @PathVariable(value = "tagName")
            @Size(min = 1, max = 200, message = "tagName length should be in 1 and 200") String tagName) {
        logger.info("start invoke dropTag()");
        Map<String, String> result = new HashMap<>();
        tagService.dropTag(tagName);
        result.put(TAG_NAME, tagName);
        return result;
    }

}
