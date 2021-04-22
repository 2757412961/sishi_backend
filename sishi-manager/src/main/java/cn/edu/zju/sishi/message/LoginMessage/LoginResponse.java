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
  private String studentNumber;
  private String unit;
//  private String grade;


  public String getUnit() { return unit; }

  public void setUnit(String unit) { this.unit = unit; }

  private String studentName;

  public String getStudentName() { return studentName; }

  public void setStudentName(String studentName) { this.studentName = studentName; }

  public String getStudentNumber() { return studentNumber; }

  public void setStudentNumber(String studentNumber) { this.studentNumber = studentNumber; }

//
//  public String getGrade() { return grade; }
//
//  public void setGrade(String grade) { this.grade = grade; }

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
