package com.example.server.controller;


import com.example.common.pojo.Department;
import com.example.server.service.IDepartmentService;
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
@RequestMapping("/system/basic/department")
public class DepartmentController {

    @Autowired
    private IDepartmentService departmentService;

    @GetMapping("/")
    public List<Department> listDepts() {

        return departmentService.listDeptsLevel();
    }

    @PostMapping("/")
    public RespBean addDept(@RequestBody Department department) {

        return departmentService.addDept(department);
    }
}
