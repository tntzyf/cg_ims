
<%@page import="java.util.ArrayList"%>
<%@page import="java.util.List"%>
<%@page import="com.cg.util.DBUtil"%><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
String quantityStr = request.getParameter("quantity");
int quantity = Integer.parseInt(quantityStr);
String id = request.getParameter("id");
String sql = "update sku set buy_number=? where sku_id=?";
List params = new ArrayList();
params.add(quantity);
params.add(id);
DBUtil.executeUpdate(sql,params);
%>