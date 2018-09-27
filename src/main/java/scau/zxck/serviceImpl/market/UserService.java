package scau.zxck.serviceImpl.market;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.dao.market.UserDao;
import scau.zxck.entity.market.User;
import scau.zxck.service.market.IUserService;

import java.util.List;


@Service
public class UserService implements IUserService {
    @Autowired
    private UserDao userDao;



    @Override
    public User findOne(String id) throws BaseException {
        return userDao.findById(id);
    }

    @Override
    public void updateUser(User user) throws BaseException {
        userDao.updateById(user);
    }

    @Override
    public void deleteUser(String id) throws BaseException {
        userDao.deleteByIds(id);
    }

    @Override
    public String addUser(User user) throws BaseException {
        return userDao.add(user);
    }

    @Override
    public User findByName(String username) throws BaseException {
        Conditions conditions = new Conditions();
        conditions.eq("name", username);
        return userDao.find(conditions);
    }

    @Override
    public List<User> listUser(Conditions conditions) throws BaseException {
        return userDao.list(conditions);
    }

}
