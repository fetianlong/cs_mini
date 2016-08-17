var ie;
if (document.all) ie=1;
else ie=0;

function isnull(str){if(str==null||str==""||str=="undefine")return true;
 return false;
}

function killErrors() { 
return true; 
} 
window.onerror = killErrors; 


function commonJump(func){
	skipToPage(document.getElementById('page.currentPage').value, func);
}
function commonJump2(func){
	skipToPage2(document.getElementById('page2.currentPage').value,func);
}


function skipToPage(page, func)
{
	document.getElementById('page.currentPage').value=page;
	if (typeof func == 'function'){
		func();
	}else document.sform.submit();
}

function skipToPage2(page, func){
	document.getElementById('page2.currentPage').value=page;
	if (typeof func == 'function'){
		func();
	}else document.form2.submit();
}
function SetOrder(str)
{
	var orderFlag=0;
	document.getElementById('page.orderString').value=str;
	if(!isnull(document.getElementById('page.orderFlag').value)) 
		orderFlag=document.getElementById('page.orderFlag').value;
	document.getElementById('page.orderFlag').value=1 - orderFlag;
	document.sform.submit();
}
function $() {
  var elements = new Array();
  for (var i = 0; i < arguments.length; i++) {
    var element = arguments[i];
    if (typeof element == 'string')
      element = document.getElementById(element);
    if (arguments.length == 1)
      return element;
    elements.push(element);
  }
  return elements;
}
String.prototype.allTrim=function(){
	return this.replace(/(\s*)/g, "");
}
function querySubmit(){
	$('keyword').value=$('keyword').value.allTrim();
	$('cid').value=$('cid').value.allTrim();
	document.sform.submit();
}