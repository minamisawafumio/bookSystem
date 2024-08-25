package com.itkoza.fm.businessLogic.common;

import java.util.HashMap;
import java.util.Map;

public class SystemInfoUtil {
	
	private static SystemInfoUtil systemInfoUtil = new SystemInfoUtil();
	
	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private SystemInfoUtil() {}
	
	public static SystemInfoUtil getInstance() {
		return systemInfoUtil;
	}
			
	private Map<String ,Object> commonMap = new HashMap<String ,Object>();
	
	public Object getValue(String key) {
		return commonMap.get(key);
	}

	public Object putValue(String key, Object obj) {
		return commonMap.put(key, obj);
	}
}
