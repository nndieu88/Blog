package com.example.demo.security;

import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CookieUtil {
    public static void create(HttpServletResponse res, String name, String value) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(60 * 60 * 24);
        cookie.setPath("/");
        res.addCookie(cookie);
    }

    public static void clear(HttpServletResponse res, String name) {
        Cookie cookie = new Cookie(name, null);
        cookie.setMaxAge(0);
        cookie.setPath("/");
        res.addCookie(cookie);
    }

    public static String getCookie(HttpServletRequest req, String name) {
        Cookie cookie = WebUtils.getCookie(req, name);
        return cookie != null ? cookie.getValue() : null;
    }
}
