package cn.edu.zju.sishi.message.UserMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author lemon
 * @date 2021/3/19
 */
public class UserListResponse {

//  private int totalCount;

  @Size(min = 36, max = 36, message = "id length should be 36")
  private String userId;


  @NotNull(message = "userName cannot be null")
  @Size(min = 5, max = 50, message = "userName length should be in 5 and 50")
  private String userName;

  private String email;

  private int score;

  private  String partyBranch;

  private String studentNumber;

  private String grade;

  private String studentName;

  private Long createTime;

  private Long updateTime;

  public String getStudentName() { return studentName; }

  public void setStudentName(String studentName) { this.studentName = studentName; }



  public String getStudentNumber() { return studentNumber; }

  public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

  public String getGrade() { return grade; }

  public void setGrade(String grade) { this.grade = grade; }

  public String getUserId() { return userId; }

  public void setUserId(String userId) { this.userId = userId; }

  public String getUserName() { return userName; }

  public void setUserName(String userName) { this.userName = userName; }

  public String getEmail() { return email; }

  public void setEmail(String email) { this.email = email; }

  public int getScore() { return score; }

  public void setScore(int score) { this.score = score; }

  public String getPartyBranch() { return partyBranch; }

  public void setPartyBranch(String partyBranch) { this.partyBranch = partyBranch; }

  public Long getCreateTime() { return createTime; }

  public void setCreateTime(Long createTime) { this.createTime = createTime; }

  public Long getUpdateTime() { return updateTime; }

  public void setUpdateTime(Long updateTime) { this.updateTime = updateTime; }
}
