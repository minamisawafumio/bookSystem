package com.itkoza.fm.businessLogic.common;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MangaUtil {
	
	private static MangaUtil mangaUtil = new MangaUtil();
	
	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private MangaUtil() {}
	
	public static MangaUtil getInstance() {
		return mangaUtil;
	}

	private  List<Object[]> tittleList;

	/**
	 * マンガタイトルのマップを取得する
	 * @return
	 * @throws FileNotFoundException 
	 */
	public Map<Integer, String> makeTittleMap() throws FileNotFoundException {
		var map = new HashMap<Integer, String>();

		var tittleList = getTittleList();

		for(var i = 0; i < tittleList.size(); i++) {
			var data = (Object [])tittleList.get(i);

			map.put(i, (String) data[0]);
		}
		return map;
	}

	public List<Object[]> getTittleList() throws FileNotFoundException {

		if (tittleList == null) {
			tittleList = new ArrayList<>();
			
			var path  =  FileUtil.getInstance().getSystemPath();

			var pathFileName = path  + "/" + Const.WORKSPACE_NAME + "/skdb/src/main/webapp/WEB-INF/temp/mangaData.json";

			String st = null;
			try {
				st = FileUtil.getInstance().file2string2(pathFileName);
			} catch (FileNotFoundException e) {
				throw e;
			}

			var list = JsonUtil.getInstance().makeListFromJsonSt(st);

			tittleList.addAll(list);
		}

		return tittleList;
    }
}
