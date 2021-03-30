package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.Question;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sky
 * @create 2021-03-10 14:25
 */
public interface UserAnswerService {

    Integer getUserAnswerStatus(String tag_name, String user_name);

    List<Question> getQuesByTag (String tag_name, String logicSymbol);

    List<Integer> insertUserAnswerStatus(String tag_name, String user_name);
}



