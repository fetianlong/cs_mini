/*
 * monthPicker v1.0  Copyright(c) 2012 by CSS WeidongWang
 */
;(function($){
	$.fn.monthPicker = function(o) {
		var defaults = {
				start:'0000-00',
				end:'9999-12',
				btn_clear:false,
				btn_month:true,
				btn_close:true,
				lang:1								//0:en, 1,中文
			},
		o = $.extend(defaults,o);
		return this.each(function() {
			var $this = $(this),
			_ie = !!window.ActiveXObject,
			_ie6 = _ie && !window.XMLHttpRequest,
			_evObj = null,
			_y=0,
			_m=0,
			_v=$this.val(),
			data={
        month:["This month","本月"],
        clear:["Clear","清空"],
        close:["Close","关闭"]
      },
      $table = $('<div class="css-month"></div>'),
			iframeTpl = _ie6 ? '<iframe id="css-month-frm" hideFocus="true" frameborder="0" src="about:blank" style="position:absolute;z-index:-1;width:100%;top:0px;left:0px;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0)"><\/iframe>' : '',
			draw = function(){
				$('.inYear',$table).val(_y);
				for(var i=1;i<=12;i++){
					var cls='';
					var s=get(_y,i);
					if(s==_v) cls='current';
					else if(s<o.start||s>o.end) cls='disabled';
					$('[rel="'+i+'"]',$table).attr('class',cls);
				}	      
			},
			init = function(){
				var calTpl='<span class="prev"></span><span class="title"><input maxlength="4" onclick="this.select()" class="inYear" /></span><span class="next"></span><div class="clear"></div><div><ul>';
				for(var i=1;i<=12;i++)
					calTpl+='<li rel="'+i+'">'+i+'</li>';
				calTpl+='</ul></div><div class="hideYear"></div><div class="clear"></div>'+iframeTpl;
				$table.html(calTpl);
				var $yDiv=$('.hideYear',$table);
				document.body.appendChild($table.get(0));
				if(o.btn_close) $table.append('<div class="btnClose"><span>'+data.close[o.lang]+'</span></div>');
				if(o.btn_clear) $table.append('<div class="btnClear"><span>'+data.clear[o.lang]+'</span></div>');
				if(o.btn_month) $table.append('<div class="btnMonth"><span>'+data.month[o.lang]+'</span></div>');
				$('.btnClose',$table).click(function(){
		   		close();
				});
				$('.btnClear',$table).click(function(){
		   		var oldValue = $this.val();
		   		$this.val('');
				if ($this.val() != oldValue) $this.trigger("change");
		   		close();
				});
				$('.btnMonth',$table).click(function(){
					today();
					set();
		   		close();
				});
		    $('.prev',$table).click(function(){
		   		$('.inYear',$table).val(--_y);
		   		draw();
				});
				$('.next',$table).click(function(){
		   		$('.inYear',$table).val(++_y);
		   		draw();
				});
				$('li', $table).click(function(){
					var i=$(this).attr('rel');
					var s=get(_y,i)
					if(s<o.start||s>o.end) return;
					_m=i;
					set();
					close();
		    });
		   	var yData = function(iY){
					var str='';
					for(var i=0;i<10;i++)
						str+='<li rel="'+(parseInt(iY)+i)+'">'+(parseInt(iY)+i)+'</li>';
					$('.hYear',$yDiv).html(str);
					$('.hYear li',$yDiv).click(function(){
						_y=parseInt($(this).attr('rel'));
						hide();
						draw();
					});
				},
				hide = function(){
					$('.inYear',$table).css('border-color','#fff');
					$yDiv.hide();
				};
				$('.inYear',$table).keyup(function(e){
					var v=$(this).val();
					if(isNaN(v)||isnull(v)) return false;
					_l=parseInt(v);
					_l=_l>9990?9990:_l;
					yData(_l);												
					if (e.keyCode == 13){
						_y=v;
						draw();
						hide();
						$('.inYear',$table).blur();
					}
				}).click(function(){
					$(this).css('border-color','#D7DCE6');
					var $that=$(this),
					_l=_y,
					$ul=$('<ul class="hYear"><ul>');
					$yDiv.html('');
					$yDiv.append($ul);
					$yDiv.append('<div><span class="hPrev">←</span><span class="hYearClose">×</span><span class="hNext">→</span></div>');
					yData(_l>5?_l-5:1);
					$yDiv.css({ left:'30px', top:'26px' });
					$yDiv.show();
					$('.hYearClose',$yDiv).click(function(){
						hide();
					});
					$('.hPrev',$yDiv).click(function(){
						_l=parseInt(_l)-10;
						yData(_l-5);
					});
					$('.hNext',$yDiv).click(function(){
						_l=parseInt(_l)+10;
						yData(_l-5);
					});
				});	
			},
			get = function(y,m){
				return y+'-'+((100+parseInt(m))+'').substring(1);
			},
			today = function(){
				_y=new Date().getFullYear();
				_m=new Date().getMonth()+1;
			},
			set = function(){
				_v=get(_y,_m);
				var oldValue = $this.val();
				$this.val(_v);
				if ($this.val() != oldValue) $this.trigger("change");
			},
			css = function(){
				var o = $this.offset();
			  $table.css({ left:o.left + 'px', top:(o.top+$this.get(0).offsetHeight-1) + 'px' });
			  _ie6 && $('#css-month-frm').css({height:$table.height()+'px'});
			},
			close = function(){
				$table.hide();
			};
			$this.click(function(evt){
			if(isnull(_v)){
					today();
				}else{
					_y=parseInt(_v.split('-')[0]);
					_m=parseInt('1'+_v.split('-')[1])-100;
				}
				draw();
	      css();
	      $table.show();
			});
			init();
			$(document).bind('click',function(evt){
				if($table.is(":visible")){
					if($(evt.target).attr('class')=='inYear') return;
					if($this.is(evt.target)||$table.is(evt.target)||$table.has(evt.target).length>0) return;
					close();
				}
			});
		});
	};
	function isnull(str){
		return (str==null||str==""||str=="undefine");
	};
})(jQuery)