package com.elderly.mapper;

import com.elderly.entity.Like;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 新闻点赞持久化层
 */
public interface LikeMapper {
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
    int insert(Like record);

    /**
     * 部分字段保存记录
     *
     * @param record
     * @return
     */
    int insertSelective(Like record);

    /**
     * 根据ID查询记录
     *
     * @param id
     * @return
     */
    Like selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Like record);

    /**
     * 全部字段更新记录
     *
     * @param record
     * @return
     */
    int updateByPrimaryKey(Like record);


    /**
     * 查询列表
     *
     * @param map
     * @return
     */
    List<Like> findList(Map map);

    /**
     * 统计
     *
     * @param map
     * @return
     */
    Integer findTotal(Map map);


    /**
     * 查询是否已存在
     *
     * @param userId
     * @param newsId
     * @return
     */
    Like selectExist(@Param(value = "userId") Integer userId,
                     @Param(value = "newsId") Integer newsId);
}