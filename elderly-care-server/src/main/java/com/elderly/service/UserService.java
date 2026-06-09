package com.elderly.service;

import com.elderly.common.dto.LoginDto;
import com.elderly.common.vo.CountVo;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.PageVo;
import com.elderly.entity.User;
import jakarta.servlet.http.HttpSession;

import java.util.List;
import java.util.Map;


/**
 * 用户服务类
 */
public interface UserService {


    /**
     * 管理员登录
     * @param loginDto
     * @param session
     * @return
     */
    JSONReturn login(LoginDto loginDto, HttpSession session);


    /**
     * 用户分页
     * @param map
     * @return
     */
    PageVo page(Map map);

    /**
     * 添加用户用户
     * @param user
     * @return
     */
    JSONReturn save(User user);


    /**
     * 修改
     * @param user
     * @return
     */
    JSONReturn update(User user);



    /**
     * 删除用户
     * @param id
     * @return
     */
    Integer del(Integer id);



    /**
     * 根据ID查询用户信息
     * @param id
     * @return
     */
    User selectById(Integer id);




    /**
     * 查询所有
     * @return
     */
    List<User> findAll();



    /**
     * 疫苗接种统计
     * @return
     */
    CountVo count();
}
