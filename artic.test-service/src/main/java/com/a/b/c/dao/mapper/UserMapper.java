package com.a.b.c.dao.mapper;

import com.a.b.c.common.model.User;
import com.a.b.c.common.model.query.UserQuery;

import java.util.List;

/**
 * Created by wengyingjian on 16/2/1.
 */
public interface UserMapper {

    int insertSelective(User user);

    List<User> selectUsers(UserQuery userQuery);

    int updateUser(User user);

}
