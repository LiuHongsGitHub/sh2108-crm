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

	<script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>
	<script type="text/javascript" src="/crm/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
	<script type="text/javascript" src="/crm/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
	<script type="text/javascript" src="/crm/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript">

	$(function(){
		
		//定制字段
		$("#definedColumns > li").click(function(e) {
			//防止下拉菜单消失
	        e.stopPropagation();
	    });
		
	});
	
</script>
</head>
<body>

	<!-- 创建客户的模态窗口 -->
	<div class="modal fade" id="createCustomerModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建客户</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="save-form">
						<input type="hidden" id="save-id" >
						<div class="form-group">
							<label for="create-customerOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-customerOwner" name="owner">
								</select>
							</div>
							<label for="create-customerName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-customerName" name="name">
							</div>
						</div>
						
						<div class="form-group">
                            <label for="create-website" class="col-sm-2 control-label">公司网站</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-website" name="website">
                            </div>
							<label for="create-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="create-phone" name="phone">
							</div>
						</div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
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
                                <label for="create-address1" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="create-address1" name="address"></textarea>
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
	
	<!-- 修改客户的模态窗口 -->
	<div class="modal fade" id="editCustomerModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel">修改客户</h4>
				</div>
				<div class="modal-body">
					<form class="form-horizontal" role="form" id="edit-form">
						<input type="hidden" id="edit-id" name="id">
						<div class="form-group">
							<label for="edit-customerOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-customerOwner" name="owner">

								</select>
							</div>
							<label for="edit-customerName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-customerName" value="动力节点" name="name">
							</div>
						</div>
						
						<div class="form-group">
                            <label for="edit-website" class="col-sm-2 control-label">公司网站</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-website" value="http://www.bjpowernode.com" name="website">
                            </div>
							<label for="edit-phone" class="col-sm-2 control-label">公司座机</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-phone" value="010-84846003" name="phone">
							</div>
						</div>
						
						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe" name="description"></textarea>
							</div>
						</div>
						
						<div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative;"></div>

                        <div style="position: relative;top: 15px;">
                            <div class="form-group">
                                <label for="edit-contactSummary" class="col-sm-2 control-label">联系纪要</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="3" id="edit-contactSummary" name="contactSummary"></textarea>
                                </div>
                            </div>
                            <div class="form-group">
                                <label for="edit-nextContactTime" class="col-sm-2 control-label">下次联系时间</label>
                                <div class="col-sm-10" style="width: 300px;">
                                    <input type="text" class="form-control" id="edit-nextContactTime" name="nextContactTime">
                                </div>
                            </div>
                        </div>

                        <div style="height: 1px; width: 103%; background-color: #D5D5D5; left: -13px; position: relative; top : 10px;"></div>

                        <div style="position: relative;top: 20px;">
                            <div class="form-group">
                                <label for="edit-address" class="col-sm-2 control-label">详细地址</label>
                                <div class="col-sm-10" style="width: 81%;">
                                    <textarea class="form-control" rows="1" id="edit-address" name="address">北京大兴大族企业湾</textarea>
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
				<h3>客户列表</h3>
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
				      <input class="form-control" type="text" id="name">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="owner">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司座机</div>
				      <input class="form-control" type="text" id="phone">
				    </div>
				  </div>
				  
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">公司网站</div>
				      <input class="form-control" type="text" id="website">
				    </div>
				  </div>
				  
				  <button type="button" class="btn btn-default" onclick="query()">查询</button>
				  
				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createCustomerModal"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" data-toggle="modal"  onclick="editCustomer()"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" onclick="deleteCustomer()"><span class="glyphicon glyphicon-minus" ></span> 删除</button>
				</div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="fatherCheckbox" /></td>
							<td>名称</td>
							<td>所有者</td>
							<td>公司座机</td>
							<td>公司网站</td>
						</tr>
					</thead>
					<tbody id="customer-body">
						<%--<tr>
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" >动力节点</a></td>
							<td>zhangsan</td>
							<td>010-84846003</td>
							<td>http://www.bjpowernode.com</td>
						</tr>--%>
					</tbody>
				</table>
			</div>
			
			<div style="height: 50px; position: relative;top: 30px;">
				<div id="customerPage" ></div>
			</div>
			
		</div>
		
	</div>
	<%--$("#").--%>
	<link rel="stylesheet" href="/crm/jquery/bs_pagination/jquery.bs_pagination.min.css"/>
	<script src="/crm/jquery/bs_pagination/en.js"></script>
	<script src="/crm/jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
	<script type="text/javascript" src="/crm/jquery/layer/layer.js"></script>
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
	$("#create-nextContactTime2").datetimepicker({
		language:  "zh-CN",
		format: "yyyy-mm-dd",//显示格式
		minView: "month",//设置只显示到月份
		initialDate: new Date(),//初始化当前日期
		autoclose: true,//选中自动关闭
		todayBtn: true, //显示今日按钮
		clearBtn : true,
		pickerPosition: "bottom-left"
	});
	refresh(1,3);
	//刷新页面进行查询
	function refresh(page,pageSize) {
		$.get("/crm/workbench/customer/list",{
			'page':page,
			'pageSize':pageSize,
			'name':$("#name").val(),
			'owner':$("#owner").val(),
			'phone':$("#phone").val(),
			'website':$("#website").val()
		},function (data) {
			//data是页面集合，包含页码，总记录数，客户集合等等
			//1.清空表体
			$("#customer-body").empty();
			/*$.each(data.list,function (i,n) {
				alert(n.name)
			})*/
			//循环拼接
			for (var i in data.list) {
				var customer = data.list[i];
				//拼接单选框
				$('#customer-body').append("<tr>\n" +
						"\t\t\t\t\t\t\t<td><input value="+customer.id+" onclick='childCheck()'   type=\"checkbox\"  class='son'   /></td>\n" +
						"\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\" href='/crm/toView/workbench/customer/detail?id="+customer.id+" '  >"+customer.name+"</a></td>\n" +
						"\t\t\t\t\t\t\t<td>"+customer.owner+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+customer.phone+"</td>\n" +
						"\t\t\t\t\t\t\t<td>"+customer.website+"</td>\n" +
						"\t\t\t\t\t\t</tr>"
				);
			}
			//分页
			$("#customerPage").bs_pagination({
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
	//异步查询客户的所有者信息，拼接到下拉框
	$.get("/crm/workbench/customer/queryUsers",function (data) {
		//data是所有者的集合
		//清空下拉框
		$("#create-customerOwner").empty();
		for (var i in data) {
			var owner = data[i];
			$("#create-customerOwner").append("<option value="+owner.id+">"+owner.name+"</option>");
		}
	},'json');
	//点击更新或保存按钮
	function saveOrUpdate(text) {
		var form;
		if("保存" == text){
			form = $('#save-form').serialize();
		}else if("更新" == text){
			form =  $('#edit-form').serialize();
		}
		$.post("/crm/workbench/customer/saveOrUpdate",form,function (data) {
			//data是resultVo
			if (data.ok){
				alert(data.message);
				refresh(1,3);
				//重置创建模态框
				document.querySelector("#save-form").reset();
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
	function editCustomer() {
		var checkedLength = $(".son:checked").length;
		if (checkedLength == 1) {
			//选择一个进行查询
			$('#editCustomerModal').modal("show");
			//获得被选中的Id
			var id = $($(".son:checked")[0]).val();
			alert(id)
			//请求查询
			$.get("/crm/workbench/customer/queryCustomer", {"id": id}, function (data) {
				//data是客户
				var customer = data;
				alert(customer.name);
				$.get("/crm/workbench/customer/queryUsers", function (data) {
					//data是用户集合
					//清空
					$("#edit-customerOwner").html("");
					for (var i in data) {
						var user = data[i];
						if (user.id == customer.owner) {
							//说明是被选中的user
							$("#edit-customerOwner").append("<option selected value="+user.id+">"+user.name+"</option>");
						} else {
							$('#edit-customerOwner').append("<option value="+user.id+">"+user.name+"</option>");
						}
					}
					$('#edit-customerName').val(customer.name);
					$('#edit-website').val(customer.website);
					$('#edit-phone').val(customer.phone);
					$('#edit-describe').val(customer.description);
					$('#edit-contactSummary').val(customer.contactSummary);
					$("#edit-nextContactTime").val(customer.nextContactTime);
					$("#edit-address").val(customer.address);
					$("#edit-id").val(customer.id);
				}, 'json');
			}, 'json');
		} else if (checkedLength > 1) {
			alert("只能同时修改一个客户！");
		} else if (checkedLength == 0) {
			alert("至少选中一个客户！");
		}
	}
		//点击删除，删除选中的客户
		function deleteCustomer(){
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
						$.post("/crm/workbench/customer/deleteCustomer",{'ids':ids.join()},function (data) {
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

	<%--$("#").--%>
</script>
</body>
</html>