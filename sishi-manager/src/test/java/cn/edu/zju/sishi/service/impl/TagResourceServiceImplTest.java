package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.entity.TagResource;
import cn.edu.zju.sishi.enums.ResourceTypeEnum;
import cn.edu.zju.sishi.service.TagResourceService;
import com.alibaba.fastjson.JSON;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TagResourceServiceImplTest {

    @Autowired
    private TagResourceService tagResourceService;

    @Test
    public void getTagResourceIds() {
        System.out.println(tagResourceService.getTagResourceIds("gero@iughi@gyi", "tb_test"));
    }

    @Test
    public void addTagResource() {
        System.out.println(
                tagResourceService.addTagResource(
                        new TagResource("fgsdfgsdfgdfsgs", "gero@iughi@gyi", "fgafg-fsa'as", "tb_test")));

    }

    @Test
    public void deleteTagResource() {
        System.out.println(
                tagResourceService.deleteTagResource("gero@iughi@gyi", "fgafg-fsa'as")
        );
    }


    @Test
    public void deleteByTagName() {
        System.out.println(tagResourceService.deleteByTagName("da"));
    }

    @Test
    public void testGetMediaItemByResourceType() {

        System.out.println(JSON.toJSONString(tagResourceService.getMediaItemsByResourceType(ResourceTypeEnum.VIDEO.getResourceType())));

    }
}