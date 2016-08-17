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

    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link href="common/portal/test/resources/css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/resources/css/axure_rp_page.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/data/styles.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/files/index/styles.css" type="text/css" rel="stylesheet"/>
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
    <script src="common/portal/test/files/index/data.js"></script>
    <script src="common/portal/test/resources/scripts/axure/legacy.js"></script>
    <script src="common/portal/test/resources/scripts/axure/viewer.js"></script>
    <script type="text/javascript">
      $axure.utils.getTransparentGifPath = function() { return 'common/portal/test/resources/images/transparent.gif'; };
      $axure.utils.getOtherPath = function() { return 'common/portal/test/resources/Other.html'; };
      $axure.utils.getReloadPath = function() { return 'common/portal/test/resources/reload.html'; };


      function check(){
    	  var a = /^(?:(?!0000)[0-9]{4}-(?:(?:0[1-9]|1[0-2])-(?:0[1-9]|1[0-9]|2[0-8])|(?:0[13-9]|1[0-2])-(?:29|30)|(?:0[13578]|1[02])-31)|(?:[0-9]{2}(?:0[48]|[2468][048]|[13579][26])|(?:0[48]|[2468][048]|[13579][26])00)-02-29)$/;
    	  if (!a.test(document.getElementById("test").value)) { 
    	  	alert("日期格式不正确!") 
    	  return false ;
    	  } 
    	  else 
    	  return true ;
    	 } 

      function gotoApplyRefund(){
    	  $.ajax({
  			type: "POST",
  		    url: "account/gotoApplyRefundCheck.action",
  		    dataType:'json',
  		  
  		    success: function(data) {
  		     
  				if(0==data.result){
  					window.location.href="<%=path %>/account/gotoApplyRefund.action";
  			    }else{
  		    		alert(data.msg)
  		    		//window.parent.parent.alertMsg(data.msg,"fail");
  			    }
  		    }
  		});
    		
        
      }
      function  gotoRecharge(){
    		$.ajax({
    			type: "POST",
    		    url: "account/gotoRechargeCheck.action",
    		    dataType:'json',
    		  
    		    success: function(data) {
    		     
    				if(0==data.result){
    					window.location.href="<%=path %>/account/gotoRechargeCheck.action";
    			    }else{
    		    		alert(data.msg)
    		    		//window.parent.parent.alertMsg(data.msg,"fail");
    			    }
    		    }
    		});
      }

      function gotoCreditCardBinding(){
    	  window.location.href="<%=path %>/account/gotoCreditCardBinding.action";
       }
    </script>
  </head>
  <body>
    <div id="base" class="">

      <!-- Unnamed (形状) -->
      <div id="u0" class="ax_形状">
        <img id="u0_img" class="img " src="common/portal/test/images/index/u0.png"/>
        <!-- Unnamed () -->
        <div id="u1" class="text">
          <p><span></span></p>
        </div>
      </div>

      <!-- Unnamed (形状) -->
      <div id="u2" class="ax_h2">
        <img id="u2_img" class="img " src="common/portal/test/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u3" class="text">
          <p><span>个人信息</span></p>
        </div>
      </div>

      <!-- Unnamed (形状) -->
      <div id="u4" class="ax_h2">
        <img id="u4_img" class="img " src="common/portal/test/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u5" class="text">
          <p><span>消费记录</span></p>
        </div>
      </div>

      <!-- Unnamed (形状) -->
      <div id="u6" class="ax_h2">
        <img id="u6_img" class="img " src="common/portal/test/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u7" class="text">
          <p><span>账户管理</span></p>
        </div>
      </div>

      <!-- Unnamed (形状) -->
      <div id="u8" class="ax_形状">
        <img id="u8_img" class="img " src="common/portal/test/images/index/u8.png"/>
        <!-- Unnamed () -->
        <div id="u9" class="text">
          <p><span></span></p>
        </div>
      </div>

      <!-- Unnamed (水平线) -->
      <div id="u10" class="ax_水平线">
        <img id="u10_start" class="img " src="common/portal/test/resources/images/transparent.gif" alt="u10_start"/>
        <img id="u10_end" class="img " src="common/portal/test/resources/images/transparent.gif" alt="u10_end"/>
        <img id="u10_line" class="img " src="common/portal/test/images/index/u10_line.png" alt="u10_line"/>
      </div>

      <!-- Unnamed (形状) -->
      <div id="u11" class="ax_h2">
        <img id="u11_img" class="img " src="common/portal/test/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u12" class="text">
          <p><span>账户管理</span></p>
        </div>
      </div>

      <!-- Unnamed (提交按钮) -->
      <div id="u13" class="ax_提交按钮">
        <input id="u13_input" type="submit" value="充值"/>
       
      </div>

      <!-- Unnamed (提交按钮) -->
      <div class="ax_提交按钮">
       
      </div>

      <!-- Unnamed (形状) -->
      <div id="u15" class="ax_文本段落">
        <img id="u15_img" class="img " src="common/portal/test/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u16" class="text">
          <p><span>您的</span><span>可用余额</span><span>为</span><span>：￥<ww:property value="account.amount"/>元</span></p>
        </div>
      </div>

      <!-- Unnamed (表格) -->
      <div id="u17" class="ax_表格">

        <!-- Unnamed (单元格) -->
        <div id="u18" class="ax_单元格">
          <img id="u18_img" class="img " src="common/portal/test/images/index/u18.png"/>
          <!-- Unnamed () -->
          <div id="u19" class="text">
            <p><span>总额</span></p>
          </div>
        </div>

        <!-- Unnamed (单元格) -->
        <div id="u20" class="ax_单元格">
          <img id="u20_img" class="img " src="common/portal/test/images/index/u20.png"/>
          <!-- Unnamed () -->
          <div id="u21" class="text">
            <p><span>￥</span><span><ww:property value="account.amount"/>元</span></p>
          </div>
        </div>

        <!-- Unnamed (单元格) -->
        <div id="u22" class="ax_单元格">
          <img id="u22_img" class="img " src="common/portal/test/images/index/u18.png"/>
          <!-- Unnamed () -->
          <div id="u23" class="text">
            <p><span>可提现</span></p>
          </div>
        </div>

        <!-- Unnamed (单元格) -->
        <div id="u24" class="ax_单元格">
          <img id="u24_img" class="img " src="common/portal/test/images/index/u20.png"/>
          <!-- Unnamed () -->
          <div id="u25" class="text">
            <p><span>￥<ww:property value="account.amount"/>元</span></p>
          </div>
        </div>

        <!-- Unnamed (单元格) -->
        <div id="u26" class="ax_单元格">
          <img id="u26_img" class="img " src="common/portal/test/images/index/u26.png"/>
          <!-- Unnamed () -->
          <div id="u27" class="text">
            <p><span>冻结</span></p>
          </div>
        </div>

        <!-- Unnamed (单元格) -->
        <div id="u28" class="ax_单元格">
          <img id="u28_img" class="img " src="common/portal/test/images/index/u28.png"/>
          <!-- Unnamed () -->
          <div id="u29" class="text">
            <p><span>￥ <input type="text" id="test"></span><span>0.00元</span> <input type="button" value="tt"  onclick="check()"> </p>
          </div>
        </div>
      </div>

      <!-- Unnamed (水平线) -->
      <div id="u30" class="ax_水平线">
        <img id="u30_start" class="img " src="common/portal/test/resources/images/transparent.gif" alt="u30_start"/>
        <img id="u30_end" class="img " src="common/portal/test/resources/images/transparent.gif" alt="u30_end"/>
        <img id="u30_line" class="img " src="common/portal/test/images/index/u10_line.png" alt="u30_line"/>
      </div>

      <!-- Unnamed (Image) -->
      <div id="u31" class="ax_image">
        <img id="u31_img" class="img " src="common/portal/test/images/index/u31.png"/>
        <!-- Unnamed () -->
        <div id="u32" class="text">
          <p><span></span></p>
        </div>
      </div>

      <!-- Unnamed (形状) -->
      <div id="u33" class="ax_文本段落">
        <img id="u33_img" class="img " src="common/portal/test/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u34" class="text">
          <p><span>
          	<!-- ============= -->
          	<ww:if test="@com.dearho.cs.util.DateUtil@isPM()">上午好</ww:if>
          	<ww:else>下午好</ww:else>
          	<ww:if test="#session.subscriber.name!=null">
          		,<ww:property value="#session.subscriber.name"/>
          	</ww:if>
          	<ww:elseif test="#session.subscriber.phoneNo!=null">
          		,<ww:property value="#session.subscriber.phoneNo"/>
          	</ww:elseif>
          	
          	
          	 <input  type="button" onclick="gotoRecharge()" value="充值"/>
          	 <input  type="button" onclick="gotoApplyRefund()" value="退款"/>
          	 <input  type="button" onclick="gotoCreditCardBinding()" value="信用卡管理"/>
          	 
          	<!-- ============= -->
          	</span></p>
        </div>
      </div>

      <!-- Unnamed (形状) -->
      <div id="u35" class="ax_形状">
        <img id="u35_img" class="img " src="common/portal/test/images/index/u35.png"/>
        <!-- Unnamed () -->
        <div id="u36" class="text">
          <p><span>当前账号</span></p>
        </div>
      </div>

      <!-- Unnamed (提交按钮) -->
      <div id="u37" class="ax_提交按钮">
        <input id="u37_input" type="submit" value="信用卡管理"/>
      </div>
    </div>
  </body>
</html>
