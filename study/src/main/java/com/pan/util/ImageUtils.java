package com.pan.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Base64Utils;



/**
 * 图片工具类
 * @author Administrator
 *
 */
public class ImageUtils {
	
	private static final Logger logger=LoggerFactory.getLogger(ImageUtils.class);
	
	/**
	 * base64字符串转化成图片
	 * @param imgStr
	 * @param fileDir
	 * @return
	 */
	public static String generateImage(String imgStr,String fileDir) { // 对字节数组字符串进行Base64解码并生成图片
		// 图像数据为空
		if (imgStr == null){			
			return null;	
		} 
		try {
			// Base64解码
			byte[] b=Base64Utils.decodeFromString(imgStr);
			for (int i = 0; i < b.length; ++i) {
				// 调整异常数据
				if (b[i] < 0) {
					b[i] += 256;
				}
			}
			// 生成jpeg图片
			String dateStr=DateUtils.getDateStr(DateUtils.FORMAT_TIME_MILLS);
			// 新生成的图片
			String fileName=dateStr+".jpg";
			String imgFilePath = fileDir+fileName;
			File destFile=new File(imgFilePath);
			destFile.setReadable(true);
			destFile.setExecutable(true);
			destFile.setWritable(true);
			OutputStream out = new FileOutputStream(destFile);
			if(!SystemUtils.isWindows()){				
				Runtime.getRuntime().exec("chmod 777 -R " + imgFilePath); 
			}
			out.write(b);
			out.flush();
			out.close();
			return fileName;
		} catch (Exception e) {
			logger.error("转换图片失败",e);
			return null;
		}
	}
}
