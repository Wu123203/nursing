package com.elderly.controller;

import com.elderly.common.dto.LoginDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.entity.Admin;
import com.elderly.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


/**
 * 管理员相关
 */
@RestController
@RequestMapping(value = "/admin")
public class AdminController{

    @Autowired
    private AdminService adminService;


    @PostMapping(value = "/login")
    public JSONReturn<?> login(@RequestBody LoginDto loginDto){
        return adminService.login(loginDto);
    }


    /**
     * 根据ID查询记录
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public JSONReturn selectById(@PathVariable(value = "id")Integer id){
        Admin admin = adminService.selectById(id);
        return JSONReturn.success(admin);
    }


    /**
     * 更新管理员信息
     * @param admin
     * @return
     */
    @PutMapping
    public JSONReturn update(@RequestBody Admin admin){
        Integer rows = adminService.update(admin);
        return rows  > 0 ? JSONReturn.success("更新成功！") : JSONReturn.failed("操作失败！");
    }







}
