package com.a.b.c.service;

import com.wengyingjian.kylin.common.model.Result;
import com.wengyingjian.kylin.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import com.a.b.c.common.enums.UserType;
import com.a.b.c.common.model.User;
import com.a.b.c.common.model.query.UserQuery;

import java.util.List;

/**
 * Created by wengyingjian on 16/2/2.
 */
@Service
public class UserService {

    private Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private com.a.b.c.common.service.UserService userServiceRpc;


    /**
     * 查找用户/根据用户id查找用户
     *
     * @param userId 用户id,为空时代表查找所有用户
     * @return
     */
    public Result<List<User>> findUser(Integer userId) {
        UserQuery userQuery = new UserQuery();
        userQuery.setId(userId);
        return userServiceRpc.findUsers(userQuery);
    }

    /**
     * 验证用户的类型是否有效
     *
     * @param userType
     * @return
     */
    private boolean validateUserType(Integer userType) {
        if (userType == null) {
            return false;
        }
        for (UserType uType : UserType.values()) {
            if (userType == uType.getCode()) {
                return true;
            }
        }
        return false;
    }

    /**
     * 修改指定id用户信息
     *
     * @param uid
     * @param userName
     * @param userType
     * @return
     */
    public Result<User> modifyUser(Integer uid, String userName, Integer userType) {
        if (uid == null) {
            logger.error("parameter uid can not be  null while modify user");
            return ResultUtil.genCommonError("parameter uid can not be  null while modify user");
        }
        if (StringUtils.isEmpty(userName) && userType == null) {
            logger.info("nothing to update : userUser is empty and user type is null");
            return ResultUtil.genCommonError("nothing to update");
        }
        if (!validateUserType(userType)) {
            logger.error("user type [{}] not supported", userType);
            return ResultUtil.genCommonError("user type [{}] not supported", userType);
        }

        User user = new User();
        user.setId(uid);
        user.setUserName(userName);
        user.setUserType(userType);

        return userServiceRpc.modifyUser(user);
    }
}
