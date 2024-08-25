//package fm.businessLogic.common;
//
//import java.awt.Color;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.TreeMap;
//
//
//
//
//public class ExcelUtil {
//	
//	private static ExcelUtil excelUtil = new ExcelUtil();
//	
//	//外部から new させない為、プライベートのコンストラクタ―を表記している
//	private ExcelUtil() {}
//	
//	public static ExcelUtil getInstance() {
//		return excelUtil;
//	}
//	
//	/**
//	 *
//	 * @param f
//	 * @param workbook
//	 * @param executeList
//	 */
//	public void updateExcelBook(File f, Workbook workbook, List<String> executeList){
//
//        for(var datas: executeList){
//
//        	var data = datas.split(",");
//
//        	var sheetName = data[0].trim();
//            // 編集したいシート
//        	var sheet = workbook.getSheet(sheetName);
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
//            	var count = Integer.parseInt(data[4].trim());
//            	var lastRowNum = sheet.getLastRowNum();
//                sheet.createRow(lastRowNum + count);
//                sheet.shiftRows(y, lastRowNum + 1, count);
//                break;
//            }
//        }
//        // 編集した内容の書き出し
//        try {
//        	var os = new FileOutputStream(f);
//            workbook.write(os);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
//
//    /**
//     *
//     * @param pathFile
//     */
//	public void makeWorkbook(String pathFile) {
//        //新規ワークブックを作成する
//    	var workbook = upsertWorkbook(pathFile);
//
//        try{
//        	var f = new File(pathFile);
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
//     * @param pathFile
//     * @return
//     */
//	public Workbook upsertWorkbook(String pathFile){
//
//    	var bool = FileUtil.getInstance().isExistFile(pathFile);
//
//        Workbook workbook;
//
//        if(bool == 0){
//            workbook = getWorkbook(pathFile);
//        }else{
//            workbook = newWorkbook(pathFile);
//        }
//
//        return workbook;
//    }
//
//    /**
//     *
//     * @param pathFile
//     * @return
//     */
//	public Workbook getWorkbook(String pathFile){
//
//        Workbook book = null;
//
//        try {
//        	FileInputStream fi = new FileInputStream(pathFile);
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
//	public String getCellValue(Workbook workbook, Sheet sheet, Integer x, Integer y){
//    	String rtnData = "";
//
//        try{
//	        Row row = sheet.getRow(y - 1);
//        	Cell cell = row.getCell(x - 1);
//        	rtnData = cell.getStringCellValue();
//        }catch(Exception e){
//
//        }
//
//        return rtnData;
//    }
//
//    /**
//     *
//     * @param pathFile
//     * @return
//     */
//	public Workbook newWorkbook(String pathFile){
//        String []array = pathFile.split("\\.");
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
//    /**
//     * バーコード作成
//     * @param message
//     * @param barCordeFileName
//     */
//	public void makeBarcode(String message, String barCordeFileName){
//	    // 生成するバーコードの情報を宣言
//	    ITF14Bean barcodeBean = new ITF14Bean();
//
//	    int dpi = 40;
//
//	    // バーコードを生成してファイルに出力する
//	    try {
//		  File file = new File(barCordeFileName);
//	      FileOutputStream outputStream = new FileOutputStream(file);
//	      BitmapCanvasProvider canvas = new BitmapCanvasProvider(outputStream, "image/x-png", dpi, BufferedImage.TYPE_BYTE_BINARY, false, 0);
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
//	 * @param sheet
//	 * @return
//	 */
//	public Map<String, List<Integer>> getMojiretuMap(Sheet sheet){
//		var rtnMap = new HashMap<String, List<Integer>>();
//
//		for(Row row: sheet){ // 全行をなめる
//			Integer y = row.getRowNum();
//			for(Cell cell: row){ // 全セルをなめる
//				String moji = cell.getStringCellValue();
//
//				//文字列が空でない かつ 文字列の左端が "$"の場合、文字列を取得する
//				if(!moji.isEmpty() && "$".equals(moji.substring(0, 1))){
//					Integer x = cell.getColumnIndex();
//					List<Integer> xyList = new ArrayList<>();
//					xyList.add(x);
//					xyList.add(y);
//					rtnMap.put(moji, xyList);
//				}
//			}
//		}
//		return rtnMap;
//	}
//
//	/**
//	 * ブックの全シートを取得する
//	 * @param workbook
//	 * @return
//	 */
//	public Map<String, Sheet> makeSheetMap(Workbook workbook){
//		Map<String, Sheet> rtnMap = new HashMap<>();
//
//        for(int s = 0; s < workbook.getNumberOfSheets(); ++s){
//        	Sheet sheet = workbook.getSheetAt(s);
//        	String sheetName = sheet.getSheetName();
//        	rtnMap.put(sheetName, sheet);
//        }
//
//        return rtnMap;
//	}
//
//
//	/**
//	 * シート取得
//	 * @param pathFile
//	 * @return
//	 */
//	public Sheet getSheet(Workbook workbook, String inSheetName){
//		Map<String, Sheet> map = makeSheetMap(workbook);
//		return map.get(inSheetName);
//	}
//
//	public List<List<String>> getList(Workbook workbook, String inSheetName){
//		List rtnList = new ArrayList<Object>();
//
//		Sheet sheet = getSheet(workbook, inSheetName);
//        for(Row row: sheet){ // 全行をなめる
//        	
//        	var list = new ArrayList<String>();
//        	
//        	for(Cell cell: row){ // 全セルをなめる
//        		CellStyle cellStyle = cell.getCellStyle();
//        		Integer x = cell.getColumnIndex();
//	        	cell = row.getCell(x);
//	        	list.add(cell.toString());
//			}
//        	rtnList.add(list);
//		}
//        return rtnList;
//	}
//	
//	/**
//	 * 線を引く情報を取得する
//	 * @param sheet
//	 * @return
//	 */
//	public List<String> getStringList(Sheet sheet){
//
//		PdfUtil pdfUtil = PdfUtil.getInstance();
//		List<String> rtnList = new ArrayList<>();
//
//        for(Row row: sheet){ // 全行をなめる
//        	Integer y = row.getRowNum();
//        	for(Cell cell: row){ // 全セルをなめる
//        		CellStyle cellStyle = cell.getCellStyle();
//        		StringBuilder sb = new StringBuilder();
//        		Integer x = cell.getColumnIndex();
//        		sb.append(x + ",");
//        		sb.append(y + ",");
//        		// 線の太さ
//         		sb.append(cellStyle.getBorderTop()    + ","); //上
//        		sb.append(cellStyle.getBorderBottom() + ","); //下
//        		sb.append(cellStyle.getBorderLeft()   + ","); //左
//        		sb.append(cellStyle.getBorderRight()  + ","); //右
//        		// 線の色
//        		sb.append(pdfUtil.getPdfColor(cellStyle.getTopBorderColor())    + ","); //上
//        		sb.append(pdfUtil.getPdfColor(cellStyle.getBottomBorderColor()) + ","); //下
//        		sb.append(pdfUtil.getPdfColor(cellStyle.getLeftBorderColor())   + ","); //左
//        		sb.append(pdfUtil.getPdfColor(cellStyle.getRightBorderColor())  + ","); //右
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
//		Integer maxX = cellColor.length;
//		Integer maxY = cellColor[0].length;
//
//		Short color = cellColor[inX][inY];
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
//	/**
//	 *
//	 * @param sheet
//	 * @return
//	 */
//	public Integer[] getXY(Sheet sheet){
//		Integer[]xy = {0, 0};
//     	xy[0] = new Integer(sheet.getRow(0).getLastCellNum());
//   		xy[1] = sheet.getLastRowNum() + 1;
//		return xy;
//	}
//
//	/**
//	 *
//	 * @param workbook
//	 * @param sheet
//	 * @param x
//	 * @param y
//	 * @return
//	 */
//	public String getCellColor(Workbook workbook, Sheet sheet, int x, int y) {
//		String rtnString = null;
//
//        Row row = sheet.getRow(y - 1);
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
//    		CellStyle cellStyle = cell.getCellStyle();
//    		Color color = cellStyle.getFillForegroundColorColor();
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
//	 * ワークブックを閉じる
//	 * @param workbook
//	 * @return
//	 */
//	public boolean workbookClose(Workbook workbook) {
//		try {
//			workbook.close();
//		} catch (IOException e) {
//			return false;
//		}
//		return true;
//	}
//
//	/**
//	 * キー部取得
//	 * @param iMap
//	 * @param sheetName
//	 * @param y
//	 * @return
//	 */
//	public String getKey(Map<String,Map<String, Object>> iMap, String sheetName, int y) {
//		Map<String, Object> map  = iMap.get(sheetName);
//		return (String) map.get(Integer.valueOf(y) + Const.TAB + Const.ROW);
//	}
//
//	/**
//	 *
//	 * @param iMap
//	 * @param sheetName
//	 * @param y
//	 * @param x
//	 * @return
//	 */
//	public String getData(Map<String,Map<String, Object>> iMap, String sheetName, int y, int x) {
//		Map<String, Object> map  = iMap.get(sheetName);
//		return getData(map, y, x);
//	}
//
//
//	/**
//	 *
//	 * @param iMap
//	 * @param sheetName
//	 * @param key1
//	 * @param ichi
//	 * @return
//	 */
//	public String getData(Map<String,Map<String, Object>> iMap, String sheetName, String key1, int x) {
//		Map<String, Object> map = iMap.get(sheetName);
//		return getData(map, key1, x);
//	}
//
//	/**
//	 *
//	 * @param iMap
//	 * @param sheetName
//	 * @param key1
//	 * @param key2
//	 * @return
//	 */
//	public String getData(Map<String,Map<String, Object>> iMap, String sheetName, String key1, String key2) {
//		Map<String, Object> map = iMap.get(sheetName);
//		return getData(map, key1, key2);
//	}
//
//	/**
//	 *
//	 * @param iMap
//	 * @param y
//	 * @param x
//	 * @return
//	 */
//	public String getData(Map<String, Object> iMap, int y, int x) {
//		String key1 = (String) iMap.get(Integer.valueOf(y) + Const.TAB + Const.ROW);
//		return getData(iMap,  key1, x);
//	}
//
//	/**
//	 *
//	 * @param iMap
//	 * @param key1
//	 * @param ichi
//	 * @return
//	 */
//	public String getData(Map<String, Object> iMap, String key1, int x) {
//		String data[] = ((String) iMap.get(key1)).split(Const.TAB);
//		return data[x - 2];
//	}
//
//	/**
//	 *
//	 * @param iMap
//	 * @param key1
//	 * @param key2
//	 * @return
//	 */
//	public String getData(Map<String, Object> iMap, String key1, String key2) {
//		String key = Const.HEAD + Const.TAB + key2;
//		Integer ichi = (Integer) iMap.get(key);
//		String data[] = ((String) iMap.get(key1)).split(Const.TAB);
//		return data[ichi - 2];
//	}
//
//	/**
//	 *
//	 * @param workbook
//	 * @return
//	 */
//	public Map<String, Map<String, Object>> makeMap(Workbook workbook) {
//		Map<String, Map<String, Object>> rtnMap = new HashMap<>();
//
//		Map<String, Sheet> sheetMap = makeSheetMap(workbook);
//
//		for(String key: sheetMap.keySet()) {
//			Sheet sheet = sheetMap.get(key);
//
//			Map<String, Object> map = makeMap(workbook, sheet);
//
//			rtnMap.put(sheet.getSheetName(), map);
//		}
//
//		return rtnMap;
//	}
//
//	/**
//	 * ブックデータアクセス用Map作成
//	 * @param workbook
//	 * @param sheet
//	 * @return
//	 */
//	//-----------------------------------------------------------------
//	public Map<String, Object> makeMap(Workbook workbook, Sheet sheet) {
//		Map<String, Object> rtnMap = new TreeMap<>();
//
//		String sheetName = getSheetName(workbook);
//
//		String headKey = sheetName + Const.TAB + Const.HEAD;
//
//	   	//HEADキー情報作成 ------------------------------------------------------------
//		int x = 1;
//
//		StringBuffer val = new StringBuffer("");
//
//		String cel = ExcelDesignUtil.getInstance().getStringValue(workbook, sheet, x, 1);
//
//		while (cel.length() > 0) {
//    	   val.append(cel + Const.TAB);
//    	   x ++;
//    	   cel = ExcelDesignUtil.getInstance().getStringValue(workbook, sheet, x, 1);
//		}
//		val.delete(val.length()-1, val.length());
//
//		rtnMap.put(headKey, val.toString());
//
//		//HEADの位置情報作成 ----------------------------------------------------------
//		String headArray = (String) rtnMap.get(headKey);
//
//		String dataArray[] = headArray.split(Const.TAB);
//
//		for(int i = 0; i <  dataArray.length; i++ ) {
//			String key = Const.HEAD + Const.TAB + dataArray[i];
//			rtnMap.put(key, Integer.valueOf(i + 1));
//		}
//
//		//----------------------------------------------------------------------------
//       int koumokuSuu = x - 1;
//
//       int y = 2;
//
//       String key = ExcelDesignUtil.getInstance().getStringValue(workbook, sheet, 1, y);
//
//       while (key.length() > 0) {
//    	   StringBuffer value = new StringBuffer("");
//    	   for(int x2 = 2; x2 <= koumokuSuu; x2++) {
//    		   String celData = ExcelDesignUtil.getInstance().getStringValue(workbook, sheet, x2, y) + Const.TAB;
//    		   value.append(celData);
//    	   }
//    	   value.delete(value.length()-1, value.length());
//    	   rtnMap.put(key, value.toString());
//    	   y ++;
//    	   key = ExcelDesignUtil.getInstance().getStringValue(workbook, sheet, 1, y);
//       }
//
//       //LOW情報作成(キー=y+TAB+"R"  値=キー部------------------------------------------
//       y = 2;
//
//       key = ExcelDesignUtil.getInstance().getStringValue(workbook, sheet, 1, y);
//
//       while (key.length() > 0) {
//    	   rtnMap.put(Integer.valueOf(y) + Const.TAB + Const.ROW, key);
//    	   y ++;
//    	   key = ExcelDesignUtil.getInstance().getStringValue(workbook, sheet, 1, y);
//       }
//
//       return rtnMap;
//	}
//
//	/**
//	 * シート名を取得する
//	 * @param workbook
//	 * @return
//	 */
//	public String getSheetName(Workbook workbook) {
//		int no =workbook.getActiveSheetIndex();
//		return workbook.getSheetName(no);
//	}
//
//	public Map<String, List<String[]>> makeSheetArray(Workbook workbook) {
//		var rtnMap = new HashMap<String, List<String[]>>();
//		
//		Map<String, Sheet> sheetMap = makeSheetMap(workbook);
//		
//		sheetMap.forEach((sheetName, value) -> {
//			var arrayList = new ArrayList<String[]>();
//
//			List<List<String>> list = getList(workbook, sheetName);
//
//			for(List<String> dataList: list) {
//				String[] array = dataList.toArray(new String[dataList.size()]);
//				arrayList.add(array);
//			}
//
//			rtnMap.put(sheetName, arrayList);
//        });
//
//		return rtnMap;
//	}
//
//	public String getFromJudgementTable01(List<String[]> tableList01, String keys[]) {
//
//		var mmm2 = makeJudgementTable01(tableList01);
//
//		StringBuffer sb = new StringBuffer();
//		for(String data : keys) {
//			sb.append(data);
//			sb.append("\t");
//		}
//		sb.delete(sb.length() -1, sb.length());
//		
//		String result = (String) mmm2.get(sb.toString());
//		
//		return result;
//	}
//
//	private HashMap<String, String> makeJudgementTable01(List<String[]> list) {
//		var mmm2 = new HashMap<String, String>();
//		
//		for(String[] sArray: list) {
//			StringBuffer sb = new StringBuffer();
//			for (int i = 0; i < sArray.length; i++) {
//				if(i != sArray.length -1) {
//					sb.append(sArray[i]);
//					sb.append("\t");
//				} else {
//					sb.delete(sb.length() - 1, sb.length());
//					mmm2.put(sb.toString(), sArray[i]);
//				}
//			}
//		}
//		return mmm2;
//	}
//	
//	public String getFromJudgementTable02(List<String[]> tableList02 , String xKey , String yKey) {
//
//		var mmm2 = makeJudgementTable02(tableList02);
//
//		String aaa = new String(xKey + "\t" + yKey);
//		
//		String kotae = (String) mmm2.get(aaa);
//		
//		return kotae;
//	}
//	
//	private HashMap<String, String> makeJudgementTable02(List<String[]> list) {
//		var mmm2 = new HashMap<String, String>();
//
//		//横キー設定----------------------------------------------
//		var xKeyMap = new HashMap<Integer, String>();
//
//		String[] sArrayX = list.get(0);
//		for (int x = 1; x < sArrayX.length; x++) {
//			xKeyMap.put(x, sArrayX[x]);
//		}
//					
//		//縦キー設定----------------------------------------------
//		var yKeyMap = new HashMap<Integer, String>();
//
//		for(int y = 1; y < list.size(); y++) {
//			String[] sArrayY = list.get(y);
//			yKeyMap.put(y, sArrayY[0]);
//		}
//
//		//データ設定--------------------------------------------------------
//		for(int y = 1; y < list.size(); y++) {
//			String[] sArray = list.get(y);
//			for (int x = 1; x < sArray.length; x++) {
//				mmm2.put(xKeyMap.get(x) + "\t" + yKeyMap.get(y), sArray[x]);
//			}
//		}
//		return mmm2;
//	}
//
//	public String getFromJudgementTable03(List<String[]> tableList03, Integer xSuu, Integer ySuu) {
//		//横キー設定----------------------------------------------
//		var xKeyMap = new HashMap<Integer, String>();
//
//		String[] sArrayX = tableList03.get(0);
//		for (int x = 1; x < sArrayX.length; x++) {
//			xKeyMap.put(x, sArrayX[x]);
//		}
//					
//		//縦キー設定----------------------------------------------
//		var yKeyMap = new HashMap<Integer, String>();
//
//		for(int y = 1; y < tableList03.size(); y++) {
//			String[] sArrayY = tableList03.get(y);
//			yKeyMap.put(y, sArrayY[0]);
//		}
//		
//		//データ設定--------------------------------------------------------
//		for(int y = 1; y < tableList03.size(); y++) {
//			String[] sArray = tableList03.get(y);
//			for (int x = 1; x < sArray.length; x++) {
//				if(! judgement(xKeyMap.get(x), xSuu)) {
//					continue;
//				}
//				if(! judgement(yKeyMap.get(y), ySuu)) {
//					continue;
//				}
//				return sArray[x];
//			}
//		}				
//		return null;
//	}
//	
//	private boolean judgement(String omji, int suu) {
//		if(omji.contains("<=")) {
//			if(suu <= Integer.parseInt(omji.substring(omji.lastIndexOf("<=") + 2, omji.length()).trim())) {
//				return true;
//			}
//		} else if(omji.contains(">=")) {
//			if(suu >= Integer.parseInt(omji.substring(omji.lastIndexOf(">=") + 2, omji.length()).trim())) {
//				return true;
//			}
//		} else if(omji.contains(">")) {
//			if(suu > Integer.parseInt(omji.substring(omji.lastIndexOf(">") + 1, omji.length()).trim())) {
//				return true;
//			}
//		} else if(omji.contains("<")) {
//			if(suu < Integer.parseInt(omji.substring(omji.lastIndexOf("<") + 1, omji.length()).trim())) {
//				return true;
//			}
//		} else if(omji.contains("=")) {
//			if(suu == Integer.parseInt(omji.substring(omji.lastIndexOf("=") + 1, omji.length()).trim())) {
//				return true;
//			}
//		}
//
//		return false;
//	}
//}
