package com.cg.action;

import java.io.File;
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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.cg.domain.JDSku;
import com.cg.exception.CGexception;
import com.cg.service.JDSkuService;

/**
 * Servlet implementation class ImportJDSkusFromExcel
 */
public class ImportJDSkusFromExcel extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ImportJDSkusFromExcel() {
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
		doGet(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream is = null;
		try {
			is = getUploadFileStream(request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		List<JDSku> jdskus = getJDSkus(is);
		List<JDSku> jdSkusInDB = JDSkuService.getInstance().queryALLItems();
		Map<String, Boolean> maps = new HashMap<String, Boolean>();
		for(JDSku jdsku : jdSkusInDB){
			maps.put(jdsku.getSku_id(), true);
		}
		for(JDSku newJdsku : jdskus){
			if(maps.containsKey(newJdsku.getSku_id())){
				throw new CGexception(newJdsku.getSku_id() + " 已经存在");
			}
		}
		for(JDSku newJdsku : jdskus){
			JDSkuService.getInstance().addJDSku(newJdsku);
		}
		request.getRequestDispatcher("/priceCal1.jsp").forward(request, response);
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

	private List<JDSku> getJDSkus(InputStream is) throws IOException {

		XSSFWorkbook xwb = new XSSFWorkbook(is);
		XSSFSheet sheet = xwb.getSheetAt(0);
		XSSFRow row;
		List<JDSku> jdskus = new ArrayList<JDSku>();
		for (int i = sheet.getFirstRowNum() + 1; i < sheet
				.getPhysicalNumberOfRows(); i++) {
			try{
				row = sheet.getRow(i);
				JDSku jdsku = new JDSku();
				String title = row.getCell(0).toString().trim();
				jdsku.setName(title);
				String jdid = row.getCell(1) == null?null:row.getCell(1).toString().trim();
				jdsku.setSku_id(jdid);
				String stockStr = row.getCell(2).toString().trim();
				float stockFlo = Float.parseFloat(stockStr);
				int stock = (int) stockFlo;
				jdsku.setBuynumber(stock);
				float price = Float.parseFloat(row.getCell(3).toString().trim());
				jdsku.setPrice(price);
				String sale = row.getCell(4) == null ? null : row.getCell(4)
						.toString();
				jdsku.setImportant(!isBlank(sale));
				jdskus.add(jdsku);
			} catch (Exception e) {
				throw new CGexception("第" + i + "行附近内容有错误 请检查",e);
			}
		}
		return jdskus;
	}

	private static boolean isBlank(String str) {
		if (str == null || str.trim().length() < 1) {
			return true;
		}
		return false;
	}
}
