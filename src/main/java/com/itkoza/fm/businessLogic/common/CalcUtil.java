package com.itkoza.fm.businessLogic.common;

public class CalcUtil {

	private static CalcUtil calcUtil = new CalcUtil();

	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private CalcUtil() {}
	
	public static CalcUtil getInstance() {
		return calcUtil;
	}

//	public Double calc(String siki,String a,String b, Double da, Double db) {
//		Expression e = new ExpressionBuilder(siki)
//                .variables(a, b)
//                .build()
//                .setVariable(a, da)
//                .setVariable(b, db);
//        double result = e.evaluate();
// 
//        return result; 
//    }
}	
	

