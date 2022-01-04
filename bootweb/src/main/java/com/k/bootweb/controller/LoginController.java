package com.k.bootweb.controller;

import com.k.bootweb.mapper.UserMapper;
import com.k.bootweb.pojo.dao.User;
import com.k.bootweb.pojo.dto.LoginDto;
import com.k.bootweb.pojo.dto.Result;
import com.k.bootweb.service.LoginService;
import com.k.bootweb.utils.constant.CommunityConstant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Api("登录相关接口")
@RestController
@CrossOrigin(origins = "*")
public class LoginController implements CommunityConstant {

//    private static final Logger LOGGER = LogManager.getLogger(UserController.class);

    @Autowired
    private LoginService loginService;

    @Autowired
    private RedisTemplate redisTemplate;

//    @ApiOperation("测试接口（通过id查找用户）")
//    @GetMapping("/queryUser")
//    public User queryUser(Integer id){
//        User user=userMapper.selectById(id);
//        return user;
//    }

    @ApiOperation("登录")
    @PostMapping(path = "/login")
    public Result login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
//
//        String kaptcha = null;
//        if (StringUtils.isNotBlank(ticket)) {
//            kaptcha = (String) redisTemplate.opsForValue().get(ticket);
//        }
        //
        int expiredSeconds = loginDto.isRememberme() ? REMEMBER_EXPIRED_SECONDS : DEFAULT_EXPIRED_SECONDS;
        Map<String, Object> map = loginService.login(loginDto.getEmail(), loginDto.getPassword(),expiredSeconds);
        if (StringUtils.isNotBlank((CharSequence) map.get("ticket"))) {
            Cookie cookie = new Cookie("ticket", map.get("ticket").toString());
            cookie.setPath("/");
            cookie.setMaxAge(expiredSeconds);
            response.addCookie(cookie);
            return Result.success("登录成功");
        } else {
            if(StringUtils.isNotBlank((CharSequence) map.get("usernameMsg"))){
                return Result.fail((String) map.get("usernameMsg"));
            }
            else if(StringUtils.isNotBlank((CharSequence) map.get("passwordMsg"))){
                return Result.fail((String) map.get("passwordMsg"));
            }
            else
                return null;
        }

    }

    @ApiOperation("注册")
    @PostMapping(path = "/register")
    public Result register(@RequestBody User user)
    {

        Map<String, String> map = loginService.register(user);
        if (map == null || map.isEmpty()) {
                return Result.success("注册成功");
        } else {
//            if(StringUtils.isNotBlank(map.get("usernameMsg"))){
//                return Result.fail(map.get("usernameMsg"));
//            }
            if(StringUtils.isNotBlank( map.get("passwordMsg"))){
                return Result.fail(map.get("passwordMsg"));
            }
            else if(StringUtils.isNotBlank(map.get("emailMsg")))
                return Result.fail(map.get("emailMsg"));
            else
                return null;
        }
    }

    @PostMapping(path = "/logout")
    public Result logout(@CookieValue("ticket") String ticket) {
        loginService.logout(ticket);
        SecurityContextHolder.clearContext();
        return Result.success("登出成功");
    }
}
