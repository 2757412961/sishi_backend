package cn.edu.zju.sishi.dao;

import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class VideoDaoTest extends TestCase {

    @Autowired
    VideoDao videoDao;

    @Test
    public void testListVideos() {
//        System.out.println(videoDao.listVideos(10, 0, "1890-1-1", "2890-1-1"));
    }
}