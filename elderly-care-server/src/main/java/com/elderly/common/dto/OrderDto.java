package com.elderly.common.dto;


import java.util.List;

/**
 * 创建订单
 */
public class OrderDto {

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 收货人姓名
     */
    private String nickname;


    /**
     * 收货人电话
     */
    private String telephone;


    /**
     * 配送地址
     */
    private String address;


    /**
     * 订单备注
     */
    private String remark;


    /**
     * 订单商品：购物车ID集合
     */
    private List<Integer> itemList;


    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }


    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public List<Integer> getItemList() {
        return itemList;
    }

    public void setItemList(List<Integer> itemList) {
        this.itemList = itemList;
    }
}
