<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="com.cg.util.DBUtil"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Collections"%>
<%
String sql="select distinct special_type from sku where special_type is not null";
List params = new ArrayList();
List<Map<String,Object>> list = DBUtil.executeQuery(sql,params);
%>

<%@page import="java.util.ArrayList"%><select name="special_type">
	<option value=""></option>
	<%for(Map<String,Object> map : list){%>
		<option value="<%=map.get("special_type") %>"><%=map.get("special_type") %></option>
	<%} %>
</select>