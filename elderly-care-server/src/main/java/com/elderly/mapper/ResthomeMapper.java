package com.elderly.mapper;

import com.elderly.entity.Resthome;

import java.util.List;
import java.util.Map;

/**
 * 入院指南持久化层
 */
public interface ResthomeMapper {
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
    int insert(Resthome record);

    /**
     * 部分字段保存记录
     * @param record
     * @return
     */
    int insertSelective(Resthome record);

    /**
     * 根据Id查询记录
     * @param id
     * @return
     */
    Resthome selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Resthome record);

    /**
     * 全部字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Resthome record);


    /**
     * 查询记录
     * @param map
     * @return
     */
    List<Resthome> findList(Map map);


    /**
     * 统计记录
     * @param map
     * @return
     */
    Integer findTotal(Map map);
}