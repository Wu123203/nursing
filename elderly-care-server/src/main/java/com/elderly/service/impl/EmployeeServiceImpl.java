package com.elderly.service.impl;

import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Employee;
import com.elderly.mapper.EmployeeMapper;
import com.elderly.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 员工服务实现类
 */
@Service
public class EmployeeServiceImpl implements EmployeeService {

    @Autowired
    private EmployeeMapper employeeMapper;
    
    @Override
    public PageVo page(Map map) {
        List<Employee> list = employeeMapper.findList(map);
        Integer total = employeeMapper.findTotal(map);
        return new PageVo(total,list);
    }

    @Override
    public JSONReturn save(Employee employee) {
        //员工名查询
        Employee exist = employeeMapper.selectByUsername(employee.getUsername());
        if(exist != null){
            return JSONReturn.failed("工号已存在，请重新输入！");
        }
        Date date = new Date();
        employee.setCreateTime(date);
        employee.setUpdateTime(date);
        employee.setStatus(0);
        //初始化性别
        employee.setSex(0);
        employeeMapper.insert(employee);
        return JSONReturn.success("注册成功！");
    }

    @Override
    public JSONReturn update(Employee employee) {

        Employee u = employeeMapper.selectByPrimaryKey(employee.getId());
        if(employee.getUsername() != null && !u.getUsername().equals(employee.getUsername())){
            Employee exist = employeeMapper.selectByUsername(employee.getUsername());
            if(exist != null){
                return JSONReturn.failed("工号已存在，请重新输入！");
            }
        }
        employee.setUpdateTime(new Date());
        employeeMapper.updateByPrimaryKeySelective(employee);
        return JSONReturn.success("更新成功！");
    }

    @Override
    public Integer del(Integer id) {
        return employeeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Employee selectById(Integer id) {
        return employeeMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Employee> findAll() {
        return employeeMapper.findAll();
    }


}
