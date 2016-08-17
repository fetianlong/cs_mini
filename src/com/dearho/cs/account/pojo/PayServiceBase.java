package com.dearho.cs.account.pojo;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang.StringUtils;

import com.dearho.cs.account.dao.AccountCardInstanceDao;
import com.dearho.cs.account.dao.AccountTradeRecordDetailDao;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.Constants;

public abstract class PayServiceBase {
	
	public abstract AccountCardInstanceDao getAccountCardInstanceDaoInstance();
	
	public abstract AccountTradeRecordDetailDao getAccountTradeRecordDetailDaoInstance();
	
	/**
	 * 整理扣款的负钱
	 * @param account
	 */
	public void clearUpPaymentCutAmount(Account account){
		List<AccountCardInstance> cardInstanceList=getAccountCardInstanceDaoInstance().queryEnabledCardInstanceByAccountId(account.getSubscriberId());
		List<AccountCardInstance> fuCardInsttanceList=new ArrayList<AccountCardInstance>();
		if(cardInstanceList==null || cardInstanceList.isEmpty()){
			return;
		}
		//取得上次扣款记录
		//在其详情表追加记录
		//负钱
		BigDecimal fu=new BigDecimal(0);
		BigDecimal zheng=new BigDecimal(0);
		
		for(AccountCardInstance instance: cardInstanceList){
			BigDecimal remainingAmount=new BigDecimal(instance.getRemainingAmount());
			if(remainingAmount.compareTo(new BigDecimal(0))<0){
				fu=fu.add(remainingAmount);
				fuCardInsttanceList.add(instance);
			}else{
				zheng=zheng.add(remainingAmount);
			}
		}
		
		//如果正钱不为0，且有付钱，则进行下一步
		if(zheng.compareTo(new BigDecimal(0))>0 && fu.compareTo(new BigDecimal(0))<0){
			//进行扣款

			
		}else{
			return;
		}
		
		
		for(AccountCardInstance fuInstance:fuCardInsttanceList){
			//为子订单扣款
			//取卡顺序一次次取卡消费
			BigDecimal cutAmount=new BigDecimal(0);
			BigDecimal cutMaxAmount=new BigDecimal(-fuInstance.getRemainingAmount());
			
			List<AccountTradeRecord> list=getAccountTradeRecordDetailDaoInstance().queryTradeRecordByBizAndType(fuInstance.getId(),Account.TYPE_CUT_PAYMENT);
			
			String accountTradeRecordId=list.get(0).getId();
			

			int index=1;//扣款顺序
			
			Boolean isEnoughMoney=false;

			//倒叙取实例
			cardInstanceList=getAccountCardInstanceDaoInstance().queryEnabledCardInstanceByAccountId(account.getSubscriberId());
			if(cardInstanceList!=null && cardInstanceList.size()>0){
				for(int i=0;i<cardInstanceList.size();i++){
					    AccountCardInstance cardInstance= cardInstanceList.get(i);
					    BigDecimal instanceAmount = new BigDecimal(cardInstance.getRemainingAmount());  
					
					
						//是否有足够押金、足够的话计算可用余额
						
						//如果还没划够 -划 else 退出
					    if(!isEnoughMoney){
							//充值卡足够扣款
							if(cutAmount.add(instanceAmount).compareTo(cutMaxAmount)>0){
								  
								   //还差多少元
								   BigDecimal difference=cutMaxAmount.subtract(cutAmount);
								   
								   cutAmount=cutAmount.add(difference);
								   
								  
									cardInstance.setRemainingAmount(instanceAmount.subtract(difference).doubleValue());
									cardInstance.setIsValid(AccountCardInstance.IS_VALID_TRUE);
									getAccountCardInstanceDaoInstance().updateAccountCardInstance(cardInstance);
									
									
									
									fuInstance.setRemainingAmount(0d);
									fuInstance.setIsValid(AccountCardInstance.IS_VALID_FALSE);
									getAccountCardInstanceDaoInstance().updateAccountCardInstance(fuInstance);
									
									isEnoughMoney=true;
								   
								   
								   	AccountTradeRecordDetail accountTradeRecordDetail= new AccountTradeRecordDetail();
									accountTradeRecordDetail.setTradeRecordId(accountTradeRecordId);
									accountTradeRecordDetail.setSubscriberId(account.getSubscriberId());
									accountTradeRecordDetail.setAmount(difference.doubleValue());
									accountTradeRecordDetail.setRechargeCardInstanceId(cardInstance.getId());
									accountTradeRecordDetail.setOrderIndex(index++);
									accountTradeRecordDetail.setDescription(null);
									accountTradeRecordDetail.setPaymentAccountId(null);
									accountTradeRecordDetail.setTs(new Date());
									getAccountTradeRecordDetailDaoInstance().addAccountTradeRecordDetail(accountTradeRecordDetail);
								   
								   
							}else if(cutAmount.add(instanceAmount).compareTo(cutMaxAmount)==0){
									//充值卡金额刚够-- 扣款 卡失效
									cutAmount=cutAmount.add(instanceAmount);
									cardInstance.setRemainingAmount(0d);
									cardInstance.setIsValid(AccountCardInstance.IS_VALID_FALSE);
									getAccountCardInstanceDaoInstance().updateAccountCardInstance(cardInstance);
									
									
									fuInstance.setRemainingAmount(0d);
									fuInstance.setIsValid(AccountCardInstance.IS_VALID_FALSE);
									getAccountCardInstanceDaoInstance().updateAccountCardInstance(fuInstance);
									
									isEnoughMoney=true;
									
									
									AccountTradeRecordDetail accountTradeRecordDetail= new AccountTradeRecordDetail();
									accountTradeRecordDetail.setTradeRecordId(accountTradeRecordId);
									accountTradeRecordDetail.setSubscriberId(account.getSubscriberId());
									accountTradeRecordDetail.setAmount(instanceAmount.doubleValue());

									accountTradeRecordDetail.setRechargeCardInstanceId(cardInstance.getId());
									accountTradeRecordDetail.setOrderIndex(index++);
									accountTradeRecordDetail.setDescription(null);
									accountTradeRecordDetail.setPaymentAccountId(null);
									accountTradeRecordDetail.setTs(new Date());
									getAccountTradeRecordDetailDaoInstance().addAccountTradeRecordDetail(accountTradeRecordDetail);
								   
							}else{
								//充值卡金额不够
								cutAmount=cutAmount.add(instanceAmount);
								
								
								fuInstance.setRemainingAmount(new  BigDecimal(fuInstance.getRemainingAmount()).add(instanceAmount).doubleValue());
								fuInstance.setIsValid(AccountCardInstance.IS_VALID_FALSE);
								getAccountCardInstanceDaoInstance().updateAccountCardInstance(fuInstance);
								
								
								cardInstance.setRemainingAmount(0d);
								cardInstance.setIsValid(AccountCardInstance.IS_VALID_FALSE);
								getAccountCardInstanceDaoInstance().updateAccountCardInstance(cardInstance);
								
								
								
								AccountTradeRecordDetail accountTradeRecordDetail= new AccountTradeRecordDetail();
								accountTradeRecordDetail.setTradeRecordId(accountTradeRecordId);
								accountTradeRecordDetail.setSubscriberId(account.getSubscriberId());
								accountTradeRecordDetail.setAmount(instanceAmount.doubleValue());
								accountTradeRecordDetail.setRechargeCardInstanceId(cardInstance.getId());
								accountTradeRecordDetail.setOrderIndex(index++);
								accountTradeRecordDetail.setDescription(null);
								accountTradeRecordDetail.setPaymentAccountId(null);
								accountTradeRecordDetail.setTs(new Date());
								getAccountTradeRecordDetailDaoInstance().addAccountTradeRecordDetail(accountTradeRecordDetail);
								
							}
						}else{
							break;
						}
				}
			}
		}
		
		
	}
	
	/**
	 * 计算押金及可用余额，相应值放到入参account相应字段
	 * 
	 * @param account
	 */
	public void computeFrozenAndUsableAmount(Account account){

		//已排序
		List<AccountCardInstance> cardInstanceList=getAccountCardInstanceDaoInstance().queryEnabledCardInstanceByAccountId(account.getSubscriberId());
	
		BigDecimal frozenAmount = new BigDecimal(0); 
		BigDecimal usableAmount = new BigDecimal(0); 
		
		BigDecimal allAmount = new BigDecimal(0); 

		BigDecimal maxFrozenMoney = new BigDecimal(Integer.parseInt(DictUtil.getCnNameByCode("maxFrozenMoney", "maxFrozenMoney")));
		
		  
	       
		Boolean isEnoughMoney=false;
		//倒叙取实例
		if(cardInstanceList!=null && cardInstanceList.size()>0){
			for(int i=cardInstanceList.size()-1;i>=0;i--){
				    AccountCardInstance cardInstance=cardInstanceList.get(i);
			
				    BigDecimal instanceAmount = new BigDecimal(cardInstance.getRemainingAmount());  
				    
				    allAmount=allAmount.add(instanceAmount);
				
					//是否有足够押金、足够的话计算可用余额
					if(!isEnoughMoney){
						//充值卡余额足够
						if(frozenAmount.add(instanceAmount).compareTo(maxFrozenMoney)>0){
							  
							   BigDecimal difference=maxFrozenMoney.subtract(frozenAmount);
							   
							   usableAmount= usableAmount.add(instanceAmount.subtract(difference));
							   frozenAmount=frozenAmount.add(difference);
							   
							   isEnoughMoney=true;
						}else if(frozenAmount.add(instanceAmount).compareTo(maxFrozenMoney)==0){
							   frozenAmount=frozenAmount.add(instanceAmount);
							   isEnoughMoney=true;
						}else{
							//充值卡金额不够
							frozenAmount=frozenAmount.add(instanceAmount);
						}
					}else{
						usableAmount= usableAmount.add(instanceAmount);
					}
			}
		}
		
		//account赋值
		account.setFrozenAmount(frozenAmount.doubleValue());
		account.setUsableAmount(usableAmount.doubleValue());
		account.setAmount(allAmount.doubleValue());
	}

	
	
	/**
	 * 充值  支付订单编号
	 * @return
	 */
	public String getPayRechargeTradeNo(){
		
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer buf=new StringBuffer("CZ");
		buf.append(format.format(new Date()));
		Random r=new Random(); 
		for(int i=0;i<6;i++){ 
			buf.append(r.nextInt(10));
		}; 
	
		return buf.toString();
	}
	
	
	/**
	 * 扣款 支付订单编号
	 * @return
	 */
	public String getCutPaymentNo(){
		
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer buf=new StringBuffer("KK");
		buf.append(format.format(new Date()));
		Random r=new Random(); 
		for(int i=0;i<6;i++){ 
			buf.append(r.nextInt(10));
		}; 
	
		return buf.toString();
	}
	

	/**
	 * 订单消费 支付订单编号
	 * @return
	 */
	public String getPayOrderTradeNo(){
		
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer buf=new StringBuffer("DD");
		buf.append(format.format(new Date()));
		Random r=new Random(); 
		for(int i=0;i<6;i++){ 
			buf.append(r.nextInt(10));
		}; 
	
		return buf.toString();
	}
	
	/**
	 * 订单消费 支付订单编号
	 * @return
	 */
	public String getMainRefundTradeNo(){
		
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer buf=new StringBuffer("TK");
		buf.append(format.format(new Date()));
		Random r=new Random(); 
		for(int i=0;i<3;i++){ 
			buf.append(r.nextInt(10));
		}; 
	
		return buf.toString();
	}
	
	public String getSubRefundTradeNo(String mainRefundTradeNo){
		
		if(StringUtils.isNotEmpty(mainRefundTradeNo)){
			
			Random r=new Random(); 
			for(int i=0;i<3;i++){ 
				mainRefundTradeNo+=r.nextInt(10);
			}; 
		}
		return mainRefundTradeNo;
	}
	
	public String getRefundTradeNo(){
		
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer buf=new StringBuffer("TK");
		buf.append(format.format(new Date()));
		Random r=new Random(); 
		for(int i=0;i<6;i++){ 
			buf.append(r.nextInt(10));
		}; 
	
		return buf.toString();
	}

	public String getRefundTradeNo2(){
		
		SimpleDateFormat format=new SimpleDateFormat("yyyyMMddHHmmss");
		StringBuffer buf=new StringBuffer("");
		buf.append(format.format(new Date()));
		Random r=new Random(); 
		for(int i=0;i<6;i++){ 
			buf.append(r.nextInt(10));
		}; 
	
		return buf.toString();
	}



/**
 * 
 * @param paytype
 * @param tradeTime
 * @return true为能退款，false为不能退款-走转账流程
 */
public boolean valideCanRefundBack(Integer paytype,Date tradeTime){
	
	Calendar calendar = Calendar.getInstance();
	calendar.setTime(tradeTime);
	if(Account.PAY_TYPE_ALIPAY.equals(paytype)){
		calendar.add(Calendar.MONTH, Constants.ALIPAY_REFUND_VALID_MONTH);
	}else if(Account.PAY_TYPE_WECHAT.equals(paytype)){
		calendar.add(Calendar.MONTH, Constants.WXPAY_REFUND_VALID_MONTH);
	}else if(Account.PAY_TYPE_UNIONPAY.equals(paytype)){
		calendar.add(Calendar.MONTH, Constants.UNIONPAY_REFUND_VALID_MONTH);
	}else{
		throw new RuntimeException("退款：未知的支付类型");
	}
	
	if(new Date().before(calendar.getTime())){
		System.out.println("退款时间段内，能退款");
		return true;
	}else{
		System.out.println("退款时间段内，不能退款，走转账");
		return false;
	}
}
	
}
