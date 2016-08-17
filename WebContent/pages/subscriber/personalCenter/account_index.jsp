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

function gotoApplyRefund(){
	  $.ajax({
		type: "POST",
	    url: "account/gotoApplyRefundCheck.action",
	    dataType:'json',	  
	    success: function(data) {
			if(0==data.result){
				window.location.href="<%=path%>/account/gotoApplyRefund.action";
		    }else{
		    	window.parent.alertMsg("提示",data.msg);
	    		//window.parent.parent.alertMsg(data.msg,"fail");
		    }
	    }
	});
		
  
}
function  gotoRecharge(){
		$.ajax({
			type: "POST",
		    url: "account/gotoRechargeCheck.action",
		    dataType:'json',
		    success: function(data) {
				if(0==data.result){
					window.location.href="<%=path%>/account/gotoRecharge.action";
			    }else{
			    	window.parent.alertMsg("提示",data.msg);
		    		//window.parent.parent.alertMsg(data.msg,"fail");
			    }
		    }
		});
}

function gotoCreditCardBinding(){
	$.ajax({
		type: "POST",
	    url: "account/gotoCreditCardBindingCheck.action",
	    dataType:'json',
	    success: function(data) {
			if(0==data.result){
				window.location.href="<%=path%>/account/gotoCreditCardBinding.action";
		    }else{
		    	window.parent.alertMsg("提示",data.msg);
	    		//alert(data.msg);
		    }
	    }
	});
	  
 }
</script>
</head>


          	
          	
<body><!--账户管理 开始-->
        <div class="zhgl">
            <div class="xyk_tit">账户管理</div>
            <div class="zhgl_name">
                <img src="common/portal/images/toux.jpg" width="115" height="115"  style="border-radius:300px;border:2px solid #fff; float:left; margin:7px 0 0 62px;"/>
                <span class="zh-n fl">
                  <h2>
                  
                  	<ww:if test="@com.dearho.cs.util.DateUtil@isPM()">上午好</ww:if>
          			<ww:else>下午好</ww:else> 
          			
          			<ww:if test="#session.subscriber.name!=null">
          				,<ww:property value="#session.subscriber.name"/>！
          		   </ww:if>
          		 <!--    <ww:elseif test="#session.subscriber.phoneNo!=null">
          		        ,<ww:property value="#session.subscriber.phoneNo"/>！
          		   </ww:elseif>
          		-->	
          			
          			</h2>
                  <p>账户名：
                  	<ww:property  value="#session.subscriber.phoneNo.substring(0, 3)"/>****<ww:property  value="#session.subscriber.phoneNo.substring(7, 11)"/>
                 	<span class="sc">上次操作时间：<ww:property value="transDateString(account.lastOperateTime)"/></span>
                 </p>
                </span>
              
            </div>
            <table width="200" border="0" class="zhgl_zhonge" cellpadding="0"  cellspacing="1" bgcolor="#d9d9d9">
                  <tr>
                    <td colspan="3" height="90">
                     <div class="nm">
                        <p class="z-e">账户总额</p>
                        <p class="money"><ww:property value="formatAmount(account.amount)"/><span>元</span></p>
                     </div>
                     <div class="zh_btn">
                          <div class="zh_btn_cz" style="cursor:pointer;" onclick="gotoRecharge()">充值</div>
                          <div class="zh_btn_tk" style="cursor:pointer;" onclick="gotoApplyRefund()" >退款</div>
                          <div class="zh_btn_xyk" style="cursor:pointer;" onclick="gotoCreditCardBinding()" >信用卡管理</div>
                     
                     </div>
                    
                    </td>
                   
                  </tr>
                  <tr>
                    <td width="293">
                        <span class="tx1">
                                <p class="tixian">可提现</p>
                                <p class="tx1-money"><ww:property value="formatAmount(account.amount)"/><span>元</span></p>
                        </span>
                    </td>
                    <td width="242">
                        <span class="tx2">
                                <p class="tixian">可用余额</p>
                                <p class="tx2-money"><ww:property value="formatAmount(account.amount)"/><span>元</span></p>
                        </span>
                    </td>
                    <td>
                         <span class="tx3">
                                <p class="tixian">冻&nbsp;&nbsp;结</p>
                                <p class="tx3-money">0.00<span>元</span></p>
                         </span>
                    
                    </td>
                  </tr>
             </table>
              
        </div>
      <!--账户管理 结束-->
</body>
</html>
