package com.doll.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

@Controller
@RequestMapping("/file")
public class FileUploadController {
		@RequestMapping("/upload")
		public void uploadImage(HttpServletRequest request, HttpServletResponse response) throws IOException{
			CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request  
		            .getSession().getServletContext());  
			if (multipartResolver.isMultipart(request)){
				MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
				Map<String,String[]> params = multiRequest.getParameterMap();
				for(String param:params.keySet()){
					System.out.println(param+"====");
					System.out.println(((String[])params.get(param))[0]);
				}
				Iterator<String> iter = multiRequest.getFileNames();  
				 while (iter.hasNext()) {  
			            // 取得上传文件  
			            MultipartFile file = multiRequest.getFile(iter.next());
			            if (file != null) {  
			                String myFileName = file.getOriginalFilename();  
			                if (myFileName.trim() != "") {  
			                    String fileName = new Date().getTime()  
			                            + file.getOriginalFilename();  
			                    String realPath = request.getSession().getServletContext()  
			                            .getRealPath("/WEB-INF/upload");  
			                    FileUtils.copyInputStreamToFile(file.getInputStream(), new File(  
			                            realPath, fileName));  
			                }  
			            }  
			        }  
			}
		}
}
