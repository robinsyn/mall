package com.example.mall.controller;

import com.example.mall.entity.Address;
import com.example.mall.entity.District;
import com.example.mall.service.AddressService;
import com.example.mall.service.exception.*;
import com.example.mall.service.impl.AddressServiceImpl;
import com.example.mall.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

//@Controller
@RestController // 等于@Controller+@ResponseBody
@RequestMapping("/addresses")
public class AddressController extends BaseController{

    @Autowired
    private AddressService addressService;

    @RequestMapping("/add_new_address")
    public JsonResult<Void> addNewAddress(HttpSession session,
                                          Address address) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        JsonResult<Void> result = new JsonResult<>();
        try {
            addressService.addNewAddress(uid, username, address);
            result.setState(200);
            result.setMessage("添加成功");
        } catch (AddressCountLimitException e) {
            result.setState(4000);
            result.setMessage("用户收货地址超出上限");
        } catch (InsertException e) {
            result.setState(4000);
            result.setMessage("添加用户地址出现未知异常");
        }
        return result;
    }

    @RequestMapping({"", "/"})
    public JsonResult<List<Address>> getByUid(HttpSession session) {
        Integer uid = getUidFromSession(session);
        List<Address> data = addressService.getByUid(uid);
        JsonResult<List<Address>> result = new JsonResult();
        result.setState(200);
        result.setData(data);
        return result;
    }

    //restful风格请求编写
    @RequestMapping("/{aid}/set_default")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer aid, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        JsonResult<Void> result = new JsonResult<>();
        try {
            addressService.setDefault(aid, uid, username);
            result.setState(200);
            result.setMessage("设置成功");
        } catch (AddressNotFoundException e) {
            result.setState(4000);
            result.setMessage("收货地址数据不存在");
        } catch (AccessDeniedException e) {
            result.setState(4000);
            result.setMessage("非法数据访问");
        } catch (UpdateException e) {
            result.setState(4000);
            result.setMessage("设置默认收货地址时出现未知错误");
        }
        return result;
    }


    @RequestMapping("/{aid}/delete")
    public JsonResult<Void> delete(@PathVariable("aid") Integer aid, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        JsonResult<Void> result = new JsonResult<>();
        try {
            addressService.delete(aid, uid, username);
            result.setState(200);
            result.setMessage("设置成功");
        } catch (AddressNotFoundException e) {
            result.setState(4000);
            result.setMessage("尝试访问的收货地址数据不存在");
        } catch (AccessDeniedException e) {
            result.setState(4000);
            result.setMessage("非法数据访问");
        } catch (DeleteException e) {
            result.setState(4000);
            result.setMessage("删除收货地址数据时出现未知错误，请联系系统管理员");
        } catch (UpdateException e) {
            result.setState(4000);
            result.setMessage("更新收货地址数据时出现未知错误，请联系系统管理员");
        }
        return result;
    }
}
