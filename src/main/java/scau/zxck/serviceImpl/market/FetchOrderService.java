package scau.zxck.serviceImpl.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scau.zxck.base.exception.BaseException;
import scau.zxck.dao.market.FetchOrderDao;
import scau.zxck.dao.market.UnionStaffDao;
import scau.zxck.entity.market.Fetchorder;
import scau.zxck.entity.market.UnionStaff;
import scau.zxck.service.market.IFetchOrderService;
import scau.zxck.service.market.IUnionStaffService;

import java.util.List;


/**
 * Created by suruijia on 2016/1/29.
 */
@Service
public class FetchOrderService implements IFetchOrderService {
    @Autowired
    private FetchOrderDao fetchOrderDao;


    @Override
    public String addFetchorder(Fetchorder fetchOrder) throws BaseException {
        return fetchOrderDao.add(fetchOrder);
    }

    @Override
    public  <v> List<v> listAll() throws BaseException {

           return   fetchOrderDao.listAll();

    }

    @Override
    public void deleteFetchorder(String id) throws BaseException {

        fetchOrderDao.deleteByIds(id);

    }

    @Override
    public Fetchorder findByid(String id) throws BaseException {
        return fetchOrderDao.findById(id);
    }

    @Override
    public void updateFetchOrder(Fetchorder fetchorder) throws BaseException {
        fetchOrderDao.updateById(fetchorder);
    }
}
