package scau.zxck.web.admin;

import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.entity.market.User;
import scau.zxck.service.market.IUserService;
import scau.zxck.utils.ReadJson;
import scau.zxck.utils.WriteJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping(value = "/")
public class UserInfoAction {
    @Autowired
    HttpServletRequest request;
    @Autowired
    IUserService iUserService;
    @Autowired
    HttpSession session;
    @RequestMapping(value = "updatePassword",method = RequestMethod.POST)
    public void updatePassword(HttpServletResponse response)throws BaseException, java.io.IOException {
        JSONObject data=ReadJson.readJson(request);
        Conditions conditions=new Conditions();
        List<User> list=iUserService.listUser(conditions.eq("user_cell",data.get("User_Cell").toString()));
        JSONObject temp=new JSONObject();
        if(list!=null){
            try{
                User user=list.get(0);
                user.setUser_password(data.get("User_Password").toString());
                iUserService.updateUser(user);
                temp.put("data","");
                temp.put("status","1");
            }catch (Exception e){
                temp.put("data","");
                temp.put("status","0");
                e.printStackTrace();
            }
        }else{
            temp.put("data","");
            temp.put("status","0");
        }
        String r=temp.toJSONString();
        WriteJson.writeJson(response,r);
    }
    @RequestMapping(value = "getUserInfo",method = RequestMethod.POST)
    public void getUserInfo(HttpServletResponse response)throws BaseException, java.io.IOException{
            JSONObject data=ReadJson.readJson(request);
            Conditions conditions=new Conditions();
            List<User>list=iUserService.listUser(conditions.eq("id",session.getAttribute("User_Id")));
            JSONObject temp=new JSONObject();
            JSONObject reJson=new JSONObject();
            if(list!=null){
                User user=list.get(0);
                temp.put("name",user.getUser_name());
                temp.put("sex",user.getUser_sex());
                temp.put("address",user.getLocation());
                temp.put("money",user.getGold());
                temp.put("cell",user.getUser_cell());
                //image path
                reJson.put("status","1");
                reJson.put("data",temp);
            }else{
                reJson.put("status","0");
                reJson.put("data","");
            }
            WriteJson.writeJson(response,reJson.toJSONString());
    }

    public void deleteUserInfo(HttpServletResponse response){

    }

    @RequestMapping(value = "updateUserInfo",method = RequestMethod.POST)
    public void updateUserInfo(HttpServletResponse response)throws BaseException, java.io.IOException{
        JSONObject data=ReadJson.readJson(request);
        Conditions conditions=new Conditions();
        List<User> list=iUserService.listUser(conditions.eq("id",session.getAttribute("user_id")));
        JSONObject temp=new JSONObject();
        if(list!=null){
            try{
                User user=list.get(0);
                user.setUser_name(data.get("User_Name").toString());
                user.setGold(Integer.parseInt(data.get("Gold").toString()));
                user.setLocation(data.get("Location").toString());
                user.setUser_sex(Integer.parseInt(data.get("User_Sex").toString()));
                iUserService.updateUser(user);
                temp.put("data","");
                temp.put("status","1");
            }catch (Exception e){
                temp.put("data","");
                temp.put("status","0");
                e.printStackTrace();
            }
        }else{
            temp.put("data","");
            temp.put("status","0");
        }
        String r=temp.toJSONString();
        WriteJson.writeJson(response,r);
    }
}
