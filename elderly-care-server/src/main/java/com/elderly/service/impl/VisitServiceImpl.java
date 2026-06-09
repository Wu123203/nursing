package com.elderly.service.impl;

import com.elderly.common.vo.PageVo;
import com.elderly.entity.Visit;
import com.elderly.mapper.VisitMapper;
import com.elderly.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 来访登记服务实现类
 */
@Service
public class VisitServiceImpl implements VisitService {

    @Autowired
    private VisitMapper visitMapper;

    @Override
    public PageVo page(Map map) {
        List<Visit> list = visitMapper.findList(map);
        Integer total = visitMapper.findTotal(map);
        return new PageVo(total,list);
    }

    @Override
    public Integer save(Visit visit) {
        Date date = new Date();
        visit.setStatus(0);
        visit.setVisitTime(date);
        visit.setCreateTime(date);
        visit.setUpdateTime(date);
        return visitMapper.insert(visit);
    }

    @Override
    public Integer update(Visit visit) {
        visit.setUpdateTime(new Date());
        return visitMapper.updateByPrimaryKeySelective(visit);
    }

    @Override
    public Integer leave(Integer id) {
        Visit visit = visitMapper.selectByPrimaryKey(id);
        visit.setStatus(1);
        visit.setLeaveTime(new Date());
        return visitMapper.updateByPrimaryKeySelective(visit);
    }

    @Override
    public Integer del(Integer id) {
        return visitMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Visit selectById(Integer id) {
        return visitMapper.selectByPrimaryKey(id);
    }

}
