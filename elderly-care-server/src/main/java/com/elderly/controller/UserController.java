package com.elderly.controller;

import com.elderly.common.dto.PageQueryDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.User;
import com.elderly.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 用户相关
 */
@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 学生分页
     * @param map
     * @return
     */
    @GetMapping
    JSONReturn page(@RequestParam Map<String,Object> map){
        PageQueryDto queryDto = new PageQueryDto(map);
        PageVo pageVo = userService.page(queryDto);
        return JSONReturn.success(pageVo);
    }


    /**
     * 根据ID查询记录
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public JSONReturn selectById(@PathVariable(value = "id")Integer id){
        User user = userService.selectById(id);
        return JSONReturn.success(user);
    }




    /**
     * 添加用户
     * @param user
     * @return
     */
    @PostMapping
    public JSONReturn save(@RequestBody User user){
        return userService.save(user);
    }


    /**
     * 更新用户
     * @param user
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody User user){
        return userService.update(user);
    }


    /**
     * 删除用户
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public JSONReturn delete(@PathVariable(value = "id")Integer id){
        Integer rows = userService.del(id);
        return rows  > 0 ? JSONReturn.success("删除成功！") : JSONReturn.failed("操作失败！");
    }


    /**
     * 查询所有
     * @return
     */
    @GetMapping(value = "/all")
    JSONReturn all(){
        List<User> all = userService.findAll();
        return JSONReturn.success(all);
    }



}
