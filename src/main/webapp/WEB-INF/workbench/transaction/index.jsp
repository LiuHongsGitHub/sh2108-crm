<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">

	<%--设置字符集--%>
	<meta charset="UTF-8">
	<%--先导入jquery--%>
	<link href="/crm/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
	<link href="/crm/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
	<link rel="stylesheet" href="/crm/jquery/bs_pagination/jquery.bs_pagination.min.css"/>
	<script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="/crm/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/crm/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="/crm/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>
	<script src="/crm/jquery/bs_pagination/en.js"></script>
	<script src="/crm/jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="/crm/jquery/layer/layer.js"></script>

<script type="text/javascript">

	$(function(){
		
		
		
	});
	
</script>
</head>
<body>

	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>交易列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="owner">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">客户名称</div>
				      <input class="form-control" type="text" id="customerName">
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">阶段</div>
					  <select class="form-control" id="stage">
					  	<option></option>
					  	<c:forEach items="${map['stage']}" var="stage">
							<option value="${stage.value}">${stage.text}</option>
						</c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">类型</div>
					  <select class="form-control" id="type">
					  	<option></option>
					  	<c:forEach items="${map['transactionType']}" var="transactionType">
							<option value="${transactionType.value}">${transactionType.text}</option>
						</c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">来源</div>
				      <select class="form-control" id="create-clueSource">
						  <option></option>
						  <c:forEach items="${map['source']}" var="source">
							  <option value="${source.value}">${source.text}</option>
						  </c:forEach>
						</select>
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">联系人名称</div>
				      <input class="form-control" type="text" id="contactName">
				    </div>
				  </div>
				  
				  <button type="queryBtn" class="btn btn-default" onclick="query()">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 10px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" onclick="window.location.href='/crm/toView/workbench/transaction/save';"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" onclick="editTran()"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="fatherCheckbox" /></td>
							<td>名称</td>
							<td>客户名称</td>
							<td>阶段</td>
							<td>类型</td>
							<td>所有者</td>
							<td>来源</td>
							<td>联系人名称</td>
						</tr>
					</thead>
					<tbody id="tran-body">
						<%--<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.jsp';">动力节点-交易01</a></td>
							<td>动力节点</td>
							<td>谈判/复审</td>
							<td>新业务</td>
							<td>zhangsan</td>
							<td>广告</td>
							<td>李四</td>
						</tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 20px;">
				<div id="tranPage"></div>
			</div>
			
		</div>
		
	</div>
<script>
	//解决分页的乱码问题
	var rsc_bs_pag = {
		go_to_page_title: 'Go to page',
		rows_per_page_title: 'Rows per page',
		current_page_label: 'Page',
		current_page_abbr_label: 'p.',
		total_pages_label: 'of',
		total_pages_abbr_label: '/',
		total_rows_label: 'of',
		rows_info_records: 'records',
		go_top_text: '首页',
		go_prev_text: '上一页',
		go_next_text: '下一页',
		go_last_text: '末页'
	};
	//第一次进入进行查询
	refresh(1,3);
	//刷新页面进行查询
	function refresh(page,pageSize) {
		$.get("/crm/workbench/transaction/list",{
			'page':page,
			'pageSize':pageSize,
			'name':$("#name").val(),
			'owner':$("#owner").val(),
			'customerId':$("#customerName").val(),
			'source':$("#create-clueSource").val(),
			'stage':$("#stage").val(),
			'contactsId':$("#contactName").val(),
			'type':$("#type").val()
		},function (data) {
			//data是页面集合，包含页码，总记录数，客户集合等等
			//1.清空表体
			$("#tran-body").empty();
			/*$.each(data.list,function (i,n) {
				alert(n.name)
			})*/
			//循环拼接
			for (var i in data.list) {
				var tran = data.list[i];
				//拼接单选框
				$('#tran-body').append("<tr>\n" +
						"\t\t\t\t\t\t\t<td><input value="+tran.id+" onclick='childCheck()'  class='son' type=\"checkbox\"  /></td>\n" +
						"\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\" href='/crm/toView/workbench/transaction/detail?id="+tran.id+" ' >"+tran.name+"</a></td>\n" +
						"\t\t\t\t\t\t\t<td>"+tran.customerId+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+tran.stage+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+tran.type+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+tran.owner+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+tran.source+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+tran.contactsId+"</td>\n" +
						"\t\t\t\t\t\t</tr>"
				);
			}

			//分页
			$("#tranPage").bs_pagination({
				currentPage: data.pageNum, // 页码
				rowsPerPage: data.pageSize, // 每页显示的记录条数
				maxRowsPerPage: 20, // 每页最多显示的记录条数
				totalPages: data.pages, // 总页数
				totalRows: data.size, // 总记录条数
				visiblePageLinks: 4, // 显示几个卡片
				showGoToPage: true,
				showRowsPerPage: true,
				showRowsInfo: true,
				showRowsDefaultInfo: true,
				//该函数只要操作分页插件都会触发该函数
				onChangePage: function (event, obj) {
					refresh(obj.currentPage,obj.rowsPerPage);}
			});
		},'json');
	}
	//点击查询并分页
	function query() {
		refresh(1,3);
	}
	//父勾选框事件
	$("#fatherCheckbox").click(
			function () {
				//声明父选框的状态为选中
				var checked = $(this).prop("checked");
				$(".son").prop("checked",checked);
			}
	);
	//判断是否全选
	function childCheck(){
		var length = $(".son:checked").length;
		var all = $(".son").length;
		if (all == length) {
			$("#fatherCheckbox").prop("checked",true);
		}else {
			$("#fatherCheckbox").prop("checked",false);
		}
	}
	//点击修改按钮时，判断子勾选框的选中数目
	function editTran() {
		var checkedLength = $(".son:checked").length;
		if (checkedLength == 1) {
			var id = $($(".son:checked"))[0].value();
			//跳转到编辑页面
			window.location.href="/crm/workbench/transaction/edit?id="+id;
		} else if (checkedLength > 1) {
			alert("只能同时修改一个联系人！");
		} else if (checkedLength == 0) {
			alert("至少选中一个联系人！");
		}
	}
	//点击删除，删除选中的客户
	function deleteTran(){
		var checkedLength = $('.son:checked').length;
		if (checkedLength == 0) {
			alert("至少选中一条记录！");
		}else {
			layer.alert("删除选中的"+checkedLength+"条数据吗？", {
				time: 0 //不自动关闭
				,btn: ['确定', '取消']
				,yes: function(index){
					//index:按钮的索引
					layer.close(index);

					var ids = [];
					$('.son:checked').each(function () {
						//push:向数组中放入元素
						ids.push($(this).val());
					});
					//join:把数组中的内容按指定的分隔符拼接成字符串 默认的分隔符就是,
					$.post("/crm/workbench/transaction/deleteTran",{'ids':ids.join()},function (data) {
						//data:resultVo
						if(data.ok){
							alert(data.message);
							refresh(1,3);
						}
					},'json');
				}
			});
		}
	}
</script>
</body>
</html>