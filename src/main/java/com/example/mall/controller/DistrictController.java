package com.example.mall.controller;


import com.example.mall.entity.District;
import com.example.mall.service.DistrictService;
import com.example.mall.util.JsonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/districts")
public class DistrictController extends BaseController {
    @Autowired
    private DistrictService districtService;

    @GetMapping({"", "/"})
//    @RequestMapping({"", "/"})
    public JsonResult<List<District>> getByParent(String parent) {
        List<District> data = districtService.getByParent(parent);
        JsonResult<List<District>> result = new JsonResult();
        result.setState(200);
        result.setData(data);
        return result;
    }
}
