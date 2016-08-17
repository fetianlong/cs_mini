package com.dearho.cs.subscriber.action.login;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

import com.dearho.cs.sys.action.AbstractAction;
import com.opensymphony.webwork.ServletActionContext;

public class AppDownAction extends AbstractAction{
	private static final long serialVersionUID = -8322482302151519730L;

	@Override
	public String process() {
		return SUCCESS;
	}
	private InputStream InputStream;
	private String downloadFileName;
	
	
	private static final String uploadPath="upload/app/";
     
	  /*  public InputStream getInputStream1 () throws Exception {     
	        InputStream is = null;     
	        try {     
	            is = new FileInputStream(fileName);     
	        } catch (FileNotFoundException e1) {     
	        }     
	        return is;     
	    }    */ 
	         
	
	  
	    
	   /* public String getDownloadFileName () {     
	        String downFileName = fileName;     
	        try {     
	            downFileName = new String(downFileName.getBytes(), "utf8");     
	        } catch (UnsupportedEncodingException e) {     
	            e.printStackTrace();     
	        }     
	        return downFileName;     
	    }     */
	
    
	    
	public String gotoAppDown(){
		 
		return SUCCESS;
	}
	
	public String downAndroidApp(){
		try {     
			 downloadFileName="android.jpg";
			 String appPath = ServletActionContext.getServletContext().getRealPath(File.separatorChar+uploadPath);
			 File file = new File(appPath+File.separatorChar+downloadFileName);
			 InputStream = new FileInputStream(file);     
	     } catch (Exception e) {
	        	e.printStackTrace();
	     }  
		return SUCCESS;
	}
	
	public String downIosApp(){
		try {     
			 downloadFileName="ios.jpg";
			 String appPath = ServletActionContext.getServletContext().getRealPath(File.separatorChar+uploadPath);
			 File file = new File(appPath+File.separatorChar+downloadFileName);
			 InputStream = new FileInputStream(file);     
	     } catch (Exception e) {
	        	e.printStackTrace();
	     }  
		return SUCCESS;
	}


	public InputStream getInputStream() {
		return InputStream;
	}

	public void setInputStream(InputStream inputStream) {
		InputStream = inputStream;
	}

	public String getDownloadFileName() {
		 try {     
			 downloadFileName = new String(downloadFileName.getBytes(), "utf8");     
	        } catch (UnsupportedEncodingException e) {     
	            e.printStackTrace();     
	        }     
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}
	
	
	
	
	
}
