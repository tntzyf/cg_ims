<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.cg.service.JDSkuService"%>
<%@page import="org.apache.commons.lang3.StringUtils"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String idsStr = request.getParameter("ids");
		idsStr = StringUtils.deleteWhitespace(idsStr);
		JDSkuService.getInstance().updateSkuPrice(idsStr); 
	%>
</body>
</html>