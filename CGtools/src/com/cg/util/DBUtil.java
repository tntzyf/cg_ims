package com.cg.util;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.cg.exception.CGexception;

public class DBUtil {
	public static Connection getConnection() throws SQLException, ClassNotFoundException{
		Class.forName("sun.jdbc.odbc.JdbcOdbcDriver");
		String url = "jdbc:odbc:driver={Microsoft Access Driver (*.mdb)};DBQ=d://cgtools.mdb";
		Connection conn = DriverManager.getConnection(url, "", "");
		return conn;
	}
	
	public static boolean executeUpdate(String sql,Object[] parameters){
		List<Object> list = new ArrayList<Object>();
		for(Object obj : parameters){
			list.add(obj);
		}
		return executeUpdate(sql, list);
	}
	
	public static boolean executeUpdate(String sql,List<Object> parameters){
		PreparedStatement ps = null;
		Connection con = null;
		int result = 0;
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			int index = 1;
			for(Object obj : parameters){
				if(obj==null){
					ps.setNull(index, Types.VARCHAR);
				}else{
					ps.setObject(index, obj);
				}
				index++;
			}
			result = ps.executeUpdate();
		} catch (SQLException e) {
			throw new CGexception(e);
		} catch (ClassNotFoundException e) {
			throw new CGexception(e);
		} finally{
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return result == 1;
	}
	
	public static List<Map<String,Object>> executeQuery(String sql,Object[] parameters){
		List<Object> list = new ArrayList<Object>();
		for(Object obj : parameters){
			list.add(obj);
		}
		return executeQuery(sql, list);
	}
	
	public static List<Map<String,Object>> executeQuery(String sql,List<Object> parameters){
		PreparedStatement ps = null;
		Connection con = null;
		ResultSet rs = null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try {
			con = DBUtil.getConnection();
			ps = con.prepareStatement(sql);
			int index = 1;
			if(parameters!=null && !parameters.isEmpty()){
				for(Object obj : parameters){
					ps.setObject(index, obj);
				}
			}
			
			rs = ps.executeQuery();
			while(rs.next()){
				ResultSetMetaData rsmd = rs.getMetaData();
				int count = rsmd.getColumnCount();
				Map<String,Object> map = new HashMap<String, Object>();
				for(int i=1;i<count+1;i++){
					String columnName = rsmd.getColumnName(i);
					Object valueObj = rs.getObject(i);
					map.put(columnName.toLowerCase(), valueObj);
				}
				list.add(map);
			}
		} catch (SQLException e) {
			throw new CGexception(e);
		} catch (ClassNotFoundException e) {
			throw new CGexception(e);
		} finally{
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(ps!=null){
				try {
					ps.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if(con!=null){
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return list;
	}
	
	public static String transferChar(String str){
		if(str==null){
			return null;
		}
		try {
			return new String(str.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
}
