function isnull(str)
{if(str==null||str=="")return true;
 return false;
}
function funCheck(sObj,frm)
{
	setcheck(sObj,frm,eval("document.sform"+sObj+"."+frm+"checkall").checked)
}
function setcheck(sObj,frm,flag)
{
  var tempstr,len,len1; 
	tmpstr=eval("document.sform"+sObj+"."+frm);
	if(isnull(tmpstr) )
	{
		return;
	}
	if(eval("document.sform"+sObj+"."+frm).length)
	{
   		for (var counter = 0; counter <eval("document.sform"+sObj+"."+frm).length; counter++){
   			if (eval("document.sform"+sObj+"."+frm)[counter].disabled == false)
				eval("document.sform"+sObj+"."+frm)[counter].checked=flag;
		}
			
	}
	else
	{
		eval("document.sform"+sObj+"."+frm).checked=flag;
	}

}

function checkOne()
{
	 if(isnull(document.sform.checkdel))
	 	{
	 		alert("��ѡ��һ���¼����༭��");
	 		return false;
	 	}
	var i=0; 	
	if(document.sform.checkdel.length)
	{
   	for (var counter = 0; counter < document.sform.checkdel.length; counter++)
			if (document.sform.checkdel[counter].checked)
				i++;
	}
	else
	{
		if(document.sform.checkdel.checked) i=1;
		else{
			alert("��ѡ��һ���¼����༭��");
		  return false;
	  }
	}
		if(i!=1)
		{
	 		alert("һ��ֻ�ܱ༭һ���¼��");
	 		return false;
	 	}
	 return true;
}	 	
function checkdeldata()
{
if(isnull(document.sform.checkdel)) return false;
	if(document.sform.checkdel.length)
	{
   	for (var counter = 0; counter < document.sform.checkdel.length; counter++)
		{

			if (document.sform.checkdel[counter].checked)
			{
				return true;

			}
		}

		 alert("��ѡ��Ҫ����ļ�¼");
	    return false;
	}
	else
	{
		if(document.sform.checkdel.checked) return true;
		alert("��ѡ��Ҫ����ļ�¼");
	   return false;
	}
}

//�ж�checkBox�Ƿ�ѡ��
function checkBox(obj){
	if(isnull(eval('document.sform.'+obj))) return false;
	if(eval('document.sform.'+obj).length){
   	for (var counter = 0; counter < eval('document.sform.'+obj).length; counter++){
			if (eval('document.sform.'+obj)[counter].checked)
				return true;
		}
	  return false;
	}
	else{
		if(eval('document.sform.'+obj).checked) return true;
		return false;
	}
}

function _getCbx(f){
	var str='';
	if(eval('document.sform.'+f).length>0){
		for(var i=0;i<eval('document.sform.'+f).length;i++){
			var eObj=eval('document.sform.'+f).options[i];
			str+=eObj.value+',';			
		}
	}
	return str;
}

function _moveT(eObj,t){
	alert(t);
	window.setTimeout(_moveT(eObj,t), 0);
}

function _move(eObj,t){
	//setTimeout("_wait()",10000);
	//_wait(100000);
	var oOption = document.createElement('OPTION');
	oOption.text=eObj.text;
	oOption.value=eObj.value;
	var l = eval('document.sform.'+t).length;
	eval('document.sform.'+t).options[l] = oOption;	
	
}

function _wait(time){
	//alert(time);
	if(null==time || 'undefined'==time)return;
	var t=0;
	for(var i=0;i<time;i++){
		//waiting...
		t+=i;
	}
}
function _moveItems(f,t){
  if(eval('document.sform.'+f).length<1) return;
	for(var i=eval('document.sform.'+f).length-1;i>=0;i--){
		var eObj=eval('document.sform.'+f).options[i];
		if(eObj.selected==true){
			_move(eObj,t);
			eval('document.sform.'+f).remove(i);
		}
  }	
}
function _moveItemsByIdStr(f,t,idstr){
  var ids=idstr.split(';');
  //alert(f+"--"+t+"--"+idstr);
  //alert(document.sform.flowAll);
  if(eval('document.sform.'+f).length<1) return;
	for(var i=eval('document.sform.'+f).length-1;i>=0;i--){
		var eObj=eval('document.sform.'+f).options[i];
		//alert(eObj.value+"--"+ids[0]);
		for(var j=0;j<ids.length;j++){
			if(eObj.value==ids[j]){
				//alert(eObj.value+"--"+ids[j]);
				_move(eObj,t);
				eval('document.sform.'+f).remove(i);
			}
		}
  	}	
}

function _removeAllItems(f){
  if(eval('document.sform.'+f).length<1) return;
	for(var i=eval('document.sform.'+f).length-1;i>=0;i--){
		eval('document.sform.'+f).remove(i);
  	}	
}


function _createOptions(text,value,sid){
	var oOption = document.createElement('OPTION');
	oOption.text=text;
	oOption.value=value;
	var l = eval('document.sform.'+sid).length;
	//alert(l);
	eval('document.sform.'+sid).options[l] = oOption;
}

function _removeAllItemsAndCreate(texts,values,sid){
	_removeAllItems(sid);
	if(''==texts||''==values)return;
	var atexts=texts.split(';');
	var avalues=values.split(';');
	
	for(var i=0;i<atexts.length;i++){
		//alert(atexts[i]);
		_createOptions(atexts[i],avalues[i],sid);
	}
}

function _moveAllItemsAndKeep(f,t){
  _removeAllItems(t);
  if(eval('document.sform.'+f).length<1) return;
	for(var i=0;i<eval('document.sform.'+f).length;i++){
		var eObj=eval('document.sform.'+f).options[i];
		_move(eObj,t);
	}
}

function _moveAllItemsAndKeepByIdStr(f,t,values){
  var avalues=values.split(';');
  //alert(avalues);
  _removeAllItems(t);
  if(eval('document.sform.'+f).length<1) return;
	for(var i=0;i<eval('document.sform.'+f).length;i++){
		var eObj=eval('document.sform.'+f).options[i];
		for(var j=0;j<avalues.length;j++)if(eObj.value==avalues[j])_move(eObj,t);
		//eval('document.sform.'+f).remove(i);
	}
}

function _moveAllItems(f,t){
  if(eval('document.sform.'+f).length<1) return;
	for(var i=eval('document.sform.'+f).length-1;i>=0;i--){
		var eObj=eval('document.sform.'+f).options[i];
		_move(eObj,t);
		eval('document.sform.'+f).remove(i);
	}
}
function selectAll(f){
	if(eval('document.sform.'+f).length<1) return false;
	for(var i=0;i<eval('document.sform.'+f).length;i++)
		eval('document.sform.'+f).options[i].selected=true;  
}
function swap(obj1,obj2){
		var txt=obj2.text;
		var val=obj2.value;
		obj2.text=obj1.text;
		obj2.value=obj1.value;
		obj1.text=txt;
		obj1.value=val;
}
function _moveUp(f){
	if(eval('document.sform.'+f).length<1) return;
	var idx=eval('document.sform.'+f).selectedIndex;
	if(idx>0){
		var obj1=eval('document.sform.'+f).options[idx-1];	
		var obj2=eval('document.sform.'+f).options[idx];
		swap(obj1,obj2)
		eval('document.sform.'+f).selectedIndex=idx-1;
	}
}
function _moveDw(f){
	if(eval('document.sform.'+f).length<1) return;
	var idx=eval('document.sform.'+f).selectedIndex;
	if(idx>=0&&idx<eval('document.sform.'+f).length-1){
		var obj1=eval('document.sform.'+f).options[idx];	
		var obj2=eval('document.sform.'+f).options[idx+1];
		swap(obj1,obj2)
		eval('document.sform.'+f).selectedIndex=idx+1;
	}
}
function  getSelectedItem(obj,msg,showalert){
	var selected = "";
	var result = "";
	var keyElement = window.document.getElementsByName(obj);		
	if(keyElement==null){
		if(false==showalert){}
		else{alert("no record exists!");}
		return null;
	}	
	//alert(keyElement.length);
	if(keyElement.length==null)
	{
		if(keyElement.checked){selected = keyElement.value;}
	}
	else{			
		for(var i=0;i<keyElement.length;i++)
		{
			if(keyElement[i].checked){selected += keyElement[i].value+";";}
		}
	}
	if(selected==null||selected==""){if(false==showalert){}else{alert(msg);}return null;}
	else{result = selected;}
	return result;	
 } 