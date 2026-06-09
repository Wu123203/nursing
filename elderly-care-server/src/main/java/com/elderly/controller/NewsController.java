package com.elderly.controller;

import com.elderly.common.dto.PageQueryDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.News;
import com.elderly.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 文章相关
 */
@RestController
@RequestMapping(value = "/news")
public class NewsController {

    @Autowired
    private NewsService newsService;

    /**
     * 文章分页
     * 
     * @param map
     * @return
     */
    @GetMapping
    JSONReturn page(@RequestParam Map<String, Object> map) {
        PageQueryDto queryDto = new PageQueryDto(map);
        PageVo pageVo = newsService.page(queryDto);
        return JSONReturn.success(pageVo);
    }

    /**
     * 新增文章
     * 
     * @param news
     * @return
     */
    @PostMapping
    public JSONReturn save(@RequestBody News news) {
        Integer rows = newsService.save(news);
        return rows > 0 ? JSONReturn.success("保存成功！") : JSONReturn.failed("操作失败！");
    }

    /**
     * 更新
     * 
     * @param news
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody News news) {
        Integer rows = newsService.update(news);
        return rows > 0 ? JSONReturn.success("更新成功！") : JSONReturn.failed("操作失败！");
    }

    /**
     * 文章详情
     * 
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    JSONReturn get(@PathVariable(value = "id") Integer id) {
        News news = newsService.selectById(id);
        news.setClickNum(news.getClickNum() + 1);
        newsService.update(news);
        return JSONReturn.success(news);
    }

    /**
     * 删除文章
     * 
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id") Integer id) {
        Integer rows = newsService.del(id);
        return rows > 0 ? JSONReturn.success("删除成功！") : JSONReturn.failed("操作失败！");
    }

    /**
     * 查询最新的文章
     * 
     * @param limit 几条
     * @return
     */
    @GetMapping(value = "/latest/{limit}")
    JSONReturn latestTop(@PathVariable(value = "limit") Integer limit) {
        List<News> all = newsService.findLatestTopList(limit);
        return JSONReturn.success(all);
    }

    /**
     * 查询点击量最高的几条文章
     * 
     * @param limit 几条
     * @return
     */
    @GetMapping(value = "/click/{limit}")
    JSONReturn clickTop(@PathVariable(value = "limit") Integer limit) {
        List<News> all = newsService.findClickTopList(limit);
        return JSONReturn.success(all);
    }

}
