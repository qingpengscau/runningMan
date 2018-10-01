package scau.zxck.service.market;

import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.dao.utils.Page;
import scau.zxck.base.exception.BaseException;
import scau.zxck.entity.market.Fetchorder;


import java.util.List;

/**
 * Created by suruijia on 2016/1/29.
 */
public interface IFetchOrderService {
    String addFetchorder(Fetchorder fetchOrder) throws BaseException;
     <v> List<v> listAll() throws BaseException;
    void deleteFetchorder(String id) throws BaseException;
    Fetchorder findByid(String id) throws  BaseException;
    void updateFetchOrder(Fetchorder fetchorder) throws BaseException;
    List< Fetchorder> listFetchorder(Conditions conditions) throws BaseException;
}
