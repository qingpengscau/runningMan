package scau.zxck.web.admin;
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
import scau.zxck.entity.market.Secure;
import scau.zxck.entity.market.User;
import scau.zxck.service.market.IUserService;
import scau.zxck.service.market.ISecureService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;

import scau.zxck.utils.WriteJson;
import scau.zxck.utils.ReadJson;

@Controller
@RequestMapping("/")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:config/spring/spring.xml")
public class RegisterAction {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private ISecureService iSecureService;
    @Autowired
    HttpServletRequest request;
    @RequestMapping(value="register",method = RequestMethod.POST)
    public void register(HttpServletResponse response)throws IOException,BaseException{
        JSONObject data=ReadJson.readJson(request);
        String cell=data.get("User_Cell").toString();
        String password=data.get("User_Password").toString();
        User user=new User();
        user.setUser_cell(cell);
        user.setUser_password(password);
        user.setGold(0);
        user.setUser_sex(2);
        user.setLocation("");
        user.setUser_name("");
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date=simpleDateFormat.format(new Date(System.currentTimeMillis()));
        user.setUser_regtime(date);
        String userId=iUserService.addUser(user);

        Secure secure=new Secure();
        secure.setUser_id(userId);
        secure.setSecure_question(data.get("Secure_Question1").toString());
        secure.setSecure_answer(data.get("Secure_Answer1").toString());

        iSecureService.addSecure(secure);

        secure.setSecure_question(data.get("Secure_Question2").toString());
        secure.setSecure_answer(data.get("Secure_Answer2").toString());

        JSONObject temp=new JSONObject();
        JSONObject temp1=new JSONObject();
        String r="";
        try {
            iSecureService.addSecure(secure);
            temp.put("status","1");
            temp.put("data","");
        }catch (Exception e){
            temp.put("status","0");
            temp.put("data","");
        }
        r=temp.toJSONString();
            WriteJson.writeJson(response,r);
        //return value
    }
    @RequestMapping(value = "valiateAccount",method = RequestMethod.POST)
    public void valiateAccount (HttpServletResponse response)throws IOException,BaseException{
            JSONObject data=ReadJson.readJson(request);
            String cell=data.get("User_Cell").toString();
            Conditions conditions=new Conditions();
            List<User>list=iUserService.listUser(conditions.eq("user_cell",cell));
        JSONObject temp=new JSONObject();
        String r="";
            if(list!=null){
                temp.put("status","0");
                temp.put("data","");

            }else{
                temp.put("status","1");
                temp.put("data","");
            }
            r=temp.toJSONString();
            WriteJson.writeJson(response,r);
    }
}
