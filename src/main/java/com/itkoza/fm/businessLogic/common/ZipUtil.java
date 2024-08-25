package com.itkoza.fm.businessLogic.common;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUtil {

	private static ZipUtil zipUtil = new ZipUtil();
	
	//外部から new させない為、プライベートのコンストラクタ―を表記している
	private ZipUtil() {}
	
	public static ZipUtil getInstancce() {
		return zipUtil;
	}

	/**
	 * フォルダ圧縮処理
	 * @param path
	 * @param pathAndFileName
	 * @return
	 */
//	public int compres(String path, String pathAndFileName) {
//		var file = new File(path);
//
//		var files = file.listFiles();
//
//		var zipFile = new File(pathAndFileName);
//
//			FileOutputStream fileOutputStream;
//
//			try {
//				fileOutputStream = new FileOutputStream(zipFile);
//
//				var bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
//
//				var zipOutputStream = new ZipOutputStream(bufferedOutputStream);
//
//				zipOutputStream.setLevel(5);
//
//				zipOutputStream.setEncoding("Shift_JIS");
//
//				var buf = new byte[1024];
//
//				InputStream bufferedInputStream = null;
//
//				for(var file2 : files){
//					var name = file2.getName();
//					var zipEntry = new ZipEntry(name);
//					zipOutputStream.putNextEntry(zipEntry);
//					var fileInputStream = new FileInputStream(file2);
//					bufferedInputStream = new BufferedInputStream(fileInputStream);
//
//					var len = 0;
//
//					while((len = bufferedInputStream.read(buf))!= -1){
//						zipOutputStream.write(buf, 0, len);
//					}
//					IOUtils.closeQuietly(bufferedInputStream);
//				}
//
//				zipOutputStream.close();
//
//				bufferedOutputStream.close();
//
//				fileOutputStream.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//				return -2;
//			}
//			return 0;
//	}

	/**
	 * ファイルディレクトリをZIP圧縮します
	 * @param zos
	 * @param files
	 * @throws Exception
	 */
	public void encodeZip(ZipOutputStream zos, File[] files) throws Exception {
		var buf = new byte[1024];

		for (var f : files) {
			if (f.isDirectory()) {
				encodeZip(zos, f.listFiles());
			} else {
				var ze = new ZipEntry(f.getPath().replace('\\', '/'));
				zos.putNextEntry(ze);
				var is = new BufferedInputStream(new FileInputStream(f));
				for (;;) {
					int len = is.read(buf);
					if (len < 0) break;
					zos.write(buf, 0, len);
				}
				is.close();
			}
		}
	}

    /**
     * 指定フォルダ下のファイルをZIPファイルにする。
     * @param files
     * @param pathAndZipFileName
     */
//	public int folderToZip(String path, String pathAndZipFileName) {
//
//    	var rtnCd = 0;
//
//    	var dir = new File(path);
//
//    	var files = dir.listFiles();
//
//        var zipFile = new File(pathAndZipFileName);
//
//    	try {
//    		var fi = new FileOutputStream(zipFile);
//
//    		var bf = new BufferedOutputStream(fi);
//
//    		var zos = new ZipOutputStream(bf);
//
//        	zos.setEncoding("MS932");
//
//            byte[] buf = new byte[1024];
//
//            InputStream is = null;
//
//            for (var file : files) {
//            	var entry = new ZipEntry(file.getName());
//                zos.putNextEntry(entry);
//                is = new BufferedInputStream(new FileInputStream(file));
//                int len = 0;
//                while ((len = is.read(buf)) != -1) {
//                    zos.write(buf, 0, len);
//                }
//            }
//
//           	is.close();
//
//            zos.close();
//
//            bf.close();
//
//            fi.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//            rtnCd = 1;
//        } finally {
//
//        }
//
//    	return rtnCd;
//    }

	/**
	 * ZIPファイルを解凍します。
	 *
	 * @param pathAndFileName ディレクトリ + ファイル
	 * @param outPath 出力ディレクトリ
	 * @throws IOException
	 */
//	public void decodeZip(String pathAndFileName, String outPath) throws IOException {
//		var zipFile = new ZipFile(pathAndFileName, "MS932");
//		var enum1 = zipFile.getEntries();
//
//		while (enum1.hasMoreElements()) {
//			var entry = (ZipEntry) enum1.nextElement();
//
//			if (entry.isDirectory()) {
//				new File(entry.getName()).mkdirs();
//			}else{
//				var parent = new File(outPath + "/" + entry.getName()).getParentFile();
//
//				if (parent != null) {
//					parent.mkdirs();
//				}
//				var out = new FileOutputStream(outPath + "/" + entry.getName());
//				var in = zipFile.getInputStream(entry);
//				var buf = new byte[1024];
//				var size = 0;
//				while ((size = in.read(buf)) != -1) {
//					out.write(buf, 0, size);
//				}
//				out.close();
//				in.close();
//			}
//		}
//		zipFile.close();
//	}
}