package com.cg.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.cg.util.DBUtil;

public class ErrorMessageService {
	private ErrorMessageService(){};
	
	private static ErrorMessageService errorMessageService = new ErrorMessageService();
	
	public static ErrorMessageService getInstance(){
		return errorMessageService;
	}
	
	public boolean removeErrorMessage(String messageId){
		String sql = "delete from error_message where id=?";
		return DBUtil.executeUpdate(sql, new String[]{messageId});
	}
}
