package com.elderly.mapper;

import com.elderly.entity.Visit;

import java.util.List;
import java.util.Map;

/**
 * 来访登记持久化
 */
public interface VisitMapper {
    /**
     * 根据ID删除记录
     *
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 全部字段保存记录
     *
     * @param record
     * @return
     */
    int insert(Visit record);

    /**
     * 部分字段保存记录
     *
     * @param record
     * @return
     */
    int insertSelective(Visit record);

    /**
     * 根据ID查询记录
     *
     * @param id
     * @return
     */
    Visit selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Visit record);

    /**
     * 全部字段更新记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Visit record);


    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<Visit> findList(Map map);

    /**
     * 统计
     *
     * @param map
     * @return
     */
    Integer findTotal(Map map);



}