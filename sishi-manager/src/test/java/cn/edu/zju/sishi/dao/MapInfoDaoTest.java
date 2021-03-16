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
        System.out.println(mapInfoDao.getAllMapInfos());
    }

    @Test
    public void getMapInfoById() {
        System.out.println(mapInfoDao.getMapInfoById("70ec86d1-bddd-4a1e-befd-580d17977b46"));
    }

    @Test
    public void getMapInfoByName() {
        System.out.println(mapInfoDao.getMapInfoByTitle("test"));
    }

    @Test
    public void addMapInfo() {
        MapInfo mapInfo = new MapInfo();
        mapInfo.setMapId("test3");
        mapInfo.setMapTitle("test3");
        mapInfo.setMapLon(123.3);
        mapInfo.setMapLat(13.3);
        mapInfo.setMapCreateTime(Instant.now().toEpochMilli());

        System.out.println(mapInfoDao.addMapInfo(mapInfo));
    }

    @Test
    public void deleteMapInfoById() {
        System.out.println(mapInfoDao.deleteMapInfoById("test3"));
    }

    @Test
    public void updateMapInfo() {
        MapInfo mapInfo = new MapInfo();
        mapInfo.setMapId("dfas");
        mapInfo.setMapTitle("test2");
        mapInfo.setMapLon(123.3);
        mapInfo.setMapLat(13.3);
        mapInfo.setMapCreateTime(Instant.now().toEpochMilli());

        System.out.println(mapInfoDao.updateMapInfo(mapInfo));
    }

    @Test
    public void getMapInfoByTag() {
        System.out.println(mapInfoDao.getMapInfosByTag("党史新学@中共一大"));
    }
}