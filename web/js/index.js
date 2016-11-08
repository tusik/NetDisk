/* 

I built this login form to block the front end of most of my freelance wordpress projects during the development stage. 

This is just the HTML / CSS of it but it uses wordpress's login system. 

Nice and Simple

*/
var xmlhttp;
function sendurl() //主程序函数
{
    if (window.XMLHttpRequest){
        xmlhttp = new XMLHttpRequest();
    }else {
        xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
    }
    var username = document.getElementById("username").value;
    var pw = document.getElementById("pw").value;
    var parm = "username=" + username + "&pw="
        + pw ;//构造URL参数
    alert(parm);
    xmlhttp.open("POST", "/Login", true); //调用
    xmlhttp.setRequestHeader("cache-control","no-cache");
    xmlhttp.setRequestHeader("contentType","text/html;charset=uft-8") //指定发送的编码
    xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;");  //设置请求头信息
    xmlhttp.onreadystatechange = callback;  //判断URL调用的状态值并处理
    xmlhttp.send(parm); //设置为发送给服务器数据
}
function callback(){
//请求完成表示
    if(xmlhttp.readyState ==4 && xmlhttp.status==200){

        if (xmlhttp.responseText!=null){//这里判断不为空
            if(xmlhttp.responseText=='false'){
                //alert(xmlhttp.responseText);
                var spanid = document.getElementById("info");
                spanid.innerHTML ='Wrong username or password';
            }else {
                window.location.href=("/Panel");
            }

        }
    }
    return false;
}
function submitbtn_click(){
    return false;//阻止提交

}