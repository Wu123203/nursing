package com.elderly.controller;

import com.elderly.common.dto.PageQueryDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Bed;
import com.elderly.service.BedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 床位相关
 */
@RestController
@RequestMapping(value = "/bed")
public class BedController {

    @Autowired
    private BedService bedService;
    /**
     * 床位分页
     * @param map
     * @return
     */
    @GetMapping
    public JSONReturn page(@RequestParam Map<String,Object> map){
        PageQueryDto queryDto = new PageQueryDto(map);
        PageVo pageVo = bedService.page(queryDto);
        return JSONReturn.success(pageVo);
    }





    /**
     * 新增床位
     * @param bed
     * @return
     */
    @PostMapping
    public JSONReturn save(@RequestBody Bed bed){
        Integer rows = bedService.save(bed);
        return rows > 0 ? JSONReturn.success("保存成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 更新
     * @param bed
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody Bed bed){
        Integer rows = bedService.update(bed);
        return rows > 0 ? JSONReturn.success("更新成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 删除床位
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id")Integer id){
        Integer rows = bedService.del(id);
        return rows  > 0 ? JSONReturn.success("删除成功！") : JSONReturn.failed("操作失败！");
    }



    /**
     * 查询所有
     * @return
     */
    @GetMapping(value = "/room/{roomId}")
    JSONReturn all(@PathVariable(value = "roomId")Integer roomId){
        List<Bed> all = bedService.findByRoom(roomId);
        return JSONReturn.success(all);
    }


}
