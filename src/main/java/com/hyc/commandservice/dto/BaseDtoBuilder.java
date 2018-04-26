package com.hyc.commandservice.dto;

/**
 * Description: 基础数据传输对象构造器
 *
 * @author wangheng2
 * @date 2018年3月23日
 */
public class BaseDtoBuilder {

    private BaseDto baseDto = new BaseDto();

    public BaseDtoBuilder result(String result) {
        baseDto.setResult(result);
        return this;
    }

    public BaseDtoBuilder message(String message) {
        baseDto.setMsg(message);
        return this;
    }

    public BaseDtoBuilder data(Object data) {
        baseDto.setData(data);
        return this;
    }

    public BaseDtoBuilder error(String msg) {
        baseDto.setResult("error");
        baseDto.setMsg(msg);
        return this;
    }

    public BaseDtoBuilder success(Object data) {
        success(data, null);
        return this;
    }

    public BaseDtoBuilder success(String msg) {
        success(null, msg);
        return this;
    }

    public BaseDtoBuilder success(Object data, String msg) {
        baseDto.setResult("success");
        baseDto.setMsg(msg);
        baseDto.setData(data);
        return this;
    }

    public BaseDto build() {
        return baseDto;
    }
}