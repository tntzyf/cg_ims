package com.cg.action;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.util.Streams;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cg.domain.JDSku;
import com.cg.exception.CGexception;
import com.cg.service.JDSkuService;
import com.cg.service.PriceCaculatorService;

/**
 * Servlet implementation class PriceAnalysisAction
 */
public class PriceAnalysisAction extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * Default constructor.
	 */
	public PriceAnalysisAction() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String action = request.getParameter("action");
		if("generateRestult".equalsIgnoreCase(action)){
			generateRestult(request, response);
		}else if("importData".equalsIgnoreCase(action)){
			importData(request, response);
		}else if("generateRestult1".equalsIgnoreCase(action)){
			generateRestult1(request, response);
		}
	}
	
	protected void generateRestult(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String type = (String)request.getSession().getAttribute("type");
		PriceCaculatorService pcs = new PriceCaculatorService((Float) (request
				.getSession().getAttribute("standPrice")), (Float) (request
				.getSession().getAttribute("maxPrice")), 0, type);
		Map<String, Object> result = null;
		String[] itemTypes = {"1","2","3"};
		String[] exclude_itemTypes = {};
		try{
			itemTypes = request.getParameterValues("types");
			String special_type = request.getParameter("special_type");
			exclude_itemTypes = request.getParameterValues("exclude_types");
			String exclude_special_type = request.getParameter("exclude_special_type");
			result = pcs.getResultFromDB(itemTypes,  special_type, exclude_itemTypes, exclude_special_type);
		}catch (CGexception e) {
			request.setAttribute("error", e.getMessage());
		}
		request.setAttribute("itemType", itemTypes);
		request.setAttribute("result", result);
		request.getRequestDispatcher("/priceCal1.jsp").forward(request, response);
	}
	
	protected void generateRestult1(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String type = (String)request.getSession().getAttribute("type");
		PriceCaculatorService pcs = new PriceCaculatorService((Float) (request
				.getSession().getAttribute("standPrice")), (Float) (request
				.getSession().getAttribute("maxPrice")), 0, type);
		Map<String, Object> result = null;
		String idsStr = request.getParameter("ids");
		idsStr = StringUtils.deleteWhitespace(idsStr);
		try{
			result = pcs.getResultFromDB1(idsStr);
		}catch (CGexception e) {
			request.setAttribute("error", e.getMessage());
		}
		request.setAttribute("ids", idsStr);
		request.setAttribute("result", result);
		request.getRequestDispatcher("/priceCal1.jsp").forward(request, response);
	}
	
	protected void importData(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
	}

	
	
	
}
