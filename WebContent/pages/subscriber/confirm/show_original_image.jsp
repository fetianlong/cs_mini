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
<%@ include file="/pages/common/common_head.jsp"%>
<title>原图</title>


</head>
<body style="overflow: auto;">
<div class="cropper-example-1 ">
  <img class="img-responsive center-block"  src="<ww:property value='#request.imageScr'/>" alt="Picture">
</div>

</body>
</html>