package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.MapInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.time.Instant;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MapInfoDaoTest {

    @Autowired
    private MapInfoDao mapInfoDao;

    @Test
    public void getAllMapInfo() {
        System.out.println(mapInfoDao.getAllMapInfo());
    }

    @Test
    public void getMapInfoById() {
        System.out.println(mapInfoDao.getMapInfoById("test"));
    }

    @Test
    public void getMapInfoByName() {
        System.out.println(mapInfoDao.getMapInfoByName("test2"));
    }

    @Test
    public void addMapInfo() {
        MapInfo mapInfo = new MapInfo();
        mapInfo.setMapId("test3");
        mapInfo.setMapName("test3");
        mapInfo.setMapJson("json3");
        mapInfo.setCreateTime(Instant.now().toEpochMilli());

        System.out.println(mapInfoDao.addMapInfo(mapInfo));
    }

    @Test
    public void deleteMapInfoById() {
        System.out.println(mapInfoDao.deleteMapInfoById("123"));
    }

    @Test
    public void updateMapInfo() {
        MapInfo mapInfo = new MapInfo();
        mapInfo.setMapId("dfas");
        mapInfo.setMapName("test2");
        mapInfo.setMapJson("json2");
        mapInfo.setCreateTime(Instant.now().toEpochMilli());

        System.out.println(mapInfoDao.updateMapInfo(mapInfo));
    }

    @Test
    public void getMapInfoByTag() {
        System.out.println(mapInfoDao.getMapInfoByTag("党史新学@中共一大"));
    }
}