package com.elderly.service.impl;

import com.elderly.common.config.RedisConstants;
import com.elderly.common.dto.LoginDto;
import com.elderly.common.vo.*;
import com.elderly.entity.*;
import com.elderly.mapper.*;
import com.elderly.service.UserService;
import com.elderly.utils.DateUtils;
import com.elderly.utils.JwtUtil;
import com.elderly.utils.RedisUtil;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户服务实现类
 */
@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private EmployeeMapper employeeMapper;

    @Autowired
    private LiveMapper liveMapper;

    @Autowired
    private RoomMapper roomMapper;

    @Autowired
    private BedMapper bedMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private NoticeMapper noticeMapper;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private RedisUtil redisUtil;

    @Override
    public JSONReturn login(LoginDto loginDto, HttpSession session) {
        String username = loginDto.getUsername();
        String captchaKey = loginDto.getCaptchaKey();
        String captcha = loginDto.getCaptcha();

        logger.info("用户登录尝试: username={}, role={}", username, loginDto.getRole());

        // 验证验证码（从 Redis 获取）
        if (captchaKey == null || captchaKey.isEmpty()) {
            logger.warn("用户登录失败: username={}, reason=未提供验证码", username);
            return JSONReturn.failed("请先获取验证码！");
        }
        String redisCaptchaKey = RedisConstants.CAPTCHA_KEY + captchaKey;
        Object captchaObj = redisUtil.get(redisCaptchaKey);
        if (captchaObj == null) {
            logger.warn("用户登录失败: username={}, reason=验证码已过期", username);
            return JSONReturn.failed("验证码已过期，请刷新验证码！");
        }
        String realCaptcha = captchaObj.toString();
        if (!captcha.equalsIgnoreCase(realCaptcha)) {
            logger.warn("用户登录失败: username={}, reason=验证码错误", username);
            return JSONReturn.failed("验证码错误！");
        }

        // 验证通过后删除验证码（一次性使用）
        redisUtil.delete(redisCaptchaKey);

        // 登录失败次数限制
        String loginFailKey = RedisConstants.LOGIN_FAIL_KEY + username;
        Object failCountObj = redisUtil.get(loginFailKey);
        if (failCountObj != null) {
            int failCount = Integer.parseInt(failCountObj.toString());
            if (failCount >= RedisConstants.MAX_LOGIN_FAIL) {
                logger.warn("用户登录失败: username={}, reason=登录失败次数过多", username);
                return JSONReturn.failed("登录失败次数过多，请稍后再试！");
            }
        }

        LoginVo loginVo = new LoginVo();
        Integer role = loginDto.getRole();
        String userId = "";
        String roleStr = "";
        boolean loginSuccess = false;

        try {
            if (role == 0) {
                Admin admin = adminMapper.selectByUsername(username);
                if (admin == null) {
                    logger.warn("用户登录失败: username={}, reason=账户不存在, role=ADMIN", username);
                    return JSONReturn.failed("账户不存在！");
                }
                if (!admin.getPassword().equals(loginDto.getPassword())) {
                    incrementLoginFail(loginFailKey);
                    logger.warn("用户登录失败: username={}, reason=密码错误, role=ADMIN", username);
                    return JSONReturn.failed("密码错误！");
                }
                BeanUtils.copyProperties(admin, loginVo);
                userId = admin.getId().toString();
                roleStr = "ADMIN";
                loginSuccess = true;
            } else if (role == 1) {
                Employee employee = employeeMapper.selectByUsername(username);
                if (employee == null) {
                    logger.warn("用户登录失败: username={}, reason=账户不存在, role=EMPLOYEE", username);
                    return JSONReturn.failed("账户不存在！");
                }
                if (!employee.getPassword().equals(loginDto.getPassword())) {
                    incrementLoginFail(loginFailKey);
                    logger.warn("用户登录失败: username={}, reason=密码错误, role=EMPLOYEE", username);
                    return JSONReturn.failed("密码错误！");
                }
                if (employee.getStatus() == 1) {
                    logger.warn("用户登录失败: username={}, reason=账号已被冻结, role=EMPLOYEE", username);
                    return JSONReturn.failed("账号已被冻结，请联系管理员！");
                }
                BeanUtils.copyProperties(employee, loginVo);
                userId = employee.getId().toString();
                roleStr = "EMPLOYEE";
                loginSuccess = true;
            } else {
                User user = userMapper.selectByUsername(username);
                if (user == null) {
                    logger.warn("用户登录失败: username={}, reason=账户不存在, role=USER", username);
                    return JSONReturn.failed("账户不存在！");
                }
                if (!user.getPassword().equals(loginDto.getPassword())) {
                    incrementLoginFail(loginFailKey);
                    logger.warn("用户登录失败: username={}, reason=密码错误, role=USER", username);
                    return JSONReturn.failed("密码错误！");
                }
                if (user.getStatus() == 1) {
                    logger.warn("用户登录失败: username={}, reason=账号已被冻结, role=USER", username);
                    return JSONReturn.failed("账号已被冻结，请联系管理员！");
                }
                BeanUtils.copyProperties(user, loginVo);
                userId = user.getId().toString();
                roleStr = "USER";
                loginSuccess = true;
            }
        } finally {
            if (loginSuccess) {
                // 登录成功，清除失败计数
                redisUtil.delete(loginFailKey);
            }
        }

        loginVo.setRole(role);

        // 生成 JWT Token
        String token = jwtUtil.generateToken(userId, username, roleStr);
        loginVo.setToken(token);

        logger.info("用户登录成功: username={}, role={}, userId={}", username, roleStr, userId);
        return JSONReturn.success("登录成功！", loginVo);
    }

    /**
     * 增加登录失败次数
     */
    private void incrementLoginFail(String loginFailKey) {
        Object failCountObj = redisUtil.get(loginFailKey);
        if (failCountObj == null) {
            redisUtil.set(loginFailKey, 1, RedisConstants.LOGIN_FAIL_TTL);
        } else {
            redisUtil.increment(loginFailKey);
        }
    }

    @Override
    public PageVo page(Map map) {
        List<User> list = userMapper.findList(map);
        Integer total = userMapper.findTotal(map);
        return new PageVo(total,list);
    }

    @Override
    public JSONReturn save(User user) {
        //用户名查询
        User exist = userMapper.selectByUsername(user.getUsername());
        if(exist != null){
            return JSONReturn.failed("用户名已存在，请重新输入！");
        }
        Date date = new Date();
        user.setCreateTime(date);
        user.setUpdateTime(date);
        user.setStatus(0);
        user.setAmount(0.0);
        //初始化性别
        user.setSex(0);
        userMapper.insert(user);
        return JSONReturn.success("注册成功！");
    }

    @Override
    public JSONReturn update(User user) {

        User u = userMapper.selectByPrimaryKey(user.getId());
        if(user.getUsername() != null && !u.getUsername().equals(user.getUsername())){
            User exist = userMapper.selectByUsername(user.getUsername());
            if(exist != null){
                return JSONReturn.failed("用户名已存在，请重新输入！");
            }
        }
        user.setUpdateTime(new Date());
        userMapper.updateByPrimaryKeySelective(user);
        return JSONReturn.success("更新成功！");
    }

    @Override
    public Integer del(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public User selectById(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public List<User> findAll() {
        return userMapper.findAll();
    }


    @Override
    public CountVo count() {
        // 先从缓存获取统计数据
        String cacheKey = RedisConstants.CACHE_COUNT_KEY;
        Object cacheObj = redisUtil.get(cacheKey);
        if (cacheObj != null) {
            logger.debug("统计数据从缓存获取");
            return (CountVo) cacheObj;
        }

        // 缓存未命中，查询数据库
        logger.debug("统计数据从数据库获取");
        CountVo countVo = new CountVo();
        //统计总护工数
        Integer totalEmployeeNum = employeeMapper.countTotal();
        //统计入住人数
        Integer totalLiveNum = liveMapper.countTotal();
        //统计总房间数
        Integer totalRoomNum = roomMapper.countTotal();
        //统计总床位数
        Integer totalBedNum = bedMapper.countTotal();
        //统计床位使用
        List<ChartPieVo> bedList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Integer totalNum = bedMapper.countByStatus(i);
            ChartPieVo bedVo = new ChartPieVo();
            if(i == 0){
                bedVo.setName("空闲");
            }else if(i == 1){
                bedVo.setName("占用");
            }
            bedVo.setValue(totalNum == null ? 0 : totalNum);
            bedList.add(bedVo);
        }
        //统计老人性别
        List<ChartPieVo> liveList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            Integer totalNum = liveMapper.countBySex(i);
            ChartPieVo liveVo = new ChartPieVo();
            if(i == 0){
                liveVo.setName("男");
            }else if(i == 1){
                liveVo.setName("女");
            }
            liveVo.setValue(totalNum == null ? 0 : totalNum);
            liveList.add(liveVo);
        }
        //统计过去7天入住人数
        String[] days = DateUtils.getPast7Days();
        //统计入住人数
        List<Integer> liveNumList =  new ArrayList<>();
        for(String day : days){
            Integer liveNum = liveMapper.countByDate(day);
            liveNumList.add(liveNum == null ? 0 : liveNum);
        }
        countVo.setTotalEmployeeNum(totalEmployeeNum);
        countVo.setTotalLiveNum(totalLiveNum);
        countVo.setTotalRoomNum(totalRoomNum);
        countVo.setTotalBedNum(totalBedNum);
        countVo.setLiveList(liveList);
        countVo.setBedList(bedList);
        countVo.setDayList(days);
        countVo.setLiveNumList(liveNumList);

        // 写入缓存
        redisUtil.set(cacheKey, countVo, RedisConstants.CACHE_COUNT_TTL);

        return countVo;
    }

}
