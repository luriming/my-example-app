package com.nqsky.office2pdf.jcom;

import jp.ne.so_net.ga2.no_ji.jcom.IDispatch;
import jp.ne.so_net.ga2.no_ji.jcom.ReleaseManager;

public class OfficeToPdf {

	/**
	 * @param officePath
	 *            -office文件的原始路径
	 * @param pdfPath
	 *            -生成pdf的路径
	 * */
	public void createPDF(String officePath, String pdfPath) throws Exception {
		ReleaseManager rm = null;
		IDispatch app = null;
		try {
			rm = new ReleaseManager();
			app = new IDispatch(rm, "PDFMakerAPI.PDFMakerApp");
			app.method("CreatePDF", new Object[] { officePath, pdfPath });
		} catch (Exception e) {
			throw e;
		} finally {
			try {
				app = null;
				rm.release();
				rm = null;
			} catch (Exception e) {
				throw e;
			}
		}
	}

	// 调用
	public static void main(String[] args) {
		OfficeToPdf o2p = new OfficeToPdf();
		String docxFilePath = "D:\\office2pdf\\AppNest2.0 消息改造开发说明V1.8.docx";
		String docFilePath = "D:\\office2pdf\\AppNest2.0 消息改造开发说明V1.8.doc";
		try {
			o2p.createPDF(docxFilePath, docxFilePath + "-jcom.pdf");
			o2p.createPDF(docFilePath, docFilePath + "-jcom.pdf");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
