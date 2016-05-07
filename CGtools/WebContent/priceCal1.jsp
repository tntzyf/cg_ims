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
<%@page import="java.math.BigDecimal"%>
<%@page import="org.apache.commons.lang3.StringUtils"%>
<%@page import="com.cg.util.PriceTool"%><html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<script type="text/javascript">
function sub(){
	var checked = $("#form1").find("input[name='order']:checked");
	$.each( checked, function(i, n){
		$(n).parent().parent().remove();
		}); 
}
function sub1(){
	$("#removestockButton").attr("disabled",'disabled');
	$.ajax({
		   type: "POST",
		   url: "<%=request.getContextPath()%>/JDSkuManageAction",
		   data: $("#form1").serialize(),
		   success: function(msg){
			   var checked = $("#form1").find("input[name='order']:checked");
				$.each( checked, function(i, n){
				$(n).parent().parent().remove();
				}); 
				$("#removestockButton").removeAttr("disabled");
		   }
		});
}
$(document).ready(function(){
	$("#checkALL").click(function(){
		$("#form1").find("input[name='order']").attr("checked",$(this).attr("checked"));
	});
}); 

</script>
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
	<input type="submit" value="Submit"/><br/>
</form>
<hr></hr>
<form name="upform" action="<%=request.getContextPath()%>/PriceAnalysisAction" method="POST">
	<c:forEach items="${jdSkuTypes}" var="jdSkuType">
		<input type="checkbox" name="types" value="${jdSkuType.id}"/>:${jdSkuType.name} &nbsp;
	</c:forEach>
	<br/>
	<jsp:include page="jsp/speical_type_list.jsp"></jsp:include>
	<br/>
	---------------排除条件-----------------
	<c:forEach items="${jdSkuTypes}" var="jdSkuType">
		<input type="checkbox" name="exclude_types" value="${jdSkuType.id}"/>:${jdSkuType.name} &nbsp;
	</c:forEach>
	<br/>
	<jsp:include page="jsp/speical_type_exclude_list.jsp"></jsp:include>
	<br/>
	<input type="submit" value="凑单" <%if(standPrice==null || maxPrice==null) {%>disabled="disabled"<%} %> />
	<br/>
	<input type="hidden" name="action" value="generateRestult">
</form>
<hr>
<form name="upform" action="<%=request.getContextPath()%>/PriceAnalysisAction" method="POST">
	<textarea rows="10" cols="60" name="ids">${ids}</textarea>
	<input type="submit" value="凑单" <%if(standPrice==null || maxPrice==null) {%>disabled="disabled"<%} %> />
	<br/>
	<input type="hidden" name="action" value="generateRestult1">
</form>
<hr>
<br></br>


<font color="red"><%=request.getAttribute("error")==null?"":request.getAttribute("error") %></font>

<%
	Map<String, Object> result = (Map<String, Object>)request.getAttribute("result");
if(result!=null && !result.isEmpty()){
	List<Item> buyList = (List<Item>)result.get(PriceCaculatorService.BUY_LIST);
	List<Sku> items = (List<Sku>)result.get(PriceCaculatorService.ITEMS);
	Collections.sort(items, new SkuComparator());
	Map<String, Integer> inventoryData = (Map<String, Integer>)result.get(PriceCaculatorService.INVENTORY_DATA);
	Map<String, Integer> originalInventoryData = (Map<String, Integer>)result.get(PriceCaculatorService.ORIGINAL_INVENTORY_DATA);
	
	StringBuilder itemInventoryInfo = new StringBuilder();
%>

<table>
	<tr>
		<td valign="top">
			计算结果:
			<form action="" method="post" id="form1">
				<table border="1" style="margin:auto;">
					<tr>
						<th><input type="checkbox" id="checkALL"/></th>
						<th>总价</th>
						<th>商品</th>
					</tr>
					<%
						int m=0;
						for(Item item : buyList){
							m++;
					%>
					<tr>
						<%
						Map<Sku,Integer> map1 = item.getSkus(); 
						Set<Map.Entry<Sku,Integer>> skus1 = map1.entrySet();
						String value = "";
						boolean hasImportantSku = false;
						for(Map.Entry<Sku,Integer> entry1 : skus1){
							if(!StringUtils.isBlank(value)){
								value+=",";
							}
							value = value+ entry1.getKey().getJdid()+":"+entry1.getValue();
							if(entry1.getKey().isSale()){
								hasImportantSku=true;
							}
						}
						%>
						<%
						//{1-[20053414:1,20068536:1,20066630:1]}|{2-[20053414:1,20068536:1,20066630:1]}
						if(itemInventoryInfo.length()>0){
							itemInventoryInfo.append("|").append("<br/>");
						}
						itemInventoryInfo.append("{").append(m).append("-").append("[").append(value).append("]").append("}");
						%>
						<td><input name="order" type="checkbox" value="<%=value %>">第<%=m %>单 <%if(hasImportantSku){ %><font color="red">重要订单</font><%} %> </td>
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
									 	(<%=PriceTool.round((item.getTotalPrice()-couponDiscount)/item.getTotalPrice()*entry.getKey().getPrice())%>, <%if(entry.getKey().getPublishPrice()!=null){ %>厂价:<%=entry.getKey().getPublishPrice() %>,<%=PriceTool.round((item.getTotalPrice()-couponDiscount)/item.getTotalPrice()*entry.getKey().getPrice()/entry.getKey().getPublishPrice())%>折<%} %>)
									 <%} %>
									 &nbsp;<a target="_blank" href="http://click.union.jd.com/JdClick/?unionId=11406&t=4&to=http://www.jd.com/product/<%=entry.getKey().getJdid()%>.html"><%=entry.getKey().getJdid() %></a>
									 <br/>
							<%
								}
							%>
						</td>
					</tr>
					<%} %>
					<tr>
						<td colspan="3">
							<input type="hidden" name="action" value="removeStock">
							<input type="button" value="去库存" id="removestockButton" onclick="sub1()"/>
						</td>
					</tr>
				</table>
			</form>
			<%=itemInventoryInfo %>
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
					<th>京东是否有货</th>
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
						&nbsp;<a target="_blank" href="http://click.union.jd.com/JdClick/?unionId=11406&t=4&to=http://www.jd.com/product/<%=sku.getJdid()%>.html"><%=sku.getJdid() %></a>
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
					<td>
						<%if(!sku.isHasstock()){%><font color="blue"><%} %>
							<%=sku.isHasstock() %>
						<%if(!sku.isHasstock()){%></font><%} %>
					</td>
				</tr>
				<%} %>
				<tr>
					<td>&nbsp;</td>
					<td>合计</td>
					<td>&nbsp;</td>
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