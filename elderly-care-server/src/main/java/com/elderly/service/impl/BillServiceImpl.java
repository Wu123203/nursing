package com.elderly.service.impl;

import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Bill;
import com.elderly.entity.Live;
import com.elderly.entity.User;
import com.elderly.mapper.BillMapper;
import com.elderly.mapper.LiveMapper;
import com.elderly.mapper.UserMapper;
import com.elderly.service.BillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 缴费账单服务实现类
 */
@Service
public class BillServiceImpl implements BillService {

    @Autowired
    private BillMapper billMapper;

    @Autowired
    private LiveMapper liveMapper;

    @Autowired
    private UserMapper userMapper;

    @Override
    public PageVo page(Map map) {
        List<Bill> list = billMapper.findList(map);
        Integer total = billMapper.findTotal(map);
        return new PageVo(total,list);
    }

    @Override
    public JSONReturn save(Bill bill) {
        Bill exist  = billMapper.selectExist(bill.getLiveId(),bill.getMonth());
        if(exist != null){
            return JSONReturn.failed("该人员当月费用录入！");
        }
        //保存缴费记录
        Date date = new Date();
        bill.setStatus(0);
        bill.setTotalCost(bill.getBedCost() + bill.getCareCost() + bill.getFoodCost());
        bill.setCreateTime(date);
        bill.setUpdateTime(date);
        billMapper.insert(bill);
        return JSONReturn.success("保存成功！");
    }

    @Override
    public JSONReturn update(Bill bill) {
        Bill b = billMapper.selectByPrimaryKey(bill.getId());
        if(b.getLiveId() != bill.getLiveId() || !b.getMonth().equals(bill.getMonth())){
            Bill exist  = billMapper.selectExist(bill.getLiveId(),bill.getMonth());
            if(exist != null){
                return JSONReturn.failed("该人员当月费用录入！");
            }
        }
        bill.setUpdateTime(new Date());
        billMapper.updateByPrimaryKeySelective(bill);
        return JSONReturn.success("保存成功！");
    }

    @Override
    public JSONReturn pay(Integer id) {
        Bill bill = billMapper.selectByPrimaryKey(id);
        if(bill.getStatus() == 1){
            return JSONReturn.failed("账单已缴费！");
        }
        Live live = liveMapper.selectByPrimaryKey(bill.getLiveId());
        User user = userMapper.selectByPrimaryKey(live.getUserId());
        if(user.getAmount() < bill.getTotalCost()){
            return JSONReturn.failed("账户余额不足！");
        }
        user.setAmount(user.getAmount() - bill.getTotalCost());
        userMapper.updateByPrimaryKeySelective(user);
        bill.setStatus(1);
        billMapper.updateByPrimaryKeySelective(bill);
        return JSONReturn.success("缴费成功！");
    }

    @Override
    public Integer del(Integer id) {
        return billMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Bill selectById(Integer id) {
        return billMapper.selectByPrimaryKey(id);
    }

}
