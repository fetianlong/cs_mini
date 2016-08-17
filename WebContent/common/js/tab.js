var cur_tab=1; 
function dis_tab(tab){ 
	if(cur_tab==tab) return; 
	$('table'+cur_tab).style.display='none'; 
	$('disTag'+cur_tab).className='g002'; 
	cur_tab=tab; 
	$('table'+cur_tab).style.display=''; 
	$('disTag'+cur_tab).className='g001'; 
}