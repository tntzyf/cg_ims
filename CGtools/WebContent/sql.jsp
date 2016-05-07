<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.cg.util.DBUtil"%>
<%@page import="java.util.Collections"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
数据处理sql
<%
String sql = request.getParameter("sql");
if(!StringUtils.isBlank(sql)){
	DBUtil.executeUpdate(sql,Collections.EMPTY_LIST);
}
%>
<form action="${ctx }/sql.jsp" method="post">
<textarea rows="20" cols="100" name="sql"></textarea>
<br/>
<input type="submit" value="submit"/>
</form>
</body>
</html>