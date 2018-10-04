package test.scau.zxck.web.admin;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import scau.zxck.entity.market.Fetchorder;
import scau.zxck.web.admin.OrderInfoAction;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.SimpleFormatter;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by lishunpeng on 2015/11/13.
 */




@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({"classpath:config/spring/spring.xml","classpath:config/spring/web/spring-mvc.xml"})
public class OrderInfoActionTest {
    // 记得配置log4j.properties ,的命令行输出水平是debug
    protected Log logger = LogFactory.getLog(OrderInfoActionTest.class);


    @Autowired
    private WebApplicationContext wac;
    private MockMvc mockMvc;
   @Autowired
   private OrderInfoAction orderInfoAction;
    @Before() // 这个方法在每个方法执行之前都会执行一遍
    public void setup() {
        mockMvc = standaloneSetup(orderInfoAction).build(); // 初始化MockMvc对象
    }

    @org.junit.Test
    public void getAllCategoryTest() throws Exception {
        //ParamObj paramObj=new ParamObj(true,"12345678","","1",""/*,200*/);
        /*String str=JSON.toJSON(paramObj).toString();
        System.out.println(str);*/
        /*Fetchorder fetchorder=new Fetchorder();
        fetchorder.setRelease_man_id("1");

        fetchorder.setReceive_man_id("1");
        fetchorder.setRelease_time("asfsaf");
        fetchorder.setDeparture("guangzhou");
        fetchorder.setDestination("beijing");
        fetchorder.setType(1);
        fetchorder.setFee(20);
        fetchorder.setIssue_descri("henzhong");
        fetchorder.setStatus(9);
        fetchorder.setRemark(0);*/
        /*Gson gson=new Gson();
        String s = gson.toJson(fetchorder);
        //System.out.println(s);
        String name="{\"status\":\"0\"}";
        String password="guanshui";
        //System.out.println(gson.toJson(name));
        System.out.println(s);*/
       // JsonObject returnData = new JsonParser().parse(name).getAsJsonObject();

      String responseString = mockMvc.perform( post("/getFetchingPlaceOrder").contentType(MediaType.APPLICATION_JSON_VALUE).content("")).andDo(print())
              .andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
    }
}