<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<%
	String skuId = (String)request.getParameter("skuId");
	//是否已经登录过广告联盟
	String HAS_REGISTYED_IN_GGLM = "hasRegistedInGglm";
	Boolean hasRegistedInGglm = (Boolean)session.getAttribute(HAS_REGISTYED_IN_GGLM);
	if(hasRegistedInGglm==null || !hasRegistedInGglm){
		session.setAttribute(HAS_REGISTYED_IN_GGLM,true);
		response.sendRedirect("http://click.union.jd.com/JdClick/?unionId=11406&t=4&to=http://www.jd.com/product/"+skuId+".html");
	}else{
		response.sendRedirect("http://item.jd.com/"+skuId+".html");
	}
%>
</body>
</html>