<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html >
<head>
<meta charset="utf-8">
<title>支付</title>
<%@ include file="/mobile/pages/common/common_head.jsp"%>


<script type="text/javascript">
function onBridgeReady(){
	   WeixinJSBridge.invoke(
	       'getBrandWCPayRequest', <ww:property value="#request.jsonResult"/>,
	       function(res){ 
	    	
	           if(res.err_msg == "get_brand_wcpay_request:ok" ) { 
	        	  // alert("this is success!")
	        	   window.location.href="<%=path %>/mobile/account/index.action"; 
	           } else if(res.err_msg == "get_brand_wcpay_request:cancel" ) { 
	        	 //  alert("this is cancel!")6
	        	   window.location.href="<%=path %>/mobile/account/index.action"; 
	           } else if(res.err_msg == "get_brand_wcpay_request:fail" ) { 
	        	   //alert("this is fail!")
	        	   window.location.href="<%=path %>/mobile/account/index.action"; 
	           } 
	       }
	   ); 
}
	
	if (typeof WeixinJSBridge == "undefined"){
	   if( document.addEventListener ){
	       document.addEventListener('WeixinJSBridgeReady', onBridgeReady, false);
		  
	   }else if (document.attachEvent){
	       document.attachEvent('WeixinJSBridgeReady', onBridgeReady); 
	       document.attachEvent('onWeixinJSBridgeReady', onBridgeReady);
	   }
	}else{
	   onBridgeReady();
	} 
</script>	
<body>
公众账号支付中...
</body>
</html>
