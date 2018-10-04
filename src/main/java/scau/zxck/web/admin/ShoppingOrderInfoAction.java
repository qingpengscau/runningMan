package scau.zxck.web.admin;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.entity.market.Address;

import scau.zxck.entity.market.ShoppingOrder;
import scau.zxck.service.market.IAddressService;

import scau.zxck.service.market.IShoppingOrderService;
import scau.zxck.utils.ReadJSONUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/")
@RunWith(SpringJUnit4ClassRunner.class)

@ContextConfiguration(locations = {"classpath:config/spring/spring.xml", "classpath:config/spring/web/spring-mvc.xml"})
public class ShoppingOrderInfoAction {
    @Autowired
   private IShoppingOrderService shoppingOrderService;
    @Autowired
    private IAddressService addressService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;

@RequestMapping(value = "addShoppingOrder", method = RequestMethod.POST)
    public void addShoppingOrder(HttpServletResponse response) throws Exception {

     request.setCharacterEncoding("utf-8");
     JSONObject data = ReadJSONUtil.readJSONStr(request);

     String Commodity = data.get("Commodity").toString();
     String Shopping_Address = data.get("Shopping_Address").toString();
     String Destination_id = data.get("Destination_Id").toString();
     String Pick_time = data.get("Pick_time").toString();
     String Prewait_Time = data.get("Prewait_Time").toString();
     String Price = data.get("Price").toString();
     String Comment = data.get("Comment").toString();
     String Fee = data.get("Fee").toString();
     String User_Id=session.getAttribute("User_Id").toString();

       ShoppingOrder shoppingOrder=new ShoppingOrder();
       SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
       shoppingOrder.setCommodity(Commodity);
       shoppingOrder.setShopping_address(Shopping_Address);
       shoppingOrder.setDestination_id(Destination_id);
       shoppingOrder.setPick_time(Pick_time);
       shoppingOrder.setPrewait_time(Integer.parseInt(Prewait_Time));
       shoppingOrder.setPrice(Double.parseDouble(Price));
       shoppingOrder.setComment(Comment);
       shoppingOrder.setFee(Double.parseDouble(Fee));
       shoppingOrder.setRelease_time(sf.format(new Date()));
       shoppingOrder.setRelease_man_id(User_Id);

       shoppingOrderService.addShoppingOrder(shoppingOrder);


     JSONObject jsonObject=new JSONObject();
     jsonObject.put("status","1");
     jsonObject.put("data","");
     String s =jsonObject.toJSONString();
     PrintWriter writer = response.getWriter();
     writer.write(s);
     writer.flush();

 }

    @RequestMapping(value = "getAllShoppingOrder", method = RequestMethod.POST)
    public void getAllShoppingingOrder(HttpServletResponse response) throws Exception {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);
       String User_Id=session.getAttribute("User_Id").toString();
        Conditions conditions=new Conditions();
        List<ShoppingOrder> shoppingorders = shoppingOrderService.listShoppingOrder(conditions.eq("release_man_id",User_Id));
        JSONArray temp=new JSONArray();
        Address address=null;
        for(int i=0;i<shoppingorders.size();i++)
     {
         JSONObject json=new JSONObject();
         json.put("orderId",shoppingorders.get(i).getId());
         json.put("departure",shoppingorders.get(i).getShopping_address());
         address = addressService.findByid(shoppingorders.get(i).getDestination_id());
         json.put("destination",address.getAddress());


         json.put("receiveMan",address.getName());
         json.put("commodity",shoppingorders.get(i).getCommodity());
         json.put("pickTime",shoppingorders.get(i).getPick_time());
         json.put("preWaitTime",shoppingorders.get(i).getPrewait_time());
         json.put("comment",shoppingorders.get(i).getComment());
         json.put("fee",shoppingorders.get(i).getFee());
         json.put("releaseTime",shoppingorders.get(i).getRelease_time());
         json.put("status",0);
         json.put("price",shoppingorders.get(i).getPrice());
         json.put("fee",shoppingorders.get(i).getFee());
         json.put("state",shoppingorders.get(i).getStatus());



         temp.add(json);
     }
     JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data",temp);

        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();
    }

    @RequestMapping(value = "isShoppingOrderAccepted", method = RequestMethod.POST)
    public void isShoppingOrderAccepted(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);
        String Order_Id = data.get("Order_Id").toString();
        Conditions conditions=new Conditions();
        ShoppingOrder shoppingOrder = shoppingOrderService.findByid(Order_Id);
        JSONObject result=new JSONObject();
        if(shoppingOrder.getStatus()>0)
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

    @RequestMapping(value = "updateShoppingOrder", method = RequestMethod.POST)
    public void updateShoppingOrder(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);

        String Order_Id = data.get("Order_Id").toString();
        String Commodity = data.get("Commodity").toString();
        String Shopping_Address = data.get("Shopping_Address").toString();
        String Destination_id = data.get("Destination_Id").toString();
        String Pick_time = data.get("Pick_time").toString();
        String Prewait_Time = data.get("Prewait_Time").toString();
        String Price = data.get("Price").toString();
        String Comment = data.get("Comment").toString();
        String Fee = data.get("Fee").toString();

        ShoppingOrder shoppingOrder = shoppingOrderService.findByid(Order_Id);

        SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        shoppingOrder.setCommodity(Commodity);
        shoppingOrder.setShopping_address(Shopping_Address);
        shoppingOrder.setDestination_id(Destination_id);
        shoppingOrder.setPick_time(Pick_time);
        shoppingOrder.setPrewait_time(Integer.parseInt(Prewait_Time));
        shoppingOrder.setPrice(Double.parseDouble(Price));
        shoppingOrder.setComment(Comment);
        shoppingOrder.setFee(Double.parseDouble(Fee));


        shoppingOrderService.updateShoppingOrder(shoppingOrder);
        JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data","");
        System.out.println(result.toString());
        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();



    }

    @RequestMapping(value = "deleteShoppingOrder", method = RequestMethod.POST)
    public void deleteFetchingOrder(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);

        String Order_Id=data.get("Order_Id").toString();
        shoppingOrderService.deleteShoppingOrder(Order_Id);

        JSONObject result=new JSONObject();
        result.put("data","");
        result.put("status","");
        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();
    }

    @RequestMapping(value = "enableShoppingOrder", method = RequestMethod.POST)
    public void enableShoppingOrder(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);

        String Order_Id = data.get("Order_Id").toString();
        String Prewait_Time = data.get("Prewait_Time").toString();

        ShoppingOrder shoppingOrder = shoppingOrderService.findByid(Order_Id);
        shoppingOrder.setPrewait_time(Integer.parseInt(Prewait_Time));
        shoppingOrder.setStatus(0);
        shoppingOrderService.updateShoppingOrder(shoppingOrder);

        JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data","");
        System.out.println(result.toString());
        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();

    }

    @RequestMapping(value = "getShoppingPlaceOrder", method = RequestMethod.POST)
    public void getShoppingPlaceOrder(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);

        String Place=data.get("Place").toString();
        List<Address> addresses = addressService.listReceiver(new Conditions().eq("address", "huashan"));
        String[] addresse_ids = new String[100];
        for(int i=0;i<addresses.size();i++)
       {
           addresse_ids[i]=addresses.get(i).getId();
       }

        Conditions conditions=new Conditions();
        conditions.in("destination_id",addresse_ids).and().eq("status","0");
        List<ShoppingOrder> shoppingorders = shoppingOrderService.listShoppingOrder(conditions);

        JSONArray temp=new JSONArray();
        Address address=null;
        for(int i=0;i<shoppingorders.size();i++)
        {
            JSONObject json=new JSONObject();
            json.put("orderId",shoppingorders.get(i).getId());
            json.put("departure",shoppingorders.get(i).getShopping_address());
            address = addressService.findByid(shoppingorders.get(i).getDestination_id());
            json.put("destination",address.getAddress());


            json.put("receiveMan",address.getName());
            json.put("commodity",shoppingorders.get(i).getCommodity());
            json.put("pickTime",shoppingorders.get(i).getPick_time());
            json.put("preWaitTime",shoppingorders.get(i).getPrewait_time());
            json.put("comment",shoppingorders.get(i).getComment());
            json.put("fee",shoppingorders.get(i).getFee());
            json.put("releaseTime",shoppingorders.get(i).getRelease_time());
            json.put("status",0);
            json.put("price",shoppingorders.get(i).getPrice());
            json.put("fee",shoppingorders.get(i).getFee());
            json.put("state",shoppingorders.get(i).getStatus());
            temp.add(json);
        }
        JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data",temp);
        System.out.println(result.toString());
        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();
    }

    @RequestMapping(value = "acceptShoppingOrder", method = RequestMethod.POST)
    public void acceptFetchingOrder(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);

        String Order_Id = data.get("Order_Id").toString();
        ShoppingOrder shoppingOrder = shoppingOrderService.findByid(Order_Id);
        shoppingOrder.setStatus(1);
        shoppingOrderService.updateShoppingOrder(shoppingOrder);

        JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data","");
        System.out.println(result.toString());
        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();

    }

    @RequestMapping(value = "getShoppingingAcceptedOrder", method = RequestMethod.POST)
    public void getFetchingAcceptedOrder(HttpServletResponse response) throws Exception
    {

        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);
        String Usre_Id =session.getAttribute("Usre_Id").toString();
        Conditions conditions=new Conditions();

        conditions.eq("execute_man_id",Usre_Id);
        List<ShoppingOrder> shoppingorders = shoppingOrderService.listShoppingOrder(conditions);
        JSONArray temp=new JSONArray();
        Address address=null;
        for(int i=0;i<shoppingorders.size();i++)
        {
            JSONObject json=new JSONObject();
            json.put("orderId",shoppingorders.get(i).getId());
            json.put("departure",shoppingorders.get(i).getShopping_address());
            address = addressService.findByid(shoppingorders.get(i).getDestination_id());
            json.put("destination",address.getAddress());


            json.put("receiveMan",address.getName());
            json.put("commodity",shoppingorders.get(i).getCommodity());
            json.put("pickTime",shoppingorders.get(i).getPick_time());
            json.put("preWaitTime",shoppingorders.get(i).getPrewait_time());
            json.put("comment",shoppingorders.get(i).getComment());
            json.put("fee",shoppingorders.get(i).getFee());
            json.put("releaseTime",shoppingorders.get(i).getRelease_time());
            json.put("status",0);
            json.put("price",shoppingorders.get(i).getPrice());
            json.put("fee",shoppingorders.get(i).getFee());
            json.put("state",shoppingorders.get(i).getStatus());



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
