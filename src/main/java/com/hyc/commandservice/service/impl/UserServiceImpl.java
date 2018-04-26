package com.hyc.commandservice.service.impl;


import com.hyc.commandservice.dao.UserDao;
import com.hyc.commandservice.entity.Role;
import com.hyc.commandservice.entity.User;
import com.hyc.commandservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

@Component
public class UserServiceImpl implements UserService, UserDetailsService {
    //	@Autowired
//	private DigestAuthenticationFilter filter;
    @Autowired
    private UserDao userdao;
//	@Autowired
//	private LoginAttempService service;
//	@Autowired
//	private UnitDao unitdao;

    @Autowired
    private EhCacheCacheManager ehcacheManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userdao.findUserByAccount(username);
        if (user == null) return user;
        user.setRoles(userdao.getUserRoles(username));
        Set<String> authorities = new HashSet<String>();
        if (user.getRoles() != null) {
            for (Role role : user.getRoles()) {
                authorities.addAll(userdao.getUserAuthority(role.getRoleCode()));
            }
        }
        ArrayList<String> privileges = new ArrayList<String>();
        privileges.addAll(authorities);
        user.setPrivileges(privileges);
//		if(service.isLocked(user.getAccount())){
//			user.setLocked();
//		}

        return user;
    }
//	@UserLogAnotation(operateType="'user_password_change'",
//			  description="#logoperatorName+#logoperateName")
//	public void changePassword(String oldpass,String newpass,String current){
//		User u=userdao.findUserByAccount(current);
//		String oldcheck=DigestUtils.md5Hex(current+":interviewService:"+oldpass);
//		if(!oldcheck.equalsIgnoreCase(u.getPassword())){
//			throw new BadRequestException("原密码不正确");
//		}
//		userdao.updatePassword(newpass,current);
//		userCache.removeUserFromCache(current);
//	}
//	@UserLogAnotation(operateType="'user_add'",
//			  target="#user.account",
//			  parameter="#user",
//			  description="#logoperatorName+' '+#logoperateName+' '+#user.account+':'+#user.name")
//	@PreAuthorize("hasPermission(#user.unitCode,'unit-notnew','user_add') and hasPermission(#user.unitCode,'unit','user_add')")
//	public int addUser(User user){
//		User u=userdao.findUserByAccount(user.getAccount());
//		if(u!=null)throw new ConflictException("账号已存在");
//		user.setPassword(user.getAccount());
//
//		int result=userdao.addUser(user);
//		if(user.getRoles()!=null){
//			for(Role role:user.getRoles()){
//				userdao.insertUserRole(user.getAccount(), role.getRoleCode());
//			}
//		}
//		return result;
//	}
//	@UserLogAnotation(operateType="'user_update'",
//			  target="#user.account",
//			  parameter="#user",
//			  description="#logoperatorName+' '+#logoperateName+' '+#user.account+':'+#user.name")
//	@PreAuthorize("hasPermission(#user.account,'user','user_modify')")
//	public void updateUser(User user){
//		userdao.updateUser(user);
//		if(user.getStatus() == 2) {
//			unitdao.resetCustomerPrincipalCode(user.getAccount());
//		}
//		if(user.getRoles()!=null){
//			userdao.clearRole(user.getAccount());
//			for(Role role:user.getRoles()){
//				userdao.insertUserRole(user.getAccount(), role.getRoleCode());
//			}
//		}
//	}
//	@PreAuthorize("hasPermission(#page,'page','user_view')")
//	public Page queryUser(Page page){
//		List<User> user=userdao.queryUser(page);
//		PageInfo pinfo=new PageInfo(user);
//		page.setTotalCount(pinfo.getTotal());
//		page.setPageCount(pinfo.getPages());
//		page.setResults(pinfo.getList());
//		return page;
//	}
//	@UserLogAnotation(operateType="'user_password_reset'",
//			  target="#account",
//			  parameter="#account",
//			  description="#logoperatorName+#logoperateName+#account")
//	@PreAuthorize("hasPermission(#account,'user','user_modify')")
//	public void resetPassowrd(String account){
//		userdao.resetPassowrd(account);
//		userCache.removeUserFromCache(account);
//	}
//	public void unlockUser(String account){
//		service.successd(account);
//		userdao.unlockUser(account);
//	}
//	public UserCache getUserCache() {
//		return userCache;
//	}
//	public void setUserCache(UserCache userCache) {
//		this.userCache = userCache;
//	}


}
