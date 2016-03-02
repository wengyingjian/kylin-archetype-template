package com.a.b.c.controller;

import com.a.b.c.common.enums.UserType;
import com.wengyingjian.kylin.common.model.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.a.b.c.common.model.User;
import com.a.b.c.service.UserService;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by wengyingjian on 16/2/2.
 */

@Controller
@RestController
@RequestMapping("user")
public class UserController {


    private Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    private UserService userService;

    /**
     * 查找用户
     *
     * @param request
     * @param userId  用户id
     * @return
     */
    @RequestMapping(value = "info", method = RequestMethod.GET)
    public Result<List<User>> findUser(HttpServletRequest request,//
                                       @RequestParam(value = "uid", required = false) Integer userId) {//

        return userService.findUser(userId);
    }

    /**
     * 修改用户信息
     *
     * @param request
     * @param uid      需要修改的用户id,required
     * @param userName 修改后的用户名字,not required
     * @param userType 修改后的用户类型,not required
     * @return
     */
    @RequestMapping(value = "info", method = RequestMethod.POST)
    public Result<User> modifyUser(HttpServletRequest request,
                                   @RequestParam("user_id") Integer uid,//
                                   @RequestParam(value = "user_name", required = false) String userName,
                                   @RequestParam(value = "user_type", required = false) Integer userType) {

        return userService.modifyUser(uid, userName, userType);
    }

    /**
     * @param request
     * @param userName
     * @param userType
     * @return
     */
    @RequestMapping(value = "bean", method = RequestMethod.POST)
    public Result<User> addUser(HttpServletRequest request,
                                @RequestParam("user_name") String userName,
                                @RequestParam(value = "user_type", required = false) Integer userType) {
        if (userType == null) {
            userType = UserType.NORMAL.getCode();
        }
        return userService.addUser(userName, userType);
    }
}
