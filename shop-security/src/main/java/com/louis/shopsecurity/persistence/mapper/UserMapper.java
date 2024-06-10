package com.louis.shopsecurity.persistence.mapper;

import com.louis.shopsecurity.persistence.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    @Insert("insert into users(username, password, mobile, roles)" +
            " values (#{username}, #{password}, #{mobile}, #{roles})")
    //在插入資料後，獲取自動增加的主鍵值
    @Options(useGeneratedKeys=true, keyProperty="id")
    int saveUser(User user);

    @Select("select * from users where username = #{username}")
    User findByUsername(String username);

    @Select("select * from users where mobile = #{mobile}")
    User findByMobile(String mobile);
}