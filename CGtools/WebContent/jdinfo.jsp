<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 tdansitional//EN" "http://www.w3.org/td/html4/loose.dtd">

<%@page import="java.util.Map"%>
<%@page import="java.util.List"%>
<%@page import="com.cg.domain.JDInfo"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>
<body>
<script type="text/javascript" src="/js/jquery-1.11.0.js"></script>
<jsp:include page="header.jsp"></jsp:include>
<%
	Map<String,Object> maps = (Map)request.getAttribute("jdresult");
	List<JDInfo> jdInfoList = (List)maps.get("jdinfos");
	List<String> errorMessages = (List)maps.get("errorMessages");
%>
<script type="text/javascript">
	function hiden(){
		$(".stock").hiden();
	}
	function show(){
		$(".stock").show();
	}
</script>
<table>
	<tr>
		<td>京东id</td>
		<td>价格</td>
		<td class="stock">库存</td>
	</tr>
	<%for(JDInfo jdinfo : jdInfoList){%>
		<tr>
			<td><%=jdinfo.getJdid() %></td>
			<td><%=jdinfo.getPrice() %></td>
			<td class="stock"><%=jdinfo.getStock() %></td>
		</tr>
	<%
		}
	%>
	
</table>
</body>
</html>