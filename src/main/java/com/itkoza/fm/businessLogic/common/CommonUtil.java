package com.itkoza.fm.businessLogic.common;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import jakarta.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.databind.ObjectMapper;

public class CommonUtil {
	
	private static CommonUtil commonUtil = new CommonUtil();

	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private CommonUtil() {}
	
	public static CommonUtil getInstance() {
		return commonUtil;
	}

	ObjectMapper objectMapper;

	/**
     * 文字列配列をSetに変換する
     * @param data
     * @return
     */
	public Set<String> toSet(String []data){
    	return new HashSet<String>(Arrays.asList(data));
    }

    private ObjectMapper getObjectMapper() {
    	if (objectMapper == null) {
    		objectMapper = new ObjectMapper();
    	}
    	return objectMapper;
    }
    
    
	public boolean isExistSession(HttpServletRequest request){

		var httpSession = request.getSession();

		var userId = (String) httpSession.getAttribute(Const.USER_ID);

		if (userId == null) {
			return false;
		} else {
			return true;
		}
	}	
}
