package scau.zxck.web.admin;
import scau.zxck.entity.sys.User;
import scau.zxck.service.sys.IUserService;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import scau.zxck.base.exception.BaseException;
@Controller
@RequestMapping("/")
public class LoginAction {
    @RequestMapping("login")
    public void login(){

    }
}
