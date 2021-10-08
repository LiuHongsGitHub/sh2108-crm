<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="/crm/jquery/jquery-1.11.1-min.js"></script>
    <script src="/crm/jquery/echarts/echarts.min.js"></script>
</head>
<body>
<%--为图表准备一个定义了宽高的容器--%>
<div id="main"  style="height: 900px;width: 600px;"></div>
<div id="main1" style="height: 900px;width: 600px;"></div>
</body>
</html>
<script>
    alert(123);

    //制作柱状图
    $.get("/crm/workbench/chart/barEcharts",function (data) {
        /*基于准备好的dom，初始化echart实例*/
        var mychart = echarts.init(document.getElementById('main'));
        alert(data);
        /*指定数据格式*/
        var option = {
            title:{
                text:"交易统计图表",
                left:'center'
            },
            xAxls:{
                type:'category',
                data:data.titles
            },
            yAxls:{
                type:'value'
            },

            series: [
                {
                    data:data.chartData,
                    type: 'bar',
                    showBackground: false,
                    backgroundStyle: {
                        color: 'rgba(180, 180, 180, 0.2)'
                    }
                }
            ]
        };
        //使用指定的配置项和数据显示图表
        mychart.setOption(option);

        },'json');

    $.get("/crm/workbench/chart/pieEcharts",function (data) {
        //data:BarVo
        // 基于准备好的dom，初始化echarts实例
        var myChart = echarts.init(document.getElementById('main1'));

        // 指定图表的配置项和数据
        var option = {
            title: {
                text: '交易统计图标',
                left: 'center'
            },
            tooltip: {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'cent'
            },
            series: [
                {
                    type: 'pie',
                    radius: '80%',
                    data:data,
                    emphasis: {
                        itemStyle: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
    },'json');
</script>