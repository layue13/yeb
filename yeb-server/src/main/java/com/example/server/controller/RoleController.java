package com.example.server.controller;


import com.example.common.pojo.Menu;
import com.example.common.pojo.Role;
import com.example.server.service.IMenuService;
import com.example.server.service.IRoleService;
import com.example.server.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
@RequestMapping("/system/basic/permiss")
public class RoleController {

    @Autowired
    private IRoleService roleService;

    @Autowired
    private IMenuService menuService;

    @GetMapping("/")
    public List<Role> listAllRoles() {

        List<Role> list = roleService.list();

        return list;
    }

    @PostMapping("/role")
    public RespBean addRole(@RequestBody Role role) {

        boolean save = roleService.save(role);

        return RespBean.success();
    }

    @DeleteMapping("/role/{id}")
    public RespBean deleteById(@PathVariable Integer id) {

        roleService.removeById(id);

        return RespBean.success();
    }

    @GetMapping("/menus")
    public List<Menu> listMenusLevel() {

        return menuService.getMenusLevel();
    }

    @GetMapping("/mid/{rid}")
    public List<Integer> listMenuIdsByRoleId(@PathVariable Integer rid) {

        return menuService.getMenuIdsByRid(rid);
    }

    @PutMapping("/")
    public RespBean updateMenuRole(Integer rid, Integer[] mids) {

        return menuService.updateMenuRole(rid, mids);
    }
}
