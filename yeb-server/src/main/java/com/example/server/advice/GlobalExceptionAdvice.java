package com.example.server.advice;

import com.example.server.exception.YebException;
import com.example.server.vo.RespBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * Advice 通知，增强，拦截所有未处理的异常，一旦出现异常就由该组件进行处理
 * 针对不同的异常，编写不同的处理方法
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionAdvice {

//    private final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionAdvice.class);

    @ExceptionHandler(ArithmeticException.class)
    public RespBean arithmeticExceptionHandler(ArithmeticException e) {

        // 打印异常
        log.error("算术异常，" + e.getMessage(), e);
        // 翻译异常，发送给客户端
        return RespBean.error(201, "算术异常，" + e.getMessage());
    }

    @ExceptionHandler(YebException.class)
    public RespBean yebExceptionHandler(YebException exception) {

        log.error(exception.getCode() + "：" + exception.getMsg(), exception);

        return RespBean.error(exception.getCode(), exception.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public RespBean exceptionHandler(Exception exception) {

        log.error("700：" + exception.getMessage(), exception);

        return RespBean.error(700, exception.getMessage());
    }
}
