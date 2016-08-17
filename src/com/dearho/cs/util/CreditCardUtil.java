package com.dearho.cs.util;

import java.util.ArrayList;
import java.util.List;

public class CreditCardUtil {

	public static boolean checkCardNo(String cardNo){
		//银行卡号校验

	    //Description:  银行卡号Luhm校验

	    //Luhm校验规则：16位银行卡号（19位通用）:
	    
	    // 1.将未带校验位的 15（或18）位卡号从右依次编号 1 到 15（18），位于奇数位号上的数字乘以 2。
	    // 2.将奇位乘积的个十位全部相加，再加上所有偶数位上的数字。
	    // 3.将加法和加上校验位能被 10 整除。
		if(StringHelper.isEmpty(cardNo)){
			return false;
		}
		if (cardNo.length() < 16 || cardNo.length() > 19) {
			//$("#banknoInfo").html("银行卡号长度必须在16到19之间");
			return false;
		}
		String num = "\\d*";  //全数字
		if (!cardNo.matches(num)) {
			//$("#banknoInfo").html("银行卡号必须全为数字");
			return false;
		}
		//开头6位
		String strBin="10,18,30,35,37,40,41,42,43,44,45,46,47,48,49,50,51,52,53,54,55,56,58,60,62,65,68,69,84,87,88,94,95,98,99";    
		if (strBin.indexOf(cardNo.substring(0, 2))== -1) {
			//$("#banknoInfo").html("银行卡号开头6位不符合规范");
			return false;
		}
        String lastNum=cardNo.substring(cardNo.length()-1);//取出最后一位（与luhm进行比较）
    
        String first15Num=cardNo.substring(0,cardNo.length()-1);//前15或18位
        List<String> newArr=new ArrayList<String>();
        for(int i=first15Num.length()-1;i>-1;i--){    //前15或18位倒序存进数组
            newArr.add(first15Num.substring(i,i+1));
        }
        List<Integer> arrJiShu=new ArrayList<Integer>();  //奇数位*2的积 <9
        List<Integer> arrJiShu2=new ArrayList<Integer>(); //奇数位*2的积 >9
        
        List<String> arrOuShu=new ArrayList<String>();  //偶数位数组
        for(int j=0;j<newArr.size();j++){
            if((j+1)%2==1){//奇数位
                if(Integer.parseInt(newArr.get(j))*2<9)
                arrJiShu.add(Integer.parseInt(newArr.get(j))*2);
                else
                arrJiShu2.add(Integer.parseInt(newArr.get(j))*2);
            }
            else //偶数位
            arrOuShu.add(newArr.get(j));
        }
        
        List<Integer> jishu_child1=new ArrayList<Integer>();//奇数位*2 >9 的分割之后的数组个位数
        List<Integer> jishu_child2=new ArrayList<Integer>();//奇数位*2 >9 的分割之后的数组十位数
        for(int h=0;h<arrJiShu2.size();h++){
            jishu_child1.add(arrJiShu2.get(h)%10);
            jishu_child2.add(arrJiShu2.get(h)/10);
        }        
        
        int sumJiShu=0; //奇数位*2 < 9 的数组之和
        int sumOuShu=0; //偶数位数组之和
        int sumJiShuChild1=0; //奇数位*2 >9 的分割之后的数组个位数之和
        int sumJiShuChild2=0; //奇数位*2 >9 的分割之后的数组十位数之和
        int sumTotal=0;
        for(int m=0;m<arrJiShu.size();m++){
            sumJiShu=sumJiShu+arrJiShu.get(m);
        }
        
        for(int n=0;n<arrOuShu.size();n++){
            sumOuShu=sumOuShu+Integer.parseInt(arrOuShu.get(n));
        }
        
        for(int p=0;p<jishu_child1.size();p++){
            sumJiShuChild1=sumJiShuChild1+jishu_child1.get(p);
            sumJiShuChild2=sumJiShuChild2+jishu_child2.get(p);
        }      
        //计算总和
        sumTotal=sumJiShu+sumOuShu+sumJiShuChild1+sumJiShuChild2;
        
        //计算Luhm值
        int k= sumTotal%10==0?10:sumTotal%10;        
        int luhm= 10-k;
        
        if(lastNum.equals(""+luhm)){
        	return true;
        }
        else{
        	return false;
        }        
    }
}
