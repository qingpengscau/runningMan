package scau.zxck.utils;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteJson {

    public static void writeJson (HttpServletResponse response,String r)throws IOException {
        PrintWriter out=response.getWriter();
        out.flush();
        out.write(r);
        response.addHeader("name","qingpeng");
        out.flush();
    }
}
