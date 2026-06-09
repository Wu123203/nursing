package com.elderly.controller;

import com.elderly.common.dto.PageQueryDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Employee;
import com.elderly.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 员工相关
 */
@RestController
@RequestMapping(value = "/employee")
public class EmployeeController {

    @Autowired
    private EmployeeService employeeService;

    /**
     * 学生分页
     * @param map
     * @return
     */
    @GetMapping
    JSONReturn page(@RequestParam Map<String,Object> map){
        PageQueryDto queryDto = new PageQueryDto(map);
        PageVo pageVo = employeeService.page(queryDto);
        return JSONReturn.success(pageVo);
    }


    /**
     * 根据ID查询记录
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public JSONReturn selectById(@PathVariable(value = "id")Integer id){
        Employee employee = employeeService.selectById(id);
        return JSONReturn.success(employee);
    }




    /**
     * 添加员工
     * @param employee
     * @return
     */
    @PostMapping
    public JSONReturn save(@RequestBody Employee employee){
        return employeeService.save(employee);
    }


    /**
     * 更新员工
     * @param employee
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody Employee employee){
        return employeeService.update(employee);
    }


    /**
     * 删除员工
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id")Integer id){
        Integer rows = employeeService.del(id);
        return rows  > 0 ? JSONReturn.success("删除成功！") : JSONReturn.failed("操作失败！");
    }





    /**
     * 查询所有
     * @return
     */
    @GetMapping(value = "/all")
    JSONReturn all(){
        List<Employee> all = employeeService.findAll();
        return JSONReturn.success(all);
    }


}
