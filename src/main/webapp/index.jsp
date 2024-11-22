<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>icloud-disk2</title>
	<link rel="stylesheet" href="css/index.css">
	<link rel="icon" type="image/x-icon" href="img/云图标.png" />
	<script type="text/javascript" src="js/jquery-1.11.0.js"></script>
	<script type="text/javascript" src="js/index.js"></script>

</head>
<body>
<div id="body1">
	<div id="box">
		<div id="hr">
			<h2 class="fl">二  组  云  盘</h2>
		</div>
	</div>

	<div id="login">
		<form action="selectuser" method="post" onsubmit="">

			<div class="tableItem">
				<i class="userHead"></i> <input type="text" placeholder="用 户 名"
												required name="user" value="" />

			</div>
			<div class="tableItem">
				<i class="userLock"></i> <input type="password" placeholder="密 码"
												required name="password" />
			</div>
			<div>
				<button class="tableBtn" type="submit">登  录</button>
			</div>
		</form>
	</div>
</div>
</body>

<script>
	var errori ='<%=request.getParameter("error")%>';
	if(errori=='yes'){
		alert("登录失败!用户名或密码错误！请重新输入！");
	}
</script>

</html>