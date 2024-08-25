package com.itkoza.fm.businessLogic.common;

import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

public class MangaDbUtil {
	
	private static MangaDbUtil mangaDbUtil = new MangaDbUtil();

	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private MangaDbUtil() {}
	
	public static MangaDbUtil getInstance() {
		return mangaDbUtil;
	}
		
	//ガベージコレクション実行
	private void doGc() {
		
		Runtime runtime = Runtime.getRuntime();
		
		runtime.gc();
		System.out.println("-- runtime.gc(); -----------------------------------");

	}
	
	
	//画像データ取得（７ページ分）
	//文字列 "000000" の3つ目の"0"が現在ページ。左端の "0"は + 3ページ。右端の"0"は - 3ページ
	//"1"はページデータが無い事の意味で、DBより取得する。"1"は、ページデータ有りの意味で、DBよりデータを取得しない。
//	public Map<String, Object> getPics(Map<String, Object> inMap) {
//
//		doGc();
//
//		int pageNoInt = Integer.parseInt((String) inMap.get("picCount")) -
//						Integer.parseInt((String) inMap.get("pageNo")) + 5;
//
//		var pageInfo = (String) inMap.get("needPageInfo");
//		
//		System.out.println(pageInfo);
//		
//		var sb = new StringBuffer();
//
//		var numMap = new HashMap<String, String>();
//
//		for (var i = 1; i < 8 ; i++) {
//			inMap.put(String.valueOf(i),"");
//			if("1".equals(pageInfo.substring(i - 1, i))) {
//				numMap.put(Integer.toString(pageNoInt - i), String.valueOf(i));
//				sb.append("'");
//				sb.append(Integer.toString(pageNoInt - i));
//				sb.append("',");
//			}
//		}
//		
//		var stringUtil = StringUtil.getInstance();
//		
//		List<Object> list = Arrays.asList(stringUtil.changeFormat("000000", inMap.get("bookNo").toString()), 
//										sb.delete(sb.length() - 1, sb.length()).toString());
//		
//		var sql = stringUtil.bindMoji(SystemConst.SQL_T_0001_01, list);
//
//		//タブを半角空文字に変換
//		sql = sql.replaceAll("\t", " ");
//		//半角空文字が複数の場合に1つに変換				
//		System.out.println(StringUtil.getInstance().deleteDuplicateSpace(sql));
//		
//		var dbUtil = DbUtil.getInstance();
//
//		var sqlSession = dbUtil.getNewSqlSession(SystemConst.MYBATIS_CONFIG);
//
//		for(var mm: dbUtil.select(sqlSession, sql)) {
//			inMap.put(numMap.get((String) mm.get("key3")), mm.get("value"));
//		}
//
//		sqlSession.close();
//
//		return inMap;
//	}

	/**
	 * マンガタイトルマップ取得
	 * @return
	 * @throws FileNotFoundException 
	 */
	public Map<String, Integer> getNumTittleMap() throws FileNotFoundException {
		var map = new HashMap<String, Integer>();

		var list = MangaUtil.getInstance().getTittleList();

		for(var i = 0; i < list.size(); i++) {
			var data = (Object [])list.get(i);
			
			String title = (String) data[0];
			
			map.put(title, i);
		}

	   	return map;
	}

	/**
	 * マンガタイトルマップ取得
	 * @return
	 * @throws FileNotFoundException 
	 */
	public Map<Integer, String> getTittleMap() throws FileNotFoundException {
	   	var map = new HashMap<Integer ,String>();

	   	var list = MangaUtil.getInstance().getTittleList();

		for(var i = 0; i < list.size(); i++) {
			Object []data = (Object [])list.get(i);

			map.put(i, (String) data[0]);
		}

	   	return map;
	}

	public Map<String, Integer> getTittleNameMap() throws FileNotFoundException {
	   	var map = new HashMap<String, Integer>();

	   	var list = MangaUtil.getInstance().getTittleList();

		for(var i = 0; i < list.size(); i++) {
			Object []data = (Object [])list.get(i);

			map.put((String) data[0], i);
		}

	   	return map;
	}

	/**
	 *
	 * @param key
	 * @return
	 */
	public String getWhereKu(String key, String ijyouKensuuKey) {

		var whereAdd = new StringBuffer(" where corp_cd='01' and del_flg='0' and rec_cd='014' and rec_kbn='4'");

		if(key != null) {
			//重複したスペースを１字に変換する
			key = StringUtil.getInstance().deleteDuplicateString(key);

			String array[] = key.split(" ");

			for(var i = 0; i < array.length; i++) {
				var key2 = array[i].trim();

				if(key2.length() > 0) {
					whereAdd.append(" and item16 like '%" + key2 + "%'");
				}
			}
		}

		if(ijyouKensuuKey != null && ijyouKensuuKey.length() > 0) {
			ijyouKensuuKey = ijyouKensuuKey.trim();
			whereAdd.append(" and cast(item02 as integer)>=");
			whereAdd.append(ijyouKensuuKey);
		}

		return whereAdd.toString();
	}

	/**
	 *
	 * @param key
	 * @param ijyouKensuuKey
	 * @return
	 */
	public StringBuffer getSelectCountSql(String key, String ijyouKensuuKey) {
		return new StringBuffer("select count(rec_cd) from t_1010 " + getWhereKu(key, ijyouKensuuKey));
	}

//	public Map<String, Object> getMangaRec2(String bookNo) {
//		var dbUtil = DbUtil.getInstance();
//
//		var sqlSession = dbUtil.getNewSqlSession(SystemConst.MYBATIS_CONFIG);
//
//		var sql = SystemConst.SQL_T_1010_01.formatted(bookNo);
//		
//		sql = sql.replaceAll("\t", " ");
//		sql = StringUtil.getInstance().deleteDuplicateSpace(sql);
//
//		System.out.println(sql);
//
//		var rtnMap = dbUtil.selectOne(sqlSession, sql);
//
//		sqlSession.close();
//
//		return rtnMap;
//	}
}
