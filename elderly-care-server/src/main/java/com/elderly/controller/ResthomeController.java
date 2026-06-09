package com.elderly.controller;

import com.elderly.common.dto.PageQueryDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Resthome;
import com.elderly.service.ResthomeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 入院指南相关控制器
 */
@RestController
@RequestMapping(value = "/resthome")
public class ResthomeController {

    @Autowired
    private ResthomeService resthomeService;

    /**
     * 入院指南查询分页
     * @return
     */
    @GetMapping
    public JSONReturn page(@RequestParam Map<String,Object> map){
        PageQueryDto queryDto = new PageQueryDto(map);
        PageVo pageVo = resthomeService.page(queryDto);
        return JSONReturn.success(pageVo);
    }



    /**
     * 添加入院指南
     * @param resthome
     * @return
     */
    @PostMapping
    public JSONReturn save(@RequestBody Resthome resthome){
        Integer rows =  resthomeService.save(resthome);
        return rows > 0 ? JSONReturn.success("保存成功！") : JSONReturn.failed("操作失败！");
    }



    /**
     * 更新
     * @param resthome
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody Resthome resthome){
        Integer rows = resthomeService.update(resthome);
        return rows > 0 ? JSONReturn.success("更新成功！") : JSONReturn.failed("操作失败！");
    }



    /**
     * 入院指南详情
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    JSONReturn get(@PathVariable(value = "id")Integer id){
        Resthome resthome = resthomeService.selectById(id);
        return JSONReturn.success(resthome);
    }




    /**
     * 删除入院指南
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id")Integer id){
        Integer rows = resthomeService.del(id);
        return rows  > 0 ? JSONReturn.success("删除成功！") : JSONReturn.failed("操作失败！");
    }

}
