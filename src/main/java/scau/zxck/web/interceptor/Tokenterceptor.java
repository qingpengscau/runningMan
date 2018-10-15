package scau.zxck.web.interceptor;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.jsonwebtoken.Claims;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import scau.zxck.utils.JwtUtil;
import scau.zxck.utils.WriteJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by qingpeng on 2018/9/12.
 */
/* 自定义拦截器需要实现 HandlerInterceptor接口 或者 扩展 HandlerInterceptorAdapter类 */
public class Tokenterceptor implements HandlerInterceptor{/*extends HandlerInterceptorAdapter*/
    private static Logger logger = Logger.getLogger(Tokenterceptor.class);
    @Autowired
    HttpSession session;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
       System.out.println("============拦截器开始处理请求===============");
        if(request.getHeader("Token")!=null){
            JwtUtil jwtUtil=new JwtUtil();
            try{
             Claims claims = jwtUtil.parseJWT(request.getHeader("Token"));
                String id=claims.getId();

                session.setAttribute("User_Id",id);
                return true;
            }catch (Exception e){
                String r="access error!";
                WriteJson.writeJson(response,r);
            }
        }else {
            String r="You have no right to access";
            WriteJson.writeJson(response,r);
        }
        return false;
      // return  false;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object obj, ModelAndView modelAndView) throws Exception {
        if(obj != null && modelAndView != null){
            logger.info("postHandle:" + JSON.toJSONString(obj));
        }
    }
    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object obj, Exception e) throws Exception {
        if(obj != null){
            logger.info("afterCompletion:" + JSON.toJSONString(obj));
        }
    }
}