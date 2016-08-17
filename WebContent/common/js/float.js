/**
 * 弹出层类
 */
var apachepath="C:/workspace/apache_doc/";
function popup_param_(filename,width,height,pimagepath,phelp){
	//alert(filename);
	
    var askpop  = new sBox(1,filename);
    askpop.s_imagepath = pimagepath;
    askpop.s_helpbutton =(null==phelp || undefined==phelp)?0:phelp;
    askpop.s_width    = (null==width?780:width);
    askpop.s_height    = (null==height?540:height);
    askpop.s_title = " <span class='param_pop_tit'></span>";
    askpop.title_color        = '#000000';
    askpop.s_boarder_outside  = '#B5CCE8';
    askpop.s_bgcolor_outside  = '#F1F7FC';
    askpop.inside_border_width= 0;
    askpop.inside_border_padding = 0;
    askpop.init();
}

function _get(id) {
	if( typeof id == 'string' ){
		return document.getElementById(id);
	}else{
		return false;
	}
}	
function sBox(type,src){
		//初始化参数
		this.s_imagepath	= '';	           //图片路径
		this.s_helpbutton	= 1;	           //现实帮助按钮
		this.s_title		= "小提示";	       //窗口标题
		this.s_title		= "小提示";	       //窗口标题
		this.s_content		= "";	           //窗口内容
		this.s_width		= 450;		       //窗口宽度
		this.s_height		= 100;		       //窗口高度
		if( type=="" ){
			this.s_type		= 0;
		}else{
			this.s_type			= type;		      //0:消息提示窗口
											      //1：带IFrame的窗口
										          //2：confirm窗口
	 										      //3：prompt窗口
		}

    	this.s_bg_touming	= 0.6;		                  //背景透明度0~1
    	this.s_bg_color		= "#666666"	;                 //蒙板颜色
    	this.s_boarder_outside	= "#000000";	          //外框颜色
    	this.s_bgcolor_outside  = "#000000";              //外框背景色
    	this.inside_border_padding = 0;                   //内外框中间的宽度
    	this.inside_border_width= 0;                      //内框宽度
    	this.s_iframe_src	= src;		                  //iframe窗口链接
    	this.s_top			= 5;		                  //弹出层距顶部高度
		this.set_title	  	= 1;		                  //是否有标题
		this.callback       = '';                         //回调函数名
        this.title_color    = '#FFFFFF';                  //标题颜色
		//初始化
		this.init  			= function (){
			var base_index	= 10002;
			var title_height= 26;
			var bg_height	= document.body.scrollHeight?document.body.scrollHeight:document.documentElement.clientHeight;	//蒙布的高度
				bg_height   = bg_height<250?250:bg_height;
				bg_height   = bg_height>250?250:bg_height;
			var bg_width	= document.body.scrollWidth?document.body.scrollWidth:document.documentElement.clientWidth;	//蒙布的宽度
			this.s_height		= this.s_height-title_height;
	       	this.s_top			= (document.body.scrollTop?document.body.scrollTop:document.documentElement.scrollTop)+this.s_top;   //弹出层距顶部高度
			//用以提高弹出层显示优先级的iframe
			var high_iframe= '<iframe src="" style="position:absolute; visibility:inherit; top:0px; left:0px; width:'+bg_height+'px; height:'+bg_width+'px; z-index:-1;filter='+"'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)'"+';" frameborder="1"></iframe>';
			//关闭按钮
			var close_button = '<img src="'+this.s_imagepath+'/close.gif" style="float:right;cursor:pointer" onclick="" id="close_img" title="关闭" />';
			//帮助按钮
			var help_button = '<img src="'+this.s_imagepath+'/help.gif" style="float:right;cursor:pointer" onclick="" id="help_img" title="帮助" />';
			//蒙版
			this.bg_div		= '<div id="bg_div" style="position:absolute;top:0px;left:0px;width:100%;height:'+bg_height*2+'px;z-index:'+base_index+';background-color:'+this.s_bg_color+';filter:alpha(opacity='+this.s_bg_touming*100+');opacity:'+this.s_bg_touming+';display:none;"></div>';
			//弹出窗口浮动层
			this.float_div	= '<div id="float_div" style="top:'+this.s_top+'px;position:absolute;left:'+(bg_width-this.s_width)/2+'px;width:'+this.width+'px;z-index:'+(base_index+2)+';background:#E3F4FC; auto;float:none;border:1px solid '+this.s_boarder_outside+'" unselectable="on">';
			//外框
			this.float_div += '<table width="'+this.s_width+'"  cellpadding="0" cellspacing="0" bgcolor="'+this.s_bgcolor_outside+'" unselectable="on">';
			//标题区
			if( this.set_title==1){
				if(this.s_helpbutton==0)this.float_div += '<tr><td  unselectable="on"><table width="100%" border="0" cellspacing="0" cellpadding="0" unselectable="on"><tr style="line-height:'+title_height+'px;"><td width="2">&nbsp;</td><td style="color: '+this.title_color+';font-weight: bold;font-size: 14px" unselectable="on" ><span class="STYLE2" unselectable="on" >&nbsp;'+this.s_title+'</span></td><td width="10"><span style="clear:both;padding:8px 0">'+close_button+'</span></td><td width="2">&nbsp;</td></tr></table></td></tr>';
				else this.float_div += '<tr><td  unselectable="on"><table width="100%" border="0" cellspacing="0" cellpadding="0" unselectable="on"><tr style="line-height:'+title_height+'px;"><td width="2">&nbsp;</td><td style="color: '+this.title_color+';font-weight: bold;font-size: 14px" unselectable="on" ><span class="STYLE2" unselectable="on" >&nbsp;'+this.s_title+'</span></td><td width="10"><span style="clear:both;padding:8px 0">'+close_button+'</td><td width="10">'+help_button+'<span style="clear:both;padding:8px 0"></span></td><td width="2">&nbsp;</td></tr></table></td></tr>';
			}
			//内容区
			
			switch(this.s_type){
				case 0://消息提示窗口
					break;
				case 1://1：带IFrame的窗口
					//alert(this.s_iframe_src);
					if( this.s_iframe_src!="" ){
						var iframe = '<iframe id="sBox_iframe" name="sBox_iframe"  width="'+this.s_width+'" height="'+this.s_height+'"  frameborder="0" src="'+this.s_iframe_src+'"   scrolling="no" ></iframe>';
						this.s_content	+= iframe;
					}else{
						this.s_content = "请初始化src1";
					}

					break;
				case 2://2：confirm窗口
				    var confirm_str = '';
				    confirm_str    += '<table width="100%" border="0" cellspacing="0" cellpadding="0">';
				    confirm_str    += '<tr>';
				    confirm_str    += '<td colspan="2"><div align="center" style="font-weight:bold;color:#CC3300;font-size:14px">'+this.s_content+'</div></td>';
				    confirm_str    += '</tr>';
				    confirm_str    += '<tr><td height="10"></td><td></td></tr>';
				    confirm_str    += '<tr>';
				    confirm_str    += '<td><div align="right" style="margin-right:20px;">';
				    confirm_str    += '<input type="button" name="confirm_btn" id="confirm_btn" value=" 确定 "  />';
				    confirm_str    += '</div></td>';
				    confirm_str    += '<td><div align="left" style="margin-left:20px;">';
				    confirm_str    += '<input type="button" name="cancel_btn" id="cancel_btn" value=" 取消 " />';
				    confirm_str    += '</div></td>';
				    confirm_str    += '</tr>';
				    confirm_str    += '<tr><td height="10"></td><td></td></tr>';
				    confirm_str    += '</table>';
				    this.s_content	= confirm_str;
					break;
				case 3://3：prompt窗口
					break;
				default ://

			}
			this.float_div += '<tr><td><div style="background-color:'+this.s_bgcolor_outside+'; padding:'+this.inside_border_padding+'px;"><div  style="border:'+this.inside_border_width+'px solid '+this.s_boarder_outside+';background-color:#FFFFFF">'+this.s_content+'</div></div></td></tr></table></div><div id="dialogBoxShadow" style="display:none;z-index:'+(base_index-2)+';"></div>';
			this.initBody();								//初始化底部span标签
			this.show();
			//添加焦点
			_get('float_div').focus();
		}
		
		
	//加载底部基层
	this.initBody 		= function (){	
		_get('body_span')?_get('body_span').parentNode.removeChild(_get('body_span')):"";
		var oDiv=document.createElement('span');
		oDiv.id='body_span';
		document.body.appendChild(oDiv);
	}
	//显示弹出层
	this.show			= function(){
			document.getElementById('body_span').innerHTML = this.bg_div+this.float_div;
			document.getElementById('bg_div').style.display= "";
			if( this.set_title==1 ){
				//为关闭按钮绑定事件
				if( _get("close_img").addEventListener ){//ff下
					_get("close_img").addEventListener("click", this.close, false);
				}else{//IE下
					_get("close_img").attachEvent("onclick", this.close);
				}
				//为帮助按钮绑定事件
				if( _get("help_img").addEventListener ){//ff下
					_get("help_img").addEventListener("click", this.help, false);
				}else{//IE下
					_get("help_img").attachEvent("onclick", this.help);
				}
			    if( this.s_type==2 ){
    				//为取消按钮绑定事件
    				if( _get("cancel_btn").addEventListener ){//ff下
    					_get("cancel_btn").addEventListener("click", this.close, false);
    				}else{//IE下
    					_get("cancel_btn").attachEvent("onclick", this.close);
    				}
    				//为确定按钮绑定事件
    				if( _get("confirm_btn").addEventListener ){//ff下
    					_get("confirm_btn").addEventListener("click", this.callback, false);
    				}else{//IE下
    					_get("confirm_btn").attachEvent("onclick", this.callback);
    				}
			    }
			}
	}
	//关闭
	this.close			= function (){
		//如果为查看页面，直接关闭窗口
		if(undefined!=sBox_iframe.pagetype && sBox_iframe.pagetype=='view'){
			_get('body_span').parentNode.removeChild(_get('body_span'));
			return;
		}
		_get('body_span').parentNode.removeChild(_get('body_span'));
	}
	
	//帮助
	this.help			= function (){
		sBox_iframe.helpMsg();
	}
	
}
