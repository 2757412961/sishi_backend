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
        System.out.println(pictureDao.getPictureAll());
    }

    @Test
    public void getPictureById() {
        System.out.println(pictureDao.getPictureById("9bdd91b3-661c-4f82-9951-6102d32d47a2"));
        System.out.println(pictureDao.getPictureById("9bdd91b3-661c-4f82-9951-6102d32d47a2"));
    }

    @Test
    public void getPictureByIds() {
        List<String> ids = new ArrayList<>();
        ids.add("9bdd91b3-661c-4f82-9951-6102d32d47a2");
        ids.add("1b08dfe4-6eb6-49b4-af7a-d803bf8bdd70");
        ids.add("1b08dfe4-6eb6-49b4-af7a-d803bf8bdd70");

        System.out.println(pictureDao.getPictureByIds(ids));
    }

    @Test
    public void getPictureByPage() {
        System.out.println(pictureDao.getPictureByPage(3, 2));
    }

    @Test
    public void getPictureByTag() {
        System.out.println(pictureDao.getPictureByTag("党史新学@中共一大"));
    }

    @Test
    public void addPicture() {
        System.out.println(
                pictureDao.addPicture(
                        new Picture(UUID.randomUUID().toString(), "ct.png", "https:126/adfss.png", 78)));
    }

    @Test
    public void deletePictureById() {
        System.out.println(pictureDao.deletePictureById("ad4-89486-dfs-sfa6f-adfs"));
    }
}