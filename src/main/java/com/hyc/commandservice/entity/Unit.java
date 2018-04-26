package com.hyc.commandservice.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hyc.commandservice.utils.CustomDateSerializer;

import java.util.Date;

public class Unit {
    private int id;
    private String unitCode;
    private String unitName;
    private int unitLevel;
    private String parent;
    private int status;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createTime;
    private int fixed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getUnitName() {
        return unitName;
    }

    public void setUnitName(String unitName) {
        this.unitName = unitName;
    }

    public int getUnitLevel() {
        return unitLevel;
    }

    public void setUnitLevel(int unitLevel) {
        this.unitLevel = unitLevel;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getFixed() {
        return fixed;
    }

    public void setFixed(int fixed) {
        this.fixed = fixed;
    }
}
