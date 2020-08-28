<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<!DOCTYPE html>
<html>
<head>
	<base href="<%=basePath%>">
<meta charset="UTF-8">
<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script>
	$(function () {
		$("#loginAct").focus();
		$("#loginBtn").click(function () {
			login();
		})
		$(window).keydown(function (event) {
			var code = event.keyCode;
			if(code==13){
				login();
			}
		})
	});

	function login() {
		var loginAct = $.trim($("#loginAct").val());
		var loginPwd = $.trim($("#loginPwd").val());
		if(loginAct=="" || loginPwd==""){
			$("#msg").html("账号密码不能为空");
			return false;
		}
		var flag = $("#xz").prop("checked");
		var str;
		if(flag){
			str = "a";
		}
		$.ajax({
			url : "settings/user/login.do",
			data : {
				"loginAct" : loginAct,
				"loginPwd" : loginPwd,
				"flag" : str
			},
			type : "post",
			dataType : "json",
			success : function (data) {
				if(data.success){
					window.location.href = "workbench/toIndex.do";
				}else{
					$("#msg").html(data.msg);
				}
			}
		})
	}
</script>
</head>
<body>
	<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
		<img src="image/IMG_7114.JPG" style="width: 100%; height: 90%; position: relative; top: 50px;">
	</div>
	<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
		<div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2019&nbsp;动力节点</span></div>
	</div>
	
	<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
		<div style="position: absolute; top: 0px; right: 60px;">
			<div class="page-header">
				<h1>登录</h1>
			</div>
			<form action="workbench/index.html" class="form-horizontal" role="form">
				<div class="form-group form-group-lg">
					<div style="width: 350px;">
						<input class="form-control" type="text" placeholder="用户名" id="loginAct">
					</div>
					<div style="width: 350px; position: relative;top: 20px;">
						<input class="form-control" type="password" placeholder="密码" id="loginPwd">
					</div>
					<div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
						<label>
							<input type="checkbox" id="xz"> 十天内免登录
						</label>
						&nbsp;&nbsp;
						<span id="msg" style="color: #FF0000"></span>
					</div>
					<!--

						按钮组件是button，但是我们现在点击该按钮需要发出的是局部刷新错误消息的ajax请求
						既然我们要发出的是ajax请求，那么button就必须要注意一点
						一旦button组件放在了form表单中，type="submit"就是通过传统请求提交表单的意思

						注意：
							type="submit" 去掉不行，只要在form表单中，你不加type="submit"，默认系统会自动为你加type="submit"

						一定要将button设置为：type="button"
							表示当前的button就是一个普通的按钮，不提交表单，等待事件的触发


					-->
					<button type="button" id="loginBtn" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
				</div>
			</form>
		</div>
	</div>
</body>
</html>


































