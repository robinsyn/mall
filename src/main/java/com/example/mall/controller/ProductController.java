package com.example.mall.controller;

import com.example.mall.entity.District;
import com.example.mall.entity.Product;
import com.example.mall.service.ProductService;
import com.example.mall.service.exception.AddressCountLimitException;
import com.example.mall.service.exception.InsertException;
import com.example.mall.service.exception.ProductNotFoundException;
import com.example.mall.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController extends BaseController {
    @Autowired
    private ProductService productService;

    @RequestMapping("/hot_list")
    public JsonResult<List<Product>> getHotList() {
        List<Product> data = productService.findHotList();
        JsonResult<List<Product>> result = new JsonResult();
        result.setState(200);
        result.setData(data);
        return result;
    }

    @GetMapping("/{id}/details")
    public JsonResult<Product> getById(@PathVariable("id") Integer id) {
        JsonResult<Product> result = new JsonResult<>();
        try {
            // 调用业务对象执行获取数据
            Product data = productService.findById(id);
            result.setState(200);
            result.setData(data);
            result.setMessage("访问的商品数据成功");
        } catch (ProductNotFoundException e) {
            result.setState(4000);
            result.setMessage("尝试访问的商品数据不存在");
        }
        return result;
    }
}
