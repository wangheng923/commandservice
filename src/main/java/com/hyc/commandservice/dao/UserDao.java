package com.hyc.commandservice.dao;

import java.util.List;

import com.github.pagehelper.Page;
import com.hyc.commandservice.entity.Role;
import com.hyc.commandservice.entity.User;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.ResultMap;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

@Mapper
public interface UserDao {
    @Select("select * from user where account=#{account}")
    public User findUserByAccount(@Param("account") String account);

    @Update("update user set password=md5(concat(#{account},':commandService:',#{newpass})) where account=#{account}")
    public int updatePassword(@Param("newpass") String newpass, @Param("account") String account);

    @Select("select com.hyc.commandservice.service.UserService.* from role com.hyc.commandservice.service.UserService,user_role c,user b where b.account=c.account and c.roleCode=com.hyc.commandservice.service.UserService.roleCode and b.account=#{account}")
    public List<Role> getUserRoles(@Param("account") String account);

    @Select("select authorityCode from role_authority where roleCode=#{roleCode}")
    public List<String> getUserAuthority(@Param("roleCode") int roleCode);

    @Insert("insert into user(account,name,password,unitCode,`status`,createTime,creator) "
            + "values(#{account},#{name},md5(concat(#{account},':interviewService:',#{password})),#{unitCode},#{status},#{createTime},#{creator})")
    public int addUser(User user);

    @Update({"<script>",
            "update user ",
            "<set>",
            "<if test=\"name != null and name != ''\">name= #{name},</if>",
            "<if test='status != null'>status=#{status},</if>",
            "</set>",
            " where account=#{account}",
            "</script>"})
    public int updateUser(User user);

    @Delete("delete from user_role where account=#{account}")
    public int clearRole(@Param("account") String account);

    @Insert("insert into user_role values(#{roleCode},#{account})")
    public int insertUserRole(@Param("account") String account, @Param("roleCode") int roleCode);

    @ResultMap("common.user")
    @Select({"<script>",
            "select com.hyc.commandservice.service.UserService.*,b.roleCode,r.roleName from user com.hyc.commandservice.service.UserService left join user_role b on com.hyc.commandservice.service.UserService.account=b.account left join role r on b.roleCode=r.roleCode",
            "<where>",
            "<if test=\"conditions.account != null\">com.hyc.commandservice.service.UserService.account = #{conditions.account} </if>",
            "<if test=\"conditions.name != null\">and com.hyc.commandservice.service.UserService.name like concat(#{conditions.name},'%')</if>",
            "<if test='conditions.cardType != null'>and com.hyc.commandservice.service.UserService.cardType= #{conditions.cardType}</if>",
            "<if test='conditions.unitCode != null'>and com.hyc.commandservice.service.UserService.unitCode= #{conditions.unitCode}</if>",
            "<if test='conditions.phoneNumber != null'>and com.hyc.commandservice.service.UserService.phoneNumber = #{conditions.phoneNumber}</if>",
            "<if test='conditions.role != null'>and b.roleCode = #{conditions.role}</if>",
            "</where>",
            "</script>"})
    public List<User> queryUser(Page page);

    @Update("update user set password=md5(concat(#{account},':commandService:',#{account})) where account=#{account}")
    public void resetPassowrd(@Param("account") String account);

    @Update("update user set `status`=1 where account=#{account}")
    public void unlockUser(@Param("account") String account);

}
