package cn.edu.zju.sishi.service;

import javax.servlet.http.HttpServletRequest;

public interface AuthorityService {

    boolean isAdamin(HttpServletRequest request);

    boolean isGuest(HttpServletRequest request);

    String getUserId(HttpServletRequest request);

    String getRoleType(HttpServletRequest request);

}
