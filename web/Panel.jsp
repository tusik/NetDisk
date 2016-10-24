<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %><%--
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
        <%
            ArrayList<String> l=(ArrayList) session.getAttribute("dirlist");
            if(l!=null){
                for(int i=0;i<l.size();i++){
                    out.print("<p class=\"dir\">"+l.get(i)+"</p>");
                }
            }else {
                out.print("false");
            }

        %>
    </div>
    <%}else{%>

    <%}%>
</div>
</body>
</html>
