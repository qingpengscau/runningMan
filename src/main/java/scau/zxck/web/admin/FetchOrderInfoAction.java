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
import scau.zxck.service.market.IAddressService;
import scau.zxck.service.market.IFetchOrderService;
import scau.zxck.serviceImpl.market.FetchOrderService;
import scau.zxck.utils.ReadJSONUtil;

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

 @RequestMapping(value = "addFetchOrder", method = RequestMethod.POST)
    public void addFetchOrder(HttpServletResponse response) throws Exception {

     request.setCharacterEncoding("utf-8");
     JSONObject data = ReadJSONUtil.readJSONStr(request);

     String Departure_id = data.get("Departure_Id").toString();
     String Destination_id = data.get("Destination_Id").toString();
     String Pick_time = data.get("Pick_time").toString();
     String Prewait_Time = data.get("Prewait_Time").toString();
     String Type = data.get("Type").toString();
     String Weight = data.get("Weight").toString();
     String Comment = data.get("Comment").toString();
     String Fee = data.get("Fee").toString();

       Fetchorder fetchorder=new Fetchorder();
       SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       fetchorder.setDeparture_id(Departure_id);
       fetchorder.setDestination_id(Destination_id);
       fetchorder.setPick_time(Pick_time);
       fetchorder.setPrewait_time(Integer.parseInt(Prewait_Time));
       fetchorder.setType(Integer.parseInt(Type));
       fetchorder.setWeight(Double.parseDouble(Weight));
       fetchorder.setComment(Comment);
       fetchorder.setFee(Double.parseDouble(Fee));
       fetchorder.setRelease_man_id(String.valueOf(session.getAttribute("User_Id")));
       fetchorder.setStatus(0);
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
         json.put("departure",fetchorders.get(i).getDeparture_id());
         address = addressService.findByid(fetchorders.get(i).getDeparture_id());
         json.put("destination",address.getAddress());


         json.put("receiveMan",address.getName());
         json.put("weight",fetchorders.get(i).getWeight());
         json.put("pickTime",fetchorders.get(i).getPick_time());
         json.put("preWaitTime",fetchorders.get(i).getPrewait_time());
         json.put("comment",fetchorders.get(i).getComment());
         json.put("fee",fetchorders.get(i).getFee());
         json.put("releaseTime",fetchorders.get(i).getRelease_time());
         json.put("status",1);
         json.put("fee",fetchorders.get(i).getFee());
         json.put("state",fetchorders.get(i).getStatus());


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
        Fetchorder fetchorder = fetchOrderService.findByid(Order_Id);
        JSONObject result=new JSONObject();
        if(fetchorder.getStatus()>0)
        {
           result.put("isAccepted","1");
        }
        else
        {
            result.put("isAccepted","0");
        }
        result.put("status","1");

        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
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
        String Weight = data.get("Weight").toString();
        String Comment = data.get("Comment").toString();
        String Fee = data.get("Fee").toString();

        Fetchorder fetchorder = fetchOrderService.findByid(Order_Id);

        fetchorder.setDeparture_id(Departure_id);
        fetchorder.setDestination_id(Destination_id);
        fetchorder.setPick_time(Pick_time);
        fetchorder.setPrewait_time(Integer.parseInt(Prewait_Time));
        fetchorder.setType(Integer.parseInt(Type));
        fetchorder.setWeight(Double.parseDouble(Weight));
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
        result.put("status","");
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
        String Prewait_Time = data.get("Prewait_Time").toString();

        Fetchorder fetchorder = fetchOrderService.findByid(Order_Id);
        fetchorder.setPrewait_time(Integer.parseInt(Prewait_Time));
        fetchorder.setStatus(0);
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

        String Place=data.get("Place").toString();
        List<Address> addresses = addressService.listReceiver(new Conditions().eq("address", Place));
        String[] addresse_ids = new String[100];
        for(int i=0;i<addresses.size();i++)
       {
           addresse_ids[i]=addresses.get(i).getId();
       }
        Conditions conditions=new Conditions();
        conditions.in("departure_id",addresse_ids).and().eq("status","0");
        List<Fetchorder> fetchorders = fetchOrderService.listFetchorder(conditions.and().eq("status","0"));
         Address address=null;
        JSONArray temp=new JSONArray();
        for(int i=0;i<fetchorders.size();i++)
        {
            JSONObject json=new JSONObject();
            json.put("orderId",fetchorders.get(i).getId());
            json.put("departure",fetchorders.get(i).getDeparture_id());
            address = addressService.findByid(fetchorders.get(i).getDeparture_id());
            json.put("destination",address.getAddress());

            json.put("receiveMan",address.getName());
            json.put("weight",fetchorders.get(i).getWeight());
            json.put("pickTime",fetchorders.get(i).getPick_time());
            json.put("preWaitTime",fetchorders.get(i).getPrewait_time());
            json.put("comment",fetchorders.get(i).getComment());
            json.put("fee",fetchorders.get(i).getFee());
            json.put("releaseTime",fetchorders.get(i).getRelease_time());
            json.put("status",1);
            json.put("fee",fetchorders.get(i).getFee());
            json.put("state",fetchorders.get(i).getStatus());


            temp.add(json);
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
        fetchorder.setStatus(1);
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
        String Usre_Id =session.getAttribute("Usre_Id").toString();
        Conditions conditions=new Conditions();

        conditions.eq("execute_man_id",Usre_Id);
        List<Fetchorder> fetchorders = fetchOrderService.listFetchorder(conditions);
        JSONArray temp=new JSONArray();
        Address address=null;
        for(int i=0;i<fetchorders.size();i++)
        {
            JSONObject json=new JSONObject();
            json.put("orderId",fetchorders.get(i).getId());
            json.put("departure",fetchorders.get(i).getDeparture_id());
            address = addressService.findByid(fetchorders.get(i).getDeparture_id());
            json.put("destination",address.getAddress());

            json.put("receiveMan",address.getName());
            json.put("weight",fetchorders.get(i).getWeight());
            json.put("pickTime",fetchorders.get(i).getPick_time());
            json.put("preWaitTime",fetchorders.get(i).getPrewait_time());
            json.put("comment",fetchorders.get(i).getComment());
            json.put("fee",fetchorders.get(i).getFee());
            json.put("releaseTime",fetchorders.get(i).getRelease_time());
            json.put("status",1);
            json.put("fee",fetchorders.get(i).getFee());
            json.put("state",fetchorders.get(i).getStatus());


            temp.add(json);
        }
        JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data",temp);

        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();
    }


}
