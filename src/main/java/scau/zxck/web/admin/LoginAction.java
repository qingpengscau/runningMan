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
import scau.zxck.entity.market.User;
import scau.zxck.service.market.IUserService;
import scau.zxck.utils.HtmlRegexpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Condition;

@Controller
@RequestMapping("/")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring/spring.xml")
public class LoginAction{
    @Autowired
    private IUserService iUserService;
    @Autowired
    private HttpServletRequest request;

    @Test
    @RequestMapping(value = "login",method =RequestMethod.POST )
    public void login() throws BaseException,IOException {
//        BufferedReader br=request.getReader();
//        String str,whoStr="";
//        while((str=br.readLine())!=null){
//            whoStr+=str;
//        }
//        JSONObject data=JSON.parseObject(whoStr);
//        String name=data.get("User_Name").toString();
//        String password=data.get("User_Password").toString();
//        String cell=data.get("User_Cell").toString();
        String name="qingpeng";
        String password="123456";
        String cell="1";
        Conditions conditions=new Conditions();
       try{
           List<User> list=iUserService.listUser(conditions.eq("user_password",password).and().leftBracket()
                   .eq("user_name",name).or().eq("user_cell",cell).rightBracket());
           System.out.print(conditions.eq("user_password",password).and().leftBracket()
                   .eq("user_name",name).or().eq("user_cell",cell).rightBracket().toWhereSQL());
           User user=list.get(0);
           System.out.println(user.getLocation()+".............");
       }catch (Exception e){
           e.printStackTrace();
       }



    }
}
