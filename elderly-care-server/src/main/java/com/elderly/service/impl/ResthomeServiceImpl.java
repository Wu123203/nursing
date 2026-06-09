package com.elderly.service.impl;

import com.elderly.common.vo.PageVo;
import com.elderly.entity.Resthome;
import com.elderly.mapper.ResthomeMapper;
import com.elderly.service.ResthomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 入院指南实现类
 */
@Service
public class ResthomeServiceImpl implements ResthomeService {

    @Autowired
    private ResthomeMapper resthomeMapper;

    @Override
    public PageVo page(Map map) {
        List<Resthome> list = resthomeMapper.findList(map);
        Integer total = resthomeMapper.findTotal(map);
        return new PageVo(total,list);
    }

    @Override
    public Integer save(Resthome resthome) {
        Date date = new Date();
        resthome.setCreateTime(date);
        resthome.setUpdateTime(date);
        resthome.setClickNum(0);
        return resthomeMapper.insert(resthome);
    }

    @Override
    public Integer update(Resthome resthome) {
        resthome.setUpdateTime(new Date());
        return resthomeMapper.updateByPrimaryKeySelective(resthome);
    }

    @Override
    public Integer del(Integer id) {
        return resthomeMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Resthome selectById(Integer id) {
        return resthomeMapper.selectByPrimaryKey(id);
    }


}
