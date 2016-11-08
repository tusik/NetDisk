
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.regex.Pattern" %>
<%@ page import="java.util.regex.Matcher" %>
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
                if(dirlist !=null &&filelist!=null){
                    String dir=request.getParameter("dir");
                    if(dir==null)dir="";
                    if(dirlist!=null){

                        for(int i=0;i<dirlist.size();i++){
                            String context="<p class=\"dir\"><a href=\"/Panel?dir="+dir+"/"
                                    +dirlist.get(i)+"\">"+dirlist.get(i)+"</a><a class=\"del\" href=\"Delete?path="
                                    +dir+"/"+dirlist.get(i) + "\">del</a></p>";
                            out.print(context);
                        }
                    }
                    if(filelist!=null){
                        for(int i=0;i<filelist.size();i++){
                            String context="<p class=\"dir\"><a href=\"/GetFiles/"+session.getAttribute("username")
                                    +dir+"/"+filelist.get(i)+"\">" +filelist.get(i)+"</a>" +
                                    "<a class=\"del\" href=\"Delete?path=" +dir+"/"+filelist.get(i)+"\">|del</a>" +
                                    "<a class=\"del\" href=\"Share?path=" + dir + "/" + filelist.get(i) +
                                    "&user="+session.getAttribute("username") +"\">|Share</a>" ;
                            String pattern = "((.*)(.jpg)(.*)|(.*)(.png)(.*)|(.*)(.gif)(.*)|(.*)(.jpeg)(.*)|" +
                                    "(.*)(.bmp)(.*)|(.*)(.ico)(.*))";
                            //创建Pattern对象
                            Pattern r = Pattern.compile(pattern);
                            //创建matcher对象
                            Matcher m = r.matcher(filelist.get(i));
                            if(m.find())
                                context+="<a class=\"del\" href=\"/view/"+session.getAttribute("username") +
                                    dir+"/"+filelist.get(i)+ "\">|VIEW</a></p>";
                            out.print(context);
                        }
                    }
                }else {
                    out.print("null");
                }
            %>
            <form enctype="multipart/form-data"  action="/UploadServlet?dir=<%if(request.getParameter("dir")!=null)
                out.print(request.getParameter("dir"));%>" method="post" class="newdir">
                <input class="pinput" type="file" id="myFile" name="myFile"/><br/>
                <div id="filen"></div>
                <input class="pinput" type="submit" value="upload"><br/>
            </form>
            <form action="/CreateDir" method="post" class="newdir">
                <input class="pinput" type="text" id="dname" name="dname"
                       value="<%if(request.getParameter("dir")!=null)out.print(request.getParameter("dir"));%>/"/><br/>
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
<script type="text/javascript">

    document.getElementById("myFile").addEventListener("change", function(){
        var name=this.value;
        var filename=name.substring(name.lastIndexOf("\\")+1).toLowerCase();
        var insertText = "<input type=\"text\" id=\"filename\" name=\"filename\" value=\""+filename+"\" hidden=\"hidden\" />";
        document.getElementById("filen").innerHTML=insertText;
        //alert(filename);
    });
</script>
</body>
</html>
