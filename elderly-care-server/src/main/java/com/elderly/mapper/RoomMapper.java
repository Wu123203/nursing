package com.elderly.mapper;

import com.elderly.entity.Room;

import java.util.List;
import java.util.Map;

/**
 * 房间持久化层
 */
public interface RoomMapper {
    /**
     * 根据ID删除记录
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * 全量字段保存记录
     * @param record
     * @return
     */
    int insert(Room record);

    /**
     * 部分字段保存记录
     * @param record
     * @return
     */
    int insertSelective(Room record);

    /**
     * 根据Id查询记录
     * @param id
     * @return
     */
    Room selectByPrimaryKey(Integer id);

    /**
     * 部分字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKeySelective(Room record);

    /**
     * 全部字段更新记录信息
     * @param record
     * @return
     */
    int updateByPrimaryKey(Room record);


    /**
     * 查询记录
     * @param map
     * @return
     */
    List<Room> findList(Map map);


    /**
     * 统计记录
     * @param map
     * @return
     */
    Integer findTotal(Map map);


    /**
     * 查询所有
     * @return
     */
    List<Room> findAll();


    /**
     * 统计总房间数
     * @return
     */
    Integer countTotal();
}