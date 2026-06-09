package com.elderly.controller;

import com.elderly.common.dto.PageQueryDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Visit;
import com.elderly.service.VisitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 来访登记相关
 */
@RestController
@RequestMapping(value = "/visit")
public class VisitController {

    @Autowired
    private VisitService visitService;

    /**
     * 来访登记分页
     * @param map
     * @return
     */
    @GetMapping
    public JSONReturn page(@RequestParam Map<String,Object> map){
        PageQueryDto queryDto = new PageQueryDto(map);
        PageVo pageVo = visitService.page(queryDto);
        return JSONReturn.success(pageVo);
    }





    /**
     * 新增来访登记
     * @param visit
     * @return
     */
    @PostMapping
    public JSONReturn save(@RequestBody Visit visit){
        Integer rows = visitService.save(visit);
        return rows > 0 ? JSONReturn.success("保存成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 更新
     * @param visit
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody Visit visit){
        Integer rows = visitService.update(visit);
        return rows > 0 ? JSONReturn.success("更新成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 更新
     * @param visit
     * @return
     */
    @PutMapping(value = "/leave")
    public JSONReturn leave(@RequestBody Visit visit){
        Integer rows = visitService.leave(visit.getId());
        return rows > 0 ? JSONReturn.success("更新成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 删除来访登记
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id")Integer id){
        Integer rows = visitService.del(id);
        return rows  > 0 ? JSONReturn.success("删除成功！") : JSONReturn.failed("操作失败！");
    }


}
