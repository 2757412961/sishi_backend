package cn.edu.zju.sishi.controller;

import cn.edu.zju.sishi.commons.UserContext;
import cn.edu.zju.sishi.commons.utils.FileUtils;
import cn.edu.zju.sishi.commons.utils.HttpServletRequestUtil;
import cn.edu.zju.sishi.config.NginxConfig;
import cn.edu.zju.sishi.exception.ValidationException;
import cn.edu.zju.sishi.message.UserMessage.*;
import cn.edu.zju.sishi.passport.annotation.AuthController;
import cn.edu.zju.sishi.service.AuthorityService;
import cn.edu.zju.sishi.service.UserService;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * @author lemon
 * @date 2021/3/17
 */

@RestController
@AuthController
@Validated
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private NginxConfig nginxConfig;

    @Autowired
    private UserService userService;

    @Autowired
    private AuthorityService authorityService;

    @PostMapping("/user/file")
    public JSONObject uploadFile(HttpServletRequest request) {
        logger.info("start invoke uploadFile()");

        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("file");
        // 判断空指针的情况
        if (multipartFile == null || multipartFile.isEmpty()) {
            throw new ValidationException("请求不是表单格式，或者未上传文件对象");
        }

        // name
        String filename = multipartFile.getOriginalFilename();
        String userId = authorityService.getUserId(request);
        // local file
        File localFile = FileUtils.newFile(nginxConfig.getLinuxRoot() + nginxConfig.getWorkspacePath() + userId + File.separator + filename);
        if (localFile.exists()){
            throw new ValidationException("上传文件已经存在");
        }
        try {
            multipartFile.transferTo(localFile);
        } catch (IOException e) {
            e.printStackTrace();
            throw new ValidationException("上传失败：" + e.getMessage());
        }

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("url", nginxConfig.getHttpHead() + nginxConfig.getWorkspacePath() + userId + File.separator + filename);

        return jsonObject;
    }

    @PostMapping("/user/avatar")
    public JSONObject updateUserAvatar(HttpServletRequest request) {
        logger.info("start invoke updateUserAvatar()");

        JSONObject jsonObject = uploadFile(request);

        UpdateUserRequest updateUserRequest = new UpdateUserRequest();
        updateUserRequest.setAvatar((String) jsonObject.get("url"));
        String userId = authorityService.getUserId(request);
        userService.updateUserInfo(userId, updateUserRequest);

        return jsonObject;
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "user", method = RequestMethod.PUT)
    public void updateUserInfo(HttpServletRequest request,
                               @Validated @RequestBody UpdateUserRequest updateUserRequest,
                               BindingResult bindingResult) {
        logger.info("start invoke updateUserInfo()");
        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            String id = HttpServletRequestUtil.getAndCheckIdFromRequest(request).getUserId();
            userService.updateUserInfo(id, updateUserRequest);
        }
    }


    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(value = "user/password", method = RequestMethod.PUT)
    public void updatePassword(HttpServletRequest request,
                               @Validated @RequestBody UpdatePasswordRequest updatePasswordRequest,
                               BindingResult bindingResult) {
        logger.info("start invoke updatePassword()");
        if (bindingResult.hasErrors()) {
            throw new javax.validation.ValidationException(bindingResult.getAllErrors().get(0).getDefaultMessage());
        } else {
            //get id from request
            String id = HttpServletRequestUtil.getAndCheckIdFromRequest(request).getUserId();
            if (!updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getConfirmPassword())) {
                throw new javax.validation.ValidationException("The new password and confirm password are inconsistent");
            }
            //check if the new password is the same as old password
            if (updatePasswordRequest.getNewPassword().equals(updatePasswordRequest.getOldPassword())) {
                throw new javax.validation.ValidationException("The new password cannot be the same as the old password");
            } else {
                userService.updatePassword(id, updatePasswordRequest);
            }

        }
    }


    @ResponseBody
    @RequestMapping(value = "user/userList", method = RequestMethod.GET)
    public List<UserListResponse> getUserList(
            @RequestParam(value = "start", required = false, defaultValue = "0")
            @Min(value = 0, message = "start must not be negative") int start,
            @RequestParam(value = "length", required = false, defaultValue = "10")
            @Min(value = 1, message = "length must be larger than 0")
            @Max(value = 1000, message = "the number of return size should be no more than 1000") int length,
            HttpServletRequest request) {
        logger.info("start invoke getUserList()");
        String id = HttpServletRequestUtil.getAndCheckIdFromRequest(request).getUserId();
        List<UserListResponse> userList = userService.getUserList(start, length, id);
        return userList;
    }

    @ResponseBody
    @RequestMapping(value = "user/scoreList", method = RequestMethod.GET)
    public List<ScoreResponse> getTopTenByScore() {
        List<ScoreResponse> scoreResponse = userService.getTopTenByScore();
        return scoreResponse;
    }


    @ResponseBody
    @RequestMapping(value = "user", method = RequestMethod.GET)
    public UserInfoResponse getUserInfo(HttpServletRequest request) {
        logger.info("start invoke getUserInfo()");
        String id = HttpServletRequestUtil.getAndCheckIdFromRequest(request).getUserId();
        UserInfoResponse userInfoResponse = userService.getUserInfo(id);
        return userInfoResponse;
    }


    @ResponseBody
    @RequestMapping(value = "user/{userId}/score", method = RequestMethod.GET)
    public ScoreResponse getScore(HttpServletRequest request) {
        logger.info("start invoke getScore()");
        UserContext userContext = HttpServletRequestUtil.getAndCheckIdFromRequest(request);
        String userId = userContext.getUserId();

        String id = HttpServletRequestUtil.getAndCheckIdFromRequest(request).getUserId();
        ScoreResponse score = userService.getScore(id);
        return score;
    }
}




