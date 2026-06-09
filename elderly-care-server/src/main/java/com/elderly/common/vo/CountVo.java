package com.elderly.common.vo;


import java.util.List;

/**
 * 统计
 */
public class  CountVo {

    /**
     * 总护工数
     */
    private Integer totalEmployeeNum;

    /**
     * 总入住人数
     */
    private Integer totalLiveNum;


    /**
     * 总房间数
     */
    private Integer totalRoomNum;

    /**
     * 总床位数
     */
    private Integer totalBedNum;

    /**
     * 床位统计
     */
    private List<ChartPieVo> bedList;

    /**
     * 老人统计
     */
    private List<ChartPieVo> liveList;

    /**
     * 过去7天
     */
    private String[] dayList;

    /**
     * 入住人数统计
     */
    private List<Integer> liveNumList;


    public Integer getTotalEmployeeNum() {
        return totalEmployeeNum;
    }

    public void setTotalEmployeeNum(Integer totalEmployeeNum) {
        this.totalEmployeeNum = totalEmployeeNum;
    }

    public Integer getTotalLiveNum() {
        return totalLiveNum;
    }

    public void setTotalLiveNum(Integer totalLiveNum) {
        this.totalLiveNum = totalLiveNum;
    }

    public Integer getTotalRoomNum() {
        return totalRoomNum;
    }

    public void setTotalRoomNum(Integer totalRoomNum) {
        this.totalRoomNum = totalRoomNum;
    }

    public Integer getTotalBedNum() {
        return totalBedNum;
    }

    public void setTotalBedNum(Integer totalBedNum) {
        this.totalBedNum = totalBedNum;
    }

    public List<ChartPieVo> getBedList() {
        return bedList;
    }

    public void setBedList(List<ChartPieVo> bedList) {
        this.bedList = bedList;
    }

    public List<ChartPieVo> getLiveList() {
        return liveList;
    }

    public void setLiveList(List<ChartPieVo> liveList) {
        this.liveList = liveList;
    }

    public String[] getDayList() {
        return dayList;
    }

    public void setDayList(String[] dayList) {
        this.dayList = dayList;
    }

    public List<Integer> getLiveNumList() {
        return liveNumList;
    }

    public void setLiveNumList(List<Integer> liveNumList) {
        this.liveNumList = liveNumList;
    }
}
