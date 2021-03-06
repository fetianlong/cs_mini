/*************************************************
	Validator v1.0
	cody by 我佛山人
	wfsr@cunite.com
	http://www.cunite.com
***************************************************
	Validator v1.1
	cody by Todd Lee
	lijiantao@eyou.com
	http://www.todd-lee.com
	log:
	1.增加了用户名(Username)，无符号字符串(Nosign)的验证方式。
	2.改进错误提示方式3中的提示方法。会先判断是否已存在信息提示框，如果存在，则重写其中内容。
	3.增加当输入框失去焦点时进行单项验证功能。
*************************************************/
 Validator = {
	Require : /.+/,
	Username2 : /^([a-zA-Z0-9]|[_]){4,15}$/,
	Username : /^[a-zA-Z]{1}([a-zA-Z0-9]|[_]){3,15}$/,
	Nosign : /^[^\s]{1}[^-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*$/,
	Domain : /^([a-zA-Z0-9]|[-]){4,16}$/,
	V_code : /^[a-zA-Z0-9]{4}$/,
	Email : /^\w+([-+.]\w+)*@\w+([-.]\\w+)*\.\w+([-.]\w+)*$/,
	Phone : /^((\(\d{3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}$/,
	Mobile : /^((\(\d{3}\))|(\d{3}\-))?1[3,5,8]\d{9}$/,
	Url : /^http:\/\/[A-Za-z0-9]+\.[A-Za-z0-9]+[\/=\?%\-&_~`@[\]\':+!]*([^<>\"\"])*$/,
	IdCard : /^\d{15,18}(\d{2}[A-Za-z0-9])?$/,
	Currency : /^\d+(\.\d+)?$/,
	Number : /^(\d{1,3}\.){3}\d{1,3}$/,
	Ip: /^(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])(\.(\d{1,2}|1\d\d|2[0-4]\d|25[0-5])){3}$/,
	IdVal: /^[a-zA-Z0-9_]+$/,
	Zip : /^[1-9]\d{5}$/,
	QQ : /^[1-9]\d{4,8}$/,
	Integer : /^[-\+]?\d+$/,
	Double : /^[-\+]?\d+(\.\d+)?$/,
	Decmail2 : /^[0-9]+([.]{1}[0-9]{1,2})?$/,
	Decmail4 : /^[0-9]+([.]{1}[0-9]{1,4})?$/,
	English : /^[A-Za-z]+$/,
	Chinese :  /^[\u0391-\uFFE5]+$/,
	NoChinese :  /.*[\u0391-\uFFE5]+.*/,
	UnSafe : /^(([A-Z]*|[a-z]*|\d*|[-_\~!@#\$%\^&\*\.\(\)\[\]\{\}<>\?\\\/\'\"]*)|.{0,5})$|\s/,
	ipchk : /^(\d{1,3}\.){3}\d{1,3}$/,
	IsSafe : function(str){return !this.UnSafe.test(str);},
	SafeString : "this.IsSafe(value)",
	Length : "this.length(value,getAttribute('max'))",
	Limit : "this.limit(value.length,getAttribute('min'),  getAttribute('max'))",
	LimitB : "this.limit(this.LenB(value.trim()), getAttribute('min'), getAttribute('max'))",
	Date : "this.IsDate(value, getAttribute('min'), getAttribute('format'))",
	Repeat : "value == document.getElementsByName(getAttribute('to'))[0].value",
	Range : "getAttribute('min') <= value && value <= getAttribute('max')",
	Compare : "this.compare(value,getAttribute('operator'),getAttribute('to'))",
	Custom : "this.Exec(value, getAttribute('regexp'))",
	Group : "this.MustChecked(getAttribute('name'), getAttribute('min'), getAttribute('max'))",
	ErrorItem : [document.forms[0]],
	OkItem : [document.forms[0]],
	ErrorMessage : ["以下原因导致提交失败：\t\t\t\t\n"],
	OkMessage : ["以下内容已通过验证：\t\t\t\t\n"],
	Validate : function(theForm, mode){
		var obj = theForm || event.srcElement;
		var count = obj.elements.length; //对象元素的长度
		this.ErrorMessage.length = 1;
		this.ErrorItem.length = 1;
		this.ErrorItem[0] = obj; //ErrorItem数组唯一的元素，obj为传进的参数1，参数1为控件对象或事件的源素。
		this.OkMessage.length = 1;
		this.OkItem.length = 1;
		this.OkItem[0] = obj; //okitem数组唯一的元素也是obj。
		for(var i=0;i<count;i++){
			with(obj.elements[i]){
				var _dataType = getAttribute("dataType"); //控件的dataType属性
				if(typeof(_dataType) == "object" || typeof(this[_dataType]) == "undefined")  continue;
				this.ClearState(obj.elements[i]);
				if(getAttribute("require") == "false" && value == "") continue;
				switch(_dataType){
					case "Date" :
					case "Repeat" :
					case "Range" :
					case "Compare" :
					case "Custom" :
					case "Group" : 
					case "Length" :
					case "Limit" :
					case "LimitB" :
					case "SafeString" :
						if(!eval(this[_dataType])) {
							this.AddError(i, getAttribute("msg"));
						}
						else if(getAttribute("okmsg")) {
							this.AddOk(i, getAttribute("okmsg"));
						}
						break;
					case "NoChinese":
							if(this[_dataType].test(value.trim())){
								this.AddError(i, getAttribute("msg"));
							}
							else if(getAttribute("okmsg")) {
								this.AddOk(i, getAttribute("okmsg"));
							}
							break;
					default :
						if(!this[_dataType].test(value.trim())) {
							this.AddError(i, getAttribute("msg"));
						}
						else if(getAttribute("okmsg")) {
							this.AddOk(i, getAttribute("okmsg"));
						}
						break;
				}
			}
		}
		this.ShowOkMsg(mode);
		return(this.ShowErrorMsg(mode));
	},
	ValidateOne : function(theElement, mode){
		var obj = theElement || event.srcElement;
		this.ErrorMessage.length = 1;
		this.ErrorItem.length = 1;
		this.ErrorItem[0] = obj;
		this.OkMessage.length = 1;
		this.OkItem.length = 1;
		this.OkItem[0] = obj;
		with(obj){
			var _dataType = getAttribute("dataType");
			if(typeof(_dataType) == "object" || typeof(this[_dataType]) == "undefined"){
				var a;	//do nothing
			}
			else{
				this.ClearState(obj);
				if(getAttribute("require") == "false" && value == ""){
					var b;	//do nothing;
				}
				else{
					switch(_dataType){
						case "Date" :
						case "Repeat" :
						case "Range" :
						case "Compare" :
						case "Custom" :
						case "Group" : 
						case "Length" :
						case "Limit" :
						case "LimitB" :
						case "SafeString" :
							if(!eval(this[_dataType]))	{
								this.AddErrorOne(obj, getAttribute("msg"));
							}
							else if(getAttribute("okmsg")) {
								this.AddOkOne(obj, getAttribute("okmsg"));
							}
							break;
						case "NoChinese":
							if(this[_dataType].test(value.trim())){
								this.AddErrorOne(obj, getAttribute("msg"));
							}
							else if(getAttribute("okmsg")) {
								this.AddOkOne(obj, getAttribute("okmsg"));
							}
							break;
						default :
							if(!this[_dataType].test(value.trim())){
								this.AddErrorOne(obj, getAttribute("msg"));
							}
							else if(getAttribute("okmsg")) {
								this.AddOkOne(obj, getAttribute("okmsg"));
							}
							break;
					}
				}
			}
		}
		this.ShowOkMsg(mode);
		return( this.ShowErrorMsg(mode) );
	},
	GetNoticeBox : function(obj) {
		try{
			if(obj.parentNode.childNodes[obj.parentNode.childNodes.length-1]){
				var lastNode = obj.parentNode.childNodes[obj.parentNode.childNodes.length-1];
			}
			if( lastNode && lastNode.tagName == "SPAN"){
				var span = lastNode;
			}
			else if( obj.nextSibling && obj.nextSibling.tagName == "SPAN" ){
				var span = obj.nextSibling;
			}
			else{
				var span = document.createElement("SPAN");
				obj.parentNode.appendChild(span);
			}
			return span;
		}
		catch(e) {
			return null;
		}
	},
	ShowErrorMsg : function(mode){
		if(this.ErrorMessage.length > 1){
			mode = mode || 1;
			var errCount = this.ErrorItem.length;
			switch(mode){
			case 2 :
				for(var i=1;i<errCount;i++)
					this.ErrorItem[i].style.color = "red";
					//this.ErrorItem[i].className = "text-error";
			case 1 :
				alert(this.ErrorMessage.join("\n"));
				this.ErrorItem[1].focus();
				break;
			case 3 :
				for(var i=1;i<errCount;i++){
					try{
						var span = this.GetNoticeBox(this.ErrorItem[i]);
						span.innerHTML = this.ErrorMessage[i].replace(/\d+:/,"");
						span.className = "noticeError";
						span.id = "__ErrorMessagePanel";
						span.style.color = "red";
						//this.ErrorItem[1].focus();
					}
					catch(e){alert(e.description);}
				}
				break;
			default :
				alert(this.ErrorMessage.join("\n"));
				break;
			}
			return false;
		}
		return true;
	},
	ShowOkMsg : function(mode){
		if(this.OkMessage.length > 1){
			mode = mode || 1;
			var okCount = this.OkItem.length;
			switch(mode){
			case 2 :
				for(var i=1;i<okCount;i++)
					this.OkItem[i].style.color = "green";
			case 1 :
				//alert(this.OkMessage.join("\n"));
				//this.OkItem[1].focus();
				break;
			case 3 :
				for(var i=1;i<okCount;i++){
					try{
						var span = this.GetNoticeBox(this.OkItem[i]);
						span.innerHTML = this.OkMessage[i].replace(/\d+:/,"");
						span.className = "noticeInfo";
						span.id = "__ErrorMessagePanel";
						span.style.color = "green";
					}
					catch(e){alert(e.description);}
				}
				break;
			default :
				//alert(this.OkMessage.join("\n"));
				break;
			}
			return true;
		}
		return true;
	},
	limit : function(len,min, max){
		min = min || 0;
		max = max || Number.MAX_VALUE;
		return min <= len && len <= max;
	},
	length : function(val,max){
		max = max || Number.MAX_VALUE;
		var len=0;
		if(val.trim().length>0){
			for(var i=0;i<val.trim().length;i++){
				if(this["Chinese"].test(val.trim().substring(i,i+1))){
					len+=2;
				}else{
					len+=1;
				}
			}
			return len <= max;
		}else{
			return false;
		}
	},
	LenB : function(str){
		return str.replace(/[^\x00-\xff]/g,"**").length;
	},
	ClearState : function(elem){
		with(elem){
			if(style.color == "red")
				style.color = "";
				className = "text";
			var lastNode = parentNode.childNodes[parentNode.childNodes.length-1];
			if(lastNode.id == "__ErrorMessagePanel"){
				//parentNode.removeChild(lastNode);
				lastNode.style.color = "";
				lastNode.className = "noticeInfo";
			}
			else if( nextSibling && nextSibling.id == "__ErrorMessagePanel" ){
				nextSibling.style.color = "";
				nextSibling.className = "noticeInfo";
			}
		}
	},
	AddError : function(index, str){
		this.ErrorItem[this.ErrorItem.length] = this.ErrorItem[0].elements[index];
		this.ErrorMessage[this.ErrorMessage.length] = this.ErrorMessage.length + ":" + str;
	},
	AddErrorOne : function(obj, str){
		this.ErrorItem[this.ErrorItem.length] = obj;
		this.ErrorMessage[this.ErrorMessage.length] = this.ErrorMessage.length + ":" + str;
	},
	AddOk : function(index, str){
		this.OkItem[this.OkItem.length] = this.OkItem[0].elements[index];
		this.OkMessage[this.OkMessage.length] = this.OkMessage.length + ":" + str;
		//alert(this.OkMessage);
	},
	AddOkOne : function(obj, str){
		this.OkItem[this.OkItem.length] = obj;
		this.OkMessage[this.OkMessage.length] = this.OkMessage.length + ":" + str;
	},
	Exec : function(op, reg){
		return new RegExp(reg,"g").test(op);
	},
	compare : function(op1,operator,op2){
		switch (operator) {
			case "NotEqual":
				return (op1 != op2);
			case "GreaterThan":
				return (op1 > op2);
			case "GreaterThanEqual":
				return (op1 >= op2);
			case "LessThan":
				return (op1 < op2);
			case "LessThanEqual":
				return (op1 <= op2);
			default:
				return (op1 == op2);            
		}
	},
	MustChecked : function(name, min, max){
		var groups = document.getElementsByName(name);
		var hasChecked = 0;
		min = min || 1;
		max = max || groups.length;
		for(var i=groups.length-1;i>=0;i--)
			if(groups[i].checked) hasChecked++;
		return min <= hasChecked && hasChecked <= max;
	},
	IsDate : function(op, formatString){
		formatString = formatString || "ymd";
		var m, year, month, day;
		switch(formatString){
			case "ymd" :
				m = op.match(new RegExp("^\\s*((\\d{4})|(\\d{2}))([-./])(\\d{1,2})\\4(\\d{1,2})\\s*$"));
				if(m == null ) return false;
				day = m[6];
				month = m[5]--;
				year =  (m[2].length == 4) ? m[2] : GetFullYear(parseInt(m[3], 10));
				break;
			case "dmy" :
				m = op.match(new RegExp("^\\s*(\\d{1,2})([-./])(\\d{1,2})\\2((\\d{4})|(\\d{2}))\\s*$"));
				if(m == null ) return false;
				day = m[1];
				month = m[3]--;
				year = (m[5].length == 4) ? m[5] : GetFullYear(parseInt(m[6], 10));
				break;
			default :
				break;
		}
		//modify by yxh 2009-12-08
		month = month==12?0:month;
		
		var date = new Date(year, month, day);
		//alert(date.getFullYear()+'-'+date.getMonth()+'-'+date.getDate());
        return (typeof(date) == "object" && year == date.getFullYear() && month == date.getMonth() && day == date.getDate());
		function GetFullYear(y){return ((y<30 ? "20" : "19") + y)|0;}
	}
 }
String.prototype.trim = function () {
	return this.replace(/(^\s*)|(\s*$)/g, "");
}
 