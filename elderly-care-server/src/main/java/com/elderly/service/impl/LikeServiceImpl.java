package com.elderly.service.impl;

import com.elderly.common.config.RedisConstants;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Like;
import com.elderly.entity.News;
import com.elderly.mapper.LikeMapper;
import com.elderly.mapper.NewsMapper;
import com.elderly.service.LikeService;
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
 * 新闻点赞服务实现类
 * 使用 Redis 实现点赞功能，支持防重复点赞和高性能计数
 */
@Service
public class LikeServiceImpl implements LikeService {

    private static final Logger logger = LoggerFactory.getLogger(LikeServiceImpl.class);

    @Autowired
    private LikeMapper likeMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Autowired
    private RedisUtil redisUtil;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public PageVo page(Map map) {
        List<Like> list = likeMapper.findList(map);
        Integer total = likeMapper.findTotal(map);
        return new PageVo(total, list);
    }

    @Override
    public JSONReturn save(Like like) {
        Integer userId = like.getUserId();
        Integer newsId = like.getNewsId();

        // 使用 Redis Set 检查用户是否已点赞
        String likeUserKey = RedisConstants.LIKE_USER_KEY + newsId;
        Boolean isLiked = redisUtil.sIsMember(likeUserKey, userId.toString());

        if (Boolean.TRUE.equals(isLiked)) {
            return JSONReturn.failed("操作失败，您已点赞！");
        }

        // 添加用户到点赞集合
        redisUtil.sAdd(likeUserKey, userId.toString());

        // 点赞数 +1（Redis 原子操作）
        String likeCountKey = RedisConstants.LIKE_COUNT_KEY + newsId;
        redisUtil.increment(likeCountKey);

        // 同时写入数据库（保证数据持久化）
        Date date = new Date();
        like.setCreateTime(date);
        like.setUpdateTime(date);
        likeMapper.insert(like);

        logger.debug("点赞成功: userId={}, newsId={}", userId, newsId);
        return JSONReturn.success("点赞成功！");
    }

    @Override
    public Integer update(Like like) {
        like.setUpdateTime(new Date());
        return likeMapper.updateByPrimaryKeySelective(like);
    }

    @Override
    public Integer del(Integer id) {
        Like like = likeMapper.selectByPrimaryKey(id);
        if (like == null) {
            return 0;
        }

        Integer userId = like.getUserId();
        Integer newsId = like.getNewsId();

        // 从 Redis Set 中移除用户
        String likeUserKey = RedisConstants.LIKE_USER_KEY + newsId;
        redisUtil.sRemove(likeUserKey, userId.toString());

        // 点赞数 -1
        String likeCountKey = RedisConstants.LIKE_COUNT_KEY + newsId;
        redisUtil.decrement(likeCountKey);

        // 删除数据库记录
        int result = likeMapper.deleteByPrimaryKey(id);

        logger.debug("取消点赞成功: userId={}, newsId={}", userId, newsId);
        return result;
    }

    @Override
    public Like selectById(Integer id) {
        return likeMapper.selectByPrimaryKey(id);
    }

    /**
     * 获取新闻点赞数
     *
     * @param newsId 新闻 ID
     * @return 点赞数
     */
    public long getLikeCount(Integer newsId) {
        String key = RedisConstants.LIKE_COUNT_KEY + newsId;
        Object obj = redisUtil.get(key);

        if (obj != null) {
            return Long.parseLong(obj.toString());
        }

        // 缓存未命中，从数据库查询并同步到 Redis
        News news = newsMapper.selectByPrimaryKey(newsId);
        if (news != null) {
            long count = news.getLikeNum() != null ? news.getLikeNum() : 0;
            redisUtil.set(key, count);
            return count;
        }

        return 0;
    }

    /**
     * 检查用户是否已点赞
     *
     * @param userId  用户 ID
     * @param newsId 新闻 ID
     * @return 是否已点赞
     */
    public boolean isLiked(Integer userId, Integer newsId) {
        String key = RedisConstants.LIKE_USER_KEY + newsId;
        return Boolean.TRUE.equals(redisUtil.sIsMember(key, userId.toString()));
    }

    /**
     * 定时任务：将 Redis 中的点赞数同步到数据库（每 10 分钟）
     */
    @Scheduled(cron = "0 */10 * * * ?")
    public void syncLikeCountToDatabase() {
        logger.info("开始同步新闻点赞数到数据库...");

        // 遍历所有点赞数 key
        Set<String> keys = redisTemplate.keys(RedisConstants.LIKE_COUNT_KEY + "*");
        if (keys == null || keys.isEmpty()) {
            logger.debug("没有需要同步的点赞数数据");
            return;
        }

        int syncCount = 0;
        for (String key : keys) {
            Object value = redisUtil.get(key);
            if (value == null) continue;

            try {
                Integer newsId = Integer.parseInt(key.substring(RedisConstants.LIKE_COUNT_KEY.length()));
                long count = Long.parseLong(value.toString());

                // 更新数据库
                News news = newsMapper.selectByPrimaryKey(newsId);
                if (news != null) {
                    news.setLikeNum((int) count);
                    newsMapper.updateByPrimaryKeySelective(news);
                    syncCount++;
                }
            } catch (Exception e) {
                logger.error("同步点赞数失败: key={}, error={}", key, e.getMessage());
            }
        }

        logger.info("点赞数同步完成，共更新 {} 条记录", syncCount);
    }
}
