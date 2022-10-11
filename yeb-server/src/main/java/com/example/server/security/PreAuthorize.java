package com.example.server.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author admin
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PreAuthorize {

    /**
     * 该注解能够修饰controller方法
     * 表示想要访问该方法，必须通过注解中type指定的授权方式进行授权
     * type 授权方式
     * hasAnyRole 用户拥有的角色和请求需要的角色有交集，就可以访问
     * hasAllRoles 用户必须拥有请求需要的所有角色才能够访问
     * <p>
     * roles 可以手动配置某个请求需要的权限列表
     */

    String type() default "hasAnyRole";

    String[] roles();
}
