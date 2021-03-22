package cn.edu.zju.sishi.entity.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
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

    private List<Double> geoCoordinates;

    public TagCompareTime() {
        this.value = "";
        this.label = "";
        this.tagName = "";
        this.tagId = "";
        this.time = "";
        this.geoCoordinates = new ArrayList<>();
    }

    @Override
    public int compareTo(TagCompareTime o) {
        return this.time.compareTo(o.getTime());
    }
}
