<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <link href="/crm/jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
    <script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>
    <script type="text/javascript" src="/crm/jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
</head>
<body>
<div style="position: absolute; top: 0px; left: 0px; width: 60%;">
    <img src="/crm/image/IMG_7114.JPG" style="width: 100%; position: relative; top: 50px;">
</div>
<div id="top" style="height: 50px; background-color: #3C3C3C; width: 100%;">
    <div style="position: absolute; top: 5px; left: 0px; font-size: 30px; font-weight: 400; color: white; font-family: 'times new roman'">CRM &nbsp;<span style="font-size: 12px;">&copy;2020&nbsp;动力节点</span></div>
</div>

<div style="position: absolute; top: 120px; right: 100px;width:450px;height:400px;border:1px solid #D5D5D5">
    <div style="position: absolute; top: 0px; right: 60px;">
        <div class="page-header">
            <h1>登录</h1>
        </div>
        <form action="workbench/index.html" id="form1" ass="form-horizontal" role="form">
            <div class="form-group form-group-lg">
                <div style="width: 350px;">
                    <input class="form-control" id="loginAct"  autofocus atype="text" placeholder="用户名">
                </div>
                <div style="width: 350px; position: relative;top: 20px;">
                    <input class="form-control" id="loginPwd" type="password" placeholder="密码">
                </div>
                <div style="width: 350px; position: relative;top: 40px;">
                    <input id="inputCode" style="width: 200px;" class="form-control" type="text" placeholder="请输入验证码">
                    <img  onclick="changeCode()" id="code" style="cursor:pointer;  position: absolute; top: 0px; right: 0px" src="/crm/getcode"/>
                </div>
                <%--src也可以向后台发送请求--%>

                <div class="checkbox"  style="position: relative;top: 30px; left: 10px;">
                    <span id="msg"></span>
                </div>
                <button type="button" onclick="login()" class="btn btn-primary btn-lg btn-block"  style="width: 350px; position: relative;top: 45px;">登录</button>
            </div>
        </form>
    </div>
</div>
</body>
<script>
     if (window != top) {
        top.location.href="/crm/login.jsp";
     }
    //点击等录，将账号密码发送至控制器，进行登录的函数
    function login() {
        $.post("/crm/settings/user/login",{
            "loginAct":$("#loginAct").val(),
            "loginPwd":$("#loginPwd").val(),
            "code":$("#inputCode").val()
        },function (data) {
            //判断用户名，密码，验证码是否正确,利用data返回相应操作Result对应data
            if(!data.ok){
                //如果输入的验证码不正确，发提示信息
                alert(data.message);
            }else{
                //验证码正确，跳转到后台首页
                window.location.href="/crm/toView/workbench/index";
            }

        },'json');
    }
    //点击回车键跳转
    //触发回车事件，也可以登录 keypress = keydown + keyup
    //超链接:4种状态 link hover active visited
    $('body').keypress(function (event) {
        //判断是哪个键
        if(event.keyCode == 13){
            $.post("/crm/settings/user/login",{
                "loginAct":$("#loginAct").val(),
                "loginPwd":$("#loginPwd").val(),
                "code":$("#inputCode").val()
            },function (data) {
                //判断用户名，密码，验证码是否正确,利用data返回相应操作Result对应data
                if(!data.ok){
                    //如果输入的验证码不正确，发提示信息
                    alert(data.message);
                }else{
                    //验证码正确，跳转到后台首页
                    window.location.href="/crm/toView/workbench/index";
                }
            },'json');
        }
        }
    );
    //刷新验证码
    function changeCode() {
       /* window.location.href = "/crm/getcode"  ;*/
        $('#code').prop('src','/crm/getcode?time=' + new Date());
    }

</script>


</html>