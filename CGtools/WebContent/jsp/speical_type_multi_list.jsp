<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.cg.util.DBUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%
String sql="select distinct special_type from sku where special_type is not null";
List<Map<String,Object>> list = DBUtil.executeQuery(sql,java.util.Collections.emptyList());
%>

<select name="special_type" multiple="multiple">
	<option value=""></option>
	<option value="null">无任何打折</option>
	<%for(Map<String,Object> map : list){%>
		<option value="<%=map.get("special_type") %>"><%=map.get("special_type") %></option>
	<%} %>
</select>