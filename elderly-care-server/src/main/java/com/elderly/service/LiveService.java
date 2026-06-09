package com.elderly.service;

import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Live;

import java.util.List;
import java.util.Map;

/**
 * 入住登记服务类
 */
public interface LiveService {


    /**
     * 入住登记分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 添加入住登记
     * @param live
     * @return
     */
    JSONReturn save(Live live);

    /**
     * 更新入住登记
     * @param live
     * @return
     */
    JSONReturn update(Live live);


    /**
     * 退房
     * @param live
     * @return
     */
    JSONReturn finish(Live live);


    /**
     * 删除入住登记
     * @param id
     * @return
     */
    Integer del(Integer id);



    /**
     * 根据ID查询入住登记
     * @param id
     * @return
     */
    Live selectById(Integer id);


    /**
     * 查询入住人
     * @param roomId
     * @return
     */
    List<Live> findByRoom(Integer roomId);
}
