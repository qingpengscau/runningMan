package scau.zxck.service.market;

import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.entity.market.Address;

import java.util.List;

/**
 * Created by suruijia on 2016/1/29.
 */
public interface IAddressService {
    String addReceiver(Address receiver) throws BaseException;
     <v> List<v> listAll() throws BaseException;
    void deleteReceiver(String id) throws BaseException;
    Address findByid(String id) throws  BaseException;
    void updateReceiver(Address receiver) throws BaseException;
    List<Address> listReceiver(Conditions conditions) throws BaseException;
}
