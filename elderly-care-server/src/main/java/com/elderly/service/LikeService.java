package com.elderly.service;

import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Like;

import java.util.Map;

/**
 * 帖子点赞服务类
 */
public interface LikeService {


    /**
     * 帖子点赞分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 添加帖子点赞
     * @param like
     * @return
     */
    JSONReturn save(Like like);

    /**
     * 更新帖子点赞
     * @param like
     * @return
     */
    Integer update(Like like);


    /**
     * 删除帖子点赞
     * @param id
     * @return
     */
    Integer del(Integer id);



    /**
     * 根据ID查询帖子点赞
     * @param id
     * @return
     */
    Like selectById(Integer id);


}
