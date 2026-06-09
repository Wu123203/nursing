package com.elderly.service;

import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Comment;

import java.util.Map;

/**
 *
 * 新闻评论服务
 */
public interface CommentService {

    /**
     * 评论分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 评论保存
     * @param comment
     * @return
     */
    JSONReturn save(Comment comment);

    /**
     * 评论更新
     * @param comment
     * @return
     */
    Integer update(Comment comment);

    /**
     * 评论删除
     * @param id
     * @return
     */
    Integer del(Integer id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Comment selectById(Integer id);




}
