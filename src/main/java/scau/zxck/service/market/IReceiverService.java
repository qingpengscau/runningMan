package scau.zxck.service.market;

import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.entity.market.Receiver;

import java.util.List;

public interface IReceiverService {
    void updateReceiver(Receiver receiver) throws BaseException;
    void addReceiver(Receiver receiver) throws BaseException;
    void deleteReceiver(String id) throws BaseException;
    List<Receiver> getAddress(Conditions conditions) throws BaseException;
}
