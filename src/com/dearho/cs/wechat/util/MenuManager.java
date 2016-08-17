package com.dearho.cs.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dearho.cs.wechat.pojo.WechatTokenInfo;
import com.dearho.cs.wechat.pojo.Button;
import com.dearho.cs.wechat.pojo.ComplexButton;
import com.dearho.cs.wechat.pojo.Menu;
import com.dearho.cs.wechat.pojo.ViewButton;

/**
 * 菜单管理器类
 * @author wangjing
 *
 */
public class MenuManager {

	private static Logger log = LoggerFactory.getLogger(MenuManager.class);
	
	public static void main(String[] args) {
		//调用接口获取access_token
		WechatTokenInfo aToken = WeixinUtil.getAccessToken();
		
		if(null != aToken){
			//调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), aToken.getWxValue());
			
			//判断菜单创建结果
			if(0 == result){
				System.out.println("+++++++++++++success+++++++++++++++");
				log.info("菜单创建成功");
			}else {
				System.out.println("+++++++++++++fail+++++++++++++++");
				log.info("菜单创建失败，错误码：" + result);
			}
		}
	}

	/**
	 * 微信创建菜单
	 */
	public static void createMenu(){
		WechatTokenInfo aToken = WeixinUtil.getAccessToken();
		
		if(null != aToken){
			//调用接口创建菜单
			int result = WeixinUtil.createMenu(getMenu(), aToken.getWxValue());
			
			//判断菜单创建结果
			if(0 == result){
				System.out.println("+++++++++++++success+++++++++++++++");
				log.info("菜单创建成功");
			}else {
				System.out.println("+++++++++++++fail+++++++++++++++");
				log.info("菜单创建失败，错误码：" + result);
			}
		}
		
	}
	
	public static Menu getMenu() {
		ViewButton btn1 = new ViewButton();
		btn1.setName("我要租车");
		btn1.setType("view");
		btn1.setUrl(WeixinUtil.hostUrlString+"/mobile/toWelcomePage.action");
//		btn1.setUrl(WeixinUtil.hostUrlString+"/mobile/toBookCar.action");
		
		ViewButton btn2 = new ViewButton();
		btn2.setName("当前订单");
		btn2.setType("view");
		btn2.setUrl(WeixinUtil.hostUrlString+"/mobile/toCurrentOrder.action");
		
		ViewButton btn3 = new ViewButton();
		btn3.setName("历史订单");
		btn3.setType("view");
		btn3.setUrl(WeixinUtil.hostUrlString+"/mobile/toOrderList.action");
		
		ViewButton btn4 = new ViewButton();
		btn4.setName("个人中心");
		btn4.setType("view");
		btn4.setUrl(WeixinUtil.hostUrlString+"/mobile/account/index.action");
		
		ViewButton btn5 = new ViewButton();
		btn5.setName("呼叫客服");
		btn5.setType("view");
		btn5.setUrl(WeixinUtil.hostUrlString+"/mobile/account/toCustomerService.action");
		
//		ViewButton btn5 = new ViewButton();
//		btn5.setName("登录/注册");
//		btn5.setType("view");
//		btn5.setUrl(WeixinUtil.hostUrlString+"/mobile/wechatLogin.action");
		
//		ViewButton btn5 = new ViewButton();
//		btn5.setName("生成QR");
//		btn5.setType("view");
//		btn5.setUrl(hostUrlString+"/mobile/createQRCode.action");
//		
//		ViewButton btn6 = new ViewButton();
//		btn6.setName("扫描QR");
//		btn6.setType("view");
//		btn6.setUrl(hostUrlString+"/mobile/scanQRCode.action");
		
//		ComplexButton mainBtn1 = new ComplexButton();
//		mainBtn1.setName("用车");
//		mainBtn1.setSub_button(new ViewButton[]{btn1});
		
		ComplexButton mainBtn2 = new ComplexButton();
		mainBtn2.setName("订单");
		mainBtn2.setSub_button(new ViewButton[]{btn2, btn3});
		
		ComplexButton mainBtn3 = new ComplexButton();
		mainBtn3.setName("账户");
		mainBtn3.setSub_button(new ViewButton[]{btn4, btn5});
		
		Menu menu = new Menu();
		menu.setButton(new Button[]{btn1, mainBtn2, mainBtn3});
		
		return menu;
	}
}
