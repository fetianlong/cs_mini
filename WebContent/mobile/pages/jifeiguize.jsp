<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>计费规则</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>


<link rel=stylesheet href="<%=path %>/mobile/common/css/costrule.css">


</head>

<body>
<div class="container-fluid">
  <div class="row">
    <div class="col-md-4 col-md-offset-4">
		<table class="table table-condensed table-responsive">
			<tr>
				<td>&nbsp;</td>
				<td colspan="2">套餐</td>
				<td rowspan="2"><img class="img-responsive center-block" src="<%=path %>/mobile/common/images/costrule/fstc.png">分时计费</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><img class="img-responsive center-block" src="<%=path %>/mobile/common/images/costrule/qrtc.png">全日套餐</td>
				<td><img class="img-responsive center-block" src="<%=path %>/mobile/common/images/costrule/bytc.png">包夜套餐</td>
			</tr>
			<tr>
				<td><img class="img-responsive center-block" src="<%=path %>/mobile/common/images/costrule/jjx.png">经济型</td>
				<td>159<span>元</span>/24h</td>
				<td>99元</td>
				<td>30<span>元</span>/h</td>
			</tr>
			<tr>
				<td><img class="img-responsive center-block" src="<%=path %>/mobile/common/images/costrule/ssx.png">舒适型</td>
				<td>189<span>元</span>/24h</td>
				<td>129元</td>
				<td>50<span>元</span>/h</td>
			</tr>
			<tr>
				<td><img class="img-responsive center-block" src="<%=path %>/mobile/common/images/costrule/hhx.png">豪华型</td>
				<td>299<span>元</span>/24h</td>
				<td>159元</td>
				<td>80<span>元</span>/h</td>
			</tr>
		</table>
		<p class="text-right">单位：人民币 - <i class="fa fa-jpy"></i></p>
	</div>
  </div>
</div>

</body>
</html>
