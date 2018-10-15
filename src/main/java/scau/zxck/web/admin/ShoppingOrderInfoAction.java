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
import scau.zxck.entity.market.User;
import scau.zxck.service.market.IAddressService;

import scau.zxck.service.market.IShoppingOrderService;
import scau.zxck.service.market.IUserService;
import scau.zxck.serviceImpl.market.UserService;
import scau.zxck.utils.ReadJSONUtil;
import scau.zxck.utils.TimeUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
    @Autowired
    private IUserService iUserService;
@RequestMapping(value = "addShoppingOrder", method = RequestMethod.POST)
    public void addShoppingOrder(HttpServletResponse response) throws Exception {

     request.setCharacterEncoding("utf-8");
     JSONObject data = ReadJSONUtil.readJSONStr(request);

     String Commodity = data.get("Commodity").toString();
     String Shopping_Address = data.get("Shopping_Address").toString();
     String Destination_id = data.get("Destination_Id").toString();
     String Pick_time = data.get("Pick_Time").toString();
     String Prewait_Time =data.get("Prewait_Time").toString();
     String Price = data.get("Price").toString();
     String Comment = data.get("Comment").toString();
     String Fee = data.get("Fee").toString();
     String User_Id=session.getAttribute("User_Id").toString();

       ShoppingOrder shoppingOrder=new ShoppingOrder();
       SimpleDateFormat sf=new SimpleDateFormat("MM-dd HH:mm");
       shoppingOrder.setCommodity(Commodity);
       shoppingOrder.setShopping_address(Shopping_Address);
       shoppingOrder.setDestination_id(Destination_id);
       shoppingOrder.setPick_time(Pick_time);
       shoppingOrder.setPrewait_time(Prewait_Time);
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
         json.put("destination",address.getArea()+"  "+address.getAddress());


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
         Date now = new Date();
         SimpleDateFormat nowTime = new SimpleDateFormat("MM-dd HH-mm");
         String currentTime = nowTime.format(now);
         if(shoppingorders.get(i).getState()!=3) {
             if (TimeUtil.isFirstTimeEarly(shoppingorders.get(i).getPrewait_time(), currentTime)) {
                 shoppingorders.get(i).setState(3);
                 shoppingOrderService.updateShoppingOrder(shoppingorders.get(i));
             }
         }
         json.put("state",shoppingorders.get(i).getState());
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
        JSONObject temp=new JSONObject();
        try {
            if (shoppingOrder.getState() == 0) {
                result.put("isAccepted", "0");
            } else if (shoppingOrder.getState() == 1) {
                result.put("isAccepted", "1");
            } else if (shoppingOrder.getState() == 2) {
                result.put("isAccepted", "2");
            }
        }catch (Exception e){
            e.printStackTrace();
            result.put("isAccepted",3);
        }
        temp.put("status","1");
        temp.put("data",result);
        PrintWriter writer = response.getWriter();
        writer.write(temp.toString());
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
      //  shoppingOrder.setPrewait_time(Integer.parseInt(Prewait_Time));
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
        JSONObject result = new JSONObject();
        try {
            String Order_Id = data.get("Order_Id").toString();
            shoppingOrderService.deleteShoppingOrder(Order_Id);
            result.put("data", "");
            result.put("status", "1");
        }catch (Exception e){
            result.put("data","");
            result.put("status","0");
        }
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
        long Prewait_Time = Long.parseLong(data.get("Prewait_Time").toString());

        ShoppingOrder shoppingOrder = shoppingOrderService.findByid(Order_Id);
        SimpleDateFormat sf=new SimpleDateFormat("MM-dd HH:mm");
        shoppingOrder.setPrewait_time(sf.format(new Date(Prewait_Time*60*1000+new Date().getTime())));
        shoppingOrder.setState(0);
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

        String place=data.get("Place").toString();
        Conditions conditions=new Conditions();
        List<ShoppingOrder> shoppingorders =shoppingOrderService.listShoppingOrder(conditions.eq("state",0));
        Conditions conditions1=new Conditions();
        List<Address>addressList=addressService.listReceiver(conditions1.eq("area",place));
        System.out.println(place);
        List<String>address_ids=new ArrayList<String>();
        for(int i=0;i<addressList.size();i++){
            address_ids.add(addressList.get(i).getId());
        }
        JSONArray temp=new JSONArray();
        Address address=null;
        for(ShoppingOrder e:shoppingorders){
            System.out.println(e.getDestination_id()+"111111111");
            for(String w:address_ids){
                System.out.println(w);
                if(e.getDestination_id().equals(w)){
                    JSONObject json=new JSONObject();
                    json.put("orderId",e.getId());
                    json.put("departure",e.getShopping_address());
                    address = addressService.findByid(w);
                    json.put("destination",address.getArea()+"  "+address.getAddress());


                    json.put("receiveMan",address.getName());
                    json.put("commodity",e.getCommodity());
                    json.put("pickTime",e.getPick_time());
                    json.put("preWaitTime",e.getPrewait_time());
                    json.put("comment",e.getComment());
                    json.put("fee",e.getFee());
                    json.put("releaseTime",e.getRelease_time());
                    json.put("status",0);
                    json.put("price",e.getPrice());
                    json.put("fee",e.getFee());
                    json.put("state",e.getState());
                    temp.add(json);
                }
            }
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
        shoppingOrder.setState(1);
        shoppingOrder.setExecute_man_id(session.getAttribute("User_Id").toString());
        shoppingOrderService.updateShoppingOrder(shoppingOrder);

        JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data","");
        System.out.println(result.toString());
        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();

    }

    @RequestMapping(value = "getShoppingAcceptedOrder", method = RequestMethod.POST)
    public void getShoppingingAcceptedOrder(HttpServletResponse response) throws Exception
    {

        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);
        String User_Id =session.getAttribute("User_Id").toString();
        Conditions conditions=new Conditions();

        conditions.eq("execute_man_id",User_Id);
        List<ShoppingOrder> shoppingorders = shoppingOrderService.listShoppingOrder(conditions);
        JSONArray temp=new JSONArray();
        Address address=null;
        for(int i=0;i<shoppingorders.size();i++)
        {
            JSONObject json=new JSONObject();
            json.put("orderId",shoppingorders.get(i).getId());
            json.put("departure",shoppingorders.get(i).getShopping_address());
            address = addressService.findByid(shoppingorders.get(i).getDestination_id());
            json.put("destination",address.getArea()+"  "+address.getAddress());


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
            json.put("state",shoppingorders.get(i).getState());



            temp.add(json);
        }
        JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data",temp);

        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();
    }
    @RequestMapping(value = "finishShopphingOrder", method = RequestMethod.POST)
    public void finishShopphingOrder(HttpServletResponse response) throws Exception
    {
        request.setCharacterEncoding("utf-8");
        JSONObject data = ReadJSONUtil.readJSONStr(request);

        String Order_Id = data.get("Order_Id").toString();
        ShoppingOrder shoppingOrder = shoppingOrderService.findByid(Order_Id);
        User release_man=iUserService.findOne(shoppingOrder.getRelease_man_id());
        User excute_man=iUserService.findOne(shoppingOrder.getExecute_man_id());
        double fee=shoppingOrder.getFee();
        release_man.setGold(release_man.getGold()-fee);
        iUserService.updateUser(release_man);

        excute_man.setGold(excute_man.getGold()+fee);
        iUserService.updateUser(excute_man);
        shoppingOrder.setState(2);
        shoppingOrderService.updateShoppingOrder(shoppingOrder);

        JSONObject result=new JSONObject();
        result.put("status","1");
        result.put("data","");
        System.out.println(result.toString());
        PrintWriter writer = response.getWriter();
        writer.write(result.toString());
        writer.flush();

    }

}
