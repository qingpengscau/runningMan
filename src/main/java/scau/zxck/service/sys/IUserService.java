package scau.zxck.service.sys;

import scau.zxck.base.dao.utils.Page;
import scau.zxck.base.exception.BaseException;

import java.util.List;

/**
 * Created by suruijia on 2016/1/29.
 */
public interface IUserService {
    Page<User> pageUser(User user) throws BaseException;

    User findOne(String id) throws BaseException;

    void updateUser(User user) throws BaseException;

    void deleteUser(String id) throws BaseException;

    String addUser(User user) throws BaseException;

    User findByName(String username) throws BaseException;

    List<User> listUser() throws BaseException;
}
