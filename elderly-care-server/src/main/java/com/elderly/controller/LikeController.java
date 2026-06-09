package com.elderly.controller;

import com.elderly.common.dto.PageQueryDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Like;
import com.elderly.service.LikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 帖子点赞相关
 */
@RestController
@RequestMapping(value = "/like")
public class LikeController{

    @Autowired
    private LikeService likeService;

    /**
     * 帖子点赞分页
     * @param map
     * @return
     */
    @GetMapping
    JSONReturn page(@RequestParam Map<String,Object> map){
        PageQueryDto queryDto = new PageQueryDto(map);
        PageVo pageVo = likeService.page(queryDto);
        return JSONReturn.success(pageVo);
    }





    /**
     * 新增帖子点赞
     * @param like
     * @return
     */
    @PostMapping
    public JSONReturn save(@RequestBody Like like){
        return likeService.save(like);
    }


    /**
     * 更新
     * @param like
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody Like like){
        Integer rows = likeService.update(like);
        return rows > 0 ? JSONReturn.success("更新成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 删除帖子点赞
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id")Integer id){
        Integer rows = likeService.del(id);
        return rows  > 0 ? JSONReturn.success("删除成功！") : JSONReturn.failed("操作失败！");
    }




}
