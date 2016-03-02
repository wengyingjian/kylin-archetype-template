package com.a.b.c.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Created by wengyingjian on 16/3/1.
 */
@Aspect
@Component
@Resource
public class LoggerAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 日志打印:参数前缀
     */
    private static final String LOG_PARAM_PREFIX = "params:[";
    /**
     * 日志打印:参数后缀
     */
    private static final String LOG_PARAM_SUFFIX = "]";
    /**
     * 日志打印:cookie前缀
     */
    private static final String LOG_COOKIE_PREFIX = "cookies:[";
    /**
     * 日志打印:cookie后缀
     */
    private static final String LOG_COOKIE_SUFFIX = "]";
    /**
     * 日志打印:不同key,value分隔符
     */
    private static final String LOG_KV_SEPARATE = ",";
    /**
     * 日志打印:相同key,value连接符
     */
    private static final String LOG_KV_CONNECT = "=";


    /**
     * 拦截所有controller中的带request参数的请求,打印参数日志
     *
     * @param point
     */
    @Before("execution(* com.a.b.c.controller.*.*(javax.servlet.http.HttpServletRequest, ..))")
    public void preController(JoinPoint point) {
        //方法全名
        String className = point.getTarget().getClass().getName();
        String methodName = point.getSignature().getName();
        //参数
        String paramLog = null;
        String cookieLog = null;
        Object[] args = point.getArgs();
        if (args.length > 0) {
            HttpServletRequest request = (HttpServletRequest) args[0];
            paramLog = paramLog(request);
            cookieLog = cookieLog(request);
        }
        logger.info("调用:{{}.{}},request参数:{{},{}}", className, methodName, paramLog, cookieLog);

    }

    /**
     * 获取params中的所有信息
     *
     * @param request
     * @return
     */
    public String paramLog(HttpServletRequest request) {
        StringBuffer params = new StringBuffer(LOG_PARAM_PREFIX);
        Enumeration<String> pararmNames = request.getParameterNames();
        boolean hasParam = false;
        while (pararmNames.hasMoreElements()) {
            hasParam = true;
            String name = pararmNames.nextElement();
            String value = request.getParameter(name);
            params.append(name).append(LOG_KV_CONNECT).append(value).append(LOG_KV_SEPARATE);
        }
        if (hasParam) {
            params.deleteCharAt(params.length() - 1);
        }
        return params.append(LOG_PARAM_SUFFIX).toString();
    }

    /**
     * 获取cookie中的所有信息
     *
     * @param request
     * @return
     */
    public String cookieLog(HttpServletRequest request) {
        //初始化,带前缀
        StringBuffer cookieStr = new StringBuffer(LOG_COOKIE_PREFIX);
        //遍历所有的cookie,加上
        Cookie[] requestCookies = request.getCookies();
        if (requestCookies != null) {
            for (Cookie cookie : requestCookies) {
                String name = cookie.getName();
                String value = cookie.getValue();
                cookieStr.append(name).append(LOG_KV_CONNECT).append(value).append(LOG_KV_SEPARATE);
            }
            cookieStr.deleteCharAt(cookieStr.length() - 1);
        }
        //加后缀
        return cookieStr.append(LOG_COOKIE_SUFFIX).toString();
    }

}
