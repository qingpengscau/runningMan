package scau.zxck.service.market;

import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.entity.market.ShoppingOrder;

import java.util.List;

/**
 * Created by suruijia on 2016/1/29.
 */
public interface IShoppingOrderService {
    String addShoppingOrder(ShoppingOrder shoppingOrder) throws BaseException;
     <v> List<v> listAll() throws BaseException;
    void deleteShoppingOrder(String id) throws BaseException;
    ShoppingOrder findByid(String id) throws  BaseException;
    void updateShoppingOrder(ShoppingOrder shoppingOrder) throws BaseException;
    List< ShoppingOrder> listShoppingOrder(Conditions conditions) throws BaseException;
}
