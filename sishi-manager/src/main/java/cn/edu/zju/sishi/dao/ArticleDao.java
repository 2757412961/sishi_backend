package cn.edu.zju.sishi.dao;

import cn.edu.zju.sishi.entity.Article;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author lemon
 * @date 2021/3/5
 */
@Mapper
@Component
public interface ArticleDao {
  void addArticle(@Param("article") Article articleEntity);

  List<Article> listArticles(@Param("length") int length,
                             @Param("offset") int offset,
                             @Param("logicSymbol") String logicSymbol);


  Article getArticle(@Param("articleId") String articleId);

  Article getArticleByTitle(@Param("title") String title);

  List<Article> getArticlesByTagName(@Param("tagName") String tagName,
                                     @Param("start") int start,
                                     @Param("length") int length,
                                     @Param("logicSymbol") String logicSymbol);

  void dropArticle(@Param("articleId") String articleId);

  int updateIsPublicById(@Param("articleId") String articleId);
}
