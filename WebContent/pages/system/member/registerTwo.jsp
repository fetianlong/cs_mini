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
<title>用户注册</title>
<link href="common/css/common.css" rel="stylesheet" type="text/css">
<script type="text/javascript" src="common/js/cropper/assets/js/jquery.min.js"></script>
<link href="common/js/cropper/dist/cropper.css" rel="stylesheet">
<script type="text/javascript" src="common/js/cropper/dist/cropper.js"></script>

<link href="common/js/cropper/assets/css/bootstrap.min.css" rel="stylesheet">
<link href="common/js/cropper/examples/crop-avatar/css/main.css" rel="stylesheet">


<script type="text/javascript" src="common/js/cropper/assets/js/bootstrap.min.js"></script>


<script type="text/javascript"   src="common/js/cropper/examples/crop-avatar/js/main.js" ></script>
<script type="text/javascript"   src="common/js/cropper/examples/crop-avatar/js/main_driving.js" ></script>



<script type="text/javascript">



</script>
</head>
<body>
用户注册---第二步 --上传用户资料
<br/><font color="red"><ww:property value="retMsg" /></font>
<form name="memberform" id="memberform" method="post" action="member/registerTwo.action">
<input type="hidden" id="idCardImage" name="member.idCardImg"/>
<input type="hidden" id="drivingImage" name="member.drivingLicenseImg"/>
<table>
	<tr>
		<td>姓名:</td><td><input type="text" name="member.name"> </td>
	</tr>
	<tr>
		<td colspan="3" align="center"><input type="submit" value="注册"> </td>
	</tr>
</table>
</form>



<!-- 身份证上传 start -->
 <div class="container" id="crop-avatar">
    <!-- Current avatar -->
    <div class="avatar-view" id="avatar-view" style="width:348px;height:195px;" title="上传身份证件">
      <img src="common/js/cropper/images/idcard-license-pre_proc.jpg" alt="身份证照片">
    </div>
    <!-- Cropping modal -->
    <div class="modal fade" id="avatar-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <form class="avatar-form" id="avatar-form" action="member/upload.action" enctype="multipart/form-data" method="post">
          	<input type="hidden" name="imageData" id="imageData">
        
          	
            <div class="modal-header">
              <button class="close" data-dismiss="modal" type="button">&times;</button>
              <h4 class="modal-title" id="avatar-modal-label">身份证上传</h4>
            </div>
            <div class="modal-body">
              <div class="avatar-body">

                <!-- Upload image and data -->
                <div class="avatar-upload" id="avatar-upload">
                  <input class="avatar-src" id="avatar-src" name="pic_src" type="hidden">
                  <input class="avatar-data" id="avatar-data" name="pic_data" type="hidden">
                <label for="avatarInput">选择图片:</label>
                  <input class="avatar-input" id="avatarInput" name="upload" type="file">
                </div>

                <!-- Crop and preview -->
                <div class="row">
                  <div class="col-md-9" style="width: 95%;">
                    <div class="avatar-wrapper"  id="avatar-wrapper"></div>
                  </div>
                  <div class="col-md-3">
                    <div style="display:none;"class="avatar-preview preview-lg"></div>
                     <div style="display:none;" class="avatar-preview preview-md"></div>
                    <div style="display:none;" class="avatar-preview preview-sm"></div> 
                  </div>
                </div>

                <div class="row avatar-btns" id="avatar-btns">
                  <div class="col-md-9" style="width:100%%;">
                    <div class="btn-group">
                      <button class="btn btn-primary" data-method="rotate" data-option="-90" type="button" title="左转">左转</button>
                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-15" type="button">左转15度</button>
                     &nbsp; <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-30" type="button">左转30度</button>
                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-45" type="button">左转45度</button>
                    </div>
                    <div class="btn-group">
                      <button class="btn btn-primary" data-method="rotate" data-option="90" type="button" title="右转">右转</button>
                      <button style="display:none;"  class="btn btn-primary" data-method="rotate" data-option="15" type="button">15deg</button>
                      &nbsp;<button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="30" type="button">右转30度</button>
                      <button style="display:none;"  class="btn btn-primary" data-method="rotate" data-option="45" type="button">45deg</button>
                    </div>
                    <div class="col-md-3">
                    <button class="btn btn-primary btn-block avatar-save" id="avatar-save" type="submit">完成</button>
                  </div>
                  </div>
                  
                </div>
              </div>
            </div>
            <!-- <div class="modal-footer">
              <button class="btn btn-default" data-dismiss="modal" type="button">Close</button>
            </div> -->
          </form>
        </div>
      </div>
    </div><!-- /.modal -->

    <!-- Loading state -->
    <div class="loading" id="loading" aria-label="Loading" role="img" tabindex="-1"></div>
  </div>

<!-- 身份证上传 end -->

<br/>
<br/><br/>



<!-- 驾照上传 start -->
 <div class="container" id="crop-driving">
    <!-- Current avatar -->
    <div class="avatar-view" style="width:580px;height:232px;" id="driving-view" title="驾驶证">
      <img src="common/js/cropper/images/idcard-license-pre_proc.jpg" alt="驾照">
    </div>
    <!-- Cropping modal -->
    <div class="modal fade" id="driving-modal" aria-hidden="true" aria-labelledby="avatar-modal-label" role="dialog" tabindex="-1">
      <div class="modal-dialog modal-lg">
        <div class="modal-content">
          <form id="driving-form" class="avatar-form" action="member/upload.action" enctype="multipart/form-data" method="post">
          	<input type="hidden" name="imageData" id="imageData_driving">
            <div class="modal-header">
              <button class="close" data-dismiss="modal" type="button">&times;</button>
              <h4 class="modal-title" id="avatar-modal-label">驾驶证上传</h4>
            </div>
            <div class="modal-body">
              <div class="avatar-body">

                <!-- Upload image and data -->
                <div class="avatar-upload" id="driving-upload">
                  <input class="avatar-src" id="driving-src" name="pic_src" type="hidden">
                  <input class="avatar-data" id="driving-data" name="pic_data" type="hidden">
                <label for="avatarInput">选择图片:</label>
                  <input class="avatar-input" id="drivingInput" name="upload" type="file">
                </div>

                <!-- Crop and preview -->
                <div class="row">
                  <div class="col-md-9" style="width: 95%;">
                    <div class="avatar-wrapper"  id="driving-wrapper"></div>
                  </div>
                  <div class="col-md-3">
                    <div style="display:none;"class="avatar-preview preview-lg driving-preview"></div>
                     <div style="display:none;" class="avatar-preview preview-md driving-preview"></div>
                    <div style="display:none;" class="avatar-preview preview-sm driving-preview"></div> 
                  </div>
                </div>

                <div class="row avatar-btns" id="driving-btns">
                  <div class="col-md-9" style="width:100%%;">
                    <div class="btn-group">
                      <button class="btn btn-primary" data-method="rotate" data-option="-90" type="button" title="左转">左转</button>
                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-15" type="button">左转15度</button>
                     &nbsp; <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-30" type="button">左转30度</button>
                      <button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="-45" type="button">左转45度</button>
                    </div>
                    <div class="btn-group">
                      <button class="btn btn-primary" data-method="rotate" data-option="90" type="button" title="右转">右转</button>
                      <button style="display:none;"  class="btn btn-primary" data-method="rotate" data-option="15" type="button">15deg</button>
                      &nbsp;<button style="display:none;" class="btn btn-primary" data-method="rotate" data-option="30" type="button">右转30度</button>
                      <button style="display:none;"  class="btn btn-primary" data-method="rotate" data-option="45" type="button">45deg</button>
                    </div>
                    <div class="col-md-3">
                    <button class="btn btn-primary btn-block avatar-save" id="driving-save" type="submit">完成</button>
                  </div>
                  </div>
                  
                </div>
              </div>
            </div>
            <!-- <div class="modal-footer">
              <button class="btn btn-default" data-dismiss="modal" type="button">Close</button>
            </div> -->
          </form>
        </div>
      </div>
    </div><!-- /.modal -->

    <!-- Loading state -->
    <div class="loading" id="driving_loading" aria-label="Loading" role="img" tabindex="-1"></div>
  </div>

<!-- 驾照上传上传 end -->



</body>
</html>