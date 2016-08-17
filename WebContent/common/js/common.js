var dialog;
var confirmdialog;
var parentdialog;
function showLoading(parent){
	if (typeof confirmdialog != 'undefined'){
		confirmdialog.unlock();
	}
	if (typeof parent == 'undefined'){
		dialog = $.dialog({
			title : false,
		    content: "<div style='margin: 0 auto;width:150px;text-align:center;'><img style='vertical-align:middle;' src='css/images/loading.gif'/><span style='margin-left:10px;font-weight:bold;'>系统处理中</span></div>",
		    drag : false,
			max: false,
		    min: false,
		    lock: true,
		    ok: false,
		    cancel: false,
		    close: function () {
		        return true;
		    }
		});
	}else{
		parentdialog = parent;
		dialog = $.dialog({
			title : false,
		    content: "<div style='margin: 0 auto;width:150px;text-align:center;'><img style='vertical-align:middle;' src='css/images/loading.gif'/><span style='margin-left:10px;font-weight:bold;'>系统处理中</span></div>",
		    drag : false,
			max: false,
		    min: false,
		    lock: true,
		    ok: false,
		    parent : parent,
		    cancel: false,
		    close: function () {
		        return true;
		    }
		});
	}
}

function hideLoading(){
	if (typeof dialog != 'undefined'){
		dialog.close();
	}
}

function hideParentDialog(){
	if (typeof parentdialog != 'undefined'){
		parentdialog.close();
		parentdialog = null;
	}
}

function alertok(content, func){
	if (typeof func == 'undefined'){
		$.dialog({
			title : "系统提示",
		    content: content,
		    drag : false,
			max: false,
		    min: false,
		    lock: true,
		    icon: 'success.gif',
		    ok: true,
		    cancel: false,
		    close: false
		});
	}else{
		$.dialog({
			title : "系统提示",
		    content: content,
		    drag : false,
			max: false,
		    min: false,
		    lock: true,
		    icon: 'success.gif',
		    ok: func,
		    cancel: false,
		    close: false
		});
	}
}

function alertinfo(content){
	$.dialog({
		title : "系统提示",
	    content: content,
	    drag : false,
		max: false,
	    min: false,
	    lock: true,
	    icon: 'alert.gif',
	    ok: true,
	    cancel: false,
	    close: false
	});
}

function alertException(content){
	$.dialog({
		title : "系统提示",
	    content: content,
	    drag : false,
		max: false,
	    min: false,
	    lock: true,
	    icon: false,
	    ok: true,
	    cancel: false,
	    close: false
	});
}

function alertconfirm(content, func){
	confirmdialog = $.dialog({
		title : "系统提示",
	    content: content,
	    drag : false,
		max: false,
	    min: false,
	    lock: true,
	    icon: 'confirm.gif',
	    ok: func,
	    cancel: true,
	    close: false
	});
}

function alerterror(content){
	if (typeof parentdialog == 'undefined'){
		$.dialog({
			title : "系统提示",
		    content: content,
		    drag : false,
			max: false,
		    min: false,
		    lock: true,
		    icon: 'error.gif',
		    ok: true,
		    cancel: false,
		    close: false
		});
	}else{
		$.dialog({
			title : "系统提示",
		    content: content,
		    drag : false,
			max: false,
		    min: false,
		    lock: true,
		    parent : parentdialog,
		    icon: 'error.gif',
		    ok: true,
		    cancel: false,
		    close: false
		});
	}
}

function alertdefine(content, title){
	$.dialog({
		title : title,
	    content: content,
	    drag : false,
		max: false,
	    min: false,
	    lock: true,
	    icon: false,
	    ok: true,
	    cancel: false,
	    close: false
	});
}


function logo_user(obj){
	try{
		obj.onerror=null;
		obj.src='../images/common/bklogo.jpg';
	}catch(ex){}
}
function Get_IE_Version(){
	var v;
	if (navigator.userAgent.indexOf("MSIE 6.0") > 0)
		v = 6;
	 else if (navigator.userAgent.indexOf("MSIE 7.0") > 0)
		v = 7
	 else if (navigator.userAgent.indexOf("MSIE 8.0") > 0)
		v = 8;
	return v;
} 
function isnull(str){if(str==null||str==""||str=="undefine")return true;
 return false;
}
String.prototype.lengthchinese = function(){
			return this.replace(/[^\x00-\xff]/g,"**").length;
}
String.prototype.lenB = function(){return this.replace(/[^\x00-\xff]/g,"**").length;}   
String.prototype.trim=function(){
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
String.prototype.allTrim=function(){
	return this.replace(/(\s*)/g, "");
}
function getExt(file) {
	return (/[.]/.exec(file)) ? /[^.]+$/.exec(file.toLowerCase()) : '';
}
function strReverse(s){
	return s.replace(/(<BR>|<BR\/>)/ig, "\n");
}
function strPackage(s){
	if(isnull(s)) return '';
	s=s.replace(/(@)/ig, "＠");
	s=s.replace(/(,)/g, "，");
	return s;
}
function dePackage(s){
	if(isnull(s)) return '';
	s=s.replace(/(＠)/ig, "@");
	return s;
}

function strRemoveBr(s){
	return s.replace(/(<BR>|<BR\/>)/ig, "");
}
function strConvert(s){
	s=s.replace(/(<)/ig, "&lt;");
	s=s.replace(/(\n)/g, "<BR>");
	return s;
}
function GetLocationString(key){
	var aParams = document.location.search.substr(1).split('&') ;
	for (i = 0 ; i < aParams.length ; i++) {
		var aParam = aParams[i].split('=');
		var actionName = aParam[0];
		if(actionName == key){
			return aParam[1];
		}
	}
	return "";
}

function killErrors() { 
return true; 
} 
window.onerror = killErrors; 
function substr(str, len) {
	if(!str || !len) { return ''; }
	var a = 0;
	var temp = '';
	for (var i=0;i<str.length;i++){
		a+=str.charCodeAt(i)>255?2:1;
		if(a >len)return temp;
		temp += str.charAt(i);
	}
	return str;
};

var backHtml='';
function backupInfo(target){
	try{
	if(isnull(target))
		target='submitId';
	backHtml=$('#'+target)[0].innerHTML;
	}catch(ex){}
}
function restoreInfo(target){
	try{
	if(isnull(target))
		target='submitId';
	$('#'+target)[0].innerHTML=backHtml;
	}catch(ex){}
}
function processing(m,target){
try{
	if(isnull(m)) m='<span class=loading>数据处理中，请稍候...</span>';
	if(isnull(target)) target='submitId';
	$('#'+target)[0].innerHTML=m;
	}catch(ex){}
}

function BASEisNotNum(theNum){
//判断是否为数字
if (isnull(theNum))
return true;
for(var i=0;i<theNum.length;i++){
oneNum=theNum.substring(i,i+1);
if (oneNum<"0" || oneNum>"9")
return true;
}
return false;
}

function BASEisNotInt(theInt){
  //判断是否为整数
	theInt=theInt.trim();
  if ((theInt.length>1 && theInt.substring(0,1)=="0") || BASEisNotNum(theInt)){
  return true;
  }
  return false;
}
function BASEisNotSignInt(theInt){
  if(BASEisNotInt(theInt)) return true;
  if(parseInt(theInt)<0) return true;
  else return false; 
}

function BASEisNotFloat(theFloat){
  //判断是否为浮点数
  len=theFloat.length;
  dotNum=0;
  if (len==0)
  return true;
  for(var i=0;i<len;i++){
  oneNum=theFloat.substring(i,i+1);
  if (oneNum==".")
  dotNum++;
  if ( ((oneNum<"0" || oneNum>"9") && oneNum!=".") || dotNum>1)
  return true;
  }
  if (len>1 && theFloat.substring(0,1)=="0"){
  if (theFloat.substring(1,2)!=".")
  return true;
  }
  return false;
}


function checkSelect(chkName){
	var bRet=false; 
	$("[name='"+chkName+"']").each(function(){
		if($(this).attr('checked')){
			bRet=true;
			return;
			}
		});
	return bRet;
}

function requestError(){
	hideLoading();
	alertException("网络异常或后台繁忙，请稍后再试。");
	restoreInfo();
}

function getWorkSpace(){
	return $;
}
String.prototype.replaceAll = function (findText, repText){
    var newRegExp = new RegExp(findText, 'gm');
    return this.replace(newRegExp, repText);
}



Array.prototype.indexOf = function(val) {              
    for (var i = 0; i < this.length; i++) {  
        if (this[i] == val) return i;  
    }  
    return -1;  
};  


Array.prototype.remove = function(val) {  
    var index = this.indexOf(val);  
    if (index > -1) {  
        this.splice(index, 1);  
    }  
};  


