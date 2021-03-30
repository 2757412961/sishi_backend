package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.commons.utils.BindResultUtils;
import cn.edu.zju.sishi.commons.utils.LogicUtil;
import cn.edu.zju.sishi.entity.Question;
import cn.edu.zju.sishi.entity.TagResource;
import cn.edu.zju.sishi.enums.ResourceTypeEnum;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.passport.annotation.AuthController;
import cn.edu.zju.sishi.service.AuthorityService;
import cn.edu.zju.sishi.service.QuestionService;
import cn.edu.zju.sishi.service.TagResourceService;
import cn.edu.zju.sishi.service.UserAnswerService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sky
 * @create 2021-03-09 10:17
 */
@RestController
//@AuthController
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    @Autowired
    private UserAnswerService userAnswerService;

    @Autowired
    private TagResourceService tagResourceService;

    @Autowired
    private AuthorityService authorityService;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @RequestMapping(value = "questions/selectques", method = RequestMethod.GET)
    @ResponseBody
    public List<Question> getQues(HttpServletRequest request) {
        logger.info("Start invoke getQues()");

        boolean isAdministrator = authorityService.isAdamin(request);
        List<Question> questions = questionService.getQues(LogicUtil.getLogicByIsAdmins(isAdministrator));

        JSONObject result = new JSONObject();
        result.put("totalCount", questions.size());
        result.put("questions", questions);
        return questions;
    }

    @RequestMapping(value = "questions/selectques/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Question getQuesByID(@PathVariable("id") String question_id) {
        logger.info("Start invoke getQuesByID()");
        return questionService.getQuesByID(question_id);
    }

    @RequestMapping(value = "questions/tagName/{tagName}", method = RequestMethod.GET)
    @ResponseBody
    public List<Question> getQuestionsByTagName(@PathVariable(value = "tagName") String tagName, HttpServletRequest request) {
        logger.info("start invoke listQuestionsByTagName()");

        boolean isAdministrator = authorityService.isAdamin(request);
        List<Question> questionsByTagName = userAnswerService.getQuesByTag(tagName, LogicUtil.getLogicByIsAdmins(isAdministrator));

        JSONObject result = new JSONObject();
        result.put("totalCount", questionsByTagName.size());
        result.put("questions", questionsByTagName);
        return questionsByTagName;
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

    @Transactional
    @RequestMapping(value = "question/{questionId}/tagName/{tagName}", method = RequestMethod.DELETE)
    public Map<String, String> deleteQuestionByTagName(@PathVariable("questionId")
                                                      @Size(min = 36, max = 36, message = "questionId length should be 36") String questionId,
                                                      @PathVariable("tagName")
                                                      @NotNull(message = "tagName cannot be null")
                                                      @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName) {
        logger.info("Start invoke deleteQuestionByTagName()");
        // 先删除资源关联表中的记录
        tagResourceService.deleteTagResource(tagName, questionId);
        // 再删除资源表的记录
        questionService.deleteQuesByID(questionId);

        Map<String, String> result = new HashMap<>();
        result.put("QUESTION_ID", questionId);

        return result;
    }


    @RequestMapping(value = "questions/insertques", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, String> insertQues(@Validated @RequestBody Question question) {
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

    @Transactional
    @RequestMapping(value = "question/tagName/{tagName}", method = RequestMethod.POST)
    public Question addQuestionByTagName(@RequestBody
                                       @Validated Question question,
                                       BindingResult bindingResult,
                                       @PathVariable("tagName")
                                       @NotNull(message = "tagName cannot be null")
                                       @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName) {
        BindResultUtils.validData(bindingResult);

        logger.info("Start invoke addQuestionByTagName()");
        // 先添加资源表的记录
        questionService.insertQues(question);
        // 再添加资源关联表中的记录
        TagResource tagResource = new TagResource("", tagName, question.getQuestionId(), ResourceTypeEnum.QUESTION.getResourceType());
        tagResourceService.addTagResource(tagResource);

        return question;
    }


    @RequestMapping(value = "questions/updateques", method = RequestMethod.PUT)
    @ResponseBody
    public Map<String, String> updateQues(@Validated @RequestBody Question question) {
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


    @Transactional
    @RequestMapping(value = "question/public/{questionId}", method = RequestMethod.PUT)
    public Map<String, String> updateIsPublicById(@PathVariable("questionId")
                                                  @Size(min = 36, max = 36, message = "questionId length should be 36") String questionId,
                                                  HttpServletRequest request) {
        logger.info("Start invoke updateIsPublicById()");
        if (!authorityService.isAdamin(request)) {
            throw new ValidationException("No permission to perform this operation");
        }

        questionService.updateIsPublicById(questionId);

        Map<String, String> result = new HashMap<>();
        result.put("questionId", questionId);

        return result;
    }

}
