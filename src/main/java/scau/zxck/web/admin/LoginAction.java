package scau.zxck.web.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.entity.market.User;
import scau.zxck.service.market.IUserService;
import scau.zxck.utils.JwtUtil;
import scau.zxck.utils.WriteJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/spring.xml"})
public class LoginAction{
    @Autowired
    private IUserService iUserService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;
        @RequestMapping(value = "login",method =RequestMethod.POST )
        public void login(HttpServletResponse response) throws BaseException,IOException {
            BufferedReader br=request.getReader();
            String str,whoStr="";
            while((str=br.readLine())!=null){
                whoStr+=str;
            }
            System.out.println(whoStr);
            JSONObject data=JSON.parseObject(whoStr);
        String name="";
        if(data.get("User_Name")!=null)
        name=data.get("User_Name").toString();
        String password=data.get("User_Password").toString();
        String cell="";
        if(data.get("User_Cell")!=null)
        cell=data.get("User_Cell").toString();
//            String cell=request.getParameter("User_Cell");
//            String password=request.getParameter("User_Password");
//            System.out.println(cell+"===================="+password);
        Conditions conditions=new Conditions();
        JSONObject temp=new JSONObject();
        JSONObject temp1=new JSONObject();
        String r="";
       try{
           List<User> list=iUserService.listUser(conditions.eq("user_password",password).and().leftBracket().eq("user_name",name).or()
                   .eq("user_cell",cell).rightBracket());
//           System.out.print(conditions.eq("user_password",password).and().leftBracket()
//                   .eq("user_name",name).or().eq("user_cell",cell).rightBracket().toWhereSQL());
           User user=list.get(0);

           temp.put("status","1");

           JwtUtil jwtUtil=new JwtUtil();
           temp1.put("token",jwtUtil.createJWT(user.getId(),18000000));//5 hour
           temp.put("data",temp1);

           r=temp.toJSONString();

       }catch (Exception e){
           temp.put("status","0");
           temp.put("data","");
           r=temp.toJSONString();
       }
       finally {
           WriteJson.writeJson(response,r);
       }



    }
}
