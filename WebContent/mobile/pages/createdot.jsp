<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>催我建点</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>

<script type="text/javascript">
function createDot(){
	
}

</script>

</head>

<body class="BookingError">
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<img class="img-responsive center-block BookingErrorImg" src="../common/images/booking/bookingerror.png">
		<h4 class="text-center">很遗憾</h4>
		<p class="text-center">附近1公里内没有用车点</p>
		<div class="BottomButton">
			<div class="col-xs-8 col-xs-offset-2">
				<button type="submit" onclick="createDot(<%=request.getParameter("lat") %>,<%=request.getParameter("lng") %>);" class="btn btn-embossed btn-primary btn-block">催我建点</button>
			</div>
		</div>
	</div>
  </div>
</div>

</body>
</html>
