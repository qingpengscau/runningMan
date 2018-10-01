package scau.zxck.service.market;

import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.dao.utils.Page;
import scau.zxck.base.exception.BaseException;
import scau.zxck.entity.market.User;
import java.util.List;

/**
 * Created by suruijia on 2016/1/29.
 */
public interface IUserService {

    User findOne(String id) throws BaseException;

    void updateUser(User user) throws BaseException;

    void deleteUser(String id) throws BaseException;

    String addUser(User user) throws BaseException;

    User findByName(String username) throws BaseException;

    List<User> listUser(Conditions conditions) throws BaseException;
}