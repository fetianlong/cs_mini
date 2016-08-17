isIE = (document.all ? true : false);
function SetData(curDate){return true;}
   var months = new Array("һ����", "������", "������", "�ġ���", "�塡��", "������", "�ߡ���", "�ˡ���", "�š���", "ʮ����", "ʮһ��", "ʮ����");
   var daysInMonth = new Array(31, 28, 31, 30, 31, 30, 31, 31,30, 31, 30, 31);
	 var dispMonth = new Date().getMonth();
	 var dispYear = new Date().getFullYear();
	 var frmObj;
function getDays(month, year){
	if (1 == month)
		return ((0 == year % 4) && (0 != (year % 100))) || (0 == year % 400) ? 29 : 28;
	else  return daysInMonth[month];
}
function getToday(){
		this.now = new Date();
    this.year = this.now.getFullYear();
    this.month = this.now.getMonth();
    this.day = this.now.getDate();
}
today = new getToday();
function newCalendar(eltName){
		today = new getToday();
    var parseYear = parseInt(dispYear + '');
    var newCal = new Date(parseYear,dispMonth,1);
    var day = -1;
    var startDayOfWeek = newCal.getDay();
    if ((today.year == newCal.getFullYear())&&(today.month == newCal.getMonth()))
		day = today.day;
    var intDaysInMonth =getDays(newCal.getMonth(), newCal.getFullYear());
    var daysGrid = makeDaysGrid(startDayOfWeek,day,intDaysInMonth,newCal,eltName)
		document.getElementById(eltName).innerHTML=daysGrid;
		setFrameSize(eltName);
}

function incMonth(delta,eltName){
	dispMonth += delta;
	if (dispMonth >= 12){
		dispMonth = 0;
		incYear(1,eltName);
	} 
	else if (dispMonth <= -1){
		dispMonth = 11;
		incYear(-1,eltName);
	} 
	else 
		newCalendar(eltName);
	
}
function changeCld(eltName){	
	dispMonth=document.getElementById(eltName+"SM").value-1;
	dispYear=document.getElementById(eltName+"SY").value;
	newCalendar(eltName);
}
function incYear(delta,eltName){
	dispYear = parseInt(dispYear + '') + delta;
	newCalendar(eltName);
}


function makeDaysGrid(startDay,day,intDaysInMonth,newCal,eltName){
	var daysGrid;
	var month = newCal.getMonth();
	var year = newCal.getFullYear();
	    
	var isThisYear = (year == new Date().getFullYear());
	var isThisMonth = (day > -1)
	var d=new Date(),mons,days;
	mons=d.getMonth()+1;
	days=d.getDate();
	if(mons<10) mons="0"+mons;
	if(days<10) days="0"+days;
	
	var dat3=d.getFullYear()+"-"+mons+"-"+days;
	
	    daysGrid='<iframe border="0" vspace="0" hspace="0" marginwidth="0" marginheight="0" framespacing="0" frameborder="1" scrolling="no" id="frameCal" src="javascript:\'\'" style="visibility:inherit; z-index:-1;filter=\'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)\';"></iframe><div id=cal >';
	    daysGrid += '<table class="calendarTable" cellpadding="2" cellspacing="0"  >';
	    
			daysGrid += '<tr class=clsth><TD colSpan=7 nowrap>����&nbsp;<SELECT onchange="changeCld(\''+eltName+'\')" name="'+eltName+'SY" id="'+eltName+'SY'+'"> ';
	    for(i=1900;i<2050;i++)
        	if(i==dispYear) daysGrid += '<option value='+i+' selected>'+i;
        	else daysGrid += '<option value='+i+'>'+i;
				daysGrid += '</SELECT>&nbsp;��</font>&nbsp;<SELECT onchange="changeCld(\''+eltName+'\')" name="'+eltName+'SM" id="'+eltName+'SM'+'">';
        
        for(i=1;i<13;i++) 
        	if(i==dispMonth+1) daysGrid += '<option value='+i+' selected>'+i;
        	else daysGrid += '<option value='+i+'>'+i;
        daysGrid += '</SELECT>&nbsp;��</td></tr>';
            
	    daysGrid += '<tr><td colspan=7 style="background:#ffffff;" nowrap><div align=center>';
	    daysGrid += '<font face="courier new, courier" >';
	    daysGrid += '&nbsp;<a href="javascript:incMonth(-1,\'' + eltName + '\')" style="color:#000000">&laquo; </a>';
	    daysGrid += '<b>';
	    if (isThisMonth) { daysGrid += '<font color=red>' + months[month] + '</font>'; }
	    else { daysGrid += months[month]; }
	    daysGrid += '</b>';
	    daysGrid += '<a href="javascript:incMonth(1,\'' + eltName + '\')"  style="color:#000000"> &raquo;</a>';
	    daysGrid += '&nbsp;&nbsp;&nbsp;';
	    daysGrid += '<a href="javascript:incYear(-1,\'' + eltName + '\')"  style="color:#000000">&laquo; </a>';
	    daysGrid += '<b>';
	    if (isThisYear) { daysGrid += '<font color=red>' + year + '</font>'; }
	    else { daysGrid += ''+year; }
	    daysGrid += '</b>';
	    daysGrid += '<a href="javascript:incYear(1,\'' + eltName + '\' )"  style="color:#000000" > &raquo;</a>';
	    daysGrid += '&nbsp;</div></td></tr>';
	    daysGrid += '<tr class=clsthWeek><td><font color=#ff0000>��<font color=#000000></td><td>һ</td><td>��</td><td>��</td><td>��</td><td>��</td><td><font color=#ff0000>��<font color=#000000></td></tr><tr>';
	    var dayOfMonthOfFirstSunday = (7 - startDay + 1);
	    var dayOfMonth,intWeek;
	    
	    for (intWeek = 0; intWeek < 6; intWeek++){	       
	       for (var intDay = 0; intDay < 7; intDay++){
	         dayOfMonth = (intWeek * 7) + intDay + dayOfMonthOfFirstSunday - 7;
				 	 if(dayOfMonth <= 0) 
				 	     	daysGrid += '<td></td>';
				 	 else if (dayOfMonth <= intDaysInMonth){
				   		var color = "blue";
				   		daysGrid += '<td class=dayTd onMouseOver="dayIn(this);" onMouseOut="dayOut(this);" onclick="javascript:setDay('+ dayOfMonth + ',\'' + eltName + '\')">';
				   		var dayString=dayOfMonth+'';
				   		if (dayString.length == 1) dayString = '0' + dayString;
				   		if (day > 0 && day == dayOfMonth) 
				   			dayString = '<span class=calToday>'+dayString+'</span></td>';
				   		else
				   			dayString = dayString+'</td>';
				   		daysGrid += dayString;
				 	}else{
				 		daysGrid += '<td></td>';
					}
	       }
	       if (dayOfMonth < intDaysInMonth)
	       	daysGrid += '</tr><tr>';
	       else 
	       	break;
	    }
	    var newToday = dat3.substring(0,4)+''+dat3.substring(5,7);
	    daysGrid += '</tr><tr class=clsth><td colspan=7 style="background:#ffffff;" nowrap><div align=center><font color=red>&nbsp;���죺<a href="javascript:setToday(\''+newToday+'\',\'' + eltName + '\')">'+dat3+'</a>&nbsp;</fond>';
			daysGrid += '<a href="javascript:clearDate(\'' + eltName + '\')" >���</a> ';
	    daysGrid += '<span style="margin-top:8px;height:20px;"><img src=../common/skins/default/images/closebutton.gif border=0 onclick="javascript:hideElement(\'' + eltName + '\');" /></span>';
	    return daysGrid + "</div></td></tr></table></div>";
}
function dayIn(obj){
	obj.className='dayTdOn';
}
function dayOut(obj){
	obj.className='dayTd';
}

function clearDate(eltName){
	frmObj.value='';
	hideElement(eltName);
}
function getLeft(e){   
	var ret=e.offsetLeft;
	while(e=e.offsetParent)ret+=e.offsetLeft;
	return ret;   
}   
function getTop(e){   
	var ret=e.offsetTop;
	while(e=e.offsetParent)ret+=e.offsetTop;
	return ret;   
}   
function getHeight(e){   
  return e.offsetHeight;   
}   
function getWidth(e){   
  return e.offsetWidth;
}   

function setDay(day,eltName){
	var tmp="";
	if(dispMonth + 1<10)
		tmp =dispYear+"0"+(dispMonth + 1);
	else
		tmp =dispYear+""+(dispMonth + 1);
	
	//if(day<10) tmp=tmp+"-0"+day;
	//else tmp=tmp+"-"+day;
	
	if(SetData(tmp)){
		frmObj.value=tmp;
		hideElement(eltName);
	}

	
}
function setToday(str,eltName){
	if(SetData(str)){
		frmObj.value=str;
		hideElement(eltName);
	}	
}
function hideElement(eltname){document.getElementById(eltname).innerHTML='';}

function toggleDatePicker(eltName,obj){ 
	frmObj=document.getElementById(obj);
	if(frmObj.value!='')
		initCld(eltName);
	else
 		newCalendar(eltName);
}
function setFrameSize(eltName){
 	var objPos=document.getElementById(eltName);
 	var objCal=document.getElementById('cal');
 	var objFrame=document.getElementById('frameCal');
	objFrame.style.left=-1;
	objFrame.style.top=-1;
	objFrame.style.width=(getWidth(objCal)+10)+'px';
	objFrame.style.height=(getHeight(objCal)+10)+'px';
	//alert(getLeft(objFrame)+','+getTop(objFrame)+','+getWidth(objFrame)+','+getHeight(objFrame));
}
function initCld(eltName){
	var str=frmObj.value;
	dispYear=parseInt(str.substring(0,4));
	dispMonth=parseInt('100'+str.substring(4,6));
	dispMonth=dispMonth%100-1;
	newCalendar(eltName);
}

