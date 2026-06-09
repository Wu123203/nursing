package com.elderly.service;

import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Bill;

import java.util.Map;

/**
 * 缴费账单服务类
 */
public interface BillService {


    /**
     * 缴费账单分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 添加缴费账单
     * @param bill
     * @return
     */
    JSONReturn save(Bill bill);

    /**
     * 更新缴费账单
     * @param bill
     * @return
     */
    JSONReturn update(Bill bill);


    /**
     * 缴费
     * @param id
     * @return
     */
    JSONReturn pay(Integer id);


    /**
     * 删除缴费账单
     * @param id
     * @return
     */
    Integer del(Integer id);



    /**
     * 根据ID查询缴费账单
     * @param id
     * @return
     */
    Bill selectById(Integer id);

}
