package com.a.b.c.service.impl;

import com.wengyingjian.kylin.common.model.Result;
import com.wengyingjian.kylin.common.utils.ResultUtil;
import com.wengyingjian.kylin.rpc.server.annotation.RemoteService;
import com.wengyingjian.kylin.rpc.server.annotation.ServiceType;
import com.a.b.c.common.enums.UserType;
import com.a.b.c.common.model.User;
import com.a.b.c.common.model.query.UserQuery;
import com.a.b.c.common.service.UserService;
import com.a.b.c.dao.UserDao;
import com.wengyingjian.kylin.util.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wengyingjian on 16/2/1.
 */
@RemoteService(serviceType = ServiceType.HESSIAN, serviceInterface = UserService.class, exportPath = "/userService")
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserDao userDao;

    @Override
    public Result<List<User>> findUsers(UserQuery userQuery) {
        if (userQuery == null) {
            return ResultUtil.genCommonError("userQuery bean can not be null");
        }
        List<User> userList = userDao.selectUsers(userQuery);
        logger.debug("userList=[{}]", JsonUtil.getJsonFromObject(userList));
        return ResultUtil.genSuccessResult(userList);
    }

    @Override
    public Result<User> addUser(User user) {
        if (user == null) {
            return ResultUtil.genCommonError("target user can not be null");
        }
        int affectedRows = userDao.insertSelective(user);
        return ResultUtil.genSuccessResult(user);
    }

    @Override
    public Result<User> modifyUser(User user) {
        int affectedRpws = userDao.updateUser(user);
        return ResultUtil.genSuccessResult(user);
    }
}
