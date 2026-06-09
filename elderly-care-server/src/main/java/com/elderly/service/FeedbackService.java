package com.elderly.service;

import com.elderly.common.vo.PageVo;
import com.elderly.entity.Feedback;

import java.util.List;
import java.util.Map;


/**
 *留言反馈服务层
 */
public interface FeedbackService {


    /**
     *留言反馈分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 添加留言反馈
     * @param feedback
     * @return
     */
    Integer save(Feedback feedback);

    /**
     * 更新留言反馈
     * @param feedback
     * @return
     */
    Integer update(Feedback feedback);


    /**
     * 删除留言反馈
     * @param id
     * @return
     */
    Integer del(Integer id);



    /**
     * 根据ID查询留言反馈
     * @param id
     * @return
     */
    Feedback selectById(Integer id);



    /**
     * 查询最新的几条新闻
     * @param limit
     * @return
     */
    List<Feedback> findLatestTopList(Integer limit);
    
}
