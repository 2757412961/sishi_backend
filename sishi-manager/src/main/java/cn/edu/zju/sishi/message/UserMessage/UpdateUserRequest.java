package cn.edu.zju.sishi.message.UserMessage;

import cn.edu.zju.sishi.commons.MobilePhoneConstraint;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * @author lemon
 * @date 2021/3/17
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UpdateUserRequest {


  @Size(min = 5, max = 50, message = "userName length should be in 5 and 50")
  private String userName;


  @MobilePhoneConstraint

  private String mobile;

  private String email;

  private  String partyBranch;

  private String avatar;


  public String getUserName() { return userName; }

  public void setUserName(String userName) { this.userName = userName; }

  public String getMobile() { return mobile; }

  public void setMobile(String mobile) { this.mobile = mobile; }

  public String getEmail() { return email; }

  public void setEmail(String email) { this.email = email; }

  public String getPartyBranch() { return partyBranch; }

  public void setPartyBranch(String partyBranch) { this.partyBranch = partyBranch; }

  public String getAvatar() { return avatar; }

  public void setAvatar(String avatar) { this.avatar = avatar; }

  }


