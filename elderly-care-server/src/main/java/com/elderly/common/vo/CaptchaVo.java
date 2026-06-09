package com.elderly.common.vo;

/**
 * 验证码返回对象
 */
public class CaptchaVo {

    /**
     * 验证码图片 Base64
     */
    private String image;

    /**
     * 验证码 Key（登录时用于从 Redis 获取验证码）
     */
    private String captchaKey;

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCaptchaKey() {
        return captchaKey;
    }

    public void setCaptchaKey(String captchaKey) {
        this.captchaKey = captchaKey;
    }
}
