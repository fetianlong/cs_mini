<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<base href="<%=basePath%>">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>充值方式</title>
<link rel="shortcut icon" href="<%=path%>/common/css/images/favicon.ico" type="image/x-icon" /> 
<link rel="stylesheet" type="text/css" href="common/portal/css/style.css"/>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="common/js/popup_layer.js"></script>
<style>
 *{ margin:0; padding:0px;}
 body{ margin:0; padding:0px; background-color:#fff;}
</style>

 <script type="text/javascript">
	function AlipayRecharge()
	{
		var intRecharge = document.getElementById("txtAmount").value;
		if (intRecharge > 0)
		{
		/*
			   document.all.ly.style.display="block";   
			   document.all.ly.style.width=document.body.clientWidth;   
			   document.all.ly.style.height=document.body.clientHeight;   
			   document.all.Layer2.style.display='block';  
		*/
			   
			window.parent.alertMsg("提示","请在新开的付款页面完成付款，再查看<a href='javascript:window.parent.parent.gotoPageByIndex(1)'>账户详情</a>");
			window.open('<%=path %>/pages/alipay/alipay_recharge.jsp?WIDtotal_fee='+ intRecharge);
		//	mShow('支付情况', 'data/recharge_message.jsp',350,180,true);
		}
		else{
			window.parent.parent.alertMsg("提示","请先输入您需要充值的金额！");
		}
	}
    </script>
</head>
<body>
 <!--充值 开始-->
 <input type="hidden" id="txtAmount" value='<ww:property  value="#request.rechargeAmount"/>'>
        <div class="zhgl">
             <div class="xyk_tit">充值</div>
             <div class="tuikuan">
                 <div class="chz">
                 <li class="chongzhi-j">充值金额：<span><ww:property  value="#request.rechargeAmount"/></span>元</li>
                 </div>  
                 <ul class="zhifu_ta">
                   <li class="zf_selected">支付宝</li>
                <!--      <li>网上银行</li>-->
                 </ul>
                 <div class="zhifu_con">
                   <div class="zhifu_con1">
                        <img src="common/portal/images/zfb.png" style="margin-top:48px; margin-bottom:15px;"/>
                         <p>提供支付宝账号的支付支持</p>
                    </div>
                     <div class="tuk_sub" style="margin-top:30px;" onclick="AlipayRecharge()">下一步</div>
                 </div>
                 
                 <!--  
                 <div class="zhifu_con_xz">
                  <div class="zhifu_con2">
                           <table width="" border="0" style="margin:0 auto; margin-top:20px;">
                              <tr>
                                 <td width="173" height="60"><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td  width="173"><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td  width="173"><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td  width="173"><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                              </tr>
                              <tr>
                                 <td height="60"><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                              </tr>
                              <tr>
                                 <td height="60"><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                              </tr>
                              <tr>
                                 <td height="60"><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                              </tr>
                              <tr>
                                 <td height="60"><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                              </tr>
                              <tr>
                                 <td height="60"><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                              </tr>
                              <tr>
                                 <td height="60"><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                                 <td><input type="radio" style="position:relative; top:-15px;"  /><img src="<%=path %>/common/portal/images/zxyinhang.jpg" style="margin-left:10px;"/></td>
                              </tr>
                            </table>
                     </div>
                     <div class="tuk_sub" style="margin-top:15px;">下一步</div>
                 </div>
                 -->
             </div>
            
                    
        </div>
        <!--退款 开始-->
        
        <!--          浮层框架开始         -->
      <!--  
         <div id="ly" style="width: 100%; height: 100%; position: absolute; top: 0px; left: 0px; background-color: rgb(45, 45, 45); z-index: 5000; opacity: 0.2; display: none; background-position: initial initial; background-repeat: initial initial;"></div>
      
	    <div id="Layer2" align="center" style="position: absolute; z-index: 3; left: expression((document.body.offsetWidth-540)/2); top: expression((document.body.offsetHeight-170)/10);
	         background-color: #fff; display: none;" >
	        <table width="540" height="300" border="0" cellpadding="0" cellspacing="0" style="border: 0    solid    #e7e3e7;
	             border-collapse: collapse ;" >
	            <tr>
	                <td style="background-color: #73A2d6; color: #fff; padding-left: 4px; padding-top: 2px;
	                     font-weight: bold; font-size: 12px;" height="10" valign="middle">
	                     <div align="right"><a href=JavaScript:; class="STYLE1" onclick="Lock_CheckForm(this);">[关闭]</a> &nbsp;&nbsp;&nbsp;&nbsp;</div></td>
	            </tr>
	            <tr>
	                <td height="130" align="center">
	                	请在新页面进行付款操作。进入账户详情,  <a href="javascript:window.parent.parent.gotoPageByIndex(1)">主页</a>
	                </td>
	            </tr>
	        </table>
	    </div>
	    -->  
    <!--  浮层框架结束-->
        
</body>
</html>
