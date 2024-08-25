package com.itkoza.fm.businessLogic.common;

import java.beans.XMLDecoder;
import java.beans.XMLEncoder;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class XmlUtil {
	
	private static XmlUtil XmlUtilFactory = new XmlUtil();

	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private XmlUtil() {}
	
	public static XmlUtil getInstancce() {
		return XmlUtilFactory;
	}
		
	/**
	 * Objectの配列をxmlファイルに出力する。
	 * (オブジェクトの配列をXMLにエンコードする）
	 * @param objArray Objectの配列
	 * @param fileName xmlファイル
	 * @throws FileNotFoundException
	 */
	public void encodeBeanArray(Object beanArray[], String fileName) throws FileNotFoundException{
		var fileOutputStream = new FileOutputStream(fileName);
		var bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
		var encoder = new XMLEncoder(bufferedOutputStream);
		encoder.writeObject(beanArray);
		encoder.close();
	}

	/**
	 * xmlファイルをオブジェクトの配列で取得する。
	 * (encodeBeanArrayでエンコードしたXMLをデコードする）
	 * @param fileName
	 * @return
	 * @throws FileNotFoundException
	 */
	public Object[] decodeBeanArray(String fileName) throws FileNotFoundException{
		var fileInputStream = new FileInputStream(fileName);
		var bufferedInputStream = new BufferedInputStream(fileInputStream);
		var dec = new XMLDecoder(bufferedInputStream);
		var rtnBeanArray = (Object[])dec.readObject();
        dec.close();
        return rtnBeanArray;
	}
}
