<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>

<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>提交成功</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>
<link rel=stylesheet href="<%=path %>/mobile/common/css/register.css">
</head>

<body class="Registered">
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<p class="SuccessTip"><span>恭喜您</span></p>
		<h3>提交成功！</h3>
		<div class="BottomButton">
        	<p class="text-center Tips">我们将对您的真实信息进行审核，通过后将以短信的方式通知您，请注意查收。</p>
			<div class="col-xs-8 col-xs-offset-2">
			<a  href="<%=path %>/mobile/subscriber/showBaseInfo.action" >
				<button type="button"class="btn btn-embossed btn-primary btn-block">完成</button>
			</a>
			</div>
		</div>
	</div>
  </div>
</div>

</body>
</html>
