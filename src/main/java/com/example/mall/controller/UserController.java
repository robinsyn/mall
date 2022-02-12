package com.example.mall.controller;

import com.example.mall.controller.exception.*;
import com.example.mall.entity.User;
import com.example.mall.service.UserService;
import com.example.mall.service.exception.*;
import com.example.mall.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;

//@Controller
@RestController // 等于@Controller+@ResponseBody
@RequestMapping("/users")
public class UserController extends BaseController{

    @Autowired
    private UserService userService;

    /**
     * 约定大于配置：开发思想来完成，省略大量配甚至注解
     *接收数据方式：
     * 1.请求处理方法的参数列表设置为pojo类型来接收前端数据
     * springboot会将前端的url地址中的参数名和pojo类的属性名进行比较
     * 如果这两个名称相同，则将值注入到pojo类中对应的属性上
     */
    @RequestMapping("/reg")
//    @ResponseBody //表示此方法的响应结果以Json格式进行数据传输
    public JsonResult<Void> reg(User user, String secondpassword) {
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("注册成功");
        } catch (UsernameDuplicatedException e) {
            result.setState(4000);
            result.setMessage("用户名已被占用");
        } catch (InsertException e) {
            result.setState(4000);
            result.setMessage("注册过程中产生未知异常");
        }
        return result;
    }

    /**
     *接收数据方式：
     * 2.请求处理方法的参数列表设置为非pojo类型
     * springboot会直接将请求的参数名和方法的参数名直接进行比较
     * 如果名称相同自动完成值的依赖注入
     */
    @RequestMapping("login")
    public JsonResult<User> login(String username,
                                  String password,
                                  HttpSession session) {
        JsonResult<User> result = new JsonResult<>();
        try {
            User data = userService.login(username, password);
            //向session对象中完成全局数据的绑定
            session.setAttribute("uid", data.getUid());
            session.setAttribute("username", data.getUsername());
            result.setState(200);
            result.setData(data);
            result.setMessage("登陆成功");
        } catch (UserNotFoundException e) {
            result.setState(4000);
            result.setMessage("用户不存在");
        } catch (PasswordNotMatchException e) {
            result.setState(4001);
            result.setMessage("密码错误");
        }
        return result;
    }

    @RequestMapping("/change_password")
    public JsonResult<Void> changePassword(HttpSession session,
                               String oldPassword,
                               String newPassword) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.updatePassword(uid, username, oldPassword, newPassword);
            result.setState(200);
            result.setMessage("修改成功");
        } catch (UserNotFoundException e) {
            result.setState(4000);
            result.setMessage("用户数据不存在");
        } catch (PasswordNotMatchException e) {
            result.setState(4000);
            result.setMessage("原密码错误");
        } catch (UpdateException e) {
            result.setState(4000);
            result.setMessage("更新时产生未知的错误异常");
        }
        return result;
    }

    @RequestMapping("/find_by_uid")
    public JsonResult<User> findByUid(HttpSession session) {
        User data = userService.findByUid(getUidFromSession(session));
        JsonResult<User> result = new JsonResult<>();
        try {
            result.setState(200);
            result.setData(data);
        } catch (UserNotFoundException e) {
            result.setState(4000);
            result.setMessage("用户不存在");
        }
        return result;
    }

    @RequestMapping("/change_info")
    public JsonResult<Void> changeInfo(User user,
                                       HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.updateInfo(uid, username, user);
            result.setState(200);
            result.setMessage("修改成功");
        } catch (UserNotFoundException e) {
            result.setState(4000);
            result.setMessage("用户数据不存在");
        } catch (UpdateException e) {
            result.setState(4000);
            result.setMessage("更新时产生未知的错误异常");
        }
        return result;
    }



    /**
     * MultipartFile接口是springmvc提供的一个接口，这个接口为我们包装了获取文件类型的数据(任何类型的file)
     * springboot整合了springmvc，在处理请求的方法参数列表上声明一个参数类型为MultipartFile的参数，然后springboot自动将
     * 传递给服务器的文件数据赋值给这个参数
     * @param session
     * @param file
     * @return
     */
    @RequestMapping("/change_avatar")
    public JsonResult<String> changeAvatar(HttpSession session,
                                           MultipartFile file) {
        //判断文件是否为null
        if (file.isEmpty()) {
            throw new FileEmptyException("文件为空");
        }
        //限制文件上传类型
        List<String> AVATAR_TYPE = new ArrayList<>();
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        //判断文件的后缀
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)) {
            throw new FileTypeException("文件类型不支持");
        }
        //上传的文件 .../upload/文件.png
        String parent = session.getServletContext().getRealPath("upload");
        //file对象指向这个路径
        File dir = new File(parent);
        //检测目录是否存在，不存在创建一个
        if (!dir.exists()) {
            dir.mkdirs();
        }
        //获取文件名称，UUID生成一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
        //取出后缀
        int index = originalFilename.lastIndexOf(".");
        String suffix = originalFilename.substring(index);
        String fileName = UUID.randomUUID().toString().toUpperCase() + suffix;
        File dest = new File(dir, fileName);
        try {
            file.transferTo(dest); //将file文件写入dest中
        } catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        } catch (IOException e) {
            throw new FileUploadIOException("文件读写异常");
        }
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        String avatar = "/upload/" + fileName;
        JsonResult<String> result = new JsonResult<>();
        try {
            userService.updateAvatar(uid, avatar, username);
            result.setState(200);
            result.setData(avatar);
            result.setMessage("上传成功");
        } catch (UserNotFoundException e) {
            result.setState(4000);
            result.setMessage("用户不存在");
        } catch (UpdateException e) {
            result.setState(4000);
            result.setMessage("更新头像时产生未知的错误异常");
        }
        return result;
    }
}
