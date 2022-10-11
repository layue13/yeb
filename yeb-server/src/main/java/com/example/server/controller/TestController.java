package com.example.server.controller;

import com.example.common.pojo.Admin;
import com.example.common.pojo.Custom;
import com.example.server.advice.log.YebRequestLog;
import com.example.server.advice.signature.AutoVerify;
import com.example.server.advice.validate.MyRequired;
import com.example.server.advice.validate.MyValidated;
import com.example.server.security.Authenticated;
import com.example.server.security.PreAuthorize;
import com.example.server.vo.RespBean;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

/**
 * RestController
 *
 * @ResponseBody 返回的结果就不再走 Thymeleaf，viewsesolver，直接将文本返回给客户端，
 * 一般返回json格式的字符串
 * @Controller 控制器，连接客户端和代码
 */
@RestController
@Api(tags = "测试控制器")
public class TestController {

    /**
     * @RequestBody 表示从请求体中读取json格式的数据，这个方法一定不是get方法；
     * @RequestParam HttpServletRequest#getParameter() 从请求参数中获取值（/xx/xxx?a=1&b=2）
     * 默认使用该注解，key值会默认按照变量名来读取
     * @PathVariable 地址中的变量
     * @RequestHeader 请求头中获取数据
     */

    @GetMapping("/test")
    @ApiOperation("测试1")
    @Authenticated
    @PreAuthorize(roles = {"ROLE_manager", "ROLE_admin"})
    public RespBean test() {

        int i = 1 / 0;
        return RespBean.success();
    }

    @PostMapping("/test2")
    @ApiOperation("测试2")
    @Authenticated
    @PreAuthorize(type = "hasAllRoles", roles = {"ROLE_manager", "ROLE_admin"})
    public RespBean test2(@RequestBody Admin admin) {

        return RespBean.success(admin);
    }

    @GetMapping("/test3")
    @ApiOperation("测试3")
    @PreAuthorize(roles = {"ROLE_manager"})
    public RespBean test3(Admin admin) {

        return RespBean.success(admin);
    }

    @DeleteMapping("/test4/{numId}")
    public RespBean test4(@PathVariable("numId") String id) {

        return RespBean.success(id);
    }

    @GetMapping("/test5")
    @YebRequestLog(methodName = "测试5")
    public RespBean test5(@RequestHeader("Token") String token) {

        return RespBean.success(token);
    }

    @GetMapping("/test6")
    @MyValidated
    public RespBean test6(@MyRequired String name, @MyRequired Integer age) {

        /**
         * 测试的时候，请求：/test6?name=uuuu
         * 这时候，程序会提示
         * {
         *     code: "201",
         *     msg: "age 是必填的字段"
         * }
         */

        return RespBean.success(name + ":" + age);
    }

    @PostMapping("/test7")
    @AutoVerify
    public RespBean test7(Custom custom) {

        return RespBean.success(custom);
    }
}
