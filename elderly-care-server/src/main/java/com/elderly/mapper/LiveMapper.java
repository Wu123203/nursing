package com.elderly.mapper;

import com.elderly.entity.Live;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 入住登记持久化层
 */
public interface LiveMapper {
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
    int insert(Live record);

    /**
     * 部分字段保存记录
     * @param record
     * @return
     */
    int insertSelective(Live record);

    /**
     * 根据Id查询记录
     * @param id
     * @return
     */
    Live selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Live record);

    /**
     * 全部字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Live record);


    /**
     * 查询记录
     * @param map
     * @return
     */
    List<Live> findList(Map map);


    /**
     * 统计记录
     * @param map
     * @return
     */
    Integer findTotal(Map map);


    /**
     * 查询入住人
     * @param roomId
     * @return
     */
    List<Live> findByRoom(@Param(value = "roomId")Integer roomId);


    /**
     * 统计当前总入住人数
     * @return
     */
    Integer countTotal();


    /**
     * 性别统计
     * @param sex
     * @return
     */
    Integer countBySex(@Param(value = "sex")Integer sex);



    /**
     * 根据入住人数
     * @param beginTime
     * @return
     */
    Integer countByDate(@Param(value = "beginTime")String beginTime);

}