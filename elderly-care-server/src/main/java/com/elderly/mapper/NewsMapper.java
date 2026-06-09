package com.elderly.mapper;

import com.elderly.entity.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 文章持久化层
 */
public interface NewsMapper {

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
    int insert(News record);

    /**
     * 部分字段保存记录
     * @param record
     * @return
     */
    int insertSelective(News record);

    /**
     * 根据Id查询记录
     * @param id
     * @return
     */
    News selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(News record);

    /**
     * 全部字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(News record);


    /**
     * 查询记录
     * @param map
     * @return
     */
    List<News> findList(Map map);


    /**
     * 统计记录
     * @param map
     * @return
     */
    Integer findTotal(Map map);


    /**
     * 查询最新的
     * @param limit    几条
     * @return
     */
    List<News> findLatestTopList(@Param(value = "limit")Integer limit);


    /**
     * 查询点击排行
     * @param limit 几条
     * @return
     */
    List<News> findClickTopList(@Param(value = "limit")Integer limit);

    /**
     * 更新点击数
     * @param id       新闻ID
     * @param clickNum 点击数
     * @return
     */
    int updateClickNum(@Param(value = "id")Integer id, @Param(value = "clickNum")Integer clickNum);
    
}