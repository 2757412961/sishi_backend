package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.dao.UserDao;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final static String USER_ID = "userId";
    private final static String ADMIN = "admin";
    private final static String GUEST = "guest";

    @Autowired
    private UserDao userDao;

    @Override
    public boolean isAdamin(HttpServletRequest request) {
        String userId = request.getHeader(USER_ID);
        String userRoleType = userDao.getUserRoleType(userId);

        if (!StringUtils.isEmpty(userRoleType) && userRoleType.equals(ADMIN)) {
            return true;
        }

        return false;
    }

    @Override
    public boolean isGuest(HttpServletRequest request) {
        String userId = request.getHeader(USER_ID);
        String userRoleType = userDao.getUserRoleType(userId);

        if (!StringUtils.isEmpty(userRoleType) && userRoleType.equals(GUEST)) {
            return true;
        }

        return false;
    }

    @Override
    public String getUserId(HttpServletRequest request) {
        return request.getHeader(USER_ID);
    }

    @Override
    public String getRoleType(HttpServletRequest request) {
        String userId = request.getHeader(USER_ID);
        if (StringUtils.isEmpty(userId)) {
            throw new ValidationException("未找到该用户");
        }

        return userDao.getUserRoleType(userId);
    }


}
