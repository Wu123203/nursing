package com.elderly.service.impl;

import com.elderly.common.vo.PageVo;
import com.elderly.entity.Leave;
import com.elderly.mapper.LeaveMapper;
import com.elderly.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 外出报备服务实现类
 */
@Service
public class LeaveServiceImpl implements LeaveService {

    @Autowired
    private LeaveMapper leaveMapper;

    @Override
    public PageVo page(Map map) {
        List<Leave> list = leaveMapper.findList(map);
        Integer total = leaveMapper.findTotal(map);
        return new PageVo(total,list);
    }

    @Override
    public Integer save(Leave leave) {
        Date date = new Date();
        leave.setStatus(0);
        leave.setCreateTime(date);
        leave.setUpdateTime(date);
        return leaveMapper.insert(leave);
    }

    @Override
    public Integer update(Leave leave) {
        leave.setUpdateTime(new Date());
        return leaveMapper.updateByPrimaryKeySelective(leave);
    }



    @Override
    public Integer del(Integer id) {
        return leaveMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Leave selectById(Integer id) {
        return leaveMapper.selectByPrimaryKey(id);
    }

}
