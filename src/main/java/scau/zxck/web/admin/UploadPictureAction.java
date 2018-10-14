package scau.zxck.web.admin;

import com.alibaba.fastjson.JSON;
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
import scau.zxck.base.dao.mybatis.Conditions;
import scau.zxck.base.exception.BaseException;
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
        @RequestMapping(value = "uploadPicture",method =RequestMethod.POST )
        public void login(HttpServletResponse response) throws BaseException, IOException, FileUploadException {
            String temp = session.getServletContext().getRealPath("temp");
            DiskFileItemFactory factory = new DiskFileItemFactory(1024*1024, new File(temp));
            ServletFileUpload upload = new ServletFileUpload(factory);
            upload.setHeaderEncoding("UTF-8");
            boolean multipartContent = upload.isMultipartContent(request);//判断表单是否是文件上传的表单
            if (multipartContent){
                List<FileItem> parseRequest = upload.parseRequest(request);
                if (parseRequest!=null){
                    for (FileItem item : parseRequest){
                        String fileName = item.getName();
                        //获得上传文件的内容
                        InputStream in = item.getInputStream();
                        String path_store = session.getServletContext().getRealPath("upload");
                        OutputStream out = new FileOutputStream(path_store+"/"+fileName);
                        IOUtils.copy(in,out);
                        in.close();
                        out.close();
                        item.delete();
                    }
                }
            }


            WriteJson.writeJson(response,temp);
        }
}
