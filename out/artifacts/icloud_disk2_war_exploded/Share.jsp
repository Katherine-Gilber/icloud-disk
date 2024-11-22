<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>分享文件</title>
</head>

<style type="text/css">
    .wrapper {position: relative;}
    #input {position: absolute;top: 0;left: 0;opacity: 0;z-index: -10;}
    #body1{
        width: auto;
        height: 800px;
        background-image: url(img/bk.jpg);
    }
    .one,.two{
        width: 20%;
        height: 20px;
        box-sizing: border-box;
    }
    #text,#text9{
        width: 30%;
        height: 70px;
    }
    #se{
        width:200px;
        height:20px;
    }
</style>
<body>

<div id="body1">
    <center>
        <br /><br /><font><b>分享文件</b></font><br /><br /><br />

        <p >URL有效期默认为五分钟，若要更改，请选择，确认修改之后请点击刷新按钮重新生成链接</p>
        <div class="one"><p id="text2">URL有效期</p></div>&ensp;&ensp;<div class="two">
        <form action="show" method="post">
            <select id="se" name="urlTime">
                <option value="60">1分钟</option>
                <option value="3600">1小时</option>
                <option value="86400">1天</option>
                <option value="604800">1周</option>
                <option value="2419200">1月</option>
            </select>
            <input type="submit" value="刷新" />
        </form>
    </div>
        <div>
            <c:forEach items="${userlist2}" var="user2">

                <b><textarea class="text" id="text9"> ${user2.toString()}</textarea> </b>

            </c:forEach>
            <div class="three"> <p id="text3">分享链接如下</p></div>
            <form action="userlist" method="get">
            </form>
            <c:forEach items="${userlist}" var="user">

                <b><textarea class="text" id="text"> ${user.toString()}</textarea> </b>

            </c:forEach>
            <br />
            <textarea id="input">url显示</textarea>
            <br /><br /></div>


        <div class="wrapper">

            <button onclick="interText()">&ensp;<a href="${userlist.get(0).toString()}">打开链接</a>&ensp;</button>
            <!--<button onclick="copyText()">复制链接</button>&ensp;&ensp;-->
            <input id="btnClose" type="button" value="关闭本页" onClick="custom_close()" />
        </div>

    </center>
    <script type="text/javascript">

        function copyText() {

            //     var arrValue=$("text.").text();
            var text = (document.getElementsByClassName("text")[0]).innerHTML;
            //  var text2 = document.getElementsById("text2");
            var input = document.getElementById("input");
            input.value = text; // 修改文本框的内容
            input.select(); // 选中文本
            document.execCommand("copy"); // 执行浏览器复制命令
            alert("复制成功");
        }
    </script>
    <script language="javascript">
        function custom_close(){
            alert("你确定要关闭本页吗");
            window.history.back(-1);
        }
    </script>

</div>
</body>
</html>