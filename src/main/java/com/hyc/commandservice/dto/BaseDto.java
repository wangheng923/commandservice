package com.hyc.commandservice.dto;

/**
 * Description: 基础数据传输对象
 *
 * @author wangheng2
 * @date 2018年3月23日
 */
public class BaseDto {

    private String result;

    private String msg;

    private String resultCode;

    private Object data;

    public BaseDto() {
    }

    public BaseDto(String result, String msg) {
        this.result = result;
        this.msg = msg;
    }

    public BaseDto(String result, String msg, Object data) {
        this.result = result;
        this.msg = msg;
        this.data = data;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

}
