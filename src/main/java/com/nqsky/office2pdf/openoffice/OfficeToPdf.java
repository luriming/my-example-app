package com.nqsky.office2pdf.openoffice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.ConnectException;

import org.artofsolving.jodconverter.OfficeDocumentConverter;
import org.artofsolving.jodconverter.office.DefaultOfficeManagerConfiguration;
import org.artofsolving.jodconverter.office.OfficeManager;

import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;

public class OfficeToPdf {

	private static OfficeManager officeManager;

	/**
	 * 将Office文档转换为PDF. 运行该函数需要用到OpenOffice, OpenOffice下载地址为
	 * http://www.openoffice.org/
	 * 
	 * <pre>
	 * 方法示例: 
	 * String sourcePath = "F:\\office\\source.doc"; 
	 * String destFile = "F:\\pdf\\dest.pdf"; 
	 * Converter.office2PDF(sourcePath, destFile);
	 * </pre>
	 * 
	 * @param sourceFile
	 *            源文件, 绝对路径. 可以是Office2003-2007全部格式的文档, Office2010的没测试. 包括.doc,
	 *            .docx, .xls, .xlsx, .ppt, .pptx等. 示例: F:\\office\\source.doc
	 * @param destFile
	 *            目标文件. 绝对路径. 示例: F:\\pdf\\dest.pdf
	 * @return 操作成功与否的提示信息. 如果返回 -1, 表示找不到源文件, 或url.properties配置错误; 如果返回 0,
	 *         则表示操作成功; 返回1, 则表示转换失败
	 */
	public static int office2PDF(String sourceFile, String destFile) {
		try {
			File inputFile = new File(sourceFile);
			if (!inputFile.exists()) {
				return -1;// 找不到源文件, 则返回-1
			}

			// 如果目标路径不存在, 则新建该路径
			File outputFile = new File(destFile);
			if (!outputFile.getParentFile().exists()) {
				outputFile.getParentFile().mkdirs();
			}

			String OpenOffice_HOME = "D:\\Program Files\\OpenOffice 4\\";// 这里是OpenOffice的安装目录,
																			// 在我的项目中,为了便于拓展接口,没有直接写成这个样子,但是这样是绝对没问题的
			// 如果从文件中读取的URL地址最后一个字符不是 '\'，则添加'\'
			if (OpenOffice_HOME.charAt(OpenOffice_HOME.length() - 1) != '\\') {
				OpenOffice_HOME += "\\";
			}
			// 启动OpenOffice的服务
//			String command = OpenOffice_HOME + "program\\soffice.exe -headless -accept=\"socket,host=127.0.0.1,port=8100;urp;\"";
//			Process pro = Runtime.getRuntime().exec(command);
			// connect to an OpenOffice.org instance running on port 8100
			OpenOfficeConnection connection = new SocketOpenOfficeConnection("192.168.1.153", 8100);
			connection.connect();

			// convert
			DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
			converter.convert(inputFile, outputFile);

			// close the connection
			connection.disconnect();
			// 关闭OpenOffice服务的进程
//			pro.destroy();

			return 0;
		}  catch (ConnectException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 转换格式
	 * 
	 * @param inputFile
	 *            需要转换的原文件路径
	 * @param fileType
	 *            要转换的目标文件类型 html,pdf
	 */
	public void convert(String sourceFile, String destFile) {
		startService();
		// 如果目标路径不存在, 则新建该路径
		File outputFile = new File(destFile);
		if (!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().mkdirs();
		}
		OfficeDocumentConverter converter = new OfficeDocumentConverter(officeManager);
		converter.convert(new File(sourceFile), outputFile);
		stopService();
	}

	public static void startService() {
		DefaultOfficeManagerConfiguration configuration = new DefaultOfficeManagerConfiguration();
		try {
			System.out.println("准备启动服务....");
			// configuration.setPortNumbers(8100); // 设置转换端口，默认为2002
			configuration.setOfficeHome("D:\\Program Files\\OpenOffice 4");
			configuration.setTaskExecutionTimeout(1000 * 60 * 5L);// 设置任务执行超时为5分钟
			configuration.setTaskQueueTimeout(1000 * 60 * 60 * 24L);// 设置任务队列超时为24小时

			officeManager = configuration.buildOfficeManager();
			officeManager.start(); // 启动服务
			System.out.println("office转换服务启动成功!");
		} catch (Exception ce) {
			System.out.println("office转换服务启动失败!详细信息:" + ce);
		}
	}

	public static void stopService() {
		System.out.println("关闭office转换服务....");
		if (officeManager != null) {
			officeManager.stop();
		}
		System.out.println("关闭office转换成功!");
	}

	// 调用
	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		 String docxFilePath = "D:\\office2pdf\\AppNest2.0 消息改造开发说明V1.8.docx";
		 String docFilePath = "D:\\office2pdf\\AppNest2.0 消息改造开发说明V1.8.doc";
		 OfficeToPdf.office2PDF(docxFilePath, docxFilePath +"-openoffice.pdf");
//		 OfficeToPdf.office2PDF(docFilePath, docFilePath +"-openoffice01.pdf");
		//
		// String xlsxFilePath = "D:\\office2pdf\\MEAP产品人员任务追踪20160408-卢日明.xlsx";
		// String xlsFilePath = "D:\\office2pdf\\MEAP产品人员任务追踪20160408-卢日明.xls";
		// OfficeToPdf.office2PDF(xlsxFilePath, xlsxFilePath +"-openoffice.pdf");
		// OfficeToPdf.office2PDF(xlsFilePath, xlsFilePath + "-openoffice.pdf");

//		OfficeToPdf o2p = new OfficeToPdf();
//		String pptxFilePath = "D:\\office2pdf\\NQSky MEAP技术培训.pptx";
//		String pptFilePath = "D:\\office2pdf\\NQSky MEAP技术培训.ppt";
//		o2p.convert(pptxFilePath, pptxFilePath + "-openoffice.pdf");
//		o2p.convert(pptFilePath, pptFilePath + "-openoffice.pdf");

//		String docxFilePath = "D:\\office2pdf\\AppNest2.0 消息改造开发说明V1.8.docx";
//		String docFilePath = "D:\\office2pdf\\AppNest2.0 消息改造开发说明V1.8.doc";
//		o2p.convert(docxFilePath, docxFilePath + "-openoffice.pdf");
//		o2p.convert(docFilePath, docFilePath + "-openoffice.pdf");
//		System.out.println("OpenOffice office2pdf useTimes:" + (System.currentTimeMillis() - start));
	}

}
