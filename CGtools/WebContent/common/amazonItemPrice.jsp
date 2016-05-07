<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.cg.util.DBUtil"%><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String skuId = request.getParameter("skuId");
	String sql = "select * from sku_price_ext where sku_id=?";
	List<Map<String,Object>> list = DBUtil.executeQuery(sql,new Object[]{skuId});
	StringBuilder sb = new StringBuilder();
	if(list != null){
		for(Map<String,Object> map: list){
			sb.append(map.get("country")).append(":").append(map.get("price")).append(",");
		}
	}
	out.print(sb);
%>