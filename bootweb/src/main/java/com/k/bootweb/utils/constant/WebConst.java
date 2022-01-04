package com.k.bootweb.utils.constant;

import java.util.HashMap;
import java.util.Map;

public class WebConst {
    /**
     * 一些网站配置
     */
    public static Map<String, String> initConfig = new HashMap<>();

    /**
     * session的key
     */
    public static String LOGIN_SESSION_KEY = "login_user";

    public static final String USER_IN_COOKIE = "S_L_ID";




    /**
     * aes加密加盐
     */
    public static String AES_SALT = "0123456789abcdef";

}
