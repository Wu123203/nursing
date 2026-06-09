package com.elderly.service;

import com.elderly.common.vo.PageVo;
import com.elderly.entity.Room;

import java.util.List;
import java.util.Map;

/**
 * 房间服务类
 */
public interface RoomService {


    /**
     * 房间分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 添加房间
     * @param room
     * @return
     */
    Integer save(Room room);

    /**
     * 更新房间
     * @param room
     * @return
     */
    Integer update(Room room);


    /**
     * 删除房间
     * @param id
     * @return
     */
    Integer del(Integer id);



    /**
     * 根据ID查询房间
     * @param id
     * @return
     */
    Room selectById(Integer id);


    /**
     * 查询所有房间
     * @return
     */
    List<Room> findAll();



}
