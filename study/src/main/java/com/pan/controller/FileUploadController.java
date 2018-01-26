package com.pan.controller;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.pan.common.enums.ResultCodeEmun;
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
@RequestMapping("/api")  
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
            //生成时间戳作为文件名称  
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
            String userId=CookieUtils.getLoginUserId(request);
            try {
            	 picture.setUserId(userId);
                 picture.setPictureId(IdUtils.generatePictureId());
                 picture.setPicUrl(PIC_BASE+path);
                 picture.setCreateTime(new Date());
                 pictureService.savePicture(picture);
                 logger.info("图片输出路径:{}",PIC_BASE+path); 
                 Map<String,Object> data=new HashMap<String, Object>(5);
                 data.put("src", PIC_BASE+path);
                 resultMsg=ResultMsg.build(ResultCodeEmun.UPLOAD_SUCCESS,ResultCodeEmun.UPLOAD_SUCCESS.getMsg(),data);
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
    
	@RequestMapping("/upload2")
	@ResponseBody
	public Map<String,Object> upLoad(HttpServletRequest request, HttpServletResponse response) {
		//创建一个通用的多部分解析器
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
		Map<String,Object> map = new HashMap<String,Object>(5);
		//判断 request 是否有文件上传,即多部分请求
		if(multipartResolver.isMultipart(request)){
			//转换成多部分request
			MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
			//取得request中的所有文件名
			Iterator<String> iter = multiRequest.getFileNames();
			List<String> data = new ArrayList<String>();
				while(iter.hasNext()){
					//取得上传文件
					MultipartFile file = multiRequest.getFile(iter.next());
					if(file != null){
						//取得当前上传文件的文件名称
						String myFileName = file.getOriginalFilename();
						//如果名称不为“”,说明该文件存在，否则说明该文件不存在
						if(myFileName.trim() !=""){
							System.out.println(myFileName);
							SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmssSSS");
				            String dateStr=sdf.format(new Date());
							String contentType=file.getContentType();
							//后缀名
							String fileType=contentType.substring(contentType.indexOf("/")+1);
							String destFileName=dateStr+"."+fileType; 
							String filePath=pictureDir+destFileName;
							
							//定义上传路径
							File localFile = new File(filePath);
							if(!localFile.exists()) {
								localFile.mkdirs();
							}
							try {
								Picture picture=new Picture();
					            String userId=CookieUtils.getLoginUserId(request);
								picture.setUserId(userId);
				                picture.setPictureId(IdUtils.generatePictureId());
				                picture.setPicUrl(PIC_BASE+destFileName);
				                picture.setCreateTime(new Date());
				                pictureService.savePicture(picture);
								file.transferTo(localFile);
								data.add(PIC_BASE+destFileName);
							} catch (Exception e) {
								e.printStackTrace();
								map.put("errno", 1);
								map.put("data", data);
								return map;
							}
						}
					}
				}
				map.put("data", data);
				map.put("errno", 0);
			}
		return map;
	}
}
