package com.elderly.controller;

import com.elderly.common.dto.PageQueryDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Comment;
import com.elderly.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


/**
 * 新闻评论相关
 */
@RestController
@RequestMapping(value = "/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    /**
     * 评论分页
     * @param map
     * @return
     */
    @GetMapping
    JSONReturn page(@RequestParam Map<String,Object> map){
        PageQueryDto queryDto = new PageQueryDto(map);
        PageVo pageVo = commentService.page(queryDto);
        return JSONReturn.success(pageVo);
    }

    /**
     * 新增评论
     * @param comment
     * @return
     */
    @PostMapping
    public JSONReturn save(@RequestBody Comment comment){
        return commentService.save(comment);
    }


    /**
     * 更新
     * @param comment
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody Comment comment){
        Integer rows = commentService.update(comment);
        return rows  > 0 ? JSONReturn.success("保存成功！") : JSONReturn.failed("操作失败！");
    }




    /**
     * 删除评论
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id")Integer id){
        Integer rows = commentService.del(id);
        return rows  > 0 ? JSONReturn.success("删除成功！") : JSONReturn.failed("操作失败！");

    }


}
