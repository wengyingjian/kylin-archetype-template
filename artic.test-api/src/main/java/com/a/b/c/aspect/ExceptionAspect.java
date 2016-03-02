package com.a.b.c.aspect;

import com.wengyingjian.kylin.common.model.Result;
import com.wengyingjian.kylin.common.utils.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * 异常全局处理
 * Created by wengyingjian on 16/3/1.
 */
@ControllerAdvice
public class ExceptionAspect {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    /**
     * 全局异常捕获，返回异常状态及信息
     *
     * @param request 发生异常的请求
     * @param t       发生的异常
     * @return 已知异常返回定义的状态码和信息，未知异常返回状态码－1和异常信息
     */
    @ExceptionHandler(Throwable.class)
    @ResponseBody
    public Result<String> handleBadRequest(HttpServletRequest request, Throwable t) {
        logger.error("", t);
        return ResultUtil.genCommonError(t.getMessage());
    }

    /**
     * 获取异常信息
     *
     * @param t
     * @return
     */
    public static String getTrace(Throwable t) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        t.printStackTrace(writer);
        StringBuffer buffer = stringWriter.getBuffer();
        return buffer.toString();
    }

}
