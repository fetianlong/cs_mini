/**
 * ��������
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
		//��ʼ������
		this.s_imagepath	= '';	           //ͼƬ·��
		this.s_helpbutton	= 1;	           //��ʵ������ť
		this.s_title		= "С��ʾ";	       //���ڱ���
		this.s_title		= "С��ʾ";	       //���ڱ���
		this.s_content		= "";	           //��������
		this.s_width		= 450;		       //���ڿ��
		this.s_height		= 100;		       //���ڸ߶�
		if( type=="" ){
			this.s_type		= 0;
		}else{
			this.s_type			= type;		      //0:��Ϣ��ʾ����
											      //1����IFrame�Ĵ���
										          //2��confirm����
	 										      //3��prompt����
		}

    	this.s_bg_touming	= 0.6;		                  //����͸����0~1
    	this.s_bg_color		= "#666666"	;                 //�ɰ���ɫ
    	this.s_boarder_outside	= "#000000";	          //�����ɫ
    	this.s_bgcolor_outside  = "#000000";              //��򱳾�ɫ
    	this.inside_border_padding = 0;                   //������м�Ŀ��
    	this.inside_border_width= 0;                      //�ڿ���
    	this.s_iframe_src	= src;		                  //iframe��������
    	this.s_top			= 5;		                  //������ඥ���߶�
		this.set_title	  	= 1;		                  //�Ƿ��б���
		this.callback       = '';                         //�ص�������
        this.title_color    = '#FFFFFF';                  //������ɫ
		//��ʼ��
		this.init  			= function (){
			var base_index	= 10002;
			var title_height= 26;
			var bg_height	= document.body.scrollHeight?document.body.scrollHeight:document.documentElement.clientHeight;	//�ɲ��ĸ߶�
				bg_height   = bg_height<250?250:bg_height;
				bg_height   = bg_height>250?250:bg_height;
			var bg_width	= document.body.scrollWidth?document.body.scrollWidth:document.documentElement.clientWidth;	//�ɲ��Ŀ��
			this.s_height		= this.s_height-title_height;
	       	this.s_top			= (document.body.scrollTop?document.body.scrollTop:document.documentElement.scrollTop)+this.s_top;   //������ඥ���߶�
			//������ߵ�������ʾ���ȼ���iframe
			var high_iframe= '<iframe src="" style="position:absolute; visibility:inherit; top:0px; left:0px; width:'+bg_height+'px; height:'+bg_width+'px; z-index:-1;filter='+"'progid:DXImageTransform.Microsoft.Alpha(style=0,opacity=0)'"+';" frameborder="1"></iframe>';
			//�رհ�ť
			var close_button = '<img src="'+this.s_imagepath+'/close.gif" style="float:right;cursor:pointer" onclick="" id="close_img" title="�ر�" />';
			//������ť
			var help_button = '<img src="'+this.s_imagepath+'/help.gif" style="float:right;cursor:pointer" onclick="" id="help_img" title="����" />';
			//�ɰ�
			this.bg_div		= '<div id="bg_div" style="position:absolute;top:0px;left:0px;width:100%;height:'+bg_height*2+'px;z-index:'+base_index+';background-color:'+this.s_bg_color+';filter:alpha(opacity='+this.s_bg_touming*100+');opacity:'+this.s_bg_touming+';display:none;"></div>';
			//�������ڸ�����
			this.float_div	= '<div id="float_div" style="top:'+this.s_top+'px;position:absolute;left:'+(bg_width-this.s_width)/2+'px;width:'+this.width+'px;z-index:'+(base_index+2)+';background:#E3F4FC; auto;float:none;border:1px solid '+this.s_boarder_outside+'" unselectable="on">';
			//���
			this.float_div += '<table width="'+this.s_width+'"  cellpadding="0" cellspacing="0" bgcolor="'+this.s_bgcolor_outside+'" unselectable="on">';
			//������
			if( this.set_title==1){
				if(this.s_helpbutton==0)this.float_div += '<tr><td  unselectable="on"><table width="100%" border="0" cellspacing="0" cellpadding="0" unselectable="on"><tr style="line-height:'+title_height+'px;"><td width="2">&nbsp;</td><td style="color: '+this.title_color+';font-weight: bold;font-size: 14px" unselectable="on" ><span class="STYLE2" unselectable="on" >&nbsp;'+this.s_title+'</span></td><td width="10"><span style="clear:both;padding:8px 0">'+close_button+'</span></td><td width="2">&nbsp;</td></tr></table></td></tr>';
				else this.float_div += '<tr><td  unselectable="on"><table width="100%" border="0" cellspacing="0" cellpadding="0" unselectable="on"><tr style="line-height:'+title_height+'px;"><td width="2">&nbsp;</td><td style="color: '+this.title_color+';font-weight: bold;font-size: 14px" unselectable="on" ><span class="STYLE2" unselectable="on" >&nbsp;'+this.s_title+'</span></td><td width="10"><span style="clear:both;padding:8px 0">'+close_button+'</td><td width="10">'+help_button+'<span style="clear:both;padding:8px 0"></span></td><td width="2">&nbsp;</td></tr></table></td></tr>';
			}
			//������
			
			switch(this.s_type){
				case 0://��Ϣ��ʾ����
					break;
				case 1://1����IFrame�Ĵ���
					//alert(this.s_iframe_src);
					if( this.s_iframe_src!="" ){
						var iframe = '<iframe id="sBox_iframe" name="sBox_iframe"  width="'+this.s_width+'" height="'+this.s_height+'"  frameborder="0" src="'+this.s_iframe_src+'"   scrolling="no" ></iframe>';
						this.s_content	+= iframe;
					}else{
						this.s_content = "���ʼ��src1";
					}

					break;
				case 2://2��confirm����
				    var confirm_str = '';
				    confirm_str    += '<table width="100%" border="0" cellspacing="0" cellpadding="0">';
				    confirm_str    += '<tr>';
				    confirm_str    += '<td colspan="2"><div align="center" style="font-weight:bold;color:#CC3300;font-size:14px">'+this.s_content+'</div></td>';
				    confirm_str    += '</tr>';
				    confirm_str    += '<tr><td height="10"></td><td></td></tr>';
				    confirm_str    += '<tr>';
				    confirm_str    += '<td><div align="right" style="margin-right:20px;">';
				    confirm_str    += '<input type="button" name="confirm_btn" id="confirm_btn" value=" ȷ�� "  />';
				    confirm_str    += '</div></td>';
				    confirm_str    += '<td><div align="left" style="margin-left:20px;">';
				    confirm_str    += '<input type="button" name="cancel_btn" id="cancel_btn" value=" ȡ�� " />';
				    confirm_str    += '</div></td>';
				    confirm_str    += '</tr>';
				    confirm_str    += '<tr><td height="10"></td><td></td></tr>';
				    confirm_str    += '</table>';
				    this.s_content	= confirm_str;
					break;
				case 3://3��prompt����
					break;
				default ://

			}
			this.float_div += '<tr><td><div style="background-color:'+this.s_bgcolor_outside+'; padding:'+this.inside_border_padding+'px;"><div  style="border:'+this.inside_border_width+'px solid '+this.s_boarder_outside+';background-color:#FFFFFF">'+this.s_content+'</div></div></td></tr></table></div><div id="dialogBoxShadow" style="display:none;z-index:'+(base_index-2)+';"></div>';
			this.initBody();								//��ʼ���ײ�span��ǩ
			this.show();
			//��ӽ���
			_get('float_div').focus();
		}
		
		
	//���صײ�����
	this.initBody 		= function (){	
		_get('body_span')?_get('body_span').parentNode.removeChild(_get('body_span')):"";
		var oDiv=document.createElement('span');
		oDiv.id='body_span';
		document.body.appendChild(oDiv);
	}
	//��ʾ������
	this.show			= function(){
			document.getElementById('body_span').innerHTML = this.bg_div+this.float_div;
			document.getElementById('bg_div').style.display= "";
			if( this.set_title==1 ){
				//Ϊ�رհ�ť���¼�
				if( _get("close_img").addEventListener ){//ff��
					_get("close_img").addEventListener("click", this.close, false);
				}else{//IE��
					_get("close_img").attachEvent("onclick", this.close);
				}
				//Ϊ������ť���¼�
				if( _get("help_img").addEventListener ){//ff��
					_get("help_img").addEventListener("click", this.help, false);
				}else{//IE��
					_get("help_img").attachEvent("onclick", this.help);
				}
			    if( this.s_type==2 ){
    				//Ϊȡ����ť���¼�
    				if( _get("cancel_btn").addEventListener ){//ff��
    					_get("cancel_btn").addEventListener("click", this.close, false);
    				}else{//IE��
    					_get("cancel_btn").attachEvent("onclick", this.close);
    				}
    				//Ϊȷ����ť���¼�
    				if( _get("confirm_btn").addEventListener ){//ff��
    					_get("confirm_btn").addEventListener("click", this.callback, false);
    				}else{//IE��
    					_get("confirm_btn").attachEvent("onclick", this.callback);
    				}
			    }
			}
	}
	//�ر�
	this.close			= function (){
		//���Ϊ�鿴ҳ�棬ֱ�ӹرմ���
		if(undefined!=sBox_iframe.pagetype && sBox_iframe.pagetype=='view'){
			_get('body_span').parentNode.removeChild(_get('body_span'));
			return;
		}
		_get('body_span').parentNode.removeChild(_get('body_span'));
	}
	
	//����
	this.help			= function (){
		sBox_iframe.helpMsg();
	}
	
}
