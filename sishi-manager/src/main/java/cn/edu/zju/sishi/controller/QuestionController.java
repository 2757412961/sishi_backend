package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.entity.Question;
import cn.edu.zju.sishi.service.QuestionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sky
 * @create 2021-03-09 10:17
 */
@RestController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "questions/selectques", method = RequestMethod.GET)
    @ResponseBody
    public List<Question> getQues() {
        logger.info("Start invoke getQues()");
        return questionService.getQues();
    }

    @RequestMapping(value = "questions/selectques/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Question getQuesByID(@PathVariable("id") String question_id) {
        logger.info("Start invoke getQuesByID()");
        return questionService.getQuesByID(question_id);
    }


    @RequestMapping(value = "questions/deleteques/{id}", method = RequestMethod.DELETE)
    @ResponseBody
    public Map<String, String> deleteQuesByID(@PathVariable("id") String question_id) {
        logger.info("Start invoke deleteQuesByID()");
        int count = questionService.deleteQuesByID(question_id);
        Map<String, String> result = new HashMap<>();

        if (count >= 1){
            result.put("msg", "Delete question "+question_id+"!");
            return result;
        }
        else {
            result.put("msg", "Failed to delete question "+question_id+"!");
            return result;
        }
    }

    @RequestMapping(value = "questions/insertques", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> insertQues(@RequestBody Question question) {
        logger.info("Start invoke insertQues()");
        Map<String, String> result = new HashMap<>();
        int count = questionService.insertQues(question);

        if (count >= 1){
            result.put("msg", "Add this question successfully!");
            return result;
        }
        else {
            result.put("msg", "Failed to add this question !");
            return result;
        }
    }

    @RequestMapping(value = "questions/updateques", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, String> updateQues(@RequestBody Question question) {
        logger.info("Start invoke updateQues()");
        int count = questionService.updateQues(question);
        Map<String, String> result = new HashMap<>();

        if (count >= 1){
            result.put("question_id", "Update "+question.getQuestionId());
            return result;
        }
        else {
            result.put("msg", "Failed to update this question !");
            return result;
        }

    }



}
