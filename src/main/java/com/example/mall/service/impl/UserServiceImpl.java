package com.example.mall.service.impl;

import com.example.mall.entity.User;
import com.example.mall.mapper.UserMapper;
import com.example.mall.service.UserService;
import com.example.mall.service.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.util.Date;
import java.util.Locale;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserMapper userMapper;

    @Override
    public void reg(User user) {

        User result = userMapper.findByName(user.getUsername());

        if (result != null) {
            throw new UsernameDuplicatedException("用户名被占用");
        }


        //密码加密处理：md5加密
        //盐值 + password + 盐值 ---> md5算法加密，连续加载三次
        String oldPassword = user.getPassword();
        //获取盐值
        String salt = UUID.randomUUID().toString().toUpperCase();
        //记录盐值
        user.setSalt(salt);
        String md5Password = getMd5Password(oldPassword,salt);
        user.setPassword(md5Password);

        user.setIsDelete(0);
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date date = new Date();
        user.setCreatedTime(date);
        user.setModifiedTime(date);

        Integer row = userMapper.addUser(user);
        if (row != 1) {
            throw new InsertException("用户注册过程中产生未知异常");
        }
    }

    @Override
    public User login(String username, String password) {
        User user = new User();
        User result = userMapper.findByName(username);
        if(result == null) {
            throw new UserNotFoundException("用户不存在");
        }
        //检测用户密码是否匹配
        String oldPassword = result.getPassword();
        String salt = result.getSalt();
        String newMd5 = getMd5Password(password, salt);
        if (!newMd5.equals(oldPassword)) {
            throw new PasswordNotMatchException("密码错误");
        }
        user.setUid(result.getUid());
        user.setUsername(result.getUsername());
        user.setAvatar(result.getAvatar());
        return user;
    }

    @Override
    public void updatePassword(Integer uid, String username, String oldPassword, String newPassword) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        String oldMd5Password = getMd5Password(oldPassword, result.getSalt());
        //检验原始密码是否正确
        if (!result.getPassword().equals(oldMd5Password)) {
            throw new PasswordNotMatchException("密码错误");
        }
        //将新的密码设置到数据库
        String newMd5Password = getMd5Password(newPassword, result.getSalt());
        Integer row = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
        if (row != 1) {
            throw new UpdateException("更新时产生未知的错误异常");
        }
    }

    @Override
    public User findByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        User user = new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    @Override
    public void updateInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedUser(username);
        user.setModifiedTime(new Date());
        Integer row = userMapper.updateInfoByUid(user);
        if (row != 1) {
            throw new UpdateException("更新资料时产生未知的错误异常");
        }
    }

    @Override
    public void updateAvatar(Integer uid, String avatar, String username) {
        User result = userMapper.findByUid(uid);
        if (result == null || result.getIsDelete() == 1) {
            throw new UserNotFoundException("用户数据不存在");
        }
        Integer row = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (row != 1) {
            throw new UpdateException("更新头像时产生未知的错误异常");
        }
    }

    /**
     * 定义一个md5算法的加密处理
     */
    private String getMd5Password(String password,String salt) {
        for (int i = 0; i < 3; i++) {
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }
}
