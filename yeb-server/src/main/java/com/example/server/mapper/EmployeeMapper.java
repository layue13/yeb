package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.common.pojo.Employee;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDate;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
public interface EmployeeMapper extends BaseMapper<Employee> {

    IPage<Employee> findAll(Page<Employee> page,
                            @Param("employee") Employee employee,
                            @Param("beginDateScope") LocalDate[] beginDateScope);

    List<Employee> findAllSimple();
}
