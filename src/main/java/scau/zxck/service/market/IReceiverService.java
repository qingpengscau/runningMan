package scau.zxck.service.market;

import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.entity.market.Fetchorder;
import scau.zxck.entity.market.Receiver;

import java.util.List;

/**
 * Created by suruijia on 2016/1/29.
 */
public interface IReceiverService {
    String addReceiver(Receiver receiver) throws BaseException;
     <v> List<v> listAll() throws BaseException;
    void deleteReceiver(String id) throws BaseException;
    Receiver findByid(String id) throws  BaseException;
    void updateReceiver(Receiver receiver) throws BaseException;
    List< Receiver> listReceiver(Conditions conditions) throws BaseException;
}
