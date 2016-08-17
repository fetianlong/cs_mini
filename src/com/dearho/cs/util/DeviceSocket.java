package com.dearho.cs.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class DeviceSocket {

	private static Socket socket;
	
	private static final String IP_ADDR = "127.0.0.1";//服务器地址   
//	private static final String IP_ADDR = "123.57.37.220";
	private static final int PORT = 10080;//服务器端口号    
	public static final Log logger = LogFactory.getLog(DeviceSocket.class);
	
	static{
		try {
			socket = new Socket(IP_ADDR, PORT);
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} 
	}
	
	public static String sendDeviceCommand(String command,String deviceNo,String ip,String port){
		try {  
	         //创建一个流套接字并将其连接到指定主机上的指定端口号  
	         if(socket == null || socket.isClosed()){
	        	 socket = new Socket(IP_ADDR, PORT);
	         }
	             
	         //读取服务器端数据    
//	         DataInputStream input = new DataInputStream(socket.getInputStream());    
	         //向服务器端发送数据    
	         DataOutputStream out = new DataOutputStream(socket.getOutputStream());   
	         if("ipportSet".equals(command)){
	        	 out.writeUTF("dearho,"+command+","+deviceNo+","+ip+","+port+"\n");    
	         }
	         else{
	        	 out.writeUTF("dearho,"+command+","+deviceNo+"\n");    
	         }
	         
	         InputStream is = socket.getInputStream();  
		     BufferedReader br = new BufferedReader(new InputStreamReader(is));  
	         String ret = br.readLine();     
	         logger.info("The msg return for service: " + ret);    
	         out.close();  
	         is.close();  
	         return ret;
	     } catch (Exception e) {  
	    	 logger.info("client error:" + e.getMessage());   
	         return "1";
	     }
		
	}
    public static void main(String[] args) {
    	DeviceSocket.sendDeviceCommand("ipportSet", "DF095269","182.92.165.121","10080");
	}
}
