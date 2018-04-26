package com.hyc.commandservice.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hyc.commandservice.utils.CustomDateSerializer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Role {
    private int roleCode;
    private String roleName;
    private int roleLevel;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createTime;
    private String creator;
    private String description;
    private int fixed;
    private List<String> privileges = new ArrayList<String>();

    public int getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(int roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public int getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(int roleLevel) {
        this.roleLevel = roleLevel;
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

    public int getFixed() {
        return fixed;
    }

    public void setFixed(int fixed) {
        this.fixed = fixed;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }
}
