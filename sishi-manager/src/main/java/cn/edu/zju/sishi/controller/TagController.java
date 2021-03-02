package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.entity.Tag;
import cn.edu.zju.sishi.service.TagService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TagController {

    @Autowired
    private TagService tagService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "tags", method = RequestMethod.GET)
    @ResponseBody
    public List<Tag> getTag() {
        logger.info("Start invoke getTag()");
        return tagService.getTag();
    }

}
