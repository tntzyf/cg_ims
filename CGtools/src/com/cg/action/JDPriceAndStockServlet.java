package com.cg.action;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
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
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cg.domain.Sku;
import com.cg.exception.CGexception;
import com.cg.service.JDPriceAndStockService;

/**
 * Servlet implementation class JDPriceAndStockServlet
 */
public class JDPriceAndStockServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public JDPriceAndStockServlet() {
        super();
        // TODO Auto-generated constructor stub
    }
    
    File tmpDir = null;// 初始化上传文件的临时存放目录
	File saveDir = null;// 初始化上传文件后的保存目录

	@Override
	public void init() throws ServletException {
		/*
		 * 对上传文件夹和临时文件夹进行初始化
		 */
		super.init();
		String tmpPath = "d://tmpdir";
		String savePath = "d://updir";
		tmpDir = new File(tmpPath);
		saveDir = new File(savePath);
		if (!tmpDir.isDirectory())
			tmpDir.mkdir();
		if (!saveDir.isDirectory())
			saveDir.mkdir();
	}
	
	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		InputStream is = null;
//		try {
//			is = getUploadFileStream(request);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		List<String> jdids = getItemFromExcel(is);
		String jdidsStr = request.getParameter("jdids");
		List<String> jdids = new ArrayList<String>();
		for(String jdid : jdidsStr.split(",")){
			jdids.add(jdid);
		}
		JDPriceAndStockService jdPriceAndStockService = JDPriceAndStockService.getInstance();
//		Map<String, Object> map = jdPriceAndStockService.getJDinfos(jdids);
//		request.setAttribute("jdresult", map);
		request.getRequestDispatcher("/jdinfo.jsp").forward(request, response);
	}

	private InputStream getUploadFileStream(HttpServletRequest request) throws Exception{
		if (ServletFileUpload.isMultipartContent(request)) {
			DiskFileItemFactory dff = new DiskFileItemFactory();// 创建该对象
			dff.setRepository(tmpDir);// 指定上传文件的临时目录
			dff.setSizeThreshold(1024000);// 指定在内存中缓存数据大小,单位为byte
			ServletFileUpload sfu = new ServletFileUpload(dff);// 创建该对象
			sfu.setFileSizeMax(5000000);// 指定单个上传文件的最大尺寸
			sfu.setSizeMax(10000000);// 指定一次上传多个文件的总尺寸
			FileItemIterator fii = sfu.getItemIterator(request);// 解析request
																// 请求,并返回FileItemIterator集合
			while (fii.hasNext()) {
				FileItemStream fis = fii.next();// 从集合中获得一个文件流
				if (!fis.isFormField() && fis.getName().length() > 0) {// 过滤掉表单中非文件域
					return fis.openStream();
				}
			}
		}
		return null;
	}
	
	private List<String> getItemFromExcel(InputStream is) throws IOException {

		XSSFWorkbook xwb = new XSSFWorkbook(is);
		XSSFSheet sheet = xwb.getSheetAt(0);
		XSSFRow row;
		List<String> jdids = new ArrayList<String>();
		for (int i = sheet.getFirstRowNum() + 1; i < sheet
				.getPhysicalNumberOfRows(); i++) {
			try{
				row = sheet.getRow(i);
				if(row == null){
					break;
				}
				XSSFCell cell = row.getCell(1);
				if(cell != null){
					String cellStr = cell.toString();
					if(!StringUtils.isBlank(cellStr)){
						String jdid = cellStr.trim();
						jdids.add(jdid);
					}
				}
			}
			catch (Exception e) {
				throw new CGexception("第" + i + "行附近内容有错误 请检查",e);
			}
		}
		return jdids;
	}
}
