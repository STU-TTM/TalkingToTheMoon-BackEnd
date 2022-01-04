package com.k.bootweb.utils;

import com.k.bootweb.pojo.dao.User;
import org.springframework.stereotype.Component;

/**
 * @Description: 根据用户类型，获得相应权限
 * @return: java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>
 **/
@Component
public class HostHolder {
    private ThreadLocal<User> users = new ThreadLocal<>();

    public void setUser(User user) {
        users.set(user);
    }

    public User getUser() {
        return users.get();
    }

    public void clear() {
        users.remove();
    }
}
