package com.elderly.service.impl;

import com.elderly.common.vo.PageVo;
import com.elderly.entity.Room;
import com.elderly.mapper.RoomMapper;
import com.elderly.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 房间服务实现类
 */
@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public PageVo page(Map map) {
        List<Room> list = roomMapper.findList(map);
        Integer total = roomMapper.findTotal(map);
        return new PageVo(total,list);
    }

    @Override
    public Integer save(Room room) {
        Date date = new Date();
        room.setCreateTime(date);
        room.setUpdateTime(date);
        return roomMapper.insert(room);
    }

    @Override
    public Integer update(Room room) {
        room.setUpdateTime(new Date());
        return roomMapper.updateByPrimaryKeySelective(room);
    }

    @Override
    public Integer del(Integer id) {
        return roomMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Room selectById(Integer id) {
        return roomMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<Room> findAll() {
        return roomMapper.findAll();
    }
}
