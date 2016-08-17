<%@page import="java.util.Date"%>
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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<base href="<%=basePath%>">
<link rel="shortcut icon" href="<%=path%>/common/css/images/favicon.ico" type="image/x-icon" /> 
<link href="common/css/common.css" rel="stylesheet" type="text/css">

<link href="common/css/main/xenon-core.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="common/js/common.js"></script>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="common/js/jquery.cookie.js"></script>
<script type="text/javascript" src="common/js/jquery.validate.min.js"></script>
<script type="text/javascript" src="common/js/lhgdialog4/lhgdialog/lhgdialog.min.js?skin=idialog"></script>

<style type="text/css">
.error{position:inherit;top:0px;}
</style>
<script type="text/javascript">


function getForm(){
	return $("#eform");
}
function isValid(){

	return true;

}


</script>
</head>

<body style="height: 80%">
	<div class="table_con" style="margin-left:50px; ">
	
		
		 <table  class="xxgl" width="95%"  border="0" cellpadding="0" cellspacing="0" >
                      <tr>
                        <td class="zuo">交易类型：</td>
                        <td class="you"><ww:property value="#request.type" /></td>
                      </tr>
                      <tr>
                        <td  class="zuo">交易金额：</td>
                        <td  class="you"><ww:property value="#request.amount" /></td>
                      </tr>
                      <tr>
                        <td class="zuo">支付方式：</td>
                        <td  class="you"><ww:property value="#request.payType" /></td>
                      </tr>
                      <tr>
                        <td class="zuo">支付账户：</td>
                        <td class="you"><ww:property value="#request.alipayAccount" /></td>
                      </tr>
                      <tr>
                        <td class="zuo">交易时间：</td>
                        <td class="you"><ww:property value="#request.tradeTime" /></td>
                      </tr>
                       <tr>
                        <td class="zuo">订单ID：</td>
                        <td class="you"><ww:property value="#request.orderId" /></td>
                      </tr>
                       <tr>
                        <td class="zuo">自动取消：</td>
                        <td class="you"><ww:property value="#request.orderAutoClear" /></td>
                      </tr>
                    </table>
	</div> 
</body>


</html>