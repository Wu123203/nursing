package com.elderly.common.vo;

import com.elderly.common.enums.ErrorCode;

/**
 * 统一JSON返回
 */
public class JSONReturn<T> {

    //返回状态码
    private Integer code;

    //响应消息
    private String msg;

    //返回数据
    private T data;

    //时间戳
    private Long timestamp;


    public int getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * 原始构造
     */
    public JSONReturn() {
        this.timestamp = System.currentTimeMillis();
    }

    /**
     * 带参构造
     * @param code
     * @param msg
     */
    public JSONReturn(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
        this.timestamp = System.currentTimeMillis();
    }


    /**
     * 带参构造
     * @param code
     * @param msg
     * @param data
     */
    public JSONReturn(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.timestamp = System.currentTimeMillis();
    }



    /**
     * 根据状态码和内容返回消息
     * @param code  状态码
     * @param message 消息
     * @return
     */
    public static JSONReturn build(Integer code, String message){
        return new JSONReturn(code,message);
    }



    /**
     * 根据状态码和内容返回消息
     * @param code  状态码
     * @param message 消息
     * @param data  消息主体
     * @return
     */
    public static <T> JSONReturn<T> build(Integer code, String message, T data){
        return new JSONReturn(code,message,data);
    }



    /**
     * 简单返回成功消息
     * @return
     */
    public static JSONReturn success(){
        return new JSONReturn(200,"操作成功！");
    }

    /**
     * 构建返回成功消息
     * @param message 消息
     * @return
     */
    public static JSONReturn success(String message){
        return new JSONReturn(200,message);
    }



    /**
     * 构建返回成功消息
     * @param body 数据
     * @return
     */
    public static <T> JSONReturn<T> success(T body){
        return new JSONReturn(200,"操作成功",body);
    }




    /**
     * 构建返回成功消息，带数据
     * @param message 消息
     * @param body 成功消息主体
     * @return
     */
    public static <T> JSONReturn<T> success(String message, T body){
        return new JSONReturn(200,message,body);
    }



    /**
     * 简单返回失败消息
     * @return
     */
    public static JSONReturn failed(){
        return new JSONReturn(500,"操作失败！");
    }


    /**
     * 构建返回失败消息
     * @param message 消息
     * @return
     */
    public static JSONReturn failed(String message){
        return new JSONReturn(500,message);
    }

    /**
     * 根据错误码枚举返回失败消息
     * @param errorCode 错误码枚举
     * @return
     */
    public static JSONReturn failed(ErrorCode errorCode){
        return new JSONReturn(errorCode.getCode(), errorCode.getMessage());
    }

    /**
     * 根据错误码枚举返回失败消息，可自定义消息
     * @param errorCode 错误码枚举
     * @param message 自定义消息
     * @return
     */
    public static JSONReturn failed(ErrorCode errorCode, String message){
        return new JSONReturn(errorCode.getCode(), message);
    }

    /**
     * 根据错误码返回失败消息
     * @param code 错误码
     * @param message 消息
     * @return
     */
    public static JSONReturn failed(Integer code, String message){
        return new JSONReturn(code, message);
    }


}
