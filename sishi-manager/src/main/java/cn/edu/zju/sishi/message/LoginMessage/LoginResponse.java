package cn.edu.zju.sishi.message.LoginMessage;

public class LoginResponse {
  private String userId;
  private String token;
  private String userName;
  private String avatar;
  private String partyBranch;
  private String roleType;
  private String mobile;
  private String email;
  private int score;
  private String honor;

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getAvatar() {
    return avatar;
  }

  public void setAvatar(String avatar) {
    this.avatar = avatar;
  }

  public String getPartyBranch() {
    return partyBranch;
  }

  public void setPartyBranch(String partyBranch) {
    this.partyBranch = partyBranch;
  }

  public String getRoleType() {
    return roleType;
  }

  public void setRoleType(String roleType) {
    this.roleType = roleType;
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

  public int getScore() {
    return score;
  }

  public void setScore(int score) {
    this.score = score;
  }

  public String getHonor() {
    return honor;
  }

  public void setHonor(String honor) {
    this.honor = honor;
  }
}
