package cn.edu.zju.sishi.message.UserMessage;

/**
 * @author lemon
 * @date 2021/3/19
 */
public class UserPasswordSaltResponse {


  private String password;

  private String salt;

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getSalt() {
    return salt;
  }

  public void setSalt(String salt) {
    this.salt = salt;
  }

  @Override
  public String toString() {
    return "UserPasswordSaltResponse{" +
      "password='" + password + '\'' +
      ", salt='" + salt + '\'';
  }
}