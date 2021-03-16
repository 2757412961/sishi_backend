package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.service.MapInfoService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class MapInfoServiceImplTest {

    @Autowired
    private MapInfoService mapInfoService;

    @Test
    public void getAllMapInfo() {
        System.out.println(mapInfoService.getAllMapInfos());
    }

    @Test
    public void getMapInfoById() {
        System.out.println(mapInfoService.getMapInfoById("das"));
    }

    @Test
    public void getMapInfoByIds() {
        List<String> ids = new ArrayList<>();
        ids.add("1312");
        ids.add("test");
        ids.add("131fsd2");
        ids.add("test2");


        System.out.println(mapInfoService.getMapInfosByIds(ids));
    }

    @Test
    public void addMapInfo() {

    }

    @Test
    public void deleteMapInfoById() {

    }

    @Test
    public void updateMapInfo() {

    }
}