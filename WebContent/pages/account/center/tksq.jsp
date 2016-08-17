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
<base href="<%=basePath%>">
    <title>退款申请</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link href="common/portal/test/resources/css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/resources/css/axure_rp_page.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/data/styles.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/files/tksq/styles.css" type="text/css" rel="stylesheet"/>
    <script src="common/portal/test/resources/scripts/jquery-1.7.1.min.js"></script>
    <script src="common/portal/test/resources/scripts/jquery-ui-1.8.10.custom.min.js"></script>
    <script src="common/portal/test/resources/scripts/axure/axQuery.js"></script>
    <script src="common/portal/test/resources/scripts/axure/globals.js"></script>
    <script src="common/portal/test/resources/scripts/axutils.js"></script>
    <script src="common/portal/test/resources/scripts/axure/annotation.js"></script>
    <script src="common/portal/test/resources/scripts/axure/axQuery.std.js"></script>
    <script src="common/portal/test/resources/scripts/axure/doc.js"></script>
    <script src="common/portal/test/data/document.js"></script>
    <script src="common/portal/test/resources/scripts/messagecenter.js"></script>
    <script src="common/portal/test/resources/scripts/axure/events.js"></script>
    <script src="common/portal/test/resources/scripts/axure/action.js"></script>
    <script src="common/portal/test/resources/scripts/axure/expr.js"></script>
    <script src="common/portal/test/resources/scripts/axure/geometry.js"></script>
    <script src="common/portal/test/resources/scripts/axure/flyout.js"></script>
    <script src="common/portal/test/resources/scripts/axure/ie.js"></script>
    <script src="common/portal/test/resources/scripts/axure/model.js"></script>
    <script src="common/portal/test/resources/scripts/axure/repeater.js"></script>
    <script src="common/portal/test/resources/scripts/axure/sto.js"></script>
    <script src="common/portal/test/resources/scripts/axure/utils.temp.js"></script>
    <script src="common/portal/test/resources/scripts/axure/variables.js"></script>
    <script src="common/portal/test/resources/scripts/axure/drag.js"></script>
    <script src="common/portal/test/resources/scripts/axure/move.js"></script>
    <script src="common/portal/test/resources/scripts/axure/visibility.js"></script>
    <script src="common/portal/test/resources/scripts/axure/style.js"></script>
    <script src="common/portal/test/resources/scripts/axure/adaptive.js"></script>
    <script src="common/portal/test/resources/scripts/axure/tree.js"></script>
    <script src="common/portal/test/resources/scripts/axure/init.temp.js"></script>
    <script src="common/portal/test/files/tksq/data.js"></script>
    <script src="common/portal/test/resources/scripts/axure/legacy.js"></script>
    <script src="common/portal/test/resources/scripts/axure/viewer.js"></script>
    <script type="text/javascript">
      $axure.utils.getTransparentGifPath = function() { return 'common/portal/test/resources/images/transparent.gif'; };
      $axure.utils.getOtherPath = function() { return 'common/portal/test/resources/Other.html'; };
      $axure.utils.getReloadPath = function() { return 'common/portal/test/resources/reload.html'; };
    </script>
    
    <script type="text/javascript">
    function refundContinue(){
       
    	if(confirm("提交退款申请以后，在退款期间内将不能再租用车辆了，确定要提交退款申请吗？ps:如申请退款期间仍需用车，请先取消退款申请，再租车。")){

    	
    		$.ajax({
    			type: "POST",
    		    url: "account/applyRefund.action",
    		    dataType:'json',
    		    data:$('#subscriberform').serialize(),
    		    success: function(data) {
    		     
    				if(0==data.result){
    					alert("申请提交成功");
    					//window.parent.parent.alertMsg("修改成功","success");
    			    }else{
    		    		//$("#retMsg").html(data.msg);
    		    		window.parent.parent.alertMsg(data.msg,"fail");
    			    }
    		    }
    		});
    		
    		
        }else{
        	//window.history.back(-1); 
        	//document.location.href="account/center.action";
        }
    }

    </script>
  </head>
  <body>
 	<p><span style="color:#333333;">当前账户余额</span><span style="color:#333333;">：</span><span style="color:#FF0000;"> <ww:property value="account.amount"/></span><span style="color:#FF0000;">元</span></p>
       
  退款账号选择:
   <form action="">
	  <table>
		  	
		  	
		   <ww:iterator  value="list" >
			   <tr>
			   		<td><input type="radio" name="alipayCountId" value="id"/></td>
			  		<td> <ww:property value="alipayNo" /></td>
			  		
			  	</tr>
		   </ww:iterator>
		   <tr>
		   	<td colspan="2"><input  type="button" onclick="refundContinue()" value="提交退款申请"/></td>
		   </tr>
	   </table>
	   
	     
   </form>
  
 
   
   
  </body>
</html>
