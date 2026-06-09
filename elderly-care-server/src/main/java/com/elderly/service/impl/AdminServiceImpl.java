package com.elderly.service.impl;

import com.elderly.common.dto.LoginDto;
import com.elderly.common.vo.JSONReturn;
import com.elderly.common.vo.LoginVo;
import com.elderly.entity.Admin;
import com.elderly.mapper.AdminMapper;
import com.elderly.service.AdminService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;


    @Override
    public JSONReturn login(LoginDto loginDto) {
        LoginVo loginVo = new LoginVo();
        Admin admin = adminMapper.selectByUsername(loginDto.getUsername());
        if (admin == null) {
            return JSONReturn.failed("账户不存在！");
        }
        if (!admin.getPassword().equals(loginDto.getPassword())) {
            return JSONReturn.failed("密码错误！");
        }
        BeanUtils.copyProperties(admin, loginVo);
        loginVo.setRole(0);
        return JSONReturn.success("登录成功！",loginVo);
    }

    @Override
    public Integer update(Admin admin) {
        admin.setUpdateTime(new Date());
        return adminMapper.updateByPrimaryKeySelective(admin);
    }

    @Override
    public Admin selectById(Integer id) {
        return adminMapper.selectByPrimaryKey(id);
    }
}
