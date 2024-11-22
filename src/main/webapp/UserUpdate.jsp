<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2021/8/20
  Time: 21:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java"
         pageEncoding="UTF-8" isELIgnored="false"%>
<html>
<head>
    <title>修改用户</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script type = "text/javascript">
        function check() {
            if (document.form1.name.value == "")
            {
                alert("请填写用户名！");
                document.form1.username.focus();
                return false;
            }
            if (document.form1.permissions.value == "")
            {
                alert("请设置用户权限！");
                document.form1.username.focus();
                return false;
            }
            if (document.form1.password.value.length>20)
            {
                alert("密码不能多于20个字符！");
                document.form1.password.focus();
                return false ;
            }
            if (document.form1.permissions.value!= "0" && document.form1.permissions.value!= "1")
            {
                alert("权限设置为：0-普通用户；1-管理员");
                document.form1.permissions.focus();
                return false ;
            }

            alert("修改成功");
            document.form1.submit();
        }
    </script>
    <style>
        a{
            text-decoration:none;
            color:#000000;
        }
    </style>
</head>
<body>
<div class="container">

    <div class="row clearfix">
        <div class="col-md-12 column">
            <div class="page-header">
                <h1>
                    <small>修改用户信息</small>
                </h1>
            </div>
        </div>
    </div>

    <%
        //获得信息
        String str=request.getParameter("id");
        int id=Integer.parseInt(str);
        String name=request.getParameter("name");
        String password=request.getParameter("password");
        String permissions=request.getParameter("permissions");
    %>
    <form name="form1" action="updateUser" method="post" οnsubmit="return check()">
        <input type="hidden" name="id" placeholder=<%=id%> value=<%=id%>>
        用户名：<%=name%> &nbsp;&nbsp;&nbsp;&nbsp;<input type="hidden" name="name" placeholder=<%=id%> value="<%=name%>" >
        用户密码：<input type="text" required="required"  autocomplete="off" name="password" placeholder=<%=password%> value="<%=password%>">
        &nbsp;&nbsp;&nbsp;
        <c:if test="${permissions==1}">
            用户权限：<%=permissions%>  <input type="hidden" required="required" style="border:0" name="permissions" placeholder=<%=permissions%> value="<%=permissions%>" readonly>
        </c:if>
        <c:if test="${permissions==2}">
            用户权限：<input type="text" required="required"  autocomplete="off" title="输入0或1；0表示普通用户，1表示管理员" placeholder=<%=permissions%> name="permissions" value="<%=permissions%>">
        </c:if>
        &nbsp;&nbsp;
        <input type="button" name="btn" onclick="check()" value="提交"/>
        &nbsp;&nbsp;
        <a href="main.jsp"><button type="button" >返回</button></a>
    </form>

</div>
</body>
</html>
