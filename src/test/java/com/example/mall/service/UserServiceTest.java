package com.example.mall.service;

import com.example.mall.entity.User;
import com.example.mall.service.exception.PasswordNotMatchException;
import com.example.mall.service.exception.ServiceException;
import com.example.mall.service.exception.UpdateException;
import com.example.mall.service.exception.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void reg() {
        try {
            User user = new User();
            user.setUsername("test");
            user.setPassword("123");
            userService.reg(user);
            System.out.println("注册成功！");
        } catch (ServiceException e) {
            System.out.println("注册失败！" + e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void login() {
        User user = userService.login("1322","123");
        System.out.println(user);
    }

    @Test
    public void updatePassword() {
        userService.updatePassword(7, "管理员", "123", "321");
    }

    @Test
    public void findByUid() {
        User result = userService.findByUid(7);
        System.out.println(result);
    }

    @Test
    public void updateInfo() {
        User user = new User();
        user.setPhone("888888");
        user.setEmail("123@qq.com");
        user.setGender(0);
        userService.updateInfo(7, "管理员", user);
    }

    @Test
    public void updateAvatar() {
        userService.updateAvatar(7, "oooo", "管理员2");
    }
}
