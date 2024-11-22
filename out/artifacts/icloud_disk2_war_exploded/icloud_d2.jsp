<%@ page language="java" import="java.util.*"
         contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@page import="java.io.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>icloud-disk2</title>
    <link rel="stylesheet" type="text/css" href="css/icloud_d2.css"/>
    <link rel="icon" type="image/x-icon" href="img/云图标.png"/>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/css/bootstrap.min.css" rel="stylesheet">
    <script src="js/jquery-1.11.0.js" type="text/javascript" charset="utf-8"></script>
    <script src="http://code.jquery.com/jquery-1.9.1.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.1/dist/js/bootstrap.min.js"></script>
    <script type="text/javascript" src="js/icloud_d2.js"></script>
</head>

<body>


<nav class="navbar navbar-expand-lg navbar-light bg-light">
    <div class="container-fluid">
        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <h3 style="font-family: 华文隶书;font-size:45px;">
                <img src="img/yunpan.png" alt="" width="60" height="45" class="d-inline-block align-text-top">
                二组云盘
            </h3>
            <div style="width: 550px;height: 50px"></div>
            <form action="searchFiles" method="get" class="d-flex">
                <input type="hidden" name="sign1" id="sign1" value="0"/>
                <select id="searchType" name="searchType" value="1" style="width:100px" onchange="Search();">
                    <option value="1">文件名</option>
                    <option value="2">上传用户</option>
                    <option value="3">上传时间</option>
                </select>
                <div id="Layer1" style="visibility: visible;position:absolute;margin-left:120px">
                    <input type="text" name="fileName1" id="fileName1" size=20>
                </div>
                <div id="Layer2" style="visibility: hidden;position:absolute;margin-left:120px">
                    <input type="text" name="fileUser1" id="fileUser1">
                </div>
                <div id="Layer3" style="visibility: hidden;position:absolute;margin-left:120px">
                    <input type="date" name="fileTime1" id="fileTime1">
                    <script type="text/javascript">
                        var now = new Date();
                        var day = ("0" + now.getDate()).slice(-2);
                        var month = ("0" + (now.getMonth() + 1)).slice(-2);
                        var today = now.getFullYear() + "-" + (month) + "-" + (day);
                        $('#fileTime1').val(today);
                    </script>
                </div>
                <input type="submit" value="提交" style="margin-left: 200px;width: 60px;background-color: skyblue;"
                       onclick="choose1(1);openCity(event, 'fileShow')">
            </form>
            <p class="text-center" style="font-family: 楷体;margin: auto;font-size: 20px;color: #2991dc">
                <img src="img/i_u.png" style="weight: 20px;height: 20px;"/> ${userName }
                <a id="out" href="index.jsp">
                    <button type="button" class="btn btn-info">退出登录</button>
                </a>
            </p>
        </div>
    </div>
</nav>

<div id="left">
    <div class="tabb">
        <form action="typeClassify" method="get" id="form11">
            <input type="hidden" id="type2" name="type" value="0"/>
            <input type="hidden" name="pageNum" id="pageNum2" value="1"/>
            <input type="hidden" name="sign2" id="sign2" value="0"/>
            <button class="tablinks" onclick="choose2(0);openCity(event, 'fileShow')">
                <img src="img/cloud.png" style="weight: 20px;height: 20px"/>
                企业云盘
            </button>
            <button class="tablinks" onclick="choose2(2);openCity(event, 'fileShow')">
                <img src="img/file.png" style="weight: 20px;height: 20px"/>
                文档
            </button>
            <button class="tablinks" onclick="choose2(1);openCity(event, 'fileShow')">
                <img src="img/picture.png" style="weight: 20px;height: 20px"/>
                图片
            </button>
            <button class="tablinks" onclick="choose2(3);openCity(event, 'fileShow')">
                <img src="img/video.png" style="weight: 20px;height: 20px"/>
                视频
            </button>
            <button class="tablinks" onclick="choose2(4);openCity(event, 'fileShow')">
                <img src="img/music.png" style="weight: 20px;height: 20px"/>
                音乐
            </button>
            <button class="tablinks" onclick="choose2(5);openCity(event, 'fileShow')">
                <img src="img/others.png" style="weight: 20px;height: 20px"/>
                其他
            </button>
        </form>
        <c:if test="${permissions != '0'}">
            <a href="main.jsp">
                <button type="button" class="btn btn-primary">
                    <img src="img/user.png" style="weight: 20px;height: 20px"/>
                    用户管理入口
                </button>
            </a>
        </c:if>
    </div>
</div>

<div id="body3">
    <div id="top">
        <div class="row">
            <div class="col-md-7">
                <button class="filbutoom1" data-bs-toggle="modal" data-bs-target="#myModal">
                    <img src="img/up.png" style="weight: 20px;height: 20px"/>上传
                </button>
                <button class="filbutoom1" onclick="open_win()" id="dl">
                    <img src="img/download.png" style="weight: 20px;height: 20px"/>下载
                </button>
                <a target='_blank' href='userlist3'>刷新获取</a>
                存储用量:<b>${size1.toString()}</b>MB
            </div>
            <div class="col-md-5">
                <form id="sort" action="SortFiles">
                    <input type="hidden" id="type3" name="type">
                    <input type="hidden" name="pageNum" id="pageNum3"/>
                    <input type="hidden" name="sign3" id="sign3" value="0"/>
                    <button class="filbutoom2"
                            onclick="choose3(1);openCity(event, 'fileShow')">
                        文件名排序
                    </button>
                    <button class="filbutoom2"
                            onclick="choose3(2);openCity(event, 'fileShow')">
                        大小排序↓
                    </button>
                    <button class="filbutoom2"
                            onclick="choose3(3);openCity(event, 'fileShow')">
                        上传时间排序↓
                    </button>
                </form>
            </div>
        </div>
    </div>

    <!--文件上传弹窗-->
    <form method="post" action="uploadMultiple" enctype="multipart/form-data" onsubmit="return check()">
        <div class="modal fade" id="myModal" tabindex="-1" aria-labelledby="myModalLabel" aria-hidden="true">
            <div class="modal-dialog">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="exampleModalLabel">上传</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body"><!--弹窗的内容,实现文件上传-->
                        <div class="upfilebox" style="width:300px;height: 80px;background-color: aliceblue">
                            <input type="file" name="attachment" id="feli1" multiple="multiple"><br>
                            <input type="hidden" name="userfile" value="${userName}">
                            <input type="submit" value="上传" id="feli2">
                        </div>
                    </div>
                    <div class="modal-footer">
                    </div>
                </div>
            </div>
        </div>
    </form>

    <!--文件显示-->
    <form action="" method="post" name="form1" id="Form">
    <div id="fileShow" style="position: absolute;margin-top: 20px;height:650px;width: 1300px;font-family: 新宋体" class="tabb2">
        <table border="1" class="table table-bordered table-hover">
            <thead>
            <tr class="success">
                <th><input id="checkAll" type="hidden" aria-label="..."></th>
                <th style="text-align: center">文件名</th>
                <th style="text-align: center">上传用户</th>
                <th style="text-align: center">文件类型</th>
                <th style="text-align: center">文件大小</th>
                <th style="text-align: center">上传时间</th>
                <th style="text-align: center">修改时间</th>
                <th style="text-align: center"  colspan="3">操作</th>
            </tr>
            </thead>
            <tbody id="list">
            <c:if test="${!empty pageInfo.list }">
                <c:forEach items="${pageInfo.list}" var="file">
                        <tr style="text-align: center">
                            <td><input id="ID" type="radio" value="${file.id}" name="id"></td>
                            <td>${file.fileName}</td>
                            <td>${file.fileUser}</td>
                            <td>${file.fileType}</td>
                            <td>${file.fileSize}</td>
                            <td><fmt:formatDate value="${file.createTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td><fmt:formatDate value="${file.updateTime}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                            <td>
                                <input type="hidden" name="fileId" id="oldId"/>
                                <input type="hidden" name="newFileName" id="name"/>
                                <button class="btn btn-primary" style="width: 80px;height: 30px" type="submit" onclick="return bounceRename(this)">重命名</button>
                            </td>
                            <form action="" method="post"><input type="hidden"></form>
                            <td>
                                <form action="userlist" method="post">
                                    <input type="hidden" name="fileName" value="${file.fileName}"/>
                                    <input type="hidden" name="fileUser" value="${file.fileUser}"/>
                                    <button class="btn btn-success" style="width: 70px;height: 30px">分享</button>
                                </form>
                            </td>
                            <td>
                                <form action="deleteFiles" method="post">
                                    <input type="hidden" name="fileUser" value="${file.fileUser}"/>
                                    <input type="hidden" name="fileName" value="${file.fileName}"/>
                                    <input type="hidden" name="fileId" value="${file.id}"/>
                                    <button class="btn btn-danger" style="width:70px;height: 35px">删除</button>
                                </form>
                            </td>
                        </tr>
                </c:forEach>
            </c:if>
            </tbody>
        </table>
        <!--接受数值，分页跳转-->
        <%
        String type =request.getParameter("type");
        request.setAttribute("type", type);
        String sign3=request.getParameter("sign3");
        String sign2=request.getParameter("sign2");
        String sign1=request.getParameter("sign1");
        request.setAttribute("sign1",sign1);
        String searchType=request.getParameter("searchType");
        String fileName1=request.getParameter("fileName1");
        String fileUser1=request.getParameter("fileUser1");
        String fileTime1=request.getParameter("fileTime1");
        request.setAttribute("searchType",searchType);
        request.setAttribute("fileName1",fileName1);
        request.setAttribute("fileUser1",fileUser1);
        request.setAttribute("fileTime1",fileTime1);
        String function="";
        if(sign1!=null && sign2==null && sign3==null)
            function="searchFiles";
        if(sign3!=null && sign2==null && sign1==null)
            function="SortFiles";
        if(sign3==null && sign2!=null && sign1==null)
            function="typeClassify";
        request.setAttribute("function",function);
    %>
        <tr>
            <nav aria-label="Page navigation">
                <ul id="all" class="pagination">
                    <c:if test="${sign1==1}">
                    <li>
                        <c:if test="${pageInfo.pageNum-1<1}">
                            <a href="" aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </c:if>
                        <c:if test="${pageInfo.pageNum-1>=1}">
                            <a href="${function}?pageNum=${pageInfo.pageNum-1}&searchType=${searchType}&fileName1=${fileName1}&fileUser1=${fileUser1}&fileTime1=${fileTime1}"
                               aria-label="Previous">
                                <span aria-hidden="true">&laquo;</span>
                            </a>
                        </c:if>
                    </li>
                    <li class="active"><a>${pageInfo.pageNum}</a></li>
                    <li>
                        <c:if test="${pageInfo.pageNum+1>pageInfo.pages}">
                            <a href="" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </c:if>
                        <c:if test="${pageInfo.pageNum+1<=pageInfo.pages}">
                            <a href="${function}?pageNum=${pageInfo.pageNum+1}&searchType=${searchType}&fileName1=${fileName1}&fileUser1=${fileUser1}&fileTime1=${fileTime1}" aria-label="Next">
                                <span aria-hidden="true">&raquo;</span>
                            </a>
                        </c:if>
                    </li>
                    <span style="font-size: 14px;margin-left: 5px;">共${pageInfo.pages}页,共${pageInfo.total}个文件</span>
                    </c:if>
                    <c:if test="${sign1!=1}">
                        <li>
                            <c:if test="${pageInfo.pageNum-1<1}">
                                <a href="${function}?pageNum=${pageInfo.pageNum}&type=${type}" aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </c:if>
                            <c:if test="${pageInfo.pageNum-1>=1}">
                                <a href="${function}?pageNum=${pageInfo.pageNum-1}&type=${type}"
                                   aria-label="Previous">
                                    <span aria-hidden="true">&laquo;</span>
                                </a>
                            </c:if>
                        </li>
                        <li class="active"><a>${pageInfo.pageNum}</a></li>
                        <li>
                            <c:if test="${pageInfo.pageNum+1>pageInfo.pages}">
                                <a href="${function}?pageNum=${pageInfo.pageNum}&type=${type}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </c:if>
                            <c:if test="${pageInfo.pageNum+1<=pageInfo.pages}">
                                <a href="${function}?pageNum=${pageInfo.pageNum+1}&type=${type}" aria-label="Next">
                                    <span aria-hidden="true">&raquo;</span>
                                </a>
                            </c:if>
                        </li>
                        <span style="font-size: 14px;margin-left: 5px;">共${pageInfo.pages}页,共${pageInfo.total}个文件</span>
                    </c:if>
                </ul>
            </nav>
        </tr>
    </div>
        <button id="download" name="id" type="submit"
                onclick="if((postform(this))){form1.action='download'}else{return false;}"></button>
    </form>
</div>
<script>
    var errori = '<%=request.getParameter("error")%>';
    if (errori == 'yes') {
        alert("删除成功");
    }
    var uploadi2 = '<%=request.getParameter("ress")%>';
    if (uploadi2 == 'yes') {
        alert("上传成功");
    }
    var uploadi4  ='<%=request.getParameter("update")%>';
    if(uploadi4=='yes'){
        alert("修改成功");
    }
    var errori2  ='<%=request.getParameter("errorDuplicate")%>';
    if (errori2 == 'yes') {
        alert("文件名称已存在");
    }
    var errori3  ='<%=request.getParameter("errorExcess")%>';
    if (errori3 == 'yes') {
        alert("文件名称长度超过50个字符");
    }
</script>
</body>
</html>