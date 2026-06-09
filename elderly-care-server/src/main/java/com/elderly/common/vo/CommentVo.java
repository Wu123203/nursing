package com.elderly.common.vo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;


/**
 * 评论回复
 */
public class CommentVo {


    /**
     * 评论ID
     */
    private Integer id;

    /**
     * 评论内容
     */
    private String content;


    /**
     * 评论者头像
     */
    private String avatar;

    /**
     * 评论者姓名
     */
    private String nickname;

    /**
     * 评论类型：0评论，1回复评论
     */
    private Integer type;

    /**
     * 如果是回复，则被回复人姓名
     */
    private String beReplyName;

    /**
     * 提交时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;


    /**
     * 商品ID
     */
    private Integer goodsId;




    /**
     * 商品名称
     */
    private String goodsName;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getBeReplyName() {
        return beReplyName;
    }

    public void setBeReplyName(String beReplyName) {
        this.beReplyName = beReplyName;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
