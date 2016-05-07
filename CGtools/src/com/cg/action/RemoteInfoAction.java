package com.cg.action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cg.ServiceIF.RemoteInfoServiceIF;
import com.cg.domain.RemoteGeneratorInfoEntity;
import com.cg.service.JDPriceAndStockService;
import com.cg.service.remoteservice.AmazonRemoteInfoService;
import com.cg.service.remoteservice.AmazonUKRemoteInfoService;
import com.cg.service.remoteservice.JDRemoteInfoService;

/**
 * Servlet implementation class RemoteInfoAction
 */
public class RemoteInfoAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RemoteInfoAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String type=request.getParameter("type");
		if("jd".equalsIgnoreCase(type)){
			jdRemoveInfoGenerator(request,response);
		}else if("US".equalsIgnoreCase(type)){
			amazonRemoveInfoGenerator(request,response);
		}else if("UK".equalsIgnoreCase(type)){
			amazonUKRemoveInfoGenerator(request,response);
		}
	}

	protected void jdRemoveInfoGenerator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idsStr = request.getParameter("ids");
		List<String> ids = new ArrayList<String>();
		for(String jdid : idsStr.split(",")){
			ids.add(jdid);
		}
		RemoteInfoServiceIF remoteInfoServiceIF = new JDRemoteInfoService();
		RemoteGeneratorInfoEntity remoteGeneratorInfoEntity = remoteInfoServiceIF.generateRemoteInfo(ids);
		request.setAttribute("remoteGeneratorInfoEntity", remoteGeneratorInfoEntity);
		request.getRequestDispatcher("/remoteSkuList.jsp").forward(request, response);
	}
	
	protected void amazonRemoveInfoGenerator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idsStr = request.getParameter("ids");
		String huilvStr = request.getParameter("huilv");
		List<String> ids = new ArrayList<String>();
		for(String jdid : idsStr.split(",")){
			ids.add(jdid);
		}
		RemoteInfoServiceIF remoteInfoServiceIF = new AmazonRemoteInfoService();
		RemoteGeneratorInfoEntity remoteGeneratorInfoEntity = remoteInfoServiceIF.generateRemoteInfo(ids);
		request.setAttribute("remoteGeneratorInfoEntity", remoteGeneratorInfoEntity);
		request.setAttribute("huilv", Double.parseDouble(huilvStr));
		request.getRequestDispatcher("/amazonRemoteSkuList.jsp").forward(request, response);
	}
	
	protected void amazonUKRemoveInfoGenerator(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String idsStr = request.getParameter("ids");
		String huilvStr = request.getParameter("huilv");
		List<String> ids = new ArrayList<String>();
		for(String jdid : idsStr.split(",")){
			ids.add(jdid);
		}
		RemoteInfoServiceIF remoteInfoServiceIF = new AmazonUKRemoteInfoService();
		RemoteGeneratorInfoEntity remoteGeneratorInfoEntity = remoteInfoServiceIF.generateRemoteInfo(ids);
		request.setAttribute("remoteGeneratorInfoEntity", remoteGeneratorInfoEntity);
		request.setAttribute("huilv", Double.parseDouble(huilvStr));
		request.getRequestDispatcher("/amazonRemoteSkuList.jsp").forward(request, response);
	}
}
