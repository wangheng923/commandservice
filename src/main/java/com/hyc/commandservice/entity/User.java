package com.hyc.commandservice.entity;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hyc.commandservice.utils.CustomDateSerializer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class User implements UserDetails {
    private int id;
    private String account;
    private String name;
    private String password;
    private String unitCode;
    private int status;
    @JsonSerialize(using = CustomDateSerializer.class)
    private Date createTime;
    private String creator;
    private int fixed;
    private List<Role> roles = new ArrayList<Role>();
    private List<String> privileges = new ArrayList<String>();

    private List<SimpleGrantedAuthority> authlist = null;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (authlist == null || authlist.size() != this.privileges.size()) {
            authlist = new ArrayList<SimpleGrantedAuthority>();
            for (String auth : privileges) {
                authlist.add(new SimpleGrantedAuthority(auth));
            }
        }
        return authlist;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return account;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return status == 1;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUnitCode() {
        return unitCode;
    }

    public void setUnitCode(String unitCode) {
        this.unitCode = unitCode;
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

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getFixed() {
        return fixed;
    }

    public void setFixed(int fixed) {
        this.fixed = fixed;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public List<String> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<String> privileges) {
        this.privileges = privileges;
    }

    public List<SimpleGrantedAuthority> getAuthlist() {
        return authlist;
    }

    public void setAuthlist(List<SimpleGrantedAuthority> authlist) {
        this.authlist = authlist;
    }
}
