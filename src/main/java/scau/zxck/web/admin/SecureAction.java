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
import scau.zxck.service.market.ISecureService;
import scau.zxck.entity.market.User;
import scau.zxck.service.market.IUserService;
import scau.zxck.utils.HtmlRegexpUtil;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.Condition;
import scau.zxck.utils.ReadJson;
import scau.zxck.utils.WriteJson;
@Controller
@RequestMapping(value = "/")
public class SecureAction {
    @Autowired
    private ISecureService iSecureService;
    @Autowired
    private  IUserService iUserService;
    @Autowired
    private
    HttpServletRequest request;

    @RequestMapping(value = "getSecureQuestion",method = RequestMethod.POST)
    public void getSecureQuestion(HttpServletResponse response) throws IOException,BaseException {
            JSONObject data=ReadJson.readJson(request);
            Conditions conditions=new Conditions();
            List<User>list=iUserService.listUser(conditions.eq("user_cell",data.get("User_Cell").toString()));
            JSONObject temp =new JSONObject();
            JSONObject temp1=new JSONObject();
            String r="";
            if(list.get(0)!=null){
                conditions=new Conditions();
                List<Secure>list1=iSecureService.listSecure(conditions.eq("id",list.get(0).getId()));

                Secure secure0=list1.get(0);
                Secure secure1=list1.get(1);

                temp.put("status","1");

                temp1.put("question1",secure0.getSecure_question());
                temp1.put("answer1",secure0.getSecure_answer());
                temp1.put("question2",secure1.getSecure_question());
                temp1.put("answer2",secure1.getSecure_answer());

                temp.put("data",temp1);

                r=temp.toJSONString();
            }else{
                temp.put("status","0");
                temp.put("data","");
            }
        WriteJson.writeJson(response,r);
    }
}
