function val_check_Plate(filedName){
	$.validator.addMethod(filedName,function(value,element,params){
		if(value.length<1){
			return true;
		}
		if(params){
			if(/^[\u4e00-\u9fa5]{1}[A-Z]{1}[A-Z_0-9]{5}$/i.test(value)){
					return true;
			}else{
				return false;
			}
		}
	},"不符合车牌号规则，示例：京A1234X");
}
function val_check_MustNumOrStr(filedName){
	$.validator.addMethod(filedName,function(value,element,params){
		if(value.length<1){
			return true;
		}
		if(params){
			if(/^[a-zA-Z0-9]*$/.test(value)){
				return true;
			}else{
				return false;
			}
		}
	},"必须为字母或数字");
}
function val_check_MustCn(filedName){
	//验证所有字符必须为中文
	$.validator.addMethod(filedName,function(value,element,params){
		if(value.length<1){
			return true;
		}
		if(params){
			if(/^[\u4e00-\u9fa5]+$/i.test(value)){
				return true;
			}else{
				return false;
			}
		}
	},"必须为汉字");
}

/**
 * 特殊字符校验
 * @param filedNames
 * @returns Boolean
 */
function val_check_SpecialChar(filedNames){
	if(filedNames == null || filedNames == ""){
		return true;
	}
	var fileds = filedNames.split(',');
	$(fileds).each(function(index,filedName){
		//验证是否有特殊字符
		$.validator.addMethod(filedName,function(value,element,params){
			if(params){
				if(value.length<1){
					return true;
				}else{
					var pattern = new RegExp("[`~!@#$^&*()=|{}':;',\\[\\].<>/?~！@#￥……&*（）;—|{}【】‘；：”“'。，、？]");
					for (var i = 0; i < value.length; i++) {
						var vrs = value.substr(i,1);
						if(pattern.test(vrs)){
							return false;
						}
					}
					return true;
				}
			}
		},"不能有特殊字符！");
	});
	
}

/**
 * 验证电话号码 数字 - 和空格
 * @param filedName
 */
function val_check_Telephone(filedName){
	$.validator.addMethod(filedName,function(value,element,params){
		if(params){
			if(value.length<1){
				return true;
			}
			if(/^0\d{2,3}-?\d{4,8}$/.test(value)){
					return true;
			}else if( /^1\d{10}$/.test(value)){
				return true;
			}else{
				return false;
			}
		}
	},"请输入正确的电话号码");
}
/**
 *  判断double类型的数字大于等于0
 * @param filedName
 */
function val_check_Double(filedName){
	$.validator.addMethod(filedName,function(value,element,params){
		if(params){
			if(value.length<1){
				return true;
			}
			if(value*1<0){
				return false;
			}else if(value*1>=0){
				return true;
			}else{
				return false;
			}
			return false;
		}
	},"输入的数字必须大于等于0");
}
/**
 * 判断一个数是否在0到10之间
 * @param filedName
 */
function val_check_num(filedName){
	$.validator.addMethod(filedName,function(value,element,params){
		if(params){
			if(value.length<1){
				return true;
			}
			if(value*1>=0&&value*1<=1){
				return true;
			}
			return false;
		}
	},"输入的数字必须在0到1之间");
}

/**
 * 优惠起止时间成对出现
 * @param filedName
 */
function val_check_beginEnd(filedName){
	$.validator.addMethod(filedName,function(value,element,params){
		if(params!=""){
			if(value!="" && value!=null){
				var s=$("#"+params).val();
				if(s==value){
					return false;
				}
				if(s!=""){
					return true;
				}else{
					return false;
				}
			}
			return true;
		}
	},"");
}
