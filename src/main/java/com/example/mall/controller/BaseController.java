package com.example.mall.controller;

import javax.servlet.http.HttpSession;

public class BaseController {
    /**
     * 获取session对象中的uid,供整个过程使用
     * @param session
     * @return
     */
    protected final Integer getUidFromSession (HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取session对象中的username,供整个过程使用
     * @param session
     * @return
     */
    protected final String getUsernameFromSession (HttpSession session) {
        return session.getAttribute("username").toString();
    }
}
