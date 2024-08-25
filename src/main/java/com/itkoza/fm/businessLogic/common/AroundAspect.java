//package fm.businessLogic.common;
//
//import java.io.IOException;
//import java.util.Set;
//
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import jakarta.servlet.http.HttpSession;
//
////import org.apache.log4j.Level;
////import org.apache.log4j.Logger;
//import org.aspectj.lang.ProceedingJoinPoint;
//import org.aspectj.lang.annotation.Around;
//import org.aspectj.lang.annotation.Aspect;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//@Aspect
//@Component
//public class AroundAspect {
//
//	//private static Logger logger = Logger.getLogger(AroundAspect.class);
//	private static final Logger logger = LoggerFactory.getLogger(AroundAspect.class);
//
//    @Around("execution(* jp.co.fm.businessLogic.service..*.*(..))")
//    public Object around(ProceedingJoinPoint proceedingJoinPoint) {
//		HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        print(proceedingJoinPoint);
//        Object object = execute(proceedingJoinPoint, request);
//        return object;
//    }
//
//    private void print(ProceedingJoinPoint proceedingJoinPoint) {
//    	var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//        var httpSession = request.getSession();
//
//        String userId = (String) httpSession.getAttribute(Const.USER_ID);//get(Const.USER_ID, httpSession);
//        String methodName = proceedingJoinPoint.getStaticPart().toString();
//
//        logger.debug("Start (" + userId + ") ★★★★★★★ 実行メソッド=" + methodName);
//    }
//
//
//    /**
//     * メソッド呼出
//     * @param proceedingJoinPoint
//     * @param httpSession
//     * @return
//     */
//    private Object execute(ProceedingJoinPoint proceedingJoinPoint, HttpServletRequest request){
//    	HttpSession httpSession = request.getSession();
//        // ここで対象のメソッドを呼び出します。
//        Object object = null;
//
//        try{
//        	boolean isLogin = SystemServiceUtil.getInstance().isLogin(request);
//
//        	boolean noLoginGamenBool = isNoLoginGamen(proceedingJoinPoint, request);
//
//        	//ログインが必要な画面、かつ、ログインしていない場合
//        	if(! noLoginGamenBool && ! isLogin) {
//        		logger.info("ログインしていません");
//           		throw new Throwable();
//        	}
//
//           	// メソッドの実行
//        	object = proceedingJoinPoint.proceed();
//        }catch(Throwable e){
//        	//ログアウト
//        	httpSession.invalidate();
//        	object = "ERROR001";
//        }
//
//        // メソッドの戻り値を返却しています。
//        return object;
//    }
//
//    /**
//     * フォワード（現在、未使用）
//     * @throws ServletException
//     * @throws IOException
//     */
//    private void forward() throws ServletException, IOException  {
//    	var request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
//    	var requestAttributes = RequestContextHolder.getRequestAttributes();
//    	var response = ((ServletRequestAttributes)requestAttributes).getResponse();
//    	String disp = "/resources/jsp/common.jsp";
//    	var dispatcher = request.getRequestDispatcher(disp);
//    	dispatcher.forward(request, response);
//    }
//
//    /**
//     * ログインしているかチェックする
//     * @param pj
//     * @throws Exception
//     */
//    private Boolean isNoLoginGamen(ProceedingJoinPoint pj, HttpServletRequest request){
//
//    	String gamenId = pj.getThis().getClass().getSimpleName().substring(0, 8);
//
//    	//ログイン画面、ユーザ登録画面、エラー画面以外をチェックする
//    	Set<String> set = (Set<String>) SystemInfoUtil.getInstance().getValue(Const.NO_LOGIN);
//
//    	if(! set.contains(gamenId)){
//        	return false;
//
//    	}
//    	return true;
//    }
//
//    /**
//    *
//    */
//   private void logPrint(String userId, String methodName, HttpSession httpSession){
//   	//セッション中の一時ログを取得する
//   	Object obj = httpSession.getAttribute(Const.LOG);//get(Const.LOG, httpSession);
//
//   	if(obj != null){
//       	Object []array = (Object []) obj;
//
//       	logger.debug("(" + userId + ")" + array[1] + methodName);
//
//   	}
//   }
//
//    /**
//     * リダイレクト（現在、未使用）
//     * @throws IOException
//     */
//    private void sendRedirect() throws IOException {
//		RequestAttributes requestAttributes = RequestContextHolder.getRequestAttributes();
//        HttpServletResponse response = ((ServletRequestAttributes)requestAttributes).getResponse();
//    	String disp = "/resources/jsp/common.jsp";
//    	response.sendRedirect(disp);
//    }
//
//}