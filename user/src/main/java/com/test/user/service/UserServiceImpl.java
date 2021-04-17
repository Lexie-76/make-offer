package com.test.user.service;

import com.test.thrift.user.UserInfo;
import com.test.thrift.user.UserService;
import com.test.user.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService.Iface {

    @Autowired
    private UserMapper userMapper;
    @Override
    public void registerUser(UserInfo userInfo) {
        userMapper.registerUser(userInfo);
    }

    @Override
    public UserInfo getUserById(int id) {
        return userMapper.getUserById(id);
    }

    @Override
    public UserInfo getUserByName(String username) {
        return userMapper.getUserByName(username);
    }
}
