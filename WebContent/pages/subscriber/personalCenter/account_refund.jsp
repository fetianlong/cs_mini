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
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>退款</title>
<link rel="shortcut icon" href="<%=path%>/common/css/images/favicon.ico" type="image/x-icon" /> 
<link rel="stylesheet" type="text/css" href="common/portal/css/style.css"/>
<script type="text/javascript" src="common/js/jquery-1.7.min.js"></script>
<script type="text/javascript" src="common/js/popup_layer.js"></script>

<style>
 *{ margin:0; padding:0px;}
 body{ margin:0; padding:0px; background-color:#fff;}
 
.select_box {width: 177px; float:left; position:relative; margin:10px;padding:0; font-size:12px;}
/*.submit_box {width:180px; position:relative; margin:10px;padding:0; font-size:12px; text-align:center;}*/
ul,li {list-style-type:none; padding:0; margin:0}
.select_box input {cursor:pointer; display:block; line-height:30px; width:100%;  height:25px; overflow:hidden;border:1px solid #93e5c9; border-radius:5px; padding-left:10px; background:url(common/portal/images/jt-1.png) no-repeat 170px center;}
.select_box ul {width:187px; position:absolute; left:0; top:25px; border:1px solid #93e5c9; background:#fff; overflow: hidden;display:none;  z-index:99999;}
.select_box ul li {display:block;height:30px;overflow:hidden;line-height:30px;padding-left:5px;width:100%;cursor:pointer; border:none; border-bottom:1px dashed #ccc; background-color:#fff; color:#000;}
.hover {background:#ccc;}
.hxian {position:absolute; top:3px; right:10px; font-size:13px; font-weight:normal; color:#CCC;}

</style>

<script type="text/javascript">
$(document).ready(function(){
	$(".select_box input").click(function(){
		var thisinput=$(this);
		var thisul=$(this).parent().find("ul");
		if(thisul.css("display")=="none"){
			if(thisul.height()>200){thisul.css({height:"200"+"px","overflow-y":"scroll" })};
			thisul.fadeIn("100");
			thisul.hover(function(){},function(){thisul.fadeOut("100");})
			thisul.find("li").click(function(){thisinput.val($(this).text());$("#alipayId").val($(this).attr("code")); thisul.fadeOut("100");}).hover(function(){$(this).addClass("hover");},function(){$(this).removeClass("hover");});
			}
		else{
			thisul.fadeOut("fast");
			}
	})
});



function refundContinue(){

	
	 if($("#alipayId").val()==null ||$("#alipayId").val()=="" ){
		   window.parent.alertMsg("提示","请选择支付宝账号");
		   return;
	}
	if(confirm("提交退款申请以后，在退款期间内将不能再租用车辆了，确定要提交退款申请吗？")){

	
		$.ajax({
			type: "POST",
		    url: "account/applyRefund.action",
		    dataType:'json',
		    data:$('#refundForm').serialize(),
		    success: function(data) {
		     
				if(0==data.result){
					 window.parent.alertMsg("提示","申请提交成功!");
					//window.parent.parent.alertMsg("修改成功","success");
			    }else{
			    	window.parent.alertMsg("提示",data.msg);
		    		//$("#retMsg").html(data.msg);
		    	//	window.parent.parent.alertMsg(data.msg,"fail");
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

<form action="" id="refundForm">
<input  type="hidden"  id="alipayId"  name="accountAlipayRecord.alipayNo">
</form>
 <!--退款 开始-->
        <div class="zhgl">
             <div class="xyk_tit">退款</div>
             <div class="tuikuan">
                 <p class="ketuik">当前可退款金额：<span class="sz"><ww:property  value="formatAmount(account.amount)"/></span><span class="yy">元</span> </p>
                 <div class="kuang" style="border:1px solid #4dd3a5;">
                 
                  <table width="350" border="0" class="tkje">
                      <tr>
                        <td width="80" align="right"><span class="tuik">退款金额：</span></td>
                        <td><input type="text" style=" margin-left:10px; padding-left:11px;" class="input_tk" readonly="readonly" disabled="disabled"  value='<ww:property  value="formatAmount(account.amount)"/>'/><span style="color:#666;">元</span></td>
                      </tr>
                      <tr>
                        <td height="40"></td>
                        <td><span style="font-size:11px; color:#666; margin-left:10px;">本次退款金额预计会在2小时内到账。</span></td>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr>
                        <td width="120"><span class="tuik">退款支付宝账户： </span></td>
                        <td>
                        	<div class="select_box"><input id="myselect" type="text" code="" value="请选择..." readonly="readonly">
	                            <ul class="select_ul">
	                            	<ww:iterator  value="list" >
										 <li code="<ww:property value="alipayNo" />"><ww:property value="alipayNo" /></li>
									 </ww:iterator>
									
	                            </ul>
	                            <span class="hxian" style="color:#93e5c9;">|</span>
                        	</div>
                        
                        </td>
                      </tr>
                       <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                    </table>
                    <div class="table_box" style="border-bottom:1px solid #eee; height:110px; border-top:1px solid #eee;">                    
                        <div class="tuk_sub"  onclick="refundContinue()" style="cursor:pointer;">提交退款申请</div>
                    </div>
                    <div class="table_box" style="height:135px; background-color:#effef9;">                  
                    <div class="yqts">
                         <p class="yq">友情提示！</p>
                         <ul class="yq_list">
                           <li>&middot;申请退款，即表明要求退还账户全部可退余额；</li>
                           <li>&middot;提出退款申请后，退款金额将在10个工作日（2015年06月17日）内退还到您原充值账户；</li>
                           <li>&middot;长假订单(如春节订单等)后，会员提出退款申请的，“华泰”在35天内向会员退回账户余额。</li>
                         </ul>
             
                    </div> 
                    </div>
                    </div>    
             </div>
            
                    
        </div>
        <!--退款 开始-->
</body>
</html>
