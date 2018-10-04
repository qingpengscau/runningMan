package scau.zxck.serviceImpl.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.dao.market.AddressDao;
import scau.zxck.entity.market.Address;
import scau.zxck.service.market.IReceiverService;

import java.util.List;


/**
 * Created by suruijia on 2016/1/29.
 */
@Service
public class ReceiverService implements IReceiverService {
    @Autowired
    private AddressDao receiverDao;


    @Override
    public String addReceiver(Address receiver) throws BaseException {
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
    public Address findByid(String id) throws BaseException {
        return receiverDao.findById(id);
    }

    @Override
    public void updateReceiver(Address receiver) throws BaseException {
        receiverDao.updateById(receiver);
    }

    @Override
    public List<Address> listReceiver(Conditions conditions) throws BaseException {
        return   receiverDao.list(conditions);
    }
}
