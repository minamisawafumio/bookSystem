package com.itkoza.fm.businessLogic.common;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.apache.logging.log4j.Level;
import org.springframework.ui.Model;


public class ServiceUtil {
	
	private static ServiceUtil serviceUtil = new ServiceUtil();
	
	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private ServiceUtil() {}
	
	public static ServiceUtil getInstance() {
		return serviceUtil;
	}

	public String getSimpleName(Class<?> inClass) {
		return inClass.getSimpleName();
	}


	public String gotoNextGamen( HttpServletRequest request, Object motoForm, Object sakiForm, Model model) {
		var httpSession = request.getSession();
		var motoFormId = motoForm.getClass().getSimpleName().replaceAll("Form", "");
		var sakiFormId = sakiForm.getClass().getSimpleName().replaceAll("Form", "");
    	httpSession.setAttribute(motoFormId + "FORM", motoForm);
    	httpSession.setAttribute(sakiFormId + "FORM", sakiForm);
    	httpSession.setAttribute(sakiFormId + "BACK", motoFormId);
    	model.addAttribute(sakiFormId + "Form", sakiForm);
    	return sakiFormId;
    }


//	public String backGamen(HttpServletRequest request, Model model, Object thisForm) {
//       	var httpSession = request.getSession();
//       	var thisGamenId = thisForm.getClass().getSimpleName().replaceAll("Form", "");
//       	var motoGamenId = (String) httpSession.getAttribute(thisGamenId + "BACK");
//       	var form = httpSession.getAttribute(motoGamenId + "FORM");
//    	//セッションタイムアウトの場合、メニュー画面に戻る
//    	if(form == null) {
//    		return SystemServiceUtil.getInstance().goMenu(model, request);
//    	}
//    	model.addAttribute(motoGamenId + "Form", form);
//        return motoGamenId;
//    }

    /**
     * ログに出力する情報をセッションに出力する
     * @param level
     * @param value
     */
	public void log(Level level, String value, HttpSession httpSession) {
    	Object []array = {level, value};
    	httpSession.setAttribute(Const.LOG, array);
    }

}
