package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.entity.Question;
import cn.edu.zju.sishi.service.UserAnswerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
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

    @RequestMapping(value="getuseranswer" , method = RequestMethod.GET)
    @ResponseBody
    public int getUserAnswerStatus(@RequestParam(value = "tag_name")String tag_name,@RequestParam(value = "user_name")String user_name) {
        logger.info("Start invoke getUserAnswerStatus()");
        return userAnswerService.getUserAnswerStatus(tag_name, user_name);
    }


    @RequestMapping(value="questionsTag" , method = RequestMethod.GET)
    @ResponseBody
    public List<Question> getQuesByTag(@RequestParam(value = "tag_name")String tag_name) {
        logger.info("Start invoke getQuesByTag()");
        return userAnswerService.getQuesByTag(tag_name);
    }


    @RequestMapping(value="useranswer" , method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, String> updateUserAnswerStatusAndScore (@RequestParam(value = "tag_name")String tag_name,@RequestParam(value = "user_name")String user_name) {
        logger.info("Start invoke updateUserAnswerStatusAndScore()");
        List<Integer> count = userAnswerService.insertUserAnswerStatus(tag_name, user_name);
        Map<String, String> result = new HashMap<>();

        int count1 = count.get(0).intValue();
        int count2 = count.get(1).intValue();

        if (count1>=1 && count2>=1){
            result.put(tag_name,user_name+"success!");
            return result;
        }
        else{
            result.put("Fail"+tag_name,user_name);
            return result;
        }
    }


}
