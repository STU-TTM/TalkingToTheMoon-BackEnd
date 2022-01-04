package com.k.bootweb.service;

import com.k.bootweb.mapper.UserMapper;
import com.k.bootweb.pojo.dao.LoginTicket;
import com.k.bootweb.pojo.dao.User;
import com.k.bootweb.utils.RedisKeyUtil;

import com.k.bootweb.utils.constant.CommunityConstant;
import com.k.bootweb.utils.constant.TaleUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.concurrent.TimeUnit;


@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public User findUserById(int id) {
        User user = getCache(id);
        if (user == null) {
            user = initCache(id);
        }
        return user;
    }

    //1.优先从缓存中取值
    @Override
    public User getCache(int userId) {
        String userKey = RedisKeyUtil.getUserKey(userId);
        return (User) redisTemplate.opsForValue().get(userKey);
    }

    //2.缓存取不到，从数据库中查，再存入缓存(初始化缓存)
    @Override
    public User initCache(int userId) {
        User user = userMapper.selectById(userId);
        String userKey = RedisKeyUtil.getUserKey(userId);
        //过期时间1h，3600s
        redisTemplate.opsForValue().set(userKey, user, 3600, TimeUnit.SECONDS);
        return user;
    }

    //3.更新用户信息，清除缓存数据
    @Override
    public void clearCache(int userId) {
        String userKey = RedisKeyUtil.getUserKey(userId);
        redisTemplate.delete(userKey);
    }

    /**
     * @Description: 更新用户
     * @param user
     * @return: Map<String, Object>
     **/
    @Override
    public int updateUserInfo(User user) {
//        if (null == user.getUid())
//            throw BusinessException.withErrorCode("用户编号不可能为空");
        return userMapper.updateUserInfo(user);
    }

    /**
     * @Description: 注册
     * @param user
     * @return: Map<String, String>
     **/
    @Override
    public Map<String, String> register(User user) {
        Map<String, String> map = new HashMap<>();

        // 空值处理
        if (user == null) {
            throw new IllegalArgumentException("参数不能为空!");
        }
//        if (StringUtils.isBlank(user.getUsername())) {
//            map.put("usernameMsg", "用户名不能为空!");
//            return map;
//        }
        if (StringUtils.isBlank(user.getEmail())) {
            map.put("emailMsg", "邮箱不能为空!");
            return map;
        }
        if (StringUtils.isBlank(user.getPassword())) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }

        // 验证账号
        User u;
//        u= userMapper.selectByName(user.getUsername());
//        if (u != null) {
//            map.put("usernameMsg", "该账号已存在!");
//            return map;
//        }

        // 验证邮箱
        u = userMapper.selectByEmail(user.getEmail());
        if (u != null) {
            map.put("emailMsg", "该邮箱已被注册!");
            return map;
        }
        // 注册用户
        user.setSalt(TaleUtils.generateUUID().substring(0, 5));
        user.setPassword(TaleUtils.md5(user.getPassword() + user.getSalt()));
        user.setType(0);
        userMapper.insertUser(user);
//        System.out.println(user);

        return map;
    }

    /**
     * @Description: 登陆验证
     * @param email,password,expireSecomds
     * @return: Map<String, Object>
     **/
    @Override
    public Map<String, Object> login(String email, String password,int expiredSeconds) {
        Map<String, Object> map = new HashMap<>();

        // 空值处理
        if(StringUtils.isBlank(email)){
            map.put("email","邮箱不能为空");
            return map;
        }
        if (StringUtils.isBlank(password)) {
            map.put("passwordMsg", "密码不能为空!");
            return map;
        }

        // 验证账号
        User user = userMapper.selectByEmail(email);
        if (user == null) {
            map.put("usernameMsg", "该账号不存在!");
            return map;
        }


        // 验证密码
        password = TaleUtils.md5(password + user.getSalt());
        if (!user.getPassword().equals(password)) {
            map.put("passwordMsg", "密码不正确!");
            return map;
        }
        // 生成登录凭证
        LoginTicket loginTicket = new LoginTicket();
        loginTicket.setUserId(user.getUid());
        loginTicket.setTicket(TaleUtils.generateUUID());
        loginTicket.setStatus(0);
        loginTicket.setExpired(new Date(System.currentTimeMillis() + expiredSeconds * 1000));


        String ticketKey = RedisKeyUtil.getTicketKey(loginTicket.getTicket());
        redisTemplate.opsForValue().set(ticketKey, loginTicket);

        map.put("ticket", loginTicket.getTicket());

        return map;
    }

    public void logout(String ticket) {
        //使用redis，更改ticket状态。不使用删除，为了后期进行登录相关的统计
        String ticketKey = RedisKeyUtil.getTicketKey(ticket);
        LoginTicket loginTicket = (LoginTicket) redisTemplate.opsForValue().get(ticketKey);
        loginTicket.setStatus(1);
        redisTemplate.opsForValue().set(ticketKey, loginTicket);
    }

    /**
     * @Description: 在redis中查找相应ticket
     * @param ticket
     * @return: LoginTicket
     **/
    @Override
    public LoginTicket findLoginTicket(String ticket) {
        String ticketKey = RedisKeyUtil.getTicketKey(ticket);
//        return loginTicketMapper.selectByTicket(ticket);
        return (LoginTicket) redisTemplate.opsForValue().get(ticketKey);
    }

    /**
     * @Description: 根据用户类型，获得相应权限
     * @param userId
     * @return: java.util.Collection<? extends org.springframework.security.core.GrantedAuthority>
     * @Date 2020/5/20
     **/
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(int userId){
        User user = userMapper.selectById(userId);

        List<GrantedAuthority> list=new ArrayList<>();
        list.add(new GrantedAuthority() {
            @Override
            public String getAuthority() {
                switch (user.getType()) {
                    case 1:
                        return CommunityConstant.AUTHORITY_ADMIN;
                    case 2:
                        return CommunityConstant.AUTHORITY_MODERATOR;
                    default:
                        return CommunityConstant.AUTHORITY_USER;
                }
            }
        });
        return list;
    }
}
