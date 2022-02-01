package com.example.mall.mapper;

import com.example.mall.entity.User;
import com.example.mall.mapper.UserMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;

//@SpringBootTest:表示是一个测试类，不会随项目一起打包发送
@SpringBootTest
public class UserMapperTest {

    //这里userMapper有红色波浪线是因为idea自动检测，接口不能直接创建bean（动态代理技术解决了，所以可以直接运行）
    @Autowired
    private UserMapper userMapper;

    /**
     * 单元测试方法必须被@test修饰
     * 返回值类型必须是void
     * 方法的参数列表不指定任何类型
     * 方法的访问修饰符必须是public
     */
    @Test
    public void addUser(){
        User user = new User();
        user.setUsername("juejue");
        user.setPassword("123456");
        Integer row = userMapper.addUser(user);
        System.out.println(row);
    }

    @Test
    public void updatePasswordByUid() {
        userMapper.updatePasswordByUid(6,"1322","管理员",new Date());
    } //根据uid更改密码


    @Test
    public void findByUid() {
        System.out.println(userMapper.findByUid(6));
    } //根据uid查找用户

    @Test
    public void updateInfoByUid() {
        User user = new User();
        user.setUid(7);
        user.setPhone("999999");
        user.setEmail("888@qq.com");
        user.setGender(1);
        userMapper.updateInfoByUid(user);
    }


}
