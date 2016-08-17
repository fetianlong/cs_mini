package com.dearho.cs.wxpay.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.InterfaceMessageLog;
import com.dearho.cs.account.pojo.PayResponseMessage;
import com.dearho.cs.account.service.InterfaceMessageLogService;
import com.dearho.cs.account.service.OrderPayService;
import com.dearho.cs.sys.action.AbstractAction;
import com.foxinmy.weixin4j.http.weixin.XmlResult;
import com.foxinmy.weixin4j.model.Consts;
import com.foxinmy.weixin4j.payment.mch.Order;
import com.foxinmy.weixin4j.xml.XmlStream;

public class JsPayOrderFinishPayBackRcveiveAction extends AbstractAction{
	
	private static final long serialVersionUID = 4598081722908212830L;
	
	public static final Log logger = LogFactory.getLog(JsPayOrderFinishPayBackRcveiveAction.class);
	private  InterfaceMessageLogService interfaceMessageLogService;
	private OrderPayService orderPayService;


	

	public void handle() throws Exception {
		
		
		
		Order order=null;
		String resultXml=null;
		
		
		HttpServletRequest request=getRequest();
		HttpServletResponse response=getResponse();
		
		try {
			logger.error("JsPayForOrderBackRcveiveAction接收后台通知开始");
			
		
			
			//读取内容 ,第二种方式获取  
			resultXml=recieveData(request);
            System.out.println(resultXml);

			
            order=XmlStream.fromXML(resultXml, Order.class);
          //  XmlStream.toXML(object)
			//返回成功
			if(order!=null && Consts.SUCCESS.equalsIgnoreCase(order.getReturnCode())){
				//业务结果
				if(Consts.SUCCESS.equalsIgnoreCase(order.getResultCode())){
					
					
					PayResponseMessage payResponseMessage =new PayResponseMessage();
					payResponseMessage.setQueryId(order.getTransactionId());
					payResponseMessage.setOrderId(order.getOutTradeNo());
					
					payResponseMessage.setPayType(Account.PAY_TYPE_WECHAT);
					
					//微信无具体支付信息
					orderPayService.orderPayOnlineCallBack(payResponseMessage, OrderPayService.STEP_FINISH);
					
					
					XmlResult xmlResult=new XmlResult("SUCCESS", "OK");
					response.getWriter().write(XmlStream.toXML(xmlResult));
				}
			}
			logger.error("JsPayForOrderBackRcveiveAction接收后台通知结束");
		}  catch (Exception e) {
			e.printStackTrace();
			logger.error(e);
			XmlResult xmlResult=new XmlResult("SUCCESS", "OK");
			response.getWriter().write(XmlStream.toXML(xmlResult));
		}finally{
			//日志记录
			
			InterfaceMessageLog interfaceMessageLog =new InterfaceMessageLog();
			//interfaceMessageLog.setType(Account.TYPE_RECHARGE);
			interfaceMessageLog.setChannel(Account.PAY_CHANNEL_WECHAT);
			interfaceMessageLog.setTs(new Date());
			if(order!=null){
				interfaceMessageLog.setBizId(order.getOutTradeNo());
				interfaceMessageLog.setRequestMessage("("+resultXml+")="+order.toString());
				interfaceMessageLog.setRespCode(order.getReturnCode()+"|"+order.getResultCode());
			}
			interfaceMessageLogService.addInterfaceMessageLog(interfaceMessageLog);
		}
		
		
		

		
	}

	
	
	//接收请求发来的xml消息体
	public static String recieveData(HttpServletRequest request){
	        String inputLine = null;
	        // 接收到的数据
	        StringBuffer recieveData = new StringBuffer();
	        BufferedReader in = null;
	        try
	        {
	            in = new BufferedReader(new InputStreamReader(
	                    request.getInputStream(), "UTF-8"));
	            while ((inputLine = in.readLine()) != null)
	            {
	                recieveData.append(inputLine);
	            }
	        }
	        catch (IOException e)
	        {
	        }
	        finally
	        {            
	            try
	            {
	                if (null != in)
	                {
	                    in.close();
	                }
	            }
	            catch (IOException e)
	            {
	            }            
	        }
	        
	        return recieveData.toString();
	    }


	public InterfaceMessageLogService getInterfaceMessageLogService() {
		return interfaceMessageLogService;
	}

	public void setInterfaceMessageLogService(InterfaceMessageLogService interfaceMessageLogService) {
		this.interfaceMessageLogService = interfaceMessageLogService;
	}


	public OrderPayService getOrderPayService() {
		return orderPayService;
	}



	public void setOrderPayService(OrderPayService orderPayService) {
		this.orderPayService = orderPayService;
	}



	@Override
	public String process() {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
