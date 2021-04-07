package cn.edu.zju.sishi.service.impl;
import cn.edu.zju.sishi.dao.ArticleDao;
import cn.edu.zju.sishi.entity.Article;
import cn.edu.zju.sishi.exception.ResourceNotFoundException;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

/**
 * @author lemon
 * @date 2021/3/5
 */
@Service
public class ArticleServiceImpl implements ArticleService {
  private static final String ARTICLE_ALREADY_EXISTS_ERROR_MSG
          = "Article with title %s already exists in the article list!";
  @Autowired
  private ArticleDao articleDao;

  @Transactional
  public void addArticle(Article articleEntity) {

    String title = articleEntity.getArticleTitle();
    Article existArticle = articleDao.getArticleByTitle(title);
    if (existArticle != null) {
      throw new ValidationException(String.format(ARTICLE_ALREADY_EXISTS_ERROR_MSG, title));
    }
    articleEntity.setArticleId(UUID.randomUUID().toString());
    long publishTime = Instant.now().toEpochMilli();
    long createTime = Instant.now().toEpochMilli();
    articleEntity.setArticleCreateTime(createTime);
    articleEntity.setArticlePublishTime(publishTime);
    articleDao.addArticle(articleEntity);
  }

  @Override
  public List<Article> listArticles(int start, int length, String startTime, String endTime, String logicSymbol) {
    return articleDao.listArticles(length, length * start, startTime, endTime, logicSymbol );
  }

  @Override
  public Article getArticle(String articleId) {
    return articleDao.getArticle(articleId);
  }

  @Override
  public List<Article> getArticlesByTagName(String tagName, int start, int length, String logicSymbol) {
    return articleDao.getArticlesByTagName(tagName, length, length * start, logicSymbol);
  }

  @Override
  public void dropArticle(String articleId) {
    Article articleEntity = articleDao.getArticle(articleId);
    if (articleEntity == null) {
      throw new ResourceNotFoundException("article", "id", articleId);
    }
    articleDao.dropArticle(articleId);
  }

  @Override
  public int updateIsPublicById(String articleId) { return articleDao.updateIsPublicById(articleId); }

}

