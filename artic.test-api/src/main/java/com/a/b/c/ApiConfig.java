package com.a.b.c;

import com.wengyingjian.kylin.hessian.client.utils.HessianProxyUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.a.b.c.common.service.UserService;

@Configuration
public class ApiConfig {

    @Value("${service.base.url}")
    private String serviceBaseUrl;

    @Bean
    public UserService userServiceRpc() {
        String serviceExportName = "userService";
        return UserService.class.cast(HessianProxyUtil.buildRemotingService(serviceBaseUrl, serviceExportName, UserService.class));
    }
}