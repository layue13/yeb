package com.example.server.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.common.pojo.Employee;
import com.example.server.mapper.EmployeeMapper;
import com.example.server.service.IEmployeeService;
import com.example.server.vo.RespBean;
import com.example.server.vo.RespPageBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author 小红
 * @since 2022-09-01
 */
@Service
public class EmployeeServiceImpl extends ServiceImpl<EmployeeMapper, Employee> implements IEmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;

    @Override
    public RespPageBean getAll(Integer currentPage, Integer size, Employee employee) {

        Page<Employee> page = new Page<>(currentPage, size);

        IPage<Employee> result = employeeMapper.findAll(page, employee, employee.getBeginDateScope());

        return new RespPageBean()
                .setTotal(result.getTotal())
                .setData(result.getRecords());
    }

    @Override
    public RespBean maxWorkId() {

        /**
         * 生成最新的workid
         * 1 总共有8个数字
         * 2 按照顺序生成
         * 3 在数字前补充0 -> 8个数字 -> 字符串
         */

        // select max(workID) maxId from t_employee
        List<Map<String, Object>> maps =
                employeeMapper.selectMaps(
                        new QueryWrapper<Employee>().select("max(workID) maxId"));

        Integer maxId = Integer.parseInt(
                maps.get(0).get("maxId").toString()
        ) + 1;

        String format = String.format("%08d", maxId);

        return RespBean.success(format);
    }

    @Override
    public RespBean addEmp(Employee employee) {

        LocalDate beginContract = employee.getBeginContract();
        LocalDate endContract = employee.getEndContract();

        long days = beginContract.until(endContract, ChronoUnit.DAYS);

        double year = days / 365.00;

        DecimalFormat format = new DecimalFormat("##.00");

        String yearStr = format.format(year);

        employee.setContractTerm(Double.parseDouble(yearStr));

        employeeMapper.insert(employee);

        return RespBean.success();
    }

    @Override
    public List<Employee> getAllSimple() {
        return employeeMapper.findAllSimple();
    }
}
