package com.dearho.cs.wxpay;

import java.io.File;
import java.io.FileInputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoader;

import com.alibaba.fastjson.JSON;
import com.dearho.cs.account.pojo.Account;
import com.dearho.cs.account.pojo.InterfaceMessageLog;
import com.dearho.cs.account.pojo.PayResponseMessage;
import com.dearho.cs.account.service.InterfaceMessageLogService;
import com.dearho.cs.account.service.OrderPayService;
import com.dearho.cs.account.service.RefundService;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.CookieTool;
import com.dearho.cs.util.PropertiesHelper;
import com.dearho.cs.wechat.util.WeixinUtil;
import com.foxinmy.weixin4j.exception.WeixinPayException;
import com.foxinmy.weixin4j.model.WeixinPayAccount;
import com.foxinmy.weixin4j.payment.PayUtil;
import com.foxinmy.weixin4j.payment.WeixinPayProxy;
import com.foxinmy.weixin4j.payment.mch.MPPayment;
import com.foxinmy.weixin4j.payment.mch.MPPaymentResult;
import com.foxinmy.weixin4j.payment.mch.MchPayPackage;
import com.foxinmy.weixin4j.payment.mch.MchPayRequest;
import com.foxinmy.weixin4j.payment.mch.PrePay;
import com.foxinmy.weixin4j.type.CurrencyType;
import com.foxinmy.weixin4j.type.IdQuery;
import com.foxinmy.weixin4j.type.IdType;
import com.foxinmy.weixin4j.type.MPPaymentCheckNameType;
import com.foxinmy.weixin4j.type.SignType;
import com.foxinmy.weixin4j.type.TradeType;
import com.foxinmy.weixin4j.util.DateUtil;
import com.foxinmy.weixin4j.util.Weixin4jConfigUtil;



public class WxPayUtil {

	public static final Log logger = LogFactory.getLog(WxPayUtil.class);
	private static InterfaceMessageLogService interfaceMessageLogService= (InterfaceMessageLogService)ContextLoader.getCurrentWebApplicationContext().getBean("interfaceMessageLogService");
	
	private static RefundService  refundService= (RefundService)ContextLoader.getCurrentWebApplicationContext().getBean("refundService");

	
	private static String caFilePath=null;
	static{
		  try {
			Properties config = new Properties();
			config.load(PropertiesHelper.class.getClassLoader().getResourceAsStream("weixin4j.properties"));
			caFilePath=(String)config.get("weixin4j.certificate.file");
		} catch (Exception e) {
			e.printStackTrace();
		}
		 
	}
	
	
	/**
	 * 公众账号支付统一下单
	 * @param openId
	 * @param body 商品描述
	 * @param orderNo 商户订单号 
	 * @param orderFee 总金额-分
	 * @param notifyUrl 通知地址
	 * @param request
	 * @param response
	 * @return
	 * @throws PayException
	 */
	public static void recharge(String openId,String body,String tradeOrderNo,Double orderFee,Date orderCreateTime,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StringBuffer requestText = new StringBuffer();
		String jsonResult=null;
		try {
			String notifyUrl=Constants.BASE_URL+request.getContextPath()+"/wxpay/jsPayRechargeBackRcveive.action";
			
			WeixinPayAccount weixinAccount = JSON.parseObject(Weixin4jConfigUtil.getValue("account"), WeixinPayAccount.class);
			
			String spbillCreateIp=CookieTool.getIpAddr(request);
			if(spbillCreateIp!=null&&spbillCreateIp.contains(",")){
				spbillCreateIp=spbillCreateIp.split(",")[0];
			}
			//交易结束时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(orderCreateTime);
			calendar.add(Calendar.MINUTE, Constants.PAY_TIMEOUT_MINUTE);
			
			String attach=null;
			String outTradeNo=tradeOrderNo;
			Double totalFee=orderFee.doubleValue();
			Date timeStart=orderCreateTime;
			Date timeExpire=calendar.getTime();
			String limitPay=null;
			String goodsTag=null;
			
			
			//请求报文记录
			
			requestText.append("&openId="+openId);
			requestText.append("&body="+body);
			requestText.append("&outTradeNo="+outTradeNo);
			requestText.append("&totalFee="+totalFee);
			requestText.append("&notifyUrl="+notifyUrl);
			requestText.append("&spbillCreateIp="+spbillCreateIp);
			requestText.append("&attach="+attach);
			requestText.append("&requestText="+DateUtil.fortmat2yyyyMMddHHmmss(timeStart));
			requestText.append("&timeExpire="+DateUtil.fortmat2yyyyMMddHHmmss(timeExpire));
			requestText.append("&goodsTag="+goodsTag);
			requestText.append("&limitPay="+limitPay);
			
			requestText.append(weixinAccount.toString());
			
		
			MchPayRequest mchPayRequest=PayUtil.createPayJsRequest(weixinAccount, openId, body, outTradeNo, totalFee, notifyUrl, spbillCreateIp, attach, timeStart, timeExpire, goodsTag, limitPay);
			jsonResult= JSON.toJSONString(mchPayRequest);
			request.setAttribute("jsonResult", jsonResult);
		} catch (Exception e) {
			jsonResult=e.getMessage();
			throw e;
		}finally{
			
			
			InterfaceMessageLog interfaceMessageLog =new InterfaceMessageLog();
			interfaceMessageLog.setType(Account.TYPE_RECHARGE);
			interfaceMessageLog.setChannel(Account.PAY_TYPE_WECHAT);
			interfaceMessageLog.setBizId(tradeOrderNo);
			interfaceMessageLog.setRequestMessage(requestText.toString());
			interfaceMessageLog.setResponseMessage(jsonResult);
			interfaceMessageLog.setTs(new Date());
			interfaceMessageLogService.addInterfaceMessageLog(interfaceMessageLog);
		}

	}
	
	
	
	
	/**
	 * 公众账号支付统一下单
	 * @param body 商品描述
	 * @param orderNo 商户订单号 
	 * @param orderFee 总金额-分
	 * @param notifyUrl 通知地址
	 * @param request
	 * @param response
	 * @return
	 * @throws PayException
	 */
	public static void rechargeForApp(String body,String tradeOrderNo,Double orderFee,Date orderCreateTime,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StringBuffer requestText = new StringBuffer();
		String jsonResult=null;
		try {
			String notifyUrl=Constants.BASE_URL+request.getContextPath()+"/wxpay/jsPayRechargeBackRcveive.action";
			
			WeixinPayAccount weixinAccount = JSON.parseObject(Weixin4jConfigUtil.getValue("appaccount"), WeixinPayAccount.class);
			
			String spbillCreateIp=CookieTool.getIpAddr(request);
			if(spbillCreateIp!=null&&spbillCreateIp.contains(",")){
				spbillCreateIp=spbillCreateIp.split(",")[0];
			}
			//交易结束时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(orderCreateTime);
			calendar.add(Calendar.MINUTE, Constants.PAY_TIMEOUT_MINUTE);
			
			String attach=null;
			String outTradeNo=tradeOrderNo;
			Double totalFee=orderFee.doubleValue();
			Date timeStart=orderCreateTime;
			Date timeExpire=calendar.getTime();
			String limitPay=null;
			String goodsTag=null;
			
			
			//请求报文记录
			

			requestText.append("&body="+body);
			requestText.append("&outTradeNo="+outTradeNo);
			requestText.append("&totalFee="+totalFee);
			requestText.append("&notifyUrl="+notifyUrl);
			requestText.append("&spbillCreateIp="+spbillCreateIp);
			requestText.append("&attach="+attach);
			requestText.append("&requestText="+DateUtil.fortmat2yyyyMMddHHmmss(timeStart));
			requestText.append("&timeExpire="+DateUtil.fortmat2yyyyMMddHHmmss(timeExpire));
			requestText.append("&goodsTag="+goodsTag);
			requestText.append("&limitPay="+limitPay);
			
			requestText.append(weixinAccount.toString());
			
		
			MchPayRequest mchPayRequest=createPayAppRequest(weixinAccount, body, outTradeNo, totalFee, notifyUrl, spbillCreateIp, attach, timeStart, timeExpire, goodsTag, limitPay);
			jsonResult= JSON.toJSONString(mchPayRequest);
			request.setAttribute("jsonResult", jsonResult);
		} catch (Exception e) {
			jsonResult=e.getMessage();
			throw e;
		}finally{
			
			
			InterfaceMessageLog interfaceMessageLog =new InterfaceMessageLog();
			interfaceMessageLog.setType(Account.TYPE_RECHARGE);
			interfaceMessageLog.setChannel(Account.PAY_TYPE_WECHAT);
			interfaceMessageLog.setBizId(tradeOrderNo);
			interfaceMessageLog.setRequestMessage(requestText.toString());
			interfaceMessageLog.setResponseMessage(jsonResult);
			interfaceMessageLog.setTs(new Date());
			interfaceMessageLogService.addInterfaceMessageLog(interfaceMessageLog);
		}

	}
	
	
	
	public static MchPayRequest createPayAppRequest(	WeixinPayAccount weixinAccount,  String body,
			String outTradeNo, double totalFee, String notifyUrl,String createIp, String attach, Date timeStart, Date timeExpire,
			String goodsTag, String limitPay) throws WeixinPayException {
		MchPayPackage payPackage = new MchPayPackage(weixinAccount, null,
				body, outTradeNo, totalFee, notifyUrl, createIp,
				TradeType.APP);
		payPackage.setAttach(attach);
		payPackage.setTimeStart(timeStart);
		payPackage.setTimeExpire(timeExpire);
		payPackage.setGoodsTag(goodsTag);
		payPackage.setLimitPay(limitPay);
		String paySignKey = weixinAccount.getPaySignKey();
		payPackage.setSign(PayUtil.paysignMd5(payPackage, paySignKey));
		PrePay prePay = PayUtil.createPrePay(payPackage, paySignKey);
		MchPayRequest jsPayRequest = new MchPayRequest(prePay);
		jsPayRequest.setSignType(SignType.MD5);
		jsPayRequest.setPaySign(PayUtil.paysignMd5(jsPayRequest, paySignKey));
		return jsPayRequest;
	}

	
	
	
	
	
	/**
	 * 微信退款
	 * @param transactionId
	 * @param outRefundNo
	 * @param totalFee
	 * @param refundFee
	 * @return
	 * @throws Exception
	 */
	public static PayResponseMessage refund(String transactionId,String outRefundNo,double totalFee, double refundFee){
		
		
		
		StringBuffer requestText=new StringBuffer();
		StringBuffer responseText=new StringBuffer();
		PayResponseMessage payResponseMessage= new PayResponseMessage();;
		try {
			
			
			
			
			WeixinPayAccount weixinAccount = JSON.parseObject(Weixin4jConfigUtil.getValue("account"), WeixinPayAccount.class);
			
			
			WeixinPayProxy proxy = new WeixinPayProxy();
			File caFile = new File(caFilePath);
			IdQuery idQuery = new IdQuery(transactionId, IdType.TRANSACTIONID);
			
			

			com.foxinmy.weixin4j.payment.mch.RefundResult result=	proxy.refundApply(new FileInputStream(caFile), idQuery, outRefundNo, totalFee, refundFee, CurrencyType.CNY, weixinAccount.getPartnerId());
			
			responseText.append(result);
			System.err.println(result);
			
			String sign = result.getSign();
			result.setSign(null);
			
			
			String valiSign = PayUtil.paysignMd5(result, weixinAccount.getPaySignKey());
			System.err.println(String.format("sign=%s,valiSign=%s", sign, valiSign));
			
			
			if("SUCCESS".equals(result.getReturnCode()) &&"SUCCESS".equals(result.getResultCode())&&valiSign.equals(sign)){
				
				/**
				 * outTradeNo=CZ20151207145154540378,
				 * transactionId=1000700605201512071946689237,
				 * 
				 * outRefundNo=TK20151207054419, 
				 * refundId=2000700605201512070094871147,
				 * 
				 */
			
			
				/*payResponseMessage.setTransactionId(result.getTransactionId());
				payResponseMessage.setOutRefundNo(result.getOutRefundNo());
				
				
				payResponseMessage.setOutTradeNo(result.getOutTradeNo());
				payResponseMessage.setRefundId(result.getRefundId());*/
				
				payResponseMessage.setOrderId(result.getOutRefundNo());
				payResponseMessage.setQueryId(result.getRefundId());
				payResponseMessage.setPayType(Account.PAY_TYPE_WECHAT);
				
				
				
					refundService.refundCallBack(payResponseMessage);
				
			
				
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("微信退款异常",e);
		}finally{
			
			InterfaceMessageLog interfaceMessageLog =new InterfaceMessageLog();
			interfaceMessageLog.setType(Account.TYPE_REFUND);
			interfaceMessageLog.setChannel(Account.PAY_TYPE_WECHAT);
			interfaceMessageLog.setBizId(payResponseMessage.getOutRefundNo());
			interfaceMessageLog.setRequestMessage(requestText.toString());
			interfaceMessageLog.setResponseMessage(responseText.toString());
			interfaceMessageLog.setTs(new Date());
			interfaceMessageLogService.addInterfaceMessageLog(interfaceMessageLog);
		
		}
		
		return payResponseMessage;
	}

	
	
	
	/**
	 * 公众账号支付统一下单
	 * @param openId
	 * @param body 商品描述
	 * @param orderNo 商户订单号 
	 * @param orderFee 总金额-分
	 * @param notifyUrl 通知地址
	 * @param request
	 * @param response
	 * @return
	 * @throws PayException
	 */
	public static void orderPay(String openId,String body,String tradeOrderNo,Double orderFee,Date orderCreateTime,String step,HttpServletRequest request,HttpServletResponse response) throws Exception{
		StringBuffer requestText = new StringBuffer();
		String jsonResult=null;
		try {
			String notifyUrl=null;
			if(OrderPayService.STEP_CRATE.equals(step)){
				 notifyUrl=Constants.BASE_URL+request.getContextPath()+"/wxpay/jsPayOrderPayBackRcveive.action";
			}else if(OrderPayService.STEP_FINISH.equals(step)){
				 notifyUrl=Constants.BASE_URL+request.getContextPath()+"/wxpay/jsPayOrderFinishPayBackRcveive.action";
			}else{
				throw new Exception("未知的支付环节");
			}
		
			
			WeixinPayAccount weixinAccount = JSON.parseObject(Weixin4jConfigUtil.getValue("account"), WeixinPayAccount.class);
			
			String spbillCreateIp=CookieTool.getIpAddr(request);
			if(spbillCreateIp!=null&&spbillCreateIp.contains(",")){
				spbillCreateIp=spbillCreateIp.split(",")[0];
			}
			
	
			//交易结束时间
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(orderCreateTime);
			calendar.add(Calendar.MINUTE, Constants.PAY_TIMEOUT_MINUTE);
			
			String attach=null;
			String outTradeNo=tradeOrderNo;
			Double totalFee=orderFee.doubleValue();
			Date timeStart=orderCreateTime;
			Date timeExpire=calendar.getTime();
			String limitPay=null;
			String goodsTag=null;
			
			//请求报文记录
			
			requestText.append("&openId="+openId);
			requestText.append("&body="+body);
			requestText.append("&outTradeNo="+outTradeNo);
			requestText.append("&totalFee="+totalFee);
			requestText.append("&notifyUrl="+notifyUrl);
			requestText.append("&spbillCreateIp="+spbillCreateIp);
			requestText.append("&attach="+attach);
			requestText.append("&requestText="+DateUtil.fortmat2yyyyMMddHHmmss(timeStart));
			requestText.append("&timeExpire="+DateUtil.fortmat2yyyyMMddHHmmss(timeExpire));
			requestText.append("&goodsTag="+goodsTag);
			requestText.append("&limitPay="+limitPay);
			
			requestText.append(weixinAccount.toString());
			
			MchPayRequest mchPayRequest=PayUtil.createPayJsRequest(weixinAccount, openId, body, outTradeNo, totalFee, notifyUrl, spbillCreateIp, attach, timeStart, timeExpire, goodsTag, limitPay);
			jsonResult= JSON.toJSONString(mchPayRequest);
			
			request.setAttribute("jsonResult", jsonResult);
		} catch (Exception e) {
			jsonResult=e.getMessage();
			throw e;
		}finally{
			
			
			InterfaceMessageLog interfaceMessageLog =new InterfaceMessageLog();
			interfaceMessageLog.setType(Account.TYPE_RECHARGE);
			interfaceMessageLog.setChannel(Account.PAY_TYPE_WECHAT);
			interfaceMessageLog.setBizId(tradeOrderNo);
			interfaceMessageLog.setRequestMessage(requestText.toString());
			interfaceMessageLog.setResponseMessage(jsonResult);
			interfaceMessageLog.setTs(new Date());
			interfaceMessageLogService.addInterfaceMessageLog(interfaceMessageLog);
		}

	}
	
	
	
	
	/**
	 * 企业付款
	 * @param outTradeNo
	 * @param openId
	 * @param desc
	 * @param amount
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public static void mpPayment(String outTradeNo,String openId,String desc,double amount,HttpServletRequest request,HttpServletResponse response) throws Exception {
				StringBuffer requestText = new StringBuffer();
				String responseText=null;
		
				try{
					
					String spbillCreateIp=CookieTool.getIpAddr(request);
					if(spbillCreateIp!=null&&spbillCreateIp.contains(",")){
						spbillCreateIp=spbillCreateIp.split(",")[0];
					}
					
					requestText.append("outTradeNo="+outTradeNo);
					requestText.append("&openId="+openId);
					requestText.append("&desc="+desc);
					requestText.append("&amount="+amount);
					requestText.append("&spbillCreateIp="+spbillCreateIp);
					
					MPPayment payment = new MPPayment(outTradeNo,
							openId,
							MPPaymentCheckNameType.NO_CHECK, desc, amount, spbillCreateIp);
					
					
					WeixinPayAccount ACCOUNT3 = JSON.parseObject(Weixin4jConfigUtil.getValue("account"), WeixinPayAccount.class);
			
					WeixinPayProxy PAY3 = new WeixinPayProxy(ACCOUNT3);
					File caFile = new File(caFilePath);
					MPPaymentResult result = PAY3.mpPayment(
							new FileInputStream(caFile), payment);
					
					PayResponseMessage payResponseMessage= new PayResponseMessage();;
					payResponseMessage.setOrderId(result.getOutTradeNo());
					payResponseMessage.setQueryId(result.getTransactionId());
					payResponseMessage.setPayType(Account.PAY_TYPE_WECHAT);
					refundService.refundCallBack(payResponseMessage);
					
					responseText=result.toString();
				/*	result.getTransactionId();
					result.getOutTradeNo();
					result.getPaymentTime()*/;
					
					System.err.println(result);
					
			} catch (Exception e) {
				responseText=e.getMessage();
				throw e;
			}finally{
				
				
				InterfaceMessageLog interfaceMessageLog =new InterfaceMessageLog();
				interfaceMessageLog.setType(Account.TYPE_RECHARGE);
				interfaceMessageLog.setChannel(Account.PAY_TYPE_WECHAT);
				interfaceMessageLog.setBizId(outTradeNo);
				interfaceMessageLog.setRequestMessage(requestText.toString());
				interfaceMessageLog.setResponseMessage(responseText);
				interfaceMessageLog.setTs(new Date());
				interfaceMessageLogService.addInterfaceMessageLog(interfaceMessageLog);
			}

	}
	
	
	
	/**
	 * 扫码支付
	 * @param openId
	 * @param body
	 * @param tradeOrderNo
	 * @param totalFee
	 * @param orderCreateTime
	 * @param request
	 * @param response
	 * @return 二维码地址
	 * @throws Exception
	 */
	public static String  NativePay(String openId,String body,String tradeOrderNo,Double totalFee,Date orderCreateTime,String type,HttpServletRequest request,HttpServletResponse response) throws Exception{
		
		
		
		StringBuffer requestText=new StringBuffer();
		StringBuffer responseText=new StringBuffer();
		PayResponseMessage payResponseMessage= new PayResponseMessage();;
		try {
			String  notifyUrl=null;
			if("recharge".equals(type)){
				System.out.println("==============recharge");
				  notifyUrl=Constants.BASE_URL+request.getContextPath()+"/wxpay/nativePayRechargeBackRcveive.action";
			}else if("order".equals(type)){
				System.out.println("===============order");
				  notifyUrl=Constants.BASE_URL+request.getContextPath()+"/wxpay/nativePayOrderBackRcveive.action";
			}else{
				throw new Exception("扫码支付未知的支付类型");
			}
			
			WeixinPayAccount weixinAccount = JSON.parseObject(Weixin4jConfigUtil.getValue("account"), WeixinPayAccount.class);
			
			 Calendar calendar = Calendar.getInstance();
			 calendar.setTime(orderCreateTime);
			 calendar.add(Calendar.MINUTE, Constants.PAY_TIMEOUT_MINUTE);
			 
			 
			 String spbillCreateIp=CookieTool.getIpAddr(request);
			if(spbillCreateIp!=null&&spbillCreateIp.contains(",")){
					spbillCreateIp=spbillCreateIp.split(",")[0];
			}
			if(spbillCreateIp!=null&&spbillCreateIp.contains(":")){
				spbillCreateIp="127.0.0.1";
			}
				
			String codeUrl=PayUtil.createNativePayRequestURL(weixinAccount, null, body, tradeOrderNo, totalFee, notifyUrl, spbillCreateIp, null, orderCreateTime, calendar.getTime(), null, null);
			
		 
			String savePath = request.getSession().getServletContext().getRealPath("/").replace("\\", "/")+"portal/common/images/qrcode/";
			Long timeStamp = new Date().getTime();
			String imgSavePath = savePath + timeStamp + ".jpg";
			
			//请求带字符参数二维码
			WeixinUtil.creatQRCode(codeUrl,imgSavePath);

			
			return "/portal/common/images/qrcode/" + timeStamp + ".jpg";
		
			
		
			
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("微信扫码支付异常",e);
		}finally{
			
			InterfaceMessageLog interfaceMessageLog =new InterfaceMessageLog();
			interfaceMessageLog.setType(Account.TYPE_REFUND);
			interfaceMessageLog.setChannel(Account.PAY_TYPE_WECHAT);
			interfaceMessageLog.setBizId(payResponseMessage.getOutRefundNo());
			interfaceMessageLog.setRequestMessage(requestText.toString());
			interfaceMessageLog.setResponseMessage(responseText.toString());
			interfaceMessageLog.setTs(new Date());
			interfaceMessageLogService.addInterfaceMessageLog(interfaceMessageLog);
		
		}
		
	}
		

	

	public static InterfaceMessageLogService getInterfaceMessageLogService() {
		return interfaceMessageLogService;
	}

	public static void setInterfaceMessageLogService(InterfaceMessageLogService interfaceMessageLogService) {
		WxPayUtil.interfaceMessageLogService = interfaceMessageLogService;
	}


	
	
	
}
