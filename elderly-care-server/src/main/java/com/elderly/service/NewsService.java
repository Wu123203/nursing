package com.elderly.service;

import com.elderly.common.vo.PageVo;
import com.elderly.entity.News;

import java.util.List;
import java.util.Map;


/**
 * 政策新闻服务类
 */
public interface NewsService {


    /**
     * 政策新闻分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 添加政策新闻
     * @param news
     * @return
     */
    Integer save(News news);

    /**
     * 更新政策新闻
     * @param news
     * @return
     */
    Integer update(News news);


    /**
     * 删除政策新闻
     * @param id
     * @return
     */
    Integer del(Integer id);



    /**
     * 根据ID查询政策新闻
     * @param id
     * @return
     */
    News selectById(Integer id);


    /**
     * 查询最新的政策新闻
     * @param limit 几条
     * @return
     */
    List<News> findLatestTopList(Integer limit);



    /**
     * 查询政策新闻点击排行
     * @param limit 几条
     * @return
     */
    List<News> findClickTopList(Integer limit);



}
