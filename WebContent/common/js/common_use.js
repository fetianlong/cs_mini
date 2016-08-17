/**
func:get key value from a single select list
input:obj--key
msg--alert message
output:XMLHTTP object
*/
var IE=true;
xmlDoc_();
function getTree(objName,absolutePath){	
	var zsdTree=null;	
		//alert(IE);
		zsdTree=new pTree(objName,absolutePath);	
		/*
		if(IE){
			//alert("IE==true");
			zsdTree=new pTree(objName,absolutePath);	
		}
		else{
			//alert("IE==false");
			zsdTree=new fxpTree(objName,absolutePath);
		}
		*/
	return zsdTree;
}
/**
func:get key value from a single select list
input:obj--key
msg--alert message
output:XMLHTTP object
*/
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
/**
func:get key value from a single select list
input:obj--key
msg--alert message
output:XMLHTTP object
*/
function  getSelectedItemValue(nameobj,keyobj,key){
	if(undefined==nameobj || key =="")return null;
	if(undefined==nameobj.length && nameobj!=null)tkmc=nameobj[i].value;
	for(var i=0;i<nameobj.length;i++){
		if(keyobj[i].value==key)return nameobj[i].value;
	}
	return null;
}
/**
func:create an XMLHTTP object
input:null
output:an XMLHTTP object
*/
function http_(){
	   var http=null;
		try {
		    http=new ActiveXObject("Msxml2.XMLHTTP");
		}
		catch(e) {
		    try {
		        http=new ActiveXObject("Microsoft.XMLHTTP");
		    }
		    catch(oc) {
				if ( !http && typeof window.XMLHttpRequest != "undefined" ) {
				    http=new XMLHttpRequest();
				}
		    }
		}		
		return http;
} 
/**
func:create a DOM object
input:null
output:a DOM object
*/
function xmlDoc_(){
	   var xmlDoc=null;
		try {
		    xmlDoc=new ActiveXObject("Msxml2.DOMDocument");
		}
		catch(e) {
		    try {
		        xmlDoc=new ActiveXObject("Microsoft.DOMDocument");
		    }
		    catch(oc) {					
				//var   doc   =   (new   DOMParser()).parseFromString(sXML, 'text/xml ')			
				
		        xmlDoc=new DOMParser();//(new DOMParser()).parseFromString("xmlText","text/xml");	
		        IE=false;
		        //alert(xmlDoc);     	      
		    }
		}		
		//alert(IE);
		return xmlDoc;
} 	
/**
func:do AJAX save
input params:
		url--service
		reload--refresh the page,value avalable:true/false
		method--get/post
		type--need revalue,boolean means need revalue:true/false,xml means need revalue:http.responseXML object,text 
means need revalue:http.responseTextobject
		pxml--the segment for post method
output:a DOM object
*/
function ajaxpost(method,url,reload,type,pxml){
		//alert(url+"---"+type);
        var xmlHttp_ =  http_();				                              
        var xmlDoc=xmlDoc_();		                   
        xmlHttp_.open(method,url,false);		                    
        xmlHttp_.setRequestHeader("context-type","text/xml;charset=utf-8"); 
        xmlHttp_.send(pxml);
        var showstr=xmlHttp_.responseText;
        if(type=="xml"){	
        	//alert(xmlHttp_.responseXML);
        	return xmlHttp_.responseXML;
        }
        if(type=="text"){	        	
        	return xmlHttp_.responseText;
        }        
        //alert(showstr);
        if(IE){
            xmlDoc.loadXML(showstr);
            if(xmlDoc.parseError.line > 0) {
                var myErr = xmlDoc.parseError;
                if(type=="boolean"){
                	return false;
                }
                else {alert("oper fail:" + myErr.reason);}
            }else {             	                             
                 if(xmlDoc.selectNodes("//result").length!=0) {
                     var allNode = xmlDoc.selectNodes("//result");              
                     if(allNode.item(0).getAttribute('status')=='success'){                   
                         if(reload){
                         	window.location=window.location;
                         	//window.location.reload();
                         }	
                         if(type=="boolean"){
                			return true;
                		}	                                 
                        else {alert("oper success");}
                      } else {
                      	if(type=="boolean"){
                			return allNode.item(0).getElementsByTagName("msg")[0].text;
                		}
                        else {alert("oper fail");}
                      } 
                 }
            }    
        }
        else {
        	var oXmlDom = window.document.implementation.createDocument("", "", null);
        	oXmlDom=xmlDoc.parseFromString(showstr,"text/xml");  
        	if (oXmlDom.documentElement.tagName != "parsererror") {			                		
        		 var allNode = oXmlDom.selectNodes("//result");
        		 if(allNode[0].getAttribute("status")=="success"){ 
					 if(reload){
                             	window.location=window.location;
                             	//window.location.reload();
                             }	
                             if(type=="boolean"){
                    			return true;
                    		}	                                 
                     else {alert("oper success");	}
                 }else {
                      	if(type=="boolean"){                      		
                			return getText(allNode[0].getElementsByTagName("msg")[0]);
                		}
                        else {alert("oper fail");}
                } 					   
			
			} else {								
			   if(type=="boolean"){
                	return false;
                }
                else {alert("oper fail");}
			}			                	
        }
}	



/**
func:for partly refresh
input params:
		id--the div id needed to be refreshed;
     	URL--AJAX execute url,then an xml segment will be returned;
output:null
*/
function refresh(id,URL)
{	
	var xmlDoc = new ActiveXObject("MSXML.DOMDocument");
	var http = xmlhttp();
	http.open("get",URL,false);
	http.setRequestHeader("context-type","text/xml");
	//http.setRequestHeader("charset","utf-8");
	http.send();
	var str=http.responseText;
	var div=window.document.getElementById(id);
	if (div!=null) div.innerHTML=str; 	
}

/**
	产生年份下拉列表，从当前年份往前paramnum年的列表
	paramnum:需要生成的数量,默认值10
	paramselectedyear:选中的年份
*/
function generateYearList(paramnum,paramselectedyear){
	 var num = paramnum;
	 var selectedyear = paramselectedyear;
	 if(null==num || undefined ==num ||""==num)num=10;
	 if(null==selectedyear || undefined ==selectedyear ||""==selectedyear)selectedyear=0;
	 var year=new Date().getYear();
     var str="<option value='' selected='true'>选择年份</option>";
     document.writeln(str);
     var nowyear = 0;      
	 for(var i=num;i>-1;i--){
	 		 nowyear = (year-(num-i));
		     if(nowyear==selectedyear){
		     	str="<option selected='true' value="+nowyear+">"+nowyear+"</option>";
		     }
		     else str="<option value="+nowyear+">"+nowyear+"</option>";
			 document.writeln(str);
	  }
}


/**
func: to make FIREFOX to support methods selectNodes()茫聙聛selectSingleNode()
the code is learned from:http://km0ti0n.blunted.co.uk/mozXPath.xap
check for XPath implementation
*/
if(window.document.implementation.hasFeature("XPath", "3.0"))
{
	// prototying the XMLDocument
	XMLDocument.prototype.selectNodes = function(cXPathString, xNode)
	{
		if( !xNode ) { xNode = this; } 
		var oNSResolver = this.createNSResolver(this.documentElement);
		var aItems = this.evaluate(cXPathString, xNode, oNSResolver, 
		XPathResult.ORDERED_NODE_SNAPSHOT_TYPE, null)
		var aResult = [];
		for( var i = 0; i < aItems.snapshotLength; i++)
		{
			aResult[i] = aItems.snapshotItem(i);
		}
		return aResult;
	}

	// prototying the Element
	Element.prototype.selectNodes = function(cXPathString)
	{
		if(this.ownerDocument.selectNodes)
		{
		   return this.ownerDocument.selectNodes(cXPathString, this);
		}
		else{throw "For XML Elements Only";}
	}
}
/**
func:to make dom support the method getText under firefox
input prams:node of dom
out put:text
*/
function getText(oNode) {

    var sText = "";

    for (var i = 0; i < oNode.childNodes.length; i++) {

       if (oNode.childNodes[i].hasChildNodes()) {

           sText += getText(oNode.childNodes[i]);

       } else {

           sText += oNode.childNodes[i].nodeValue;

       }
    }
    return sText;

}
/**get the current time of system*/
function getDate(format){
    var myDate = new Date();
    /**
    myDate.getYear();       //
    myDate.getFullYear();   //1970-????
    myDate.getMonth();      //0-11
    myDate.getDate();       //1-31
    myDate.getDay();        //0-6
    myDate.getTime();       //1970
    myDate.getHours();      //0-23
    myDate.getMinutes();    //0-59
    myDate.getSeconds();    //0-59
    myDate.getMilliseconds();   //0-999
    myDate.toLocaleDateString();    //
    */
    //var mytime=myDate.toLocaleTimeString();    //
    //myDate.toLocaleString( );       //
    if(null==format||"undefined"==format)format="-";
    return myDate.getFullYear()+format+(myDate.getMonth()+1)+format+(myDate.getDate()); ;
 }


	var stk_Width=60;
	var stk_Height=50;
	function CreateInfo(){		
		var infospan=document.createElement("span");
		infospan.id="s_info";
		infospan.name="s_info";
		infospan.style.position="absolute";
		infospan.style.zIndex="1000";
		infospan.style.color="green";
		infospan.style.visibility="hidden";		
		document.getElementById("hint").appendChild(infospan);
	}
	function getInfospan(){
		return document.getElementById("s_info");
	}
	function loadJson(obj,ev){	
			
			var Oinfo=getInfospan();
			//alert(Oinfo.style.visibility);
			posMouse(Oinfo,ev);	
			Oinfo.innerHTML="<table bgcolor='blue' width="+stk_Width+" height="+stk_Height+"><tr><td font size=6>"+obj.msg+"</td></tr></table>";	
			//if(null==obj.msgcolor|| undefined==obj.msgcolor)alert(obj.msgcolor);
			Oinfo.style.color=((null==obj.msgcolor|| undefined==obj.msgcolor)?"green":obj.msgcolor);
			Oinfo.style.visibility = "";
		};
	function ShowInfo(obj,ev){	
			if(null==getInfospan())CreateInfo();	
			loadJson(obj,ev);
		}
	function DropInfo(){
			var Oinfo=getInfospan();
			Oinfo.style.visibility = "hidden";
		};	
	function HiddenInfo(obj,ev){
			DropInfo();	
		}
	function posMouse(oList,ev)
		{
			var cw=document.documentElement.clientWidth;
			var ch=document.documentElement.clientHeight;
			var cl=document.documentElement.scrollLeft;
			var ct=document.documentElement.scrollTop;
			//alert(oList);
			if(oList){ev=ev||window.event;x=ev.clientX;y=ev.clientY;
				if(cl+x+stk_Width>cl+cw){oList.style.left=cl+x-stk_Width+"px";}
				else{oList.style.left=cl+x+"px";}
				if(ct+y+stk_Height>ct+ch){oList.style.top=ct+y-stk_Height+"px";}
				else{oList.style.top=ct+y+"px";}
			}
		}

		
		function window_maxmize(){	
			//alert(screen.width+"--"+screen.height);
			fullscreen = true;
			//window.moveTo(0,0);   
			//window.resizeBy(screen.width,screen.height);
			//alert(screen.width+"--"+screen.height);
			//window.open(document.location, 'big', 'fullscreen=yes');
			//var win = window.open("Main.aspx","_blank","fullscreen=yes"); 
			//window.opener=null; 
			//window.open('','_self'); 
			//window.close(); 
			//win.moveTo(0,0); 
			//win.resizeTo(screen.availWidth,screen.availHeight); 
		}
	/*
		a common use js for batch data input
	*/
	Batch = {
		totalAllowed : 3,
		initnum : 1,
		items : 1,
		nowitems : 1,
		effectiveitems : 1,
		checkitems_id : new Array("billhis","cCostDTO.ccPayDate","cCostDTO.ccMonneyAmount"),
		defaultlinestr: '',
		listlineid : 'linestr',
		headstrid : 'headstr',	
		buttonstrid : 'buttonstr',
		samplelinestr : 'samplelinestr',		
		checkboxid : 'bacth_list_checkbox_id',
		item_sample_id : 'item_sample_id',
		sample_tag_name : 'sample',
		sample_tag_id : 'sample_tag_id',
		sample_msg_1 : '\u6279\u91CF\u5F55\u5165\u6BCF\u6B21\u5141\u8BB8\u5F55\u5165\u6700\u5927\u8BB0\u5F55\u6570\u4E3A',
		sample_msg_2 : '\u6761\uFF08\u5305\u542B\u5F55\u5165\u8FC7\u7A0B\u4E2D\u5220\u9664\u7684\u8BB0\u5F55\u6570\uFF09\u3002  \u6DF1\u84DD\u8272\u80CC\u666F\u8272\u7684\u884C\u4E3A\u6279\u91CF\u4FEE\u6539\u884C\uFF0C\u5F53\u60A8\u3010\u9009\u4E2D\u3011\u8BE5\u884C\u7684\u590D\u9009\u6846\u540E\uFF0C\u4FEE\u6539\u8BE5\u884C\u7684\u4E0B\u62C9\u6846\u7684\u503C\uFF0C\u6216\u4FEE\u6539\u8F93\u5165\u6846\u7684\u503C\u540E\u3010\u53CC\u51FB\u3011\u8F93\u5165\u6846\u65F6\uFF0C\u4E0B\u9762\u5217\u8868\u4E2D\u3010\u9009\u4E2D\u3011\u884C\u7684\u503C\u4F1A\u81EA\u52A8\u4FEE\u6539\u3002',
		success_msg : '\u64CD\u4F5C\u6210\u529F',
		modifyAllId : 'bacth_modify_all_checkbox_id',
		sequenceid : 'bacth_list_sequence_id',
		seqGen : function(index){
			return 'seq_'+(index)+'_';
		},
		letterGen : function(index){
			 var pre=' \u7B2C',add='\u884C  ';
			 var m = index%10;
			 var c = (index - m)/10;
			 var re = c>0?(c==1?(m>0?this.intToLetter(0)+this.intToLetter(m):this.intToLetter(0)):
			 (m>0?this.intToLetter(c)+this.intToLetter(0)+this.intToLetter(m):this.intToLetter(c)+this.intToLetter(0)))
			 :this.intToLetter(index);
			 return pre+re+add;
		},
		intToLetter : function(index){
		    var re = '';
			switch(index){
				case 0:re = '\u5341';break;
				case 1:re = '\u4E00';break;
				case 2:re = '\u4E8C';break;
				case 3:re = '\u4E09';break;
				case 4:re = '\u56DB';break;
				case 5:re = '\u4E94';break;
				case 6:re = '\u516D';break;
				case 7:re = '\u4E03';break;
				case 8:re = '\u516B';break;
				case 9:re = '\u4E5D';break;
				case 10:re = '\u5341';break;
				default:re = index;break;
			}
			return re;
		},
		replaceTableTag : function(pstr){
			var str = pstr;
			str = str.replaceAll("<table>","");
			str = str.replaceAll("<TABLE>","");
			str = str.replaceAll("</table>","");
			str = str.replaceAll("</TABLE>","");
			str = str.replaceAll("<tr>","");
			str = str.replaceAll("<TR>","");
			str = str.replaceAll("</tr>","");
			str = str.replaceAll("</TR>","");
			str = str.replaceAll("<tbody>","");
			str = str.replaceAll("<TBODY>","");
			str = str.replaceAll("</tbody>","");
			str = str.replaceAll("</TBODY>","");
			return str;
		},
		replaceTagWithSeq : function(plinstr,seq){
			var linestr = plinstr;
			//alert(seq+'--'+linestr);
				if(linestr.indexOf('id="')>0){
					linestr=linestr.replaceAll('id="','id="'+seq);
					linestr=linestr.replaceAll('name="','name="'+seq);
					linestr=linestr.replaceAll("exchange_needed_1_",this.letterGen(this.items));
					linestr=linestr.replaceAll("exchange_needed_",seq);
				}
				else{
					linestr=linestr.replaceAll("id=","id="+seq);
					linestr=linestr.replaceAll("name=","name="+seq);
					linestr=linestr.replaceAll("exchange_needed_1_",this.letterGen(this.items));
					linestr=linestr.replaceAll("exchange_needed_",seq);
				}
			//alert(seq+'--'+linestr);
			return linestr;
		},
		replaceTagWithSample : function(plinstr){
			var linestr = plinstr;
				if(linestr.indexOf('id="')>0){
					linestr=linestr.replaceAll('id="','id="'+this.sample_tag_id);
					linestr=linestr.replaceAll('name="','name="'+this.sample_tag_id);
					linestr=linestr.replaceAll("exchange_needed_1_",this.sample_tag_id);
					linestr=linestr.replaceAll("exchange_needed_",this.sample_tag_id);
				}
				else{
					linestr=linestr.replaceAll("id=","id="+this.sample_tag_id);
					linestr=linestr.replaceAll("name=","name="+this.sample_tag_id);
					linestr=linestr.replaceAll("exchange_needed_1_",this.sample_tag_id);
					linestr=linestr.replaceAll("exchange_needed_",this.sample_tag_id);
				}
			return linestr;
		},
		genHeader:function(){
			var sequence = '<th nowrap>&#24207;&#21495;</th>';			
			var checkbox = '<th nowrap><input type="checkbox" name="'+this.checkboxid+'" id="'+this.checkboxid+'" onclick ="Batch.setCheck(this.checked)"/></th>';
			var linestr=this.replaceTagWithSample($(this.headstrid).innerHTML);
				linestr = this.replaceTableTag(linestr);
			var tag_1 = '<tbody id="'+this.headstrid+'tbodyid"><tr>';
			var tag_2 = '</tr></tbody>';
				linestr=tag_1+sequence+checkbox+linestr+tag_2;
				//alert('genHeader is:'+linestr);
			return linestr;
		},
		genButton:function(){
			var linestr=$(this.buttonstrid).innerHTML;
				linestr = this.replaceTableTag(linestr);
			var tag_1 = '<tbody id="'+this.buttonstrid+'tbodyid"><tr>';
			var tag_2 = '</tr></tbody>';
				linestr=tag_1+linestr+tag_2;
				//alert('genButton is:'+linestr);
			return linestr;
		},
		genSampleLine:function(){
			var sequence = '<td><span id="'+this.item_sample_id+'">'+this.sample_tag_name+'</span></td>';			
			var checkbox = '<td><input type=\"checkbox\" name=\"'+this.modifyAllId+'\" id=\"'+this.modifyAllId+'\"/></td>';
			var linestr=this.replaceTagWithSample($(this.samplelinestr).innerHTML);
				//alert('genSampleLine is:'+linestr);
				linestr = this.replaceTableTag(linestr);
			var tag_1 = '<tbody id="'+this.item_sample_id+'tbodyid">';
			var tag_2 = '</tr></tbody>';
			var msg = '<tr><td colspan="100"><font color="black">'+this.sample_msg_1+this.totalAllowed+this.sample_msg_2+'</font></td></tr><tr bgcolor="blue">';
			linestr=tag_1+msg+sequence+checkbox+linestr+tag_2;
			//alert('genSampleLine is:'+linestr);
			return linestr;
		},
		genLine:function(type){
			var seq = this.seqGen(this.items);//'seq_'+(this.items++)+'_';				
			var sequence = '<td><span id="'+seq+this.sequenceid+'">'+this.items+'</span></td>';			
			var checkbox = '<td><input type=\"checkbox\" name=\"'+this.checkboxid+'\" id=\"'+this.checkboxid+'\" value=\"'+this.items+'\"/></td>';
			if(this.isNull(this.defaultlinestr)){
				this.defaultlinestr=document.getElementById(this.listlineid).innerHTML;
				this.defaultlinestr = this.replaceTableTag(this.defaultlinestr);
			}
			/*
			{
					var obj = document.getElementById(this.listlineid);
					alert(obj.tagName);
					var td = obj.getElementsByTagName("TD");
					var j=0;
					for(j=0;j<td.length;j++){
						
					}
					alert(j);
			}
			*/			
			//alert(document.getElementById(this.listlineid).innerHTML);
			var linestr='';//(this.isNull(type)||type==0)?this.replaceTagWithSeq(this.defaultlinestr,seq):this.replaceTagWithSample(this.defaultlinestr);
			switch(type){
				case 0:linestr = this.replaceTagWithSeq(this.defaultlinestr,seq);break;
				case 1:linestr = this.replaceTagWithSample(this.defaultlinestr);break;
				default:linestr = this.replaceTagWithSeq(this.defaultlinestr,seq);break;
			}
			//alert('genLine is:'+linestr);
			//alert(linestr);
			var tag_1 = '<tbody style="display:none" id="'+seq+'tbodyid"><tr>';
			var tag_2 = '</tr></tbody>';
			linestr=tag_1+sequence+checkbox+linestr+tag_2;
			//var line = $('list').innerHTML;
			//$('list').innerHTML = line+linestr;
			this.items++;
			//alert('genLine is:'+linestr);
			return linestr;
		},
		genHidden : function(){
			return '<tbody><tr><td colspan="100"><input type="hidden" id="batchUtilNowitems" name="batchUtilNowitems" value="'+this.nowitems+'"/></td></tr></tbody>';
		},
		addLineNew : function(){
			if(this.nowitems>this.totalAllowed){alert('\u8D85\u8FC7\u9875\u9762\u9884\u7F6E\u7F13\u5B58\u6700\u5927\u6570('+this.totalAllowed+')\u64CD\u4F5C\u5931\u8D25\uFF0C\u8BF7\u5148\u63D0\u4EA4\u4FDD\u5B58\u540E\u7EE7\u7EED\u6B64\u64CD\u4F5C');return;}
			var seq = this.seqGen(this.nowitems);
			//alert(1+'--'+seq);
			this.showLine(seq);
			//alert(2+'--'+seq);
			this.resetSeq();
			//alert(3+'--'+seq);
			this.nowitems++;
			this.effectiveitems++;
			$('batchUtilNowitems').value = this.nowitems;
		},
		addLines : function(linenum){
			var leftitems = this.totalAllowed - this.nowitems+1;
			if(this.isNull(linenum))return;
			if(leftitems<=0){alert('\u8D85\u8FC7\u9875\u9762\u9884\u7F6E\u7F13\u5B58\u6700\u5927\u6570('+this.totalAllowed+')\u64CD\u4F5C\u5931\u8D25\uFF0C\u8BF7\u5148\u63D0\u4EA4\u4FDD\u5B58\u540E\u7EE7\u7EED\u6B64\u64CD\u4F5C');return;}
			if(leftitems<linenum){
				for(var i=0;i<leftitems;i++){
					this.addLineNew();
				}
			}
			else for(var i=0;i<linenum;i++){
					this.addLineNew();
				}
		},
		init : function(paramListid,paramHeadstrid,paramListlineid,paramButtonstrid,paramTotalAllowed,paramInitnum,paramCheckitems_id){
			if(!this.isNull(paramTotalAllowed))this.totalAllowed=paramTotalAllowed;
			if(!this.isNull(paramInitnum))this.initnum=paramInitnum;
			if(!this.isNull(paramCheckitems_id))this.checkitems_id=paramCheckitems_id;//paramCheckitems_id.split(';');
			if(!this.isNull(paramListlineid))this.listlineid=paramListlineid;
			if(!this.isNull(paramHeadstrid))this.headstrid=paramHeadstrid;
			if(!this.isNull(paramButtonstrid))this.buttonstrid=paramButtonstrid;
			
			if(this.initnum>this.totalAllowed)this.initnum=this.totalAllowed;
			//{for(i=0;i<this.checkitems_id.length;i++)alert(this.checkitems_id[i]);}
		    var linestr='';
			for(var i=0;i<this.totalAllowed;i++){
				linestr+=this.genLine(0);
			}
			var headstr =this.genHeader();
			var samplestr = this.genSampleLine();
			var buttonstr = this.genButton();
			//alert(this.nowitems);
			var hiddenstr = this.genHidden();
			$(paramListid).innerHTML = '<table>'+buttonstr+samplestr+headstr+linestr+hiddenstr+'</table>';
			
			for(var i=1;i<this.initnum+1;i++){
				var seq = this.seqGen(i);
				//alert(this.initnum+'--'+this.totalAllowed+'--'+seq);
				this.addLineNew();
			}
		},
		delLineNew : function(linenum){
			if(this.nowitems < 0)return;
			if(undefined==linenum || 'undefined'==linenum || null==linenum)linenum = this.nowitems;//linenum = this.nowitems--;
			var seq = this.seqGen(linenum);
			//alert(document.getElementById(seq+'deleteflag'));
			if($(seq+'deleteflag').value == '1'){
				this.delLineNew(--linenum);
			}
			this.hideLine(seq);
			this.effectiveitems--;
		},
		hideLine: function(seq){
			//alert(seq);
			$(seq+'tbodyid').style.display = 'none';
			$(seq+'deleteflag').value = '1';
			for(var j=0;j<this.checkitems_id.length;j++){
				if(this.isNull($(seq+this.checkitems_id[j])) || this.isNull($(seq+this.checkitems_id[j]).getAttribute('require')))continue;
				$(seq+this.checkitems_id[j]).setAttribute('require','false');
			};
		},
		showLine: function(seq){
			//alert($(seq+'tbodyid').style.display+'--'+$(seq+'tbodyid').innerHTML);
			$(seq+'tbodyid').style.display = '';
			$(seq+'deleteflag').value = '0';
			for(var j=0;j<this.checkitems_id.length;j++){
				//alert($(seq+this.checkitems_id[j]).getAttribute("require"));
				if(this.isNull($(seq+this.checkitems_id[j])) || this.isNull($(seq+this.checkitems_id[j]).getAttribute('require')))continue;
				$(seq+this.checkitems_id[j]).setAttribute('require','true');
			};
		},
		delSelectedLines : function(){
			var selected = this.getSelectedLines();
			//alert(selected);
			if(!this.isNull(selected)){
				if(!confirm('\u64CD\u4F5C\u4E0D\u53EF\u6062\u590D\uFF0C\u786E\u8BA4\u5220\u9664\u9009\u4E2D\u7684\u5168\u90E8\u884C?'))return;
			}
			
			var arr = selected.split(';');
			//alert(arr.length);
			for(var i=0;i<arr.length;i++){
				this.delLineNew(arr[i]);
			};
			this.resetSeq();
			this.setCheck(false);
		},
		resetSeq : function(){
			var newseq = 1;
			for(var j=1;j<=this.nowitems;j++){
				var seq = this.seqGen(j);
				//alert($(seq+this.sequenceid).innerHTML+'--'+$(seq+'deleteflag').value);
				if($(seq+'deleteflag').value=='0')$(seq+this.sequenceid).innerHTML = newseq++;
					for(var k=0;k<this.checkitems_id.length;k++){	
						if(this.isNull($(seq+this.checkitems_id[k])) || this.isNull($(seq+this.checkitems_id[k]).getAttribute('linemsg')))continue;
						$(seq+this.checkitems_id[k]).setAttribute('linemsg',this.letterGen(newseq-1));	
						//$(seq+this.checkitems_id[k]).linemsg=this.letterGen(newseq-1);
					};
			}				
		},
		setCheck : function(flag,Obj)
			{
			  var sObj = this.isNull(Obj)?'':Obj;
			  var tempstr,len,len1; 
				tmpstr=eval("document.form1"+sObj+"."+this.checkboxid);
				if(isnull(tmpstr) ){return;}
				//alert(eval("document.form1"+sObj+"."+this.checkboxid).length+'--'+flag);
				if(eval("document.form1"+sObj+"."+this.checkboxid).length)
				{
			   		for (var counter = 0; counter <eval("document.form1"+sObj+"."+this.checkboxid).length; counter++){
			   			//alert(("document.form1"+sObj+"."+this.seqGen(counter)+"deleteflag"+".value"));
			   			if(counter==0)eval("document.form1"+sObj+"."+this.checkboxid)[counter].checked=flag;
			   			else if (flag && !this.isNull(eval("document.form1"+sObj+"."+this.seqGen(counter)+'deleteflag')) && eval("document.form1"+sObj+"."+this.seqGen(counter)+'deleteflag'+'.value')=='0')
			   				 eval("document.form1"+sObj+"."+this.checkboxid)[counter].checked=true;
			   			else eval("document.form1"+sObj+"."+this.checkboxid)[counter].checked=false;
					}
				}
				else{eval("document.form1"+sObj+"."+this.checkboxid).checked=flag;}
			},
		getInitId : function(id,type){
			if(this.isNull(id))return '';
			var seq_ ='';
				switch(type){
					case 0:
						seq_ = id.substring(id.indexOf('seq_')+4,id.length);
						seq_ = seq_.substring(seq_.indexOf('_')+1,seq_.length);
						break;
					case 1:
						seq_ = id.substring(id.indexOf(this.sample_tag_id)+this.sample_tag_id.length,id.length);
						break;
					default:
						seq_ = id.substring(id.indexOf('seq_')+4,id.length);
						seq_ = seq_.substring(seq_.indexOf('_')+1,seq_.length);
						break;
				}
			 
			return seq_;
		},
		modifyAll : function(obj){
		    var id=this.getInitId(obj.id);
		    var sseq = obj.id.replaceAll(id,'');
		    //alert('sseq is:'+sseq+',id is:'+id);
		    var tobj,tseq;
			if($(this.modifyAllId).checked){
				var selected = this.getSelectedLines();
				if(this.isNull(selected)){return;};
				var arr = selected.split(';');
				alert(this.success_msg);
				for(var i=0;i<arr.length;i++){
					tseq = this.seqGen(arr[i]);
					tobj = $(tseq+id);
					tobj.value = obj.value;
					if(!this.isNull(tobj.extfunc)){
						var sid = sseq+tobj.extfuncpara;
						var tid = tseq+tobj.extfuncpara;
						var s = $(sid).innerHTML;
						//alert(sid+'--'+tid+'--'+s.replaceAll(sseq,tseq));
						if(tid!=sid){
							$(tid).innerHTML = s.replaceAll(sseq,tseq);
						}
					}
				};
			}
		},		
		modifyAllNew : function(obj,pextfuncpara){
		    var id=this.getInitId(obj.id,1);
		    var sseq = this.sample_tag_id;
		    var tobj,tseq;
			if($(this.modifyAllId).checked){
				var selected = this.getSelectedLines();
				if(this.isNull(selected)){return;};
				var arr = selected.split(';');
				//alert(obj.id+'--'+selected+'--'+sseq+'-/-'+id+'--'+'operation success!');
				alert(this.success_msg);
				for(var i=0;i<arr.length;i++){
					tseq = this.seqGen(arr[i]);
					tobj = $(tseq+id);
					//alert((tseq+id)+'--'+$(tseq+id));
					tobj.value = obj.value;
					if(!this.isNull(pextfuncpara)){
						var extfuncpara = pextfuncpara.split(';');
						//alert(tobj.extfuncpara);
						for(var j=0;j<extfuncpara.length;j++){
							var sid = sseq+extfuncpara[j];
							var tid = tseq+extfuncpara[j];
							var s = $(sid).innerHTML;
							//alert(sid+'--'+tid+'--'+s.replaceAll(sseq,tseq));
							$(tid).innerHTML = s.replaceAll(sseq,tseq);
						}
					}
				};
			}
		},		
		getSelectedLines: function(){
			var keyElement = window.document.getElementsByName(this.checkboxid);		
			if(keyElement==null){
				return null;
			}		
			var selected = '';					
			for(var i=1;i<keyElement.length;i++)
			{
				if(keyElement[i].checked){selected += keyElement[i].value+";";}
			}
			//alert(selected);
			if(!this.isNull(selected))selected=selected.substring(0,selected.length-1);
			return selected;	
		},
		delLine : function (linenum){
			if(nowitems < 0)return;
			if(undefined==linenum || 'undefined'==linenum || null==linenum)linenum = --nowitems;
			var line = $('list').innerHTML;
			var seq = this.seqGen(linenum);
			var tag_1 = '<tag'+seq+'>';
			var tag_2 = '</tag'+seq+'>';
			/*
			alert('tag_1 is:'+tag_1+'--index of tag_1 is:'
			+line.toLowerCase().indexOf(tag_1)+'--'
			+line.substring(line.toLowerCase().indexOf(tag_1),line.toLowerCase().indexOf(tag_1)+tag_1.length)+'--index of tag_2 is:'
			+line.toLowerCase().indexOf(tag_2)+'--'
			+line.substring(line.toLowerCase().indexOf(tag_2),line.toLowerCase().indexOf(tag_2)+tag_2.length)
			+'--\n'+line);
			*/
			var str1=line.substring(0,line.toLowerCase().indexOf(tag_1));
			var str2=line.substring(line.toLowerCase().indexOf(tag_2)+tag_2.length,line.length);
			//alert(str1);
			//alert(tag_1+'--'+line.toLowerCase().indexOf(tag_1)+'--'+line.substring(line.toLowerCase().indexOf(tag_1),tag_1.length)+'--\n'+line);
			line = str1+str2;
			$('listlines').innerHTML = line;
		},
		save : function(formid){
			if(!Validator.Validate($(formid))){return false;}	
			eval(formid+'.submit()');
		},
		isNull : function(str){if(str==null||str==''||str=='undefine')return true;
		 	return false;
		}
	}
	
	String.prototype.replaceAll = function(reallyDo, replaceWith, ignoreCase) {   
	    if (!RegExp.prototype.isPrototypeOf(reallyDo)) {   
	        return this.replace(new RegExp(reallyDo, (ignoreCase ? "gi": "g")), replaceWith);   
	    } else {   
	        return this.replace(reallyDo, replaceWith);   
	    }   
	}   
