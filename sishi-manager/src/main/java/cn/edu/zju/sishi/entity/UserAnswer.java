package cn.edu.zju.sishi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author sky
 * @create 2021-03-09 11:12
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAnswer implements Serializable {

    private String userName;
    private String tagName;
    private Integer userAnswerStatus;
    private Date time;

}
