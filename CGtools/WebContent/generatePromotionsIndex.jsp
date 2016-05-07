<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="com.cg.util.DBUtil"%>
<%@page import="java.util.Collections"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<script type="text/javascript">
function confirmGeneratePromotionInfoInDB()
{
var r=confirm("是否抓取数据？耗时较长"); 
if (r==true) 
  { 
	generatePromotionInfoInDB();
  } 
}
function generatePromotionInfoInDB() 
{
	//var type = $("#type").val();
	//var ajaxUrl = '${ctx}/generatePriceAndStockInDB.jsp?type='+type;
	//$.get(ajaxUrl);

	$.ajax({
        type: "POST",
        url:"${ctx}/generatePromotionInfoInDB.jsp",
        data:$('#getPriceForm').serialize(),
        async: true,
        error: function(request) {
            alert("Connection error");
        },
        success: function(data) {
        	alert("抓取完毕");
        }
    });
	alert("已开始抓取");
}

</script>
<%
	List<Map<String,Object>> result = DBUtil.executeQuery("select config_value from config where config_key='jd_sku_promotion_update'", Collections.EMPTY_LIST);
	String time = null; 
	if(result!=null && !result.isEmpty()){
		time = (String)result.get(0).get("config_value");
	}
	List<Map<String,Object>> errors = DBUtil.executeQuery("select * from error_message where type=2", Collections.EMPTY_LIST);
%>
<form id="getPriceForm" action="${ctx}/generatePriceAndStockInDB.jsp" method="post">
<table>
	<tr>
		<td>抓取
			<select name="type" id="type">
				<option value="0">库存大于0的商品</option>
				<option value="1">抓取全部商品</option>
			</select>
		</td>
		<td>
			<select name="skuType" multiple="multiple" style="height: 150px">
				<c:forEach items="${jdSkuTypes}" var="jdSkuType">
					<option value="${jdSkuType.id}">${jdSkuType.name}</option>
				</c:forEach>
			</select>
		</td>
		<td><input type="button" value="抓取" onclick="confirmGeneratePromotionInfoInDB()"/></td>
	</tr>
</table>
</form>
<br/>

<hr></hr>
<table>
	<tr>
		<th>上次更新时间:</th>
		<th><%=time %></th>
	</tr>
	<tr>
		<td>上次更新结果</td>
		<td></td>
	</tr>
	<%
		if(errors!=null&&!errors.isEmpty()){
		for(Map<String,Object> map : errors) {%>
		<tr>
			<td><%=map.get("error") %></td>
			<td><a href="<%=request.getContextPath()%>/ErrorMessageAction?action=remove&messageId=<%=map.get("id") %>">删除</a></td>
		</tr>
	<%}}else{ %>
		<tr>
			<td>成功</td>
			<td></td>
		</tr>
	<%} %>
	
</table>
</body>
</html>