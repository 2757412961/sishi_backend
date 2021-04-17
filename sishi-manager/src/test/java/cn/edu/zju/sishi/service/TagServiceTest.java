package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.vo.TagTree;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class TagServiceTest {

    @Autowired
    private TagService tagService;

    @Test
    public void mmmm() {
        JSONObject json = new JSONObject();
        JSONObject json01 = new JSONObject();
        JSONObject json02 = new JSONObject();
        JSONObject json03 = new JSONObject();

        json01.put("zhonggongyida", "das");
        json02.put("h", "das");

        json.put("党史", json01);
        json.put("党史", json02);

        JSONArray jsonArray = new JSONArray();
        jsonArray.add(json01);
        jsonArray.add(json02);


        System.out.println(jsonArray);
        System.out.println(jsonArray.toString());
        System.out.println(jsonArray.isEmpty());
        System.out.println(jsonArray.contains("zhonggongyida"));
        System.out.println(jsonArray.contains(json01));
        System.out.println("------------------------------------");


//        System.out.println(new JSON());

        System.out.println(json03.isEmpty());
        System.out.println(json03.values());
        System.out.println(json03);

        System.out.println(json);
        System.out.println(json.isEmpty());
        System.out.println(json.size());
        System.out.println(json.values());
        System.out.println(json.toString());
        System.out.println(json.toJSONString());
    }

    @Test
    public void getTagByTagName() {

    }

    @Test
    public void selectTags() {

    }

    @Test
    public void testGetTagByTagName() {

    }

    @Test
    public void testGetTagCompareTime() {

        System.out.println(JSON.toJSONString(tagService.getTagCompareTime("党史新学")));

    }

    @Test
    public void getTagTree() {
        TagTree tagTree = new TagTree();

//        System.out.println(tagService.getTagTree());
        System.out.println(JSON.toJSON(tagService.getTagTree()));
    }

    @Test
    public void getTagTree02() {
//        System.out.println(tagService.getTagTree02());
    }

    @Test
    public void getTagTree03() {
//        System.out.println(tagService.getTagTree03());
    }

    @Test
    public void getChildTag() {
        System.out.println(tagService.getChildTag("党史新学"));
        System.out.println(tagService.getChildTag("党史新学@中共一大"));
        System.out.println(tagService.getChildTag("党史新学@中共一大@北京"));
        System.out.println(tagService.getChildTag("改革开放@改革复兴"));
    }
}