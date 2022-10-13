package com.example.server.controller;


import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import com.example.common.pojo.*;
import com.example.server.service.*;
import com.example.server.vo.RespBean;
import com.example.server.vo.RespPageBean;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
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
@RequestMapping("/employee/basic")
public class EmployeeController {

    @Autowired
    private IEmployeeService employeeService;

    @Autowired
    private INationService nationService;

    @Autowired
    private IJoblevelService joblevelService;

    @Autowired
    private IDepartmentService departmentService;

    @Autowired
    private IPoliticsStatusService politicsStatusService;

    @Autowired
    private IPositionService positionService;

    @GetMapping("/")
    public RespPageBean list(@RequestParam(defaultValue = "1") Integer currentPage,
                             @RequestParam(defaultValue = "10") Integer size,
                             Employee employee
    ) {

        return employeeService.getAll(currentPage, size, employee);
    }

    @ApiOperation("获取所有政治面貌")
    @GetMapping("/politicsstatus")
    public List<PoliticsStatus> getAllPolitics() {
        return politicsStatusService.list();
    }

    @ApiOperation("获取所有职称")
    @GetMapping("/joblevels")
    public List<Joblevel> getAllJoblevels() {

        return joblevelService.list();
    }

    @ApiOperation("获取所有名族")
    @GetMapping("/nations")
    public List<Nation> getAllNations() {

        return nationService.list();
    }

    @ApiOperation("获取所有职位")
    @GetMapping("/positions")
    public List<Position> getAllPosition() {

        return positionService.list();
    }

    @ApiOperation("获取所有部门")
    @GetMapping("/deps")
    public List<Department> getAllDeps() {

        return departmentService.listDeptsLevel();
    }

    @GetMapping("/maxWorkID")
    public RespBean maxWorkId() {

        return employeeService.maxWorkId();
    }

    @PostMapping("/")
    public RespBean addEmp(@RequestBody Employee employee) {

        return employeeService.addEmp(employee);
    }

    @PutMapping("/")
    public RespBean editEmp(@RequestBody Employee employee) {

        employeeService.updateById(employee);

        return RespBean.success();
    }

    @DeleteMapping("/{id}")
    public RespBean deleteEmp(@PathVariable Integer id) {

        employeeService.removeById(id);

        return RespBean.success();
    }

    @GetMapping("/export")
    public void export(HttpServletResponse response) {
        response.setHeader("content-type", "application/octet-stream");
        response.setHeader("content-disposition", "attachment;filename=" +
                URLEncoder.encode("员工表.xls", Charset.defaultCharset()));
        List<Employee> employees = employeeService.getAllSimple();
        ExportParams exportParams = new ExportParams("员工花名册", "员工信息", ExcelType.HSSF);
        Workbook sheets = ExcelExportUtil.exportExcel(exportParams, Employee.class, employees);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            sheets.write(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
