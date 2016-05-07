<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<style type="text/css">
body {font-size:12px;}
</style>
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.3.2.js"></script>
&nbsp; <a href="<%=request.getContextPath()%>/addSku.jsp">添加sku</a> 
&nbsp; <a href="<%=request.getContextPath()%>/skuList.jsp">查询sku</a>
&nbsp; <a href="${ctx}/generatePriceAndStockIndex.jsp">获取价格&库存</a>
&nbsp; <a href="<%=request.getContextPath()%>/priceCal1.jsp">凑单</a>
&nbsp; <a href="<%=request.getContextPath()%>/findRemotePrice.jsp">远程查询价格</a>
&nbsp; <a href="<%=request.getContextPath()%>/qukucun.jsp">去库存</a>
&nbsp; <a href="<%=request.getContextPath()%>/sql.jsp">sql</a>
&nbsp; <a href="<%=request.getContextPath()%>/query_sql.jsp">查询sql</a>
&nbsp; <a href="${ctx}/generatePromotionsIndex.jsp">打折信息</a>
<hr>

<jsp:include page="includes/common.jsp"></jsp:include>