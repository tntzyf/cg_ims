
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.cg.util.DBUtil"%><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String importantStr = request.getParameter("important");
int important = Integer.parseInt(importantStr);
String id = request.getParameter("id");
String sql = "update sku set important=? where sku_id=?";
List params = new ArrayList();
params.add(important);
params.add(id);
DBUtil.executeUpdate(sql,params);
%>