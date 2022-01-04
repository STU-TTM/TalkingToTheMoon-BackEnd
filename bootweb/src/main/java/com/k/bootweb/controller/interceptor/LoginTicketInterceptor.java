package com.k.bootweb.controller.interceptor;

import com.k.bootweb.pojo.dao.LoginTicket;
import com.k.bootweb.pojo.dao.User;
import com.k.bootweb.pojo.dto.Result;
import com.k.bootweb.service.LoginService;
import com.k.bootweb.utils.CookieUtil;
import com.k.bootweb.utils.HostHolder;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextImpl;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

@Component
public class LoginTicketInterceptor implements HandlerInterceptor {
    @Autowired
    private LoginService loginService;

    @Autowired
    private HostHolder hostHolder;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从cookie中获取凭证
        String ticket = CookieUtil.getValue(request, "ticket");

        if (ticket != null) {
            // 查询凭证
            LoginTicket loginTicket = loginService.findLoginTicket(ticket);
//            System.out.println(loginTicket);
            // 检查凭证是否有效
            if (loginTicket != null && loginTicket.getStatus() == 0 && loginTicket.getExpired().after(new Date())) {
                // 根据凭证查询用户
                User user = loginService.findUserById(loginTicket.getUserId());
                // 在本次请求中持有用户
                hostHolder.setUser(user);

                // 构建用户认证的结果,并存入SecurityContext,以便于Security进行授权.
                // principal: 主要信息; credentials: 证书; authorities: 权限;
                Authentication authentication = new UsernamePasswordAuthenticationToken(
                        user, user.getPassword(), loginService.getAuthorities(user.getUid()));
                // 存入SecurityContext
                SecurityContextHolder.setContext(new SecurityContextImpl(authentication));
                return true;

            }
            else {
                returnJson(response);
                return false;
            }
        }

        returnJson(response);
        return false;

    }
    private void returnJson(HttpServletResponse response) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            writer = response.getWriter();
            JSONObject jsonObject = new JSONObject(Result.fail("请先登录"));
            writer.print(jsonObject);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (writer != null) {
                writer.close();
            }
        }
    }
    /*
    在模板引擎之前使用
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        User user = hostHolder.getUser();
        if (user != null && modelAndView != null) {
            modelAndView.addObject("loginUser", user);
        }
    }

    /*
    最后清除user数据
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        hostHolder.clear();
        // 清除保存权限验证的结果
        SecurityContextHolder.clearContext();
    }

}
