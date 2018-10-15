package scau.zxck.web.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import com.google.gson.JsonArray;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;

import scau.zxck.entity.market.Address;
import scau.zxck.entity.market.Fetchorder;
import scau.zxck.entity.market.ShoppingOrder;
import scau.zxck.entity.market.User;
import scau.zxck.service.market.IAddressService;
import scau.zxck.service.market.IFetchOrderService;
import scau.zxck.service.market.IUserService;
import scau.zxck.serviceImpl.market.FetchOrderService;
import scau.zxck.utils.ReadJSONUtil;
import scau.zxck.utils.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Condition;

@Controller
@RequestMapping("/")
@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {"classpath:config/spring/spring.xml", "classpath:config/spring/web/spring-mvc.xml"})
public class FetchOrderInfoAction {
    @Autowired
   private  IFetchOrderService fetchOrderService;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;
    @Autowired
    private IUserService iUserService;
 @RequestMapping(value = "addFetchOrder", method = RequestMethod.POST)
    public void addFetchOrder(HttpServletResponse response) throws Exception {

     request.setCharacterEncoding("utf-8");
     JSONObject data = ReadJSONUtil.readJSONStr(request);

     String Departure_id = data.get("Departure_Id").toString();
     String Destination_id = data.get("Destination_Id").toString();
     String Pick_time = data.get("Pick_Time").toString();
     String Prewait_Time = data.get("Prewait_Time").toString();

     String Type_Weight = data.get("Type_Weight").toString();
     String Comment = data.get("Comment").toString();
     String Fee = data.get("Fee").toString();

       Fetchorder fetchorder=new Fetchorder();
       SimpleDateFormat sf=new SimpleDateFormat("MM-dd HH:mm");
       fetchorder.setDeparture_id(Departure_id);
       fetchorder.setDestination_id(Destination_id);
       fetchorder.setPick_time(Pick_time);
       fetchorder.setPrewait_time(Prewait_Time);
       fetchorder.setType_weight(Type_Weight);
       fetchorder.setComment(Comment);
       fetchorder.setFee(Double.parseDouble(Fee));
       fetchorder.setRelease_man_id(String.valueOf(session.getAttribute("User_Id")));
       fetchorder.setState(0);
       fetchorder.setRelease_time(sf.format(new Date()));

         fetchOrderService.addFetchorder(fetchorder);


     JSONObject jsonObject=new JSONObject();
     jsonObject.put("status","1");
     jsonObject.put("data","");
     String s =jsonObject.toJSONString();
     PrintWriter writer = response.getWriter();
     writer.write(s);
     writer.flush();

 }

    @RequestMapping(value = "getAllFetchingOrder", method = RequestMethod.POST)
    public void getAllFetchingOrder(HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);
       String User_Id=session.getAttribute("User_Id").toString();
        Conditions conditions=new Conditions();
        List<Fetchorder> fetchorders = fetchOrderService.listFetchorder(conditions.eq("release_man_id",User_Id));
        JSONArray temp=new JSONArray();
        Address address=null;
        for(int i=0;i<fetchorders.size();i++)
     {
         JSONObject json=new JSONObject();
         json.put("orderId",fetchorders.get(i).getId());
         String departure_id=fetchorders.get(i).getDeparture_id();

         Address address1=addressService.findByid(departure_id);
         json.put("departure",address1.getArea()+"  "+address1.getAddress());

         address = addressService.findByid(fetchorders.get(i).getDestination_id());
         json.put("destination",address.getArea()+"  "+address.getAddress());


         json.put("receiveMan",address.getName());
         json.put("typeWeight",fetchorders.get(i).getType_weight());
         json.put("pickTime",fetchorders.get(i).getPick_time());
         json.put("preWaitTime",fetchorders.get(i).getPrewait_time());
         json.put("comment",fetchorders.get(i).getComment());
         json.put("fee",fetchorders.get(i).getFee());
         json.put("releaseTime",fetchorders.get(i).getRelease_time());
         json.put("status",1);
         json.put("fee",fetchorders.get(i).getFee());
         Date now = new Date();
         SimpleDateFormat nowTime = new SimpleDateFormat("MM-dd HH-mm");
         String currentTime = nowTime.format(now);
         if(fetchorders.get(i).getState()!=3) {
             if (TimeUtil.isFirstTimeEarly(fetchorders.get(i).getPrewait_time(), currentTime)) {
                 fetchorders.get(i).setState(3);
                fetchOrderService.updateFetchOrder(fetchorders.get(i));
             }
         }
         json.put("state",fetchorders.get(i).getState());


         temp.add(json);
     }
     JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data",temp);

        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();
    }

    @RequestMapping(value = "isFetchOrderAccepted", method = RequestMethod.POST)
    public void isFetchOrderAccepted(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);
        String Order_Id = data.get("Order_Id").toString();
        Conditions conditions=new Conditions();
        JSONObject temp = new JSONObject();
        try {
            Fetchorder fetchorder = fetchOrderService.findByid(Order_Id);
            JSONObject result = new JSONObject();
            if (fetchorder.getState() == 0) {
                result.put("isAccepted", "0");
            } else if (fetchorder.getState() == 1) {
                result.put("isAccepted", "1");
            } else if (fetchorder.getState() == 2) {
                result.put("isAccepted", "2");
            }

            temp.put("status", "1");
            temp.put("data", result);
        }catch (Exception e){
            e.printStackTrace();
            temp.put("status","1");
            JSONObject result=new JSONObject();
            result.put("isAccepted","3");
            temp.put("data",result);
        }

        PrintWriter writer = response.getWriter();
        writer.write(temp.toString());
        writer.flush();
    }
    @RequestMapping(value = "updateFetchOrder", method = RequestMethod.POST)
    public void updateFetchOrder(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);

        String Order_Id = data.get("Order_Id").toString();
        String Departure_id = data.get("Departure_Id").toString();
        String Destination_id = data.get("Destination_Id").toString();
        String Pick_time = data.get("Pick_time").toString();
        String Prewait_Time = data.get("Prewait_Time").toString();
        String Type = data.get("Type").toString();
        String Type_Weight = data.get("Type_Weigh").toString();
        String Comment = data.get("Comment").toString();
        String Fee = data.get("Fee").toString();

        Fetchorder fetchorder = fetchOrderService.findByid(Order_Id);

        fetchorder.setDeparture_id(Departure_id);
        fetchorder.setDestination_id(Destination_id);
        fetchorder.setPick_time(Pick_time);
        fetchorder.setPrewait_time("");
        fetchorder.setType_weight(Type_Weight);
        fetchorder.setComment(Comment);
        fetchorder.setFee(Double.parseDouble(Fee));
        fetchOrderService.updateFetchOrder(fetchorder);
        JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data","");
        System.out.println(result.toString());
        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();



    }

    @RequestMapping(value = "deleteFetchingOrder", method = RequestMethod.POST)
    public void deleteFetchingOrder(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);

        String Order_Id=data.get("Order_Id").toString();
        fetchOrderService.deleteFetchorder(Order_Id);

        JSONObject result=new JSONObject();
        result.put("data","");
        result.put("status","1");
        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();
    }

    @RequestMapping(value = "enableFetchingOrder", method = RequestMethod.POST)
    public void enableFetchingOrder(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);

        String Order_Id = data.get("Order_Id").toString();
        long Prewait_Time = Long.parseLong(data.get("Prewait_Time").toString());
        SimpleDateFormat sf=new SimpleDateFormat("MM-dd HH:mm");
        Fetchorder fetchorder = fetchOrderService.findByid(Order_Id);
        fetchorder.setPrewait_time(sf.format(new Date(Prewait_Time*60*1000+new Date().getTime())));
        fetchorder.setState(0);
        fetchOrderService.updateFetchOrder(fetchorder);

        JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data","");
        System.out.println(result.toString());
        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();

    }

    @RequestMapping(value = "getFetchingPlaceOrder", method = RequestMethod.POST)
    public void getFetchingPlaceOrder(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);

        String place=data.get("Place").toString();
        Conditions conditions=new Conditions();
        List<Fetchorder> fetchorders=fetchOrderService.listFetchorder(conditions.eq("state",0));
        Conditions conditions1=new Conditions();
        List<Address>addressList=addressService.listReceiver(conditions1.eq("area",place));
        System.out.println(place);
        List<String>address_ids=new ArrayList<String>();
        for(int i=0;i<addressList.size();i++){
            address_ids.add(addressList.get(i).getId());
        }
        JSONArray temp=new JSONArray();
        Address address=null;
        Address departure=null;
        for(Fetchorder e:fetchorders){
            for(String w:address_ids){
                System.out.println(w);
                if(e.getDestination_id().equals(w)){
                    JSONObject json=new JSONObject();
                    json.put("orderId",e.getId());
                    departure=addressService.findByid(e.getDeparture_id());

                    json.put("departure",departure.getArea()+"  "+departure.getAddress());
                    address = addressService.findByid(w);
                    json.put("destination",address.getArea()+"  "+address.getAddress());

                    json.put("typeWeight",e.getType_weight());
                    json.put("receiveMan",address.getName());
                    json.put("pickTime",e.getPick_time());
                    json.put("preWaitTime",e.getPrewait_time());
                    json.put("comment",e.getComment());
                    json.put("fee",e.getFee());
                    json.put("releaseTime",e.getRelease_time());
                    json.put("status",1);
                    json.put("state",e.getState());
                    temp.add(json);
                }
            }
        }
        JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data",temp);

        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();
    }

    @RequestMapping(value = "acceptFetchingOrder", method = RequestMethod.POST)
    public void acceptFetchingOrder(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);

        String Order_Id = data.get("Order_Id").toString();
        Fetchorder fetchorder = fetchOrderService.findByid(Order_Id);
        fetchorder.setState(1);
        fetchorder.setExecute_man_id(session.getAttribute("User_Id").toString());
        fetchOrderService.updateFetchOrder(fetchorder);

        JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data","");
        System.out.println(result.toString());
        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();

    }

    @RequestMapping(value = "getFetchingAcceptedOrder", method = RequestMethod.POST)
    public void getFetchingAcceptedOrder(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);
        String Usre_Id =session.getAttribute("User_Id").toString();
        Conditions conditions=new Conditions();

        conditions.eq("execute_man_id",Usre_Id);
        List<Fetchorder> fetchorders = fetchOrderService.listFetchorder(conditions);
        JSONArray temp=new JSONArray();
        Address address=null;
        for(int i=0;i<fetchorders.size();i++)
        {
            JSONObject json=new JSONObject();
            json.put("orderId",fetchorders.get(i).getId());
            address=addressService.findByid(fetchorders.get(i).getDeparture_id());
            json.put("departure",address.getArea()+"  "+address.getAddress());
            address = addressService.findByid(fetchorders.get(i).getDestination_id());
            json.put("destination",address.getArea()+"  "+address.getAddress());

            json.put("receiveMan",address.getName());
            json.put("typeWeight",fetchorders.get(i).getType_weight());
            json.put("pickTime",fetchorders.get(i).getPick_time());
            json.put("preWaitTime",fetchorders.get(i).getPrewait_time());
            json.put("comment",fetchorders.get(i).getComment());
            json.put("fee",fetchorders.get(i).getFee());
            json.put("releaseTime",fetchorders.get(i).getRelease_time());
            json.put("status",1);
            json.put("fee",fetchorders.get(i).getFee());
            json.put("state",fetchorders.get(i).getState());


            temp.add(json);
        }
        JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data",temp);

        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();
    }
    @RequestMapping(value = "finishFetchingOrder", method = RequestMethod.POST)
    public void finishFetchingOrder(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);

        String Order_Id = data.get("Order_Id").toString();
        Fetchorder fetchorder = fetchOrderService.findByid(Order_Id);
        fetchorder.setState(2);
        fetchOrderService.updateFetchOrder(fetchorder);
        User release_man=iUserService.findOne(fetchorder.getRelease_man_id());
        User excute_man=iUserService.findOne(fetchorder.getExecute_man_id());
        double fee=fetchorder.getFee();
        release_man.setGold(release_man.getGold()-fee);
        iUserService.updateUser(release_man);

        excute_man.setGold(excute_man.getGold()+fee);
        iUserService.updateUser(excute_man);
        JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data","");
        System.out.println(result.toString());
        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();

    }

}
