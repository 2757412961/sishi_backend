package cn.edu.zju.sishi.entity.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
public class TagTree implements Serializable {

    private String value;

    private String label;

    private List<TagTree> children;

    public TagTree() {
        this.value = "";
        this.label = "";
        this.children = new ArrayList<>();
    }

}
