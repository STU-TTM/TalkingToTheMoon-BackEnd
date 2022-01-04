package com.k.bootweb.service;

import com.k.bootweb.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper userMapper;

    @Override
    public int updatePsystatus(int uid, double psystatus) {
        return userMapper.updatePsystatus(uid, psystatus);
    }
}
