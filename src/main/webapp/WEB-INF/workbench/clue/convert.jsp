<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head
>
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
		$("#isCreateTransaction").click(function(){
			if(this.checked){
				$("#create-transaction2").show(200);
			}else{
				$("#create-transaction2").hide(200);
			}
		});
	});
</script>

</head>
<body>
	
	<!-- 搜索市场活动的模态窗口 -->
	<div class="modal fade" id="searchActivityModal" role="dialog" >
		<div class="modal-dialog" role="document" style="width: 90%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title">搜索市场活动</h4>
				</div>
				<div class="modal-body">
					<div class="btn-group" style="position: relative; top: 18%; left: 8px;">
						<form class="form-inline" role="form">
						  <div class="form-group has-feedback">
						    <input id="name" type="text" class="form-control" style="width: 300px;" placeholder="请输入市场活动名称，支持模糊查询">
						    <span class="glyphicon glyphicon-search form-control-feedback"></span>
						  </div>
						</form>
					</div>
					<table id="activityTable" class="table table-hover" style="width: 900px; position: relative;top: 10px;">
						<thead>
							<tr style="color: #B3B3B3;">
								<td></td>
								<td>名称</td>
								<td>开始日期</td>
								<td>结束日期</td>
								<td>所有者</td>
								<td></td>
							</tr>
						</thead>
						<tbody id="activitybody">
							<%--<tr>
								<td><input type="radio" name="activity"/></td>
								<td>发传单</td>
								<td>2020-10-10</td>
								<td>2020-10-20</td>
								<td>zhangsan</td>
							</tr>--%>

						</tbody>
					</table>
				</div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
                    <button type="button" onclick="bind()" class="btn btn-primary" data-dismiss="modal">关联</button>
                </div>
			</div>
		</div>
	</div>

	<div id="title" class="page-header" style="position: relative; left: 20px;">
		<h4>转换线索 <small id="small">李四先生-动力节点</small></h4>
	</div>
	<div id="create-customer" style="position: relative; left: 40px; height: 35px;">
		新建客户：<span id="company">动力节点</span>
	</div>
	<div id="create-contact" style="position: relative; left: 40px; height: 35px;">
		新建联系人：<span id="fullname">李四先生</span>
	</div>
	<div id="create-transaction1" style="position: relative; left: 40px; height: 35px; top: 25px;">
		<input type="checkbox" id="isCreateTransaction" value="0"/>
		为客户创建交易
	</div>
	<div id="create-transaction2" style="position: relative; left: 40px; top: 20px; width: 80%; background-color: #F7F7F7; display: none;" >
	
		<form>
		  <div class="form-group" style="width: 400px; position: relative; left: 20px;">
		    <label for="amountOfMoney">金额</label>
		    <input type="text" class="form-control" id="amountOfMoney">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="tradeName">交易名称</label>
		    <input type="text" class="form-control" id="tradeName" value="动力节点-">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="expectedClosingDate">预计成交日期</label>
		    <input type="text" class="form-control" id="expectedClosingDate">
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
		    <label for="stage">阶段</label>
		    <select id="stage"  class="form-control">
		    	<option>请选择</option>
		    	<c:forEach items="${map['stage']}" var="stage">
                    <option value="${stage.value}">${stage.text}</option>
                </c:forEach>
		    </select>
		  </div>
		  <div class="form-group" style="width: 400px;position: relative; left: 20px;">
              <input type="hidden" id="activityId">
		    <label for="activity" >市场活动源&nbsp;&nbsp;<a href="javascript:void(0);" data-toggle="modal" data-target="#searchActivityModal" style="text-decoration: none;"><span class="glyphicon glyphicon-search"></span></a></label>
		    <input type="text" class="form-control" id="activity" placeholder="点击上面搜索" readonly>
		  </div>
		</form>
		
	</div>
	
	<div id="owner" style="position: relative; left: 40px; height: 35px; top: 50px;">
		记录的所有者：<br>
		<b id="owner2">zhangsan</b>
	</div>
	<div id="operation" style="position: relative; left: 40px; height: 35px; top: 100px;">
		<input class="btn btn-primary" type="button" value="转换">
		&nbsp;&nbsp;&nbsp;&nbsp;
		<input class="btn btn-default" type="button" value="取消">
	</div>
<script>
	//发送异步请求，查询线索信息
	$.get("/crm/workbench/clue/queryClue",
			{'id':'${id}'},
			function (data) {
				//data是线索
				var clue = data;
				$("#small").text(clue.fullname+ clue.appellation+ "-" + clue.company);
				$("#company").text(clue.company);
				$("#fullname").text(clue.fullname + clue.appellation);
				$("#owner2").text(clue.owner);
			},'json'
	);
	//判断是否选中单选框,选中则创建交易
	$('#isCreateTransaction').bind('click',function () {
		if($(this).prop('checked')){
			//已选中,发生交易了
			$(this).val("1");
		}else {
			$(this).val("0");
		}
	});
	//异步转换
	function convert() {
		$.post("/crm/workbench/clue/convert",
				{'money':$("#amountOfMoney").val(),
				'name':$("#tradeName").val(),
				'expectedDate':$("#expectedClosingDate").val(),
				'stage':$("#stage").val(),
				'isCreateTransaction':$("#isCreateTransaction").val(),
				'clueId':${id},
                'activityId':$("#activityId").val()},function (data) {
		        //data是resultVo
                    if (data.ok){
                        alert(data.message);
                    }
            },'json');
	}
    //点击放大镜按钮，按回车键查询关联的市场活动
    $("#name").keypress(function (event) {
        //回车事件进行查询
        if (event.keyCode == 13) {
            //发送请求查询已关联的市场
            $.get("/crm/workbench/clue/queryBindActivities",{'id':${id},'name':$("#name").val()},function (data) {
                //data是市场活动集合
                //清空
                $("#activitybody").html("");
                for(i=0;i<data.length;ind++){
                    var activity = data[i];
                    $("#activitybody").append("<tr>\n" +
                        "\t\t\t\t\t\t\t\t<td><input type=\"radio\"  class='son' value="+activity.id+"  name=\"activity\"  /></td>\n" +
                        "\t\t\t\t\t\t\t\t<td>"+activity.name+"</td>\n" +
                        "\t\t\t\t\t\t\t\t<td>"+activity.startDate+"</td>\n" +
                        "\t\t\t\t\t\t\t\t<td>"+activity.endDate+"</td>\n" +
                        "\t\t\t\t\t\t\t\t<td>"+activity.owner+"</td>\n" +
                        "\t\t\t\t\t\t\t</tr>");
                }
            },'json')
        }
    });
    //按下关联键，交易关联线索绑定的市场活动
    function bind() {
        //获取勾中的单选框元素
        var checkedRedio = $($(".son:checked")[0]);
        //获取勾中市场活动的名字
        var activityName = checkedRedio.parent().next().text();
        //放入市场活动源输入框中
        $("#activity").val(activityName);
        //获取勾中市场活动的id
        var activityId = checkedRedio.val();
        //放入隐藏域中
        $("#activityId").val(activityId);
    }

</script>
</body>
</html>