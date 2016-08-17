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
<title>基本资料</title>
<link rel="shortcut icon" href="<%=path%>/common/css/images/favicon.ico" type="image/x-icon" /> 
<link rel="stylesheet" type="text/css" href="common/portal/css/style.css"/>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="common/js/popup_layer.js"></script>
<style>
 *{ margin:0; padding:0px;}
 body{ margin:0; padding:0px; background-color:#fff;}

</style>

<script type="text/javascript">

function creditCardBinding(){
	  $.ajax({
		type: "POST",
	    url: "account/creditCardBinding.action",
	    dataType:'json',
	    success: function(data) {
	     
			if(0==data.result){
				window.parent.alertMsg("提示","绑定成功");
				
				window.location.href="<%=path %>/account/gotoCreditCardBinding.action";
		    }else{
	    		window.parent.alertMsg("提示",data.msg);
		    }
	    }
	});
		
	 
	
}
function creditCardUnbinding(){
		$.ajax({
			type: "POST",
		    url: "account/creditCardUnbinding.action",
		    dataType:'json',
		    success: function(data) {
		     
				if(0==data.result){

					window.parent.alertMsg("提示","解绑成功");
					window.location.href="<%=path %>/account/gotoCreditCardBinding.action";
			    }else{
			    	window.parent.alertMsg("提示",data.msg);
			    }
		    }
		});
}

</script>
</head>

<body>
<!--信用卡管理 开始-->
        <div class="zhgl">
                    <div class="xyk_tit">信用卡管理</div>
                    <div class="xyk_kt">
                    	
                    	
                    	<ww:if test="accountCreditCardBinding==null">
						   	   <p class="bd">您未开通“先用车后付款”的支付方式<div class="jb_btn"  onclick="creditCardBinding()"  style="cursor:pointer;width:68px; margin:0; margin-left:20px;">绑定信用卡</div></p>
						   	   
						  </ww:if>
						  <ww:else>
						  
 
                    	
                        <p class="bd">您已开通“先用车后付款”的支付方式（已绑定您的信用卡）：</p>
                        <table width="782" border="0" class="xybangding" cellpadding="0"  cellspacing="1" bgcolor="#d9d9d9">
                              <tr>
                                <th width="187" height="48">银行名称  </th>
                                <th width="213">卡号</th>
                                <th width="166">状态</th>
                                <th width="">操作</th>                  
                              </tr>
                              <tr>
                                <td height="62"><img src="common/portal/images/zxyinhang.jpg" /></td>
                                <td> **** **** **** **** <ww:property  value="accountCreditCardBinding.creditCardNo.substring(accountCreditCardBinding.creditCardNo.length()-4, accountCreditCardBinding.creditCardNo.length())"/></td>
                                <td>已绑定</td>
                                <td>
                                    <div class="jb_btn"  onclick="creditCardUnbinding()"  style="cursor:pointer;">解绑</div>
                                </td>                 
                              </tr>
                         </table>  
                          </ww:else>               
                    </div>
                    <div class="cjwt">
                        <p class="cjwt_tit">常见问题：</p>
                        <p class="cjwt_tit_1">问：什么是"先用车后付钱"?</p>
                        <p class="cjwt_tit_2">
                             答：易到用车推出“先用车后付款”服务，乘客在网站或手机应用下单预订车辆不用预先付款，预定完成后司机将会到您指定的地址去接您。<br />
        用车结束后再自行付款，服务更加人性化、更安全、更便捷！
                        </p>                          
                        <p class="cjwt_tit_1">问：怎么才能享受“先用车后付款”的服务？</p>
                        <p class="cjwt_tit_2">
                            答：首次下单的用户，只需在下单时根据提示“验证信用卡”或“账户余额”大于所选车辆和服务的最低消费额，就可以享受易到用车“先用车后付款”的服务。
                        </p>                        
                        <p class="cjwt_tit_1">问：一张信用卡可以在多个帐户使用吗？</p>
                        <p class="cjwt_tit_2">
                            答：可以，但为了您的信用卡安全，一张信用卡只能在2个帐户使用。
                        </p>           
                        <p class="cjwt_tit_1">问：用车结束后，我怎么付钱?</p>
                        <p class="cjwt_tit_2">
                             答：用车结束后，您可以通过4种渠道付钱。<br />
                             1）网站付款：用车结束后登录网站会员中心，在待付款的订单引导中使用网上银行进行付款。<br />
                             2）手机应手付款：在手机上打开易到租车应用，登录之后在我的订单中进行网银付款。<br />
                             3）语音支付：致电400-1111-777根据语音提示进行付款。<br />
                             4）委托易到代扣款：用车结束2小时内您如没有操作付款，易到将从您的代扣信用卡相应金额，或代扣您的易到账户余额。

                        </p>                        
                        <p class="cjwt_tit_1">问：我可以验证多张信用卡吗?</p>
                        <p class="cjwt_tit_2">
                             答：不可以，只充许验证一张信用卡。如果想更换信用卡，您可以将已验证的信用卡取消验证后，就可以验证新卡了。
                        </p>                         
                    </div> 
                    
        </div>
     
      <!--信用卡管理 结束-->
</body>
</html>
