package com.example.mall.util;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Json格式数据进行响应
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JsonResult<E> implements Serializable {
    //状态码
    private Integer state;
    //描述信息
    private String message;
    //数据
    private E data;

    /** 出现异常时调用 */
//    public JsonResult(Throwable e) {
//        super();
//        // 获取异常对象中的异常信息
//        this.message = e.getMessage();
//    }
}
