package com.sixwork.sixdgrs.FILE.ZIP;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.zip.CRC32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class ZipAndUnzip {

	
	public static void zip(String srcPathName, String zipFileName)  
    {  
        File file = new File(srcPathName);  
        File zipFile = new File(zipFileName);  
        if (!file.exists()) throw new RuntimeException(srcPathName + "不存在！");  
        try  
        {  
            FileOutputStream fileOutputStream = new FileOutputStream(zipFile);  
            CheckedOutputStream cos = new CheckedOutputStream(fileOutputStream, new CRC32());  
            ZipOutputStream out = new ZipOutputStream(cos);  
            Properties pro=System.getProperties();
            String osName=pro.getProperty("os.name");
//            if("Linux".equals(osName)||"linux".equals(osName)){
//                out.setEncoding("GBK");//设置文件名编码方式
//            }else{
//                out.setEncoding(System.getProperty("sun.jnu.encoding"));//设置文件名编码方式
//            }

            String basedir = "";  
            compress(file, out, basedir);  
            out.close();  
        }  
        catch (Exception e)  
        {  
            throw new RuntimeException(e);  
        }  
    }
/*
     * inputFileName 输入一个文件夹
     * zipFileName 输出一个压缩文件夹
     */
    private static void compress(File file, ZipOutputStream out, String basedir)  
    {  
        /* 判断是目录还是文件 */  
        if (file.isDirectory())  
        {  
            // System.out.println("压缩：" + basedir + file.getName());  
            compressDirectory(file, out, basedir);  
        }  
        else  
        {  
            // System.out.println("压缩：" + basedir + file.getName());  
            compressFile(file, out, basedir);  
        }  
    }  

    /** 压缩一个目录 */  
    private static void compressDirectory(File dir, ZipOutputStream out, String basedir)  
    {  
        if (!dir.exists()) return;  

        File[] files = dir.listFiles();  
        for (int i = 0; i < files.length; i++)  
        {  
            /* 递归 */  
            compress(files[i], out, basedir + dir.getName() + "/");  
        }  
    }  

    /** 压缩一个文件 */  
    private static void compressFile(File file, ZipOutputStream out, String basedir)  
    {  
        if (!file.exists())  
        {  
            return;  
        }  
        try  
        {  
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));  
            ZipEntry entry = new ZipEntry(basedir + file.getName());
            Properties pro=System.getProperties();
            String osName=pro.getProperty("os.name");
//            if("Linux".equals(osName)||"linux".equals(osName)){
//                entry.setUnixMode(644);
//            }
            out.putNextEntry(entry);  
            int count;  
            byte data[] = new byte[8192];  
            while ((count = bis.read(data, 0, 8192)) != -1)  
            {  
                out.write(data, 0, count);  
            }  
            bis.close();  
        }  
        catch (Exception e)  
        {  
            throw new RuntimeException(e);  
        }  
    }  
    

    public static void unzip(String zipFilePath, String destDir)  
    {  
      System.setProperty("sun.zip.encoding", System.getProperty("sun.jnu.encoding")); //防止文件名中有中文时出错  
      //System.out.println(System.getProperty("sun.zip.encoding")); //ZIP编码方式  
      //System.out.println(System.getProperty("sun.jnu.encoding")); //当前文件编码方式  
      //System.out.println(System.getProperty("file.encoding")); //这个是当前文件内容编码方式  
        
      File dir = new File(destDir);  
      // create output directory if it doesn't exist  
      if (!dir.exists()) dir.mkdirs();  
      FileInputStream fis;  
      // buffer for read and write data to file  
      byte[] buffer = new byte[1024];  
      try  
      {  
        fis = new FileInputStream(zipFilePath);  
        ZipInputStream zis = new ZipInputStream(fis);  
        ZipEntry ze = zis.getNextEntry();  
        while (ze != null)  
        {  
          String fileName = ze.getName();  
          File newFile = new File(destDir + File.separator + fileName);  
          //System.out.println("Unzipping to " + newFile.getAbsolutePath());  
          // create directories for sub directories in zip  
          new File(newFile.getParent()).mkdirs();  
          FileOutputStream fos = new FileOutputStream(newFile);  
          int len;  
          while ((len = zis.read(buffer)) > 0)  
          {  
            fos.write(buffer, 0, len);  
          }  
          fos.close();  
          // close this ZipEntry  
          zis.closeEntry();  
          ze = zis.getNextEntry();  
        }  
        // close last ZipEntry  
        zis.closeEntry();  
        zis.close();  
        fis.close();  
      }  
      catch (IOException e)  
      {  
        e.printStackTrace();  
      }  
    
    }  
    
    public static void main(String[] args)  
    {  
      String filePath = "D:\\export\\zlp12121591261851247 - 副本.csv";  
    
      
      String destDir = "D:\\export\\zlp12121591261727585.zip";  
    
      zip(filePath, destDir);  
    }  
    
}
