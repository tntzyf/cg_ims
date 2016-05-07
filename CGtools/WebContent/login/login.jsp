<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
</head>
<body>
<form action="<%=request.getContextPath()%>/UserAction">
username:<input name="username" type="text"/><br>
password:<input name="password" type="password"/>
<input name="method" value="login" type="hidden"/>
<input type="submit" value="submit">
</form>

</body>
</html>