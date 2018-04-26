package com.hyc.commandservice.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hyc.commandservice.utils.CustomDateSerializer;

import java.util.Date;

public class Group {
    private int groupId;
    private String groupName;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createTime;
    private String creator;
    private String description;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
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
