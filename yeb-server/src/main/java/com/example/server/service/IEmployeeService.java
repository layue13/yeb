package com.example.server.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.common.pojo.Employee;
import com.example.server.vo.RespBean;
import com.example.server.vo.RespPageBean;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
public interface IEmployeeService extends IService<Employee> {

    RespPageBean getAll(Integer currentPage, Integer size, Employee employee);

    RespBean maxWorkId();

    RespBean addEmp(Employee employee);

    List<Employee> getAllSimple();

}
