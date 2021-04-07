package cn.edu.zju.sishi.service;

import cn.edu.zju.sishi.entity.Article;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lemon
 * @date 2021/3/5
 */
public interface ArticleService {
  void addArticle(Article articleEntity);

  List<Article> listArticles(int start, int length, String startTime, String endTime, String logicSymbol);

  List<Article> getArticlesByTagName(String tagName, int start, int length, String logicSymbol);
  
  Article getArticle(String articleId);

  void dropArticle(String articleId);

  int updateIsPublicById(String articleId);

}
