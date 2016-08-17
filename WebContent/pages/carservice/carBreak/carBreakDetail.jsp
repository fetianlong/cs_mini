<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@  taglib prefix="ww" uri="webwork"%>
<ww:bean name="'com.dearho.cs.sys.util.DictUtil'" id="dictUtil" />
<%
String path = request.getContextPath();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>车辆损坏上报</title>
<%@ include file="/pages/common/common_head.jsp"%>


</head>
<body  class="hyglbjPage" style="overflow-y:auto;">
      <div class="tc" >
  		
  		<table class="xxgl">
		   <tbody>
		   <tr >
		   		<td class="zuo"><span>描述</span>：</td>
			   <td class="you" colspan="10">
			   		<ww:property value="carBreak.breakDesc"/>
			   </td>
		   
		   </tr>
		   
		  <tr>
		   	<td class="zuo"><span>照片</span>：</td>
		   
		  	<td class="you">
		  	
		  		<ww:iterator value="imgurl" status="loop"> 
			  			<img width="120" height="120" src='<ww:property value="imgurl[#loop.index]"/>'/>
		  		</ww:iterator>
		  	</td>
		  </tr>
		</tbody></table>
  		
  		
      </div>

</body>
<script type="text/javascript">
 
$(function () {
	$('#myTab a').click(function (e) {
		  e.preventDefault()

		  var refId=$(this).attr("refId");
		  var refHref=$(this).attr("refHref");
		  var isLoad=$("#"+refId).attr("isload");
		  
		  
		  if(isLoad=="Y"){
			  $(this).tab('show')
		  }else{
			  $.ajax({
				  url: '<%=path%>/'+refHref,
				  data:{"carViolation.orderId":'<ww:property value="orders.id" />',"carAccident.orderId":'<ww:property value="orders.id" />'},
			      type: 'POST',
				  dataType: 'html',
				  success: function(data){
				 		$("#"+refId).html(data);
				 		$(this).tab('show')
				 		$("#"+refId).attr("isload","Y");	
				  }
				});
		  }
		  
		 
		});
	
	$('#myTab a:first').click();
});
</script>
</html>