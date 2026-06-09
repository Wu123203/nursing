package com.elderly.controller;

import com.elderly.common.dto.PageQueryDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.Notice;
import com.elderly.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 公告相关
 */
@RestController
@RequestMapping(value = "/notice")
public class NoticeController{

    @Autowired
    private NoticeService noticeService;

    /**
     * 公告分页
     * @param map
     * @return
     */
    @GetMapping
    JSONReturn page(@RequestParam Map<String,Object> map){
        PageQueryDto queryDto = new PageQueryDto(map);
        PageVo pageVo = noticeService.page(queryDto);
        return JSONReturn.success(pageVo);
    }





    /**
     * 新增公告
     * @param notice
     * @return
     */
    @PostMapping
    public JSONReturn save(@RequestBody Notice notice){
        Integer rows = noticeService.save(notice);
        return rows > 0 ? JSONReturn.success("保存成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 更新
     * @param notice
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody Notice notice){
        Integer rows = noticeService.update(notice);
        return rows > 0 ? JSONReturn.success("更新成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 公告详情
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    JSONReturn get(@PathVariable(value = "id")Integer id){
        Notice notice = noticeService.selectById(id);
        return JSONReturn.success(notice);
    }



    /**
     * 删除公告
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id")Integer id){
        Integer rows = noticeService.del(id);
        return rows  > 0 ? JSONReturn.success("删除成功！") : JSONReturn.failed("操作失败！");
    }



    /**
     * 查询前几条公告
     * @param limit
     * @return
     */
    @GetMapping(value = "/latest/{limit}")
    JSONReturn latestTop(@PathVariable(value = "limit")Integer limit){
        List<Notice> all = noticeService.findLatestTopList(limit);
        return JSONReturn.success(all);
    }





}
