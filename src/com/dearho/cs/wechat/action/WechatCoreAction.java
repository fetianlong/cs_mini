package com.dearho.cs.wechat.action;

import java.util.Date;

import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.wechat.pojo.Menu;
import com.dearho.cs.wechat.pojo.WechatTokenInfo;
import com.dearho.cs.wechat.service.WechatTokenInfoService;
import com.dearho.cs.wechat.service.WechatUserInfoService;
import com.dearho.cs.wechat.util.MenuManager;
import com.dearho.cs.wechat.util.WeixinUtil;

/**
 * 微信核心控制类
 * 
 * @author wangjing
 * 
 */
public class WechatCoreAction extends AbstractAction {

	private static final long serialVersionUID = -7774959106488827153L;

	private WechatTokenInfoService wechatTokenInfoService;

	private WechatUserInfoService wechatUserInfoService;
	
	private String imgShowPath;

	@Override
	public String process() {
		return SUCCESS;
	}

	/**
	 * 创建菜单
	 * 
	 * @return
	 */
	public String creatWechatMenu() {
		// 调用接口获取access_token
		WechatTokenInfo tokenInfo = WeixinUtil.getAccessToken();
		Menu menu = MenuManager.getMenu();
		if (null != tokenInfo) {
			// 调用接口创建菜单
			int resultMark = WeixinUtil
					.createMenu(menu, tokenInfo.getWxValue());

			// 判断菜单创建结果
			if (0 == resultMark) {
				log.info("菜单创建成功");
				result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,
						"菜单创建成功!");
				return SUCCESS;
			} else {
				log.info("菜单创建失败，错误码：" + result);
				result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,
						"菜单创建失败!");
				return ERROR;
			}
		}
		return SUCCESS;
	}

	/**
	 * 查询数据库中的access_token
	 * 
	 * @return
	 */
	public String getSavedAccessToken() {
		WechatTokenInfo accessToken = wechatTokenInfoService
				.getAccessToken("access_token");
		if (accessToken != null) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,
					"当前的access_token=" + accessToken.getWxValue());
		} else {
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,
					"当前的access_token为空");
		}
		return SUCCESS;
	}

	/**
	 * 请求JsapiTicket，请求过程中已经保存
	 * 
	 * @return
	 */
	public String saveJsapiTicket() {
		WechatTokenInfo jsapiTicketInfo = WeixinUtil.getJsapiTicket();

		if (jsapiTicketInfo != null) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,
					"已经得到并保存jsapi_ticket=" + jsapiTicketInfo.getWxValue());
		} else {
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,
					"jsapi_ticket获取失败");
		}
		return SUCCESS;
	}

	/**
	 * 查询数据库中的JsapiTicket
	 * 
	 * @return
	 */
	public String queryJsapiTicket() {
		WechatTokenInfo accessToken = wechatTokenInfoService
				.getAccessToken("jsapi_ticket");
		if (accessToken != null) {
			result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,
					"当前的jsapi_ticket=" + accessToken.getWxValue());
		} else {
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED,
					"当前的jsapi_ticket为空");
		}
		return SUCCESS;
	}

	/**
	 * 生成二维码
	 * 
	 * @return
	 * @throws Exception 
	 * 
	 * <img src="http://qr.liantu.com/api.php?bg=f3f3f3&fg=ff0000&gc=222222&el=l&w=200&m=10&text=www.liantu.com"/>
	 */
	public String createQRCode() throws Exception {
		getRequest().setCharacterEncoding("utf-8");//不编码的话会出现乱码
		
		//获取存储路径
		String savePath = getRequest().getSession().getServletContext().getRealPath("/").replace("\\", "/")+"mobile/common/images/qrcode/";
		Long timeStamp = new Date().getTime();
		String imgSavePath = savePath + timeStamp + ".jpg";
		
		//请求带字符参数二维码
//		WeixinUtil.creatQRCode("http://123.57.37.220/cs/portal/pages/about.jsp",imgSavePath);
		WeixinUtil.creatQRCode("88",imgSavePath);
//		WeixinUtil.creatQRCode("http://www.dearho.com",FilePath);
		
		imgShowPath="/mobile/common/images/qrcode/" + timeStamp + ".jpg";
		
		return SUCCESS;
	}
	
	public WechatTokenInfoService getWechatTokenInfoService() {
		return wechatTokenInfoService;
	}

	public void setWechatTokenInfoService(
			WechatTokenInfoService wechatTokenInfoService) {
		this.wechatTokenInfoService = wechatTokenInfoService;
	}

	public WechatUserInfoService getWechatUserInfoService() {
		return wechatUserInfoService;
	}

	public void setWechatUserInfoService(
			WechatUserInfoService wechatUserInfoService) {
		this.wechatUserInfoService = wechatUserInfoService;
	}

	public String getImgShowPath() {
		return imgShowPath;
	}

	public void setImgShowPath(String imgShowPath) {
		this.imgShowPath = imgShowPath;
	}


}
