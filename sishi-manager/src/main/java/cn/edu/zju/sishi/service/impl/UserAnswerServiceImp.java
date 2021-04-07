package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.dao.UserAnswerDao;
import cn.edu.zju.sishi.dao.UserDao;
import cn.edu.zju.sishi.entity.Question;
import cn.edu.zju.sishi.entity.UserAnswer;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.UserAnswerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author sky
 * @create 2021-03-10 14:25
 */

@Service
public class UserAnswerServiceImp implements UserAnswerService {

    @Autowired
    private UserAnswerDao userAnswerDao;

    @Autowired
    private UserDao userDao;

    @Override
    public UserAnswer getUserAnswerStatus(String tag_name, String user_name) {
        return  userAnswerDao.getUserAnswerStatus(tag_name, user_name);

    }

    @Override
    public List<Question> getQuesByTag(String tag_name, String logicSymbol) {
        return userAnswerDao.getQuesByTag(tag_name, logicSymbol);
    }


    @Override
    public List<Integer> insertUserAnswerStatus(String tag_name, String user_name, Date time) {
        if(userDao.getUserByName(user_name) == null) {
            throw new ValidationException(String.format("User %s not exist!", user_name));
        }
        else if (new Integer(1).equals(userAnswerDao.getUserAnswerStatus(tag_name, user_name))) {
            throw new ValidationException(String.format("This user has answered these questions!"));
        }

        ArrayList<Integer> count = new ArrayList<>();
        count.add(userAnswerDao.updateUserScore(user_name));
        count.add(userAnswerDao.insertUserAnswerStatus(tag_name, user_name,time));
        return count;
    }
}
