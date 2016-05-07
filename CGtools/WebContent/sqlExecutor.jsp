<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">


<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.cg.util.DBUtil"%>
<%@page import="java.util.Collections"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<%
	String sql = request.getParameter("sql");
	if(!StringUtils.isBlank(sql)){
		sql = sql.toLowerCase();
		if(!StringUtils.contains(sql,"drop") && StringUtils.contains(sql,"truncate")){
			DBUtil.executeUpdate(sql, Collections.EMPTY_LIST);
		}
	}
%>
<form action="<%=request.getContextPath()%>/sqlExecutor.jsp">
<textarea rows="10" cols="60" name="sql">${sql}</textarea>
<input type="submit" value="submit"/>
</form>

</body>
</html>