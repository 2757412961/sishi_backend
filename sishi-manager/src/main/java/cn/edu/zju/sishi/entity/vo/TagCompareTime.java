package cn.edu.zju.sishi.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Data
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TagCompareTime implements Comparable<TagCompareTime>, Serializable {

    private String value;

    private String label;

    private String tagName;

    private String tagId;

    private String time;

    private String picUrl;

    private String property;

    private List<List<Double>> geoCoordinates;

    private List<String> boundry;

    public TagCompareTime() {
        this.value = "";
        this.label = "";
        this.tagName = "";
        this.tagId = "";
        this.time = "";
        this.picUrl = "";
        this.property = "";
        this.geoCoordinates = new ArrayList<>();
        this.boundry = Collections.EMPTY_LIST;
    }

    @Override
    public int compareTo(TagCompareTime o) {
        return this.time.compareTo(o.getTime());
    }
}
