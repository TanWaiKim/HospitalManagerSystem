<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/echarts.js"></script>
<!--  
<script type="text/javascript" src="js/echarts-2.2.7/build/dist/echarts-all.js"></script>
-->
<title>Insert title here</title>
</head>
<body>
<div id='line' style="height:500px;border:1px solid #ccc;padding:10px;"></div>
<script type="text/javascript">
var myChart = echarts.init(document.getElementById('line'));
var lineOption = {
        title : {
            text: '',
            subtext: ''
        },
        tooltip : {
            trigger: 'axis' //item 点在哪条线上显示哪条线上的数据，axis点在哪个坐标点上显示对于点上所有数据
        },
        legend: {
            data:[]
        },
        toolbox: {
            show : true,
            orient : 'vertical',
            x: 'right',
            y: 'center',
            feature : {
                mark : {show: true},
                dataView : {show: true, readOnly: false},
                magicType : {show: true, type: ['line', 'bar']},
                restore : {show: true},
                saveAsImage : {
                    show: true,
                    name:'折线图'//保存的图片名次
                    }
            }
        },
        calculable : true,
        xAxis : [
            {
                type : 'category',
                boundaryGap : false,
                data : []
            }
        ],
        yAxis : [
            {
                type : 'value',
                axisLabel : {
                    formatter: '{value} '
                }
            }
        ],
        grid: {
            x:30,//设置该图形与对应div的左边距
            y:30,//设置该图形与对应div的上边距
            x2:50,//设置该图形与对应div的右边距
            y2:10//设置该图形与对应div的下边距
        },
        series : []
    };

myChart.setOption(lineOption);
var ajax = function() {
    $.ajax({
        url : '${pageContext.request.contextPath }/echarts/show',
        success: function(responseText) {
            //请求成功时处理
            var responseText = eval('(' + responseText + ')'); 
            lineOption.legend.data=responseText.legend;
            lineOption.xAxis[0].data = responseText.xAxis;
            var serieslist = responseText.series;
            //alert(serieslist);
            for(var i=0;i<serieslist.length;i++) {
                lineOption.series[i] = serieslist[i];
            }
            //alert(lineOption.series);
            myChart.setOption(lineOption,true);
        },
        complete: function() {
            //请求完成的处理
        },
        error: function() {
            //请求出错处理
            alert("加载失败");
        }
    })
}

window.setTimeout(ajax,100);
//window.setInterval(ajax,1000*60*5);
</script>
</body>

</html>