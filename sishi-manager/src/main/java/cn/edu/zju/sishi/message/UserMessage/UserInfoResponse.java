package cn.edu.zju.sishi.message.UserMessage;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author lemon
 * @date 2021/3/17
 */
public class UserInfoResponse {

  @Size(min = 36, max = 36, message = "id length should be 36")
  private String userId;

  @NotNull(message = "userName cannot be null")
  @Size(min = 5, max = 50, message = "userName length should be in 5 and 50")
  private String userName;

  private Long createTime;

  private Long updateTime;

  private String mobile;

  private String email;

  private int score;

  private  String partyBranch;

  private String avatar;

  private String honor;

  private String roleType;

  private String studentNumber;

  private String idNumber;

  private String grade;

  private String studentName;

  public String getStudentName() { return studentName; }

  public void setStudentName(String studentName) { this.studentName = studentName; }

  public String getStudentNumber() { return studentNumber; }

  public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

  public String getIdNumber() { return idNumber; }

  public void setIdNumber(String idNumber) { this.idNumber = idNumber; }

  public String getGrade() { return grade; }

  public void setGrade(String grade) { this.grade = grade; }

  public String getRoleType() { return roleType; }

  public void setRoleType(String roleType) { this.roleType = roleType; }

  public String getUserId() { return userId; }

  public void setUserId(String userId) { this.userId = userId; }

  public String getUserName() { return userName; }

  public void setUserName(String userName) { this.userName = userName; }

  public Long getCreateTime() { return createTime; }

  public void setCreateTime(Long createTime) { this.createTime = createTime; }

  public Long getUpdateTime() { return updateTime; }

  public void setUpdateTime(Long updateTime) { this.updateTime = updateTime; }

  public String getMobile() { return mobile; }

  public void setMobile(String mobile) { this.mobile = mobile; }

  public String getEmail() { return email; }

  public void setEmail(String email) { this.email = email; }

  public int getScore() { return score; }

  public void setScore(int score) { this.score = score; }

  public String getPartyBranch() { return partyBranch; }

  public void setPartyBranch(String partyBranch) { this.partyBranch = partyBranch; }

  public String getAvatar() { return avatar; }

  public void setAvatar(String avatar) { this.avatar = avatar; }

  public String getHonor() { return honor; }

  public void setHonor(String honor) { this.honor = honor; }
}
