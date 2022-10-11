package com.example.server.advice.signature;

import com.example.server.exception.YebException;
import com.example.server.utils.RsaUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;

@Aspect
@Component
public class AutoVerifyAdvice {

    @Pointcut("@annotation(com.example.server.advice.signature.AutoVerify)")
    public void autoVerify() {

    }

    @Around("autoVerify()")
    public Object autoVerifyAdvice(ProceedingJoinPoint joinPoint) {

        /**
         * 1. 获取签名
         * 2. 获取原始数据
         * 3. 进行比较
         */

        ServletRequestAttributes requestAttributes =
                (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();
        String sign = request.getHeader("sign");

        Object[] args = joinPoint.getArgs();
        try {

            int contentLength = request.getContentLength();
            if (contentLength < 0) {
                throw new RuntimeException("没有数据需要验证");
            }
            byte[] buffer = new byte[contentLength];
            for (int i = 0; i < contentLength; ) {
                int readLength = request.getInputStream().read(buffer, i, contentLength - i);
                if (readLength == -1) {
                    break;
                }
                i += readLength;
            }
            String characterEncoding = request.getCharacterEncoding();
            if (StringUtils.isEmpty(characterEncoding)) {
                characterEncoding = StandardCharsets.UTF_8.toString();
            }
            // 生成数据原文
            String json = new String(buffer, characterEncoding);

            boolean verify = RsaUtils.verify(json, sign);
            if (!verify) {
                throw new RuntimeException("验证签名失败，数据可能被篡改！");
            }

            // 代替spring框架的@RequestBody注解，手动将json转换为需要的对象
            Class<?> aClass = args[0].getClass();
            Object data = new ObjectMapper().readValue(json, aClass);
            args[0] = data;

            Object result = joinPoint.proceed(args);
            return result;
        } catch (Throwable e) {

            throw new YebException(202, "验签异常：" + e.getMessage());
        }

    }
}
