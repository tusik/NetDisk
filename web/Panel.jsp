
<%@ page import="java.util.ArrayList" %>
<%--
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
    <%
        String predir=request.getParameter("dir");
        if(predir!=null){
            String tmp[]=predir.split("/");
            predir="";
            for(int i=1;i<tmp.length-1;i++)predir+=tmp[i];
        }else {
            predir="";
        }

    %>
    <div class="layer1">
        <div class="listlayer">
            <%
                ArrayList<String> dirlist=(ArrayList) request.getAttribute("dirlist");
                ArrayList<String> filelist=(ArrayList) request.getAttribute("filelist");
                if(dirlist !=null ){
                    String dir=request.getParameter("dir");
                    if(dir==null)dir="";
                    for(int i=0;i<dirlist.size();i++){

                        out.print("<p class=\"dir\"><a href=\"/Panel?dir="+dir+"/"
                                +dirlist.get(i)+"\">"+dirlist.get(i)+"</a><a class=\"del\" href=\"Delete?path="
                                + request.getParameter("dir") + dirlist.get(i) + "\">del</a></p>");
                    }
                    if(filelist!=null){
                        for(int i=0;i<filelist.size();i++){
                            out.print("<p class=\"dir\"><a href=\"/GetFiles/"+session.getAttribute("username")
                                    +dir+"/"+filelist.get(i)+"\">"
                                    +filelist.get(i)+"</a><a class=\"del\" href=\"Delete?path="
                                    +dir+"/"+filelist.get(i)+"\">|del</a><a class=\"del\" href=\"Share?path="
                                    + dir + "/" + filelist.get(i) + "&user="+session.getAttribute("username")
                                    +"\">|Share</a></p>");
                        }
                    }
                }else {
                    out.print("null");
                }
            %>
            <form action="/CreateDir" method="post" class="newdir">
                <input class="pinput" type="text" id="dname" name="dname" value="<%=request.getParameter("dir")%>/"/><br/>
                <input class="pinput" type="submit" value="Create Dir"><br/>
            </form>
            <a href="/Panel?dir=/<%=predir%>">..</a><br/>
            <a href="/Sharelist">Share List</a><br/>
            <a href="/Logout">Logout</a>
        </div>
    </div>
    <%}else{%>

    <%}%>
</div>
</body>
</html>
