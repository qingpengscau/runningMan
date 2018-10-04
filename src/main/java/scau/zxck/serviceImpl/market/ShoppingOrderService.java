package scau.zxck.serviceImpl.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;

import scau.zxck.dao.market.ShoppingOrderDao;
import scau.zxck.entity.market.ShoppingOrder;
import scau.zxck.service.market.IShoppingOrderService;


import java.util.List;


/**
 * Created by suruijia on 2016/1/29.
 */
@Service
public class ShoppingOrderService implements IShoppingOrderService {
    @Autowired
    private ShoppingOrderDao shoppingOrderDao;

    @Override
    public String addShoppingOrder(ShoppingOrder shoppingOrder) throws BaseException {
        return shoppingOrderDao.add(shoppingOrder);
    }

    @Override
    public <v> List<v> listAll() throws BaseException {
        return shoppingOrderDao.listAll();
    }

    @Override
    public void deleteShoppingOrder(String id) throws BaseException {
           shoppingOrderDao.deleteByIds(id);
    }

    @Override
    public ShoppingOrder findByid(String id) throws BaseException {
        return shoppingOrderDao.findById(id);
    }

    @Override
    public void updateShoppingOrder(ShoppingOrder shoppingOrder) throws BaseException {
                     shoppingOrderDao.updateById(shoppingOrder);
    }

    @Override
    public List<ShoppingOrder> listShoppingOrder(Conditions conditions) throws BaseException {
        return shoppingOrderDao.list(conditions);
    }



}
