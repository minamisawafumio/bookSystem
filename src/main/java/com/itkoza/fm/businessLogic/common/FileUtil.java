package com.itkoza.fm.businessLogic.common;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeMap;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;



public class FileUtil {
	
	private static FileUtil fileUtil =  new FileUtil();
	
	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private FileUtil() {}
	
	public static FileUtil getInstance() {
		return fileUtil;
	}
				
	/**
	 * byte配列をファイルに出力する
	 * @param b
	 * @param outputFile
	 */
	public int byte2File(byte[] byteData, String outputFile) {
		var rtnCd =0;
	
		try {
			var inputStream = new ByteArrayInputStream(byteData);
			inputStream2File(inputStream, outputFile);
			inputStream.close();
		} catch (IOException e) {
			rtnCd = 1;
			e.printStackTrace();
		}
	
		return rtnCd;
	}
	
	/**
	 * ファイル名変更
	 * @param oldFilePathAndName
	 * @param newFilePathAndName
	 * @return
	 */
	public String changeFileName(String oldFilePathAndName, String newFilePathAndName){
		 //変更前ファイル名
		var fileA = new File(oldFilePathAndName);
	
	      //変更後のファイル名
		var fileB = new File(newFilePathAndName);
	
	      if(fileA.renameTo(fileB)){
	         return newFilePathAndName;
	      }else{
	         return null;
	      }
	}
	
	/**
	 *
	 * @param is
	 * @return
	 */
	public byte[] file2Byte(BufferedInputStream is){
		var b = new ByteArrayOutputStream();
		var os = new BufferedOutputStream(b);
		int c;
		try {
			while ((c = is.read()) != -1) {
				os.write(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return b.toByteArray();
	}
	
	public byte[] file2Byte(DataInputStream is){
		var b = new ByteArrayOutputStream();
		var os = new BufferedOutputStream(b);
		int c;
		try {
			while ((c = is.read()) != -1) {
				os.write(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return b.toByteArray();
	}
	
	/**
	 * ファイルをコピーする
	 * @param path1
	 * @param fileName1
	 * @param path2
	 * @param fileName2
	 * @return
	 */
	public int copyFile(String path1, String fileName1, String path2, String fileName2) {
		return copyFile(path1 + "/" + fileName1, path2 + "/" + fileName2);
	}
	
	/**
	 * フルパスのファイルをコピーする
	 * @param pathFile1
	 * @param pathFile2
	 * @return
	 */
	public int copyFile(String pathFile1, String pathFile2) {
		var rtnCd = 0;
	
		try{
			var ic = new FileInputStream(pathFile1).getChannel();
			var oc = new FileOutputStream(pathFile2).getChannel();
			oc.transferFrom(ic, 0, ic.size());
			ic.close();
			oc.close();
		}catch(IOException e){
			rtnCd = 1;
			e.printStackTrace();
		}
	
		return rtnCd;
	}
	
	/**
	 * パス内のファイルをコピーする
	 * @param path1
	 * @param path2
	 * @return
	 */
	public int copyPath(String path1, String path2) {
		var rtnCd = 0;
	
		var fileList = getFileOnlyNameList(path1);
	
		for(var fileName: fileList){
			rtnCd = copyFile(path1, fileName, path2, fileName);
	
			if(rtnCd != 0){
				return rtnCd;
			}
		}
	
		return rtnCd;
	}
	
	/**
	 * 画像ファイルの幅、高さを配列で返す
	 * @param pathAndfileName
	 * @return
	 */
	public Integer[] getFileWidthHeight(String pathAndfileName) {
		var file = new File(pathAndfileName);
	
		var width = 0;
		var height = 0;
	
		try {
			var img = ImageIO.read(file);
			width = img.getWidth();
			height = img.getHeight();
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		Integer widthHeight[] = {width, height};
		return widthHeight;
	}
	
	/**
	 * ファイル削除
	 * @param pathAndfileName
	 * @return
	 */
	public boolean delete(String pathAndfileName) {
		var file = new File(pathAndfileName);
	    return file.delete();
	}
	
	/**
	 * ファイル削除
	 * @param path
	 * @param fileName
	 * @return
	 */
	public boolean delete(String path, String fileName) {
		var file = new File(path + "/" + fileName);
	    return file.delete();
	}
	
	/**
	 * 指定ディレクトリ内のファイルを削除する
	 * @param path
	 * @return
	 */
	public boolean deleteFiles(String path) {
		var fileList = getFileOnlyNameList(path);
	
		for(var i = 0; i < fileList.size(); i++){
			String fileName = fileList.get(i);
			delete(path, fileName);
		}
		return true;
	}
	
	/**
	 * ファイルをbyte配列で返す
	 * @param file
	 * @return
	 */
	public byte[] file2Byte(String file){
		try {
			var bis = new BufferedInputStream( new FileInputStream(file) );
			return file2Byte(bis);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * ファイルをオブジェクトに変換する
	 * @param intputFile
	 * @return
	 */
//	public Object file2Object(String intputFile, Object object) {
//	
//		String st = null;
//	
//		try {
//			st = file2string2(intputFile);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//			return null;
//		}
//	
//		return JsonUtil.getInstance().makeObjectFromJsonString(object.getClass(), st);
//	}
	
	/**
	 * ファイル（encodeBase64画像ファイルなど）を文字列にして返す。
	 * (画像ファイルなどのファイルを文字列に変換する)
	 * @param fileName
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws MessagingException
	 */
//	public String file2string(String fileName) {
//	
//		String ddd = null;
//	
//		try {
//			var fis = new FileInputStream(fileName);
//			//BufferedInputStream bis = new BufferedInputStream(fis); //BufferedInputStream 非推奨になったのでやめた
//			var bis = new DataInputStream(fis);
//	
//			var ss = file2Byte(bis);
//			ddd = StringUtil.getInstance().encodeBase64_2(ss);
//			bis.close();
//			fis.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//	
//		return ddd;
//	}
	
	public String file2string2(String fileName) throws FileNotFoundException{
		String result = null;
		try {
			var fis = new FileInputStream(fileName);
			var bis = new BufferedInputStream(fis);
			var ss = file2Byte(bis);
			result = new String(ss, "UTF-8");
			bis.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	/**
	 * ファイルコピー処理
	 * @param inPathAndfileName   コピー元
	 * @param outPathAndfileName  コピー先
	 * @return
	 */
	public int fileCopy(String inPathAndfileName, String outPathAndfileName){
	
		var buff = new byte[1024];    //配列の定義
	    FileInputStream infile = null;    // ファイル“入力装置”
	    FileOutputStream outfile=null;    // ファイル“出力装置”
	    try{
	        infile = new FileInputStream(inPathAndfileName) ;
	        outfile = new FileOutputStream(outPathAndfileName) ;
	    }
	    catch(FileNotFoundException e){
	        return 1;
	    }
	
	    // ファイルの終了まで,以下のループを繰り返します
	    try {
	        while (true) {
	        	var n = infile.read(buff);    // 入力ファイルからの読み込み
	            if(n<0) break;                // 入力ファイルの終わりに達した場合ループを終了
	            else outfile.write(buff, 0, n);    // 出力ファイルへの書き出し
	        }
	        infile.close() ;
	        outfile.close() ;
	    } catch(IOException e) {
	        return 1;
	    }
	    return 0;
	}
	
	/**
	 * Listをファイル出力
	 * @param toCode 文字コード "SJIS"、"UTF-8"
	 * @param lineCode 改行コード LF="\n"、CRLF="\r\n"
	 * @param list 出力文字列リスト
	 * @param pathFileName
	 * @return
	 */
	public int fileWrite(String toCode, String lineCode, List<String> list, String pathFile) {
	
	    PrintWriter pw2 = null;
	    
		try {
			pw2 = 
				new PrintWriter(
				new BufferedWriter(
				new OutputStreamWriter(
				new FileOutputStream
					(pathFile), toCode)));
			
			for (int i = 0; i < list.size(); i++) {
				var data = list.get(i);
				
				pw2.print(data + lineCode);
			}
			
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return 1;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 2;
		} finally {
			pw2.close();
		}
		
		return 0;
	}
	
	
	/**
	 * ファイル出力
	 * @param charCose 文字コード "SJIS"、"UTF-8"
	 * @param String 出力文字列
	 * @param pathFileName
	 * @return
	 */
	public int fileWrite(String charCose, String data, String pathFileName) {
		try {
			var fos = new FileOutputStream(pathFileName);
	
			var osw = new OutputStreamWriter(fos, charCose);
	
			var bw = new BufferedWriter(osw);
	
			bw.write(data);
	
			bw.close();
	
			osw.close();
	
			fos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return 1;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return 2;
		} catch (IOException e) {
			e.printStackTrace();
			return 3;
		}
		return 0;
	
	}
	
	/**
	 * ファイル(UTF-8)出力
	 * @param charCose 文字コード "SJIS"、"UTF-8"
	 * @param String 出力文字列
	 * @param pathFileName
	 * @return
	 */
	public int fileWriteUTF_8(String data, String pathFileName) {
		return fileWrite("UTF-8", data, pathFileName);
	}
	
	/**
	 * ファイルの内容をListに出力する
	 * @param pathFile
	 * @return
	 */
	public List<String> fileRead(String pathFile){
		var l = new ArrayList<String>();
	
		try{
			var br = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile), "Shift_JIS"));
		    String line;
		    while((line = br.readLine()) != null){
		    	l.add(line);
		    }
		    br.close();
		    return l;
		}catch(Exception e) {
		    return null;
		}
	}
	
	/**
	 * ファイルの内容をListに出力する
	 * ファイルの文字コード:charCose="SJIS","UTF-8"
	 */
	public List<String> fileRead2(String charCose, String pathFile){
		var l = new ArrayList<String>();
	
		try{
			var br = new BufferedReader(new InputStreamReader(new FileInputStream(pathFile), charCose));
		    String line;
		    while((line = br.readLine()) != null){
		    	l.add(line);
		    }
		    br.close();
		    return l;
		}catch(Exception e) {
		    return null;
		}
	}
	
	/**
	 * 	ファイルの内容を改行を削除してに文字列で返却する
	 */
	public String fileReadToString(String pathFile){
		var sb = new StringBuffer();
	    
	    var list = fileRead(pathFile);
	    
	    for(var st: list) {
	    	sb.append(st.replace(System.getProperty("line.separator").toString(), ""));
	    }
	    
	    return sb.toString();
	}
	
	
	/**
	 * ファイルの内容をListに出力する
	 * @param path
	 * @param fileName
	 * @return
	 */
	public List<String> fileRead(String path, String fileName){
	    return fileRead(path + "/" + fileName);
	}
	
	/**
	 * ファイルアップロード処理
	 * @param filePath アップロードフォルダ名
	 * @param ff
	 * @return
	 */
	public int fileUpload(String filePath, InputStream inputStream, String fileName){
	
		BufferedInputStream  bis = null;
		BufferedOutputStream bos = null;
	
		try {
			bis = new BufferedInputStream(inputStream);
			//格納ファイルパス
			bos = new BufferedOutputStream(new FileOutputStream(filePath + "/" + fileName));
			//1byte読んで1byte書き込む
			int b = 0;
			while((b = bis.read()) != -1){
				bos.write(b);
			}
		} catch (IOException e) {
			return 1;
		} finally {
			if (bis != null){
				try {
					bis.close();
				} catch (IOException e) {
					return 1;
				}
			}
			if (bos != null){
				try {
					bos.close();
				} catch (IOException e) {
					return 1;
				}
			}
		}
	
		return 0;
	}
	
	/**
	 * 文字列(画像を文字列化)のファイルをオブジェクトファイルに変換して出力する。
	 * （例：文字列ファイルを画像ファイルに変換して出力する。）
	 * @param gazouString
	 * @param objeFile
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws MessagingException
	 */
//	public int gazouString2objFile(String gazouString, String objeFile) {
//		var rtnCd = 0;
//	
//		try {
//			var fff = StringUtil.getInstance().decodeBase64(gazouString);
//			rtnCd = byte2File(fff, objeFile);
//		}catch(Exception e) {
//			rtnCd = 1;
//			e.printStackTrace();
//		}
//		return rtnCd;
//	}
	
	/**
	 * 文字列(画像を文字列化)のファイルをオブジェクトのファイルに変換して出力する。
	 * （例：文字列ファイルを画像ファイルに変換して出力する。）
	 * @param gazouFile
	 * @param objeFile
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws MessagingException
	 */
//	public int gazuFile2objFile(String gazouFile, String objeFile) {
//	
//		var rtnCd = 0;
//	
//		try {
//			var bis = new BufferedInputStream( new FileInputStream(gazouFile));
//			var byteData = file2Byte(bis);
//			var stdata = new String(byteData, "UTF-8");
//			gazouString2objFile(stdata, objeFile);
//		}catch(Exception e) {
//			rtnCd = 1;
//			e.printStackTrace();
//		}
//		return rtnCd;
//	}
	
	/**
	 * 絶対パスを取得する
	 * @return
	 */
	public String getAbsolutePath(){
		return new File("").getAbsolutePath();
	}
	
	/**
	 * 指定フォルダ名までの絶対パスを取得する
	 * @return
	 */
	public String getTargetPath(String folderName) {
		var folderNameLength = folderName.length();
		var path = getAbsolutePath();
		path = path.replaceAll("\\\\", "/");
		var ichi = path.lastIndexOf(folderName);
		return path.substring(0, ichi + folderNameLength);
	}
	
	/**
	 *
	 * @param inClass
	 * @return
	 */
	public String getClassDir(Class<?> inClass) {
		var ward = "workspace_skdb";
		var wardLength = ward.length();
		var simpleName = inClass.getSimpleName() + ".class";
		var userDir = inClass.getResource(simpleName).toString();
		var workspacePoint = userDir.indexOf(ward);
		var slashPoint = userDir.indexOf("/", workspacePoint + wardLength + 1);
		var rtnString = userDir.substring(0, slashPoint);
	   	return rtnString.replaceAll("file:/", "");
	}
	
	/**
	 * 指定ディレクトリ内のファイルの名称(パスを含む)をListで返す
	 * @param inPath
	 * @return
	 */
	public List<String> getPathFileNameList(String inPath) {
		List<String> list = getPathList(inPath);
	
		List<String> pathFilelist = new ArrayList<>();
	
		for(String pathName: list) {
			List<String> fileList = getFileOnlyNameList(pathName);
	
			StringBuffer sb;
	
			for(String fileName: fileList) {
				sb = new StringBuffer();
				sb.append(pathName);
				sb.append("\\");
				sb.append(fileName);
				pathFilelist.add(sb.toString());
			}
		}
	
		return pathFilelist;
	}
	
	
	/**
	 * 指定ディレクトリ内のファイルの名称(パスを含まない)をListで返す
	 * @param path
	 * @return
	 */
	public List<String> getFileOnlyNameList(String path) {
	
		List<String> fileList = new ArrayList<>();
	
	    File dir = new File(path);
	    File[] files = dir.listFiles();
	    for (int i = 0; i < files.length; i++) {
	        File file = files[i];
	
	        if(file.isFile()){
		        String fileName = file.getName();
		        fileList.add(fileName);
	        }
	    }
	
		return fileList;
	}
	
	/**
	 * 指定ディレクトリ内のファイルの名称(パスを含まない(拡張子も含まない))をListで返す
	 * @param path
	 * @return
	 */
	public List<String> getFileOnlyNameListNoExtension(String path) {
	
		List<String> fileList = new ArrayList<>();
	
	    File dir = new File(path);
	    File[] files = dir.listFiles();
	    for (int i = 0; i < files.length; i++) {
	        File file = files[i];
	
	        if(file.isFile()){
		        String fileName = file.getName();
		        String array[] = fileName.split(".");
		        fileList.add(array[0]);
	        }
	    }
	
		return fileList;
	}
	
	/**
	 * 指定ディレクトリ内のファイルの名称(パスを含まない(拡張子も含まない))をTreeSetで返す
	 * @param path
	 * @return
	 */
	public TreeMap<String, Integer> getFileOnlyNameTreeMapNoExtension(String path) {
	
		TreeMap<String, Integer> fileMap = new TreeMap<String, Integer>();
	
	    File dir = new File(path);
	    File[] files = dir.listFiles();
	    for (int i = 0; i < files.length; i++) {
	        File file = files[i];
	
	        if(file.isFile()){
		        String fileName = file.getName();
		        String []array = fileName.split(Pattern.quote("."));
		        fileMap.put(array[0], i + 1);
	        }
	    }
	
		return fileMap;
	}
	
	/**
	 * 指定ディレクトリ内の指定文字列を含むファイルの名称をListで返す
	 * (指定文字列は 大文字、小文字の区別なし)
	 * @param path
	 * @param kakucyousi
	 * @return
	 */
	public List<String> getFileSiteimojiIgnoreCaseList(String path, String siteimoji) {
	
		int siteimojiLength = siteimoji.length();
	
		List<String> fileList = new ArrayList<>();
	
	    File dir = new File(path);
	    File[] files = dir.listFiles();
	    for (int i = 0; i < files.length; i++) {
	        File file = files[i];
	
	        if(file.isFile()){
		        String fileName = file.getName();
	
		        String kakucyousi = fileName.substring(fileName.length() - siteimojiLength, fileName.length());
	
		        if(kakucyousi.compareToIgnoreCase(siteimoji)==0){
			        fileList.add(fileName);
		        }
	        }
	    }
	
		return fileList;
	}
	
	/**
	 * 指定ディレクトリ内の指定文字列を含むファイルの名称をListで返す
	 * (指定文字列は 大文字、小文字の区別をしている)
	 * @param path
	 * @param kakucyousi
	 * @return
	 */
	public List<String> getFileSiteimojiList(String path, String siteimoji) {
	
		List<String> fileList = new ArrayList<>();
	
	    File dir = new File(path);
	
	    File[] files = dir.listFiles();
	
	    for (int i = 0; i < files.length; i++) {
	        File file = files[i];
	
	        if(file.isFile()){
		        String fileName = file.getName();
	
		        Integer ichi = fileName.lastIndexOf(siteimoji);
	
		        if(ichi >= 0){
			        fileList.add(fileName);
		        }
	        }
	    }
	
		return fileList;
	}
	
	/**
	 * ファイルサイズを取得する
	 * @param pathAndfileName
	 * @return
	 */
	public long getFileSize(String pathAndfileName) {
		File file = new File(pathAndfileName);
		return file.length();
	}
	
	/**
	 * ファイルの最終更新日を返す(YYYYMMDD)
	 * @param path
	 * @param fileName
	 * @return
	 */
	public String getLastModifiedYyyyMmDd(String path, String fileName) {
	    File file = new File(path + "/" + fileName);
	    Date lastModifiedDate = new Date(file.lastModified());
	    var calendar = SystemConst.getCalendar();
		calendar.setTime(lastModifiedDate);
	    return DateUtil.getInstance().getYyyyMMddHHmmssSSS(calendar);
	}
	
	/**
	 * 指定ディレクトリ内のファイルの名称をListで返す（頭にファイルパスを付ける）
	 * @param path
	 * @return
	 */
	public List<String> getPathFileList(String path) {
		List<String> fileList = getFileOnlyNameList(path);
	
		List<String> pathFileList = new ArrayList<>();
	
		for(String file: fileList){
			pathFileList.add(path + "/" + file);
		}
	
		return pathFileList;
	}
	
	/**
	 * ファイル名から拡張子を返します。
	 * @param fileName ファイル名
	 * @return ファイルの拡張子
	 */
	public String getSuffix(String fileName) {
	    if (fileName == null)
	        return null;
	    int point = fileName.lastIndexOf(".");
	    if (point != -1) {
	        return fileName.substring(point + 1);
	    }
	    return fileName;
	}
	
	/**
	 * InputStreamをbyte配列に変換する
	 * @param is
	 * @return
	 */
	public byte[] inputStream2Bytes(InputStream is) {
		var b = new ByteArrayOutputStream();
		var os = new BufferedOutputStream(b);
		int c;
		try {
			while ((c = is.read()) != -1) {
				os.write(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return b.toByteArray();
	}
	
	/**
	 * ユーザディレクトリを返す
	 * @return
	 */
	public String getUserDir() {
		return System.getProperty("user.dir").replace("\\","/");
	}
	
	public void inputStream2File(InputStream inputStream, String outputFile) {
		File outputImageFile = new File(outputFile);
	
		OutputStream os = null;
		try {
			os = new BufferedOutputStream(new FileOutputStream(outputImageFile));
			int c;
			while ((c = inputStream.read()) != -1){
				os.write(c);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (os != null) {
				try {
					os.flush();
					os.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * ディレクトリ存在チェック
	 * @param path
	 * @return
	 */
	public Boolean isDirectory(String path) {
		File dir = new File(path);
		return dir.isDirectory();
	}
	
	public int isExistFile(String pathFileName) {
	    int rtnCd = 1;
	
	    File file = new File(pathFileName);
	
	    if(file.exists() && file.isFile()){
	    	rtnCd = 0;
	    }
	
	    return rtnCd;
	}
	
	/**
	 * ファイル存在チェック
	 * @param fileName
	 * @return
	 */
	public int isExistFile(String path, String fileName) {
	    return isExistFile(path + "/" + fileName);
	}
	
	/**
	 * リストの内容を指定ファイルに出力する
	 * @param list
	 * @param outFileName
	 * @return
	 */
	public int listWrite(List<String> list, String path, String fileName) {
		try{
			//ファイル名のインスタンス化
			File outputFile = new File(path + "/" + fileName);
	
			//入出力ストリームのインスタンス化
			FileWriter out = new FileWriter(outputFile);
	
			for(int i=0;i < list.size() ; i ++){
				out.write(list.get(i));
				out.write("\n");
			}
	
			out.close();
			return 0;
		}catch(IOException err){
			return 1;
		}
	}
	
	/**
	 * ディレクトリを作成する(親ディレクトリも含めてまとめて作成)
	 * @param dirName
	 * @return
	 */
	public int mkDirs(String dirName){
	    File file = new File(dirName);
	
	    if(file.mkdirs()){
	        return 0; //作成
	    }else{
	        return 1; //既に存在するので未作成
	    }
	}
	
	public int mkFile(String pathfileName) throws IOException{
		int rtnCd = 0;
	
		File file = new File(pathfileName);
		
		try {
			file.createNewFile();
		} catch (IOException e) {
			rtnCd = 1;
			throw e;
		}
		return rtnCd;
	}
	
	/**
	 * ファイル作成する
	 * @param dirName
	 * @return
	 * @throws IOException 
	 */
	public int mkFile(String path, String fileName) throws IOException{
		return  mkFile(path + "/" + fileName);
	}
	
	/**
	 * ファイルを移動する
	 * @param path1
	 * @param fileName1
	 * @param path2
	 * @param fileName2
	 * @return
	 */
	public boolean moveFile(String path1, String fileName1, String path2, String fileName2) {
		File file1 = new File(path1 + "/" + fileName1);	// 移動元
		File file2 = new File(path2 + "/" + fileName2);	// 移動先
		boolean ret = file1.renameTo(file2);
		return ret;
	}
	
	/**
	 * オブジェクトをファイルに出力する
	 * @param obj
	 * @param outputFile
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public int object2File(Object obj ,String outputFile) throws UnsupportedEncodingException {
	
		String jsonSt = JsonUtil.getInstance().makeJsonStringFromObject(obj);
	
		int rtnCd = string2File(jsonSt, outputFile);
	
		return rtnCd;
	}
	
	/**
	 * オブジェクトファイルを文字列ファイルに変換して出力する。
	 * （例：画像ファイルを文字列に変換してファイルに出力する。）
	 * @param objeFile
	 * @param stringFile
	 * @throws UnsupportedEncodingException
	 * @throws IOException
	 * @throws MessagingException
	 */
//	public int objFile2stringFile(String objeFile, String stringFile) throws UnsupportedEncodingException {
//	
//		String ddd = file2string(objeFile);
//	
//		return string2File(ddd, stringFile);
//	}
	
	public Boolean output(File file, OutputStream os) throws IOException {
		Boolean rtnBool = true;
	
		byte buffer[] = new byte[4096];
	    try (FileInputStream fis = new FileInputStream(file)) {
	        int size;
	        while ((size = fis.read(buffer)) != -1) {
	            os.write(buffer, 0, size);
	        }
	    } catch (IOException e) {
			throw e;
		}
	
	    return rtnBool;
	}
	
	/**
	 * 対象のファイルオブジェクトの削除を行う.<BR>
	 * ディレクトリの場合は再帰処理を行い、削除する。
	 *
	 * @param file ファイルオブジェクト
	 * @throws Exception
	 */
	public int recursiveDeleteFile(File file) {
	    // 存在しない場合は処理終了
	    if (!file.exists()) {
	        return 1;
	    }
	    // 対象がディレクトリの場合は再帰処理
	    if (file.isDirectory()) {
	        for (File child : file.listFiles()) {
	            recursiveDeleteFile(child);
	        }
	    }
	    // 対象がファイルもしくは配下が空のディレクトリの場合は削除する
	    file.delete();
	
	    return 0;
	}
	
	/**
	 * 対象のファイルオブジェクトの削除を行う.<BR>
	 * @param pathFile
	 * @return
	 */
	public int recursiveDeleteFile(String pathFile) {
		return recursiveDeleteFile(new File(pathFile));
	}
	
	/**
	 * 文字列をファイルに出力する
	 * @param b
	 * @param outputFile
	 * @throws UnsupportedEncodingException
	 * @return
	 */
	public int string2File(String data, String outputFile) throws UnsupportedEncodingException {
		int rtnCd = 0;
		try {
			byte[] byteData = data.getBytes("UTF-8");
			byte2File(byteData, outputFile);
		} catch (UnsupportedEncodingException e) {
			rtnCd = 1;
			throw e;
		}
		return rtnCd;
	}
	
	/**
	 * 文字列リストをファイルに出力する
	 * @param dataList
	 * @param outputFile
	 * @throws UnsupportedEncodingException
	 */
	public void stringListToFile(List<String> dataList, PrintWriter pw) {
		// ファイルへの書き込み
		for(String data: dataList){
	    	  StringBuffer str = new StringBuffer();
	    	  str.append(data);
	    	  str.append(System.getProperty("line.separator"));
	    	  pw.write(str.toString());
		}
	}
	
	/**
	 * 文字列リストをファイルに出力する
	 * @param dataList
	 * @param outputFile
	 * @throws Exception 
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 */
	public int stringListToFile(List<String> dataList, String _outputFile) throws Exception {
	
		int rtnCd = 0;
	
		try {
				    // ファイルオブジェクトの生成
		    File outputFile = new File(_outputFile);
	
		    // 出力ストリームの生成（文字コード指定）
		    var fo = new FileOutputStream(outputFile);
		    var ow = new OutputStreamWriter(fo, "UTF-8");
		    var pw = new PrintWriter(ow);
	
		    stringListToFile(dataList, pw);
	
		    pw.close();
		    ow.close();
		    fo.close();
	
		} catch(Exception e) {
			throw e;
		}
	
	    return rtnCd;
	}
	
	/**
	 * パスリスト取得
	 * 指定パスは以下のフォルダ名称を再帰的に取得してリストで返す
	 * @param inPath
	 * @return
	 */
	public List<String> getPathList(String inPath) {
		List<String> rtnList = new ArrayList<>();
		return dumpPath(rtnList, new File(inPath));
	}
	
	/**
	 * パスリスト取得
	 * @param list
	 * @param file
	 * @return
	 */
	private List<String> dumpPath(List<String> list, File file){
	
	    // ファイル一覧取得
	    File[] files = file.listFiles();
	
	    if(files == null){
	        return list;
	    }
	
	    for (File tmpFile : files) {
	    	// ディレクトリの場合
	        if(tmpFile.isDirectory()){
	            // 再帰呼び出し
	            list.add(tmpFile.getPath());
	            dumpPath(list, tmpFile);
	        }
	    }
	
	    return list;
	}
	
	public String file2string3(String fileName) {
		StringBuffer sb = new StringBuffer();
	
		List<String> list = getPathFileList(fileName);
	
		for(String data : list) {
			sb.append(data);
			sb.append(System.getProperty("line.separator"));
		}
	
		return sb.toString();
	}
	
	public String getSystemPath() {
	
		String targetPath = new File(".").getAbsoluteFile().getParent();
	
		String pathName = null;
	
		if (targetPath.indexOf("eclipse") < 0) {
			pathName = StringUtil.getInstance().getFirstIndexOfString(targetPath, Const.WORKSPACE_NAME);
		}else {
			pathName = StringUtil.getInstance().getFirstIndexOfString(targetPath, "eclipse");
		}
	
		return pathName;
	}
	
	public int copyFile2(String toCharCose, String lineCode, String pathFile1, String pathFile2) {
		List<String> list = fileUtil.fileRead(pathFile1);
	    return fileUtil.fileWrite(toCharCose, lineCode, list, pathFile2);
	}
	
	public boolean writeData(FileWriter filewriter, String data, String crlf) throws IOException {
		boolean rtnBool = true;
	    try {
			filewriter.write(data + crlf);
		    filewriter.flush();
		} catch (IOException e) {
			rtnBool = false;
			throw e;
		}
		return rtnBool;
	}
	
	/**
	 * 指定フォルダ下のファイルの名称をリストに詰めて返す。
	 * @param files
	 * @param pathAndZipFileName
	 */
	public List<String> folderToFileNameList(String path) {
		List<String> rtnList = new ArrayList<String>();
	
	    File dir = new File(path);
	
	    File[] files = dir.listFiles();
	
	
	    for (File file : files) {
	    	rtnList.add(file.getName());
	    }
	
		return rtnList;
	}
	
	//画像のサイズを取得する
	public Integer[] getJpegSize(String pathFile) {
	
		Integer rtnArray[] = {0, 0};
		// 画像格納クラス
		BufferedImage img = null;
	
		try {
			// inname(入力JPEG)を読み込んでimgにセット
			img = ImageIO.read( new File( pathFile ) );
		} catch (Exception e) {
			// inname(入力JPEG)の読み込みに失敗したときの処理
			 e.printStackTrace();
			 return rtnArray;
		}
		// 画像の横方向のピクセル数をwidthに代入
		rtnArray[0] = img.getWidth();
		// 画像の縦方向のピクセル数をheightに代入
		rtnArray[1] = img.getHeight();
		
		return rtnArray;
	}
}
