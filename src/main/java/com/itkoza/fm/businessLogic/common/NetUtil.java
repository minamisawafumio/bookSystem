package com.itkoza.fm.businessLogic.common;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;


public class NetUtil {
	
	private static NetUtil netUtil = new NetUtil();
	
	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private NetUtil() {}
	
	public static NetUtil getInstance() {
		return netUtil;
	}

	/**
	 * 自分の IP アドレスを取得する
	 * @return
	 * @throws SocketException
	 */
	public List<String> getHostAddressList() throws SocketException {
		var rtnList = new ArrayList<String>();

		for(var n: Collections.list(NetworkInterface.getNetworkInterfaces()) ) {
			for (var addr : Collections.list(n.getInetAddresses()))  {
				if( addr instanceof Inet4Address && !addr.isLoopbackAddress() ){
					rtnList.add(addr.getHostAddress());
				}
			}
		}
		return rtnList;
	}

	/**
	 *
	 * @param url
	 * @return
	 */
	private List<String> getHostNameAndIpAddress() {
		try{
			var inetAddress = InetAddress.getLocalHost();
			var address = inetAddress.toString();
			return StringUtil.getInstance().splitStringList(address, "/");
		}catch(Exception e){
			return null;
		}
	}

	/**
	 * ホスト名を取得する
	 * @return
	 */
	public String getMyHostName() {
		List<String> l = getHostNameAndIpAddress();
		return l.get(0);
	}

	/**
	 * IPアドレスを取得する
	 * @return
	 */
	public String getMyIpAddress() {
		List<String> l = getHostNameAndIpAddress();
		return (String)l.get(1);
	}

	/**
	 * リモート端末の IP アドレス
	 * @param req
	 * @return
	 */
	public String getRemoteAddr(HttpServletRequest req) {
		return req.getRemoteAddr();
	}

	/**
	 *
	 * @param request
	 * @return
	 */
	public Map<String, List<String>> getHeaderInfo(HttpServletRequest request) {
		Map<String, List<String>> rtnMap = new HashMap<>();
		Enumeration headernames = request.getHeaderNames();
	    while (headernames.hasMoreElements()){
	      String name = (String)headernames.nextElement();
	      Enumeration headervals = request.getHeaders(name);
	      List<String> list = new ArrayList<>();
	      while (headervals.hasMoreElements()){
	        String val = (String)headervals.nextElement();
	        list.add(val);
	      }
	      rtnMap.put(name, list);
	    }
	    return rtnMap;
	}

	/**
	 *
	 * @param strUrl
	 * @return
	 */
	public String getReturnUrl(String strUrl){

		URL url = null;

		try {
			url = new URL(strUrl);
		} catch (MalformedURLException e) {

			e.printStackTrace();
			return null;
		}

		String query = URLEncoder.encode("message=テスト");

		int cl = query.length();

		StringBuffer str = new StringBuffer();

		try {
			var uc = url.openConnection();
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setAllowUserInteraction(false);
			uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			uc.setUseCaches(false);
			var dos = new DataOutputStream(uc.getOutputStream());

			dos.writeBytes(query);
			dos.flush();
			dos.close();

			var dis = new DataInputStream(uc.getInputStream());
			String nextline;

			while((nextline=dis.readLine())!=null){
				str.append(nextline+"＼n");
			}

			dis.close();
		} catch (Exception ex) {
			return null;
		}
		return str.toString();
	}

	public String getIp() {
    	String rtn = null;

    	try {
			rtn = getHostAddressList().get(0);
		} catch (SocketException e) {
			e.printStackTrace();
		}

    	return rtn;
    }

	/*
	 * URLコンテンツをListで返す
	 **/
	public List<String> getUrlContents(String url) {
		var l = new ArrayList<String>();

		try{
			var u = new URL(url);
			var obj = u.getContent();
			if(obj instanceof InputStream){
				var br = new BufferedReader(new InputStreamReader((InputStream)obj));
				String line;
				while((line = br.readLine()) != null){
					l.add(line);
				}
				br.close();
			}
			return l;
		}catch(Exception e){
			return null;
		}
	}
	
	/**
	 * HTTP GET リクエスト実行
	 */
	public String getJson(String urlSt) {
		StringBuffer json = new StringBuffer();
		URL url;
		try {
			url = new URL(urlSt);
			BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
			
			String line;

			while ((line = reader.readLine()) != null) {
			  json.append(line);
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json.toString();
	}
}
		
