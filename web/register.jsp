<%@ page import="cat.code.netdisk.admin.ConfigFromSQL" %><%--
  Created by IntelliJ IDEA.
  User: zinc
  Date: 2016/10/21
  Time: 22:24
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html >
<head>
    <meta charset="UTF-8">
    <title><%=ConfigFromSQL.GetValues("title")%>|Register Form</title>

    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/style.css">

    <script src="js/prefixfree.min.js"></script>
    <%
        String error = request.getParameter("error");
        if(error!=null){
            if(error.equals("notmatch"))
                out.print("<script>alert(\"Retype password not match\")</script>");
            if(error.equals("username"))
                out.print("<script>alert(\"Username exist, please choose another one\")</script>");
            if(error.equals("database"))
                out.print("<script>alert(\"Something wrong with database\")</script>");
            if(error.equals("close"))
                out.print("<script>alert(\"Website not open registration\")</script>");
        }
    %>
</head>

<body>

<div class="login">
    <h1>Login</h1>
    <form method="post" action="/Register">
        <input type="text" name="username" id="username" placeholder="Username" required="required" />
        <input type="password" name="pw" id="pw" placeholder="Password" required="required" />
        <input type="password" name="pw1" id="pw1" placeholder="Password" required="required" />
        <button type="submit" class="btn btn-primary btn-block btn-large">Submit</button>
    </form>
</div>

<script src="js/index.js"></script>



</body>
</html>
