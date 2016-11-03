<%--
  Created by IntelliJ IDEA.
  User: zinc
  Date: 2016/11/3
  Time: 19:12
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=request.getAttribute("filename")%></title>
</head>
<body>
<img src="<%=request.getAttribute("filepath")%>"/>
</body>
</html>
