package com.elderly.controller;

import com.elderly.common.dto.PageQueryDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Room;
import com.elderly.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 房间相关
 */
@RestController
@RequestMapping(value = "/room")
public class RoomController {

    @Autowired
    private RoomService roomService;
    /**
     * 房间分页
     * @param map
     * @return
     */
    @GetMapping
    public JSONReturn page(@RequestParam Map<String,Object> map){
        PageQueryDto queryDto = new PageQueryDto(map);
        PageVo pageVo = roomService.page(queryDto);
        return JSONReturn.success(pageVo);
    }





    /**
     * 新增房间
     * @param room
     * @return
     */
    @PostMapping
    public JSONReturn save(@RequestBody Room room){
        Integer rows = roomService.save(room);
        return rows > 0 ? JSONReturn.success("保存成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 更新
     * @param room
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody Room room){
        Integer rows = roomService.update(room);
        return rows > 0 ? JSONReturn.success("更新成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 删除房间
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id")Integer id){
        Integer rows = roomService.del(id);
        return rows  > 0 ? JSONReturn.success("删除成功！") : JSONReturn.failed("操作失败！");
    }



    /**
     * 查询所有
     * @return
     */
    @GetMapping(value = "/all")
    JSONReturn all(){
        List<Room> all = roomService.findAll();
        return JSONReturn.success(all);
    }


}
