package com.itkoza.fm.businessLogic.common;

import java.lang.reflect.InvocationTargetException;

public class ReflectionUtil {
	
	private static ReflectionUtil reflectionUtil = new ReflectionUtil();

	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private ReflectionUtil() {}
	
	public static ReflectionUtil getInstance() {
		return reflectionUtil;
	}
			
	/**
     * オブジェクトを取得する
     * @param tableObject
     * @param getterMethod
     * @return
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
     */
	public Object doGetterMethod(Object tableObject, String getterMethod) throws Exception{
        Class[] argType = {};
        Object[] args = {};
		return invoke(tableObject, getterMethod, argType, args);
    }

	/**
	 * メソッドの型を取得する
	 * @param obj
	 * @param fieldName
	 * @return
	 * @throws Exception 
	 */
	public String getMethodType(Object obj, String fieldName) throws Exception{

		String rtnSt = null;
		
		try {
			rtnSt = obj.getClass().getDeclaredField(fieldName).getType().getSimpleName();
		} catch (Exception e) {
			throw e;
		}

		return rtnSt;
	}

	/**
	 * クラス（インスタンス）のフィールドの値を取得する
	 * @param clsInst
	 * @param fieldName
	 * @param value
	 */
	public Object getValueOfField(Object clsInst, String fieldName){
		var fields = clsInst.getClass().getDeclaredFields();

		for(var i =0; i < fields.length; i++){
			if(fieldName.equals(fields[i].getName())){
				fields[i].setAccessible(true);
				try {
					return fields[i].get(clsInst);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		return null;
	}

	/**
	 * クラス（インスタンス）のメソッド（文字列）を実行する
	 * @param clsInst
	 * @param methodName
	 * @param argType
	 * @param args
	 * @return
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 */
	public Object invoke(Object clsInst, String methodName, Class[] argType, Object[] args) throws Exception{
		var method = clsInst.getClass().getDeclaredMethod(methodName, argType);
		method.setAccessible(true);
		return method.invoke(clsInst, args);
	}

    /**
     * 指定したクラス名が存在するか？
     * @param className
     * @return
     */
	public Boolean isExistClass(String className){
		try {
			Class.forName(className);
		} catch (ClassNotFoundException e) {
			return false;
		}
		return true;
	}

	/**
	 * 指定したクラス内に指定(シンプル)メソッド名が存在するか？
	 * @param obj
	 * @param methodName
	 * @return
	 */
	public Boolean isExistMethodName(Object obj, String _methodName){

		var methodArray = obj.getClass().getMethods();

		for(var method: methodArray){
			var methodName = method.getName();
			if(methodName.equals(_methodName)){
				return true;
			}
		}
		return false;
	}
}
