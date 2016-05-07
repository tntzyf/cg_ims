<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 tdansitional//EN" "http://www.w3.org/td/html4/loose.dtd">

<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.cg.domain.JDInfo"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<style type="text/css">
body {font-size:12px;}
</style>
<jsp:include page="header.jsp"></jsp:include>
<c:if test="${remoteGeneratorInfoEntity.errorMessages!=null && fn:length(remoteGeneratorInfoEntity.errorMessages)>0}">
<table>
	<tr>
		<th>错误信息</th>
	</tr>
	<c:forEach var="errorMessage" items="${remoteGeneratorInfoEntity.errorMessages}">
		<tr>
			<td>${errorMessage }</td>
		</tr>
	</c:forEach>
</table>
<hr></hr>
</c:if>
<table border="1">
	<tr>
		<td>skuId</td>
		<td>价格</td>
		<td>公式价格</td>
	</tr>
	<c:forEach var="remoteSku" items="${remoteGeneratorInfoEntity.remoteSkus}">
		<tr>
			<td>${remoteSku.skuId }</td>
			<td>${remoteSku.price }</td>
			<td>${(remoteSku.price+4.5)*huilv }</td>
		</tr>
	</c:forEach>
</table>
</body>
</html>