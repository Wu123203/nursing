package com.elderly.mapper;

import com.elderly.entity.Comment;

import java.util.List;
import java.util.Map;

/**
 * 新闻评论持久化层
 */
public interface CommentMapper {
    /**
     * 根据ID删除记录
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 全部字段保存记录
     * @param record
     * @return
     */
    int insert(Comment record);

    /**
     * 部分字段保存记录
     * @param record
     * @return
     */
    int insertSelective(Comment record);

    /**
     * 根据ID查询记录
     * @param id
     * @return
     */
    Comment selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Comment record);

    /**
     * 全部字段更新记录
     * @param record
     * @return
     */
    int updateByPrimaryKey(Comment record);


    /**
     * 查询列表
     * @param map
     * @return
     */
    List<Comment> findList(Map map);

    /**
     * 统计评论
     * @param map
     * @return
     */
    Integer findTotal(Map map);

}