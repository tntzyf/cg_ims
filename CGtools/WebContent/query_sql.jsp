<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.cg.util.DBUtil"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.Collection"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.ArrayList"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>

<%
String sql = request.getParameter("sql");
List<Map<String,Object>> listResults = null;
if(!StringUtils.isBlank(sql)){
	listResults = DBUtil.executeQuery(sql,Collections.EMPTY_LIST);
}
%>
数据查询sql
<form action="${ctx }/query_sql.jsp" method="post">
<textarea rows="20" cols="100" name="sql"></textarea>

	<span>sku type说明</span><br/>
	1=jd音像,
	2=jd图书,
	3=jd游戏,
	4=zy音像,
	5=zy图书,
	6=Amazon US,
	7=Amazon UK,
	8=Amazon FR,
	9=Amazon DE,
<br/>
<input type="submit" value="submit"/>
</form>
<table>
	<tr>
		<td>
			查询价格区间 并按照价格排序 (查询大于100小于120的商品)
		</td>
		<td>
			select * from sku where jd_price >100 and jd_price<120 order by jd_price desc
		</td>
	</tr>
</table>
<hr></hr>
<%
	if(listResults!=null && listResults.size()>0){
		List<String> keys = new ArrayList<String>();
		keys.addAll(listResults.get(0).keySet());
		%>
		<table border="1">
			<tr>
			
			<%
				for(String title : keys){
			%>
				<th><%=title %></th>
			<%
				}
			%>
			</tr>
		
		<%
		for(Map<String,Object> map : listResults){
			%>
			<tr>
				<%
					for(String title : keys){
				%>
					<th><%=map.get(title) %></th>
				<%
					}
				%>
			</tr>
			<%
		}
		%>
		</table>
		<%
	}
%>
<table>
	
</table>
</body>
</html>