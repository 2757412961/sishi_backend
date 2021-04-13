package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.commons.utils.BindResultUtils;
import cn.edu.zju.sishi.commons.utils.LogicUtil;
import cn.edu.zju.sishi.entity.Article;
import cn.edu.zju.sishi.entity.TagResource;
import cn.edu.zju.sishi.enums.ResourceTypeEnum;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.passport.annotation.AuthController;
import cn.edu.zju.sishi.service.ArticleService;
import cn.edu.zju.sishi.service.AuthorityService;
import cn.edu.zju.sishi.service.TagResourceService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lemon
 * @date 2021/3/5
 */
@RestController
@Validated
@AuthController
public class ArticleController {
  private static final String ID = "id";
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  ArticleService articleService;

  @Autowired
  private TagResourceService tagResourceService;

  @Autowired
  private AuthorityService authorityService;

  @ResponseBody
  @RequestMapping(value = "article", method = RequestMethod.POST)
  public Map<String, String> addArticle(@RequestBody @Validated Article article, BindingResult bindingResult) {
    if (bindingResult.hasErrors()) {
      throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
    }
    logger.info("start invoke addArticle()");
    Map<String, String> result = new HashMap<>();
    articleService.addArticle(article);
    result.put(ID, article.getArticleId());
    return result;
  }

  @Transactional
  @ResponseBody
  @RequestMapping(value = "article/tagName/{tagName}", method = RequestMethod.POST)
  public Article addArticleByTagName(@RequestBody
                                     @Validated  Article article,
                                     BindingResult bindingResult,
                                     @PathVariable("tagName")
                                     @NotNull(message = "tagName cannot be null")
                                     @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName,
                                     HttpServletRequest request) {
    BindResultUtils.validData(bindingResult);

    logger.info("Start invoke addArticleByTagName()");
    // 先添加资源表的记录
    articleService.addArticle(article);
    // 再添加资源关联表中的记录
    TagResource tagResource = new TagResource("", tagName, article.getArticleId(), ResourceTypeEnum.ARTICLE.getResourceType(), authorityService.getUserId(request));
    tagResourceService.addTagResource(tagResource);

    return article;
  }

  @RequestMapping(value = "articles", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject listArticles(
    @RequestParam(value = "start", required = false, defaultValue = "0")
    @Min(value = 0, message = "start must not be negative") int start,
    @RequestParam(value = "length", required = false, defaultValue = "10")
    @Min(value = 1, message = "length must be larger than 0")
    @Max(value = 1000, message = "the number of return size should be no more than 1000") int length,
    HttpServletRequest request) {
    logger.info("start invoke listArticles()");
    JSONObject result = new JSONObject();
    boolean isAdministrator = authorityService.isAdamin(request);
    List<Article> articles = articleService.listArticles(start, length, LogicUtil.getLogicByIsAdmins(isAdministrator));


    int count = articles.size();
    result.put("totalCount", count);
    result.put("articles", articles);
    return result;
  }

  @ResponseBody
  @RequestMapping(value = "article/{articleId}", method = RequestMethod.GET)
  public Article getArticle(@PathVariable(value = "articleId") String articleId) {
    logger.info("start invoke getArticle()");
    Article articleEntity = articleService.getArticle(articleId);
    if (articleEntity != null) {
      return articleEntity;
    } else {
      throw new ResourceNotFoundException(HttpStatus.NOT_FOUND.value(), "article", "id", articleId);
    }
  }


  @RequestMapping(value = "articles/tagName/{tagName}", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject getArticlesByTagName(
          @PathVariable(value = "tagName") String tagName,
          @RequestParam(value = "start", required = false, defaultValue = "0")
          @Min(value = 0, message = "start must not be negative") int start,
          @RequestParam(value = "length", required = false, defaultValue = "10")
          @Min(value = 1, message = "length must be larger than 0")
          @Max(value = 1000, message = "the number of return size should be no more than 1000") int length,
          HttpServletRequest request) {
    logger.info("start invoke listArticlesByTagName()");
    JSONObject result = new JSONObject();
    boolean isAdministrator = authorityService.isAdamin(request);
    List<Article> articlesByTagName = articleService.getArticlesByTagName(tagName, start, length, LogicUtil.getLogicByIsAdmins(isAdministrator));
    int count = articlesByTagName.size();
    result.put("totalCount", count);
    result.put("articles", articlesByTagName);
    return result;
  }

  @RequestMapping(value = "article/{articleId}", method = RequestMethod.DELETE)
  public Map<String, String> dropArticle(
          @PathVariable(value = "articleId") String articleId) {
    logger.info("start invoke dropArticle()");
    Map<String, String> result = new HashMap<>();
    articleService.dropArticle(articleId);
    result.put(ID, articleId);
    return result;
  }

  @Transactional
  @RequestMapping(value = "article/{articleId}/tagName/{tagName}", method = RequestMethod.DELETE)
  public Map<String, String> deleteArticleByTagName(@PathVariable("articleId")
                                                    @Size(min = 36, max = 36, message = "articleId length should be 36") String articleId,
                                                    @PathVariable("tagName")
                                                    @NotNull(message = "tagName cannot be null")
                                                    @Size(min = 1, max = 200, message = "tagName length should be between 1 and 200") String tagName) {
    logger.info("Start invoke deleteArticleByTagName()");
    // 先删除资源关联表中的记录
    tagResourceService.deleteTagResource(tagName, articleId);
    // 再删除资源表的记录
    articleService.dropArticle(articleId);

    Map<String, String> result = new HashMap<>();
    result.put(ID, articleId);

    return result;
  }
  @Transactional
  @RequestMapping(value = "article/public/{articleId}", method = RequestMethod.PUT)
  public Map<String, String> updateIsPublicById(@PathVariable("articleId")
                                                @Size(min = 36, max = 36, message = "articleId length should be 36") String articleId,
                                                HttpServletRequest request) {
    logger.info("Start invoke updateIsPublicById()");
    if (!authorityService.isAdamin(request)) {
      throw new ValidationException("No permission to perform this operation");
    }

    articleService.updateIsPublicById(articleId);

    Map<String, String> result = new HashMap<>();
    result.put(ID, articleId);

    return result;
  }
}