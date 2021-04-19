package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.enums.ResourceTypeEnum;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TagDaoTest {

    @Autowired
    private TagDao tagDao;

    @Test
    public void selectTags() {
        System.out.println(tagDao.selectTags());
    }

    @Test
    public void ada(){
        ResourceTypeEnum article = ResourceTypeEnum.ARTICLE;

    }

    @Test
    public void getTagsByPrefix() {
        System.out.println(tagDao.getTagsByPrefix("党史新学"));
    }
}