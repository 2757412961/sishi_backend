package cn.edu.zju.sishi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author sky
 * @create 2021-03-09 9:50
 */

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Question implements Serializable {

    @Size(min = 36, max = 36, message = "id length should be 36")
    private  String question_id;

    private  String question_content;
    private  String options;
    private  String answer;
}
