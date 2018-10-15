package scau.zxck.utils;

import com.alibaba.fastjson.JSONObject;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;

public class ReadJson {

    public static JSONObject readJson (HttpServletRequest request)throws IOException {
        BufferedReader br=request.getReader();
        String str,whoStr="";
        while((str=br.readLine())!=null){
            whoStr+=str;
        }
        System.out.println(whoStr);
        JSONObject data=JSONObject.parseObject(whoStr);
        System.out.println(request.toString());
        return data;
    }
}
