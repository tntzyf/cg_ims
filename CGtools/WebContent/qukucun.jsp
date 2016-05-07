<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<form action="${ctx }/JDSkuManageAction" method="post">
<textarea rows="20" cols="100" name="inventoryInfo"></textarea>
<br/>
<input type="hidden" name="action" value="removeStockByText"/>
<input type="submit" value="submit"/>
</form>
</body>
</html>