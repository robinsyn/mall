package com.example.mall.service;

import com.example.mall.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AddressServiceTest {

    @Autowired
    private AddressService addressService;

    @Test
    public void addNewAddress() {
        Address address = new Address();
        address.setName("juejue2");
        address.setPhone("888888");
        addressService.addNewAddress(6, "管理员", address);
    }

    @Test
    public void setDefault() {
        try {
            Integer aid = 7;
            Integer uid = 7;
            String username = "系统管理员";
            addressService.setDefault(aid, uid, username);
            System.out.println("OK.");
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

    @Test
    public void delete() {
        try {
            Integer aid = 1;
            Integer uid = 7;
            String username = "管理员";
            addressService.delete(aid, uid, username);
            System.out.println("OK.");
        } catch (Exception e) {
            System.out.println(e.getClass().getSimpleName());
            System.out.println(e.getMessage());
        }
    }

}
