<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">
	$(function () {

		$("#code").blur(function () {

			checkCode();
		});

		/*$("#save").click(function () {

			var code = $.trim($("#code").val());
			var name = $.trim($("#name").val());
			var description = $.trim($("#description").val());

			if (checkCode()) {

				$.ajax({
					url: 'settings/dictionary/type/saveType.do',
					data: {
						"code": code,
						"name": name,
						"description": description
					},
					type: 'post',
					dataType: 'json',
					success: function () {
						alert(11111111)
					}
				});
			}
		});*/

		$("#save").on("click",function () {
			if (!checkCode()){
				alert("编码不能重复或为空");
				return false;
			}
		})

	});
	function checkCode() {

		var ret=false;
		var code = $.trim($("#code").val());

		if (code==""){
			$("#msg").html("编码为空");
			return false;
		}

		$.ajax({
			url:'settings/dictionary/type/checkTypeCode.do',
			data:{
				code:code
			},
			type:'get',
			dataType:'json',
			async:false,
			success:function(data){
				if (data.success){
					$("#msg").html("编码已存在");
					ret=false;
				}else{
					$("#msg").html("");
					ret=true;
				}
			}
		});

		return ret;
	}
</script>
</head>
<body>

	<div style="position:  relative; left: 30px;">
		<h3>新增字典类型</h3>
	  	<div style="position: relative; top: -40px; left: 70%;">
			<button id="save" type="submit" form="saveType" class="btn btn-primary">保存</button>
			<button type="button" class="btn btn-default" onclick="window.history.back();">取消</button>
		</div>
		<hr style="position: relative; top: -40px;">
	</div>
	<form id="saveType" action="settings/dictionary/type/saveType.do" class="form-horizontal" role="form">
					
		<div class="form-group">
			<label for="code" class="col-sm-2 control-label">编码<span style="font-size: 15px; color: red;">*</span></label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="code" name="code" style="width: 200%;">
				<span id="msg" style="color: red"></span>
			</div>
		</div>
		
		<div class="form-group">
			<label for="name" class="col-sm-2 control-label">名称</label>
			<div class="col-sm-10" style="width: 300px;">
				<input type="text" class="form-control" id="name" name="name" style="width: 200%;">
			</div>
		</div>
		
		<div class="form-group">
			<label for="description" class="col-sm-2 control-label">描述</label>
			<div class="col-sm-10" style="width: 300px;">
				<textarea class="form-control" rows="3" id="description" name="description" style="width: 200%;"></textarea>
			</div>
		</div>
	</form>
	
	<div style="height: 200px;"></div>
</body>
</html>