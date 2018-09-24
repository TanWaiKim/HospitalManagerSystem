<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>  
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">  
<html>  
 <head>  
 <link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/pintuer.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/admin.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/css/all.css">
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/jquery-1.8.1.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath }/js/echarts.js"></script>
<script src="${pageContext.request.contextPath }/js/pintuer.js"></script>
<script src="${pageContext.request.contextPath }/js/list.js"></script>
<script src="${pageContext.request.contextPath}/js/ajaxfileupload.js"></script>
<script src="${pageContext.request.contextPath }/js/My97DatePicker/WdatePicker.js"></script> 
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/artDialog-master/css/dialog.css">
<script src="${pageContext.request.contextPath }/js/artDialog-master/dist/dialog.js"></script>
 </head>  
  <body>  
  	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
  	<div id="main" style="width: 1100px;height:450px;"></div>	 
  
	<script type="text/javascript">
        var myChart = echarts.init(document.getElementById('main'));
        // 显示标题，图例和空的坐标轴
        myChart.setOption({
            title: {
                text: '各医药种类所占库存量',
                x:'center'
            },
            tooltip: {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            	},
            legend: {
                x : 'center',
                y : 'bottom',
                data : []
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {
                        show: true, 
                        type: ['pie', 'funnel']
                    },
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            series: [        {
                name:'医药种类',
                type:'pie',
                radius : [20, 150],
                center : ['50%', 200],
                roseType : 'radius',
                width: '40%',       // for funnel
                max: 60,            // for funnel
                itemStyle : {
                    normal : {
                        label : {
                            show : false
                        },
                        labelLine : {
                            show : false
                        }
                    },
                    emphasis : {
                        label : {
                            show : true
                        },
                        labelLine : {
                            show : true
                        }
                    }
                },
                data: []
            }]
        });
        
        myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
        
        $.ajax({
        type : "post",
        async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url : "${pageContext.request.contextPath }/statistics/category1?", //请求发送到Controller处
        data : {},
        dataType : "json",        //返回数据形式为json
        success : function(result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            if (result) {
                var names=[];    //类别数组（实际用来盛放X轴坐标值）
                var nums=[];    //销量数组（实际用来盛放Y坐标值）
                for(var i=0;i<result.length;i++){       
                   names.push(result[i].name);    //挨个取出类别并填入类别数组
                 }
                for(var i=0;i<result.length;i++){       
                	nums.push(result[i].value);    //挨个取出类别并填入类别数组
                  }
                   myChart.hideLoading();    //隐藏加载动画
                   myChart.setOption({        //加载数据图表
                	   legend: {data:names},
                	   series: [{
                           // 根据名字对应到相应的系列
                           data:result
                       }]
                   });    
            }
        
       },
        error : function(errorMsg) {
            //请求失败时执行该函数
        	var d = dialog({
				okValue: '确定',
				title: '温馨提示',
				content: '阿偶，图表请求错误',

				width: 200,
				height: 50,
				ok: function () {
					
				}
			});
			d.showModal();
        myChart.hideLoading();
        }
   })

	</script>
  </body>  
</html>  