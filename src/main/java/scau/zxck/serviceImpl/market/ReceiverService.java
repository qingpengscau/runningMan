package scau.zxck.serviceImpl.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.dao.market.FetchOrderDao;
import scau.zxck.dao.market.ReceiverDao;
import scau.zxck.entity.market.Fetchorder;
import scau.zxck.entity.market.Receiver;
import scau.zxck.service.market.IFetchOrderService;
import scau.zxck.service.market.IReceiverService;

import java.util.List;


/**
 * Created by suruijia on 2016/1/29.
 */
@Service
public class ReceiverService implements IReceiverService {
    @Autowired
    private ReceiverDao receiverDao;


    @Override
    public String addReceiver(Receiver receiver) throws BaseException {
        return receiverDao.add(receiver);
    }

    @Override
    public  <v> List<v> listAll() throws BaseException {

           return   receiverDao.listAll();

    }

    @Override
    public void deleteReceiver(String id) throws BaseException {

        receiverDao.deleteByIds(id);

    }

    @Override
    public Receiver findByid(String id) throws BaseException {
        return receiverDao.findById(id);
    }

    @Override
    public void updateReceiver(Receiver receiver) throws BaseException {
        receiverDao.updateById(receiver);
    }

    @Override
    public List<Receiver> listReceiver(Conditions conditions) throws BaseException {
        return   receiverDao.list(conditions);
    }
}
