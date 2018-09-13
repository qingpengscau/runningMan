package scau.zxck.service.market;

import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.entity.market.Secure;

import java.util.List;

/**
 * Created by suruijia on 2016/1/29.
 */
public interface ISecureService {

    String addSecure(Secure secure) throws BaseException;

    List<Secure> listSecure(Conditions conditions) throws BaseException;
}
