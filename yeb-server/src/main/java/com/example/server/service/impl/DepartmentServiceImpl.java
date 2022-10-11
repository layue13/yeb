package com.example.server.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.pojo.Department;
import com.example.server.exception.YebException;
import com.example.server.mapper.DepartmentMapper;
import com.example.server.service.IDepartmentService;
import com.example.server.vo.RespBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
@Service
public class DepartmentServiceImpl extends ServiceImpl<DepartmentMapper, Department> implements IDepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Override
    public List<Department> listDeptsLevel() {
        return departmentMapper.findDeptsLevel(-1);
    }

    @Override
    public RespBean addDept(Department department) {
        departmentMapper.callAddDept(department);

        if (department.getResult() != 1) {
            throw new YebException(501, "新增失败");
        } else {
            return RespBean.success(department);
        }
    }
}
