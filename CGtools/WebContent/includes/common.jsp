
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="com.cg.domain.JDSkuType"%>
<%@page import="java.util.List"%>
<%@page import="com.cg.service.JDSkuService"%><%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
List<JDSkuType> jdSkuTypes = (List<JDSkuType>)application.getAttribute("jdSkuTypes");
if(jdSkuTypes==null || jdSkuTypes.isEmpty()){
	jdSkuTypes = JDSkuService.getInstance().getJDSkuTypes();
	application.setAttribute("jdSkuTypes",jdSkuTypes);
	Map<String,String> jdSkuTypeMap = new HashMap<String,String>();
	for(JDSkuType jdSkuType : jdSkuTypes){
		jdSkuTypeMap.put(jdSkuType.getId(),jdSkuType.getName());
	}
	application.setAttribute("jdSkuTypeMap",jdSkuTypeMap);
}

%>