package com.sxj.mapper;

import com.sxj.entity.UserBean;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {

    /* user表 */
    @Select("SELECT * FROM user WHERE uname = #{uname} AND upwd = #{upwd}")
    UserBean getInfo(@Param("uname") String uname, @Param("upwd") String upwd);

    @Insert("insert into user(uname,upwd)values(#{uname},#{upwd})")
    Integer saveInfo(@Param("uname") String uname, @Param("upwd") String upwd);

    //	插入不重复的用户数据
    @Insert("insert into user (uname, upwd) select #{uname}, #{upwd} from DUAL where not exists (select uname from user where uname=#{uname}); ")
    Integer saveNotRepeatInfo(@Param("uname") String uname, @Param("upwd") String upwd);

}
