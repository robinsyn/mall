package com.example.mall.controller;

import com.example.mall.entity.Order;
import com.example.mall.service.OrderService;
import com.example.mall.service.exception.InsertException;
import com.example.mall.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("orders")
public class OrderController extends BaseController {
    @Autowired
    private OrderService orderService;

    @RequestMapping("/create")
    public JsonResult<Order> create(Integer aid, Integer[] cids, HttpSession session) {
        // 从Session中取出uid和username
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        JsonResult<Order> result = new JsonResult<>();
        try {
            // 调用业务对象执行业务
            Order data = orderService.create(aid, cids, uid, username);
            result.setData(data);
            result.setState(200);
            result.setMessage("创建订单成功");
        } catch (InsertException e) {
            result.setState(4000);
            result.setMessage("插入订单数据时出现未知错误，请联系系统管理员");
        }
        return result;
    }
}
