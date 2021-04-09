package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.TagResource;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TagResourceDaoTest {

    @Autowired
    private TagResourceDao tagResourceDao;

    @Test
    public void getTagResourceType() {
        System.out.println(tagResourceDao.getTagResourceType("党史新学@中共一大", "1e96b9d3-183d-406e-ab30-d8759baca6f6"));
    }

    @Test
    public void getTagResourceIds() {
        System.out.println(tagResourceDao.getTagResourceIds("gero@iughi@gyi", "tb_test"));
    }

//    @Test
//    public void addTagResource() {
//        System.out.println(
//                tagResourceDao.addTagResource(
//                        , authorityService.getUserId(request), authorityService.getUserId(request)new TagResource("fgsdfgsdfgdfsgs", "gero@iughi@gyi", "fgafg-fsa'as", "tb_test")));
//    }

    @Test
    public void deleteTagResource() {
        System.out.println(
                tagResourceDao.deleteTagResource("gero@iughi@gyi", "fgafg-fsa'as")
        );
    }
}