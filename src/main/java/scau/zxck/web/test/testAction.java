package scau.zxck.web.test;

import java.sql.Date;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import scau.zxck.entity.market.User;
import scau.zxck.service.market.IUserService;
import scau.zxck.utils.JwtUtil;
import scau.zxck.web.admin.RegisterAction;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations = {"classpath:config/spring/spring.xml","classpath:config/spring/web/spring-mvc.xml"})
@Transactional
public class testAction {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private RegisterAction registerAction;
    @Autowired
    private MockHttpServletRequest request;
    @Autowired
    private MockHttpSession session;
    private MockMvc mockMvc;
    @Before
    public void before() throws Exception {
        mockMvc=standaloneSetup(registerAction).build();
    }

    @After
    public void after() throws Exception {
    }

//    public static void main(String args[]){
//       Timestamp timestamp=new Timestamp(System.currentTimeMillis());
//       System.out.println(timestamp.toString());
//       Date date=new Date(System.currentTimeMillis());
//        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
//        String r=simpleDateFormat.format(date);
//        System.out.println(r);
//    }
    @Test
    public void testRegister() throws Exception{
        String currentMills=String.valueOf(System.currentTimeMillis());
        System.out.println(currentMills);
        String jsonStr=new String("{\"User_Name\":\"renguan\",\"User_Password\":\"123456\",\"User_Sex\":\"1\",\"Location\":\"taishan\",\"Gold\":\"100\",\"User_Cell\":\"111111111\",\"User_Regtime\":\"1536674805422\",\"Secure_Question1\":\"1111111\",\"Secure_Question2\":\"1111111\",\"Secure_Answer1\":\"1111111\",\"Secure_Answer2\":\"1111111\"}");
        JwtUtil jwtUtil=new JwtUtil();
        String jwt=jwtUtil.createJWT("123213",System.currentTimeMillis());
        String responseString=mockMvc.perform(post("/register")
                .contentType(MediaType.APPLICATION_JSON).content(jsonStr).header("AuthToken",jwt)
        ).andExpect(status().isOk()).andDo(print()).andReturn().getResponse().getContentAsString();
        System.out.println(responseString);
    }
}
