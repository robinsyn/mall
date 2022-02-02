package com.example.mall.mapper;

import com.example.mall.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.Date;

public interface UserMapper {
    /**
     *
     * @param user
     * @return 返回影响的行数
     */
    public Integer addUser(User user); //增加用户

    /**
     *
     * @param username
     * @return 有这个用户返回用户的数据，没有返回null
     */
    public User findByName(String username); //根据名字查找用户

    /**
     * 更新用户的密码
     * @param uid
     * @param password
     * @param modifiedUser
     * @param modifiedTime
     * @return 返回影响的行数
     */
    public Integer updatePasswordByUid(Integer uid,
                                       String password,
                                       String modifiedUser,
                                       Date modifiedTime); //根据uid更改密码

    /**
     *
     * @param uid
     * @return
     */
    public User findByUid(Integer uid); //根据uid查找用户

    /**
     * 更新用户的资料
     * @param user
     * @return 返回影响的行数
     */
    public Integer updateInfoByUid(User user);

    /**
     * @Param("uid"):解决的问题：当sql的占位符与映射的接口方法参数名不一致时，将某个参数强行注入某个占位符变量
     * 上传头像
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
    public Integer updateAvatarByUid(@Param("uid") Integer uid,
                                    String avatar,
                                    String modifiedUser,
                                    Date modifiedTime);
}
