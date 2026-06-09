package com.elderly.service.impl;

import com.elderly.common.config.RedisConstants;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.News;
import com.elderly.mapper.NewsMapper;
import com.elderly.service.NewsService;
import com.elderly.utils.RedisUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 文政策新闻实现类
 */
@Service
public class NewsServiceImpl implements NewsService {

    private static final Logger logger = LoggerFactory.getLogger(NewsServiceImpl.class);

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public PageVo page(Map map) {
        List<News> list = newsMapper.findList(map);
        Integer total = newsMapper.findTotal(map);
        return new PageVo(total, list);
    }

    @Override
    public Integer save(News news) {
        Date date = new Date();
        news.setClickNum(0);
        news.setLikeNum(0);
        news.setCommentNum(0);
        news.setCreateTime(date);
        news.setUpdateTime(date);
        int result = newsMapper.insert(news);
        
        // 清除相关缓存
        clearNewsCache();
        
        return result;
    }

    @Override
    public Integer update(News news) {
        news.setUpdateTime(new Date());
        int result = newsMapper.updateByPrimaryKeySelective(news);
        
        // 清除相关缓存
        clearNewsCache();
        
        return result;
    }

    @Override
    public Integer del(Integer id) {
        int result = newsMapper.deleteByPrimaryKey(id);
        
        // 清除相关缓存和计数
        redisUtil.delete(RedisConstants.VIEW_COUNT_NEWS_KEY + id);
        clearNewsCache();
        
        return result;
    }

    @Override
    public News selectById(Integer id) {
        // 增加浏览量（Redis 原子操作）
        incrementViewCount(id);
        
        return newsMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<News> findLatestTopList(Integer limit) {
        String key = RedisConstants.CACHE_HOT_NEWS_KEY + "latest:" + limit;
        Object cacheObj = redisUtil.get(key);
        
        if (cacheObj != null) {
            logger.debug("热点新闻列表从缓存获取: {}", key);
            return (List<News>) cacheObj;
        }
        
        List<News> list = newsMapper.findLatestTopList(limit);
        
        if (list != null && !list.isEmpty()) {
            redisUtil.set(key, list, RedisConstants.CACHE_HOT_NEWS_TTL);
        }
        
        return list;
    }

    @Override
    public List<News> findClickTopList(Integer limit) {
        String key = RedisConstants.CACHE_HOT_NEWS_KEY + "click:" + limit;
        Object cacheObj = redisUtil.get(key);
        
        if (cacheObj != null) {
            logger.debug("热点新闻列表从缓存获取: {}", key);
            return (List<News>) cacheObj;
        }
        
        List<News> list = newsMapper.findClickTopList(limit);
        
        if (list != null && !list.isEmpty()) {
            redisUtil.set(key, list, RedisConstants.CACHE_HOT_NEWS_TTL);
        }
        
        return list;
    }

    /**
     * 增加新闻浏览量（使用 Redis 原子操作）
     *
     * @param newsId 新闻 ID
     * @return 当前浏览量
     */
    public long incrementViewCount(Integer newsId) {
        String key = RedisConstants.VIEW_COUNT_NEWS_KEY + newsId;
        long count = redisUtil.increment(key);
        logger.debug("新闻浏览量+1: newsId={}, count={}", newsId, count);
        return count;
    }

    /**
     * 获取新闻浏览量
     *
     * @param newsId 新闻 ID
     * @return 浏览量
     */
    public long getViewCount(Integer newsId) {
        String key = RedisConstants.VIEW_COUNT_NEWS_KEY + newsId;
        Object obj = redisUtil.get(key);
        
        if (obj != null) {
            return Long.parseLong(obj.toString());
        }
        
        // 缓存未命中，从数据库查询并同步到 Redis
        News news = newsMapper.selectByPrimaryKey(newsId);
        if (news != null) {
            long count = news.getClickNum() != null ? news.getClickNum() : 0;
            redisUtil.set(key, count);
            return count;
        }
        
        return 0;
    }

    /**
     * 清除新闻相关缓存
     */
    private void clearNewsCache() {
        // 删除热点新闻缓存
        Set<String> keys = redisTemplate.keys(RedisConstants.CACHE_HOT_NEWS_KEY + "*");
        if (keys != null && !keys.isEmpty()) {
            redisUtil.delete(keys);
            logger.debug("清除热点新闻缓存: {} 个", keys.size());
        }
    }

    /**
     * 定时任务：将 Redis 中的浏览量同步到数据库（每 10 分钟）
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void syncViewCountToDatabase() {
        logger.info("开始同步新闻浏览量到数据库...");
        
        // 遍历所有新闻浏览量 key
        Set<String> keys = redisTemplate.keys(RedisConstants.VIEW_COUNT_NEWS_KEY + "*");
        if (keys == null || keys.isEmpty()) {
            logger.debug("没有需要同步的浏览量数据");
            return;
        }
        
        int syncCount = 0;
        for (String key : keys) {
            Object value = redisUtil.get(key);
            if (value == null) continue;
            
            try {
                Integer newsId = Integer.parseInt(key.substring(RedisConstants.VIEW_COUNT_NEWS_KEY.length()));
                long count = Long.parseLong(value.toString());
                
                // 更新数据库
                newsMapper.updateClickNum(newsId, (int) count);
                syncCount++;
            } catch (Exception e) {
                logger.error("同步浏览量失败: key={}, error={}", key, e.getMessage());
            }
        }
        
        logger.info("浏览量同步完成，共更新 {} 条记录", syncCount);
    }
}
