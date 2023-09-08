package com.sxj.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.sxj.entity.UserBean;
import com.sxj.mapper.UserMapper;

@Service
public class UserService {
    @Resource
    private UserMapper userMapper;

    public UserBean LoginIn(String uname, String upwd) {
        return userMapper.getInfo(uname, upwd);
    }

    public Integer Insert(String uname, String upwd) {
//		Integer saveInfo = userMapper.saveInfo(uname, upwd);
        /* 插入不重复的用户数据 */
        Integer saveInfo = userMapper.saveNotRepeatInfo(uname, upwd);
        return saveInfo;
    }
}
