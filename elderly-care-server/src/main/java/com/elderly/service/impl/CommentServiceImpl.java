package com.elderly.service.impl;

import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Comment;
import com.elderly.entity.News;
import com.elderly.mapper.CommentMapper;
import com.elderly.mapper.NewsMapper;
import com.elderly.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 新闻评论服务实现类
 */
@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private NewsMapper newsMapper;

    @Override
    public PageVo page(Map map) {
        List<Comment> list = commentMapper.findList(map);
        Integer total = commentMapper.findTotal(map);
        return new PageVo(total,list);
    }

    @Override
    public JSONReturn save(Comment comment) {

        Date date = new Date();
        comment.setCreateTime(date);
        comment.setUpdateTime(date);
        commentMapper.insert(comment);
        //评论量+1
        News news = newsMapper.selectByPrimaryKey(comment.getNewsId());
        news.setCommentNum(news.getCommentNum() + 1);
        newsMapper.updateByPrimaryKeySelective(news);
        return JSONReturn.success("保存成功！");
    }

    @Override
    public Integer update(Comment comment) {
        comment.setUpdateTime(new Date());
        return commentMapper.updateByPrimaryKeySelective(comment);
    }

    @Override
    public Integer del(Integer id) {
        Comment comment = commentMapper.selectByPrimaryKey(id);
        //评论量-1
        News news = newsMapper.selectByPrimaryKey(comment.getNewsId());
        news.setCommentNum(news.getCommentNum() - 1);
        newsMapper.updateByPrimaryKeySelective(news);
        //删除评论
        return commentMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Comment selectById(Integer id) {
        return commentMapper.selectByPrimaryKey(id);
    }
}
