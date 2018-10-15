package scau.zxck.web.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
import scau.zxck.entity.market.Address;
import scau.zxck.entity.market.User;
import scau.zxck.service.market.IUserService;
import scau.zxck.utils.JwtUtil;
import scau.zxck.utils.WriteJson;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.List;

@Controller
@RequestMapping("/")
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:config/spring/spring.xml"})
public class UploadPictureAction {
    @Autowired
    private IUserService iUserService;
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpSession session;
    @RequestMapping(value = "/uploadHeadPicture", method = RequestMethod.POST)
    @ResponseBody
    public void uploadHeadPicture(HttpServletResponse response,@RequestParam("head") MultipartFile[] partFiles, String data) throws IOException {
        JSONObject temp = new JSONObject();
        String r = "";
        String id = session.getAttribute("User_Id").toString();
        try {
            if (null != partFiles && partFiles.length > 0)
            {
                for (int i = 0; i < partFiles.length; i++)
                {
                    MultipartFile partFile = partFiles[i];
                    //服务器图片保存的路径
                    String imgPath = session.getServletContext().getRealPath("head")+"/"+id+".jpg";
                    File imgFile = new File(imgPath);
                    //将图片写到指定的文件下
                    partFile.transferTo(imgFile);
                }
                temp.put("status","1");
                temp.put("data",null);
                r=temp.toJSONString();
            }

        }catch (Exception e){
            temp.put("status","0");
            temp.put("data","");
            r=temp.toJSONString();
            e.printStackTrace();
        }finally {
            WriteJson.writeJson(response,r);
        }
    }


    @RequestMapping(value = "downloadHeadPicture",method =RequestMethod.POST )
    public void getDeliverAddress(HttpServletResponse response) throws BaseException,IOException {
        try {
            String id = session.getAttribute("User_Id").toString();
            String fileName = id+".jpg";
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
            response.setContentType(request.getServletContext().getMimeType(fileName));
            String path = request.getServletContext().getRealPath("/head/"+fileName);
            InputStream inputStream = new FileInputStream(path);
            OutputStream outputStream = response.getOutputStream();
            int len = 0;
            byte[] buffer = new byte[1024];
            while((len=inputStream.read(buffer))>0){
                outputStream.write(buffer,0,len);
            }
        }catch (Exception e){

            e.printStackTrace();
        }finally {
        }
    }

}
