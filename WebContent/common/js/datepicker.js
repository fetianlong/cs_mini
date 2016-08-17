;(function($){
	$.fn.datePicker = function(o) {
		var defaults = {
				startDay:'0000-00',
				endDay:'9999-12-31',
		        disabledDays:[],			//1,3,5 即日期为1,3,5的无效
		        disabledWeekDays:[],	//0,1   即周日、周一无效	
		        btn_ok:true,
		       	btn_clear:true,
				btn_today:true,
				btn_close:true,
				time:false,						//true 带时间 false 不带时间
				lang:1								//0:en, 1,中文
			},
		o = $.extend(defaults,o);
		var dayChanged = false;
		var currentDay = null;
		var currentDayCss = null;
		var firstDay = null;
		var firstDayCss = null;
		return this.each(function() {
			if(o.time){
				o.btn_ok = true;
				o.btn_close=false;
			}else{
				o.btn_close=false;
				o.btn_ok = false;
			}
			var $this = $(this),
			_ie = !!window.ActiveXObject,
			_ie6 = _ie && !window.XMLHttpRequest,
			_evObj = null,
			_y=0,
			_m=0,
			_d=0,
			_h=0,
			_t=0,
			_v=$this.val(),
			data={
        month: [
           ["JAN", "FEB", "MAR", "APR", "MAY", "JUN", "JUL", "AUG", "SEP", "OCT", "NOV", "DEC"],
           ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"]
        ],
        week: [
           ["Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"],
           ["日", "一", "二", "三", "四", "五", "六"]
        ],
        ok:["Ok","确定"],
        today:["Today","今天"],
        clear:["Clear","清空"],
        close:["Close","关闭"]
      },
			$table = $('<div class="css-date"></div>'),
			$cal = $('<div class="css-calendar"></div>'),
			iframeTpl = _ie6 ? '<iframe id="css-date-frm" hideFocus="true" frameborder="0" src="about:blank" style="position:absolute;z-index:-1;width:100%;top:0px;left:0px;filter:progid:DXImageTransform.Microsoft.Alpha(opacity=0)"><\/iframe>' : '',
			calTpl ='<span class="prev"></span><div class="month"></div><span class="next"></span>'+
								'<span class="prev_year"></span><div class="year"><input maxlength="4" onclick="this.select()" class="inYear" /></div><span class="next_year"></span>' +
								'<div class="hideMonth"></div><div class="hideYear"></div><div class="clear"></div><div>'+
								'<ul class="week">';
			for(var i=0;i<7;i++)
				calTpl+='<li>'+data.week[o.lang][i]+'</li>';
			calTpl+='</ul><ul class="date">';
			for (var i = 0; i < 42; i++)
				calTpl+='<li></li>';
			calTpl+='</ul></div><div class="clear"></div>';
			if(o.time){
				calTpl+='<div class="selTime"><select class="inHour">';
				for(var i=0; i<24; i++)
					calTpl+='<option>'+i+'</option>';
				calTpl+='</select><select class="inMinute">';
				for(var i=0; i<60; i++)
					calTpl+='<option>'+i+'</option>';
				calTpl+='</select></div>';	
			}
			calTpl+=iframeTpl;
			$cal.html(calTpl);
			var $mDiv=$('.hideMonth',$cal),
			$yDiv=$('.hideYear',$cal),
			draw = function(){
				$('.month',$cal).html(data.month[o.lang][parseInt(_m)]);
				$('.inYear',$cal).val(_y);
				var firstD=new Date(_y,_m,1);
				var d=add(firstD,-1*firstD.getDay());
				for (var i = 0; i < 42; i++){
					var cls='';
					if(toString(d)==_v) cls='current';	
					else if(disabled(d)) cls='disabled';
					else if(toString(d)==toString(new Date())) cls='today';
					else if(d.getMonth()!=_m) cls='other';
					else if(d.getDay()==0||d.getDay()==6) cls='week_end';
					if (o.time) if(toString(d)==_v.substring(0,10))cls='current';
					var li=$('.date li:eq('+i+')',$cal);
					li.attr('rel',toString(d));
					li.html(d.getDate());
					li.attr('class',cls);
					if (o.time && cls=='current'){
						firstDayCss = li.attr('class');
						firstDay = li;
					}
					d=add(d,1);
				}
			},
			init = function(){
				document.body.appendChild($table.get(0));
				$table.append($cal);
				if(o.btn_ok) $table.append('<div class="btnOk"><span>'+data.ok[o.lang]+'</span></div>');
				if(o.btn_close) $table.append('<div class="btnClose"><span>'+data.close[o.lang]+'</span></div>');
				if(o.btn_clear) $table.append('<div class="btnClear"><span>'+data.clear[o.lang]+'</span></div>');
				if(o.btn_today) $table.append('<div class="btnToday"><span>'+data.today[o.lang]+'</span></div>');
				$('.btnOk',$table).click(function(){
					_v = _v.substring(0,10);
					get_v();
					var oldValue = $this.val();
					$this.val(_v);
					if ($this.val() != oldValue) $this.trigger("change");
					close();
				});
				$('.btnClose',$table).click(function(){
					close();
				});
				$('.btnClear',$table).click(function(){
				var oldValue = $this.val();
		   		$this.val('');
		   		if ($this.val() != oldValue) $this.trigger("change");
		   		close();
				});
				$('.btnToday',$table).click(function(){
					if (o.time == false){
						today();
						get_v();
						var oldValue = $this.val();
						$this.val(_v);
						if ($this.val() != oldValue) $this.trigger("change");
						close();
					}else{
						today();
						draw();
						currentDay = null;
					}
				});
				$('.prev_year',$cal).click(function(){
		   		_y--;
		   		draw();
				});
				$('.next_year',$cal).click(function(){
		   		_y++;
		   		draw();
				});
		    $('.prev',$cal).click(function(){
		    	_m--;
		    	if(_m<0) _m=11;
		   		draw();
				});
				$('.next',$cal).click(function(){
		   		_m++;
		   		if(_m>11) _m=0;
		   		draw();
				});
				$('.date li', $cal).click(function(){
					if (o.time){
						_v=$(this).attr('rel');
						var p=(_v+' ').split(' ');
						var v=p[0].split('-');
						_y=parseInt(v[0]);
						_m=parseInt('1'+v[1])-101;
						_d=parseInt('1'+v[2])-100;
						if (currentDay != null){
							currentDay.attr('class',currentDayCss);
						}else{ 
							if (firstDay != null){
								firstDay.attr('class','');
							}
						}
						currentDayCss = $(this).attr('class');
						currentDay = $(this);
						dayChanged = true;
						$(this).attr('class','current');
					}else{
						if($(this).attr('class')=='disabled')	return;
						_v=$(this).attr('rel');
						get_v();
						var oldValue = $this.val();
						$this.val(_v);
						if ($this.val() != oldValue) $this.trigger("change");
						close();
					}
		     });
				$('.month',$cal).click(function(){
					hide();
					var $that=$(this);
					var str='<ul class="hMonth">';
					for(var i=0;i<12;i++)
						str+='<li rel="'+i+'">'+data.month[o.lang][i]+'</li>';
					str+='</ul><div class="hMonthClose">×</div>';
					$mDiv.html(str);
					$mDiv.css({ left:'2px', top:'26px' });
					$mDiv.show();
					$('.hMonth li',$mDiv).click(function(){
						_m=parseInt($(this).attr('rel'));
						draw();
						$mDiv.hide();
					});
					$('.hMonthClose',$mDiv).click(function(){
						$mDiv.hide();
					});
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
					$('.inYear',$cal).css('border-color','#fff');
					$yDiv.hide();
				};
				$('.inYear',$cal).keyup(function(e){
					var v=$(this).val();
					if(isNaN(v)||isnull(v)) return false;
					_l=parseInt(v);
					_l=_l>9990?9990:_l;
					yData(_l);												
					if (e.keyCode == 13){
						_y=v;
						draw();
						hide();
						$('.inYear',$cal).blur();
					}
				}).click(function(){
					$mDiv.hide();
					$(this).css('border-color','#D7DCE6');
					var $that=$(this),
					_l=_y,
					$ul=$('<ul class="hYear"><ul>');
					$yDiv.html('');
					$yDiv.append($ul);
					$yDiv.append('<div><span class="hPrev">←</span><span class="hYearClose">×</span><span class="hNext">→</span></div>');
					yData(_l>5?_l-5:1);
					$yDiv.css({ left:'88px', top:'26px' });
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
			toString = function(dt){
				var y=dt.getFullYear(),
				m=dt.getMonth(),
				d=dt.getDate(),
				h=dt.getHours(),
				t=dt.getMinutes();
				var s=y+'-'+((101+parseInt(m))+'').substring(1)+'-'+((100+parseInt(d))+'').substring(1);
				return s; 
			},
			add = function(d,i){
				return new Date(d.getTime()+i*24*60*60*1000) 
			},
			ok = function(){
				get_v();
				var oldValue = $this.val();
				$this.val(_v);
				if ($this.val() != oldValue) $this.trigger("change");
				close();
			},
			today = function(){
				_y=new Date().getFullYear();
				_m=new Date().getMonth();
				_d=new Date().getDate();
				_h=new Date().getHours();
				_t=new Date().getMinutes();
				_v=toString(new Date());
			},
			get_v = function(){
				if(o.time){
					_h=$('.inHour',$cal).val();
					if(isnull(_h)) _h=0;
					_t=$('.inMinute',$cal).val();
					if(isnull(_t)) _t=0;
					_v+=' '+((100+parseInt(_h))+'').substring(1)+':'+((100+parseInt(_t))+'').substring(1)+':00';
				}
			},
			parse = function(){
				var p=(_v+' ').split(' ');
				var v=p[0].split('-');
				_y=parseInt(v[0]);
				_m=parseInt('1'+v[1])-101;
				_d=parseInt('1'+v[2])-100;
				if(o.time){
					v=p[1].split(':');
					_h=parseInt('1'+v[0])-100;
					_t=parseInt('1'+v[1])-100;
				}	
			},
			css = function(){
				var o = $this.offset();
			  $table.css({ left:o.left + 'px', top:(o.top+$this.get(0).offsetHeight-1) + 'px' });
			  _ie6 && $('#css-date-frm').css({height:$table.height()+'px'});
			},
			disabled = function(d){
        var v=toString(d);
        if(v<o.startDay||v>o.endDay) return true;
        for(var i=0;i<o.disabledDays.length;i++)
        	if(o.disabledDays[i]==d.getDate()) return true;
        for(var i=0;i<o.disabledWeekDays.length;i++)
        	if(o.disabledWeekDays[i]==d.getDay()) return true;
        return false;
			},
			close = function(){
				$table.hide();
			};
			$this.click(function(evt){
				if(isnull(_v))
					today();
				else
					parse();
				if(o.time){
					$('.inHour',$cal).val(_h);
					$('.inMinute',$cal).val(_t);
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