package com.example.mall.service;

import com.example.mall.entity.Address;

import java.util.List;

/**收货地址业务接口*/
public interface AddressService {

    void addNewAddress(Integer uid, String username, Address address);

    List<Address> getByUid(Integer uid);

    /**
     * 设置默认地址
     * @param aid
     * @param uid
     * @param username
     */
    void setDefault(Integer aid, Integer uid, String username);

    /**
     * 删除收货地址
     * @param aid 收货地址id
     * @param uid 归属的用户id
     * @param username 当前登录的用户名
     */
    void delete(Integer aid, Integer uid, String username);

    /**
     * 根据aid查询
     * @param aid
     * @return
     */
    Address getByAid(Integer aid, Integer uid);
}
