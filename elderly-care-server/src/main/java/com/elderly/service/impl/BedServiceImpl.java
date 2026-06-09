package com.elderly.service.impl;

import com.elderly.common.vo.PageVo;
import com.elderly.entity.Bed;
import com.elderly.mapper.BedMapper;
import com.elderly.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 床位服务实现类
 * 提供床位的增删改查、分页查询等业务逻辑
 */
@Service
public class BedServiceImpl implements BedService {

    /** 床位数据访问接口 */
    @Autowired
    private BedMapper bedMapper;

    /**
     * 分页查询床位列表
     * 
     * @param map 查询参数，包含分页信息(pageNum/pageSize)和筛选条件(roomId/status/bedNo)
     * @return 分页结果对象，包含总记录数和当前页数据
     */
    @Override
    public PageVo page(Map map) {
        List<Bed> list = bedMapper.findList(map);
        Integer total = bedMapper.findTotal(map);
        return new PageVo(total, list);
    }

    /**
     * 新增床位
     * 新增时自动设置：状态为0(空闲)、创建时间、更新时间
     * 
     * @param bed 床位实体对象
     * @return 影响的行数
     */
    @Override
    public Integer save(Bed bed) {
        Date date = new Date();
        bed.setStatus(0); // 设置床位状态为"空闲"
        bed.setCreateTime(date); // 设置创建时间
        bed.setUpdateTime(date); // 设置更新时间
        return bedMapper.insert(bed);
    }

    /**
     * 更新床位信息
     * 更新时自动刷新更新时间
     * 
     * @param bed 床位实体对象
     * @return 影响的行数
     */
    @Override
    public Integer update(Bed bed) {
        bed.setUpdateTime(new Date()); // 更新时刷新更新时间
        return bedMapper.updateByPrimaryKeySelective(bed);
    }

    /**
     * 删除床位
     * 
     * @param id 床位ID
     * @return 影响的行数
     */
    @Override
    public Integer del(Integer id) {
        return bedMapper.deleteByPrimaryKey(id);
    }

    /**
     * 根据ID查询床位详情
     * 
     * @param id 床位ID
     * @return 床位实体对象
     */
    @Override
    public Bed selectById(Integer id) {
        return bedMapper.selectByPrimaryKey(id);
    }

    /**
     * 查询指定房间下的所有床位
     * 
     * @param roomId 房间ID
     * @return 该房间下所有床位列表
     */
    @Override
    public List<Bed> findByRoom(Integer roomId) {
        return bedMapper.findByRoom(roomId);
    }
}
