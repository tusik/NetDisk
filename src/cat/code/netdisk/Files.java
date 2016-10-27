package cat.code.netdisk;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.net.URLEncoder;
import java.util.ArrayList;

/**
 * Created by zinc on 2016/10/25.
 *
 * @author Image
 */
@WebServlet(name = "Files" , urlPatterns = "/Files")
public class Files extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        doPost(request,response);
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response)
            throws IOException {
        String url=request.getParameter("url");
        String s[]=url.split("/");
        String username=s[0];
        String filedir="null";
        HttpSession session=request.getSession();
        //判断session中的用户名是否和url中的相同
        if(username.equals(session.getAttribute("username"))){
            String dir=new String();
            if(s.length>0)
                for(int i=1;i<s.length;i++)dir=dir+"/"+s[i];
            try{
                //获取真实文件地址
                filedir=this.getClass().getClassLoader().getResource("../../WEB-INF/files/"
                        +username+"/"+dir).getPath();
            }catch (Exception e){
                e.printStackTrace();
            }
            File file = new File(filedir);
            if(file.exists()){
                //下载文件
                downloadLocal(response,filedir,s[s.length-1]);
            }else {
                response.setStatus(404);
                response.sendRedirect("/WEB-INF/404.jsp");
            }
        }

    }
    public   void  downloadLocal(HttpServletResponse response,String filepath,String fileName)
            throws  FileNotFoundException {
        //下载本地文件
        //读到流中
        InputStream inStream  =   new  FileInputStream( filepath ); //文件的存放路径
        //设置输出的格式
        response.reset();
        response.setContentType( " bin " );
        response.addHeader( " Content-Disposition " ,  " attachment; filename=\""  + fileName +  "\"" );
        //循环取出流中的数据
        byte [] b  =   new   byte [ 100 ];
        int  len;
        try  {
            while  ((len  =  inStream.read(b))  >   0 )
                response.getOutputStream().write(b,  0 , len);
            inStream.close();
        }  catch  (IOException e) {
            e.printStackTrace();
        }
    }
}
