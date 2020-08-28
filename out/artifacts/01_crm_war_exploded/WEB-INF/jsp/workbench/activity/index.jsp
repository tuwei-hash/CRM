<%@ taglib prefix="C" uri="http://java.sun.com/jsp/jstl/core" %>
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
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination/jquery.bs_pagination.min.css">

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript" src="jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination/en.js"></script>

<script type="text/javascript">

	$(function(){

		//刷新页面
		pageList(1,5);

		//条件查询
		$("#searchActivityByCondition").click(function () {

			$("#hidden-name").val($("#search-name").val());
			$("#hidden-owner").val($("#search-owner").val());
			$("#hidden-startDate").val($("#search-startDate").val());
			$("#hidden-endDate").val($("#search-endDate").val());

			pageList(1, $("#activityPage").bs_pagination("getOption", "rowsPerPage"));

		});


		//日期插件
		$(".time").datetimepicker({
			minView: "month",
			language:  'zh-CN',
			format: 'yyyy-mm-dd',
			autoClose: true,
			todayBtn: true,
			pickerPosition: "bottom-left"
		});

		//打开添加市场活动模态窗口
		$("#toSaveActivityBtn").click(function () {

			var id="${user.id}";

			$("#create-owner").val(id);

			$("#createActivityModal").modal("show");
		});

		//保存添加市场活动，清空表单，关闭模态窗口
		$("#saveActivityBtn").click(function () {

			$.ajax({
				url:'workbench/activity/saveActivity.do',
				data:{
					owner:$.trim($("#create-owner").val()),
					name:$.trim($("#create-name").val()),
					startDate:$.trim($("#create-startTime").val()),
					endDate:$.trim($("#create-endTime").val()),
					cost:$.trim($("#create-cost").val()),
					description:$.trim($("#create-description").val())
				},
				type:'post',
				dataType: 'json',
				success:function (data) {

					if (data.success){

						//表单重置
						$("#saveActivityForm")[0].reset();

						$("#createActivityModal").modal("hide");

						pageList(1,$("#activityPage").bs_pagination("getOption","rowsPerPage"))

					}else{

						alert("添加市场活动失败");
					}
				}

			});

		});


		//全选和取消全选
		$("#checkedAll").click(function () {

			$("#activityBody input[type='checkbox']").prop("checked",this.checked);
		});
		//全选和取消全选
		$("#activityBody").on("click","input[type='checkbox']",function () {

			$("#checkedAll").prop("checked",$("#activityBody input[type='checkbox']").size()===$("#activityBody input[type='checkbox']:checked").size());
		});


		//打开修改市场活动模态窗口
		$("#toEditActivityBtn").click(function () {

			var $checked = $("#activityBody input[type='checkbox']:checked");

			if ($checked.length==0){

				alert("请选择需要修改的数据");

			}else if ($checked.length>1){

				alert("一次只能修改一条数据");

			}else {

				var id=$checked.val();

				$.ajax({
					url:'workbench/activity/toEditActivity.do',
					data:{
						id:id
					},
					type:'get',
					dataType:'json',
					success:function (data) {

						$("#edit-id").val(data.id);
						$("#edit-name").val(data.name);
						$("#edit-owner").val(data.owner);
						$("#edit-startDate").val(data.startDate);
						$("#edit-endDate").val(data.endDate);
						$("#edit-cost").val(data.cost);
						$("#edit-description").val(data.description);

						$("#editActivityModal").modal("show");

					}

				});

			}

		});

		//保存修改市场活动，关闭模态窗口
		$("#editActivityBtn").click(function () {

			$.ajax({
				url:'workbench/activity/editActivity.do',
				data:{
					id:$("#edit-id").val(),
					owner:$("#edit-owner").val(),
					name:$("#edit-name").val(),
					startDate:$("#edit-startDate").val(),
					endDate:$("#edit-endDate").val(),
					cost:$("#edit-cost").val(),
					description:$("#edit-description").val()
				},
				type:'post',
				dataType:'json',
				success:function (data) {

					if (data.success){

						pageList($("#activityPage").bs_pagination("getOption","currentPage")
								,$("#activityPage").bs_pagination("getOption","rowsPerPage"));

						$("#editActivityModal").modal("hide");

					}else {

						alert("修改市场活动失败");
					}

				}

			});

		});


		//删除市场活动
		$("#deleteActivityBtn").click(function () {

			var $checked = $("#activityBody input[type='checkbox']:checked");

			if ($checked.length==0){

				alert("至少选择一条数据");

			}else{

				if (confirm("确定删除吗？")){

					var ids="";
					//取得要删除的市场活动id
					$.each($checked,function (i,c) {

						ids+="ids="+c.value+"&";

					});
					ids=ids.substr(0,ids.length-1);

					$.ajax({
						url:'workbench/activity/deleteActivityByIds.do',
						data:ids,
						type:'post',
						dataType:'json',
						success:function (data) {

							if (data.success){

								pageList(1,$("#activityPage").bs_pagination("getOption","rowsPerPage"));
							}

						}

					});

				}

			}

		});


		//导出全部市场活动，用Excel表导出
		$("#exportActivityAllBtn").click(function () {

			window.location.href='workbench/activity/exportActivityAll.do'

		});

		//选择导出
		$("#exportActivityXzBtn").click(function () {

			var $checked = $("#activityBody input[type='checkbox']:checked");

			if ($checked.length===0) {

				alert("请选择需要导出的数据")
			}else {

				var ids="";

				$.each($checked,function () {

					ids+="ids="+this.value+"&";

				});
				ids.substr(0,ids.length-1);

				window.location.href='workbench/activity/exportActivityByIds.do?'+ids

			}

		});

		$("#importActivityBtn").click(function () {

			var fileName=$("#activityFile").val();

			var suffix=fileName.substr(fileName.lastIndexOf("."));

			if (!(suffix==".xls"||suffix==".xlsx")){

				alert("不是一个有效的文件");
				return false;
			}

			var file=$("#activityFile")[0].files[0];

			if (file.size>1024*1024*5){

				alert("文件不能超过5M");

				return false;
			}

			var formData=new FormData;

			formData.append("file",file);

			$.ajax({
				url:'workbench/activity/importActivity.do',
				data:formData,
				type:"post",
				processData:false,
				contentType:false,
				dataType:"json",
				success:function (data) {

					if (data.success){

						pageList(1,2);

						$("#activityFile").val("");

						$("#importActivityModal").modal("hide");

					}else{

						alert("文件上传失败");
					}

				}
			})

		});

	});



	function pageList(pageNo,pageSize) {

		$("#checkedAll").prop("checked",false);

		$.ajax({
			url:'workbench/activity/getPageList.do',
			data:{
				pageNo:pageNo,
				pageSize:pageSize,
				owner:$.trim($("#hidden-owner").val()),
				name:$.trim($("#hidden-name").val()),
				startDate:$.trim($("#hidden-startDate").val()),
				endDate:$.trim($("#hidden-endDate").val())
			},
			type:'get',
			dataType: 'json',
			success:function (data) {

				var html="";

				$.each(data.dataList,function (index,activity) {
					html+="<tr class='active'>";
					html+="<td><input type='checkbox' value='"+activity.id+"'/></td>";
					html+='<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/toDetailActivity.do?id='+activity.id+'\';">'+activity.name+'</a></td>';
					html+="<td>"+activity.owner+"</td>";
					html+="<td>"+activity.startDate+"</td>";
					html+="<td>"+activity.endDate+"</td>";
					html+="</tr>";

				});

				$("#activityBody").html(html);

				var totalPages=data.total%pageSize===0?data.total/pageSize:(data.total/pageSize)+1;
				//翻页插件
				$("#activityPage").bs_pagination({
				currentPage: pageNo, // 页码
				rowsPerPage: pageSize, // 每页显示的记录条数
				maxRowsPerPage: 5, // 每页最多显示的记录条数
				totalPages: totalPages, // 总页数
				totalRows: data.total, // 总记录条数

				visiblePageLinks: 3, // 显示几个卡片

				showGoToPage: true,
				showRowsPerPage: true,
				showRowsInfo: true,
				showRowsDefaultInfo: true,

					onChangePage : function(event, data){
						pageList(data.currentPage , data.rowsPerPage);
					}

				});

			}

		});

	}
	
</script>
</head>
<body>

	<input type="hidden" id="hidden-name"/>
	<input type="hidden" id="hidden-owner"/>
	<input type="hidden" id="hidden-startDate"/>
	<input type="hidden" id="hidden-endDate"/>

	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form id="saveActivityForm" class="form-horizontal" role="form">
					
						<div class="form-group">
							<label for="create-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-owner">
									<option></option>
									<C:forEach items="${userList}" var="u">
										<option value="${u.id}">${u.name}</option>
									</C:forEach>
								</select>
							</div>
                            <label for="create-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-name">
                            </div>
						</div>
						
						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" autocomplete="off" id="create-startTime">
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control time" autocomplete="off" id="create-endTime">
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="saveActivityBtn" type="button" class="btn btn-primary" >保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">
				
					<form class="form-horizontal" role="form">

						<input type="hidden" id="edit-id"/>

						<div class="form-group">
							<label for="edit-owner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-owner">
									<option></option>
									<C:forEach items="${userList}" var="u">
										<option value="${u.id}">${u.name}</option>
									</C:forEach>
								</select>
							</div>
                            <label for="edit-name" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-name">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startDate" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-startDate">
							</div>
							<label for="edit-endDate" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-endDate">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-description" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-description"></textarea>
							</div>
						</div>
						
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button id="editActivityBtn" type="button" class="btn btn-primary" >更新</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls或.xlsx格式]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
					</div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS/XLSX的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input id="search-name" class="form-control" type="text">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input id="search-owner" class="form-control" type="text">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input id="search-startDate" class="form-control" type="text"/>
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input id="search-endDate" class="form-control" type="text"/>
				    </div>
				  </div>
				  
				  <button id="searchActivityByCondition" type="button" class="btn btn-default">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button id="toSaveActivityBtn" type="button" class="btn btn-primary"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button id="toEditActivityBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button id="deleteActivityBtn" type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input id="checkedAll" type="checkbox" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="activityBody">

					</tbody>
				</table>
			</div>
			
			<div style="
			height: 50px; position: relative;top: 30px;">
				<div id="activityPage"></div>
			</div>
			
		</div>
		
	</div>
</body>
</html>