package cn.edu.zju.sishi.service.impl;

import cn.edu.zju.sishi.dao.UserDao;
import cn.edu.zju.sishi.service.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

@Service
public class AuthorityServiceImpl implements AuthorityService {

    private final static String USER_ID = "userId";
    private final static String ADMIN = "admin";

    @Autowired
    private UserDao userDao;

    @Override
    public boolean isAdamin(HttpServletRequest request) {
        String userId = request.getHeader(USER_ID);
        String userRoleType = userDao.getUserRoleType(userId);

        if (userRoleType.equals(ADMIN)) {
            return true;
        }

        return false;
    }

    @Override
    public String getUserId(HttpServletRequest request) {
        return request.getHeader(USER_ID);
    }


}
