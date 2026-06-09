package com.elderly.service;

import com.elderly.common.vo.PageVo;
import com.elderly.entity.Bed;

import java.util.List;
import java.util.Map;

/**
 * 床位服务类
 */
public interface BedService {


    /**
     * 床位分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 添加床位
     * @param bed
     * @return
     */
    Integer save(Bed bed);

    /**
     * 更新床位
     * @param bed
     * @return
     */
    Integer update(Bed bed);


    /**
     * 删除床位
     * @param id
     * @return
     */
    Integer del(Integer id);



    /**
     * 根据ID查询床位
     * @param id
     * @return
     */
    Bed selectById(Integer id);


    /**
     * 查询房间床位
     * @return
     */
    List<Bed> findByRoom(Integer roomId);



}
