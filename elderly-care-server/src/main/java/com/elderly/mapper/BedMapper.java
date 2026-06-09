package com.elderly.mapper;

import com.elderly.entity.Bed;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 床位持久化层
 */
public interface BedMapper {
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
    int insert(Bed record);

    /**
     * 部分字段保存记录
     * @param record
     * @return
     */
    int insertSelective(Bed record);

    /**
     * 根据Id查询记录
     * @param id
     * @return
     */
    Bed selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Bed record);

    /**
     * 全部字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Bed record);


    /**
     * 查询记录
     * @param map
     * @return
     */
    List<Bed> findList(Map map);


    /**
     * 统计记录
     * @param map
     * @return
     */
    Integer findTotal(Map map);


    /**
     * 根据房间查询床位
     * @param roomId
     * @return
     */
    List<Bed> findByRoom(@Param(value = "roomId")Integer roomId);


    /**
     * 统计床位数
     * @return
     */
    Integer countTotal();


    /**
     * 根据床位状态统计
     * @param status
     * @return
     */
    Integer countByStatus(@Param(value = "status")Integer status);
}