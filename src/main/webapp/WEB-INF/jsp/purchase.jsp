<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<meta name="renderer" content="webkit">
<title></title>
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
	href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
<script src="//code.jquery.com/jquery-1.9.1.js"></script>
<script src="//code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
<link rel="stylesheet"
	href="http://jqueryui.com/resources/demos/style.css">
<link rel="stylesheet"
	href="${pageContext.request.contextPath }/js/artDialog-master/css/dialog.css">
<script src="${pageContext.request.contextPath }/js/artDialog-master/dist/dialog.js"></script>
</head>
<body>
	<%   
          java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat("yyyy-MM-dd"); 
          java.util.Date currentTime = new java.util.Date();//得到当前系统时间 
          String str_date = formatter.format(currentTime); //将日期时间格式化 
 	%> 
	<form method="post"
		action="${pageContext.request.contextPath }/statistics/purchaseByCondition1"
		id="listform">
		<div class="panel admin-panel">
			<div class="padding border-bottom">

				<ul class="search" style="padding-left: 10px;">
					<li>
						医药名称
						<input type="text" placeholder="请输入医药名称" id="drugName" name="drugName" value="${drugName }" style= "width:120px"/>  
					</li>
					<li>
						开始时间
						<input type="text" class="Wdate" value="${beginTime }" id="beginTime" name="beginTime"  
								 placeholder="请输入开始时间"
								 onClick="WdatePicker({lang:'zh-cn'})" />  
					</li>
					<li>
						结束日期
						<c:if test="${endTime == null}">
							<c:set var='endTime' value="<%=str_date%>" scope="page"></c:set>
						</c:if>
						<input type="text" class="Wdate" value="${endTime }" id="endTime" name="endTime"  
								 placeholder="请输入结束时间"
								 onClick="WdatePicker({lang:'zh-cn'})" />  
					</li>

					<li>
					
						<a href="javascript:void(0)" class="button border-main icon-search" name="keySearch" onclick="changesearch()"> 
							搜索
						</a>
					</li>
				</ul>

			</div>
			
	    	<!-- 为ECharts准备一个具备大小（宽高）的Dom -->
	    	<div id="main" style="width: 1100px;height:450px;"></div>			
		</div>
	</form>
	<script type="text/javascript">
		$("#drugName").autocomplete({
			source : "<c:url value="/drug/auto"/>",
			minLength : 1
		});
	
	//搜索
		function changesearch() {
			$('#listform').submit();
		};
		var drugName = document.getElementById('drugName').value;
		var beginTime = document.getElementById('beginTime').value;
		var endTime = document.getElementById('endTime').value;
        var myChart = echarts.init(document.getElementById('main'));
        // 显示标题，图例和空的坐标轴
        myChart.setOption({
            title: {
                text: '药品采购数量以及金额统计分析'
            },
            tooltip: {
            	trigger: 'axis'
            	},
            legend: {
                data:['采购数量','采购金额']
            },
            toolbox: {
                show : true,
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    magicType : {show: true, type: ['line', 'bar', 'stack', 'tiled']},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            calculable : true,
            xAxis: [
               {
                   type : 'category',
                   name : ''
               }
            ],
            yAxis: [
               {
                   type : 'value',
                   name : '数量'
               },
               {
                   type : 'value',
                   name : '金额',
                   axisLabel : {
                       formatter: '￥{value}'
                   }
               }
            ],
            series: [{
                name: '采购数量',
                type: 'bar',
                smooth:true,
                data: []
            },{
                name: '采购金额',
                type: 'line',
                yAxisIndex: 1,
                smooth:true,
                data: []
            }]
        });
        
        myChart.showLoading();    //数据加载完之前先显示一段简单的loading动画
        
        var names=[];    //类别数组（实际用来盛放X轴坐标值）
        var nums=[];    //销量数组（实际用来盛放Y坐标值）
        var prices=[];    //销量数组（实际用来盛放Y坐标值）
        
        $.ajax({
        type : "post",
        async : true,            //异步请求（同步请求将会锁住浏览器，用户其他操作必须等待请求完成才可以执行）
        url : "${pageContext.request.contextPath }/statistics/purchase2?drugName=${drugName}&&beginTime=${beginTime}&&endTime=${endTime}", //请求发送到Controller处
        data : {},
        dataType : "json",        //返回数据形式为json
        success : function(result) {
            //请求成功时执行该函数内容，result即为服务器返回的json对象
            if (result) {
                   for(var i=0;i<result.length;i++){       
                      names.push(result[i].time + '月');    //挨个取出类别并填入类别数组
                    }
                   for(var i=0;i<result.length;i++){       
                       nums.push(result[i].num);    //挨个取出销量并填入销量数组
                     }
                   for(var i=0;i<result.length;i++){       
                   	prices.push(result[i].price);    //挨个取出总额并填入总额数组
                     }
                   myChart.hideLoading();    //隐藏加载动画
                   myChart.setOption({        //加载数据图表
                       xAxis: {
                           data: names
                       },
                       series: [{
                           // 根据名字对应到相应的系列
                           name: '采购数量',
                           data: nums
                       },{
                           // 根据名字对应到相应的系列
                           name: '采购金额',
                           data: prices
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