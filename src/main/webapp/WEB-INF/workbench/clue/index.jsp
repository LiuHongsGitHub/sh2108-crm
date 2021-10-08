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

	<!-- 创建线索的模态窗口 -->
	<div class="modal fade" id="createClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">创建线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="save-form">

						<div class="form-group">
							<label for="create-clueOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-clueOwner" name="owner">
									<c:forEach items="${users}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
							<label for="create-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-company" name="company">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-surname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-surname" name="fullname">
							</div>
							<label for="create-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-call" name="appellation">
									<c:forEach items="${map['appellation']}" var="appellation">
										<option value="${appellation.value}">${appellation.text}</option>
									</c:forEach>
								</select>
							</div>

						</div>
						
						<div class="form-group">
							<label for="create-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-job" name="job">
							</div>
							<label for="create-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-email" name="email">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone" name="phone">
							</div>
							<label for="create-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-website" name="website">
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-mphone" name="mphone">
							</div>
							<label for="create-status" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-status" name="state">
									<c:forEach items="${map['clueState']}" var="clueState">
										<option value="${clueState.value}">${clueState.text}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="create-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-source" name="source">
									<c:forEach items="${map['source']}" var="source">
										<option value="${source.value}">${source.text}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						

						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">线索描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe" name="description"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="create-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="create-contactSummary" name="contactSummary"></textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="create-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="create-nextContactTime" name="nextContactTime">
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>
						
						<div style="position: relative;top: 20px;">
							<div class="form-group">
                                <label for="create-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address" name="address"></textarea>
                                </div>
							</div>
						</div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveOrUpdate($(this).text())">保存</button>
				</div>
			</div>
		</div>
	</div>
	
	<!-- 修改线索的模态窗口 -->
	<div class="modal fade" id="editClueModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">修改线索</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="edit-form">
						<input type="hidden"  id="edit-id" name="id">
						<div class="form-group">
							<label for="edit-clueOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-clueOwner" name="owner">
									<c:forEach items="${users}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
							<label for="edit-company" class="col-sm-2 control-label">公司<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-company" value="动力节点" name="company">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-call" class="col-sm-2 control-label">称呼</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-call" name="appellation">
									<c:forEach items="${map['appellation']}" var="appellation">
										<option value="${appellation.value}">${appellation.text}</option>
									</c:forEach>
								</select>
							</div>
							<label for="edit-fullname" class="col-sm-2 control-label">姓名<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-fullname" name="fullname">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-job" class="col-sm-2 control-label">职位</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-job"  name="job">
							</div>
							<label for="edit-email" class="col-sm-2 control-label">邮箱</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-email" name="eamil">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone" name="phone">
							</div>
							<label for="edit-website" class="col-sm-2 control-label">公司网站</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-website" name="website">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-mphone" class="col-sm-2 control-label">手机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-mphone" name="mphone">
							</div>
							<label for="edit-status" class="col-sm-2 control-label">线索状态</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-status" name="state">
									<c:forEach items="${map['clueState']}" var="clueState">
										<option value="${clueState.value}">${clueState.text}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-source" class="col-sm-2 control-label">线索来源</label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-source" name="source">
									<c:forEach items="${map['source']}" var="source">
										<option value="${source.value}">${source.text}</option>
									</c:forEach>
								</select>
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe" name="description">这是一条线索的描述信息</textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>
						
						<div style="position: relative;top: 15px;">
							<div class="form-group">
								<label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
								<div class="col-sm-10" style="width: 81%;">
									<textarea class="form-control" rows="3" id="edit-contactSummary" name="contactSummary">这个线索即将被转换</textarea>
								</div>
							</div>
							<div class="form-group">
								<label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
								<div class="col-sm-10" style="width: 300px;">
									<input type="text" class="form-control" id="edit-nextContactTime" name="nextContactTime" >
								</div>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address" name="address">北京大兴区大族企业湾</textarea>
                                </div>
                            </div>
                        </div>
					</form>
					
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveOrUpdate($(this).text())">更新</button>
				</div>
			</div>
		</div>
	</div>
	
	
	
	
	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>线索列表</h3>
			</div>
		</div>
	</div>
	
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
	
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">
		
			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;" id="queryForm">
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="fullnameAppellation" name="fullname">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司</div>
				      <input class="form-control" type="text"id="query-company" name="company" >
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司座机</div>
				      <input class="form-control" type="text" id="query-phone" name="phone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索来源</div>
					  <select class="form-control" id="query-source" name="source">
						  <option style='display: none' property="selected"></option>
						  <c:forEach items="${map['source']}" var="source">
							  <option value="${source.value}">${source.text}</option>
						  </c:forEach>
					  </select>
				    </div>
				  </div>
				  
				  <br>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="query-owner" name="owner">
				    </div>
				  </div>
				  
				  
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">手机</div>
				      <input class="form-control" type="text" id="mphone" name="mphone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">线索状态</div>
					  <select class="form-control" id="query-state" name="state">
						  <option style='display: none' property="selected"></option>
						<c:forEach items="${map['clueState']}" var="clueState">
							<option value="${clueState.value}">${clueState.text}</option>
						</c:forEach>
						</select>
				    </div>
				  </div>

				  <button type="button" class="btn btn-default" onclick="query()">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 40px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createClueModal"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" data-toggle="modal" onclick="editClue()"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" onclick="deleteClue()"><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
			</div>
			<div style="position: relative;top: 50px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" /></td>
							<td>名称</td>
							<td>公司</td>
							<td>公司座机</td>
							<td>手机</td>
							<td>线索来源</td>
							<td>所有者</td>
							<td>线索状态</td>
						</tr>
					</thead>
					<tbody id="clue-body">
						<%--<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;">李四先生</a></td>
							<td>动力节点</td>
							<td>010-84846003</td>
							<td>12345678901</td>
							<td>广告</td>
							<td>zhangsan</td>
							<td>已联系</td>
						</tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 60px;">
				<div id="cluePage"></div>
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
	//解决日历的乱码问题
	(function($){
		$.fn.datetimepicker.dates['zh-CN'] = {
			days: ["星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六", "星期日"],
			daysShort: ["周日", "周一", "周二", "周三", "周四", "周五", "周六", "周日"],
			daysMin:  ["日", "一", "二", "三", "四", "五", "六", "日"],
			months: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
			monthsShort: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
			today: "今天",
			suffix: [],
			meridiem: ["上午", "下午"]
		};
	}(jQuery));
	//日历插件
	$("#create-nextContactTime").datetimepicker({
		language:  "zh-CN",
		format: "yyyy-mm-dd",//显示格式
		minView: "month",//设置只显示到月份
		initialDate: new Date(),//初始化当前日期
		autoclose: true,//选中自动关闭
		todayBtn: true, //显示今日按钮
		clearBtn : true,
		pickerPosition: "bottom-left"
	});


	$("#edit-nextContactTime").datetimepicker({
		language:  "zh-CN",
		format: "yyyy-mm-dd",//显示格式
		minView: "month",//设置只显示到月份
		initialDate: new Date(),//初始化当前日期
		autoclose: true,//选中自动关闭
		todayBtn: true, //显示今日按钮
		clearBtn : true,
		pickerPosition: "bottom-left"
	});
	//第一次进入进行查询
	refresh(1,3);
	//刷新页面进行查询
	function refresh(page,pageSize) {
		$.get("/crm/workbench/clue/list",{
			'page':page,
			'pageSize':pageSize,
			'fullname':$("#query-fullname").val(),
			'company':$("#query-company").val(),
			'phone':$("#query-phone").val(),
			'mphone':$("#query-mphone").val(),
			'source':$("#query-source").val(),
			'owner':$("#query-owner").val(),
			'state':$("#query-state").val(),
		},function (data) {
			//data是页面集合，包含页码，总记录数，客户集合等等
			//1.清空表体
			$("#clue-body").empty();
			/*$.each(data.list,function (i,n) {
                alert(n.name)
            })*/
			//循环拼接
			for (var i in data.list) {
				var clue = data.list[i];
				//拼接单选框
				$('#clue-body').append("<tr>\n" +
						"\t\t\t\t\t\t\t<td><input value="+clue.id+" onclick='childCheck()'  class='son' type=\"checkbox\"  /></td>\n" +
						"\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\" href='/crm/toView/workbench/clue/detail?id="+clue.id+" '  >"+clue.fullname+" </a></td>\n" +
						"\t\t\t\t\t\t\t<td>"+clue.company+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+clue.phone+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+clue.mphone+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+clue.source+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+clue.owner+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+clue.state+"</td>\n" +
						"\t\t\t\t\t\t</tr>"
				);
			}
			//分页
			$("#cluePage").bs_pagination({
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
	//点击更新或保存按钮
	function saveOrUpdate(text) {
		var form;
		if("保存" == text){
			form = $('#save-form').serialize();
		}else if("更新" == text){
			form =  $('#edit-form').serialize();
		}
		$.post("/crm/workbench/clue/saveOrUpdate",form,function (data) {
			//data是resultVo
			if (data.ok){
				alert(data.message);
				document.querySelector("#save-form").reset();
				refresh(1,3);
				//重置创建模态框
			}
		},'json');
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
	function editClue() {
		var checkedLength = $(".son:checked").length;
		if (checkedLength == 1) {
			//选择一个进行查询
			$('#editClueModal').modal("show");
			//获得被选中的Id
			var id = $($(".son:checked")[0]).val();
			alert(id);
			//请求查询
			$.get("/crm/workbench/clue/queryClue", {"id": id}, function (data) {
				//data是客户
				var clue = data;
				//data是用户集合

				//清空
				$("#edit-owner").val(clue.owner);
				$("#company").val(clue.company);
				$('#edit-fullname').val(clue.fullname);
				$('#edit-appellation').val(clue.appellation);
				$('#edit-job').val(clue.job);
				$('#edit-mphone').val(clue.mphone);
				$('#edit-phone').val(clue.phone);
				$('#edit-email').val(clue.email);
				$('#edit-website').val(clue.website);
				$('#edit-state').val(clue.state);
				$('#edit-source').val(clue.source);
				$('#edit-description').val(clue.description);
				$('#edit-contactSummary').val(clue.contactSummary);
				$("#edit-nextContactTime").val(clue.nextContactTime);
				$("#edit-address").val(clue.address);
				$("#edit-id").val(clue.id);
			}, 'json');
		} else if (checkedLength > 1) {
			alert("只能同时修改一个线索！");
		} else if (checkedLength == 0) {
			alert("至少选中一个线索！");
		}
	}
	//点击删除，删除选中的客户
	function deleteClue(){
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
					$.post("/crm/workbench/clue/deleteClue",{'ids':ids.join()},function (data) {
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