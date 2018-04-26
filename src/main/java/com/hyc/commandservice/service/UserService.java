package com.hyc.commandservice.service;

import org.springframework.security.core.userdetails.UserDetails;

/**
 * @Description:
 * @Author: wangheng2
 * @Date: 2018/4/24
 * @Modified By:
 */
public interface UserService {

    /**
     * @Description: springsecurity 用户认证服务
     * @Author: wangheng2
     * @Date: 2018/4/24
     * @param: username
     * @Modified By:
     */
    public UserDetails loadUserByUsername(String username);
}
