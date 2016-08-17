<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@include file="/mobile/pages/common/include.jsp" %>
<!doctype html>
<html lang="zh-CN">
<head>
<meta charset="utf-8">
<title>消费记录</title>

<%@ include file="/mobile/pages/common/common_head.jsp"%>


<link rel=stylesheet href="<%=path %>/mobile/common/css/tradinglist.css">

<script>
var currentPage=<ww:property value="page.currentPage"/>;
var totalPages=<ww:property value="page.totalPages"/>;
var index=1;
$(function(){
	$(".ListBlock .table tbody").on('click', "tr", function(e) {
		/*ajax先修改弹框内容再show*/
		
		var trId=$(this).attr("id");
	  $.ajax({
		type: "POST",
	    url: '<%=path%>/mobile/account/showTradingDetailMini.action?accountTradeRecord.id='+trId,
	    dataType:'html',	  
	    success: function(data) {
	    	$("#Detail").html(data);
			$("#Detail").modal("show");
		   
	    },
		error: function(){
			Alert("暂时无法提交退款请求，请稍后再试");
		}
	});
		
		
	});
	
	
	$(document).scroll(function(e) {
        if ($(document).scrollTop() >= ($(document).height()-$(window).height())) {
        	
        	searchMoreTradeList();
		};
    });
});


function searchMoreTradeList(){
	
	if(currentPage<totalPages){
		currentPage++;
		$.ajax({
			type: "POST",
		    url: '<%=path%>/mobile/account/showTradingListMini.action?page.currentPage='+currentPage,
		    dataType:'html',	  
		    success: function(data) {
		    	$("#tradeTbody").append(data);
			   
		    }
		});
	}
	
	
}
</script>
</head>
<% int index=1; %>
<body>
<div class="container">
		
		
	<div class="row ListBlock">
		<div class="col-xs-12">
			<table class="table table-striped">
				<thead>
					<tr>
						<th>交易号</th>
						<th>金额</th>
						<th>类型</th>
					</tr>
				</thead>
				<tbody id="tradeTbody">
					
						
					
				 			<ww:iterator value="page.mResults"   status="st">
							 	<tr id='<ww:property value="id"/>'>
									
									<ww:if test="@com.dearho.cs.account.pojo.Account@TYPE_RECHARGE==type">
										<th><ww:property value="tradeOrderNo"/></th>
										
										<td class="Color1">+<ww:property  value="formatAmount(amount)"/></td>
										<td class="Color1">充值</td>
									</ww:if>
									<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_REFUND==type">
										<th><ww:property value="tradeOrderNo"/></th>
										
										<td class="Color3">-<ww:property  value="formatAmount(amount)"/></td>
										<td class="Color3">退款</td>
									</ww:elseif>
									<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_ORDER==type">
										<th><ww:property value="tradeOrderNo"/></th>
										
										<td class="Color2">-<ww:property  value="formatAmount(amount)"/></td>
										<td class="Color2">租车</td>
									</ww:elseif>
									<ww:elseif test="@com.dearho.cs.account.pojo.Account@TYPE_CUT_PAYMENT==type">
										<th><ww:property value="tradeOrderNo"/></th>
										
										<td class="Color3"><ww:property  value="formatAmount(amount)"/></td>
										<td class="Color3">扣款</td>
									</ww:elseif>
									
								</tr>
							</ww:iterator>
				
					
				</tbody>
			</table>
		</div>
	</div>
</div>
<div class="modal fade" id="Detail" tabindex="-1" role="dialog" aria-labelledby="Detail">

</div>
</body>
</html>
