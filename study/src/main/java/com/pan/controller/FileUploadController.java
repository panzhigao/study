package com.pan.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pan.common.vo.ResultMsg;
import com.pan.entity.Picture;
import com.pan.service.PictureService;
import com.pan.util.CookieUtils;
import com.pan.util.IdUtils;


/**
 * 文件上传
 * @author Administrator
 *
 */
@Controller  
@RequestMapping("/file")  
public class FileUploadController {
	
	private static final Logger logger=LoggerFactory.getLogger(FileUploadController.class);
	
	@Autowired
	private PictureService pictureService;
	
	//TODO 路径配到配置文件
	/**
	 * 图片保存路径
	 */
	@Value("${picture.dir}")
	private String pictureDir;
	
	private static final String PIC_BASE="http://www.pan.com/myimage/"; 
	
    @RequestMapping(value="/upload",headers=("content-type=multipart/*"),method=RequestMethod.POST)
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
            File destFile=new File(pictureDir+path); 
            file.transferTo(destFile);  
            Picture picture=new Picture();
            String userId=CookieUtils.getLoingUserId(request);
            try {
            	 picture.setUserId(userId);
                 picture.setPictureId(IdUtils.generatePictureId());
                 picture.setPicUrl(PIC_BASE+path);
                 picture.setCreateTime(new Date());
                 pictureService.savePicture(picture);
                 logger.info("图片输出路径:{}",PIC_BASE+path); 
                 resultMsg=ResultMsg.ok(PIC_BASE+path);
			} catch (Exception e) {
				logger.error("保存图片信息失败",e);
				resultMsg=ResultMsg.fail("保存图片信息失败");
				if(destFile.exists()){
					boolean deleted=destFile.delete();
					if(deleted){
						logger.info("删除图片成功"); 
					}else{						
						logger.info("删除图片失败"); 
					}
				}
			}
        }else{
        	resultMsg=ResultMsg.fail("文件格式有误");
        }
        return resultMsg;
    }  
}
