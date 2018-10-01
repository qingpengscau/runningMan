package scau.zxck.web.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.converter.json.GsonBuilderUtils;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.entity.market.Receiver;
import scau.zxck.entity.market.User;
import scau.zxck.service.market.IReceiverService;
import scau.zxck.service.market.IUserService;
import scau.zxck.serviceImpl.market.UserService;
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
public class DeliverAddressAction {
    @Autowired
    private IUserService userService;
    @Autowired
    private IReceiverService receiverService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;


    @RequestMapping(value = "updateDeliverAddress",method =RequestMethod.POST )
    public void updateDeliverAddress(HttpServletResponse response) throws BaseException,IOException {
        BufferedReader br=request.getReader();
        String str,whoStr="";
        while((str=br.readLine())!=null){
            whoStr+=str;
        }
        JSONObject data=JSON.parseObject(whoStr);
        JSONObject temp=new JSONObject();
        String name = data.get("Name").toString();
        String cell = data.get("cell").toString();
        String area = data.get("Area").toString();
        String detail_area = data.get("Detail_Area").toString();
        String address_id = data.get("Address_Id").toString();
        String r = "";
        try {
            Receiver receiver = new Receiver();
            receiver.setDeliver_address(detail_area);
            receiver.setReceiver_cell(cell);
            receiver.setReceiver_name(name);
            String id = session.getAttribute("id").toString();
            receiver.setUser_id(id);
            User user = userService.findOne(id);
            int sex = user.getUser_sex();
            receiver.setReceiver_sex(sex);
            receiver.setReceiver_area(area);
            receiver.setId(address_id);
            receiverService.updateReceiver(receiver);
            temp.put("status","1");
            temp.put("data",receiver.getId());
            r=temp.toJSONString();
        }catch (Exception e){
            temp.put("status","0");
            temp.put("data","");
            r=temp.toJSONString();
            e.printStackTrace();
        }finally {
            WriteJson.writeJson(response,r);
        }

    }
    @RequestMapping(value = "addDeliverAddress",method =RequestMethod.POST )
    public void addDeliverAddress(HttpServletResponse response) throws BaseException,IOException {
        BufferedReader br=request.getReader();
        String str,whoStr="";
        while((str=br.readLine())!=null){
            whoStr+=str;
        }
        JSONObject data=JSON.parseObject(whoStr);
        JSONObject temp=new JSONObject();
        String name = data.get("Name").toString();
        String cell = data.get("cell").toString();
        String area = data.get("Area").toString();
        String detail_area = data.get("Detail_Area").toString();
        String r = "";
        try {
            Receiver receiver = new Receiver();
            receiver.setDeliver_address(detail_area);
            receiver.setReceiver_cell(cell);
            receiver.setReceiver_name(name);
            String id = session.getAttribute("id").toString();
            receiver.setUser_id(id);
            User user = userService.findOne(id);
            int sex = user.getUser_sex();
            receiver.setReceiver_sex(sex);
            receiver.setReceiver_area(area);
            receiverService.addReceiver(receiver);
            temp.put("status","1");
            temp.put("data",receiver.getId());
            r=temp.toJSONString();
        }catch (Exception e){
            temp.put("status","0");
            temp.put("data","");
            r=temp.toJSONString();
            e.printStackTrace();
        }finally {
            WriteJson.writeJson(response,r);
        }
    }
    @RequestMapping(value = "deleteDeliverAddress",method =RequestMethod.POST )
    public void deleteDeliverAddress(HttpServletResponse response) throws BaseException,IOException {
        BufferedReader br=request.getReader();
        String str,whoStr="";
        while((str=br.readLine())!=null){
            whoStr+=str;
        }
        JSONObject data=JSON.parseObject(whoStr);
        JSONObject temp=new JSONObject();
        String id = data.get("Address_Id").toString();
        String r = "";
        try {
            receiverService.deleteReceiver(id);
            temp.put("status","1");
            temp.put("data",null);
            r=temp.toJSONString();
        }catch (Exception e){
            temp.put("status","0");
            temp.put("data","");
            r=temp.toJSONString();
            e.printStackTrace();
        }finally {
            WriteJson.writeJson(response,r);
        }
    }
    @RequestMapping(value = "getDeliverAddress",method =RequestMethod.POST )
    public void getDeliverAddress(HttpServletResponse response) throws BaseException,IOException {
        BufferedReader br=request.getReader();
        String str,whoStr="";
        while((str=br.readLine())!=null){
            whoStr+=str;
        }
        JSONObject data=JSON.parseObject(whoStr);
        JSONObject temp=new JSONObject();

        String r = "";
        try {
            String id = session.getAttribute("id").toString();
            Conditions conditions = new Conditions();
            List<Receiver> addresses = receiverService.getAddress(conditions.eq("user_id",id));
            JSONArray s=new JSONArray();
            for(Receiver address: addresses){
                JSONObject tp=new JSONObject();
                tp.put("addressId",address.getId());
                tp.put("name",address.getReceiver_name());
                tp.put("cell",address.getReceiver_cell());
                tp.put("area",address.getReceiver_area());
                tp.put("detailArea",address.getDeliver_address());
                s.add(tp);
            }
            temp.put("status","1");
            temp.put("data",s);
            r=temp.toJSONString();
        }catch (Exception e){
            temp.put("status","0");
            temp.put("data","");
            r=temp.toJSONString();
            e.printStackTrace();
        }finally {
            WriteJson.writeJson(response,r);
        }
    }
}
