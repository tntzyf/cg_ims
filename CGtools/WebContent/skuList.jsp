<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>sku查询</title>
</head>
<body>
<jsp:include page="header.jsp"></jsp:include>
<script type="text/javascript">
function removeSku(skuId){
	var r=confirm("确认删除?"); 
	if (r==true) 
	  { 
		$.ajax({
			   type: "GET",
			   url: "${ctx}/JDSkuManageAction",
			   data: "action=remove&skuId="+skuId,
			   success: function(msg){
			     $("#tr_"+skuId).remove();
			   }
		}); 
	  } 
	
}

function changeQuantity(skuId,quantity_input_id){
	var quantity = $("#"+quantity_input_id).val();
	$.ajax({
		type: "GET",
		url: "${ctx}/common/changeQuantity.jsp",
		data: "quantity="+quantity+"&id="+skuId,
		success: function(msg){
			$("#span_"+skuId).html('success');
		},
		error:function(msg){
			$("#span_"+skuId).html('error');
		}
	}); 
}

function changeImportant(skuId,important){
	$.ajax({
		type: "GET",
		url: "${ctx}/ajax/changeImportant.jsp",
		data: "important="+important+"&id="+skuId,
		success: function(msg){
			if(important=='1' || important == 1){
				$("#span_important_"+skuId).html('true');
			}else{
				$("#span_important_"+skuId).html('false');
			}
		},
		error:function(msg){
			$("#span_"+skuId).html('error');
		}
	}); 
}

function redirect(){
	$("#currentPage").val($("#currentPageForm").val());
	$("#redirectForm").submit();
}
</script>
	<table>
		<tr>
			<td>
				<table>
					<tr>
						<td>
							<form action="<%=request.getContextPath()%>/JDSkuManageAction">
								<table border="1">
									<tr>
										<td>
											京东Id:<input type="text" name="skuId" value="${query_skuId}"/> &nbsp; 
											美萍Id:<input type="text" name="cgId" value="${query_cgId}"/> &nbsp;
											name:<input type="text" name="name" value="${query_name}"/> &nbsp;
											条形码:<input type="text" name="barcode" value="${query_barcode}"/> &nbsp;
											类型:<select name="type">
													<option value=""></option>
													<c:forEach items="${jdSkuTypes}" var="jdSkuType">
														<option value="${jdSkuType.id}" <c:if test="${query_type==jdSkuType.id}">selected="selected"</c:if>>${jdSkuType.name}</option>
													</c:forEach>
												</select> &nbsp;
											重要:<select name="important">
													<option value=""></option>
													<option value="1" <c:if test="${query_important=='1'}">selected="selected"</c:if>>是</option>
													<option value="0" <c:if test="${query_important=='0'}">selected="selected"</c:if>>否</option>
												</select> &nbsp;
											库存:<select name="instock">
													<option value=""></option>
													<option value="1" <c:if test="${query_instock=='1'}">selected="selected"</c:if>>有</option>
													<option value="0" <c:if test="${query_instock=='0'}">selected="selected"</c:if>>无</option>
												</select> &nbsp;
											&nbsp;
											特殊字段:<jsp:include page="jsp/speical_type_list.jsp"></jsp:include>
										</td>
									</tr>
									<tr>
										<td>
											<input type="hidden" name="action" value="query"/>
											<input type="submit" name="submit" value="submit"/>
										</td>
									</tr>
								</table>
							</form>
						</td>
					</tr>
					<tr>
						<td><jsp:include page="buyCount.jsp"></jsp:include></td>					
					</tr>	
				</table>
			</td>
			<td>
				<form action="<%=request.getContextPath()%>/JDSkuManageAction" method="post">
					<table border="1">
						<tr>
							<td>京东id(*)</td>
							<td><input type="text" name="skuId"/></td>
						</tr>
						<tr>
							<td>cg id(*)</td>
							<td><input type="text" name="cgId"/></td>
						</tr>
						<tr>
							<td>名称(*)</td>
							<td><input type="text" name="name"/></td>
						</tr>
						<tr>
							<td>类型</td>
							<td>
								<select name="type">
									<c:forEach items="${jdSkuTypes}" var="jdSkuType">
										<option value="${jdSkuType.id}">${jdSkuType.name}</option>
									</c:forEach>
								</select>
							</td>
						</tr>
						<tr>
							<td>采购数量(*)</td>
							<td><input type="text" name="buynumber"/></td>
						</tr>
						<tr>
							<td>京东价格</td>
							<td><input type="text" name="price"/></td>
						</tr>
						<tr>
							<td>出版社价格</td>
							<td><input type="text" name="publishingPrice"/></td>
						</tr>
							<tr>
							<td>条形码</td>
							<td><input type="text" name="barcode"/></td>
						</tr>
						<tr>
							<td>重要(*)</td>
							<td>
								<select name="important">
									<option value="0" selected="selected">否</option>
									<option value="1">是</option>
								</select>
							</td>
						</tr>
						<tr>
							<td>特殊字段</td>
							<td>
								<input type="text" name="special_type"/>
							</td>
						</tr>
						<tr>
							<td>京东是否有库存</td>
							<td>
								<select name="instock">
									<option value="1" selected="selected">是</option>
									<option value="0" >否</option>
								</select>
							</td>
							<td colspan="2">
								<input type="hidden" name="action" value="add">
								<input type="hidden" name="url" value="JDSkuManageAction?<%=request.getQueryString()%>">
								<input type="submit" value="添加"/>
							</td>
						</tr>
					</table>
					</form>
			</td>
		</tr>
	</table>
	
	<hr/>
	<%--
	<c:if test="${querySkus!=null}">
		<table border="1">
			<tr>
				<td>skuId</td>
				<td>name</td>
				<td>类型</td>
				<td>京东价格</td>
				<td>价格更新时间</td>
				<td>出版社价格</td>
				<td>京东库存</td>
				<td>重要</td>
				<td>采购数量</td>
				<td>
			</tr>
			<c:forEach items="${querySkus}" var="sku">
				<tr id="tr_${sku.sku_id}">
					<td><a target="_blank" href="http://click.union.jd.com/JdClick/?unionId=11406&t=4&to=http://www.jd.com/product/${sku.sku_id}.html">${sku.sku_id}</a></td>
					<td>${sku.name}</td>
					<td>${jdSkuTypeMap[sku.typeId]}</td>
					<td>${sku.price}</td>
					<td>${sku.updateDate}</td>
					<td>${sku.publishingPrice}</td>
					<td>${sku.instock}</td>
					<td>${sku.important}</td>
					<td>${sku.buynumber}</td>
					<td><a target="_blank" href="<%=request.getContextPath()%>/JDSkuManageAction?action=find&skuId=${sku.sku_id}">修改</a>&nbsp; 
						<a href="javascript:removeSku('${sku.sku_id}')">删除</a>
					</td>
				</tr>
			</c:forEach>
		</table>
	</c:if>
	 --%>
	<c:if test="${resultMap!=null}">
		<table border="1">
			<tr>
				<td>京东Id<br></br><a href="<%=request.getContextPath()%>/JDSkuManageAction?action=query&sortName=sku_id&sort=1">(高-低)</a>&nbsp;<a href="<%=request.getContextPath()%>/JDSkuManageAction?action=query&sortName=sku_id&sort=0">(低-高)</a></td>
				<td>美萍Id<br></br><a href="<%=request.getContextPath()%>/JDSkuManageAction?action=query&sortName=cgId&sort=1">(高-低)</a>&nbsp;<a href="<%=request.getContextPath()%>/JDSkuManageAction?action=query&sortName=cgId&sort=0">(低-高)</a></td>
				<td>name</td>
				<td>条形码</td>
				<td>类型</td>
				<td>京东价格<br></br><a href="<%=request.getContextPath()%>/JDSkuManageAction?action=query&sortName=jdPrice&sort=1">(高-低)</a>&nbsp;<a href="<%=request.getContextPath()%>/JDSkuManageAction?action=query&sortName=jdPrice&sort=0">(低-高)</a></td>
				<td>价格更新时间</td>
				<td>出版社价格<br></br><a href="<%=request.getContextPath()%>/JDSkuManageAction?action=query&sortName=publishPrice&sort=1">(高-低)</a>&nbsp;<a href="<%=request.getContextPath()%>/JDSkuManageAction?action=query&sortName=publishPrice&sort=0">(低-高)</a></td>
				<td>京东库存</td>
				<td>重要</td>
				<td>采购数量<br></br><a href="<%=request.getContextPath()%>/JDSkuManageAction?action=query&sortName=buyNumber&sort=1">(高-低)</a>&nbsp;<a href="<%=request.getContextPath()%>/JDSkuManageAction?action=query&sortName=buyNumber&sort=0">(低-高)</a></td>
				<td>打折信息</td>
				<td>
			</tr>
			<c:forEach items="${resultMap.querySkus}" var="sku">
				<tr id="tr_${sku.sku_id}">
					<td>
						<jsp:include page="/common/itemUrlLink.jsp?id=${sku.sku_id}&type=${sku.typeId}"/>
					</td>
					<td>${sku.cgId}</td>
					<td>${sku.name}</td>
					<td>${sku.barcode}</td>
					<td>${jdSkuTypeMap[sku.typeId]}</td>
					<td>
						<c:choose>
							<c:when test="${sku.typeId=='10'}">
								<jsp:include page="/common/amazonItemPrice.jsp?skuId=${sku.sku_id}"/>
							</c:when>
							<c:otherwise>
								${sku.price}
							</c:otherwise>
						</c:choose>
					</td>
					<td>${sku.updateDate}</td>
					<td>${sku.publishingPrice}</td>
					<td>${sku.instock}</td>
					<td>
						<span id="span_important_${sku.sku_id}">${sku.important}</span>
						<a href="javascript:changeImportant('${sku.sku_id}','1')">重要</a>&nbsp;<a href="javascript:changeImportant('${sku.sku_id}','0')">非重要</a>
					</td>
					<td>
						<input id="quantity_${sku.sku_id}" name="quantity" style="width: 50px" value="${sku.buynumber}">
						<a href="javascript:changeQuantity('${sku.sku_id}','quantity_${sku.sku_id}')">修改</a>
						<span id="span_${sku.sku_id}"></span>
					</td>
					<td>${sku.special_type}</td>
					<td><a target="_blank" href="<%=request.getContextPath()%>/JDSkuManageAction?action=find&skuId=${sku.sku_id}">修改</a>&nbsp; 
						<a href="javascript:removeSku('${sku.sku_id}')">删除</a>
					</td>
				</tr>
			</c:forEach>
			<tr>
			<td colspan="9">共${resultMap.allRowsCount}条,${resultMap.currentPage}/${resultMap.allPageCount} 
				<c:if test="${resultMap.currentPage>1}"><a href="<%=request.getContextPath()%>/JDSkuManageAction?action=query&currentPage=${resultMap.currentPage-1}&sortName=${sortName}&sort=${sort}" >上一页</a></c:if>,
				<c:if test="${resultMap.currentPage<resultMap.allPageCount}"><a href="<%=request.getContextPath()%>/JDSkuManageAction?action=query&currentPage=${resultMap.currentPage+1}&sortName=${sortName}&sort=${sort}">下一页</a></c:if>
				
				&nbsp; <input type="text" id="currentPageForm" name="page" value="${resultMap.currentPage}" maxlength="4" style="width:20px;"/> <input type="button" value="跳转" onclick="redirect();"/>
			</td>
			</tr>
		</table>
		<form id="redirectForm" action="${ctx}/JDSkuManageAction" method="post">
			<input type="hidden" name="action" value="query"/>
			<input type="hidden" id="currentPage" name="currentPage" value="${resultMap.currentPage}"/>
			<input type="hidden" name="sortName" value="${sortName}"/>
			<input type="hidden" name="sort" value="${sort}"/>
		</form>
	</c:if>
</body>
</html>