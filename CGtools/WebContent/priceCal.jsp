<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="com.cg.service.PriceCaculatorService"%>
<%@page import="java.util.List"%>
<%@page import="com.cg.domain.Item"%>
<%@page import="java.util.Map"%>
<%@page import="com.cg.domain.Sku"%>
<%@page import="java.util.Set"%>
<%@page import="java.util.Collections"%>
<%@page import="java.util.ArrayList"%>
<%@page import="com.cg.util.EntryComparator"%>
<%@page import="com.cg.util.SkuComparator"%>
<%@page import="java.math.BigDecimal"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<%
Float standPrice = (Float)session.getAttribute("standPrice");
Float maxPrice = (Float)session.getAttribute("maxPrice");
String type = (String)session.getAttribute("type");
Float couponCondition = (Float)session.getAttribute("couponCondition");
Float couponDiscount = (Float)session.getAttribute("couponDiscount");
%>
初始设置:
<form name="upform" action="<%=request.getContextPath()%>/couponSet.jsp" method="POST">
	coupon使用额度:<input name="standPrice" value="<%=standPrice==null?"":standPrice %>"><br/> 
	coupon拼凑上线:<input name="maxPrice" value="<%=maxPrice==null?"":maxPrice  %>"><br/>
	折扣用卷:<input name="couponCondition" value="<%=couponCondition==null?"":couponCondition  %>"> - 
	<input name="couponDiscount" value="<%=couponDiscount==null?"":couponDiscount  %>">
	<br/>
	多item:<input type="radio" name="type" value="0" <%if("0".equals(type)||type==null) {%>checked="checked"<%} %>/> &nbsp;
	单item:<input type="radio" name="type" value="1" <%if("1".equals(type)) {%>checked="checked"<%} %>/>
	<input type="submit" value="Submit" /><br/>
</form>

<hr>
<form name="upform" action="<%=request.getContextPath()%>/PriceAnalysisAction" method="POST" enctype="multipart/form-data">
	<input type="file" name="excelFile" <%if(standPrice==null || maxPrice==null) {%>disabled="disabled"<%} %> />
	<input type="submit" value="Submit" <%if(standPrice==null || maxPrice==null) {%>disabled="disabled"<%} %> /><br/>
</form>
<br></br>
<form action="<%=request.getContextPath()%>/JDPriceAndStockServlet" method="post">
<textarea rows="10" cols="100" name="jdids"></textarea>
<input type="submit" value="submit"/>
</form>

<font color="red"><%=request.getAttribute("error")==null?"":request.getAttribute("error") %></font>

<%
	Map<String, Object> result = (Map<String, Object>)request.getAttribute("result");
if(result!=null && !result.isEmpty()){
	List<Item> buyList = (List<Item>)result.get(PriceCaculatorService.BUY_LIST);
	List<Sku> items = (List<Sku>)result.get(PriceCaculatorService.ITEMS);
	Collections.sort(items, new SkuComparator());
	Map<String, Integer> inventoryData = (Map<String, Integer>)result.get(PriceCaculatorService.INVENTORY_DATA);
	Map<String, Integer> originalInventoryData = (Map<String, Integer>)result.get(PriceCaculatorService.ORIGINAL_INVENTORY_DATA);
%>

<table>
	<tr>
		<td>
			计算结果:
			<table border="1">
				<tr>
					<th></th>
					<th>总价</th>
					<th>商品</th>
				</tr>
				<%
					int m=0;
					for(Item item : buyList){
						m++;
				%>
				<tr>
					<td>第<%=m %>单</td>
					<td>
						<%=item.getTotalPrice() %>
					</td>
					<td>
						<%Map<Sku,Integer> map = item.getSkus(); 
						Set<Map.Entry<Sku,Integer>> skus = map.entrySet();
						List<Map.Entry<Sku,Integer>> listSkus = new ArrayList<Map.Entry<Sku,Integer>>();
						
						for(Map.Entry<Sku,Integer> entry : skus){
							listSkus.add(entry);
						}
						Collections.sort(listSkus,new EntryComparator());
							for(Map.Entry<Sku,Integer> entry : listSkus){
								%>
								<%if(entry.getKey().isSale()){%><font color="blue"><%} %>
								<%=entry.getKey().getTitle() %>
								<%if(entry.getKey().isSale()){%></font><%} %>
								 : <font color="red"><%=entry.getValue() %>个</font> * <%=entry.getKey().getPrice() %>
								 <%if(couponCondition!=null && couponDiscount!=null) {%>
								 	(<%=new BigDecimal((item.getTotalPrice()-couponDiscount)/item.getTotalPrice()*entry.getKey().getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue()%>)
								 <%} %>
								 <br/>
						<%
							}
						%>
					</td>
				</tr>
				<%} %>
			</table>
		</td>
		<%if(items!=null) {%>
		<td>
			Excel 数据:
			<table border="1">
				<tr>
					<th>&nbsp;</th>
					<th>商品名</th>
					<th>单价</th>
					<th>理想价格</th>
					<th>原始采购数量</th>
					<th>计算后剩余数量</th>
				</tr>
				<%
					int i = 0;
					int total_orignal_buy_number = 0;
					int total_post_calcuate_number = 0;
					for (Sku sku : items){ 
					++i;
					%>
				<tr>
					<td><%=i %></td>
					<td>
						<%if(sku.isSale()){%><font color="blue"><%} %>
						<%=sku.getTitle() %>
						<%if(sku.isSale()){%></font><%} %>
					</td>
					<td><%=sku.getPrice() %></td>
					<td>
					<%if(couponCondition!=null && couponDiscount!=null) {%>
					 	<%=new BigDecimal((couponCondition-couponDiscount)/couponCondition*sku.getPrice()).setScale(2, BigDecimal.ROUND_HALF_UP).floatValue()%>
					 <%} %>
					</td>
					<td><%=originalInventoryData.get(sku.getJdid()) %><%total_orignal_buy_number+=originalInventoryData.get(sku.getJdid()); %> </td>
					<td>
						<%if(originalInventoryData.get(sku.getJdid())!=inventoryData.get(sku.getJdid())){ %>
							<font color="red"><%=inventoryData.get(sku.getJdid())  %></font>
						<%}else{ %>
							<%=inventoryData.get(sku.getJdid())  %>
						<%} %>
						<%total_post_calcuate_number+=inventoryData.get(sku.getJdid()); %>
					</td>
				</tr>
				<%} %>
				<tr>
					<td>&nbsp;</td>
					<td>合计</td>
					<td>&nbsp;</td>
					<td><%=total_orignal_buy_number %></td>
					<td><%=total_post_calcuate_number %></td>
				</tr>
			</table>
		</td>
		<%} %>
	</tr>
</table>

<%	}%>

</body>
</html>