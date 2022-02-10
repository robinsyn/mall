package com.example.mall.mapper;

import com.example.mall.entity.Address;
import com.example.mall.entity.District;

import java.util.Date;
import java.util.List;

/**收货地址持久层*/
public interface AddressMapper {
    /**
     * 添加用户收货地址
     * @param address
     * @return
     */
    Integer addAddress(Address address);

    /**
     * 统计用户收货地址数量
     * @param uid
     * @return
     */
    Integer countByUid(Integer uid);

    /**
     * 根据uid查询用户收货地址数据
     * @param uid
     * @return
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid查询用户收货地址数据
     * @param aid
     * @return
     */
    Address findByAid(Integer aid);

    /**
     * 将某用户的所有收货地址设置为非默认地址
     * @param uid 收货地址归属的用户id
     * @return 受影响的行数
     */
    Integer updateNonDefault(Integer uid);

    /**
     * 将指定的收货地址设置为默认地址
     * @param aid 收货地址id
     * @param modifiedUser 修改执行人
     * @param modifiedTime 修改时间
     * @return 受影响的行数
     */
    Integer updateDefaultByAid(Integer aid,
                               String modifiedUser,
                               Date modifiedTime);

    /**
     * 根据收货地址id删除数据
     * @param aid 收货地址id
     * @return 受影响的行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 查询某用户最后修改的收货地址
     * @param uid 归属的用户id
     * @return 该用户最后修改的收货地址，如果该用户没有收货地址数据则返回null
     */
    Address findLastModified(Integer uid);
}
