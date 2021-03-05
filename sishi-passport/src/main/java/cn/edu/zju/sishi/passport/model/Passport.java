package cn.edu.zju.sishi.passport.model;

import java.io.Serializable;

public class Passport implements Serializable {
  private static final long serialVersionUID = 1L;
  private String userId = "";
  private String token = "";

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
}
