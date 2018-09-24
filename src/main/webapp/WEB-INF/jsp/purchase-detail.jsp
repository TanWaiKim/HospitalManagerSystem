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
<script type="text/javascript"
    src="${pageContext.request.contextPath }/js/jquery-1.8.1.min.js"></script>
<script src="${pageContext.request.contextPath }/js/pintuer.js"></script>
</head>
<body>
    <div class="panel">
        <div class="panel-head">
            <a class="button bg-blue"
				href="${pageContext.request.contextPath }/purchase/stockByPurchaseNo?purchaseNo=${purchase.purchaseNo }">
				<span class="icon-database"></span> 入库
		   </a>
		   &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
		   &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
		   &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
		   &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp; &nbsp;
            <strong>采药单${purchase.purchaseNo }的详细信息</strong> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        </div>
        <table class="table">
            <tbody>
                <tr>
                    <td style="width:300px;border:1px solid cadetblue;" class="current">药品种类：</td>
                    <td style="border:1px solid cadetblue;">${purchase.drug.drugtype.drugtypeName }</td>
                </tr>
                <tr>
                    <td style="border:1px solid cadetblue;" class="current">药品名称：</td>
                    <td style="border:1px solid cadetblue;">${purchase.drug.drugName }</td>
                </tr>
                <tr>
                    <td style="border:1px solid cadetblue;" class="current">采购单价（元）：</td>
                    <td style="border:1px solid cadetblue;">${purchase.drug.purchasePrice }</td>
                </tr>
                <tr>
                    <td style="border:1px solid cadetblue;" class="current">预售单价（元）：</td>
                    <td style="border:1px solid cadetblue;">${purchase.drug.salePrice }</td>
                </tr>
                <tr>
                    <td style="border:1px solid cadetblue;" class="current">采购数量：</td>
                    <td style="border:1px solid cadetblue;">${purchase.quantity }</td>
                </tr>
          
                <tr>
                    <td style="border:1px solid cadetblue;" class="current">计量单位：</td>
                    <td style="border:1px solid cadetblue;" >${purchase.drug.unit }</td>
                </tr>
                <tr>
                    <td style="border:1px solid cadetblue;" class="current">生产日期：</td>
                    <td style="border:1px solid cadetblue;">${purchase.drug.produceTime }</td>
                </tr>
                
                <tr>
                    <td style="border:1px solid cadetblue;" class="current">有效期至：</td>
                    <td style="border:1px solid cadetblue;">${purchase.drug.validTime }</td>
                </tr>
                
                <tr>
                    <td style="border:1px solid cadetblue;" class="current">产品批号：</td>
                    <td style="border:1px solid cadetblue;">${purchase.drug.drugNo }</td>
                </tr>
                
                <tr>
                    <td style="border:1px solid cadetblue;" class="current">入库状态：</td>
                    <td style="border:1px solid cadetblue;">
                    	<c:if test="${purchase.isStock == 1 }">
                    		已入库
                    	</c:if>
                    	<c:if test="${purchase.isStock == 0 }">
                    		未入库
                    	</c:if>
                    </td>
                </tr>
                
            </tbody>
        </table>
        
    </div>
</body>
</html>