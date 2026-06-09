package com.elderly.service;

import com.elderly.common.vo.PageVo;
import com.elderly.entity.Resthome;

import java.util.Map;

/**
 *
 * 入院指南服务类
 */
public interface ResthomeService {

    /**
     * 入院指南分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 入院指南保存
     * @param resthome
     * @return
     */
    Integer save(Resthome resthome);

    /**
     * 入院指南更新
     * @param resthome
     * @return
     */
    Integer update(Resthome resthome);

    /**
     * 入院指南删除
     * @param id
     * @return
     */
    Integer del(Integer id);

    /**
     * 根据ID查询
     * @param id
     * @return
     */
    Resthome selectById(Integer id);


}
