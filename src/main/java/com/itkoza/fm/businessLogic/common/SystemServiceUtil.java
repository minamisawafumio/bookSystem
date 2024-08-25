package com.itkoza.fm.businessLogic.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//import javax.xml.bind.DatatypeConverter;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

//import jp.co.fm.presentation.form.ERROR001Form;
//import net.arnx.jsonic.JSON;

public class SystemServiceUtil {
	
	private static SystemServiceUtil systemServiceUtil = new SystemServiceUtil();
	
	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private SystemServiceUtil() {}
	
	public static SystemServiceUtil getInstance() {
		return systemServiceUtil;
	}

    private Map<String, Object> instanceMap = new HashMap<String, Object>();

	/**
	 * 指定文字列のSystemCommonクラスのstaticメソッドを実行
	 * @param request
	 * @return
	 */
//	public String syori(HttpServletRequest request) {
//		var jsonData = "";
//
//		var map = getGamenMap2(request);
//		
//		var className	= (String) map.get("className");
//        //実行クラスのインスタンスがMap内より取得する
//		Object clsInst = instanceMap.get(Const.SERVICE_PACKAGE_NAME + className);
//
//		try {
//			//実行クラスのインスタンスがMap内に無い場合
//	        if (clsInst == null) {
//		        //新たに作成する
//	        	clsInst =  Class.forName(Const.SERVICE_PACKAGE_NAME + className).getDeclaredConstructor().newInstance();
//	        	instanceMap.put(Const.SERVICE_PACKAGE_NAME + className, clsInst);
//	        }
//
//			Class[] argType = {HttpServletRequest.class};
//	        Object[] args  ={request};
//	        var methodName	= (String) map.get("methodName");
//	        
//			var httpSession = request.getSession();
//	        String userId = (String) httpSession.getAttribute(Const.USER_ID);
//	        System.out.println("className=" + className + "  " + "methodName=" + methodName + "  " + "userId=" + userId);
//			jsonData = (String)ReflectionUtil.getInstance().invoke(clsInst, methodName, argType, args);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return jsonData;
//	}

	/**
	 *
	 * @param request
	 * @return
	 */
	public boolean isLogin(HttpServletRequest request) {
       	//ログインしていない場合
		if(request.getSession().getAttribute(Const.USER_ID) == null){
			return false;
		}
		
		return true;
	}

	/**
	 * クロスオリジンを回避（なぜ？うまく動かない）
	 * @param request
	 * @param response
	 */
	public void avoidCrossOrigin(HttpServletRequest request, HttpServletResponse response) {
		//レスポンスヘッダを付ける（つけないとホスト名が変わる(localhose⇒直ID)とChromeでエラーになる）
		response.setHeader("Access-Control-Allow-Origin", "*");
//				response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
		response.setHeader("Access-Control-Allow-Methods", "POST, PUT, GET, DELETE, OPTIONS");
		response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
		response.setHeader("Access-Control-Max-Age", "-1");
	}

	/**
	 *
	 * @param request
	 * @return
	 */
//	public Map<String, Object> getGamenMap2(HttpServletRequest request) {
//
//		var jsonString = request.getParameter(Const.REQUEST_KEY);
//
//		if(jsonString == null) {
//			return null;
//		}
//		
//		var json = new String(DatatypeConverter.parseHexBinary(jsonString));
//
//		return JSON.decode(json, Map.class);
//	}

	/**
	 *
	 * @return
	 */
//	public String getNotLoginImg() {
//
//		var dbUtil = DbUtil.getInstance();
//		
//		//待ち画像取得用のSQLを返す
//		Supplier<String> getSQL = () -> {
//			var map = new HashMap<String, Object>();
//			map.put(SystemConst.TABLE_NAME, "T_0001");
//			map.put(SystemConst.TABLE_ITEMS	,"*");
//			map.put(SystemConst.PK_KEY, SystemConst.PK_T_0001);
//			map.put("key1", "1");
//			map.put("key2", "0001");
//			map.put("key3", "1");
//			return dbUtil.getSelectSql(map);
//		};
//
//		String imgString = null;
//
//		//エラー画像（ログインしなおしてください）取得
//		var sqlSession = dbUtil.getSqlSession();
//
//		var resultList = dbUtil.select(sqlSession, getSQL.get());
//		
//		if(! resultList.isEmpty()) {
//			try {
//				imgString = resultList.get(0).get("value").toString();
//			}catch(Exception e) {
//				e.printStackTrace();
//			}
//		}
//
//		return imgString;
//	}

	/**
	 * 初期画面に戻る
	 * @param model
	 * @param request
	 * @return
	 */
//	public String goMenu(Model model, HttpServletRequest request) {
//
//		var httpSession = request.getSession();
//
//		var form = new ERROR001Form();
//
//    	httpSession.setAttribute("eRROR001Form", form);
//
//        model.addAttribute("ERROR001Form", form);
//
//        return "ERROR001";
//    }

	/**
	 *
	 * @param request
	 * @param key
	 * @return
	 */
	public String getJson(HttpServletRequest request, String key) {
		return StringUtil.getInstance().decodeStBase64(request.getParameter(key));
	}

	/**
	 *
	 * @param request
	 * @param key
	 * @return
	 */
	public Map<String, String> getDecodeMap(HttpServletRequest request, String key) {
		var json = getJson(request, key);
		json = StringUtil.getInstance().decode(json);
		return JsonUtil.getInstance().parse(Map.class, json);
	}

	public Map<String, List<Object>> getGamenListA(Map<String, Object> map) {
		return Map.of(
			"thisGamenId"	,List.of(map.get("thisGamenId")),
			"methodName"	,List.of(map.get("methodName")),
			"searchKey"		,List.of(StringUtil.getInstance().cnvNull((String) map.get("searchKey"))),
			"syoriNo"		,List.of(map.get("syoriNo")),
			"className"		,List.of(map.get("className")),
			"mangaOffset"	,List.of(map.get("mangaOffset")),
			"listenerEvent"	,List.of(map.get("listenerEvent")
		));
	}
	
//    public boolean isLogin(String userId, String password) {
//
//    	var hashPassword = StringUtil.getInstance().encryption(password, Const.CODE_SHA_512);
//
//    	var dbUtil = DbUtil.getInstance();
//
//    	var m = dbUtil.selectOne(dbUtil.getSqlSession(), 
//    			StringUtil.getInstance().bindMoji(SystemConst.SQL_T_0160_01, List.of(userId).toArray()));
//
//		if(m == null || hashPassword == null){
//			return false;
//		}else {
//			if(! hashPassword.equals(m.get("pswd"))){
//				return false;
//			}
//		}
//		
//		return true;
//	}
}
