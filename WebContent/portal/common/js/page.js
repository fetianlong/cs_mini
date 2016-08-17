

function isnull(str){if(str==null||str==""||str=="undefine")return true;
 return false;
}


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
	}else document.getElementById("sform").submit();
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
String.prototype.allTrim=function(){
	return this.replace(/(\s*)/g, "");
}
