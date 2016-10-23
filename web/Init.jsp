<%--
  Created by IntelliJ IDEA.
  User: zinc
  Date: 2016/10/22
  Time: 15:31
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Init System</title>
</head>
<body>
<form method="post" action="/InitSystem">
    数据库名：<input type="text" name="dbname" id="dbname"/><br/>
    数据库用户名：<input type="text" name="dbuser" id="dbuser"/><br/>
    数据库密码：<input type="text" name="dbpw" id="dbpw"/><br/>
    管理员用户名:<input type="text" name="adminname" id="adminname"/><br/>
    管理员密码：<input type="password"name="adminpw" id="adminpw"/><br/>
    重复密码：<input type="password"name="adminpw1" id="adminpw1"/><br/>
    SALT：<input type="text"name="salt" id="salt"/><br/>
    <input type="submit"/>
</form>
</body>
</html>
