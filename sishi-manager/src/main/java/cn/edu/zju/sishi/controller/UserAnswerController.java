package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.commons.utils.LogicUtil;
import cn.edu.zju.sishi.entity.Question;
import cn.edu.zju.sishi.entity.UserAnswer;
import cn.edu.zju.sishi.service.AuthorityService;
import cn.edu.zju.sishi.service.UserAnswerService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ValidationException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sky
 * @create 2021-03-10 14:54
 */

@RestController
public class UserAnswerController {

    @Autowired
    private UserAnswerService userAnswerService;
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private AuthorityService authorityService;

    @RequestMapping(value = "userAnswer/user", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject getUserAnswerByUser(HttpServletRequest request) {
        logger.info("Start invoke getUserAnswerByUser()");
        String userId = authorityService.getUserId(request);
        if (userId == null) {
            throw new ValidationException("userId length should be 36");
        }
        List<UserAnswer> userAnswers = userAnswerService.getUserAnswerByUser(userId);

        JSONObject object = new JSONObject();
        object.put("userAnswers", userAnswers);
        object.put("totalSize", userAnswers.size());
        return object;
    }

    @RequestMapping(value = "getuseranswer", method = RequestMethod.GET)
    @ResponseBody
    public UserAnswer getUserAnswerStatus(@RequestParam(value = "tag_name") String tag_name, @RequestParam(value = "user_name") String user_name) {
        logger.info("Start invoke getUserAnswerStatus()");
        return userAnswerService.getUserAnswerStatus(tag_name, user_name);
    }

    @RequestMapping(value = "questionsTag", method = RequestMethod.GET)
    @ResponseBody
    public List<Question> getQuesByTag(@RequestParam(value = "tag_name") String tag_name, HttpServletRequest request) {
        logger.info("Start invoke getQuesByTag()");
        boolean isAdministrator = authorityService.isAdamin(request);
        return userAnswerService.getQuesByTag(tag_name, LogicUtil.getLogicByIsAdmins(isAdministrator));
    }


    @RequestMapping(value = "useranswer", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> updateUserAnswerStatusAndScore(
            @RequestParam(value = "tag_name") String tag_name,
            @RequestParam(value = "user_name") String user_name,
            HttpServletRequest request) {
        logger.info("Start invoke updateUserAnswerStatusAndScore()");
        Map<String, String> result = new HashMap<>();

        // GUEST
        boolean isGUEST = authorityService.isGuest(request);
        if (isGUEST) {
            result.put("message", "访客用户答题记录无效，请先注册并登录!");
            return result;
        }
        // GUEST

        Date time = new Date();
        List<Integer> count = userAnswerService.insertUserAnswerStatus(tag_name, user_name, time);

        int count1 = count.get(0).intValue();
        int count2 = count.get(1).intValue();

        if (count1 >= 1 && count2 >= 1) {
            result.put(tag_name, user_name + "success!");
            return result;
        } else {
            result.put("Fail" + tag_name, user_name);
            return result;
        }
    }


}
