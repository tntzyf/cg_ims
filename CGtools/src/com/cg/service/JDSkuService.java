package com.cg.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.poi.util.StringUtil;

import com.cg.ServiceIF.RemoteInfoServiceIF;
import com.cg.domain.JDSku;
import com.cg.domain.JDSkuType;
import com.cg.domain.UpdatedSku;
import com.cg.exception.CGexception;
import com.cg.util.DBUtil;
import com.cg.service.remoteservice.JDRemoteInfoService;
import com.cg.service.remoteservice.RemoteInfoServiceHelper;
import com.cg.source.ResourceUtils;
import com.cg.util.PriceTool;

public class JDSkuService {
	private JDSkuService() {
	};

	private static final int ITEMS_IN_PAGE = 20;
	private static final String ALL_PAGE_COUNT = "allPageCount";
	private static final String ALL_ROWS_COUNT = "allRowsCount";
	private static final String CURRENT_PAGE = "currentPage";
	private static final String QUERY_SKUS = "querySkus";

	private RemoteInfoServiceIF jdRemoteInfoServiceIF = new JDRemoteInfoService();

	private static JDSkuService jDSkuService = new JDSkuService();

	private List<JDSkuType> JDSkuTypes;

	public static JDSkuService getInstance() {
		return jDSkuService;
	}

	public boolean addJDSku(JDSku jdSku) {
		JDSku sku = findJDSku(jdSku.getSku_id());
		if (sku != null) {
			throw new CGexception("此jd skuId已经存在于系统中");
		}
		String sql = "insert into sku (sku_id,name,jd_price,important,jd_instock,buy_number,publishing_price,type,special_type,barcode,cg_id) values(?,?,?,?,?,?,?,?,?,?,?)";
		PreparedStatement ps = null;
		Connection con = null;
		int result = 0;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, jdSku.getSku_id());
			ps.setString(2, jdSku.getName());
			ps.setString(3, PriceTool.roundToString(jdSku.getPrice()));
			ps.setBoolean(4, jdSku.getImportant());
			ps.setBoolean(5, jdSku.getInstock());
			ps.setInt(6, jdSku.getBuynumber());
			ps
					.setString(7, PriceTool.roundToString(jdSku
							.getPublishingPrice()));
			ps.setString(8, jdSku.getTypeId());
			ps.setString(9, jdSku.getSpecial_type());
			ps.setString(10, jdSku.getBarcode());
			ps.setString(11, jdSku.getCgId());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw new CGexception(e);
		} catch (ClassNotFoundException e) {
			throw new CGexception(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return result == 1;
	}

	public boolean removeJDSku(String skuId) {
		String sql = "delete from sku where sku_id=?";
		PreparedStatement ps = null;
		Connection con = null;
		int result = 0;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, skuId);
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw new CGexception(e);
		} catch (ClassNotFoundException e) {
			throw new CGexception(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					throw new CGexception(e);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					throw new CGexception(e);
				}
			}
		}
		return result == 1;
	}

	public boolean updateJDSkus(UpdatedSku sku) {
		String sql = "update sku set name=?,jd_price=?,important=?,jd_instock=?,buy_number=?,publishing_price=?,type=?,special_type=?,sku_id=?,barcode=?,cg_id=? where sku_id=?";
		PreparedStatement ps = null;
		Connection con = null;
		int result = 0;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, sku.getName());
			ps.setString(2, PriceTool.roundToString(sku.getPrice()));
			ps.setBoolean(3, sku.getImportant());
			ps.setBoolean(4, sku.getInstock());
			ps.setInt(5, sku.getBuynumber());
			ps.setString(6, PriceTool.roundToString(sku.getPublishingPrice()));
			ps.setString(7, sku.getTypeId());
			ps.setString(8, sku.getSpecial_type());
			ps.setString(9, sku.getNew_sku_id());
			ps.setString(10, sku.getBarcode());
			ps.setString(11, sku.getCgId());
			ps.setString(12, sku.getSku_id());
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw new CGexception(e);
		} catch (ClassNotFoundException e) {
			throw new CGexception(e);
		} finally {
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result == 1;
	}

	public Map<String, Object> queryALL(String skuId, String cgId, String name,
			Boolean important, Boolean jd_instock, String type,
			String special_type, String barcode, int page, String sortName,
			String sort) {
		List<JDSku> allSkus = queryJDSkus(skuId, cgId, name, important,
				jd_instock, type, special_type, barcode, sortName, sort);
		int allRows = allSkus.size();
		int allPages = allRows % ITEMS_IN_PAGE == 0 ? allRows / ITEMS_IN_PAGE
				: allRows / ITEMS_IN_PAGE + 1;
		if (page > allPages) {
			return null;
		}
		List<JDSku> querySkus = allSkus.subList((page - 1) * ITEMS_IN_PAGE,
				(page) * ITEMS_IN_PAGE > allRows ? allRows : (page)
						* ITEMS_IN_PAGE);
		Map<String, Object> result = new HashMap<String, Object>();
		result.put(ALL_ROWS_COUNT, allRows);
		result.put(ALL_PAGE_COUNT, allPages);
		result.put(QUERY_SKUS, querySkus);
		result.put(CURRENT_PAGE, page);
		return result;
	}

	public Map<String, Object> queryALL(int page, String sortName, String sort) {
		return queryALL(null, null, null, null, null, null, null, null, page,
				sortName, sort);
	}

	public List<JDSku> queryALLItems() {
		List<JDSku> allSkus = queryJDSkus(null, null, null, null, null, null,
				null, null);
		return allSkus;
	}

	public List<JDSku> queryJDSkuBySQL(String sql, List<Object> parameters) {
		List<Map<String, Object>> result = DBUtil.executeQuery(sql, parameters);
		List<JDSku> list = new ArrayList<JDSku>();
		for (Map<String, Object> map : result) {
			list.add(convertObject(map));
		}
		return list;
	}

	private JDSku convertObject(Map<String, Object> result) {
		JDSku sku = new JDSku();
		sku.setName((String) result.get("name"));
		Double priceStr = (Double) result.get("jd_price");
		if (priceStr != null) {
			sku.setPrice(priceStr.floatValue());
		}
		sku.setSku_id((String) result.get("sku_id"));
		sku.setImportant((Boolean) result.get("important"));
		sku.setBuynumber((Integer) result.get("buy_number"));
		sku.setInstock((Boolean) result.get("jd_instock"));
		sku.setUpdateDate((String) result.get("update_date"));
		sku.setTypeId(result.get("type").toString());
		return sku;
	}

	private JDSku convertObject(ResultSet rs) throws SQLException {
		JDSku sku = new JDSku();
		sku.setSku_id(rs.getString("sku_id"));
		sku.setCgId(rs.getString("cg_id"));
		sku.setName(rs.getString("name"));
		String priceStr = rs.getString("jd_price");
		if (!StringUtils.isBlank(priceStr)) {
			sku.setPrice(Float.parseFloat(priceStr));
		}
		sku.setBuynumber(rs.getInt("buy_number"));
		sku.setImportant(rs.getBoolean("important"));
		sku.setInstock(rs.getBoolean("jd_instock"));
		sku.setBarcode(rs.getString("barcode"));
		String publishingPriceStr = rs.getString("publishing_price");
		if (!StringUtils.isBlank(publishingPriceStr)) {
			sku.setPublishingPrice(Float.parseFloat(publishingPriceStr));
		}
		sku.setUpdateDate(rs.getString("update_date"));
		sku.setTypeId(String.valueOf(rs.getInt("type")));
		sku.setSpecial_type(rs.getString("special_type"));
		return sku;
	}

	public List<JDSku> queryJDSkus(String skuId, String cgId, String name,
			Boolean important, String special_type, String barcode,
			Boolean jd_instock, String type) {
		return queryJDSkus(skuId, cgId, name, important, jd_instock, type,
				special_type, barcode, null, null);
	}

	private String sortSql(String sortName, String sort) {
		if (StringUtils.isBlank(sortName) || StringUtils.isBlank(sort)) {
			return " order by buy_number desc";
		}
		String sql = " order by ";
		if ("jdPrice".equals(sortName)) {
			sql += "jd_price ";
		} else if ("publishPrice".equals(sortName)) {
			sql += "publishing_price ";
		} else if ("buyNumber".equals(sortName)) {
			sql += "buy_number ";
		} else if ("sku_id".equals(sortName)) {
			sql += "sku_id ";
		} else if ("cgId".equals(sortName)) {
			sql += "cg_id ";
		} else {
			sql += "buy_number ";
		}
		if ("1".equals(sort)) {
			sql += " desc";
		} else if ("0".equals(sort)) {
			sql += " asc";
		} else {
			sql += " desc";
		}
		return sql;
	}

	public List<JDSku> queryJDSkus(String skuId, String cgId, String name,
			Boolean important, Boolean jd_instock, String type,
			String special_type, String barcode, String sortName, String sort) {
		String sql = "select * from sku where ";
		if (!StringUtils.isBlank(skuId)) {
			sql += "sku_id like ? and ";
		}
		if (!StringUtils.isBlank(cgId)) {
			sql += "cg_id like ? and ";
		}
		if (!StringUtils.isBlank(name)) {
			sql += "name like ? and ";
		}
		if (!StringUtils.isBlank(barcode)) {
			sql += "barcode like ? and ";
		}
		if (important != null) {
			sql += "important=? and ";
		}
		if (!StringUtils.isBlank(type)) {
			sql += "type = ? and ";
		}
		if (!StringUtils.isBlank(special_type)) {
			sql += "special_type = ? and ";

		}
		if (jd_instock != null) {
			sql += "jd_instock=? ";
		} else {
			sql += "1=1 ";
		}
		sql += sortSql(sortName, sort);
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		List<JDSku> jdSkus = new ArrayList<JDSku>();
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			if (!StringUtils.isBlank(skuId)) {
				ps.setString(getIndex(skuId), "%" + skuId + "%");
			}
			if (!StringUtils.isBlank(cgId)) {
				ps.setString(getIndex(skuId, cgId), "%" + cgId + "%");
			}
			if (!StringUtils.isBlank(name)) {
				ps.setString(getIndex(skuId, cgId, name), "%" + name + "%");
			}
			if (!StringUtils.isBlank(barcode)) {
				ps.setString(getIndex(skuId, cgId, name, barcode), "%"
						+ barcode + "%");
			}
			if (important != null) {
				ps.setBoolean(getIndex(skuId, cgId, name, barcode, important),
						important);
			}
			if (!StringUtils.isBlank(type)) {
				ps.setString(getIndex(skuId, cgId, name, barcode, important,
						type), type);
			}
			if (!StringUtils.isBlank(special_type)) {
				ps.setString(getIndex(skuId, cgId, name, barcode, important,
						type, special_type), special_type);
			}
			if (jd_instock != null) {
				ps.setBoolean(getIndex(skuId, cgId, name, barcode, important,
						type, special_type, jd_instock), jd_instock);
			}
			rs = ps.executeQuery();
			while (rs.next()) {
				jdSkus.add(convertObject(rs));
			}
		} catch (SQLException e) {
			throw new CGexception(e);
		} catch (ClassNotFoundException e) {
			throw new CGexception(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return jdSkus;
	}

	// return '1','2','3'
	private String getInSql(String[] itemTypes) {
		String sql = "";
		if (itemTypes != null && itemTypes.length > 0) {
			int i = 0;
			for (String itemType : itemTypes) {
				if (i > 0) {
					sql += ",";
				}
				sql += ("'" + itemType + "'");
				i++;
			}
		}
		return sql;
	}

	public List<JDSku> queryAllJDWithPriceSkus(String[] itemTypes,
			String special_type, String[] exclude_itemTypes,
			String exclude_special_type) {
		StringBuilder sql = new StringBuilder();
		if ((exclude_itemTypes == null || exclude_itemTypes.length == 0)
				&& StringUtils.isBlank(exclude_special_type)) {
			sql.append("select * from sku where jd_price is not null");
			if (itemTypes != null && itemTypes.length > 0) {
				sql.append(" and type in (" + getInSql(itemTypes) + ")");
			}
			if (!StringUtils.isBlank(special_type)) {
				sql.append(" and special_type='" + special_type + "'");
			}
			sql.append(" order by buy_number desc");
		} else {
			sql.append("select * from sku a where jd_price is not null");
			if (itemTypes != null && itemTypes.length > 0) {
				sql.append(" and type in (" + getInSql(itemTypes) + ")");
			}
			if (!StringUtils.isBlank(special_type)) {
				sql.append(" and special_type='" + special_type + "'");
			}
			sql
					.append(" and sku_id not in ( select sku_id from sku b where b.jd_price is not null");
			if (exclude_itemTypes != null && exclude_itemTypes.length > 0) {
				sql
						.append(" and type in (" + getInSql(exclude_itemTypes)
								+ ")");
			}
			if (!StringUtils.isBlank(exclude_special_type)) {
				sql.append(" and special_type='" + exclude_special_type + "'");
			}
			sql.append(") order by buy_number desc");
		}

		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		List<JDSku> jdSkus = new ArrayList<JDSku>();
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql.toString());
			rs = ps.executeQuery();
			while (rs.next()) {
				jdSkus.add(convertObject(rs));
			}
		} catch (SQLException e) {
			throw new CGexception(e);
		} catch (ClassNotFoundException e) {
			throw new CGexception(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return jdSkus;
	}

	private static int getIndex(Object... parameter) {
		int i = 0;
		for (Object obj : parameter) {
			if (obj instanceof String) {
				if (!StringUtils.isBlank((String) obj)) {
					i++;
				}
			} else if (obj != null) {
				i++;
			}
		}
		return i;
	}

	public JDSku findJDSku(String skuId) {
		String sql = "select * from sku where sku_id=?";
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		JDSku sku = null;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, skuId);
			rs = ps.executeQuery();
			while (rs.next()) {
				sku = convertObject(rs);
			}
		} catch (SQLException e) {
			throw new CGexception(e);
		} catch (ClassNotFoundException e) {
			throw new CGexception(e);
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (ps != null) {
				try {
					ps.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return sku;
	}

	public boolean updatePriceAndStock(String skuId, String price,
			Boolean stock, String updateDateStr) {
		String sql = "";
		if (stock == null) {
			sql = "update sku set jd_price=?,update_date=? where sku_id=?";
		} else {
			sql = "update sku set jd_price=?,update_date=?,jd_instock=? where sku_id=?";
		}
		List<Object> parameter = new ArrayList<Object>();
		parameter.add(price);
		parameter.add(updateDateStr);
		if (stock != null) {
			parameter.add(stock);
		}
		parameter.add(skuId);
		return DBUtil.executeUpdate(sql, parameter);
	}

	public List<String> updateSkuPrice(String idsStr) {
		if (StringUtils.isBlank(idsStr)) {
			return null;
		}
		String[] ids = idsStr.split(",");
		StringBuilder sb = new StringBuilder(
				"select * from sku where sku_id in (");
		int i = 0;
		for (String id : ids) {
			if (i != 0) {
				sb.append(",");
			}
			sb.append("'").append(id).append("'");
			i++;
		}
		sb.append(")");
		List<JDSku> jdskus = queryJDSkuBySQL(sb.toString(), Collections
				.emptyList());
		return updateSkuPrice(jdskus);
	}

	public List<String> updateSkuPrice(boolean searchAllItems,
			String[] itemTypes) {
		List<JDSku> jdskus = null;

		String sql = "select * from sku where ";
		if (searchAllItems) {
			sql += " 1=1 ";
		} else {
			sql += " buy_number>0 ";
		}
		if (itemTypes != null && itemTypes.length > 0) {
			sql += (" and type in (" + getInSql(itemTypes) + ")");
		}
		jdskus = queryJDSkuBySQL(sql, Collections.emptyList());
		return updateSkuPrice(jdskus);
	}

	public List<String> updateSkuPromotion(boolean searchAllItems,
			String[] itemTypes) {
		List<JDSku> jdskus = null;

		String sql = "select * from sku where ";
		if (searchAllItems) {
			sql += " 1=1 ";
		} else {
			sql += " buy_number>0 ";
		}
		if (itemTypes != null && itemTypes.length > 0) {
			sql += (" and type in (" + getInSql(itemTypes) + ")");
		}
		jdskus = queryJDSkuBySQL(sql, Collections.emptyList());
		return updateSkuPromotions(jdskus);
	}

	public List<String> updateSkuPromotions(List<JDSku> jdskus) {
		long startTime = System.currentTimeMillis();
		String sql = "update sku set special_type =? where sku_id=?";
		List<String> errorMessage = new ArrayList<String>();
		for (JDSku jdSku : jdskus) {
			try {
				RemoteInfoServiceIF remoteInfoService = RemoteInfoServiceHelper
						.getRemoteInfoServiceIF(jdSku);
				String promotions = remoteInfoService.getPromotionInfo(jdSku
						.getSku_id());
				System.out.println(promotions);
				DBUtil.executeUpdate(sql, new Object[] { promotions,
						jdSku.getSku_id() });
			} catch (Exception e) {
				errorMessage
						.add("sku:" + jdSku.getSku_id() + " has some error");
				System.out.println(e.getMessage());
			}
		}
		long endTime = System.currentTimeMillis();
		String costTime = Long.toString((endTime - startTime) / 1000);
		// 记录更新时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String updateDate = "update config set config_value=? where config_key=?";
		List<Object> parameter = new ArrayList<Object>();
		parameter.add(sdf.format(new Date()) + " 共耗时" + costTime + "秒");
		parameter.add("jd_sku_promotion_update");
		DBUtil.executeUpdate(updateDate, parameter);
		addErrorMessage(errorMessage, 2);
		return null;
	}

	public List<String> updateSkuPrice(List<JDSku> jdskus) {
		long startTime = System.currentTimeMillis();
		List<String> errorMessage = new ArrayList<String>();
		String updateDateStr = getDateStr();
		for (JDSku jdSku : jdskus) {
			if ("10".equals(jdSku.getTypeId())) {
				AmazonItemPriceHandler.getInstance().handleAmazonPrice(jdSku);
				updatePriceAndStock(jdSku.getSku_id(), "0", null, updateDateStr);
				continue;
			} else {
				CloseableHttpClient httpclient = HttpClients.createDefault();
				try {
					String price = RemoteInfoServiceHelper.getInstance()
							.getPrice(httpclient, jdSku);
					if (StringUtils.isBlank(price)) {
						errorMessage.add("Price is null| Sku:"
								+ generateLink(jdSku.getSku_id())
								+ " error in get Price & Stock");
						continue;
					}
					Boolean stock = RemoteInfoServiceHelper.getInstance()
							.hasStock(httpclient, jdSku);
					if (stock == null) {
						errorMessage.add("Sku:"
								+ generateLink(jdSku.getSku_id())
								+ " error in get Stock");
					}
					updatePriceAndStock(jdSku.getSku_id(), price, stock,
							updateDateStr);
					System.out.println("sku:" + jdSku.getSku_id() + " price:"
							+ price + " stock:" + stock);
				} catch (CGexception e) {
					errorMessage.add("Sku:" + generateLink(jdSku.getSku_id())
							+ " error in get Price & Stock");
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (Exception e) {
					errorMessage.add("Exception Sku:"
							+ generateLink(jdSku.getSku_id())
							+ " error in get Price & Stock");
					e.printStackTrace();
				} finally {
					try {
						httpclient.getConnectionManager().shutdown();
						httpclient.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		long endTime = System.currentTimeMillis();
		String costTime = Long.toString((endTime - startTime) / 1000);
		// 记录更新时间
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		String updateDate = "update config set config_value=? where config_key=?";
		List<Object> parameter = new ArrayList<Object>();
		parameter.add(sdf.format(new Date()) + " 共耗时" + costTime + "秒");
		parameter.add("jd_sku_price_update");
		DBUtil.executeUpdate(updateDate, parameter);
		addErrorMessage(errorMessage, 1);
		return null;
	}

	private static String getDateStr() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return sdf.format(new Date());
	}

	public void addErrorMessage(List<String> errors, int type) {
		String sql = "delete from error_message";
		DBUtil.executeUpdate(sql, Collections.emptyList());
		String insertSql = "insert into error_message(error,type) values(?,?)";
		for (String error : errors) {
			List<Object> parameter = new ArrayList<Object>();
			parameter.add(error);
			parameter.add(type);
			DBUtil.executeUpdate(insertSql, parameter);
		}
	}

	public void removeStock(List<JDSku> skus) {
		String sql = "update sku set buy_number=buy_number-? where sku_id=?";
		for (JDSku sku : skus) {
			List<Object> parameter = new ArrayList<Object>();
			parameter.add(sku.getBuynumber());
			parameter.add(sku.getSku_id());
			DBUtil.executeUpdate(sql, parameter);
		}
	}

	public void refreshImportantProperty() {
		String sql = "update sku set important=0 where buy_number=0";
		DBUtil.executeUpdate(sql, Collections.emptyList());
	}

	public List<JDSkuType> getJDSkuTypes() {
		if (JDSkuTypes != null && !JDSkuTypes.isEmpty()) {
			return JDSkuTypes;
		}
		String typeString = ResourceUtils.readResource("jdSkuType");
		String[] types = StringUtils.split(typeString, ",");
		List<JDSkuType> jdSkuTypes = new ArrayList<JDSkuType>(2);
		for (String type : types) {
			String[] jdSkuTypeStr = StringUtils.split(type, "=");
			JDSkuType jst = new JDSkuType();
			jst.setId(jdSkuTypeStr[0]);
			jst.setName(jdSkuTypeStr[1]);
			jdSkuTypes.add(jst);
		}
		this.JDSkuTypes = jdSkuTypes;
		return jdSkuTypes;
	}

	public JDSkuType findJDSkuTypes(String id) {
		List<JDSkuType> jdSkuTypes = getJDSkuTypes();
		for (JDSkuType jst : jdSkuTypes) {
			if (jst.getId().equals(id)) {
				return jst;
			}
		}
		return null;
	}

	private static String generateLink(String skuId) {
		return "<a target=\"_blank\" href=\"http://click.union.jd.com/JdClick/?unionId=11406&t=4&to=http://www.jd.com/product/"
				+ skuId + ".html\">" + skuId + "</a>";
	}
}
