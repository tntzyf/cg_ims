
<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.cg.util.DBUtil"%><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="java.util.Collections"%>
<%
String importantBuyItemTypeSizeSql = "select t1.type as all_type,*  from ((select type,count(type) as allTypeNumber,sum(buy_number) as allBuyNumber from sku group by type) t1 left join (select type,count(type) as importantTypeNumber,sum(buy_number) as importantBuyNumber from sku where important=1 group by type) t2 on t1.type=t2.type) left join (select type,count(type) as nonImportantTypeNumber,sum(buy_number) as nonImportantBuyNumber from sku where important=0 group by type) t3 on t1.type=t3.type";
List<Map<String,Object>> list = DBUtil.executeQuery(importantBuyItemTypeSizeSql,Collections.emptyList());
%>

<table>
	<tr>
		<th>类型</th>
		<th>商品采购类型总数</th>
		<th>商品采购总数</th>
		<th>重要商品采购类型总数</th>
		<th>重要商品采购总数</th>
		<th>非重要商品采购类型总数</th>
		<th>非重要商品采购总数</th>
	</tr>
	<% for(Map<String,Object> map : list){ %>
	<tr>
		<th><%=((Map<String,String>)application.getAttribute("jdSkuTypeMap")).get(map.get("all_type"))%></th>
		<th><%=map.get("alltypenumber") %></th>
		<th><%=map.get("allbuynumber") %></th>
		<th><%=map.get("importanttypenumber") %></th>
		<th><%=map.get("importantbuynumber") %></th>
		<th><%=map.get("nonimportanttypenumber") %></th>
		<th><%=map.get("nonimportantbuynumber") %></th>
	</tr>
	<%} %>
</table>