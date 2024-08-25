package com.itkoza.fm.businessLogic.common;

import java.util.ArrayList;
import java.util.List;

public class SystemUtil {

	private static SystemUtil systemUtil = new SystemUtil();
	
	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private SystemUtil() {}
	
	public static SystemUtil getInstance() {
		return systemUtil;
	}
			
	public List<Object> getObjArray(Object obj1, Object obj2){
		var objectList = new ArrayList<>();
		objectList.add(obj1);
		objectList.add(obj2);
		return objectList;
	}

	/**
	 * 指定したクラスが配備されているプロジェクトのパスを返却する
	 * @param inClass
	 * @return
	 */
	public String getProjectDir(Class<?> inClass) {
		var simpleName = inClass.getSimpleName() + ".class";
		var userDir = inClass.getResource(simpleName).toString();
    	return userDir;
	}
}
