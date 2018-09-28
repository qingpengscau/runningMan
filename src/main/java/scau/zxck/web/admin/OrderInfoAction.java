package scau.zxck.web.admin;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import scau.zxck.base.exception.BaseException;

import scau.zxck.entity.market.Fetchorder;
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

@Controller
@RequestMapping("/")
/*@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {"classpath:config/spring/spring.xml", "classpath:config/spring/web/spring-mvc.xml"})*/
public class OrderInfoAction {
    @Autowired
   private  IFetchOrderService fetchOrderService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;

 @RequestMapping(value = "addFetchOrder", method = RequestMethod.POST)
    public void addFetchOrder(HttpServletResponse response) throws Exception {

     request.setCharacterEncoding("utf-8");
     JSONObject data = ReadJSONUtil.readJSONStr(request);

     String Departure = data.get("Departure").toString();
     String Destination = data.get("Destination").toString();
     String Receive_man = data.get("Receive_man").toString();
     String Excute_Man = data.get("Excute_Man").toString();
     String Type = data.get("Type").toString();
     String Weight = data.get("Weight").toString();
     String Pick_Time = data.get("Pick_Time").toString();
     String Pre_Wait_Time = data.get("Pre_Wait_Time").toString();
     String Comment = data.get("Comment").toString();
     String Fee = data.get("Fee").toString();
     String Issue_descri = data.get("Issue_descri").toString();
     String Status = data.get("Status").toString();
       Fetchorder fetchorder=new Fetchorder();
     SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        fetchorder.setRelease_man_id("1");
        fetchorder.setReceive_man_id("1");
        fetchorder.setRelease_time(sf.format(new Date()));
        fetchorder.setDeparture(Departure);
        fetchorder.setDestination(Destination);
        fetchorder.setType(Integer.parseInt(Type));
        fetchorder.setFee(Integer.parseInt(Fee));
        fetchorder.setIssue_descri(Issue_descri);
        fetchorder.setStatus(Integer.parseInt(Status));
        fetchorder.setRemark(0);
         fetchOrderService.addFetchorder(fetchorder);

     Gson gson=new Gson();

     JSONObject jsonObject=new JSONObject();
     jsonObject.put("status","0");
     jsonObject.put("data",gson.toJson(fetchorder));
     String s =jsonObject.toJSONString();
     PrintWriter writer = response.getWriter();
     writer.write(s);
     writer.flush();

 }
    @RequestMapping(value = "addShoppingOrder", method = RequestMethod.POST)
    public void addShoppingOrder(HttpServletResponse response)throws IOException, BaseException{
                  String departure=request.getParameter("departure");
                  System.out.println(departure);
    }
    @RequestMapping(value = "getAllOrder", method = RequestMethod.POST)
    public void getAllOrder(HttpServletResponse response) throws IOException, BaseException {



           List list=  fetchOrderService.listAll();

          List<Fetchorder> fetchorders=new ArrayList<>();

        for (Iterator iter = ((java.util.List) list).iterator(); iter.hasNext(); )
        {
            Fetchorder fetchorder= (Fetchorder) iter.next();
            fetchorders.add(fetchorder);
        }



        Gson gson=new Gson();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status","1");
        jsonObject.put("data",gson.toJson(fetchorders));

        PrintWriter writer = response.getWriter();
        writer.write(jsonObject.toString());
        writer.flush();


    }
    @RequestMapping(value = "getOneOrder", method = RequestMethod.POST)
    public void getOneOrder(HttpServletResponse response)throws Exception{
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);
        //String Order_Id=data.get("Order_Id").toString();
        Fetchorder fetchorder = fetchOrderService.findByid("d1bd0cfd76494ee8849a2cac82b706a1");
        Gson gson=new Gson();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status","1");
        jsonObject.put("data",gson.toJson(fetchorder));

        PrintWriter writer = response.getWriter();
        writer.write(jsonObject.toString());
        writer.flush();
    }
    @RequestMapping(value = "updateFetchOrder", method = RequestMethod.POST)
    public void updateFetchOrder(HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);

       String Departure = data.get("DeliverAdress").toString();
        String Destination = data.get("Destination").toString();
        String Receive_man = data.get("Receive_man").toString();
        String Excute_Man = data.get("Excute_Man").toString();
        String Type = data.get("Type").toString();
        String Weight = data.get("Weight").toString();
        String Pick_Time = data.get("Pick_Time").toString();
        String Pre_Wait_Time = data.get("Wait_Time").toString();


        String Issue_descri = data.get("Issue_descri").toString();
        String Status = data.get("Status").toString();
        Fetchorder fetchorder=fetchOrderService.findByid("d1bd0cfd76494ee8849a2cac82b706a1");
        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        fetchorder.setDeparture("hahaha");
        fetchorder.setRemark(0);
        fetchOrderService.updateFetchOrder(fetchorder);
        Gson gson=new Gson();
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("status","1");
        jsonObject.put("data","null");

        PrintWriter writer = response.getWriter();
        writer.write(jsonObject.toString());
        writer.flush();
    }

    public void updateShoppingOrder(){

    }
    @RequestMapping(value = "deleteOrder", method = RequestMethod.POST)
    public void deleteOrder(HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);
        String Order_Id=data.get("Order_Id").toString();
          fetchOrderService.deleteFetchorder(Order_Id);
          JSONObject jsonObject=new JSONObject();
          jsonObject.put("status","0");
        PrintWriter writer = response.getWriter();
        writer.write(jsonObject.toString());
        writer.flush();
    }
}
