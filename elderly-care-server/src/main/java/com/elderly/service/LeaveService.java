package com.elderly.service;

import com.elderly.common.vo.PageVo;
import com.elderly.entity.Leave;

import java.util.Map;


/**
 * 外出报备服务层
 */
public interface LeaveService {


    /**
     *外出报备分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 添加外出报备
     * @param leave
     * @return
     */
    Integer save(Leave leave);

    /**
     * 更新外出报备
     * @param leave
     * @return
     */
    Integer update(Leave leave);




    /**
     * 删除外出报备
     * @param id
     * @return
     */
    Integer del(Integer id);



    /**
     * 根据ID查询外出报备
     * @param id
     * @return
     */
    Leave selectById(Integer id);

}
