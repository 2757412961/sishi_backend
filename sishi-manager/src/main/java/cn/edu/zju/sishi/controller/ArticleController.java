package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.entity.Article;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.ArticleService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lemon
 * @date 2021/3/5
 */
@RestController
@Validated
public class ArticleController {
  private static final String ID = "id";
  private final Logger logger = LoggerFactory.getLogger(this.getClass());

  @Autowired
  ArticleService articleService;

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

  @RequestMapping(value = "articles", method = RequestMethod.GET)
  @ResponseBody
  public JSONObject listArticles(
          @Min(value = 0, message = "start must not be negative")
          @RequestParam(value = "start", required = false, defaultValue = "0")
          @Min(value = 0, message = "start must not be negative") int start,
          @RequestParam(value = "length", required = false, defaultValue = "10")
          @Min(value = 1, message = "length must be larger than 0")
          @Max(value = 1000, message = "the number of return size should be no more than 1000") int length) {
    logger.info("start invoke listArticles()");
    JSONObject result = new JSONObject();
    List<Article> articles = articleService.listArticles(start, length);
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
          @Max(value = 1000, message = "the number of return size should be no more than 1000") int length
  ) {
    logger.info("start invoke listArticlesByTagName()");
    JSONObject result = new JSONObject();
    List<Article> articlesByTagName = articleService.getArticlesByTagName(tagName, start, length);
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
}