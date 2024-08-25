//package fm.businessLogic.common;
//
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//
//
//public class ExcelDesignUtil {
//
//	private static ExcelDesignUtil excelDesignUtil = new ExcelDesignUtil();
//
//	//外部から new させない為、プライベートのコンストラクタ―を表記している
//	private ExcelDesignUtil() {}
//	
//	public static ExcelDesignUtil getInstance() {
//		return excelDesignUtil;
//	}
//
//	public void updateExcelBook(File f, Workbook workbook, List<String> executeList){
//
//        for(var datas: executeList){
//
//        	var data = datas.split(",");
//
//            var sheetName = data[0].trim();
//            // 編集したいシート
//            var sheet = workbook.getSheet(sheetName);
//
//            if(sheet == null){
//                sheet = workbook.createSheet(sheetName);
//            }
//
//            var order = data[1];
//            var x = Integer.parseInt(data[2].trim());
//            var y = Integer.parseInt(data[3].trim());
//
//            var row = sheet.getRow(y);
//
//            if(row == null){
//                row = sheet.createRow(y);
//            }
//
//            var cell = row.getCell(x);
//
//            if(cell == null){
//                cell = row.createCell(x);
//            }
//
//            switch (order){
//              case Const.UPDATE:
//            	  var value = data[4].trim();
//                    cell.setCellValue(value);
//                break;
//              case Const.DELETE:
//                //--------列の追加は、まだ、未実装（列追加のメソッドは容易されていないもよう。行単位に１つづつ、シフトさせて対応）-----------------------------
//
//                if (x > 0){
//
//                }
//
//                if(y > 0){
//                    sheet.removeRow(row);
//                    // 上に1つ行をずらす
//                    var lastRow = sheet.getLastRowNum();
//                    if(y + 1 <= lastRow) {
//                        sheet.shiftRows(y + 1, lastRow, -1);
//                    }
//                }
//                break;
//              case Const.INSERT:
//                //--------列の追加は、まだ、未実装（列追加のメソッドは容易されていないもよう。行単位に１つづつ、シフトさせて対応）-----------------------------
//
//                var count = Integer.parseInt(data[4].trim());
//                var lastRowNum = sheet.getLastRowNum();
//                sheet.createRow(lastRowNum + count);
//                sheet.shiftRows(y, lastRowNum + 1, count);
//                break;
//            }
//        }
//        // 編集した内容の書き出し
//        try {
//            OutputStream os = new FileOutputStream(f);
//            workbook.write(os);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    public void makeWorkbook(String fileName) {
//        //新規ワークブックを作成する
//    	var workbook = upsertWorkbook(fileName);
//
//        try{
//        	var f = new File(fileName);
//        	var os = new FileOutputStream(f);
//            //作成したワークブックを保存する
//            workbook.write(os);
//            os.close();
//        }catch(Exception e){
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     * Workbookを取得する（無ければ新規に作成）
//     * @param fileName
//     * @return
//     */
//	public Workbook upsertWorkbook(String fileName){
//
//        int bool = FileUtil.getInstance().isExistFile(fileName);
//
//        Workbook workbook;
//
//        if(bool == 0){
//            workbook = getWorkbook(fileName);
//        }else{
//            workbook = newWorkbook(fileName);
//        }
//
//        return workbook;
//    }
//
//	public Workbook getWorkbook(String fileName){
//
//        Workbook book = null;
//
//        FileInputStream fi;
//        try {
//            fi   = new FileInputStream(fileName);
//            book = WorkbookFactory.create(fi);
//            fi.close();
//        } catch (IOException | EncryptedDocumentException e) {
//            e.printStackTrace();
//        } catch (org.apache.poi.openxml4j.exceptions.InvalidFormatException e) {
//			e.printStackTrace();
//		}
//
//        return book;
//    }
//
//    /**
//     * セルの内容を取得する。 内容が空か、文字列以外の場合は "" を返す。
//     * @param workbook
//     * @param sheetName
//     * @param x
//     * @param y
//     * @return
//     */
//	public String getStringValue(Workbook workbook, Sheet sheet, Integer x, Integer y){
//    	String rtnData = "";
//
//    	var row = sheet.getRow(y - 1);
//
//        Cell cell = null;
//
//        try{
//        	cell = row.getCell(x - 1);
//        	rtnData = cell.getStringCellValue();
//        }catch(Exception e){
//
//        }
//
//        return rtnData;
//    }
//
//	public Workbook newWorkbook(String fileName){
//        var array = fileName.split("\\.");
//
//        Workbook workbook;
//
//        if("xlsx".equals(array[array.length - 1])){
//            workbook =  new XSSFWorkbook();
//        }else{
//            workbook =  new HSSFWorkbook();
//        }
//        return workbook;
//    }
//
//	public void makeBarCorde(String message, String barCordeFileName){
//	    // 生成するバーコードの情報を宣言
//		var barcodeBean = new ITF14Bean();
//
//	    int dpi = 40;
//
//	    // バーコードを生成してファイルに出力する
//	    try {
//	      var file = new File(barCordeFileName);
//	      var outputStream = new FileOutputStream(file);
//	      var canvas = new BitmapCanvasProvider(outputStream, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
//	      barcodeBean.generateBarcode(canvas, message);
//	      canvas.finish();
//	      outputStream.close();
//	    }catch(IOException exception) {
//	      exception.printStackTrace();
//	    }
//	}
//
//	/**
//	 *
//	 * @param fileName
//	 * @return
//	 */
//	public Map<String, List<Integer>> getMojiretuMap(String fileName){
//		var rtnMap = new HashMap<String, List<Integer>>();
//
//		var sheet = getSheet(fileName, "data");
//
//		for(Row row: sheet){ // 全行をなめる
//	      	Integer y = row.getRowNum();
//	      	for(var cell: row){ // 全セルをなめる
//	      		String moji = cell.getStringCellValue();
//
//	      		//文字列が空でない かつ 文字列の左端が "$"の場合、文字列を取得する
//	      		if(!moji.isEmpty() && "$".equals(moji.substring(0, 1))){
//	      			Integer x = cell.getColumnIndex();
//	      			List<Integer> xyList = new ArrayList<>();
//	      			xyList.add(x);
//	      			xyList.add(y);
//	      			rtnMap.put(moji, xyList);
//	      		}
//	      	}
//		}
//		return rtnMap;
//	}
//
//
//	/**
//	 * シート取得
//	 * @param fileName
//	 * @return
//	 */
//	public Sheet getSheet(String fileName, String inSheetName){
//		Sheet sheet = null;
//
//	    var workbook = getWorkbook(fileName);
//
//         for(var s = 0; s < workbook.getNumberOfSheets(); ++s){ // 全シートをなめる(※)
//        	 sheet = workbook.getSheetAt(s);
//        	 var sheetName = sheet.getSheetName();
//        	 if (sheetName.contains(inSheetName)){
//        		 return sheet;
//        	 }
//         }
//
//		return sheet;
//	}
//
//	/**
//	 * 線を引く情報を取得する
//	 * @param sheet
//	 * @return
//	 */
//	public List<String> getStringList(Sheet sheet){
//
//		var rtnList = new ArrayList<String>();
//
//        for(var row: sheet){ // 全行をなめる
//        	var y = row.getRowNum();
//        	for(var cell: row){ // 全セルをなめる
//        		var cellStyle = cell.getCellStyle();
//        		var sb = new StringBuilder();
//        		var x = cell.getColumnIndex();
//        		sb.append(x + ",");
//        		sb.append(y + ",");
//        		// 線の太さ
//         		sb.append(cellStyle.getBorderTop()    + ","); //上
//        		sb.append(cellStyle.getBorderBottom() + ","); //下
//        		sb.append(cellStyle.getBorderLeft()   + ","); //左
//        		sb.append(cellStyle.getBorderRight()  + ","); //右
//        		// 線の色
//        		sb.append(PdfUtil.getInstance().getPdfColor(cellStyle.getTopBorderColor())    + ","); //上
//        		sb.append(PdfUtil.getInstance().getPdfColor(cellStyle.getBottomBorderColor()) + ","); //下
//        		sb.append(PdfUtil.getInstance().getPdfColor(cellStyle.getLeftBorderColor())   + ","); //左
//        		sb.append(PdfUtil.getInstance().getPdfColor(cellStyle.getRightBorderColor())  + ","); //右
//        		//背景色
//        		sb.append(cellStyle.getFillForegroundColor() + ",");
//        		//文字
//        		sb.append(cell);
//
//        		rtnList.add(sb.toString());
//        	}
//        }
//        return rtnList;
//	}
//
//	/**
//	 * 対角の座標（Object[0]=X、Object[1]=Y）を取得する
//	 * @param cellColor
//	 * @param cellBoolean
//	 * @param inX
//	 * @param inY
//	 * @return
//	 */
//	public List<Object> getXyList(Short [][]cellColor, Boolean [][]cellBoolean, Integer inX, Integer inY){
//
//		var maxX = cellColor.length;
//		var maxY = cellColor[0].length;
//
//		var color = cellColor[inX][inY];
//
//		while (true){
//			inX ++;
//			if(inX > maxX){
//				break;
//			}
//			if(cellColor[inX][inY] != color){
//				break;
//			}
//		}
//		inX --;
//		while (true){
//			inY ++;
//			if(inY > maxY){
//				break;
//			}
//			if(cellColor[inX][inY] != color){
//				break;
//			}
//		}
//		inY--;
//
//		return List.of(inX, inY);
//	}
//
//	public Integer[] getXY(Sheet sheet){
//		Integer[]xy = {0, 0};
//     	xy[0] = Short.toUnsignedInt(sheet.getRow(0).getLastCellNum());
//   		xy[1] = sheet.getLastRowNum() + 1;
//		return xy;
//	}
//
//
//	public String getCellColor(Workbook workbook, Sheet sheet, int x, int y) {
//		String rtnString = null;
//
//		var row = sheet.getRow(y - 1);
//
//        Cell cell = null;
//
//        try{
//        	cell = row.getCell(x - 1);
//
//        	if(cell == null) {
//        		return rtnString;
//        	}
//
//        	var cellStyle = cell.getCellStyle();
//    		var color = cellStyle.getFillForegroundColorColor();
//    		if (color != null) {
//    	    	if (color instanceof XSSFColor) {
//    	    		rtnString =  ((XSSFColor)color).getARGBHex();
//    	    	} else if (color instanceof HSSFColor) {
//    	    		if (! (color instanceof HSSFColor.AUTOMATIC)) {
//    	    			rtnString = ((HSSFColor)color).getHexString();
//    	    		}
//    		    }
//    	    }
//        }catch(Exception e){
//        	e.printStackTrace();
//        }
//        return rtnString;
//	}
//
//	/**
//	 * セルの情報を取得する
//	 * @param workbook
//	 * @param sheet
//	 * @param xStart
//	 * @param xEnd
//	 * @param y
//	 * @return
//	 */
//	public List<String> getCellInfoList(Workbook workbook, Sheet sheet, int xStart, int xEnd, int y){
//		var rtnList = new ArrayList<String>();
//		var st = "";
//    	for(var x = xStart; x <= xEnd; x ++ ) {
//    		st = getStringValue(workbook,  sheet, x, y);
//    		rtnList.add(st);
//    	}
//
//    	wrkbookClose(workbook);
//    	return rtnList;
//	}
//
//	/**
//	 * ワークブックを閉じる
//	 * @param workbook
//	 * @return
//	 */
//	public boolean wrkbookClose(Workbook workbook) {
//		try {
//			workbook.close();
//		} catch (IOException e) {
//			return false;
//		}
//		return true;
//	}
//
//	public Map<String, List<String>> getItemNameMap(Workbook workbook, Sheet sheet){
//		Map<String,List<String>> rtnMap = new HashMap<>();
//
//		List<Integer> keyValueIchiList = getKeyValueIchiList(workbook, sheet);
//
//		List<String> cellInfoList = getCellInfoList(workbook, sheet, keyValueIchiList.get(0), keyValueIchiList.get(3), 1);
//
//		List<String> keyList   = makeList(cellInfoList, keyValueIchiList.get(0) - 1, keyValueIchiList.get(1) - 1);
//		List<String> valueList = makeList(cellInfoList, keyValueIchiList.get(2) - 1, keyValueIchiList.get(3) - 1);
//
//		rtnMap.put("key", keyList);
//		rtnMap.put("value", valueList);
//
//		return rtnMap;
//	}
//
//	private List<String> makeList(List<String> list, int start, int end) {
//		List<String> trnList = new ArrayList<>();
//
//		for(int i = start; i <= end; i ++) {
//			trnList.add(list.get(i));
//		}
//
//		return trnList;
//	}
//
//	/**
//	 *
//	 * @param pathFile
//	 * @param sheetName
//	 * @return list(0):キーの開始位置、list(1):キーの終了位置、list(2):値の開始位置、list(3):値の終了位置
//	 */
//	public List<Integer> getKeyValueIchiList(Workbook workbook, Sheet sheet){
//		List<Integer> list = new ArrayList<>();
//
//		int x = 1;
//
//		list.add(x); //
//
//		while(true){
//			String collor = getCellColor(workbook, sheet, x, 1);
//			if(WorkFlowConst.COLOR_KEY.equals(collor)) {
//				x ++;
//				continue;
//			}
//
//			list.add(x - 1);
//			break;
//		}
//
//		x = list.get(1) + 1;
//
//		list.add(x);
//
//		while(true){
//			var collor = getCellColor(workbook, sheet, x, 1);
//			if(WorkFlowConst.COLOR_VALUE.equals(collor)) {
//				x ++;
//				continue;
//			}
//			list.add(x - 1);
//			break;
//		}
//
//    	wrkbookClose(workbook);
//
//		return list;
//
//	}
//
//	/**
//	 *
//	 * @param pathFile
//	 * @param sheetName
//	 * @return
//	 */
//	public Map<String, Object> getMap(Workbook workbook, Sheet sheet){
//
//		List<Integer> list = getKeyValueIchiList(workbook, sheet);
//
//		Map<String, Object> map = getMap(workbook, sheet, 2, list.get(0), list.get(1), list.get(2), list.get(3));
//
//		Map<String, List<String>> mmmm = getItemNameMap(workbook, sheet);
//
//		map.putAll(mmmm);
//
//		return map;
//	}
//
//	/**
//	 *
//	 * @param workbook
//	 * @param sheet
//	 * @param startRow
//	 * @param keyColumnS
//	 * @param keyColumnE
//	 * @param valueColumnS
//	 * @param valueColumnE
//	 * @return
//	 */
//	public Map<String, Object> getMap(Workbook workbook, Sheet sheet, int startRow, int keyColumnS, int keyColumnE, int valueColumnS, int valueColumnE){
//		Map<String, Object> rtnMap = new HashMap<>();
//
//		int row = startRow;
//
//		while (true) {
//			var keySb = new StringBuffer();
//
//			String st;
//
//			for(int x = keyColumnS; x <= keyColumnE; x++) {
//				st = getStringValue(workbook,  sheet, x, row);
//				if(st.length() > 0) {
//					keySb.append(st);
//					keySb.append("\t");
//				}
//			}
//
//			if(keySb.length() == 0) {
//				break ;
//			}
//
//			keySb.delete(keySb.length() - 1, keySb.length());
//
//			StringBuffer valueSb = new StringBuffer();;
//
//			for(int x = valueColumnS; x <= valueColumnE; x++) {
//				valueSb.append(getStringValue(workbook,  sheet, x, row));
//				valueSb.append("\t");
//			}
//			valueSb.delete(valueSb.length() - 1, valueSb.length());
//
//			rtnMap.put(keySb.toString(), valueSb.toString());
//
//			row ++;
//		}
//
//    	wrkbookClose(workbook);
//
//		return rtnMap;
//	}
//}
