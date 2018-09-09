package scau.zxck.web.admin;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.entity.market.Secure;
import scau.zxck.entity.market.User;
import scau.zxck.service.market.IUserService;
import scau.zxck.entity.market.Secure;
import scau.zxck.service.market.
import scau.zxck.utils.HtmlRegexpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Condition;
import scau.zxck.utils.ReadJson;

@Controller
@RequestMapping("/")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring/spring.xml")
public class RegisterAction {
    @Autowired
    private IUserService iUserService;
    @Autowired
    HttpServletRequest request;
    @RequestMapping(value="register",method = RequestMethod.POST)
    public void register()throws IOException,BaseException{
        JSONObject data=ReadJson.readJson(request);
        String cell=data.get("User_Cell").toString();
        String password=data.get("User_Password").toString();
        User user=new User();
        user.setUser_cell(cell);
        user.setUser_password(password);
        String userId=iUserService.addUser(user);

        Secure secure=new Secure();
        secure.setId(userId);
        secure.setSecure_question(data.get("Secure_Question1").toString());
        secure.setSecure_answer(data.get("Secure_Answer1").toString());


        secure.setId(userId);
        secure.setSecure_question(data.get("Secure_Question1").toString());
        secure.setSecure_answer(data.get("Secure_Answer1").toString());
    }

    public void valiateAccount(){

    }
}
