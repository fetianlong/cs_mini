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
<title>注册成功</title>
<link rel="shortcut icon" href="<%=path%>/common/css/images/favicon.ico" type="image/x-icon" /> 
<link rel="stylesheet" type="text/css" href="common/portal/css/style.css"/>
<script type="text/javascript" src="common/js/popup_layer.js"></script>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript">
function showCenterIndex(){
	window.location.href="<%=path %>/subscriber/centerIndex.action";
}
</script>
</head>
<body>
<ww:include page="/pages/subscriber/header.jsp"></ww:include>
<div class="center">
      <div class="step">
         <img src="common/portal/images/zhuce3.png" width="1002" />        
         <ul>
           <li class="yh">用户注册</li>
           <li class="sm">实名认证</li>
           <li class="zc">注册成功</li>          
         </ul>       
      </div>
       <div class="tjjg">
          您的资料已成功提交，请耐心等待结果。
       </div>
       <div class="tixing">
          我们将在5个工作日内进行审核，届时将以手机短信的方式通知您审核结果。<br />

           <input class="input_sub" type="button" onclick="showCenterIndex();" value="进入个人中心" style=" width:282px; height:39px; margin:0 auto; display:inline-block; color:#fff; font-size:16px; font-weight:bold; line-height:39px;background:url(common/portal/images/text_btn.png) no-repeat; border:none; margin-left:20px;cursor:pointer;" />
       </div>
      
</div>
<div class="footer"></div>
</body>
</html>