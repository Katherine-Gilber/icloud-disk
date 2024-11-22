<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<% String appPath = request.getContextPath(); %>

<html>
<head>
    <title>${userName}用户管理系统</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="icon" type="image/x-icon" href="img/云图标.png"/>
    <!-- 引入 Bootstrap -->
    <link href="https://cdn.bootcss.com/bootstrap/3.3.7/css/bootstrap.min.css" rel="stylesheet">
    <script src="https://cdn.bootcss.com/bootbox.js/5.3.2/bootbox.js"></script>
    <script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>
    <script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>
    <script type="text/javascript">sessionStorage.setItem('permissions',"${permissions}");</script>
    <script type="text/javascript" src="./js/main.js"></script>
</head>

<body>
<div class="container">
    <h3 style="text-align: center">用户管理系统</h3>
    <div style="float: left;">
        <form class="form-inline">
            <div class="form-group">
                <label for="userId">用户id</label>
                <input name="userId" type="text" class="form-control" id="userId" >
            </div>
            <div class="form-group">
                <label for="userName">用户名</label>
                <input name="userName" type="text" class="form-control" id="userName" >
            </div>
            <div class="form-group">
                <c:if test="${permissions == '2'}">
                    <label for="permissions">等级</label>
                    <select name="permissions" class="form-control" id="permissions">
                        <option value="">所有</option>
                        <option value="0">用户</option>
                        <option value="1">管理员</option>
                    </select>
                </c:if>
            </div>
            <button id="submit1" type="button" class="btn btn-default">查询</button>
        </form>
    </div>
    <form action="delete" method="get">
        <div style="float: right;margin-bottom: 15px">
            <!-- 点击按钮触发添加用户弹窗 -->
            <button class="btn btn-primary" data-toggle="modal"  data-target="#addUserModal">添加用户</button>
            <input class="btn btn-danger" type="submit" onclick="alert('批量删除成功')" value="批量删除">
            <a href="icloud_d2.jsp"><button type="button" class="btn btn-primary">返回首页</button></a>
        </div>
        <table border="1" class="table table-bordered table-hover">
            <tr class="success">
                <th>选择</th>
                <th>用户id</th>
                <th>用户名</th>
                <th>密码</th>
                <c:if test="${permissions == '2'}">
                    <th>等级</th>
                </c:if>
                <th>操作</th>
            </tr>
            <tbody id="info">
            </tbody>
        </table>
    </form>
        <div>
            <nav aria-label="Page navigation">
                <ul id="all" class="pagination">
                </ul>
            </nav>
        </div>

        <!-- 添加用户弹框 -->
        <form method="post" action="insert" class="form-horizontal" role="form" id="userAddModal" onsubmit="return checkform()" style="margin: 20px;">
            <div class="modal fade" id="addUserModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
                <div class="modal-dialog">
                    <div class="modal-content">
                        <div class="modal-header">
                            <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                                &times;
                            </button>
                            <h4 class="modal-title" id="myModalLabel">
                                添加用户信息
                            </h4>
                        </div>
                        <div class="modal-body">
                            <form class="form-horizontal" role="form" id="userAddModalForm">

                                <div class="form-group">
                                    <label for="user_name" class="col-sm-2 control-label" >用户名</label>
                                    <div class="col-sm-10">
                                        <input type="text" class="form-control" name="username" value="" id="user_name"
                                               placeholder="请输入用户名(3-18位中英文、数字、符号的组合)">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label for="user_password" class="col-sm-2 control-label">密码</label>
                                    <div class="col-sm-10">
                                        <input type="password" name="password" class="form-control"  value=""
                                               id="user_password" placeholder="请输入密码(6-16位英文、数字、符号的组合)">
                                    </div>
                                </div>

                                <div class="form-group">
                                    <label class="col-sm-2 control-label">用户权限</label>
                                    <div class="col-sm-10">
                                        <c:if test="${permissions == '2'}">
                                            <label class="radio-inline">
                                                <input type="radio" name="user_permission" id="user_permission1"
                                                       value="管理员"> 管理员
                                            </label>
                                        </c:if>
                                        <label class="radio-inline">
                                            <input type="radio" name="user_permission" id="user_permission2" value="普通用户"
                                                   checked="checked"> 普通用户
                                        </label>
                                        <input type="hidden" name="permissions" id="permission" value="0" />
                                    </div>
                                </div>
                            </form>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-default" data-dismiss="modal">关闭
                            </button>
                            <button type="submit" class="btn btn-primary" id="userAddModalSaveBtn" onclick="check()">
                                提交
                            </button><span id="tip"> </span>
                        </div>
                    </div>
                </div>
            </div>
        </form>
</div>
</body>

<script>
    var Exist  ='<%=request.getParameter("exist")%>';
    if (Exist == 'yes') {
        alert("该用户已存在！请重新输入！");
    }

    var Add  ='<%=request.getParameter("add")%>';
    if (Add == 'yes') {
        alert("添加成功！");
    }
</script>

</html>