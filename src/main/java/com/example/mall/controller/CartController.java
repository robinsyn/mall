package com.example.mall.controller;

import com.example.mall.entity.Product;
import com.example.mall.service.CartService;
import com.example.mall.service.exception.AccessDeniedException;
import com.example.mall.service.exception.CartNotFoundException;
import com.example.mall.service.exception.InsertException;
import com.example.mall.service.exception.ProductNotFoundException;
import com.example.mall.util.JsonResult;
import com.example.mall.vo.CartVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/carts")
public class CartController extends BaseController {
    @Autowired
    private CartService cartService;

    @RequestMapping("/add_to_cart")
    public JsonResult<Void> addToCart(Integer pid, Integer amount, HttpSession session) {
        // 从Session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        JsonResult<Void> result = new JsonResult<>();
        try {
            // 调用业务对象执行添加到购物车
            cartService.addToCart(uid, pid, amount, username);
            result.setState(200);
            result.setMessage("添加到购物车成功");
        } catch (InsertException e) {
            result.setState(4000);
            result.setMessage("添加到购物车时出现未知错误，请联系系统管理员");
        }
        return result;
    }

    @RequestMapping({"", "/"})
    public JsonResult<List<CartVO>> getVOByUid(HttpSession session) {
        // 从Session中获取uid
        Integer uid = getUidFromSession(session);
        JsonResult<List<CartVO>> result = new JsonResult<>();
        // 调用业务对象执行查询数据
        List<CartVO> data = cartService.getVOByUid(uid);
        result.setState(200);
        result.setData(data);
        result.setMessage("联表查询成功");
        // 返回成功与数据
        return result;
    }

    @RequestMapping("{cid}/num/add")
    public JsonResult<Integer> addNum(@PathVariable("cid") Integer cid, HttpSession session) {
        // 从Session中获取uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        JsonResult<Integer> result = new JsonResult<>();
        try {
            // 调用业务对象执行增加数量
            Integer data = cartService.addNum(cid, uid, username);
            result.setState(200);
            result.setData(data);
            result.setMessage("增加成功");
        } catch (CartNotFoundException e) {
            result.setState(4000);
            result.setMessage("尝试访问的购物车数据不存在");
        } catch (AccessDeniedException e) {
            result.setState(4000);
            result.setMessage("非法访问");
        } catch (InsertException e) {
            result.setState(4000);
            result.setMessage("修改商品数量时出现未知错误，请联系系统管理员");
        }
        return result;
    }

    @RequestMapping("/list")
    public JsonResult<List<CartVO>> getVOByCids(Integer[] cids, HttpSession session) {
        // 从Session中获取uid
        Integer uid = getUidFromSession(session);
        JsonResult<List<CartVO>> result = new JsonResult<>();
        // 调用业务对象执行查询数据
        List<CartVO> data = cartService.getVOByCids(uid, cids);
        result.setState(200);
        result.setData(data);
        result.setMessage("getVOByCids成功");
        // 返回成功与数据
        return result;
    }
}
