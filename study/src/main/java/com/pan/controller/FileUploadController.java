package com.pan.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import com.pan.common.vo.ResultMsg;


/**
 * 文件上传
 * @author Administrator
 *
 */
@Controller  
@RequestMapping("/file")  
public class FileUploadController {
	
	private static final Logger logger=LoggerFactory.getLogger(FileUploadController.class);
	
	private static String fileRoot="C:\\Users\\Administrator\\Desktop\\nginx-1.13.8\\images\\";
	
    @RequestMapping(value="/upload",method=RequestMethod.POST)
    @ResponseBody
    public ResultMsg fildUpload(@RequestParam(value="file",required=false) MultipartFile file,HttpServletRequest request)throws Exception{ 
    	logger.info("上传文件开始");
        //获得物理路径webapp所在路径  
    	ResultMsg resultMsg=null;
        String path="";  
        if(!file.isEmpty()){  
            //生成uuid作为文件名称  
            SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String dateStr=sdf.format(new Date());
            //获得文件类型（可以判断如果不是图片，禁止上传）  
            String contentType=file.getContentType();  
            //获得文件后缀名称  
            String imageName=contentType.substring(contentType.indexOf("/")+1);  
            path=dateStr+"."+imageName;  
            file.transferTo(new File(fileRoot+path));  
            resultMsg=ResultMsg.ok(path);
        }else{
        	resultMsg=ResultMsg.fail("文件格式有误");
        }  
        logger.info("图片输出路径:{}",path); 
        return resultMsg;
    }  
}
