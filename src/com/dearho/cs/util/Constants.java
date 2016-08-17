package com.dearho.cs.util;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-21
 */
public class Constants {
	
	
	/** APP使用的常量 **/

	public final static int APP_RESULT_CODE_SUCCESS = 0;
	public final static int APP_RESULT_CODE_FAIL = 1;
	public final static int APP_RESULT_CODE_LOGIN = -1;

	
	public final static String SESSION_USER = "user";
	public final static String SESSION_USER_PERMISSION = "user_permission";
	public final static String SESSION_USER_MENU = "user_menu";
	
	
	public final static int RESULT_CODE_SUCCESS = 0;
	public final static int RESULT_CODE_FAILED = 1;
	
	public final static String SESSION_SUBSCRIBER = "subscriber";//前台会员登录session
	
	
	public final static String SUBSCIRBER_REGISTER_PHONE_CODE="registerPhoneCode";///注册短信验证码信息
	public final static String SUBSCIRBER_CHANGE_OLD_PHONE_CODE="oldChangePhoneCode";//修改绑定手机短信验证码
	public final static String SUBSCIRBER_CHANGE_NEW_PHONE_CODE="newChangePhoneCode";//修改绑定手机短信验证码
	public final static String SUBSCRIBER_PHONE_FIND_PWD_CODE="findPwdCode";
	public final static String SUBSCRIBER_LOGIN_CODE="loginCode";
	
	
	public final static Integer REGISTER_SMS_VALID_MINUTE= 5;//注册短信验证码有效时间 单位分 5
	public final static Integer REGISTER_SMS_VALID_RETRY_MINUTE= 2;
	
	
	public final static String 	BASE_URL="http://www.dearho.com";
	
	public final static Integer PAY_TIMEOUT_MINUTE=5;//支付超时分钟数
	public final static Integer ORDER_PAY_TIMEOUT_MINUTE=5;//支付超时分钟数
	
	
	
	public final static Integer REFUND_TRANSIT_DAY=5;//	退款中转时间
	
	/*退款 最近订单 完成时间*/
	public final static Integer REFUND_LATELY_COMPLETION_DAY=15;
	
	public final static Integer ALIPAY_REFUND_VALID_MONTH=3;//阿里 交易可走退款流程期限
	public final static Integer UNIONPAY_REFUND_VALID_MONTH=11;//银联 交易可走退款期限
	public final static Integer WXPAY_REFUND_VALID_MONTH=12;//微信交易可走退款期限
	
	public final static String RETURN_BACK_CAR_PAY_ORDERS = "RETURN_BACK_CAR_PAY_ORDERS";//还车时最后需要支付的订单
	
	
//	public final static Integer maxFrozenMoney=500;
	
	public final static String DEFAULT_PASSWORDS = "123456";
	
	public static final String APP_DES_IV = "6LA2EyQm";
	public static final String APP_DES_KEY = "HeGeA8G3";
	
	/*app返回状态码*/
    public static final Integer APP_LOGIN_SUCCESS    	= 200;//登录成功
    public static final Integer APP_LOGIN_FAIL 			= 201;//登录失败
    public static final Integer APP_LOGIN_NULL 			= 202;//手机号和密码不能为空
    public static final Integer APP_LOGIN_MOBILE_STYLE  = 203;//手机格式错误
    public static final Integer APP_LOGIN_NO_MOBILE 	= 204;//该手机号没有注册
    public static final Integer APP_LOGIN_ERROR_PWD 	= 205;//登录密码错误
    public static final Integer APP_LOGIN_TOKEN 		= 206;//token失效
    public static final Integer APP_MOBILE_NULL 		= 207;//手机号为空
    public static final Integer APP_MOBILE_EXIST 		= 208;//手机号已注册
    public static final Integer APP_MOBILE_CODE_NULL 	= 209;//手机号验证码为空
    public static final Integer APP_MOBILE_CODE_ERROR 	= 210;//手机号验证码错误
    public static final Integer APP_MOBILE_CODE_NO 		= 211;//手机号验证码失效
    public static final Integer APP_MOBILE_OLD_ERROR 	= 212;//当前用户手机号输入有误
    public static final Integer APP_EVENT_STATE_HALF	= 213; //半锁  不能下单/不能操作账户
	public static final Integer APP_EVENT_STATE_FUll	= 214; //全锁 不能登录
	public static final Integer APP_NAME_NULL			= 215; //姓名为空
	public static final Integer APP_STATE_WAIT_CONFIRMED= 216; //资料待审核
	public static final Integer APP_STATE_NORMAL		= 217; //资料已审核
	public static final Integer APP_IDCARD_NULL			= 218; //身份证上传为空
	public static final Integer APP_DRIVING_NULL		= 219; //驾驶证上传为空
	public static final Integer APP_SERVER_EXCEPTION	= 220; //服务器异常
	public static final Integer APP_TYPE_NULL			= 221; //发送短信类型为空
}
