package com.elderly.mapper;

import com.elderly.entity.Employee;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 员工持久化层
 */
public interface EmployeeMapper {
    /**
     * 根据ID删除记录
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 全量字段保存记录
     * @param record
     * @return
     */
    int insert(Employee record);

    /**
     * 部分字段保存记录
     * @param record
     * @return
     */
    int insertSelective(Employee record);

    /**
     * 根据Id查询记录
     * @param id
     * @return
     */
    Employee selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Employee record);

    /**
     * 全部字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Employee record);


    /**
     * 查询记录
     * @param map
     * @return
     */
    List<Employee> findList(Map map);


    /**
     * 统计记录
     * @param map
     * @return
     */
    Integer findTotal(Map map);



    /**
     * 根据账号查询账号
     * @param username
     * @return
     */
    Employee selectByUsername(@Param(value = "username")String username);


    /**
     * 统计总护工数
     * @return
     */
    Integer countTotal();


    /**
     * 查询所有
     * @return
     */
    List<Employee> findAll();

}