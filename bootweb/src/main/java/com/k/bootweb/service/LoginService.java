package com.k.bootweb.service;

import com.k.bootweb.pojo.dao.LoginTicket;
import com.k.bootweb.pojo.dao.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;

@Service
public interface LoginService {

    User findUserById(int id);

    User getCache(int userId);

    User initCache(int userId);

    void clearCache(int userId);

    int updateUserInfo(User user);

    void logout(String ticket);

    Map<String, String> register(User user);

    Map<String, Object> login(String email, String password,int expiredSeconds);

    LoginTicket findLoginTicket(String ticket);

    Collection<? extends GrantedAuthority> getAuthorities(int userId);
}
