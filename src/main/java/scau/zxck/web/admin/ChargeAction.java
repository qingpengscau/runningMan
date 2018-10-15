package scau.zxck.web.admin;

import com.alibaba.fastjson.JSONObject;
import netscape.javascript.JSObject;
import org.apache.shiro.session.Session;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import scau.zxck.base.exception.BaseException;
import scau.zxck.entity.market.User;
import scau.zxck.service.market.IUserService;
import scau.zxck.utils.ReadJson;
import scau.zxck.utils.WriteJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@Controller
@RequestMapping("/")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/spring.xml","classpath:config/spring/web/spring-mvc.xml"})
public class ChargeAction {
    @Autowired
    private IUserService iUserService;
    @Autowired
    HttpServletRequest request;
    @Autowired
    HttpSession session;
    @RequestMapping(value = "recharge",method = RequestMethod.POST)
    public void recharge(HttpServletResponse response)throws IOException,BaseException {
        JSONObject data=ReadJson.readJson(request);
        String user_id=session.getAttribute("User_Id").toString();
        String gold=data.get("Gold").toString();
       // System.out.println(gold+"===========");
        User user=iUserService.findOne(user_id);
        user.setGold(user.getGold()+Double.parseDouble(gold));
        JSONObject temp=new JSONObject();
        try {
            iUserService.updateUser(user);
            temp.put("status","1");
            temp.put("data","");
        }catch (Exception e){
            e.printStackTrace();
            temp.put("status",0);
            temp.put("data","");
        }
        WriteJson.writeJson(response,temp.toJSONString());
    }
}
