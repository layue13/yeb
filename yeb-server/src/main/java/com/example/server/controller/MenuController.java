package com.example.server.controller;


import com.example.common.pojo.Menu;
import com.example.server.service.IMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
@RestController
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @GetMapping("/system/menu")
    public List<Menu> getMenus() {

        return menuService.getMenuWithChildrenByAdminId();
    }
}
