package com.example.server.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.example.common.pojo.Admin;
import com.example.common.pojo.AdminRole;
import com.example.common.pojo.Role;
import com.example.server.security.context.SecurityContextHolder;
import com.example.server.service.IAdminRoleService;
import com.example.server.service.IAdminService;
import com.example.server.service.IRoleService;
import com.example.server.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
@RestController
public class AdminController {

    @Autowired
    private IAdminService adminService;

    @Autowired
    private SecurityContextHolder securityContextHolder;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IAdminRoleService adminRoleService;

    @PostMapping("/login")
    public RespBean login(@RequestBody Admin admin, HttpSession session) {

        /**
         * 1 收集客户端的数据
         * 1.1 数据校验
         * 2 调用service方法，传递搜集的数据，执行业务逻辑
         * 3 获取service的结果，封装为RespBean 对象发送给客户端
         */
        RespBean respBean = adminService.login(admin, session);

        return respBean;
    }

    @GetMapping("/admin/info")
    public Admin adminInfo() {

        Admin userDetails = (Admin) securityContextHolder.getUserDetails();

        return userDetails;
    }

    @GetMapping("/system/admin/")
    public List<Admin> listAdminsByKeyWords(String keywords) {

        return adminService.getAdminsByKeywords(keywords);
    }

    @PutMapping("/system/admin/")
    public RespBean editAdmin(@RequestBody Admin admin) {

        adminService.updateById(admin);

        return RespBean.success();
    }

    @DeleteMapping("/system/admin/{id}")
    public RespBean deleteAdmin(@PathVariable Integer id) {

        adminService.removeById(id);

        return RespBean.success();
    }

    @GetMapping("/system/admin/roles")
    public List<Role> listRoles() {

        return roleService.list();
    }

    @GetMapping("/system/admin/{id}/rids")
    public List<Integer> listRidsByAdmin(@PathVariable Integer id) {

        List<AdminRole> roleList = adminRoleService.list(
                new QueryWrapper<AdminRole>().eq("adminId", id));

        /**
         * 匿名内部类
         * lambda
         * method reference
         */
        List<Integer> collect = roleList.stream()
                .map(AdminRole::getRid)
                .collect(Collectors.toList());

        return collect;
    }

    @PutMapping("/system/admin/{adminId}/update/rids")
    public RespBean updateAdminRoles(@PathVariable Integer adminId,
                                     Integer[] rids) {

        return adminRoleService.editAdminRoles(adminId, rids);
    }
}
