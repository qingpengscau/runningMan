package scau.zxck.serviceImpl.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scau.zxck.dao.market.Fetch_orderDao;
import scau.zxck.dao.market.LinkDao;
@Service
public class Fetch_orderService {
    @Autowired
    private Fetch_orderDao fetch_orderDaoDao;

}
