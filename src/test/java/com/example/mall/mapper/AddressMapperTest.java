package com.example.mall.mapper;

import com.example.mall.entity.Address;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class AddressMapperTest {

    @Autowired
    private AddressMapper addressMapper;

    @Test
    public void addAddress() {
        Address address = new Address();
        address.setName("juejue");
        address.setPhone("999999");
        address.setUid(7);
        addressMapper.addAddress(address);
    }

    @Test
    public void countByUid() {
        Integer result = addressMapper.countByUid(7);
        System.out.println(result);
    }

    @Test
    public void findByUid() {
        List<Address> list = addressMapper.findByUid(6);
        System.out.println("count=" + list.size());
        for (Address item : list) {
            System.out.println(item);
        }
    }

    @Test
    public void updateNonDefaultByUid() {
        Integer uid = 7;
        Integer rows = addressMapper.updateNonDefault(uid);
        System.out.println("rows=" + rows);
    }

    @Test
    public void updateDefaultByAid() {
        Integer aid =7;
        String modifiedUser = "管理员";
        Integer rows = addressMapper.updateDefaultByAid(aid, modifiedUser, new Date());
        System.out.println("rows=" + rows);
    }

    @Test
    public void findByAid() {
        Integer aid = 7;
        Address result = addressMapper.findByAid(aid);
        System.out.println(result);
    }

    @Test
    public void deleteByAid() {
        Integer aid = 4;
        Integer rows = addressMapper.deleteByAid(aid);
        System.out.println("rows=" + rows);
    }

    @Test
    public void findLastModified() {
        Integer uid = 7;
        Address result = addressMapper.findLastModified(uid);
        System.out.println(result);
    }
}
