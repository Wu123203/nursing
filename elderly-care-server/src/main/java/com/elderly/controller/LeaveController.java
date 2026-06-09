package com.elderly.controller;

import com.elderly.common.dto.PageQueryDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Leave;
import com.elderly.service.LeaveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 外出报备相关
 */
@RestController
@RequestMapping(value = "/leave")
public class LeaveController {

    @Autowired
    private LeaveService leaveService;

    /**
     * 外出报备分页
     * @param map
     * @return
     */
    @GetMapping
    JSONReturn page(@RequestParam Map<String,Object> map){
        PageQueryDto queryDto = new PageQueryDto(map);
        PageVo pageVo = leaveService.page(queryDto);
        return JSONReturn.success(pageVo);
    }





    /**
     * 新增外出报备
     * @param leave
     * @return
     */
    @PostMapping
    public JSONReturn save(@RequestBody Leave leave){
        Integer rows = leaveService.save(leave);
        return rows > 0 ? JSONReturn.success("保存成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 更新
     * @param leave
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody Leave leave){
        Integer rows = leaveService.update(leave);
        return rows > 0 ? JSONReturn.success("更新成功！") : JSONReturn.failed("操作失败！");
    }



    /**
     * 删除外出报备
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id")Integer id){
        Integer rows = leaveService.del(id);
        return rows  > 0 ? JSONReturn.success("删除成功！") : JSONReturn.failed("操作失败！");
    }



}
