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
<title>充值</title>
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



<!-- 弹框-->
.mask_black { background:#000; z-index:9998; position:absolute; top:0; left:0; opacity: 0.5;
    filter: alpha(opacity=50);}
.popdiv_white,.popdiv_black{position: absolute;z-index: 5001; width: 500px; left:-9999em; top: -9999em; display: none;-moz-box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);-webkit-box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);-o-box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);-ms-box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);box-shadow: 0 0 10px rgba(0, 0, 0, 0.5);}
.popdiv_black{width:auto;z-index:5500;}
.popdiv_black_main{color:#fff;padding:20px;}
.popdiv_close{position: absolute;right: 16px;top: 16px}
.popdiv_title{height: 40px; margin: -40px -20px 0 -20px;border-bottom: 1px solid #E3E3E3;line-height: 25px;font-size: 18px}
.popdiv_white_main{padding:50px 40px 30px}

.white-t-r{background-position: right -350px;padding-right: 5px;}
.white-t-l{background-position: 0 -345px;}
.white-b-r{background-position: right -360px;padding-right: 5px;}
.white-b-l{background-position: 0 -355px;}

<!-- 弹框 end>
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
			thisul.find("li").click(function(){thisinput.val($(this).text());$("#rechargeAmount").val($(this).val());thisul.fadeOut("100");}).hover(function(){$(this).addClass("hover");},function(){$(this).removeClass("hover");});
			}
		else{
			thisul.fadeOut("fast");
			}
	})
});

function continueRecharge(){

	
	
	if($("#rechargeAmount").val()==null || $("#rechargeAmount").val()==""){
		window.parent.alertMsg("提示","请选择充值金额");
		//window.parent.alertMsg("修改成功","success");
		return;
	}
	
	$("#rechargeForm").submit();
	
}
</script>
</head>

<body>

 <form action="account/continueRecharge.action" id="rechargeForm">
 	<input type="hidden" name="rechargeAmount" id="rechargeAmount" value="500">
    
       
  </form>
 <!--退款 开始-->
        <div class="zhgl">
             <div class="xyk_tit">充值</div>
             <div class="tuikuan" style="height:522px;">
                 <p class="ketuik">当前可退款金额：<span class="sz"><ww:property value="formatAmount(account.amount)"/></span><span class="yy">元</span> </p>
                  <div class="kuang" style="border:1px solid #4dd3a5;">
                  <table width="330" border="0" class="tkje">
                      <tr>
                        <td width="80"><span class="tuik">我要充入：</span></td>
                        <td>
                         <div class="select_box"><input id="myselect" type="text" code="" value="500元" readonly="readonly">
                            <ul class="select_ul">
                            	<ww:iterator value="@com.dearho.cs.sys.util.DictUtil@getDictsByGroupCode('11')" id="data" status="rl">
									 <li value="<ww:property value="code" />"><ww:property value="cnName" /></li>
								</ww:iterator>
                               
                                
                            </ul>
                            <span class="hxian" style="color:#93e5c9;">|</span>
                        </div>
                        <!--<input type="text" class="input_tk"/>-->
                        <span style="color:#666; position:relative; top:15px; left:5px;">元</span></td>
                      </tr>
                      <tr>
                        <td height="40"></td>
                        <td><span style="font-size:11px; color:#666;">本次退款金额预计会在2小时内到账。</span></td>
                      </tr>
                      <tr>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                    </table>
                     <div class="table_box"   onclick="continueRecharge()" style="border-bottom:1px solid #eee; height:110px; border-top:1px solid #eee;">  
                            <div class="tuk_sub">下一步 </div>
                             <font color="red"><ww:property value="retMsg"/></font>
                           
                      </div>
                    <div class="table_box" style="height:135px; background-color:#effef9;">                  
                    <div class="yqts">
                         <p class="yq">友情提示！</p>
                         <ul class="yq_list">
                           <li><span>&middot;</span>充值金额可直接支付用车费用，随时订车更方便；</li>
                           <li><span>&middot;</span>为保证你的账号安全，退款申请来电手机号需与注册账号一致才可办理；</li>
                           <li><span>&middot;</span>退款金额将于3-21个工作日返还至原支付账户。</li>
                         </ul>
             
                    </div>
                   </div>
             </div>
            
                    
        </div>
        <!--退款 开始-->
        
        
  
  <div style="left: 500px; top: 200px; width: 500px; display: none;" id="popLayer_charge" class="popdiv_white"> 
    <div class="white-t-r"><div class="white-t-l"></div></div>
    <div class="bg_white popdiv_white_main"> 
        <a href="javascript:;" title="关闭" class="popdiv_close icons icons-close-light _close"></a>
        <div class="popdiv_title">充值遇到问题？</div>
        <div class="xs2 mt20"><i class="icon_common_logo icon_decorate mr5 vm"></i>请在新开的付款页面完成付款后再选择</div>

	    <div class="mt10 xs2 fzbold pl20">充值成功：<a href="/user/record.php" class="c_blue">查看余额</a></div>
	    <div class="mt10 xs2 fzbold pl20">充值失败： <a href="javascript:;" onclick="$('#popLayer_charge').hide(); YCUtils.showMaskBackground(false);" class="c_blue">重新选择充值方式</a></div>

    </div>
    <div class="white-b-r"><div class="white-b-l"></div></div>
</div>
</body>
</html>
