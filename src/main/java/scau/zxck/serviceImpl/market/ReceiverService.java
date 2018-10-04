package scau.zxck.serviceImpl.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.dao.market.ReceiverDao;
import scau.zxck.entity.market.Receiver;
import scau.zxck.service.market.IReceiverService;

import java.util.List;

@Service
public class ReceiverService implements IReceiverService {

    @Autowired
    private ReceiverDao receiverDao;
    @Override
    public void updateReceiver(Receiver receiver) throws BaseException {
        receiverDao.updateById(receiver);
    }

    @Override
    public void addReceiver(Receiver receiver) throws BaseException {
        receiverDao.add(receiver);
    }

    @Override
    public void deleteReceiver(String id) throws BaseException {
        receiverDao.deleteByIds(id);
    }

    @Override
    public List<Receiver> listReceiver(Conditions conditions ) throws BaseException {
        return receiverDao.list(conditions);
    }
}
