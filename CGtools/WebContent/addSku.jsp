<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<c:if test="${result=='success'}">
<font color="green">success</font>
</c:if>
<form action="<%=request.getContextPath()%>/JDSkuManageAction" method="post">
<table border="1">
	<tr>
		<td>京东id(*)</td>
		<td><input type="text" name="skuId"/></td>
	</tr>
	<tr>
		<td>美萍id(*)</td>
		<td><input type="text" name="cgId"/></td>
	</tr>
	<tr>
		<td>名称(*)</td>
		<td><input type="text" name="name"/></td>
	</tr>
	<tr>
		<td>类型</td>
		<td>
			<select name="type">
				<c:forEach items="${jdSkuTypes}" var="jdSkuType">
					<option value="${jdSkuType.id}">${jdSkuType.name}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td>采购数量(*)</td>
		<td><input type="text" name="buynumber"/></td>
	</tr>
	<tr>
		<td>京东价格</td>
		<td><input type="text" name="price"/></td>
	</tr>
	<tr>
		<td>出版社价格</td>
		<td><input type="text" name="publishingPrice"/></td>
	</tr>
	<tr>
		<td>条形码</td>
		<td><input type="text" name="barcode"/></td>
	</tr>
	<tr>
		<td>重要(*)</td>
		<td>
			<select name="important">
				<option value="0" selected="selected">否</option>
				<option value="1">是</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>京东是否有库存</td>
		<td>
			<select name="instock">
				<option value="1" selected="selected">是</option>
				<option value="0">否</option>
			</select>
		</td>
		<td colspan="2">
			<input type="hidden" name="action" value="add">
			<input type="submit" value="添加"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>