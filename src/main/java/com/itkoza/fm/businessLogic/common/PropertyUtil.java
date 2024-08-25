package com.itkoza.fm.businessLogic.common;

import java.util.ResourceBundle;

public class PropertyUtil {
	private static PropertyUtil propertyUtil = new PropertyUtil();

	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private PropertyUtil() {}
	
	public static PropertyUtil getInstance() {
		return propertyUtil;
	}

	public String getStringValue(String propertyName, String sqlId){

		var applicationBundle = ResourceBundle.getBundle(propertyName);

		return applicationBundle.getString(sqlId);
	}
}
