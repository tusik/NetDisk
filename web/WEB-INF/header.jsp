<%@ page import="cat.code.netdisk.admin.IsAdmin" %>
<%@ page import="java.util.regex.Matcher" %>
<%@ page import="java.util.regex.Pattern" %><%--
  Created by IntelliJ IDEA.
  User: zinc
  Date: 2016/11/21
  Time: 18:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    String url=request.getRequestURI();
%>
<div class="header">
    <p>欢迎<%=session.getAttribute("username")%>
        <%
            Pattern r = Pattern.compile("/admin");
            Matcher m = r.matcher(url);

            IsAdmin ia = new IsAdmin(request);
            if(ia.isAdmin()==1){
                if(!m.find()){
                    out.print(" 管理员|<a href=\"/manage\">后台管理</a>");
                }else {
                    out.print(" 管理员|<a href=\"/\">前台页面</a>");
                }
            }
        %></p>
    <p class="use">
        使用量
        <%
            if(ia.isAdmin()!=1) {
                if (!m.find()) {
                    out.print(session.getAttribute("diskused") +
                            "/" + session.getAttribute("maxdisk"));
                } else {
                    out.print(session.getAttribute("allused"));
                }
            }else {
                out.print(session.getAttribute("diskused") +
                        "/" + session.getAttribute("maxdisk"));
            }
        %>
        MB | <%=session.getAttribute("filecount")%>个文件
    </p>
</div>
