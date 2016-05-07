<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	String id = request.getParameter("id");
	String type = request.getParameter("type");
	if("1".equals(type)||"2".equals(type)||"3".equals(type)){
	%>
		<a target="_blank" href="http://click.union.jd.com/JdClick/?unionId=11406&t=4&to=http://www.jd.com/product/<%=id%>.html"><%=id%></a>
	<%
	} else if ("4".equals(type)||"5".equals(type)){
	%>
		<a target="_blank" href="http://www.amazon.cn/dp/<%=id%>"><%=id%></a>
	<%
	} else if ("6".equals(type)){
	%>
		<a target="_blank" href="http://astore.amazon.com/conexant-20/detail/<%=id%>"><%=id%></a>
	<%	
	} else if ("7".equals(type)){
	%>
		<a target="_blank" href="http://astore.amazon.co.uk/7080ren-21/detail/<%=id%>"><%=id%></a>
	<%	
	} else if ("8".equals(type)){
	%>
		<a target="_blank" href="http://www.amazon.fr/dp/<%=id%>"><%=id%></a>
	<%	
	} else if ("9".equals(type)){
	%>
		<a target="_blank" href="http://www.amazon.de/dp/<%=id%>"><%=id%></a>
	<%	
	} else if ("10".equals(type)){
	%>
		<%=id%>&nbsp;
		<a target="_blank" href="http://astore.amazon.com/conexant-20/detail/<%=id%>">US</a>&nbsp;
		<a target="_blank" href="http://astore.amazon.co.uk/7080ren-21/detail/<%=id%>">UK</a>&nbsp;
		<a target="_blank" href="http://www.amazon.fr/dp/<%=id%>">FR</a>&nbsp;
		<a target="_blank" href="http://www.amazon.de/dp/<%=id%>">DE</a>&nbsp;
	<%	
	}
%>