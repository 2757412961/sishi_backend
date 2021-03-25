package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.Picture;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class PictureDaoTest {

    @Autowired
    private PictureDao pictureDao;

    @Test
    public void getPictureAll() {
        System.out.println(pictureDao.getPicturesAll());
    }

    @Test
    public void getPictureById() {
        System.out.println(pictureDao.getPictureById("7fe00224-8a0d-4857-badb-8d7b03843b33"));
    }

    @Test
    public void getPictureByIds() {
        List<String> ids = new ArrayList<>();
        ids.add("7fe00224-8a0d-4857-badb-8d7b03843b33");
        ids.add("497e30c5-da2a-421c-b593-7b8cdae576e6");
        ids.add("1b08dfe4-6eb6-49b4-af7a-d803bf8bdd70");

        System.out.println(pictureDao.getPicturesByIds(ids));
    }

    @Test
    public void getPictureByPage() {
        System.out.println(pictureDao.getPicturesByPage(3, 1));
    }

    @Test
    public void getPictureByTag() {
        System.out.println(pictureDao.getPicturesByTag("党史新学@中共一大"));
    }


    @Test
    public void deletePictureById() {
        System.out.println(pictureDao.deletePictureById("1f606233-b6f2-48e1-9e62-a154e6189993"));
    }

    @Test
    public void testUpdateIsPublicById() {
        System.out.println(pictureDao.updateIsPublicById("a5256cb1-96ef-46a1-874b-2198e20c09d0"));
    }
}