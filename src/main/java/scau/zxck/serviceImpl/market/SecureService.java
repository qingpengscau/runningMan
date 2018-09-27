package scau.zxck.serviceImpl.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.dao.market.SecureDao;
import scau.zxck.dao.market.UserDao;
import scau.zxck.entity.market.Secure;
import scau.zxck.service.market.ISecureService;
import scau.zxck.entity.market.User;
import java.util.List;


@Service
public class SecureService implements ISecureService {
    @Autowired
    private SecureDao secureDao;

    @Override
    public String addSecure(Secure secure) throws BaseException {
        return secureDao.add(secure);
    }
    @Override
    public List<Secure> listSecure(Conditions conditions) throws BaseException {
        return secureDao.list(conditions);
    }

}
