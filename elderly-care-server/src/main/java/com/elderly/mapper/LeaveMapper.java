package com.elderly.mapper;

import com.elderly.entity.Leave;

import java.util.List;
import java.util.Map;

/**
 * 外出报备持久化层
 */
public interface LeaveMapper {
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
    int insert(Leave record);

    /**
     * 部分字段保存记录
     * @param record
     * @return
     */
    int insertSelective(Leave record);

    /**
     * 根据Id查询记录
     * @param id
     * @return
     */
    Leave selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Leave record);

    /**
     * 全部字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Leave record);


    /**
     * 查询记录
     * @param map
     * @return
     */
    List<Leave> findList(Map map);


    /**
     * 统计记录
     * @param map
     * @return
     */
    Integer findTotal(Map map);

}