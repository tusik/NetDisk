<%@ page import="java.util.ArrayList" %>
<%@ page import="cat.code.netdisk.admin.ConfigFromSQL" %><%--
  Created by IntelliJ IDEA.
  User: zinc
  Date: 2016/10/29
  Time: 15:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=ConfigFromSQL.GetValues("title")%>|Share File List</title>
    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<div class="main">
    <%if(session.getAttribute("login").equals("true")){%>
        <div class="layer1">
            <div class="listlayer">
                <p class="dir"></p>
                <%
                    ArrayList codelist =(ArrayList) request.getAttribute("codelist");
                    ArrayList namelist =(ArrayList) request.getAttribute("namelist");
                    for(int i=0;i<codelist.size();i++){
                        out.print("<a href=\"/GetShare/"
                                +codelist.get(i)+"\">"+namelist.get(i)+"</a>" +
                                "<a class=\"del\" href=\"/Share?id="+codelist.get(i)
                                +"&del=1\">Canel Sharing</a><br/>");
                    }
                %>
                </p>
                <a href="/">Panel</a><br/>
                <a href="/Logout">Logout</a>
            </div>
        </div>
    <%}else{%>

    <%}%>
    <%if(session.getAttribute("DEBUG").equals(1))%>
    <jsp:include page="WEB-INF/DEBUG.jsp"></jsp:include>
</div>
</body>
</html>
