package com.elderly.service;

import com.elderly.common.vo.PageVo;
import com.elderly.entity.Visit;

import java.util.Map;


/**
 * 来犯登记服务层
 */
public interface VisitService {


    /**
     *来犯登记分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 添加来犯登记
     * @param visit
     * @return
     */
    Integer save(Visit visit);

    /**
     * 更新来犯登记
     * @param visit
     * @return
     */
    Integer update(Visit visit);



    /**
     * 离开
     * @param id
     * @return
     */
    Integer leave(Integer id);


    /**
     * 删除来犯登记
     * @param id
     * @return
     */
    Integer del(Integer id);



    /**
     * 根据ID查询来犯登记
     * @param id
     * @return
     */
    Visit selectById(Integer id);

    
    
}
