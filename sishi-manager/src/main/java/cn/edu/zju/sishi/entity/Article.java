package cn.edu.zju.sishi.entity;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author lemon
 * @date 2021/3/5
 */
public class Article implements Serializable {
  private String articleId;

  @NotNull(message = "article title cannot be null")
  @Size(min = 1, max = 500, message = "article title length should be between 1 and 500")
  private String articleTitle;


  @NotNull(message = "article Author cannot be null")
  @Size(min = 1, max = 200, message = "article Author length should be between 1 and 200")
  private String articleAuthor;


  @NotNull(message = "article Content cannot be null")
  private String articleContent;

  private long articleCreateTime;

  private long articlePublishTime;

  public String getArticleId() {
    return articleId;
  }

  public void setArticleId(String articleId) {
    this.articleId = articleId;
  }

  public String getArticleTitle() {
    return articleTitle;
  }

  public void setArticleTitle(String articleTitle) {
    this.articleTitle = articleTitle;
  }

  public String getArticleAuthor() {
    return articleAuthor;
  }

  public void setArticleAuthor(String articleAuthor) {
    this.articleAuthor = articleAuthor;
  }

  public String getArticleContent() {
    return articleContent;
  }

  public void setArticleContent(String articleContent) {
    this.articleContent = articleContent;
  }

  public long getArticleCreateTime() {
    return articleCreateTime;
  }

  public void setArticleCreateTime(long articleCreateTime) {
    this.articleCreateTime = articleCreateTime;
  }

  public long getArticlePublishTime() {
    return articlePublishTime;
  }

  public void setArticlePublishTime(long articlePublishTime) {
    this.articlePublishTime = articlePublishTime;
  }
}
