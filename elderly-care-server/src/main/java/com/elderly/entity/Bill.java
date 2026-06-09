package com.elderly.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.util.Date;

/**
 * 缴费账单表
 */
public class Bill implements Serializable {
    /**
     * 缴费ID
     */
    private Integer id;

    /**
     * 入住ID
     */
    private Integer liveId;

    /**
     * 月份
     */
    private String month;

    /**
     * 床位费
     */
    private Double bedCost;

    /**
     * 照护费
     */
    private Double careCost;

    /**
     * 餐饮费
     */
    private Double foodCost;

    /**
     * 总费用
     */
    private Double totalCost;

    /**
     * 备注
     */
    private String remark;

    /**
     * 状态：0待缴费，1已缴费
     */
    private Integer status;

    /**
     * 添加时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新时间
     */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
    private Date updateTime;

    /**
     * 房间ID
     */
    private Integer roomId;

    /**
     * 房间名称
     */
    private String roomName;

    /**
     * 床位编号
     */
    private String bedNo;

    /**
     * 老人姓名
     */
    private String name;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getLiveId() {
        return liveId;
    }

    public void setLiveId(Integer liveId) {
        this.liveId = liveId;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month == null ? null : month.trim();
    }

    public Double getBedCost() {
        return bedCost;
    }

    public void setBedCost(Double bedCost) {
        this.bedCost = bedCost;
    }

    public Double getCareCost() {
        return careCost;
    }

    public void setCareCost(Double careCost) {
        this.careCost = careCost;
    }

    public Double getFoodCost() {
        return foodCost;
    }

    public void setFoodCost(Double foodCost) {
        this.foodCost = foodCost;
    }

    public Double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(Double totalCost) {
        this.totalCost = totalCost;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getRoomId() {
        return roomId;
    }

    public void setRoomId(Integer roomId) {
        this.roomId = roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getBedNo() {
        return bedNo;
    }

    public void setBedNo(String bedNo) {
        this.bedNo = bedNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}