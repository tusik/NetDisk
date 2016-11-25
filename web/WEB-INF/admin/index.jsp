<%@ page import="java.util.Map" %>
<%@ page import="cat.code.netdisk.admin.ConfigFromSQL" %><%--
  Created by IntelliJ IDEA.
  User: zinc
  Date: 2016/11/17
  Time: 19:28
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=ConfigFromSQL.GetValues("title")%></title>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../css/normalize.css">
    <link rel="stylesheet" href="../css/style.css">
    <script src="../js/prefixfree.min.js"></script>
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<%
    Map<String, String> map = (Map<String, String>)request.getAttribute("data");
%>
<div class="main">
    <div class="layer1">

        <div class="listlayer">
            <li>基本管理</li>
            <li><a href="/admin/UserManage">用户管理  </a></li>
            <li><a href="/admin/FileManage">文件管理</a></li>
            <div>
                <form method="post" >
                    <input type="text" value="<%=map.get("title")%>"><br/>
                    <input type="text" value="<%=map.get("DEBUG")%>"><br/>
                    <input type="text" value="<%=map.get("size")%>"><br/>
                    <input type="text" value="<%=map.get("regopen")%>"><br/>
                </form>
            </div>
        </div>
    </div>
</div>

</body>
</html>
