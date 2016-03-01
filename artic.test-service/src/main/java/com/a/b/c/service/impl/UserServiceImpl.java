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
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * Created by wengyingjian on 16/2/1.
 */
@RemoteService(serviceType = ServiceType.HESSIAN, serviceInterface = UserService.class, exportPath = "/userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Result<List<User>> findUsers(UserQuery userQuery) {
        if(userQuery==null){
            return ResultUtil.genCommonError("userQuery bean can not be null");
        }
        return ResultUtil.genSuccessResult(userDao.selectUsers(userQuery));
    }

    @Override
    public Result<Boolean> addUser(User user) {
        if (user == null) {
            return ResultUtil.genCommonError("target user can not be null");
        }
        int affectedRows = userDao.insertSelective(user);
        boolean result = affectedRows == 0 ? false : true;
        return ResultUtil.genSuccessResult(result);
    }

    @Override
    public Result<Boolean> modifyUser(User user) {
        int affectedRpws = userDao.updateUser(user);
        boolean result = affectedRpws == 0 ? false : true;
        return ResultUtil.genSuccessResult(result);
    }
}
