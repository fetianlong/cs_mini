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
    <title>支付方式选择</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link href="common/portal/test/resources/css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/resources/css/axure_rp_page.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/data/styles.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/files/zffs/styles.css" type="text/css" rel="stylesheet"/>
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
    <script src="common/portal/test/files/zffs/data.js"></script>
    <script src="common/portal/test/resources/scripts/axure/legacy.js"></script>
    <script src="common/portal/test/resources/scripts/axure/viewer.js"></script>
    <script type="text/javascript">
      $axure.utils.getTransparentGifPath = function() { return 'common/portal/test/resources/images/transparent.gif'; };
      $axure.utils.getOtherPath = function() { return 'common/portal/test/resources/Other.html'; };
      $axure.utils.getReloadPath = function() { return 'common/portal/test/resources/reload.html'; };
    </script>
    
    
    <script type="text/javascript">
	function AlipayRecharge()
	{
		var intRecharge = document.getElementById("txtAmount").value;
		if (intRecharge > 0)
		{
			window.open('pages/alipay/alipay_recharge.jsp?WIDtotal_fee='+ intRecharge);
		//	mShow('支付情况', 'data/recharge_message.jsp',350,180,true);
		}
		else
		{
			alert("请先输入您需要充值的金额！");
		}
	}
    
    </script>
  </head>
  <body>
  <!-- ===================== -->
  <input type="hidden" id="txtAmount" value='<ww:property  value="#request.rechargeAmount"/>'>

  <!-- ===================== -->
  
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
          <p><span>账户</span><span>充值</span></p>
        </div>
      </div>

      <!-- Unnamed (形状) -->
      <div id="u13" class="ax_文本段落">
        <img id="u13_img" class="img " src="common/portal/test/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u14" class="text">
          <p><span style="color:#333333;">充值金额</span><span style="color:#333333;">：</span><span style="color:#FF0000;">100元</span></p>
        </div>
      </div>

      <!-- Tab Panel (动态面板) -->
      <div id="u15" class="ax_动态面板" data-label="Tab Panel">
        <div id="u15_state0" class="panel_state" data-label="Tab 1">
          <div id="u15_state0_content" class="panel_state_content">

            <!-- Unnamed (形状) -->
            <div id="u16" class="ax_形状">
              <img id="u16_img" class="img " src="common/portal/test/images/zffs/u16.png"/>
              <!-- Unnamed () -->
              <div id="u17" class="text">
                <p><span>支付宝</span></p>
              </div>
            </div>

            <!-- Unnamed (形状) -->
            <div id="u18" class="ax_形状">
              <img id="u18_img" class="img " src="common/portal/test/images/zffs/u18.png"/>
              <!-- Unnamed () -->
              <div id="u19" class="text">
                <p><span>网上银行</span></p>
              </div>
            </div>

            <!-- Unnamed (形状) -->
            <div id="u20" class="ax_形状">
              <img id="u20_img" class="img " src="common/portal/test/images/zffs/u20.png"/>
              <!-- Unnamed () -->
              <div id="u21" class="text">
                <p><span></span></p>
              </div>
            </div>

            <!-- Unnamed (形状) -->
            <div id="u22" class="ax_形状">
              <img id="u22_img" class="img " src="common/portal/test/images/zffs/u22.png"/>
              <!-- Unnamed () -->
              <div id="u23" class="text">
                <p><span></span></p>
              </div>
            </div>

            <!-- Unnamed (Image) -->
            <div id="u24" class="ax_image">
              <img id="u24_img" class="img " src="common/portal/test/images/zffs/u24.jpg"/>
              <!-- Unnamed () -->
              <div id="u25" class="text">
                <p><span></span></p>
              </div>
            </div>
          </div>
        </div>
        <div id="u15_state1" class="panel_state" data-label="Tab 2">
          <div id="u15_state1_content" class="panel_state_content">

            <!-- Unnamed (形状) -->
            <div id="u26" class="ax_形状">
              <img id="u26_img" class="img " src="common/portal/test/images/zffs/u16.png"/>
              <!-- Unnamed () -->
              <div id="u27" class="text">
                <p><span>网上银行</span></p>
              </div>
            </div>

            <!-- Unnamed (形状) -->
            <div id="u28" class="ax_形状">
              <img id="u28_img" class="img " src="common/portal/test/images/zffs/u18.png"/>
              <!-- Unnamed () -->
              <div id="u29" class="text">
                <p><span>支付宝</span></p>
              </div>
            </div>

            <!-- Unnamed (形状) -->
            <div id="u30" class="ax_形状">
              <img id="u30_img" class="img " src="common/portal/test/images/zffs/u20.png"/>
              <!-- Unnamed () -->
              <div id="u31" class="text">
                <p><span></span></p>
              </div>
            </div>

            <!-- Unnamed (形状) -->
            <div id="u32" class="ax_形状">
              <img id="u32_img" class="img " src="common/portal/test/images/zffs/u32.png"/>
              <!-- Unnamed () -->
              <div id="u33" class="text">
                <p><span></span></p>
              </div>
            </div>

            <!-- Unnamed (Image) -->
            <div id="u34" class="ax_image">
              <img id="u34_img" class="img " src="common/portal/test/images/zffs/u34.png"/>
              <!-- Unnamed () -->
              <div id="u35" class="text">
                <p><span></span></p>
              </div>
            </div>

            <!-- Unnamed (提交按钮) -->
            <div id="u36" class="ax_提交按钮">
              <input id="u36_input" type="button" onclick="AlipayRecharge()" value="去支付"/>
            </div>
          </div>
        </div>
      </div>
    </div>
  </body>
</html>
