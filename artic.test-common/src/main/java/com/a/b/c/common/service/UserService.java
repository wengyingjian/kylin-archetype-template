package com.a.b.c.common.service;

import com.wengyingjian.kylin.common.model.Result;
import com.a.b.c.common.model.User;
import com.a.b.c.common.model.query.UserQuery;

import java.util.List;


/**
 * Created by wengyingjian on 16/2/1.
 */
public interface UserService {

    /**
     * 根据userQuery查找
     *
     * @param userQuery
     * @return
     */
    Result<List<User>> findUsers(UserQuery userQuery);

    /**
     * 添加用户
     *
     * @param user
     * @return
     */
    Result<User> addUser(User user);

    /**
     * 修改用户信息
     *
     * @param user
     * @return
     */
    Result<User> modifyUser(User user);

}
