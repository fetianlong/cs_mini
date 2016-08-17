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
    <title>信用卡管理</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link href="common/portal/test/resources/css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/resources/css/axure_rp_page.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/data/styles.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/files/xyk/styles.css" type="text/css" rel="stylesheet"/>
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
    <script src="common/portal/test/files/xyk/data.js"></script>
    <script src="common/portal/test/resources/scripts/axure/legacy.js"></script>
    <script src="common/portal/test/resources/scripts/axure/viewer.js"></script>
    <script type="text/javascript">
      $axure.utils.getTransparentGifPath = function() { return 'common/portal/test/resources/images/transparent.gif'; };
      $axure.utils.getOtherPath = function() { return 'common/portal/test/resources/Other.html'; };
      $axure.utils.getReloadPath = function() { return 'common/portal/test/resources/reload.html'; };

      function creditCardBinding(){
    	  $.ajax({
  			type: "POST",
  		    url: "account/creditCardBinding.action",
  		    dataType:'json',
  		    success: function(data) {
  		     
  				if(0==data.result){
      				
  					//alert(data.msg);
  					window.location.href="<%=path %>/account/gotoCreditCardBinding.action";
  			    }else{
  		    		alert(data.msg);
  		    	
  			    }
  		    }
  		});
    		
    	 
  		
      }
      function creditCardUnbinding(){
    		$.ajax({
    			type: "POST",
    		    url: "account/creditCardUnbinding.action",
    		    dataType:'json',
    		    success: function(data) {
    		     
    				if(0==data.result){
        				
    				//	alert(data.msg);
    					window.location.href="<%=path %>/account/gotoCreditCardBinding.action";
    			    }else{
    		    		alert(data.msg);
    		    	
    			    }
    		    }
    		});
      }
      
    </script>
  </head>
  <body>
  
  <ww:if test="accountCreditCardBinding==null">
   	未绑定信用卡，<input  type="button" onclick="creditCardBinding()" value="绑定信用卡"/>
  </ww:if>
  <ww:else>
  	<table>
  		<tr>
  			<td>银行名称</td>
  			<td>卡号</td>
  			<td>绑定状态</td>
  			<td>操作</td>
  		</tr>
  		<tr>
  			<td> <ww:property  value="accountCreditCardBinding.creditCardBank"/> </td>
  			<td> <ww:property  value="accountCreditCardBinding.creditCardNo"/> </td>
  			<td>已绑定</td>
  			<td><input  type="button" onclick="creditCardUnbinding()" value="解绑"/></td>
  		</tr>
  		
  	</table>
 
  
  </ww:else>
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
          <p><span>信用卡管理</span></p>
        </div>
      </div>

      <!-- Unnamed (水平线) -->
      <div id="u13" class="ax_水平线">
        <img id="u13_start" class="img " src="common/portal/test/resources/images/transparent.gif" alt="u13_start"/>
        <img id="u13_end" class="img " src="common/portal/test/resources/images/transparent.gif" alt="u13_end"/>
        <img id="u13_line" class="img " src="common/portal/test/images/index/u10_line.png" alt="u13_line"/>
      </div>

      <!-- Unnamed (形状) -->
      <div id="u14" class="ax_文本段落">
        <img id="u14_img" class="img " src="common/portal/test/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u15" class="text">
          <p><span>已开通“先用车后付钱”的信用卡</span><span>：</span></p> <input type="button" value="绑定信用卡" >
        </div>
      </div>

      <!-- Unnamed (表格) -->
      <div id="u16" class="ax_表格">

        <!-- Unnamed (单元格) -->
        <div id="u17" class="ax_单元格">
          <img id="u17_img" class="img " src="common/portal/test/images/xyk/u17.png"/>
          <!-- Unnamed () -->
          <div id="u18" class="text">
            <p><span>银行名称</span></p>
          </div>
        </div>

        <!-- Unnamed (单元格) -->
        <div id="u19" class="ax_单元格">
          <img id="u19_img" class="img " src="common/portal/test/images/xyk/u17.png"/>
          <!-- Unnamed () -->
          <div id="u20" class="text">
            <p><span>卡号</span></p>
          </div>
        </div>

        <!-- Unnamed (单元格) -->
        <div id="u21" class="ax_单元格">
          <img id="u21_img" class="img " src="common/portal/test/images/xyk/u17.png"/>
          <!-- Unnamed () -->
          <div id="u22" class="text">
            <p><span>绑定状态</span></p>
          </div>
        </div>

        <!-- Unnamed (单元格) -->
        <div id="u23" class="ax_单元格">
          <img id="u23_img" class="img " src="common/portal/test/images/xyk/u23.png"/>
          <!-- Unnamed () -->
          <div id="u24" class="text">
            <p><span>操作</span></p>
          </div>
        </div>

        <!-- Unnamed (单元格) -->
        <div id="u25" class="ax_单元格">
          <img id="u25_img" class="img " src="common/portal/test/images/xyk/u25.png"/>
          <!-- Unnamed () -->
          <div id="u26" class="text">
            <p><span></span></p>
          </div>
        </div>

        <!-- Unnamed (单元格) -->
        <div id="u27" class="ax_单元格">
          <img id="u27_img" class="img " src="common/portal/test/images/xyk/u25.png"/>
          <!-- Unnamed () -->
          <div id="u28" class="text">
            <p><span>**** **** **** **** 3838</span></p>
          </div>
        </div>

        <!-- Unnamed (单元格) -->
        <div id="u29" class="ax_单元格">
          <img id="u29_img" class="img " src="common/portal/test/images/xyk/u25.png"/>
          <!-- Unnamed () -->
          <div id="u30" class="text">
            <p><span>已绑定</span></p>
          </div>
        </div>

        <!-- Unnamed (单元格) -->
        <div id="u31" class="ax_单元格">
          <img id="u31_img" class="img " src="common/portal/test/images/xyk/u31.png"/>
          <!-- Unnamed () -->
          <div id="u32" class="text">
            <p><span></span></p>
          </div>
        </div>
      </div>

      <!-- Unnamed (Image) -->
      <div id="u33" class="ax_image">
        <img id="u33_img" class="img " src="common/portal/test/images/xyk/u33.png"/>
        <!-- Unnamed () -->
        <div id="u34" class="text">
          <p><span></span></p>
        </div>
      </div>

      <!-- Unnamed (提交按钮) -->
      <div id="u35" class="ax_提交按钮">
        <input id="u35_input" type="submit" value="解绑"/>
      </div>

      <!-- Unnamed (形状) -->
      <div id="u36" class="ax_文本段落">
        <img id="u36_img" class="img " src="common/portal/test/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u37" class="text">
          <p><span>常见问题：</span></p><p><span>问：什么是&quot;先用车后付钱&quot;?</span></p><p><span>答：易到用车推出“先用车后付款”服务，乘客在网站或手机应用下单预订车辆不用预先付款，预定完成后司机将会到您指定的地址去接您。用车结束后再自行付款，服务更加人性化、更安全、更便捷！</span></p><p><span>&nbsp;</span></p><p><span>问：怎么才能享受“先用车后付款”的服务？</span></p><p><span>答：首次下单的用户，只需在下单时根据提示“验证信用卡”或“账户余额”大于所选车辆和服务的最低消费额，就可以享受易到用车“先用车后付款”的服务。</span></p><p><span>&nbsp;</span></p><p><span>问：一张信用卡可以在多个帐户使用吗？</span></p><p><span>答：可以，但为了您的信用卡安全，一张信用卡只能在2个帐户使用。</span></p><p><span>&nbsp;</span></p><p><span>问：用车结束后，我怎么付钱?</span></p><p><span>答：用车结束后，您可以通过4种渠道付钱。1）网站付款：用车结束后登录网站会员中心，在待付款的订单引导中使用网上银行进行付款。2）手机应手付款：在手机上打开易到租车应用，登录之后在我的订单中进行网银付款。3）语音支付：致电400-1111-777根据语音提示进行付款。4）委托易到代扣款：用车结束2小时内您如没有操作付款，易到将从您的代扣信用卡相应金额，或代扣您的易到账户余额。</span></p><p><span>&nbsp;</span></p><p><span>问：我可以验证多张信用卡吗?</span></p><p><span>答：不可以，只充许验证一张信用卡。如果想更换信用卡，您可以将已验证的信用卡取消验证后，就可以验证新卡了。</span></p>
        </div>
      </div>
    </div>
  </body>
</html>
