package com.example.server.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.common.pojo.Department;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
public interface DepartmentMapper extends BaseMapper<Department> {

    List<Department> findDeptsLevel(@Param("parentId") int parentId);

    void callAddDept(Department department);
}
