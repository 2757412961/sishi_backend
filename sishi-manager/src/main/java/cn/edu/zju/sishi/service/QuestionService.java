package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.Question;

import java.util.List;

/**
 * @author sky
 * @create 2021-03-09 9:55
 */
public interface QuestionService {

    List<Question> getQues (String logicSymbol);

    Question getQuesByID (String question_id);

    int deleteQuesByID (String question_id);

    int insertQues ( Question question);

    int updateQues ( Question question);

    int updateIsPublicById(String question_id);

}
