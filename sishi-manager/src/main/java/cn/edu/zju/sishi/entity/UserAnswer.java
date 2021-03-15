package cn.edu.zju.sishi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author sky
 * @create 2021-03-09 11:12
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserAnswer implements Serializable {

    private String user_name;
    private String tag_name;
    private Integer user_answer_status;

}