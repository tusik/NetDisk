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
    <title><%=ConfigFromSQL.GetValues("title")%>|Login Form</title>

    <link rel="stylesheet" href="css/normalize.css">
    <link rel="stylesheet" href="css/style.css">

    <script src="js/prefixfree.min.js"></script>

</head>

<body>

<div class="login">
    <h1>Login</h1>
    <form method="post" onsubmit="return submitbtn_click()">

        <input type="text" name="username" id="username" placeholder="Username" required="required" />
        <input type="password" name="pw" id="pw" placeholder="Password" required="required" />
        <button type="submit" onclick="sendurl()" class="btn btn-primary btn-block btn-large">Let me in.</button>
        <h4 id="info"></h4>
    </form>
</div>

<script src="js/index.js"></script>



</body>
</html>
