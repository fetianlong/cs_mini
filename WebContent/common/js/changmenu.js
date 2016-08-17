function openTree(n)

{
	obj = eval("tree" + n);
	sIcon=eval("icon" + n);
	if (obj.style.display == "none")

	{
		sIcon.src="common/skins/default/images/title_R.gif";
		obj.style.display = "block";//��ʾ�Ӳ˵�
	}

	else

	{
		sIcon.src="common/skins/default/images/title_R_up.gif";
		obj.style.display = "none";

	}

}
function collapseAll(){

for(i=1;i<menuNum+1;i++)
{
   eval("tree" +i+ ".style.display = 'none'")
   eval("icon" +i+ ".src = 'common/skins/default/images/title_R_up.gif'")
}

}
//չ����һ��,�ر�����
function collapseAll2(){
for(i=1;i<menuNum+1;i++)
{
   eval("tree" +i+ ".style.display = 'none'")
   eval("icon" +i+ ".src = 'common/skins/default/images/title_R_up.gif'")
}
openTree('1');

}
function showAll(){
for(i=1;i<menuNum+1;i++)
{
   eval("tree" +i+ ".style.display = 'block'")
   eval("icon" +i+ ".src = 'common/skins/default/images/title_R.gif'")
}

}

