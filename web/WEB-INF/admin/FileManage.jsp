<%@ page import="java.util.regex.Matcher" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: zinc
  Date: 2016/11/17
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="../css/normalize.css">
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<div class="main">
    <div class="layer1">

        <div class="listlayer">
<%
    //获取文件和目录列表
    ArrayList<String> dirlist=(ArrayList) request.getAttribute("dirlist");
    ArrayList<String> filelist=(ArrayList) request.getAttribute("filelist");
    if(dirlist !=null &&filelist!=null){
        String dir=request.getParameter("dir");//获取当前目录
        if(dir==null)dir="";
        //目录列表输出
        if(dirlist!=null){
            for(int i=0;i<dirlist.size();i++){
                String context="<p class=\"dir\"><a href=\"/admin/FileManage?dir="+dir+"/"
                        +dirlist.get(i)+"\">"+dirlist.get(i)+"</a><a class=\"del\" href=\"/Delete?admin=true&path="
                        +dir+"/"+dirlist.get(i) + "\">del</a></p>";
                out.print(context);
            }
        }
        //文件列表输出
        if(filelist!=null){
            for(int i=0;i<filelist.size();i++){
                String context="<p class=\"dir\"><a href=\"/GetFiles/"+session.getAttribute("username")
                        +dir+"/"+filelist.get(i)+"\">" +filelist.get(i)+"</a>" +
                        "<a class=\"del\" href=\"Delete?admin=true&path=" +dir+"/"+filelist.get(i)+"\">|del</a>" +
                        "<a class=\"del\" href=\"Share?path=" + dir + "/" + filelist.get(i) +
                        "&user="+session.getAttribute("username") +"\">|Share</a>" ;
                String pattern = "((.*)(.jpg)(.*)|(.*)(.png)(.*)|(.*)(.gif)(.*)|(.*)(.jpeg)(.*)|" +
                        "(.*)(.bmp)(.*)|(.*)(.ico)(.*))";
                Pattern r = Pattern.compile(pattern);
                Matcher m = r.matcher(filelist.get(i));
                if(m.find())//判断为图像文件则提供直接浏览
                    context+="<a class=\"del\" href=\"/view/"+session.getAttribute("username") +
                            dir+"/"+filelist.get(i)+ "\">|VIEW</a></p>";
                out.print(context);
            }
        }
    }else {
        out.print("null");
    }
%>
            </div>
        </div>
    </div>
</body>
</html>
