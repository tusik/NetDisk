
<%@ page import="java.util.ArrayList" %>
<%--
  Created by IntelliJ IDEA.
  User: zinc
  Date: 2016/10/24
  Time: 18:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>NetDisk Panel</title>
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/style.css">

</head>
<body>

<div class="main">
    <%if(session.getAttribute("login").equals("true")){%>
    <div class="layer1">
        <div class="listlayer">
            <%
                ArrayList<String> dirlist=(ArrayList) session.getAttribute("dirlist");
                ArrayList<String> filelist=(ArrayList) session.getAttribute("filelist");
                if(dirlist !=null ){
                    for(int i=0;i<dirlist.size();i++){
                        out.print("<p class=\"dir\"><a href=\""+dirlist.get(i)+"\">"+dirlist.get(i)+"</a></p>");
                    }
                    if(filelist!=null){
                        for(int i=0;i<filelist.size();i++){
                            out.print("<p class=\"dir\"><a href=\""+filelist.get(i)+"\">"+filelist.get(i)+"</a></p>");
                        }
                    }
                }else {
                    out.print("false");
                }
            %>
            <a href="/Files">aaa</a>
        </div>
    </div>
    <%}else{%>

    <%}%>
</div>
</body>
</html>
