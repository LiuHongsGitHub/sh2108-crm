<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <%--设置字符集--%>
    <meta charset="UTF-8">
    <%--先导入jquery--%>
        <link href="/crm/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
        <link href="/crm/jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />

        <script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>
        <script type="text/javascript" src="/crm/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
        <script type="text/javascript" src="/crm/jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
        <script type="text/javascript" src="/crm/jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

</head>
<body>
<!-- 创建市场活动的模态窗口 -->
<div class="modal fade" role="dialog" id="createActivityModal">
    <div class="modal-dialog" role="document" style="width: 85%;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal">
                    <span aria-hidden="true">×</span>
                </button>
                <h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
            </div>
            <div class="modal-body">

                <form id="addActivityForm" class="form-horizontal" role="form">
                    <input type="hidden" name="保存">
                    <div class="form-group">
                        <label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" id="create-marketActivityOwner" name="owner">
                            </select>
                        </div>
                        <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-marketActivityName" name="name">
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-startTime" name="startDate">
                        </div>
                        <label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-endTime" name="endDate">
                        </div>
                    </div>
                    <div class="form-group">

                        <label for="create-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" id="create-cost" name="cost">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="create-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" id="create-describe" name="description"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveAndUpdate($(this).text())">保存</button>
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

                <form class="form-horizontal" role="form" id="edit-form">
                    <input id="id" name="id" type="hidden">
                    <div class="form-group">

                        <label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <select class="form-control" name="owner" id="edit-marketActivityOwner">
                            </select>
                        </div>
                        <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span
                                style="font-size: 15px; color: red;">*</span></label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" name="name" id="edit-marketActivityName" >
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" name="startDate"  id="edit-startTime" >
                        </div>
                        <label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" name="endDate"  id="edit-endTime" >
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-cost" class="col-sm-2 control-label">成本</label>
                        <div class="col-sm-10" style="width: 300px;">
                            <input type="text" class="form-control" name="cost" id="edit-cost" >
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="edit-describe" class="col-sm-2 control-label">描述</label>
                        <div class="col-sm-10" style="width: 81%;">
                            <textarea class="form-control" rows="3" name="description" id="edit-describe"></textarea>
                        </div>
                    </div>

                </form>

            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary" data-dismiss="modal" onclick="saveAndUpdate($(this).text())">更新</button>
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
        <%--查询栏--%>
        <div class="btn-toolbar" role="toolbar" style="height: 80px;">
            <form class="form-inline" id="activityForm" role="form" style="position: relative;top: 8%; left: 5px;">

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">名称</div>
                        <input id="name" name="name" class="form-control" type="text">
                    </div>
                </div>

                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">所有者</div>
                        <input id="owner" name="owner" class="form-control" type="text">
                    </div>
                </div>
                
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">开始日期</div>
                        <input  class="form-control" name="startDate" type="text" id="startTime"/>
                    </div>
                </div>
                <div class="form-group">
                    <div class="input-group">
                        <div class="input-group-addon">结束日期</div>
                        <input  class="form-control" name="endDate" type="text" id="endTime">
                    </div>
                </div>

                <button type="button" onclick="query()" class="btn btn-default">查询</button>

            </form>
        </div>
        <%--按钮--%>
        <div class="btn-toolbar" role="toolbar"
             style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
            <div class="btn-group" style="position: relative; top: 18%;">
                <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#createActivityModal" >
                    <span class="glyphicon glyphicon-plus"></span> 创建
                </button>
                <button type="button" class="btn btn-default" data-toggle="modal"  onclick="editActivity()"><span
                        class="glyphicon glyphicon-pencil"></span> 修改
                </button>
                <button type="button" class="btn btn-danger" onclick="deleteActivity()"><span class="glyphicon glyphicon-minus"></span> 删除</button>
                <button type="button" class="btn btn-success"   onclick="exportExcel()"><span class="glyphicon glyphicon-arrow-right"></span> 导出表格</button>
            </div>

        </div>
        <%--表头--%>
        <div style="position: relative;top: 10px;">
            <table class="table table-hover">
                <thead>
                <tr style="color: #B3B3B3;">
                    <td><input type="checkbox" id="fatherCheckbox"/></td>
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

        <div  style="height: 50px; position: relative;top: 30px;">
            <div id="activityPage">123</div>
        </div>
    </div>
</div>
<link rel="stylesheet" href="/crm/jquery/bs_pagination/jquery.bs_pagination.min.css"/>
<script src="/crm/jquery/bs_pagination/en.js"></script>
<script src="/crm/jquery/bs_pagination/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="/crm/jquery/layer/layer.js"></script>
<script>
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
    refresh(1,3);
    //刷新页面
    function refresh(page,pageSzie){
        $.get("/crm/workbench/activity/list",
            {'page': page,
            'pageSize':pageSzie,
            'name':$("#name").val(),
            'owner':$("#owner").val(),
            'startDate':$("#startTime").val(),
            'endDate':$("#endTime").val()
            },
            function (data) {
                //data是object(activities)的集合，是一个大的object，包含总页数
                //清空表体数据
                $("#activityBody").empty();
                //循环拼接
                for (var i = 0; i < data.list.length; i++) {
                    //取出单个市场活动
                    var activity = data.list[i];
                    //拼接单选框，取出姓名、owner.startDate.endDate
                    $("#activityBody").append("<tr class=\"active\">\n" +
                        "\t\t\t\t\t\t\t<td><input value="+activity.id+" onclick='childCheck()' type=\"checkbox\" class='son'  /></td>\n" +
                    "\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\"  href='/crm/toView/workbench/activity/detail?id="+activity.id+" '  >" + activity.name + "</a></td>\n" +
                    "                            <td>" + activity.owner + "</td>\n" +
                        "\t\t\t\t\t\t\t<td>" + activity.startDate + "</td>\n" +
                        "\t\t\t\t\t\t\t<td>" + activity.endDate + "</td>\n" +
                        "\t\t\t\t\t\t</tr>");
                }
                //点击分页的
                $("#activityPage").bs_pagination({
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
                            refresh(obj.currentPage,obj.rowsPerPage);
                        }
                    }
                );
            }, 'json'
        );
    }

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
    $("#startTime").datetimepicker({
        language:  "zh-CN",
        format: "yyyy-mm-dd",//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),//初始化当前日期
        autoclose: true,//选中自动关闭
        todayBtn: true, //显示今日按钮
        clearBtn : true,
        pickerPosition: "bottom-left"
    });
    $("#endTime").datetimepicker({
        language:  "zh-CN",
        format: "yyyy-mm-dd",//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),//初始化当前日期
        autoclose: true,//选中自动关闭
        todayBtn: true, //显示今日按钮
        clearBtn : true,
        pickerPosition: "bottom-left"
    });
    $("#create-startTime").datetimepicker({
        language:  "zh-CN",
        format: "yyyy-mm-dd",//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),//初始化当前日期
        autoclose: true,//选中自动关闭
        todayBtn: true, //显示今日按钮
        clearBtn : true,
        pickerPosition: "bottom-left"
    });
    $("#create-endTime").datetimepicker({
        language:  "zh-CN",
        format: "yyyy-mm-dd",//显示格式
        minView: "month",//设置只显示到月份
        initialDate: new Date(),//初始化当前日期
        autoclose: true,//选中自动关闭
        todayBtn: true, //显示今日按钮
        clearBtn : true,
        pickerPosition: "bottom-left"
    });

    //点击查询，查询所有符合条件的市场活动，并且分页
    function query() {
        //这里会产生page为null的错误
        /*var form = $("#activityForm").serialize();
        $.get(
            "/crm/workbench/activity/list",
            form,
            function (data) {
                refresh(1,3);
            },'json'
        );*/
        refresh(1,3);
    }
    //异步查询全部所有者的信息，并且拼接到create-marketActivityOwner的下拉框下面
    $.get(
        "/crm/workbench/activity/queryUsers",
        function (data) {
            $("#create-marketActivityOwner").empty();
            //循环拼接所有者
            for(var i in data){
                var user = data[i];
                $("#create-marketActivityOwner").append("<option value="+user.id+">"+user.name+"</option>");
            }
        },
        'json'
    );
    //点击保存或者更新的创建和保存的方法
    /*$("#saveBtn").click(
        var text = "save";
        function saveAndUpdate(text);
    );*/
    function saveAndUpdate(text){
        var form;
        if("保存" == text){
            form = $('#form').serialize();
        }else if("更新" == text){
            form =  $('#edit-form').serialize();
        }

            $.post("/crm/workbench/activity/saveAndUpdate",form,function (data) {
                if(data.ok){
                    alert(data.message);
                    refresh(1,3);
                    //重置创建表单的模态框
                    //document.getElementById("addActivityForm").onreset();
                    document.querySelector("#addActivityForm").reset();

                }
            },'json');
    }
    //先写勾选框事件
    $("#fatherCheckbox").click(
        function () {
            //checked是布尔值
            var checked = $(this).prop("checked");
            if(checked){
                //如果正确说明全部选中
                $(".son").prop("checked",true);
            }else{
                $(".son").prop("checked",false);
            }
        }
    );
    function childCheck(){
        //获取选中的个数
        var checkedLength = $('.son:checked').length;
        //获取所有的复选框的个数
        var checkLength = $('.son').length;
        if(checkedLength ==checkLength){
            $("#fatherCheckbox").prop("checked",true);
        }else{
            $("#fatherCheckbox").prop("checked",false);
        }
    }
    //点击修改，判断被选中的框有几个
    function editActivity() {
        var checkedLength = $('.son:checked').length;
        if(checkedLength == 1){
            //选择一个进行查询
            $('#editActivityModal').modal("show");
            //获得被选中的Id
            var id = $($(".son:checked")[0]).val();
            //请求查询市场活动
            $.get(
                "/crm/workbench/activity/queryActivity",
                {'id':id},function (data) {
                    var activity = data;
                    $.get(
                        "/crm/workbench/activity/queryUsers",
                        function (data) {
                            $("#edit-marketActivityOwner").empty();
                            for(var i in data){
                                var user = data[i];
                                if(activity.owner == user.id){
                                    $("#edit-marketActivityOwner").append("<option selected value="+user.id+">"+user.name+"</option>");
                                }else {
                                    $('#edit-marketActivityOwner').append("<option value="+user.id+">"+user.name+"</option>");
                                }

                            }
                            $('#edit-marketActivityName').val(activity.name);
                            $('#edit-startTime').val(activity.startDate);
                            $('#edit-endTime').val(activity.endDate);
                            $('#edit-cost').val(activity.cost);
                            $('#edit-describe').val(activity.description);
                            $("#id").val(activity.id);
                        },'json'
                    );
                },'json'
            );
        }else if(checkedLength > 1){
            alert("只能同时修改一条市场活动！");
        }else if(checkedLength == 0 ){
            alert("至少选中一条市场活动！");
        }
    }
    //点击删除，删除选中的市场活动
    function deleteActivity(){
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
                    $.post("/crm/workbench/activity/deleteBatch",{'ids':ids.join()},function (data) {
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
    function exportExcel() {
        location.href = "/crm/workbench/activity/exportExcel";
    }
    //查询单个所有者信息
    /*$.get(
        "/crm/workbench/activity/queryActivity",
        function (data) {
            $("#edit-marketActivityOwner").empty();
            //循环拼接所有者
            for(var i in data){
                var user = data[i];
                $("#edit-marketActivityOwner").append("<option value="+user.id+">"+user.name+"</option>");
            }
        },
        'json'
    );*/



    //传统方式，获取所有市场活动
    /*$.get("/crm/workbench/activity/list",
        {},
        function (data) {
        //data是object(activities)的集合，是一个大的object，包含总页数
        //清空表体数据
           $("#activityBody").empty();
        //循环拼接
            for(var i = 0;i < data.list.length;i++){
                //取出单个市场活动
                var activity = data.list[i];
                //拼接单选框，取出姓名、owner.startDate.endDate
                $("#activityBody").append("<tr class=\"active\">\n" +
                    "\t\t\t\t\t\t\t<td><input type=\"checkbox\" /></td>\n" +
                    "\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='detail.jsp';\">"+activity.name+"</a></td>\n" +
                    "                            <td>"+activity.owner+"</td>\n" +
                    "\t\t\t\t\t\t\t<td>"+activity.startDate+"</td>\n" +
                    "\t\t\t\t\t\t\t<td>"+activity.endDate+"</td>\n" +
                    "\t\t\t\t\t\t</tr>"
                );}
            //添加分页
            for(var y = 1;y <= data.t ;y++ ){
                $("#pages").append("<a style='margin-left:5px' onclick='nextPage("+y+")' href='javascript:;'>"+y+ "</a>");
            }
        },
        'json');

    //分页跳转
        function nextPage(i) {
            $.get("/crm/workbench/activity/list",
                {'p':i},
                function (data) {
                    //data是object(activities)的集合，是一个大的object，包含总页数
                    //清空表体数据
                    $("#activityBody").empty();
                    //循环拼接
                    for(var i = 0;i < data.list.length;i++){
                        //取出单个市场活动
                        var activity = data.list[i];
                        //拼接单选框，取出姓名、owner.startDate.endDate
                        $("#activityBody").append("<tr class=\"active\">\n" +
                            "\t\t\t\t\t\t\t<td><input type=\"checkbox\" /></td>\n" +
                            "\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='detail.jsp';\">"+activity.name+"</a></td>\n" +
                            "                            <td>"+activity.owner+"</td>\n" +
                            "\t\t\t\t\t\t\t<td>"+activity.startDate+"</td>\n" +
                            "\t\t\t\t\t\t\t<td>"+activity.endDate+"</td>\n" +
                            "\t\t\t\t\t\t</tr>"
                        );}
                },
                'json');
        }*/
    /*$.get("/crm/workbench/activity/list",
        function (data) {
            //data是object(activities)的集合，是一个大的object，包含总页数
            //清空表体数据
            $("#activityBody").empty();
            //循环拼接
            for (var i = 0; i < data.list.length; i++) {
                //取出单个市场活动
                var activity = data.list[i];
                //拼接单选框，取出姓名、owner.startDate.endDate
                $("#activityBody").append("<tr class=\"active\">\n" +
                    "\t\t\t\t\t\t\t<td><input type=\"checkbox\" /></td>\n" +
                    "\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='detail.jsp';\">" + activity.name + "</a></td>\n" +
                    "                            <td>" + activity.owner + "</td>\n" +
                    "\t\t\t\t\t\t\t<td>" + activity.startDate + "</td>\n" +
                    "\t\t\t\t\t\t\t<td>" + activity.endDate + "</td>\n" +
                    "\t\t\t\t\t\t</tr>");
            }
        }, 'json'
    );
    $("#activityPage").bs_pagination({

            currentPage: 1, // 页码
            rowsPerPage: 3, // 每页显示的记录条数
            maxRowsPerPage: 20, // 每页最多显示的记录条数
            totalPages: 20, // 总页数
            totalRows: 50, // 总记录条数
            visiblePageLinks: 3, // 显示几个卡片
            showGoToPage: true,
            showRowsPerPage: true,
            showRowsInfo: true,
            showRowsDefaultInfo: true,
            //该函数只要操作分页插件都会触发该函数
            onChangePage: function (event, obj) {
                $.get("/crm/workbench/activity/list",
                    {'p': obj.currentPage},
                    function (data) {
                        //data是object(activities)的集合，是一个大的object，包含总页数
                        //清空表体数据
                        $("#activityBody").empty();
                        //循环拼接
                        for (var i = 0; i < data.list.length; i++) {
                            //取出单个市场活动
                            var activity = data.list[i];
                            //拼接单选框，取出姓名、owner.startDate.endDate
                            $("#activityBody").append("<tr class=\"active\">\n" +
                                "\t\t\t\t\t\t\t<td><input type=\"checkbox\" /></td>\n" +
                                "\t\t\t\t\t\t\t<td><a style=\"text-decoration: none; cursor: pointer;\" onclick=\"window.location.href='detail.jsp';\">" + activity.name + "</a></td>\n" +
                                "                            <td>" + activity.owner + "</td>\n" +
                                "\t\t\t\t\t\t\t<td>" + activity.startDate + "</td>\n" +
                                "\t\t\t\t\t\t\t<td>" + activity.endDate + "</td>\n" +
                                "\t\t\t\t\t\t</tr>");
                        }
                    }, 'json'
                );
            }
        }
    );*/
</script>

</body>
</html>
