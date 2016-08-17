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
    <title>充值</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link href="common/portal/test/resources/css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/resources/css/axure_rp_page.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/data/styles.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/files/cz/styles.css" type="text/css" rel="stylesheet"/>
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
    <script src="common/portal/test/files/cz/data.js"></script>
    <script src="common/portal/test/resources/scripts/axure/legacy.js"></script>
    <script src="common/portal/test/resources/scripts/axure/viewer.js"></script>
    <script type="text/javascript">
      $axure.utils.getTransparentGifPath = function() { return 'common/portal/test/resources/images/transparent.gif'; };
      $axure.utils.getOtherPath = function() { return 'common/portal/test/resources/Other.html'; };
      $axure.utils.getReloadPath = function() { return 'common/portal/test/resources/reload.html'; };

      
    </script>
  </head>
  <body>
  
  <form action="account/continueRecharge.action">
    <select class="sel_style" name="rechargeAmount">
             	<ww:iterator value="@com.dearho.cs.sys.util.DictUtil@getDictsByGroupCode('11')" id="data" status="rl">
						<option value="<ww:property value="code" />"  ><ww:property value="cnName" /></option>	
				</ww:iterator>
        </select>
        <input type="submit" value="下一步">
  </form>
      
           
           
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
          <p><span>我要充</span></p>
        </div>
      </div>

  	<form action="account/selectRechargeMoney.action"  method="post">
  
      <!-- Unnamed (下拉列表框) -->
      <div id="u15" class="ax_下拉列表框">
      <!-- =========== -->
  
        <select class="sel_style" name="rechargeAmount">
             	<ww:iterator value="@com.dearho.cs.sys.util.DictUtil@getDictsByGroupCode('11')" id="data" status="rl">
						<option value="<ww:property value="code" />"  ><ww:property value="cnName" /></option>	
				</ww:iterator>
        </select>
           
      <!-- =========== -->
      </div>

      <!-- Unnamed (提交按钮) -->
      <div id="u16" class="ax_提交按钮">
        <input id="u16_input" type="submit" value="下一步"/>
      </div>
       </form>

      <!-- Unnamed (水平线) -->
      <div id="u17" class="ax_水平线">
        <img id="u17_start" class="img " src="common/portal/test/resources/images/transparent.gif" alt="u17_start"/>
        <img id="u17_end" class="img " src="common/portal/test/resources/images/transparent.gif" alt="u17_end"/>
        <img id="u17_line" class="img " src="common/portal/test/images/index/u10_line.png" alt="u17_line"/>
      </div>

      <!-- Unnamed (Image) -->
      <div id="u18" class="ax_image">
        <img id="u18_img" class="img " src="common/portal/test/images/cz/u18.png"/>
        <!-- Unnamed () -->
        <div id="u19" class="text">
          <p><span></span></p>
        </div>
      </div>

      <!-- Unnamed (形状) -->
      <div id="u20" class="ax_文本段落">
        <img id="u20_img" class="img " src="common/portal/test/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u21" class="text">
          <p><span>充值金额可直接支付用车费用，随时订车更方便。</span></p><p><span>如需取回余额，</span><span>的顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶顶 </span></p><p><span>为保证你的账号安全，退款申请来电手机号需与注册账号一致才可办理。</span></p><p><span>退款金额将于3-21个工作日返还至原支付账户</span></p>
        </div>
      </div>
    </div>
  </body>
</html>
