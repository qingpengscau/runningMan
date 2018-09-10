package scau.zxck.service.market;

import scau.zxck.base.dao.utils.Page;
import scau.zxck.base.exception.BaseException;
import scau.zxck.entity.market.Fetchorder;


import java.util.List;

/**
 * Created by suruijia on 2016/1/29.
 */
public interface IFetchOrderService {
    String addFetchorder(Fetchorder fetchOrder) throws BaseException;
    <V> List<V> ListAll() throws BaseException;
}
