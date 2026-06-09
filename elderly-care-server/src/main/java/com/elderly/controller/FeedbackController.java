package com.elderly.controller;

import com.elderly.common.dto.PageQueryDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Feedback;
import com.elderly.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 留言反馈相关
 */
@RestController
@RequestMapping(value = "/feedback")
public class FeedbackController{

    @Autowired
    private FeedbackService feedbackService;

    /**
     * 留言反馈分页
     * @param map
     * @return
     */
    @GetMapping
    JSONReturn page(@RequestParam Map<String,Object> map){
        PageQueryDto queryDto = new PageQueryDto(map);
        PageVo pageVo = feedbackService.page(queryDto);
        return JSONReturn.success(pageVo);
    }





    /**
     * 新增留言反馈
     * @param feedback
     * @return
     */
    @PostMapping
    public JSONReturn save(@RequestBody Feedback feedback){
        Integer rows = feedbackService.save(feedback);
        return rows > 0 ? JSONReturn.success("保存成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 更新
     * @param feedback
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody Feedback feedback){
        Integer rows = feedbackService.update(feedback);
        return rows > 0 ? JSONReturn.success("更新成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 删除留言反馈
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id")Integer id){
        Integer rows = feedbackService.del(id);
        return rows  > 0 ? JSONReturn.success("删除成功！") : JSONReturn.failed("操作失败！");
    }



    /**
     * 查询最新的几条评论
     * @param limit
     * @return
     */
    @GetMapping(value = "/latest/{limit}")
    JSONReturn latestTop(@PathVariable(value = "limit")Integer limit){
        List<Feedback> all = feedbackService.findLatestTopList(limit);
        return JSONReturn.success(all);
    }


}
