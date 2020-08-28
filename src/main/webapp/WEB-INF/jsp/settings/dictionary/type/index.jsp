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

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript">

	$(function () {

		//一键全选
		$("#checkedAll").click(function () {

            $("input[name='check']").prop("checked",this.checked);

        });

		//取消全选
		$("input[name='check']").click(function () {

			$("#checkedAll").prop("checked",$("input[name='check']").length===$("input[name='check']:checked").length);

		});

		//发送数据给编辑页面
		$("#toEdit").click(function () {

			var checked=$("input[name='check']:checked");

			if (checked.length==0){

				alert("请选择要编辑的数据");
				return;
			}
			if (checked.length>1){

				alert("只能选择一条数据");
				return;
			}else {

				var code=checked.val();
				window.location.href='settings/dictionary/type/toEdit.do?code='+code;

			}

		});

		//删除字典值
		$("#delete").click(function () {

			var checked=$("input[name='check']:checked");

			if(checked.length==0){

				alert("请选择要删除的记录");
				return;
			}else {

				if(confirm("确定删除吗？")){

					var codes="";
					$.each(checked,function () {
						codes+="codes="+this.value+"&";
					});
					codes=codes.substr(0,codes.length-1);

					window.location.href="settings/dictionary/type/deleteType.do?"+codes;

				}

			}

		});

	});

</script>
</head>
<body>

	<div>
		<div style="position: relative; left: 30px; top: -10px;">
			<div class="page-header">
				<h3>字典类型列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button type="button" class="btn btn-primary" onclick="window.location.href='settings/dictionary/type/toSave.do'"><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button id="toEdit" type="button" class="btn btn-default" ><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button id="delete" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input id="checkedAll" type="checkbox" /></td>
					<td>序号</td>
					<td>编码</td>
					<td>名称</td>
					<td>描述</td>
				</tr>
			</thead>
			<tbody>

				<c:forEach items="${dtList}" var="dt" varStatus="vs">

					<tr class="active">
						<td><input value="${dt.code}" name="check" type="checkbox"></td>
						<td>${vs.count}</td>
						<td>${dt.code}</td>
						<td>${dt.name}</td>
						<td>${dt.description}</td>
					</tr>

				</c:forEach>

			</tbody>
		</table>
	</div>
	
</body>
</html>