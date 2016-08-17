package com.dearho.cs.sys.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Iterator;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.web.context.ContextLoader;

import com.dearho.cs.sys.pojo.SMSRecord;
import com.dearho.cs.sys.service.SMSRecordService;

public class SMSUtil {
	//使用StringBuffer的append获得xml形式的字符串
		
		private static  BufferedReader br=null;
		private static	URL url=null;
		private static	HttpURLConnection con;
		private static	String line;
		private static	String account = "AC00254";//账号
		private static	String password = "AC0025458";//密码
//		private static	String account = "huatai";//账号
//		private static	String password = "huatai123";//密码

		private static SMSRecordService smsRecordService = 
				(SMSRecordService)ContextLoader.getCurrentWebApplicationContext().getBean("smsRecordService") ;
		
		
		public static void sendSMS(String phoneNo,String content,Integer type){
			sendSMS(phoneNo, content, type, null, null);
		}
		public static void sendSMS(String phoneNo,String content,Integer type,String userId,String name){
			if(!content.contains("【乐途出行】")){
				content="【乐途出行】"+content;
			}
			String inputline = send(phoneNo, content);
			SMSRecord smsRecord =new SMSRecord();
			smsRecord.setContent(content);
			smsRecord.setPhoneNo(phoneNo);
			smsRecord.setType(type);
			smsRecord.setResult(inputline);
			smsRecord.setUserId(userId);
			smsRecord.setUserName(name);
			smsRecord.setTs(new Date());
			smsRecordService.addSMSRecord(smsRecord);
			System.out.println(phoneNo+":"+content);
		}
		public static void main(String[] args) {
			send("18810289689", "【乐途出行】尊敬的侯自伟您好，您的华泰会员身份审核已通过。");
		}
		
		/**
		 * 
		 * @param mobile   手机号
		 * @param content  发送内容
		 * @return
		 */
		public static String send(String mobile,String content){
			String status = null;
			xmlEntity xmlentity=new xmlEntity();
			String xml=null;
			 //发送调用
			xml=SendMessage("", account, password, mobile, content, "").toString();
	        System.out.println(xml);
	        xmlentity.setReturnstatus("returnstatus");
	        xmlentity.setMessage("message");
	        xmlentity.setRemainpoint("remainpoint");
	        xmlentity.setTaskID("taskID");
	        xmlentity.setSuccessCounts("successCounts");
	        xmlentity=readStringXmlCommen(xmlentity, xml);
	        System.out.println("状态"+xmlentity.getReturnstatus()+"返回信息"+xmlentity.getMessage()+"成功条数"+xmlentity.getSuccessCounts());
	        status = xmlentity.getReturnstatus();
	        return status;
		}
		/**
		 * 发送短信
		 * @param userid 默认为空
		 * @param account 账号
		 * @param password 密码
		 * @param mobile   手机号
		 * @param content  发送内容
		 * @param sendTime 定时发送 空默认为及时发送
		 * @return
		 */
		public static StringBuffer SendMessage(String userid,String account,String password,String mobile,String content,String sendTime){
			StringBuffer sub=new StringBuffer();
			try {
				//设置发送内容的编码方式
				String send_content=URLEncoder.encode(content.replaceAll("<br/>", " "), "UTF-8");//发送内容
				
				url=new URL("http://dx.ipyy.net/sms.aspx?action=send&userid="+userid+"&account="+account+"&password="+password+"&mobile="+mobile+"&content="+send_content+"&sendTime="+sendTime+"");	
				con = (HttpURLConnection)url.openConnection();
				
				br=new  BufferedReader(new InputStreamReader(con.getInputStream(),"UTF-8"));
				//br=new BufferedReader(new InputStreamReader(url.openStream()));
			     
			    while((line=br.readLine())!=null){
			    	//追加字符串获得XML形式的字符串
			    	sub.append(line+"");
			    	//System.out.println("提取数据 :  "+line);
			    }
			    br.close();
			    
			} catch (IOException e){
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally	{
				return sub;
			}
		}
		
		//XML字符串解析通用方法
		public static xmlEntity readStringXmlCommen(xmlEntity xmlentity,String xml){
			xmlEntity xe=new xmlEntity();
			Document doc=null;
			try {
				//将字符转化为XML
				doc=DocumentHelper.parseText(xml);
				//获取根节点
				Element rootElt=doc.getRootElement();
				//拿到根节点的名称
				//System.out.println("根节点：" + rootElt.getName()); 
				
				//获取根节点下的子节点的值
				if(xmlentity.getReturnstatus()!=null)
				{
					xe.setReturnstatus(rootElt.elementText(xmlentity.getReturnstatus()).trim());
				}
				if(xmlentity.getMessage()!=null)
				{
					xe.setMessage(rootElt.elementText(xmlentity.getMessage()).trim());
				}
				if(xmlentity.getRemainpoint()!=null)
				{
					xe.setRemainpoint(rootElt.elementText(xmlentity.getRemainpoint()).trim());
				}
				if(xmlentity.getTaskID()!=null)
				{
					xe.setTaskID(rootElt.elementText(xmlentity.getTaskID()).trim());
				}
				if(xmlentity.getSuccessCounts()!=null)
				{
					xe.setSuccessCounts(rootElt.elementText(xmlentity.getSuccessCounts()).trim());
				}
				if(xmlentity.getPayinfo()!=null)
				{
					xe.setPayinfo(rootElt.elementText(xmlentity.getPayinfo()).trim());
				}
				if(xmlentity.getOverage()!=null)
				{
					xe.setOverage(rootElt.elementText(xmlentity.getOverage()).trim());
				}
				if(xmlentity.getSendTotal()!=null)
				{
					xe.setSendTotal(rootElt.elementText(xmlentity.getSendTotal()).trim());
				}
				//接收状态返回的报告
				if(rootElt.hasMixedContent()==false)
				{
					System.out.println("无返回状态！");
				}
				else
				{
					for (int i = 1; i <= rootElt.elements().size(); i++) {
						if(xmlentity.getStatusbox()!=null)
						{
							System.out.println("状态"+i+":");
							//获取根节点下的子节点statusbox
							 Iterator iter = rootElt.elementIterator(xmlentity.getStatusbox()); 
							// 遍历statusbox节点 
							 while(iter.hasNext())
							 {
								 Element recordEle = (Element) iter.next();
								 xe.setMobile(recordEle.elementText("mobile").trim());
								 xe.setTaskid(recordEle.elementText("taskid").trim());
								 xe.setStatus(recordEle.elementText("status").trim());
								 xe.setReceivetime(recordEle.elementText("receivetime").trim());
								 System.out.println("对应手机号："+xe.getMobile());
								 System.out.println("同一批任务ID："+xe.getTaskid());
								 System.out.println("状态报告----10：发送成功，20：发送失败："+xe.getStatus());
								 System.out.println("接收时间："+xe.getReceivetime());
							 }	 
						 }
						
					}

				}
				
				//错误返回的报告
				if(xmlentity.getErrorstatus()!=null)
				{
					//获取根节点下的子节点errorstatus
					 Iterator itererr = rootElt.elementIterator(xmlentity.getErrorstatus()); 
					// 遍历errorstatus节点
		            while(itererr.hasNext())
		            {
		            	Element recordElerr = (Element) itererr.next();
		            	xe.setError(recordElerr.elementText("error").trim());
		            	xe.setRemark(recordElerr.elementText("remark").trim());
		            	System.out.println("错误代码："+xe.getError());
		            	System.out.println("错误描述："+xe.getRemark());
		            }
				}
				
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			return xe;
		}
				
}
