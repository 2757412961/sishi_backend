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
public class TagTree implements Serializable {

    private String value;

    private String label;

    private String tagName;

    private List<TagTree> children;

    private List<Double> geoCoordinates;

    public TagTree() {
        this.value = "";
        this.label = "";
        this.children = new ArrayList<>();
    }

}
