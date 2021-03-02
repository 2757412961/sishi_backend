package cn.edu.zju.sishi.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class Tag implements Serializable {
    private Integer tagId;
    private String tagName;
}
