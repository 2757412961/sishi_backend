package cn.edu.zju.sishi.entity.vo;

import junit.framework.TestCase;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;

public class TagCompareTimeTest extends TestCase {

    @Test
    public void test01() {

        TagCompareTime tagCompareTime01 = new TagCompareTime("asd", "sd", "d", "as", "1829nian745", null);
        TagCompareTime tagCompareTime02 = new TagCompareTime("asd", "ad", "asd", "asd", "1848asd", null);
        TagCompareTime tagCompareTime03 = new TagCompareTime("asd", "ad", "asd", "asd", "1811asd", null);

        System.out.println("1829nian745".compareTo("1848asd"));
        System.out.println(tagCompareTime01.compareTo(tagCompareTime02));

        ArrayList<TagCompareTime> objects = new ArrayList<>();
        objects.add(tagCompareTime01);
        objects.add(tagCompareTime02);
        objects.add(tagCompareTime03);

        System.out.println(objects);
        Collections.sort(objects);
        System.out.println(objects);

    }

}