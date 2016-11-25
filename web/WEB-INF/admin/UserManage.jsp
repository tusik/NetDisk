<%@ page import="java.util.ArrayList" %>
<%@ page import="cat.code.netdisk.User" %>
<%@ page import="cat.code.netdisk.admin.ConfigFromSQL" %><%--
  Created by IntelliJ IDEA.
  User: zinc
  Date: 2016/11/17
  Time: 19:32
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title><%=ConfigFromSQL.GetValues("title")%></title>
    <link rel="stylesheet" href="../css/normalize.css">
    <link rel="stylesheet" href="../css/style.css">
</head>
<body>
<jsp:include page="../header.jsp"></jsp:include>
<div class="main">
    <div class="layer1">
        <div class="listlayer">
            <table border="0">
<%
    ArrayList<User> users = (ArrayList<User>) request.getAttribute("users");
    User user;
    out.print("<tr><th>id</th><th>username</th><th>password</th><th>rank</th><th>filecount</th>" +
            "<th>diskusage</th><th>maxdisk</th><th>regdate</th><th></th></tr>");
    for(int i=0;i<users.size();i++){
        user =users.get(i);
        StringBuffer context = new StringBuffer("<tr><td>"+user.getId()+"</td>" +
                "<td>"+ user.getUsername()+"</td>" +
                "<td>"+ user.getPassword()+"</td>" +
                "<td>"+ user.getRank()+"</td>" +
                "<td>"+ user.getFilecount()+"</td>" +
                "<td>"+ user.getDiskused()+"</td>" +
                "<td>"+ user.getMaxdisk()+"</td>" +
                "<td>"+user.getRegdate()+"</td>" +
                "<td>");
        if(!session.getAttribute("username").equals(user.getUsername())){
            context.append("<a href=\"?del_id="+user.getId()+"\">del</a>");
        }
        context.append("</td></tr><br/>");
        out.print(context);
    }
%>
                </table>
        </div>
    </div>
    <%if(session.getAttribute("DEBUG")!=null){%>
    <jsp:include page="../DEBUG.jsp"></jsp:include>
    <%}%>
</div>
</body>
</html>
