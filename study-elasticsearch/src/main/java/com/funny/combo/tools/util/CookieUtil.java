package com.funny.combo.tools.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static final String USER_NAME = "userName";
    public static final String TOKEN = "token";

    public static void deleteCookie(String name, HttpServletResponse response) {
        Cookie cookie = new Cookie(name.trim(), "");
        cookie.setMaxAge(0);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static void setCookie(String name, String value, HttpServletResponse response) {
        Cookie cookie = new Cookie(name.trim(), value.trim());
        cookie.setMaxAge(30 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);
    }

    public static String getCookie(String name, HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if(cookies!=null && cookies.length > 0){
            for (Cookie cookie : cookies) {
                if (name.trim().equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;
    }
}
