package com.elderly.common.vo;

/**
 * 饼状图统计
 */
public class ChartPieVo {

    /**
     * 名称
     */
    private String name;

    /**
     * 名称下数量
     */
    private Integer value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
