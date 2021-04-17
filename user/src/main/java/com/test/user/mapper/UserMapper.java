package com.test.user.mapper;

import com.test.thrift.user.UserInfo;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Component
public interface UserMapper {

    // 用户注册
    @Insert("insert into pe_user(username, password, email) " +
            "values (#{u.username}, #{u.password}, #{u.email})")
    void registerUser(@Param("u") UserInfo userInfo);

    // 根据 id 找用户
    @Select("select id, username, password, email " +
            " from pe_user where id = #{id}")
    UserInfo getUserById(@Param("id") int id);

    // 根据 username 找用户
    @Select("select id, username, password,  email " +
            " from pe_user where username = #{username}")
    UserInfo getUserByName(@Param("username")String username);
}
