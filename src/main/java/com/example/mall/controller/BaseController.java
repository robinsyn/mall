package com.example.mall.controller;

import com.example.mall.controller.exception.*;
import com.example.mall.service.exception.*;
import com.example.mall.controller.exception.FileUploadException;
import com.example.mall.service.exception.ServiceException;
import com.example.mall.util.JsonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

public class BaseController {
    /**
     * 获取session对象中的uid,供整个过程使用
     * @param session
     * @return
     */
    protected final Integer getUidFromSession (HttpSession session) {
        return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取session对象中的username,供整个过程使用
     * @param session
     * @return
     */
    protected final String getUsernameFromSession (HttpSession session) {
        return session.getAttribute("username").toString();
    }

    /** 操作成功的状态码 */
//    public static final int OK = 200;

    /** @ExceptionHandler用于统一处理方法抛出的异常 */
//    @ExceptionHandler({ServiceException.class, FileUploadException.class})
//    public JsonResult<Void> handleException(Throwable e) {
//        JsonResult<Void> result = new JsonResult<>(e);
//        if (e instanceof UsernameDuplicatedException) {
//            result.setState(4000);
//        } else if (e instanceof UserNotFoundException) {
//            result.setState(4001);
//        } else if (e instanceof PasswordNotMatchException) {
//            result.setState(4002);
//        } else if (e instanceof InsertException) {
//            result.setState(5000);
//        } else if (e instanceof UpdateException) {
//            result.setState(5001);
//        } else if (e instanceof FileEmptyException) {
//            result.setState(6000);
//        } else if (e instanceof FileSizeException) {
//            result.setState(6001);
//        } else if (e instanceof FileTypeException) {
//            result.setState(6002);
//        } else if (e instanceof FileStateException) {
//            result.setState(6003);
//        } else if (e instanceof FileUploadIOException) {
//            result.setState(6004);
//        }
//        return result;
//    }
}
