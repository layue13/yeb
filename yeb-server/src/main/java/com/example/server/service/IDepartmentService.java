package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.pojo.Department;
import com.example.server.vo.RespBean;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
public interface IDepartmentService extends IService<Department> {

    List<Department> listDeptsLevel();

    RespBean addDept(Department department);
}
