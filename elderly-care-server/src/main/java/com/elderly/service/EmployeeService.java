package com.elderly.service;

import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Employee;

import java.util.List;
import java.util.Map;


/**
 * 员工服务类
 */
public interface EmployeeService {
    
    /**
     * 员工分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 添加员工
     * @param employee
     * @return
     */
    JSONReturn save(Employee employee);


    /**
     * 修改
     * @param employee
     * @return
     */
    JSONReturn update(Employee employee);



    /**
     * 删除员工
     * @param id
     * @return
     */
    Integer del(Integer id);



    /**
     * 根据ID查询员工信息
     * @param id
     * @return
     */
    Employee selectById(Integer id);


    /**
     * 查询所有
     * @return
     */
    List<Employee> findAll();


}
