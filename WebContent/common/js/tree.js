function isnull(str){if(str==null||str==""||str=="undefine")return true;
 return false;
}
var preDD1='',preDD2='';  //dd1Ϊ�м���dd2Ϊ����
var preCity=0;
function _clkCity(cid){  //ʵ�ֵ������Ч��
	try{
	if(preCity!=cid){    //��һ����id��������id����ͬʱ
		if(preCity!=0)   //�����һ����id��Ϊ0������һ���е�classname����ΪfocusOff������������ʾΪ����ɫ
			$('ca'+preCity).className='focusOff';  //ca + precity����ʾ���ǳ������ֵ�<a></a>��id
		$('ca'+cid).className='focusOn';  //����������ʾΪ��ɫ
		preCity=cid;
	}
 }catch(ex){}
}
function clickTree(objName,clsName,actClsName,id){
	try{
			var tabList = document.getElementsByClassName(actClsName);
		  $A(tabList).each(function(node){
		  	if(node.id==(objName+id+'')){
		  		node.className=clsName;
		  		return;
		  	}
		  	node.className=clsName;
		  });
		  $(objName+id).className=actClsName;
		}catch(ex){}
}
function dsdiv(DT){
try{
	if(preDD1==DT){
		$('dd'+DT).className='closeDD';
		preDD1='';
	}else{
		if(!isnull(preDD1))
			$('dd'+preDD1).className='closeDD';
		$('dd'+DT).className='openDD';
		preDD1=DT;
	}
	setCity1(DT);
 }catch(ex){}
}
function d2div(DT,eFlag){  //dtΪ����id��eFlagΪ���
try{
	if(preDD2==DT){  //dd2Ϊ��������
		$('ddd1'+DT).className='closeDD';  //�ر���
		preDD2='';
	}else{
		if(!isnull(preDD2))  //preDD2��Ϊ��
			$('ddd1'+preDD2).className='closeDD';
		$('ddd1'+DT).className='openDD';
		preDD2=DT;
	}
	setCity2(DT,eFlag);		//��������
 }catch(ex){}
}
function initFileTree(lid,cid,cname){
	
	var str='';
	var eObj;
	try{
		switch(parseInt(lid)){
			case 0:	
				str='<dl><div style="padding-top:5px;"><STRONG><a href="javascript:parent.subMainFrame.q_aId();">ȫ��</a></STRONG></div>';
				eObj=city0[0].split(',');
				for(var i=0;i<eObj.length-1;i=i+2)
					str+='<dt class=off onclick="dsdiv(\''+eObj[i]+'\');"></dt><dt><a href="javascript:_clkCity('+eObj[i]+');parent.subMainFrame.q_id('+eObj[i]+',\''+eObj[i+1]+'\')" id=ca'+eObj[i]+'>'+eObj[i+1]+'</a></dt><div class=clear></div><span id=dd'+eObj[i]+' class="closeDD"></span>';
				str+='</dl>';
				$('treeList').innerHTML=str;
				break;
			case 1:
			case 2:
				if(cid%10000==0){
					str='<dl>';
					str+='<dt class=off onclick="dsdiv(\''+cid+'\');"></dt><dt><a href="javascript:_clkCity('+cid+');parent.subMainFrame.q_id('+cid+',\''+cname+'\')" id=ca'+cid+'>'+cname+'</a></dt><div class=clear></div><span id=dd'+cid+' class="closeDD"></span>';
					str+='</dl>';
					$('treeList').innerHTML=str;
					dsdiv(cid);
				}else if(cid%100==0){
					str='<dl>';
					str+='<dt class=dd12 onclick="d2div(\''+cid+'\',1);"></dt><dt><a href="javascript:_clkCity('+cid+');parent.subMainFrame.q_id('+cid+',\''+cname+'\')" id=ca'+cid+'>'+cname+'</a></dt><div class=clear></div><span id=ddd1'+cid+' class="closeDD"></span>';
					str+='</dl>';
					$('treeList').innerHTML=str;
					d2div(cid,1);						
				}else{
					str='<dl><div style="padding-top:5px;"><STRONG>�û�����</STRONG></div>';
					str+='<dd class=dd61><a href="javascript:_clkCity('+cid+');parent.subMainFrame.q_id('+cid+',\''+cname+'\');" id=ca'+cid+'>'+cname+'</a></dd>';
					str+='</dl>';
					$('treeList').innerHTML=str;
				}
				
				break;
			default:
				alert('�޲���Ȩ�ޣ�');
				break;
		}
	}catch(ex){}
}

function setCity1(code0){
	if(isnull($('dd'+code0).innerHTML)){		
		var str='';
		var code=(code0+'').substring(0,2);
		var eObj=city1[code].split(',');
		for(var i=0;i<eObj.length-1;i=i+2)	{
			if(!isnull(eObj[i]))
				str+='<dt class=dd1'+(i==eObj.length-3?'1':'')+' id=dd1'+eObj[i]+' onclick="d2div(\''+eObj[i]+'\','+(i==eObj.length-3?'1':'0')+');"></dt><dt><a href="javascript:_clkCity('+eObj[i]+');parent.subMainFrame.q_id('+eObj[i]+',\''+eObj[i+1]+'\');" id=ca'+eObj[i]+'>'+eObj[i+1]+'</a></dt><div class=clear></div><span id=ddd1'+eObj[i]+' class=closeDD1></span>';
		}
		$('dd'+code0).innerHTML=str;
	}
		
}
function setCity2(code0,eFlag){
	var codeTail = (code0+'').substring(4,6);
	if(codeTail != '00') return;
	if(isnull($('ddd1'+code0).innerHTML)){		
		var str='';
		var code=(code0+'').substring(0,4);
		var eObj=city2[code].split(',');
		for(var i=0;i<eObj.length-1;i=i+2)	{
			if(!isnull(eObj[i]))
				str+='<dd class=dd'+(eFlag==1?'6':'2')+(i==eObj.length-3?'1':'')+'><a href="javascript:_clkCity('+eObj[i]+');parent.subMainFrame.q_id('+eObj[i]+',\''+eObj[i+1]+'\');" id=ca'+eObj[i]+'>'+eObj[i+1]+'</a></dd>';
		}
		$('ddd1'+code0).innerHTML=str;
	}		
}


