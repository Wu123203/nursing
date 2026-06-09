package com.elderly.controller;

import com.elderly.common.dto.PageQueryDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Live;
import com.elderly.service.LiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 帖子点赞相关
 */
@RestController
@RequestMapping(value = "/live")
public class LiveController {

    @Autowired
    private LiveService liveService;

    /**
     * 帖子点赞分页
     * @param map
     * @return
     */
    @GetMapping
    JSONReturn page(@RequestParam Map<String,Object> map){
        PageQueryDto queryDto = new PageQueryDto(map);
        PageVo pageVo = liveService.page(queryDto);
        return JSONReturn.success(pageVo);
    }





    /**
     * 新增帖子点赞
     * @param live
     * @return
     */
    @PostMapping
    public JSONReturn save(@RequestBody Live live){
        return liveService.save(live);
    }


    /**
     * 更新
     * @param live
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody Live live){
        return liveService.update(live);
    }


    /**
     * 退房
     * @param live
     * @return
     */
    @PutMapping(value = "/finish")
    public JSONReturn finish(@RequestBody Live live){
        return liveService.finish(live);
    }



    /**
     * 删除帖子点赞
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id")Integer id){
        Integer rows = liveService.del(id);
        return rows  > 0 ? JSONReturn.success("删除成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 查询所有
     * @return
     */
    @GetMapping(value = "/room/{roomId}")
    JSONReturn all(@PathVariable(value = "roomId")Integer roomId){
        List<Live> all = liveService.findByRoom(roomId);
        return JSONReturn.success(all);
    }


}
