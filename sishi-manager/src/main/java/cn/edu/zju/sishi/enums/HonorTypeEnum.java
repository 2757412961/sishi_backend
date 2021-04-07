package cn.edu.zju.sishi.enums;

import cn.edu.zju.sishi.exception.ValidationException;

/**
 * @author lemon
 * @date 2021/3/17
 */
public enum  HonorTypeEnum {

  FLAG("Flag"),
  TICKET("Ticket"),
  MEDAL("Medal");


  private final String honorType;
  private static final String honorTypes ="[Flag,Ticket,Medal]";

  HonorTypeEnum(String honorType) { this.honorType = honorType; }

  public String getHonor() {return honorType;}

  public static boolean isValidType(String honorType) {
    for (HonorTypeEnum honorTypeEnum : HonorTypeEnum.values()) {
      if (honorTypeEnum.honorType.equalsIgnoreCase(honorType)) {
        return true;
      }
    }
    throw  new ValidationException(String.format("honorType should be in %s", HonorTypeEnum.honorTypes));
  }
}

