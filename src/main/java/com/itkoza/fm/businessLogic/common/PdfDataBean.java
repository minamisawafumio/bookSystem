package com.itkoza.fm.businessLogic.common;

import java.util.List;
import java.util.Map;

public class PdfDataBean {
	private String fileName;
	private List<Map<String, Object>> excelLineList;
	private List<Map<String, Object>> excelMojiList;
	private List<Map<String, Object>> pdfImageFileBeanList;
	private List<Map<String, Object>> pdfFillBoxMapList;
	private List<List<Object>> mojiDataList;

//	private PDDocument pDDocument;
	private Integer maxX;
	private Integer maxY;
	private Map<String, List<Integer>> excelMojiMap;

	public List<Map<String, Object>> getExcelLineList() {
		return excelLineList;
	}
	public PdfDataBean setExcelLineList(List<Map<String, Object>> excelLineList) {
		this.excelLineList = excelLineList;
		return this;
	}
	public List<Map<String, Object>> getExcelMojiList() {
		return excelMojiList;
	}
	public PdfDataBean setExcelMojiList(List<Map<String, Object>> excelMojiList) {
		this.excelMojiList = excelMojiList;
		return this;
	}
	public Integer getMaxX() {
		return maxX;
	}
	public PdfDataBean setMaxX(Integer maxX) {
		this.maxX = maxX;
		return this;
	}
	public Integer getMaxY() {
		return maxY;
	}
	public PdfDataBean setMaxY(Integer maxY) {
		this.maxY = maxY;
		return this;
	}
	public Map<String, List<Integer>> getExcelMojiMap() {
		return excelMojiMap;
	}
	public PdfDataBean setExcelMojiMap(Map<String, List<Integer>> excelMojiMap) {
		this.excelMojiMap = excelMojiMap;
		return this;
	}
	public List<Map<String, Object>> getPdfImageFileMapList() {
		return pdfImageFileBeanList;
	}
	public PdfDataBean setPdfImageFileMapList(List<Map<String, Object>> pdfImageFileBeanList) {
		this.pdfImageFileBeanList = pdfImageFileBeanList;
		return this;
	}
	public List<Map<String, Object>> getPdfFillBoxMapList() {
		return pdfFillBoxMapList;
	}
	public PdfDataBean setPdfFillBoxMapList(List<Map<String, Object>> pdfFillBoxBeanList) {
		this.pdfFillBoxMapList = pdfFillBoxBeanList;
		return this;
	}
//	public PDDocument getpDDocument() {
//		return pDDocument;
//	}
//	public PdfDataBean setpDDocument(PDDocument pDDocument) {
//		this.pDDocument = pDDocument;
//		return this;
//	}
	public List<List<Object>> getMojiDataList() {
		return mojiDataList;
	}

	public PdfDataBean setMojiDataList(List<List<Object>> mojiDataList) {
		this.mojiDataList = mojiDataList;
		return this;
	}
	public String getFileName() {
		return fileName;
	}
	public PdfDataBean setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}
}
