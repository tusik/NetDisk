<%@ page import="java.util.Enumeration" %><%--
  Created by IntelliJ IDEA.
  User: zinc
  Date: 2016/11/16
  Time: 19:34
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div class="DEBUG">
<%
    String tmp;
    Enumeration s = session.getAttributeNames();
    for(;s.hasMoreElements();){
        tmp=(String)s.nextElement();
        out.print("<li>"+tmp+"="+session.getAttribute(tmp)+"</li>");
    }
%></div>