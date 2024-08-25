//package fm.businessLogic.common;
//
//import java.awt.Font;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.io.UnsupportedEncodingException;
//import java.math.BigDecimal;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//
//import javax.imageio.ImageIO;
//
////import org.apache.pdfbox.pdmodel.PDDocument;
////import org.apache.pdfbox.pdmodel.PDPage;
////import org.apache.pdfbox.pdmodel.PDPageContentStream;
////import org.apache.pdfbox.pdmodel.font.PDFont;
////import org.apache.pdfbox.pdmodel.font.PDType0Font;
////import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
////import org.apache.pdfbox.rendering.ImageType;
////import org.apache.pdfbox.rendering.PDFRenderer;
////import org.apache.poi.ss.usermodel.Cell;
////import org.apache.poi.ss.usermodel.CellStyle;
////import org.apache.poi.ss.usermodel.Row;
////import org.apache.poi.ss.usermodel.Sheet;
////
////import com.util.bean.pdf.PdfDataBean;
//
//import net.arnx.jsonic.JSON;
//
//public class PdfUtil {
//	
////	private static String COLOR_WHITE = "255255255"; //白
////	private static String COLOR_YELLOW = "255255000"; //黄色
//	private static String COLOR_PERPLE = "255000255"; //赤紫
//	private static String COLOR_RED = "255000000"; //赤
////	private static String COLOR_LIGHT_BLUE = "255255"; //水色
//	private static String COLOR_YELLOW_GREEN = "255000"; //黄緑
//	private static String COLOR_BLUE = "255"; //青
//	private static String COLOR_BLACK = "0"; //黒
//	private static String EXCEL_COLOR = "10:255000000," //赤
//			+ "13:255255000," //黄色
//			+ "17:255000," //黄緑
//			+ "30:255"; //青
//	private static Double PAGE_WIDTH = 610d;
//	private static Double PAGE_HIGHT = 790d;
//	private static PdfUtil pdfUtil =  new PdfUtil();
//	
//	//外部から new させない為、プライベートのコンストラクタ―を表記している
//	private PdfUtil() {}
//	
//	public static PdfUtil getInstance() {
//		return pdfUtil;
//	}
//	
//	public Map<String, String> excelColorMap;
//
////	public PdfDataBean makePdfDataBean() {
////		var imgPath = (String) SystemInfoUtil.getInstance().getValue(Const.PDF_PATH);;
////
////		PdfDataBean pdfDataBean = null;
////
////        //-------------------------------------------------------------------------------------------------------------
////		//エクセルファイルより 線データリスト（）と文字データリスト（）を作成する ここから
////		var pathFile = imgPath + "PDF data006.xls";
////
////		try {
////			var workbook = ExcelUtil.getInstance().getWorkbook(pathFile);
////			var sheet = ExcelUtil.getInstance().getSheet(workbook, "data");
////			pdfDataBean = decodeExcel(sheet);
////			workbook.close();
////		} catch (IOException e) {
////			e.printStackTrace();
////		}
////
////        //エクセルファイルより 線データリスト（）と文字データリスト（）を作成する ここまで
////
////        //背景塗りつぶし ここから --------------------------------------------------------------
////		var pdfFillBoxMapList = pdfDataBean.getPdfFillBoxMapList();
////		changeColor(pdfFillBoxMapList); //色変換
////        makePdfFillBoxBeanList(pdfFillBoxMapList);
////        pdfDataBean.setPdfFillBoxMapList(pdfFillBoxMapList);
////        //背景塗りつぶし ここまで --------------------------------------------------------------
////
////	    //--- 画像情報作成 ここから----------------------------------------------------------------------------
////        var pdfImageFileMapList = new ArrayList<Map<String, Object>>();
////
////        makePdfImageFileMapList(pdfImageFileMapList, imgPath);
////        pdfDataBean.setPdfImageFileMapList(pdfImageFileMapList);
////	    //--- 画像情報作成 ここまで----------------------------------------------------------------------------
////
////        //線を引く
////        pdfDataBean.getExcelLineList().add(Map.of("x1",0, "y1",0, "x2",pdfDataBean.getMaxX(), "y2",pdfDataBean.getMaxY(), "lineColor", COLOR_PERPLE, "lineWidth", 5 , "lineType","4f4f2f"));
////
////        return pdfDataBean;
////	}
//
//	/**
//	 *
//	 * @param pdfPath
//	 * @param pdfDataBean
//	 * @return
//	 */
//	public boolean pdfPrint(String pdfPath, PdfDataBean pdfDataBean) {
//
//		var rtnBool = true;
//
//		var excelMojiList = pdfDataBean.getExcelMojiList();
//
//		var excelMojiMap  = pdfDataBean.getExcelMojiMap();
//
//		var pDDocument = new PDDocument();
//
//        pdfDataBean.setpDDocument(pDDocument);
//
//		PDFont pDFont;
//
//		try {
//			pDFont = PDType0Font.load(pDDocument, new File("C:/Windows/Fonts/ARIALUNI.TTF"));
//
//			List<Object> opo = null;
//
//	        var objectList = new ArrayList<List<Object>>();
//
//	        List<Map<String, Object>> mojiList = null;
//
//	        var comDateTime = DateUtil.getInstance().getComDateTime("{0, date,yyyyMMddHHmmssSSS}");
//
//	        //１ページ目CommonUtil.makeObjectMap("x", o1, "y", o2, "fontSize", o3, "color", o4, "moji", o5);
//	        mojiList = new ArrayList<>();
//	        mojiList.add(Map.of("x",  1, "y", 1, "fontSize",12, "color","0"       , "moji", "11みみ11111" + comDateTime));
//	        mojiList.add(Map.of("x", 10, "y",10, "fontSize",12, "color","0"       , "moji","12ああああ1ttt111111111" + comDateTime));
//	        mojiList.add(Map.of("x", 20, "y",20, "fontSize",12, "color",COLOR_RED, "moji","９９９ととと111111111" + comDateTime));
//	        mojiList.add(Map.of("x", 45, "y",41, "fontSize",12, "color",COLOR_RED, "moji","９９９とと111111111" + comDateTime));
//	        mojiList.add(Map.of("x", 20, "y",93, "fontSize",12, "color",COLOR_RED, "moji","９９９とと11111111111" + comDateTime));
//	        mojiList.add(Map.of("x", 50, "y",80, "fontSize",28, "color",COLOR_RED, "moji","P"));
//	        mojiList.add(Map.of("x", 55, "y",93, "fontSize",28, "color",COLOR_RED, "moji","93"));
//	        mojiList.add(Map.of("x",  2, "y", 2, "fontSize",12, "color",COLOR_RED, "moji","い"));
//	        mojiList.add(Map.of("x", 26, "y",41, "fontSize",12, "color",COLOR_RED, "moji","こんにちは" + comDateTime));
//	        mojiList.addAll(excelMojiList);
//	        addPdfCellMojiBean(mojiList, excelMojiMap, "$B" , 12, COLOR_BLACK, "みなみさわれんみなみさわれん");
//
//	        opo = SystemUtil.getInstance().getObjArray(pDFont, mojiList);
//	        objectList.add(opo);
//
//	        //２ページ目
//	        mojiList = new ArrayList<>();
//	        mojiList.add(Map.of("x", 0, "y",   0, "fontSize", 22, "color","0", "moji", "21あああう####うう222222222" + comDateTime));
//	        mojiList.add(Map.of("x",60, "y", 130, "fontSize", 12, "color","0", "moji", "22ああえええええ3ああ2222222" + comDateTime));
//	        mojiList.addAll(excelMojiList);
//
//	        opo = SystemUtil.getInstance().getObjArray(pDFont, mojiList);
//	        objectList.add(opo);
//
//	        //３ページ目
//	        mojiList = new ArrayList<>();
//	        mojiList.add(Map.of("x", 0, "y",  0, "fontSize",  6, "color", COLOR_PERPLE, "moji", comDateTime + "31ああpopo####うああ333333333333333"));
//	        mojiList.add(Map.of("x",60, "y",130, "fontSize", 12, "color",           "0",  "moji", "32ああええpipiipえええ3あああああ3333333333333333"));
//	        mojiList.addAll(excelMojiList);
//	        addPdfCellMojiBean(mojiList, excelMojiMap, "$A", 29 , COLOR_BLACK, "みなみさわふみおですyo");
//
//	        opo = SystemUtil.getInstance().getObjArray(pDFont, mojiList);
//
//	        objectList.add(opo);
//
//	        pdfDataBean.setMojiDataList(objectList);
//
//	        //PDFプリント
//	        printPage(pdfDataBean);
//
//	        var name = pdfDataBean.getFileName();
//
//	        var pathFile = pdfPath + "\\" + name;
//
//	        pDDocument.save(pathFile);
//
//	        pDDocument.close();
//		} catch (IOException e) {
//			e.printStackTrace();
//			rtnBool = false;
//		}
//
//		return rtnBool;
//	}
//
//	/**
//	 *
//	 * @return
//	 */
//	private void makePdfImageFileMapList(List<Map<String, Object>> pdfImageFileBeanList, String imgPath){
//
//		var message = "1000560890123";
//		var barcodeFileName = imgPath + "barcode.png";
//	    ExcelUtil.getInstance().makeBarcode(message, barcodeFileName); // バーコード作成
//
//	    pdfImageFileBeanList.add(setImage(100, 200, barcodeFileName));
//
//	    pdfImageFileBeanList.add(setImage(120, 740, imgPath + "abc.jpg"));
//	}
//
//	/**
//	 *
//	 * @param mojiList
//	 * @param excelMojiMap
//	 * @param a
//	 * @param b
//	 * @param c
//	 * @param d
//	 */
//	private void addPdfCellMojiBean(List<Map<String, Object>> mojiList, Map<String, List<Integer>> excelMojiMap, String a, Integer b, String c, String d){
//		var pdfCellMojiBean = getObjArray(excelMojiMap, a, b , c, d);
//
//		if (pdfCellMojiBean != null){
//			mojiList.add(pdfCellMojiBean);
//		}
//	}
//
//	/**
//	 *
//	 * @param pdfFillBoxBeanList
//	 * @return
//	 */
//	private List<Map<String, Object>> makePdfFillBoxBeanList(List<Map<String, Object>> pdfFillBoxBeanList){
//
//		pdfFillBoxBeanList.add(setColor(50, 50, 60, 60, COLOR_YELLOW_GREEN));
//		pdfFillBoxBeanList.add(setColor(60, 60, 70, 70, "110010110"));
//		pdfFillBoxBeanList.add(setColor(30, 70, 40, 80, COLOR_BLUE));
//
//        return pdfFillBoxBeanList;
//	}
//
//	/**
//	 *
//	 * @param pDPageContentStream
//	 * @param pDDocument
//	 * @param pdfImageFileBeanList
//	 * @throws IOException
//	 */
//	public void printImage(PDPageContentStream pDPageContentStream , PDDocument pDDocument, List<Map<String, Object>> pdfImageFileBeanList) {
//
//		try {
//			//画像ファイル描画
//			for(var pdfImageFileBean: pdfImageFileBeanList){
//
//		       	Integer ix1 = ((BigDecimal) pdfImageFileBean.get("x")).intValue();
//		   		Integer iy1 = ((BigDecimal) pdfImageFileBean.get("y")).intValue();
//
//		   		Double dy = iy1.doubleValue();
//
//		   		dy = PAGE_HIGHT - dy;
//
//		   		var fileName = (String) pdfImageFileBean.get("fileName");
//		   		var pdImage = PDImageXObject.createFromFile(fileName, pDDocument);
//		        pDPageContentStream.drawImage(pdImage, ix1, dy.intValue());
//		        var scale = 1f;
//		        pDPageContentStream.drawImage(pdImage, ix1, dy.intValue(), pdImage.getWidth()*scale, pdImage.getHeight()*scale);
//	       }
//		}catch(Exception e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 *
//	 * @param pDPageContentStream
//	 * @param maxX
//	 * @param maxY
//	 * @param pdfFillBoxBeanList
//	 * @throws IOException
//	 */
//	public void printFillLine(PDPageContentStream pDPageContentStream , Integer maxX, Integer maxY, List<Map<String, Object>> pdfFillBoxBeanList) throws IOException{
//
//		for(var pdfFillBoxBean: pdfFillBoxBeanList){
//			String fillColor = (String) pdfFillBoxBean.get("fillColor");
//
//			Integer ix1 = ((BigDecimal) pdfFillBoxBean.get("x1")).intValue();
//			Integer iy1 = ((BigDecimal) pdfFillBoxBean.get("y1")).intValue();
//			Integer ix2 = ((BigDecimal) pdfFillBoxBean.get("x2")).intValue();
//			Integer iy2 = ((BigDecimal) pdfFillBoxBean.get("y2")).intValue();
//
//			ix1 = ix1 - 1;
//			iy1 = iy1 - 1;
//
//			var dx = PAGE_WIDTH / maxX;
//			var dy = PAGE_HIGHT / maxY;
//
//			iy1 = maxY - iy1;
//			iy2 = maxY - iy2;
//
//
//			Double dx1 = ix1.doubleValue() * dx;
//			Double dy1 = iy1 * dy + 1;
//			Double dx2 = ix2 * dx;
//			Double dy2 = iy2 * dy + 1;
//
//			var fx1 = dx1.floatValue();
//			var fx2 = dx2.floatValue() - dx1.floatValue();
//			var fy1 = dy1.floatValue();
//			var fy2 = dy2.floatValue() - dy1.floatValue();
//			// 塗りつぶし色指定
//			setNonStrokingColor(pDPageContentStream, fillColor);
//
//			pDPageContentStream.addRect(fx1, fy1, fx2, fy2);
//			// 塗りつぶし
//			pDPageContentStream.fill();
//		}
//	}
//
//	/**
//	 * 文字に色を付ける
//	 * 数値文字列９ケタを３ケタづつ区切る。例 "255255255"⇒ 255 255 255
//	 * @param pDPageContentStream
//	 * @param moji
//	 * @throws IOException
//	 */
//	public void setNonStrokingColor(PDPageContentStream pDPageContentStream, String moji) throws IOException{
//		var col = StringUtil.getInstance().changeFormat("000000000", Double.parseDouble(moji));
//		pDPageContentStream.setNonStrokingColor(
//			Integer.parseInt(col.substring(0, 3).trim()),
//			Integer.parseInt(col.substring(3, 6).trim()),
//			Integer.parseInt(col.substring(6, 9).trim())
//		);
//	}
//
//
//	public Double getPointX(Integer bunkatuX, Integer x){
//		var dx = PAGE_WIDTH / bunkatuX;
//		var dx1 = x.doubleValue() * dx;
//		return dx1;
//	}
//
//	public Double getPointY(Integer bunkatuY, Integer y){
//		var dy = PAGE_HIGHT / bunkatuY;
//		var dy1 = y.doubleValue() * dy - 15;
//		return dy1;
//	}
//
//	/**
//	 * ラインを引く
//	 * @param contents
//	 * @param objectList
//	 * @throws IOException
//	 */
//	public void printLine(PDPageContentStream pDPageContentStream, Integer maxX, Integer maxY, List<Map<String, Object>> objectList) throws IOException{
//		for(Map<String, Object> pdfLineBean: objectList){
//			Integer ix1 = ((BigDecimal) pdfLineBean.get("x1")).intValue();
//			Integer iy1 = ((BigDecimal) pdfLineBean.get("y1")).intValue();
//			Integer ix2 = ((BigDecimal) pdfLineBean.get("x2")).intValue();
//			Integer iy2 = ((BigDecimal) pdfLineBean.get("y2")).intValue();
//	        // 線の色
//	        String color = (String) pdfLineBean.get("lineColor");
//	        // 線の太さ
//			Integer line = ((BigDecimal) pdfLineBean.get("lineWidth")).intValue();
//	        // 線の種類
//	        String lineType  = (String) pdfLineBean.get("lineType");
//
//			Double dx = PAGE_WIDTH / maxX;
//			Double dy = PAGE_HIGHT / maxY;
//
//			iy1 = maxY - iy1;
//			iy2 = maxY - iy2;
//
//			Double dx1 = ix1.doubleValue() * dx;
//			Double dy1 = iy1 * dy + 1;
//			Double dx2 = ix2 * dx;
//			Double dy2 = iy2 * dy + 1;
//
//			//線の太さを設定
//			pDPageContentStream.setLineWidth(line / 2);
//			// ペンの位置を移動
//			pDPageContentStream.moveTo(dx1.floatValue(), dy1.floatValue());
//	        // ラインの終端座標を指定
//			pDPageContentStream.lineTo(dx2.floatValue(), dy2.floatValue());
//		    // 線のパターン指定("0f"=実線  "4f4f2f"=破線 )
//			setLineType(pDPageContentStream, lineType);
//			// 線の色指定
//			setStrokingColor(pDPageContentStream, color);
//	        // 線を描画
//			pDPageContentStream.stroke();
//		}
//	}
//
//	/**
//	 * PDFページ作成
//	 * @param mojiDataList
//	 * @param lineDataList
//	 * @param maxX
//	 * @param maxY
//	 * @throws IOException
//	 */
//	public void printPage(PdfDataBean pdfDataBean) throws IOException{
//
//		List<Map<String, Object>> lineDataList = pdfDataBean.getExcelLineList();
//        List<Map<String, Object>> pdfImageFileBeanList = pdfDataBean.getPdfImageFileMapList();
//        List<Map<String, Object>> pdfFillBoxBeanList = pdfDataBean.getPdfFillBoxMapList();
//
//        PDDocument pDDocument = pdfDataBean.getpDDocument();
//        Integer maxX = pdfDataBean.getMaxX();
//        Integer maxY = pdfDataBean.getMaxY();
//
//        List<List<Object>> mojiDataList = pdfDataBean.getMojiDataList();
//
//
//    	for(List<Object> obj: mojiDataList){
//        	PDPage pDPage = new PDPage();
//            PDFont pDFont = (PDFont) obj.get(0);
//            List<Map<String, Object>> dataList = (List<Map<String, Object>>) obj.get(1);
//
//            pDDocument.addPage(pDPage);
//
//            PDPageContentStream pDPageContentStream = new PDPageContentStream(pDDocument, pDPage);
//
//	        printFillLine(pDPageContentStream , maxX, maxY, pdfFillBoxBeanList);
//	        //画像ファイル描画
//	        printImage(pDPageContentStream , pDDocument, pdfImageFileBeanList);
//	        //罫線描画
//	        printLine(pDPageContentStream, maxX, maxY, lineDataList);
//
//            pDPageContentStream.beginText();
//            //文字描画
//            for(Map<String, Object> pdfCellMojiBean: dataList){
//
//            	// 偶にBigDecimal型が入っていてClassCastExceptionが発生するので対応した
//            	Integer x     = changeInteger(pdfCellMojiBean, "x");
//            	Integer y     = changeInteger(pdfCellMojiBean, "y");
//                Integer size  = changeInteger(pdfCellMojiBean, "fontSize");
//                String  moji  = (String) pdfCellMojiBean.get("moji");
//                String  color = (String) pdfCellMojiBean.get("color");
//
//                y =  maxY - y + 1;
//
//                Double pointX = getPointX(maxX, x);
//                Double pointY = getPointY(maxY, y);
//
//    	        pDPageContentStream.setFont(pDFont,size);
//    	        pDPageContentStream.setTextTranslation(pointX, pointY);
//
//    	        setNonStrokingColor(pDPageContentStream, color);
//
//    	        pDPageContentStream.showText(moji);
//            }
//
//	        pDPageContentStream.endText();
//
//	        pDPageContentStream.close();
//    	}
//	}
//
//	/**
//	 * 線を引く
//	 * ライン情報文字列長が６でない場合、実線になる
//	 * @param pDPageContentStream
//	 * @param lineType
//	 * @throws IOException
//	 */
//	private void setLineType(PDPageContentStream pDPageContentStream, String lineType) throws IOException{
//		if(lineType.length() == 6){
//			Float f1 = new Float(lineType.substring(0, 2));
//			Float f2 = new Float(lineType.substring(2, 4));
//			Float f3 = new Float(lineType.substring(4, 6));
//			//破線を引く
//			pDPageContentStream.setLineDashPattern(new float[]{f1, f2}, f3);
//		}
//	}
//
//	/**
//	 * 線に色を付ける
//	 * 数値文字列９ケタを３ケタづつ区切る。例 "255255255"⇒ 255 255 255
//	 * @param pDPageContentStream
//	 * @param col
//	 * @throws IOException
//	 */
//	private void setStrokingColor(PDPageContentStream pDPageContentStream, String moji) throws IOException{
//		String col = StringUtil.getInstance().changeFormat("000000000", Double.parseDouble(moji));
//		pDPageContentStream.setStrokingColor(
//			Integer.parseInt(col.substring(0, 3).trim()),
//			Integer.parseInt(col.substring(3, 6).trim()),
//			Integer.parseInt(col.substring(6, 9).trim())
//		);
//	}
//
//	/**
//	 * カラーコード変換
//	 *
//	 * @param excelColor
//	 * @return
//	 */
//	public String getPdfColor(Short excelColor){
//		if(excelColorMap == null){
//			excelColorMap = new HashMap<>();
//			String []colorArray = EXCEL_COLOR.split(",");
//			for(String colorData: colorArray){
//				String []color = colorData.split(":");
//				excelColorMap.put(color[0], color[1]);
//			}
//		}
//
//		String color = (String) excelColorMap.get(excelColor.toString());
//
//		if(color == null){
//			return COLOR_BLACK;
//		}
//		return color;
//	}
//
//	public List<Map<String, Object>> getObjectArrayList(Sheet sheet){
//
//		List<Map<String, Object>> objectList = new ArrayList<>();
//
//		List<String> stringList = ExcelUtil.getInstance().getStringList(sheet);
//
//		Map<String, String> xLineMap = new HashMap<>();
//		Map<String, String> yLineMap = new HashMap<>();
//
//		for(String string: stringList){
//			String data[] = string.split(",");
//
//			//-------------------------------------------------------------
//			String yValue = data[1];
//			String xData = xLineMap.get(yValue);
//
//			if(xData == null){
//				xLineMap.put(yValue, string);
//			}else{
//				xLineMap.put(yValue, xData + ":" + string);
//			}
//
//			//-------------------------------------------------------------
//			String xValue = data[0];
//			String yData = yLineMap.get(xValue);
//
//			if(yData == null){
//				yLineMap.put(xValue, string);
//			}else{
//				yLineMap.put(xValue, yData + ":" + string);
//			}
//		}
//
//		//---エクセルシートより、ページの最大行数、最大列数を取得する------------------------
//		Integer[]xy = ExcelUtil.getInstance().getXY(sheet);
//
//		Integer maxX = xy[0];
//		Integer maxY = xy[1];
//
//		//---横線描画情報取得----------------------------------------------------------
//		for(Integer y = 0; y < maxY; y ++){
//			String data = xLineMap.get(y.toString());
//			if(data != null){
//				//---横線描画情報取得（上）
//				List<Map<String, Object>> ol1 = getLineDataX(data);
//				objectList.addAll(ol1);
//				//---横線描画情報取得（下）
//				List<Map<String, Object>> ol2 = getLineDataX2(data);
//				objectList.addAll(ol2);
//			}
//		}
//
//		//---縦線描画情報取得---------------------------------------------------------
//		for(Integer x = 0; x < maxX; x ++){
//			String data = yLineMap.get(x.toString());
//			if(data != null){
//				//---縦線描画情報取得（左）
//				List<Map<String, Object>> ol1 = getLineDataY(data);
//				objectList.addAll(ol1);
//				//---縦線描画情報取得（右）
//				List<Map<String, Object>> ol2 = getLineDataY2(data);
//				objectList.addAll(ol2);
//			}
//		}
//
//		return objectList;
//	}
//
//	/**
//	 * 横線（上）描画情報編集
//	 * @param data
//	 * @return
//	 */
//	public List<Map<String, Object>> getLineDataX(String data){
//		List<Map<String, Object>> objectList = new ArrayList<>();
//
//		String []dataArray = data.split(":");
//
//		Integer start = null;
//
//		Integer x = null;
//		Integer y = null;
//
//		Integer beforX     = null;
//		String  beforColor = null;
//		Integer beforLine  = null;
//
//		Map<String, Object> pdfLineBean = null;
//
//		for(String dd: dataArray){
//			String []ee = dd.split(",");
//			x = Integer.parseInt(ee[0]);
//			y = Integer.parseInt(ee[1]);
//
//			Integer line  = Integer.parseInt(ee[2]); //上
//			String color = ee[6]; //上線の色
//
//			if(0 == line){ // 線の太さが 0 の場合（線が途切れた場合）
//				if (start != null) { //線が引かれていた場合
//					pdfLineBean = Map.of("x1",start, "y1",y, "x2",beforX + 1, "y2", y, "lineColor", beforColor, "lineWidth",beforLine, "lineType","0f");
//					objectList.add(pdfLineBean);
//					start = null;
//				}
//			}else{
//				if (start == null) {
//					start = x; // 線引きスタート
//				} else if (beforX != null && (x > beforX + 1 || !color.equals(beforColor) || line != beforLine)){
//					pdfLineBean = Map.of("x1",start, "y1",y, "x2",beforX + 1, "y2", y, "lineColor", beforColor, "lineWidth", beforLine, "lineType","0f");
//					objectList.add(pdfLineBean);
//					start = x;
//				}
//			}
//			beforX     = x;
//			beforColor = color;
//			beforLine  = line;
//		}
//		if (start != null) {
//			pdfLineBean = Map.of("x1",start, "y1", y, "x2", beforX + 1, "y2", y, "lineColor", beforColor, "lineWidth", beforLine, "lineType","0f");
//			objectList.add(pdfLineBean);
//		}
//
//		return objectList;
//	}
//
//	/**
//	 * 横線（下）描画情報編集
//	 * @param data
//	 * @return
//	 */
//	private List<Map<String, Object>> getLineDataX2(String data){
//		List<Map<String, Object>> objectList = new ArrayList<>();
//
//		String []dataArray = data.split(":");
//
//		Integer start = null;
//
//		Integer x = null;
//		Integer y = null;
//
//		Integer beforX     = null;
//		String  beforColor = null;
//		Integer beforLine  = null;
//
//		Map<String, Object> pdfLineBean = null;
//
//		for(String dd: dataArray){
//			String []ee = dd.split(",");
//			x = Integer.parseInt(ee[0]);
//			y = Integer.parseInt(ee[1]);
//
//			Integer line  = Integer.parseInt(ee[3]); //下
//			String  color = ee[7]; //下線の色
//
//			if(0 == line){ // ライン無しの場合
//				if (start != null) {
//					pdfLineBean = Map.of("x1",start, "y1",y + 1, "x2",beforX + 1, "y2",y + 1, "lineColor",beforColor, "lineWidth",beforLine, "lineType", "0f");
//					objectList.add(pdfLineBean);
//					start = null;
//				}
//			}else{
//				if (start == null) {
//					start = x;
//				} else if (beforX != null && (x > beforX + 1 || !color.equals(beforColor) || line != beforLine) ){
//					pdfLineBean = Map.of("x1",start, "y1",y + 1, "x2",beforX + 1, "y2",y + 1, "lineColor", beforColor, "lineWidth", beforLine, "lineType","0f");
//					objectList.add(pdfLineBean);
//					start = x;
//				}
//			}
//			beforX = x;
//			beforColor = color;
//			beforLine  = line;
//		}
//		if (start != null) {
//			pdfLineBean = Map.of("x1",start, "y1",y + 1, "x2",beforX + 1, "y2",y + 1, "lineColor", beforColor, "lineWidth", beforLine, "lineType","0f");
//			objectList.add(pdfLineBean);
//		}
//
//		return objectList;
//	}
//
//	/**
//	 * 縦線（左）描画情報編集
//	 * @param data
//	 * @return
//	 */
//	private List<Map<String, Object>> getLineDataY(String data){
//		List<Map<String, Object>> objectList = new ArrayList<>();
//
//		String []dataArray = data.split(":");
//
//		Integer start = null;
//
//		Integer x = null;
//		Integer y = null;
//
//		Integer beforY     = null;
//		String  beforColor = null;
//		Integer beforLine  = null;
//
//		Map<String, Object> pdfLineBean = null;
//
//		for(String dd: dataArray){
//			String []ee = dd.split(",");
//			x = Integer.parseInt(ee[0]);
//			y = Integer.parseInt(ee[1]);
//
//			Integer line  = Integer.parseInt(ee[4]); //左
//			String  color = ee[8]; //左線の色
//
//			if(0 == line){ // ライン無しの場合
//				if (start != null) {
//					pdfLineBean = Map.of("x1",x, "y1",start, "x2",x, "y2",beforY + 1, "lineColor", beforColor, "lineWidth", beforLine, "lineType","0f");
//					objectList.add(pdfLineBean);
//					start = null;
//				}
//			}else{
//				if (start == null) {
//					start = y;
//				} else if (beforY != null && (y > beforY + 1 || !color.equals(beforColor) || line != beforLine)){
//					pdfLineBean = Map.of("x1",x, "y1",start, "x2",x, "y2",beforY + 1, "lineColor", beforColor, "lineWidth", beforLine, "lineType","0f");
//					objectList.add(pdfLineBean);
//					start = y;
//				}
//			}
//			beforY = y;
//			beforColor = color;
//			beforLine  = line;
//		}
//
//		if (start != null) {
//			pdfLineBean = Map.of("x1",x, "y1",start, "x2",x, "y2",beforY + 1, "lineColor", beforColor, "lineWidth", beforLine, "lineType","0f");
//			objectList.add(pdfLineBean);
//		}
//
//		return objectList;
//	}
//
//	/**
//	 * 縦線（左）描画情報編集
//	 * @param data
//	 * @return
//	 */
//	private List<Map<String, Object>> getLineDataY2(String data){
//		List<Map<String, Object>> objectList = new ArrayList<>();
//
//		String []dataArray = data.split(":");
//
//		Integer start = null;
//
//		Integer x = null;
//		Integer y = null;
//
//		Integer beforY     = null;
//		String  beforColor = null;
//		Integer beforLine  = null;
//
//		Map<String, Object> pdfLineBean = null;
//
//		for(String dd: dataArray){
//			String []ee = dd.split(",");
//			x = Integer.parseInt(ee[0]);
//			y = Integer.parseInt(ee[1]);
//
//			Integer line  = Integer.parseInt(ee[5]); //右
//
//			String  color = ee[9]; //右線の色
//
//			if(0 == line){ //（線無しの場合） 線の太さ = 0
//				if (start != null) {
//					pdfLineBean = Map.of("x1", x + 1, "y1",start, "x2",x + 1, "y2",beforY + 1, "lineColor", beforColor, "lineWidth", beforLine, "lineType","0f");
//					objectList.add(pdfLineBean);
//					start = null;
//				}
//			}else{
//				if (start == null) {
//					start = y;
//				} else if (beforY != null && (y > beforY + 1 || !color.equals(beforColor) || line != beforLine)){
//					pdfLineBean = Map.of("x1",x + 1, "y1",start, "x2",x + 1, "y2",beforY + 1, "lineColor", beforColor, "lineWidth",beforLine, "lineType", "0f");
//					objectList.add(pdfLineBean);
//					start = y;
//				}
//			}
//			beforY = y;
//			beforColor = color;
//			beforLine  = line;
//		}
//
//		if (start != null) {
//			pdfLineBean = Map.of("x1",x + 1, "y1",start, "x2",x + 1, "y2",beforY + 1, "lineColor", beforColor, "lineWidth", beforLine, "lineType", "0f");
//			objectList.add(pdfLineBean);
//		}
//
//		return objectList;
//	}
//
//	/**
//	 *
//	 * @param fileName
//	 * @return
//	 */
//	public PdfDataBean decodeExcel(Sheet sheet){
//		Integer []xy = ExcelUtil.getInstance().getXY(sheet);
//		Integer maxX = xy[0];
//		Integer maxY = xy[1];
//		List<Map<String, Object>> lineDataList = getObjectArrayList(sheet); //エクセルファイルで引いた線の情報を取得する
//
//		//---エクセルシート内の文字を取得してPDFに出力する
//		Map<String, List<Integer>> mojiretuMap = ExcelUtil.getInstance().getMojiretuMap(sheet);
//
//		List<Map<String, Object>> pdfFillBoxBeanList = getColorMap(sheet, maxX, maxY);
//
//		List<Map<String, Object>> excelMojiList = getExcelMojiList(sheet);
//
//		return new PdfDataBean()
//			.setExcelMojiList(excelMojiList)
//			.setExcelLineList(lineDataList)
//			.setMaxX(maxX)
//			.setMaxY(maxY)
//			.setExcelMojiMap(mojiretuMap)
//			.setPdfFillBoxMapList(pdfFillBoxBeanList);
//	}
//
//	/**
//	 *
//	 * @param mojiretuMap
//	 * @param key
//	 * @param moji
//	 * @return
//	 */
//	public Map<String, Object> getObjArray(Map<String, List<Integer>> mojiretuMap, String key , Integer mojiSize, String color, String moji){
//		List<Integer> integerList = mojiretuMap.get(key);
//		if(integerList == null){
//			return null;
//		}
//
//		return Map.of("x", integerList.get(0), "y", integerList.get(1), "mojiSize", mojiSize, "color", color, "moji",moji);
//	}
//
//	/**
//	 *
//	 * @param sheet
//	 * @param maxX
//	 * @param maxY
//	 * @return
//	 */
//	public List<Map<String, Object>> getColorMap(Sheet sheet, Integer maxX, Integer maxY){
//
//		List<Map<String, Object>> mapList = new ArrayList<>();
//
//		Short [][]cellColor = new Short[maxX][maxY];
//
//		Boolean [][]cellBoolean = new Boolean[maxX][maxY];
//
//       //-------------------------------------------------------------
//       for(Integer x = 0; x < maxX; x ++){
//           for(Integer y = 0; y < maxY; y ++){
//           	cellBoolean[x][y] = false;
//           	cellColor[x][y] = 64;
//           }
//       }
//       //-------------------------------------------------------------
//       for(Row row: sheet){ // 全行をなめる
//       	Integer y = row.getRowNum();
//       	for(Cell cell: row){ // 全セルをなめる
//       		Integer x = cell.getColumnIndex();
//       		Short color = cell.getCellStyle().getFillForegroundColor();
//
//       		if(color != 64){
//       			cellColor[x][y] = color;
//       		}
//       	}
//       }
//       //-------------------------------------------------------------
//       ExcelUtil eu = ExcelUtil.getInstance();
//
//       for(Integer x = 0; x < maxX; x ++){
//           for(Integer y = 0; y < maxY; y ++){
//           	if(cellBoolean[x][y] == false){
//
//           		Short fillColor = cellColor[x][y];
//
//               	if(fillColor != 64){
//               		List<Object> objectList = eu.getXyList(cellColor, cellBoolean,  x, y);
//
//               		Integer endX = (Integer) objectList.get(0);
//               		Integer endY = (Integer) objectList.get(1);
//
//               		for (Integer px = x; px <= endX; px ++){
//                   		for (Integer py = y; py <= endY; py ++){
//                   			cellBoolean[px][py] = true;
//                   		}
//               		}
//
//               		mapList.add(Map.of("x1", x, "y1", y, "x2", endX, "y2", endY, "fillColor", fillColor.toString()));
//               	}
//               	cellBoolean[x][y] = true;
//           	}
//           }
//       }
//
//       return mapList;
//	}
//
//	/**
//	 *
//	 * @param fileName
//	 * @return
//	 */
//	public List<Map<String, Object>> getExcelMojiList(Sheet sheet){
//
//		List<Map<String, Object>> objectList = new ArrayList<>();
//
//       for(Row row: sheet){ // 全行をなめる
//       	Integer y = row.getRowNum();
//       	for(Cell cell: row){ // 全セルをなめる
//       		CellStyle cellStyle = cell.getCellStyle();
//       		Font font = cell.getSheet().getWorkbook().getFontAt(cellStyle.getFontIndex());
//
//       		String moji = cell.getStringCellValue();
//
//       		//文字列が空でない かつ 文字列の左端が "$"でない場合、文字列を取得する
//       		if(!moji.isEmpty() && !"$".equals(moji.substring(0, 1))){
//      			Integer fontSize = new Integer(font.getFontHeight() / 22);
//
//       			Map<String, Object> pdfCellMojiBean = Map.of("x", cell.getColumnIndex(), "y",y, "fontSize", fontSize, "color", "0", "moji", moji);
//           		objectList.add(pdfCellMojiBean);
//       		}
//       	}
//       }
//       return objectList;
//	}
//
//	/**
//	 * 色変換
//	 * @param pdfFillBoxMapList
//	 * @throws IOException
//	 */
//	public void changeColor(List<Map<String, Object>> pdfFillBoxMapList){
//		for(Map<String, Object> pdfFillBoxBean: pdfFillBoxMapList){
//			String fillColor = (String) pdfFillBoxBean.get("fillColor");
//			fillColor = getPdfColor(Short.valueOf(fillColor));
//			if(fillColor != null){
//				pdfFillBoxBean.put("fillColor", fillColor);
//			}
//		}
//	}
//
//	/**
//	 * オブジェクト配列をXMLファイルに出力する
//	 * @param fileName
//	 * @param lineDataList
//	 * @param excelMojiList
//	 * @param maxX
//	 * @param maxY
//	 */
//	public void encodeXml(String fileName, PdfDataBean pdfDataBean){
//
//		Object beanArray[] = {pdfDataBean};
//
//		try {
//			XmlUtil.getInstancce().encodeBeanArray(beanArray, fileName);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//	}
//
//	/**
//	 *
//	 * @param fileName
//	 * @param jsonPdfDataBean
//	 * @throws UnsupportedEncodingException 
//	 */
//	public void encodeJson(String fileName, PdfDataBean jsonPdfDataBean) throws UnsupportedEncodingException{
////				String encodeData = JSON.encode(jsonPdfDataBean);		//整形しない場合
//		String encodeData = JSON.encode(jsonPdfDataBean, true);	//整形する場合
//
//		FileUtil.getInstance().string2File(encodeData, fileName);
//	}
//
//	/**
//	 * XMLファイルをオブジェクト配列に復元する
//	 * @param fileName
//	 * @return
//	 */
//	public PdfDataBean decodeXml(String fileName){
//		Object beanArray[] = null;
//		try {
//			beanArray = XmlUtil.getInstancce().decodeBeanArray(fileName);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		PdfDataBean pdfDataBean = (PdfDataBean) beanArray[0];
//		return pdfDataBean;
//	}
//
//	/**
//	 *
//	 * @param fileName
//	 * @return
//	 */
//	public PdfDataBean decodeJson(String fileName){
//
//		String encodeData = null;
//
//		try {
//			encodeData = FileUtil.getInstance().file2string2(fileName);
//		} catch (FileNotFoundException e) {
//			e.printStackTrace();
//		}
//
//		PdfDataBean jsonPdfDataBean = JSON.decode(encodeData, PdfDataBean.class);
//
//		return jsonPdfDataBean;
//	}
//
//	/**
//	 *
//	 * @param o1
//	 * @param o2
//	 * @param o3
//	 * @param o4
//	 * @param color
//	 * @return
//	 */
//	public Map<String, Object> setColor(Object o1, Object o2,Object o3,Object o4, Object color){
//		return Map.of("fillColor", color, "x1", o1, "y1", o2, "x2", o3, "y2", o4);
//	}
//
//	/**
//	 *
//	 * @param o1
//	 * @param o2
//	 * @param barCordeFileName
//	 * @return
//	 */
//	public Map<String, Object> setImage(Integer o1, Integer o2, String barCordeFileName){
//		return Map.of("fileName", barCordeFileName, "x", o1, "y", o2);
//	}
//
//	public Integer[] getXY(Sheet sheet){
//		Integer[]xy = {0, 0};
//     	xy[0] = new Integer(sheet.getRow(0).getLastCellNum());
//   		xy[1] = sheet.getLastRowNum() + 1;
//		return xy;
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
//		List<Object> objectList = new ArrayList<>();
//		objectList.add(inX);
//		objectList.add(inY);
//
//		return objectList;
//	}
//
//	public void pdfToJpeg(String pathFilePdf, String pathFileJpeg, int page) {
//        Path path = Paths.get(pathFileJpeg);
//
//        try {
//        	InputStream in = new FileInputStream(pathFilePdf);
//            OutputStream out = Files.newOutputStream(path);
//            rasterize(in, out, page);
//            in.close();
//            out.close();
//        }catch(Exception e){
//        	e.printStackTrace();
//        }
//	}
//
//	public void rasterize(InputStream in, OutputStream out, int page) {
//
//        try{
//    		PDDocument doc = PDDocument.load(in);
//
//    		int pages = doc.getNumberOfPages();
//
//    		if(page > pages) {
//    			return;
//    		}
//
//            PDFRenderer pdfRenderer = new PDFRenderer(doc);
//            BufferedImage bim = pdfRenderer.renderImageWithDPI(page - 1, 300, ImageType.RGB);
//            ImageIO.write(bim, "JPEG", out);
//        }catch(Exception e){
//        	e.printStackTrace();
//        }
//    }
//
//	/**
//	 * PDFファイルのページ数を返す
//	 * @param pathFilePdf
//	 * @return
//	 */
//	public int getPage(String pathFilePdf){
//		int rtnInt = 0;
//		try {
//			InputStream in = new FileInputStream(pathFilePdf);
//			rtnInt = getPage(in);
//			in.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return rtnInt;
//	}
//
//	/**
//	 * PDFファイルのページ数を返す
//	 * @param in
//	 * @return
//	 */
//	public int getPage(InputStream in){
//		int pages = 0;
//
//		try {
//			PDDocument doc = PDDocument.load(in);
//			pages = doc.getNumberOfPages();
//			doc.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//
//		return pages;
//    }
//
//	/**
//	 *
//	 * @param map
//	 * @param key
//	 * @return
//	 */
//	public Integer changeInteger(Map map, String key){
//       	String simpleName = map.get(key).getClass().getSimpleName();
//    	if(simpleName.equals("BigDecimal")) {
//    		return ((BigDecimal) map.get(key)).intValue();
//    	}
//    	return ((Integer) map.get(key)).intValue();
//	}
//}
