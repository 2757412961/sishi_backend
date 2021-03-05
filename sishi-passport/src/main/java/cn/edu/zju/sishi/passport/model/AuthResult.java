package cn.edu.zju.sishi.passport.model;

import java.io.Serializable;

public class AuthResult implements Serializable {
  private static final long serialVersionUID = 3338533726508799286L;
  private String userId;
  private String token;
  private String username;
  private String code;
  private String codeDesc;

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

  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getCode() {
    return code;
  }

  public void setCode(String code) {
    this.code = code;
  }

  public String getCodeDesc() {
    return codeDesc;
  }

  public void setCodeDesc(String codeDesc) {
    this.codeDesc = codeDesc;
  }
}
