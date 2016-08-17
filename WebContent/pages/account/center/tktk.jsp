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
    <title>退款弹出窗口</title>
    <meta http-equiv="X-UA-Compatible" content="IE=edge"/>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <meta name="apple-mobile-web-app-capable" content="yes"/>
    <link href="common/portal/test/resources/css/jquery-ui-themes.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/resources/css/axure_rp_page.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/data/styles.css" type="text/css" rel="stylesheet"/>
    <link href="common/portal/test/files/tktk/styles.css" type="text/css" rel="stylesheet"/>
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
    <script src="common/portal/test/files/tktk/data.js"></script>
    <script src="common/portal/test/resources/scripts/axure/legacy.js"></script>
    <script src="common/portal/test/resources/scripts/axure/viewer.js"></script>
    <script type="text/javascript">
      $axure.utils.getTransparentGifPath = function() { return 'common/portal/test/resources/images/transparent.gif'; };
      $axure.utils.getOtherPath = function() { return 'common/portal/test/resources/Other.html'; };
      $axure.utils.getReloadPath = function() { return 'common/portal/test/resources/reload.html'; };
    </script>
  </head>
  <body>
    <div id="base" class="">

      <!-- Unnamed (流程形状) -->
      <div id="u0" class="ax_流程形状">
        <img id="u0_img" class="img " src="common/portal/test/images/tktk/u0.png"/>
        <!-- Unnamed () -->
        <div id="u1" class="text">
          <p><span></span></p>
        </div>
      </div>

      <!-- Unnamed (形状) -->
      <div id="u2" class="ax_文本段落">
        <img id="u2_img" class="img " src="common/portal/test/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u3" class="text">
          <p><span>提示信息</span></p>
        </div>
      </div>

      <!-- Unnamed (水平线) -->
      <div id="u4" class="ax_水平线">
        <img id="u4_start" class="img " src="common/portal/test/resources/images/transparent.gif" alt="u4_start"/>
        <img id="u4_end" class="img " src="common/portal/test/resources/images/transparent.gif" alt="u4_end"/>
        <img id="u4_line" class="img " src="common/portal/test/images/tktk/u4_line.png" alt="u4_line"/>
      </div>

      <!-- Unnamed (形状) -->
      <div id="u5" class="ax_文本段落">
        <img id="u5_img" class="img " src="common/portal/test/resources/images/transparent.gif"/>
        <!-- Unnamed () -->
        <div id="u6" class="text">
          <p><span>提交退款申请以后，在退款期间内将不能再租用车辆了，确定要提交退款申请吗？</span></p><p><span>ps:如申请退款期间仍需用车，请先取消退款申请，再租车。</span></p>
        </div>
      </div>

      <!-- Unnamed (提交按钮) -->
      <div id="u7" class="ax_提交按钮">
        <input id="u7_input" type="submit" value="不提交，我还要用车"/>
      </div>

      <!-- Unnamed (提交按钮) -->
      <div id="u8" class="ax_提交按钮">
        <input id="u8_input" type="submit" value="提交退款"/>
      </div>
    </div>
  </body>
</html>
