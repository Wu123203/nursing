package com.elderly.service.impl;

import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Bed;
import com.elderly.entity.Live;
import com.elderly.mapper.BedMapper;
import com.elderly.mapper.LiveMapper;
import com.elderly.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 入住登记服务实现类
 */
@Service
public class LiveServiceImpl implements LiveService {

    @Autowired
    private LiveMapper liveMapper;

    @Autowired
    private BedMapper bedMapper;

    @Override
    public PageVo page(Map map) {
        List<Live> list = liveMapper.findList(map);
        Integer total = liveMapper.findTotal(map);
        return new PageVo(total,list);
    }

    @Override
    public JSONReturn save(Live live) {

        Bed bed = bedMapper.selectByPrimaryKey(live.getBedId());
        if(bed.getStatus() == 1){
            return JSONReturn.failed("该床位已被占用！");
        }
        //更新床位状态
        bed.setStatus(1);
        bedMapper.updateByPrimaryKeySelective(bed);
        //保存入住记录
        Date date = new Date();
        live.setStatus(0);
        live.setCreateTime(date);
        live.setUpdateTime(date);
        liveMapper.insert(live);
        return JSONReturn.success("点赞成功！");
    }

    @Override
    public JSONReturn update(Live live) {
        Live l = liveMapper.selectByPrimaryKey(live.getId());
        if(l.getBedId() != live.getBedId()){
            Bed newBed = bedMapper.selectByPrimaryKey(live.getBedId());
            if(newBed.getStatus() == 1){
                return JSONReturn.failed("该床位已被占用！");
            }
            //释放原来的床位状态
            Bed oldBed = bedMapper.selectByPrimaryKey(l.getBedId());
            oldBed.setStatus(0);
            bedMapper.updateByPrimaryKeySelective(oldBed);
            //更新新床位状态
            newBed.setStatus(1);
            bedMapper.updateByPrimaryKeySelective(newBed);
        }
        live.setUpdateTime(new Date());
        liveMapper.updateByPrimaryKeySelective(live);
        return JSONReturn.success("保存成功！");
    }

    @Override
    public JSONReturn finish(Live live) {
        Live l = liveMapper.selectByPrimaryKey(live.getId());
        if(l.getStatus() == 1){
            return JSONReturn.failed("您已退房！");
        }
        //更新床位状态
        Bed bed = bedMapper.selectByPrimaryKey(l.getBedId());
        bed.setStatus(0);
        bedMapper.updateByPrimaryKeySelective(bed);
        //更新入住记录
        live.setStatus(1);
        liveMapper.updateByPrimaryKeySelective(live);
        return JSONReturn.success("退房成功！");
    }

    @Override
    public Integer del(Integer id) {
        return liveMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Live selectById(Integer id) {
        return liveMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Live> findByRoom(Integer roomId) {
        return liveMapper.findByRoom(roomId);
    }
}
