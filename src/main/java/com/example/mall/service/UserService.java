package com.example.mall.service;

import com.example.mall.entity.User;

public interface UserService {
    /**
     *用户注册功能
     * @param user
     */
    public void reg(User user);

    /**
     * 用户登录功能
     * @param username
     * @param password
     * @return 当前匹配的用户数据，如果没有返回null
     */
    public User login(String username, String password);

    /**
     * 更新密码
     * @param uid
     * @param username
     * @param oldPassword
     * @param newPassword
     */
    public void updatePassword(Integer uid,
                               String username,
                               String oldPassword,
                               String newPassword);

    /**
     * 根据uid查找用户
     * @param uid
     * @return
     */
    public User findByUid(Integer uid);

    /**
     * 修改用户信息
     * @param user
     */
    public void updateInfo(Integer uid, String username, User user);
}
