package com.elderly.service;


import com.elderly.common.dto.LoginDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.entity.Admin;


/**
 * 管理员服务类
 */
public interface AdminService {


    /**
     * 管理员登录
     * @param loginDto
     * @return
     */
    JSONReturn login(LoginDto loginDto);



    /**
     * 修改
     * @param admin
     * @return
     */
    Integer update(Admin admin);





    /**
     * 根据ID查询管理员信息
     * @param id
     * @return
     */
    Admin selectById(Integer id);




}
