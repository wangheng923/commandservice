package com.hyc.commandservice.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hyc.commandservice.utils.CustomDateSerializer;

import java.util.Date;

public class Device {
    private int id;
    private String serialNumber;
    private String devcieName;
    private String unitCode;
    private String owner;
    private String x;
    private String y;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createTime;
    private String creator;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public String getDevcieName() {
        return devcieName;
    }

    public void setDevcieName(String devcieName) {
        this.devcieName = devcieName;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getX() {
        return x;
    }

    public void setX(String x) {
        this.x = x;
    }

    public String getY() {
        return y;
    }

    public void setY(String y) {
        this.y = y;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
