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
String standPriceStr = request.getParameter("standPrice");
String maxPriceStr = request.getParameter("maxPrice");
String huafeiPriceStr = request.getParameter("huafeiPrice");
String typeStr = request.getParameter("type");
String couponConditionStr = request.getParameter("couponCondition");
String couponDiscountStr = request.getParameter("couponDiscount");
float standPrice = Float.parseFloat(standPriceStr);
float maxPrice = Float.parseFloat(maxPriceStr);
float couponCondition = Float.parseFloat(couponConditionStr);
float couponDiscount = Float.parseFloat(couponDiscountStr);
session.setAttribute("standPrice",standPrice);
session.setAttribute("maxPrice",maxPrice);
session.setAttribute("type",typeStr);
session.setAttribute("couponCondition",couponCondition);
session.setAttribute("couponDiscount",couponDiscount);
response.sendRedirect("priceCal1.jsp");
%>
</body>
</html>