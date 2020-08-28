<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core_1_1" %>
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

		var checkbox=$("input[name='check']");

		$("#checkedAll").click(function () {

			checkbox.prop("checked",this.checked);

		});

		checkbox.click(function () {

			$("#checkedAll").prop("checked",checkbox.length===$("input[name='check']:checked").length);

		});


		$("#toEdit").click(function () {

			var checked=$("input[name='check']:checked");

			if(checked.length==0){

				alert("请选择要编辑的数据");
				return;
			}
			if (checked.length>1){

				alert("一次只能编辑一条数据");
				return;
			}else {

				var id=checked.val();

				window.location.href="settings/dictionary/value/toEditValue.do?id="+id
			}

		});

		$("#deleteValueById").click(function () {

			var checked=$("input[name='check']:checked");

			if (checked==0){

				alert("请选择要删除的数据");

			}else {

				if(confirm("确定删除吗？")){

					var ids="";

					$.each(checked,function () {

						ids+="ids="+this.value+"&"
					});

					ids=ids.substr(0,ids.length-1);

					window.location.href="settings/dictionary/value/deleteValueByIds.do?"+ids;

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
				<h3>字典值列表</h3>
			</div>
		</div>
	</div>
	<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;left: 30px;">
		<div class="btn-group" style="position: relative; top: 18%;">
		  <button type="button" class="btn btn-primary" onclick="window.location.href='settings/dictionary/value/toSaveValue.do'"><span class="glyphicon glyphicon-plus"></span> 创建</button>
		  <button id="toEdit" type="button" class="btn btn-default" ><span class="glyphicon glyphicon-edit"></span> 编辑</button>
		  <button id="deleteValueById" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
		</div>
	</div>
	<div style="position: relative; left: 30px; top: 20px;">
		<table class="table table-hover">
			<thead>
				<tr style="color: #B3B3B3;">
					<td><input id="checkedAll" type="checkbox" /></td>
					<td>序号</td>
					<td>字典值</td>
					<td>文本</td>
					<td>排序号</td>
					<td>字典类型编码</td>
				</tr>
			</thead>
			<tbody>
				<c:forEach items="${dvList}" var="dv" varStatus="vs">
					<tr class="active">
						<td><input name="check" type="checkbox" value="${dv.id}"></td>
						<td>${vs.count}</td>
						<td>${dv.value}</td>
						<td>${dv.text}</td>
						<td>${dv.orderNo}</td>
						<td>${dv.typeCode}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	
</body>
</html>