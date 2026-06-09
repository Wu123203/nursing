package com.elderly.controller;

import com.elderly.common.config.RedisConstants;
import com.elderly.common.dto.LoginDto;
import com.elderly.common.vo.CaptchaVo;
import com.elderly.common.vo.CountVo;
import com.elderly.common.vo.JSONReturn;
import com.elderly.service.UserService;
import com.elderly.utils.CodeUtil;
import com.elderly.utils.RedisUtil;
import jakarta.servlet.http.HttpSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Base64Utils;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.UUID;

/**
 * 首页
 */
@RestController
public class HomeController {

    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private RedisUtil redisUtil;

    /**
     * 登录
     * @param loginDto
     * @param session
     * @return
     */
    @PostMapping(value = "/login")
    public JSONReturn<?> login(@Validated @RequestBody LoginDto loginDto, HttpSession session){
        return userService.login(loginDto, session);
    }

    /**
     * 统计
     * @return
     */
    @GetMapping(value = "/count")
    public JSONReturn count(){
        CountVo vo = userService.count();
        return JSONReturn.success(vo);
    }

    /**
     * 获取验证码
     *
     * @param l 验证码长度
     * @param w 宽度
     * @param h 高度
     */
    @GetMapping("/captcha/{l}/{w}/{h}")
    public JSONReturn getCaptcha(@PathVariable(value = "l") Integer l,
                                 @PathVariable(value = "w") Integer w,
                                 @PathVariable(value = "h") Integer h) {

        CodeUtil codeUtil = new CodeUtil(l, w, h);
        String generatorVCode = codeUtil.generatorVCode();
        BufferedImage codeImage = codeUtil.generatorRotateVCodeImage(generatorVCode, true);
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();

        // 生成验证码的唯一 key
        String captchaKey = UUID.randomUUID().toString().replace("-", "");

        try {
            ImageIO.write(codeImage, "gif", os);
            String base64Img = Base64Utils.encodeToString(os.toByteArray());

            // 将验证码存入 Redis
            String redisKey = RedisConstants.CAPTCHA_KEY + captchaKey;
            redisUtil.set(redisKey, generatorVCode, RedisConstants.CAPTCHA_TTL);
            logger.debug("验证码已存入 Redis: key={}, code={}", redisKey, generatorVCode);

            // 构建返回对象
            CaptchaVo captchaVo = new CaptchaVo();
            captchaVo.setImage(base64Img);
            captchaVo.setCaptchaKey(captchaKey);

            return JSONReturn.success("验证码生成成功！", captchaVo);
        } catch (IOException e) {
            logger.error("验证码生成失败", e);
            return JSONReturn.failed("验证码生成失败！");
        }
    }
}
