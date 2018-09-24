<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>

<link rel="stylesheet" href="css/pintuer.css">
<link rel="stylesheet" href="css/admin.css">
<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath }/js/echarts.js"></script>
</head>
<body>
<div id="chartmain" style="width:600px; height: 400px;"></div>
	<p>
		欢迎使用医院管理系统——医药管理子系统<br>
		作者：谭伟健<br>
		班级：2014级软件工程2班<br>
		院系：计算机与网络安全学院<br>
	</p>
	<div id="chartmain" style="width:600px; height: 400px;"></div>
	<script type="text/javascript">
        //指定图标的配置和数据
        var option = {
            title:{
                text:'ECharts 数据统计'
            },
            tooltip:{},
            legend:{
                data:['用户来源']
            },
            xAxis:{
                data:["Android","IOS","PC","Ohter"]
            },
            yAxis:{

            },
            series:[{
                name:'访问量',
                type:'bar',
                data:[500,200,360,100]
            }]
        };
        //初始化echarts实例
        var myChart = echarts.init(document.getElementById('chartmain'));

        //使用制定的配置项和数据显示图表
        myChart.setOption(option);
    </script>
</body>
</html>