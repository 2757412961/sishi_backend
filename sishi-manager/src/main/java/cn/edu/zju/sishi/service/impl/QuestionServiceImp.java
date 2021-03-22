package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.dao.QuestionDao;
import cn.edu.zju.sishi.entity.Question;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author sky
 * @create 2021-03-09 10:04
 */

@Service
public class QuestionServiceImp implements QuestionService {

    @Autowired
    private QuestionDao questionDao;

    @Override
    public List<Question> getQues()
    { return questionDao.getQues(); }

    @Override
    public Question getQuesByID(String question_id)
    {  if (questionDao.getQuesByID(question_id) == null) {
        throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), String.format("Question %s does not exist!", question_id));
    }
        return questionDao.getQuesByID(question_id); }

    @Override
    public int deleteQuesByID(String question_id) {
        if (questionDao.getQuesByID(question_id) == null) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), String.format("Question %s does not exist!", question_id));
        }
         return questionDao.deleteQuesByID(question_id);
    }

    @Override
    public int insertQues(Question question) {
       setID(question);
       return questionDao.insertQues(question);
    }

    private void setID(Question question) {
        final String uuid = UUID.randomUUID().toString();
        question.setQuestionId(uuid);
           }

    @Override
    public int updateQues(Question question) {
        if (questionDao.getQuesByID(question.getQuestionId()) == null) {
            throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), String.format("Question %s does not exist!", question.getQuestionId()));
        }
        return questionDao.updateQues(question);
    }


}
