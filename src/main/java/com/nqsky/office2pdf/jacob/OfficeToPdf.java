package com.nqsky.office2pdf.jacob;

import java.io.File;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;

public class OfficeToPdf {

	private static final int wdFormatPDF = 17;
	private static final int xlTypePDF = 0;
	private static final int ppSaveAsPDF = 32;

	public boolean convert2PDF(String inputFile, String pdfFile) {
		String suffix = getFileSufix(inputFile);
		File file = new File(inputFile);
		if (!file.exists()) {
			System.out.println("文件不存在！");
			return false;
		}
		if (suffix.equals("pdf")) {
			System.out.println("PDF not need to convert!");
			return false;
		}
		if (suffix.equals("doc") || suffix.equals("docx") || suffix.equals("txt")) {
			return word2PDF(inputFile, pdfFile);
		} else if (suffix.equals("ppt") || suffix.equals("pptx")) {
			return ppt2PDF(inputFile, pdfFile);
		} else if (suffix.equals("xls") || suffix.equals("xlsx")) {
			return excel2PDF(inputFile, pdfFile);
		} else {
			System.out.println("文件格式不支持转换!");
			return false;
		}
	}

	public static String getFileSufix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(splitIndex + 1);
	}

	public boolean word2PDF(String inputFile, String pdfFile) {
		try {
			// 打开word应用程序
			ActiveXComponent app = new ActiveXComponent("Word.Application");
			// 设置word不可见
			app.setProperty("Visible", false);
			// 获得word中所有打开的文档,返回Documents对象
			Dispatch docs = app.getProperty("Documents").toDispatch();
			// 调用Documents对象中Open方法打开文档，并返回打开的文档对象Document
			Dispatch doc = Dispatch.call(docs, "Open", inputFile, false, true).toDispatch();
			// 调用Document对象的SaveAs方法，将文档保存为pdf格式
			/*
			 * Dispatch.call(doc, "SaveAs", pdfFile, wdFormatPDF
			 * //word保存为pdf格式宏，值为17 );
			 */
			Dispatch.call(doc, "ExportAsFixedFormat", pdfFile, wdFormatPDF // word保存为pdf格式宏，值为17
			);
			// 关闭文档
			Dispatch.call(doc, "Close", false);
			// 关闭word应用程序
			app.invoke("Quit", 0);
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public boolean excel2PDF(String inputFile, String pdfFile) {
		try {
			ActiveXComponent app = new ActiveXComponent("Excel.Application");
			app.setProperty("Visible", false);
			Dispatch excels = app.getProperty("Workbooks").toDispatch();
			Dispatch excel = Dispatch.call(excels, "Open", inputFile, false, true).toDispatch();
			Dispatch.call(excel, "ExportAsFixedFormat", xlTypePDF, pdfFile);
			Dispatch.call(excel, "Close", false);
			app.invoke("Quit");
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean ppt2PDF(String inputFile, String pdfFile) {
		try {
			ActiveXComponent app = new ActiveXComponent("PowerPoint.Application");
			//app.setProperty("Visible", false);
			Dispatch ppts = app.getProperty("Presentations").toDispatch();

			Dispatch ppt = Dispatch.call(ppts, "Open", inputFile, true,// ReadOnly
					true,// Untitled指定文件是否有标题
					false// WithWindow指定文件是否可见
					).toDispatch();

			Dispatch.call(ppt, "SaveAs", pdfFile, ppSaveAsPDF);

			Dispatch.call(ppt, "Close");

			app.invoke("Quit");
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	// 调用
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		OfficeToPdf o2p = new OfficeToPdf();
//		String docxFilePath = "D:\\office2pdf\\AppNest2.0 消息改造开发说明V1.8.docx";
//		String docFilePath = "D:\\office2pdf\\AppNest2.0 消息改造开发说明V1.8.doc";
//		o2p.convert2PDF(docxFilePath, docxFilePath + ".pdf");
//		o2p.convert2PDF(docFilePath, docFilePath + ".pdf");
//
//		String xlsxFilePath = "D:\\office2pdf\\MEAP产品人员任务追踪20160408-卢日明.xlsx";
//		String xlsFilePath = "D:\\office2pdf\\MEAP产品人员任务追踪20160408-卢日明.xls";
//		o2p.convert2PDF(xlsxFilePath, xlsxFilePath + ".pdf");
//		o2p.convert2PDF(xlsFilePath, xlsFilePath + ".pdf");
		
		String pptxFilePath = "D:\\office2pdf\\NQSky MEAP技术培训.pptx";
		String pptFilePath = "D:\\office2pdf\\NQSky MEAP技术培训.ppt";
		o2p.convert2PDF(pptxFilePath, pptxFilePath + ".pdf");
		o2p.convert2PDF(pptFilePath, pptFilePath + ".pdf");
		System.out.println("jacob office2pdf useTimes:"+(System.currentTimeMillis() - start));
	}
}
