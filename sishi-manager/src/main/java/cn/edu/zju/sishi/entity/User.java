package cn.edu.zju.sishi.entity;

import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class User implements Serializable {
  
  @Size(min = 36, max = 36, message = "id length should be 36")
  private String userId;

  @NotNull(message = "userName cannot be null")
  @Size(min = 5, max = 50, message = "userName length should be in 5 and 50")
  private String userName;

  private Long createTime;

  private Long updateTime;

  @NotNull(message = "password cannot be null")
  private String password;

  private String salt;

  private String mobile;

  private String email;

  private int score;

  private  String partyBranch;

  private String avatar;

  private String honor;

  private String roleType;

  public String getSalt() { return salt; }

  public void setSalt(String salt) { this.salt = salt; }

  public String getRoleType() { return roleType; }

  public void setRoleType(String roleType) { this.roleType = roleType; }



  public int getScore() { return score; }

  public void setScore(int score) { this.score = score; }

  public String getPartyBranch() { return partyBranch; }

  public void setPartyBranch(String partyBranch) { this.partyBranch = partyBranch; }

  public String getAvatar() { return avatar; }

  public void setAvatar(String avatar) { this.avatar = avatar; }

  public String getHonor() { return honor; }

  public void setHonor(String honor) { this.honor = honor; }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public Long getCreateTime() {
    return createTime;
  }

  public void setCreateTime(Long createTime) {
    this.createTime = createTime;
  }

  public Long getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(Long updateTime) {
    this.updateTime = updateTime;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getMobile() {
    return mobile;
  }

  public void setMobile(String mobile) {
    this.mobile = mobile;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
}
