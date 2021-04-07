package cn.edu.zju.sishi.message.UserMessage;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

/**
 * @author lemon
 * @date 2021/3/18
 */
@NoArgsConstructor
@AllArgsConstructor
public class UpdatePasswordRequest {


  @NotNull(message = "old password cannot be null")
  private String oldPassword;

  @NotNull(message = "new password cannot be null")
  private String newPassword;

  @NotNull(message = "confirm password cannot be null")
  private String confirmPassword;

  //todo 是否要passwordConstraint?

  public String getOldPassword() {
    return oldPassword;
  }

  public void setOldPassword(String oldPassword) {
    this.oldPassword = oldPassword;
  }

  public String getNewPassword() {
    return newPassword;
  }

  public void setNewPassword(String newPassword) {
    this.newPassword = newPassword;
  }

  public String getConfirmPassword() {
    return confirmPassword;
  }

  public void setConfirmPassword(String confirmPassword) {
    this.confirmPassword = confirmPassword;
  }
}




