package com.sixwork.sixdgrs.FTP;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

public class FTPTest {

	public static void main(String[] argzs) throws Exception {
		fileList("/data/reportExportFiles/","");
		download();
		
		
	}

	public static void getConnect() {

	}

	public static void fileList(String pathName, String ext) throws IOException {
		FTPClient ftp = new FTPClient();
		ftp.connect("192.168.51.81");
		ftp.login("maywide_es", "Maywide368$%^");
		if (pathName.startsWith("/") && pathName.endsWith("/")) {
			// 更换目录到当前目录
			ftp.changeWorkingDirectory(pathName);
			FTPFile[] files = ftp.listFiles();
			for (FTPFile file : files) {
				if (file.isFile()) {
						System.out.println(file.getName());
				} else if (file.isDirectory()) {
					if (!".".equals(file.getName()) && !"..".equals(file.getName())) {
						fileList(pathName + file.getName() + "/", ext);
					}
				}
			}
		}
		
		ftp.noop(); // check that control connection is working OK
		ftp.logout();
	}

	public static void download() throws Exception {
		FileOutputStream fos = null;
		FTPClient ftpclient = new FTPClient();
		FileInputStream fis = null;
		try {

			ftpclient.connect("192.168.51.81");
			ftpclient.login("maywide_es", "Maywide368$%^");
			File file = new File("d://export//zlp12121590829973127.csv");
			ftpclient.changeWorkingDirectory("/data/reportExportFiles/");
			ftpclient.setBufferSize(1024);
			ftpclient.setControlEncoding("UTF-8");
			ftpclient.setFileType(FTPClient.BINARY_FILE_TYPE);

			fos = new FileOutputStream(file);
			ftpclient.enterLocalPassiveMode();
			//ftpclient.retrieveFile(new String("11.txt".getBytes("utf-8"), "iso-8859-1"), fos);
			 ftpclient.retrieveFile("zlp12121590829973127.csv", fos);

			fos.close();
			fos = null;

			fis = new FileInputStream(file);

			if (0l == fis.available()) {
				throw new Exception("文件不存在");
			}
			fis.close();
			fis = null;

			ftpclient.noop(); // check that control connection is working OK
			ftpclient.logout();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw e;
		} finally {
			if (null != fos) {
				try {
					fos.close();
				} catch (IOException f) {
					// do nothing
				}
			}
			if (null != fis) {
				try {
					fis.close();
				} catch (IOException f) {
					// do nothing
				}
			}
			if (ftpclient.isConnected()) {
				try {
					ftpclient.disconnect();
				} catch (IOException f) {
					// do nothing
				}
			}
		}

	}
}
