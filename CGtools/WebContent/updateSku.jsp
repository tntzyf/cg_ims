<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>修改</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<c:if test="${result=='success'}">
<font color="green">success</font>
</c:if>
<form action="<%=request.getContextPath()%>/JDSkuManageAction" method="post">
<table border="1">
	<tr>
		<td>京东id</td>
		<td><input type="text" value="${sku.sku_id}" name="new_skuId" value="${sku.sku_id}"/>
			<input type="hidden" name="skuId" value="${sku.sku_id}">
		</td>
	</tr>
	<tr>
		<td>cg id</td>
		<td><input type="text" name="cgId" value="${sku.cgId}"/></td>
	</tr>
	<tr>
		<td>名称</td>
		<td><input type="text" name="name" value="${sku.name}"/></td>
	</tr>
	<tr>
		<td>类型</td>
		<td>
			<select name="type">
				<c:forEach items="${jdSkuTypes}" var="jdSkuType">
					<option value="${jdSkuType.id}" <c:if test="${sku.typeId==jdSkuType.id}">selected="selected"</c:if>>${jdSkuType.name}</option>
				</c:forEach>
			</select>
		</td>
	</tr>
	<tr>
		<td>采购数量</td>
		<td><input type="text" name="buynumber" value="${sku.buynumber}"/></td>
	</tr>
	<tr>
		<td>京东价格</td>
		<td><input type="text" name="price" value="${sku.price}"/></td>
	</tr>
	<tr>
		<td>出版社价格</td>
		<td><input type="text" name="publishingPrice" value="${sku.publishingPrice}"/></td>
	</tr>
	<tr>
		<td>条形码</td>
		<td><input type="text" name="barcode" value="${sku.barcode}"/></td>
	</tr>
	<tr>
		<td>重要</td>
		<td>
			<select name="important">
				<option value="0" <c:if test="${!sku.important}">selected="selected"</c:if>>否</option>
				<option value="1" <c:if test="${sku.important}">selected="selected"</c:if>>是</option>
			</select>
		</td>
	</tr>
	<tr>
		<td>特殊字段</td>
		<td>
			<input type="text" name="special_type" value="${sku.special_type}"/>
		</td>
	</tr>
	<tr>
		<td>是否有库存</td>
		<td>
			<select name="instock">
				<option value="1" <c:if test="${sku.instock}">selected="selected"</c:if>>是</option>
				<option value="0" <c:if test="${!sku.instock}">selected="selected"</c:if>>否</option>
			</select>
		</td>
		<td colspan="2">
			<input type="hidden" name="action" value="update">
			<input type="submit" value="修改"/>
		</td>
	</tr>
</table>
</form>
</body>
</html>