package com.example.server.advice.log;

import com.example.common.pojo.RequestLog;
import com.example.server.service.IRequestLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;

@Aspect
@Component
public class RequestLogAdvice {

    @Autowired
    private IRequestLogService requestLogService;

    @Pointcut("@annotation(com.example.server.advice.log.YebRequestLog)")
    public void requestLogAnono() {

    }

    @Around("requestLogAnono()")
    public Object requestLogAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {

        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        YebRequestLog annotation = method.getAnnotation(YebRequestLog.class);

        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        String name = method.getName();

        RequestLog requestLog = new RequestLog();
        requestLog.setMethod(name);
        requestLog.setMethodName(annotation.methodName());
        requestLog.setStartTime(LocalDateTime.now());
        requestLog.setHost(request.getRemoteHost());

        try {

            // 正常执行被代理对象的业务逻辑
            Object result = proceedingJoinPoint.proceed();
            requestLog.setStatus(1);
            requestLog.setMsg("请求执行成功！");
            return result;
        } catch (Throwable e) {

            requestLog.setStatus(0);
            requestLog.setMsg(e.getMessage());

            throw e;
        } finally {

            requestLog.setEndTime(LocalDateTime.now());
            requestLogService.save(requestLog);
        }
    }
}
