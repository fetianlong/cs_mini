package com.dearho.cs.util;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.cxf.common.util.StringUtils;

import com.opensymphony.webwork.ServletActionContext;

import sun.misc.BASE64Decoder;

public class ImageHelper {
	
    static String url = "http://img.lee-tu.com";
    static String updateurl = "http://img.lee-tu.com:8888/cs_img/upload/imgUpload";

    
    public static String uploadPic(File upload,String uploadFileName,String uploadContentType,Integer fileMaxSize,String uploadPath) throws Exception{
		
		HttpServletResponse response =ServletActionContext.getResponse();
		response.setCharacterEncoding("UTF-8");
		//PrintWriter out = response.getWriter();
		
	 	if(upload==null || uploadContentType==null || uploadFileName==null){  
	 		throw new Exception("上传文件为空!");
        }  
	 	
	 	if(uploadFileName.toLowerCase().endsWith("jpg")||uploadFileName.toLowerCase().endsWith("bmp")||uploadFileName.toLowerCase().endsWith("png")||uploadFileName.toLowerCase().endsWith("jpeg")){
	 		
	 	}else{
	 		throw new Exception("文件格式不正确!");
	 	}

       /* if ((uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg"))  
                && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".jpg")) {  
        	 //IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg  
        }else if(uploadContentType.equals("image/png") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".png")){  
              
        }else if(uploadContentType.equals("image/gif") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".gif")){  
              
        }else if(uploadContentType.equals("image/bmp") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".bmp")){  
              
        }else{  
        	throw new Exception("文件格式不正确!");
        }  */
        
        if(upload.length() > fileMaxSize){ 
        	throw new Exception("文件大小不得大于"+fileMaxSize/1024+"M!");
        }  
		
      //将文件保存到项目目录下   
        InputStream is = new FileInputStream(upload);  
        uploadPath = ServletActionContext.getServletContext()     
              .getRealPath(File.separatorChar+uploadPath);   //设置保存目录  
        String fileName = java.util.UUID.randomUUID().toString();  //采用UUID的方式随机命名
        fileName += uploadFileName.substring(uploadFileName.lastIndexOf("."));  ;  
        File toFile = new File(uploadPath, fileName);  
        OutputStream os = new FileOutputStream(toFile);     
        byte[] buffer = new byte[1024];     
        int length = 0;  
        while ((length = is.read(buffer)) > 0) {     
            os.write(buffer, 0, length);     
        }     
        is.close();  
        os.close();  
        
        //System.err.println(fileName);
        
      //  String realImgPath = ServletActionContext.getRequest().getSession().getServletContext().getRealPath("")+"\\" + fileName; 
        
        Map<String, String> param = new HashMap<String,String>();
        param.put("type", "single");
        param.put("quality", "");
        param.put("useType", "1");
        param.put("userId", "system");
        
        String resStr = HttpClientFile.SubmitPost(updateurl,toFile,param);
        toFile.delete();//删除本地图片
        JSONObject  json = JSONObject.fromObject(resStr);
        
        if(json.get("state").toString().equals("true")){
        	fileName = json.get("url").toString();
        }else{
        	throw new Exception(json.getString("error"));
        }
        return fileName;
          
	}
    
    
    
    /**
     * 
     * @param canvasData  上传canvas数据
     * @param uploadPath  保存图片的路径
     * @param fileName    保存图片的名称
     * @return
     * @throws Exception
     */
	 public  static String uploadForCanvasToService(String canvasData,String uploadPath,String fileName,String UserId) throws Exception{
		  
		 if(canvasData==null){
			 throw new Exception("上传数据为空!");
		 }
		 
		 
		try {
			canvasData = canvasData.substring(30);
			canvasData = URLDecoder.decode(canvasData,"UTF-8");
					
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] data = decoder.decodeBuffer(canvasData);
			
			for(int i=0;i<data.length;++i){
			    if(data[i]<0){
			    	//调整异常数据
			        data[i]+=256;
			    }
			}
			 
			
			 uploadPath = ServletActionContext.getServletContext()     
             .getRealPath(File.separatorChar+uploadPath); 
			 
			 
			if(StringUtils.isEmpty(fileName)){
				 fileName = java.util.UUID.randomUUID().toString()+".png";
			}
			File file = new File(uploadPath  ,fileName);
			FileOutputStream outputStream = new FileOutputStream(file);  
			      
			outputStream.write(data);  
			outputStream.flush();  
			outputStream.close();
			Map<String, String> param = new HashMap<String,String>();
		    param.put("type", "single");
		    param.put("quality", "hight");//hight
		    param.put("useType", "1");
		    param.put("userId", UserId);
		    
			String resStr = HttpClientFile.SubmitPost(updateurl,file,param);
		        
		    JSONObject  json = JSONObject.fromObject(resStr);
		        
		    if(json.get("state").toString().equals("true")){
		        	fileName = json.get("url").toString();
		    }else{
		        	throw new Exception(json.getString("error"));
		    }
			file.delete();//删除本地图片
			
		}catch (Exception e) {
			 throw new Exception("保存图片异常!",e);
		}  
			
		return fileName;
	  }
    
    
    
   
    /**
     * 
     * @param canvasData  上传canvas数据
     * @param uploadPath  保存图片的路径
     * @param fileName    保存图片的名称
     * @return
     * @throws Exception
     */
	 public  static String uploadForCanvas(String canvasData,String uploadPath,String fileName) throws Exception{
		  
		 if(canvasData==null){
			 throw new Exception("上传数据为空!");
		 }
		 
		 
		try {
			canvasData = canvasData.substring(30);
			canvasData = URLDecoder.decode(canvasData,"UTF-8");
					
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] data = decoder.decodeBuffer(canvasData);
			
			for(int i=0;i<data.length;++i){
			    if(data[i]<0){
			    	//调整异常数据
			        data[i]+=256;
			    }
			}
			 
			
			 uploadPath = ServletActionContext.getServletContext()     
             .getRealPath(File.separatorChar+uploadPath); 
			if(StringUtils.isEmpty(fileName)){
				 fileName = java.util.UUID.randomUUID().toString()+".png";
			}
			FileOutputStream outputStream = new FileOutputStream(new File(uploadPath  ,fileName));  
			      
			outputStream.write(data);  
			outputStream.flush();  
			outputStream.close();
		}catch (Exception e) {
			 throw new Exception("保存图片异常!",e);
		}  
			
		return fileName;
	  }
    
	 
	 
	 
	 
	    public static String uploadImgForAutoName(File upload,String uploadFileName,String uploadContentType,Integer fileMaxSize,String uploadPath) throws Exception{
			
			HttpServletResponse response =ServletActionContext.getResponse();
			response.setCharacterEncoding("UTF-8");
						
		 	if(upload==null || uploadContentType==null || uploadFileName==null){  
		 		throw new Exception("上传文件为空!");
	        }  
	        if ((uploadContentType.equals("image/pjpeg") || uploadContentType.equals("image/jpeg"))  
	                && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".jpg")) {  
	        	 //IE6上传jpg图片的headimageContentType是image/pjpeg，而IE9以及火狐上传的jpg图片是image/jpeg  
	        }else if(uploadContentType.equals("image/png") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".png")){  
	              
	        }else if(uploadContentType.equals("image/gif") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".gif")){  
	              
	        }else if(uploadContentType.equals("image/bmp") && uploadFileName.substring(uploadFileName.length() - 4).toLowerCase().equals(".bmp")){  
	              
	        }else{  
	        	throw new Exception("文件格式不正确!");
	        }  
	        
	        if(upload.length() > fileMaxSize){ 
	        	throw new Exception("文件大小不得大于"+fileMaxSize/1024+"M!");
	        }  
			
	      //将文件保存到项目目录下   
	        InputStream is = new FileInputStream(upload);  
	        uploadPath = ServletActionContext.getServletContext()     
	              .getRealPath(File.separatorChar+uploadPath);   //设置保存目录  
	        String fileName = java.util.UUID.randomUUID().toString();  //采用UUID的方式随机命名
	        fileName += uploadFileName.substring(uploadFileName.length() - 4);  
	        //fileName += "_"+uploadFileName;  
	        File toFile = new File(uploadPath, fileName);  
	        OutputStream os = new FileOutputStream(toFile);     
	        byte[] buffer = new byte[1024];     
	        int length = 0;  
	        while ((length = is.read(buffer)) > 0) {     
	            os.write(buffer, 0, length);     
	        }     
	        is.close();  
	        os.close();  
	        
	        return fileName;
	          
		}
	    
	    public static void main(String[] args) {
	    	compressPic("D:"+File.separatorChar+"2.jpg","D:"+File.separatorChar+"3.jpg");
		}
	    
	    public static boolean compressPic(String srcFilePath, String descFilePath)  {  
	        File file = null;  
	        BufferedImage src = null;  
	        FileOutputStream out = null;  
	        ImageWriter imgWrier;  
	        ImageWriteParam imgWriteParams;  
	  
	        // 指定写图片的方式为 jpg  
	        imgWrier = ImageIO.getImageWritersByFormatName("jpg").next();  
	        imgWriteParams = new javax.imageio.plugins.jpeg.JPEGImageWriteParam(null);  
	        // 要使用压缩，必须指定压缩方式为MODE_EXPLICIT  
	        imgWriteParams.setCompressionMode(imgWriteParams.MODE_EXPLICIT);  
	        // 这里指定压缩的程度，参数qality是取值0~1范围内，  
	        imgWriteParams.setCompressionQuality((float)0.5);  
	        imgWriteParams.setProgressiveMode(imgWriteParams.MODE_DISABLED);  
	        ColorModel colorModel = ColorModel.getRGBdefault();  
	        // 指定压缩时使用的色彩模式  
	        imgWriteParams.setDestinationType(new javax.imageio.ImageTypeSpecifier(colorModel, colorModel  
	                .createCompatibleSampleModel(16, 16)));  
	  
	        try  
	        {  
	            if(StringUtils.isEmpty(srcFilePath))  
	            {  
	                return false;  
	            }  
	            else  
	            {  
	                file = new File(srcFilePath);  
	                src = ImageIO.read(file);  
	                File toFile = new File( descFilePath);  
	                out = new FileOutputStream(toFile);  
	                
	                imgWrier.reset();  
	                // 必须先指定 out值，才能调用write方法, ImageOutputStream可以通过任何 OutputStream构造  
	                imgWrier.setOutput(ImageIO.createImageOutputStream(out));  
	                // 调用write方法，就可以向输入流写图片  
	                imgWrier.write(null, new IIOImage(src, null, null), imgWriteParams);  
	                out.flush();  
	                out.close();  
	            }  
	        }  
	        catch(Exception e)  
	        {  
	            e.printStackTrace();  
	            return false;  
	        }  
	        return true;  
	    }  
	    
    
    
}
