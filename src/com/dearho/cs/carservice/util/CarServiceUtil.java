package com.dearho.cs.carservice.util;

import com.dearho.cs.carservice.pojo.CarAccident;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

public class CarServiceUtil {
	public static boolean paramStr2Obj(String str, CarAccident updateAccident) {
		if(StringHelper.isEmpty(str) || updateAccident == null){
			return false;
		}
		try {
			updateAccident.setId(findValue("carAccident.id", str));
			if(!StringHelper.isRealNull(findValue("carAccident.code",str))){
				updateAccident.setCode(findValue("carAccident.code", str));
			}
			if(!StringHelper.isRealNull(findValue("carAccident.city",str))){
				updateAccident.setCity(findValue("carAccident.city", str));
			}
			updateAccident.setAcceptFlag(findValue("carAccident.acceptFlag", str));
			updateAccident.setAcceptMember(findValue("carAccident.acceptMember", str));
			updateAccident.setAccidentDesc(findValue("carAccident.acceptDesc", str));
			//carAccident.id=402881e84e2db4fa014e2db5e2980000&carAccident.isDiscard=0&carAccident.code=12&carAccident.city=1&carAccident.orderId=402881e44df9ec48014df9ee93590000&carAccident.orderCode=2015061600002&carAccident.memberId=402881e44ddc145e014ddc15079a0000&carAccident.memberName=韦小宝&carAccident.carId=402881e44d79b282014d7a20e2720000&vehiclePlateId=京W23545&carAccident.accidentType=402881e74e2473c2014e247a37950003&carAccident.carLose=&carAccident.happenTime=&carAccident.noticeCs=0&carAccident.acceptFlag=&carAccident.noticeTime=&carAccident.acceptMember=&carAccident.handleStatus=&carAccident.accidentDesc=&carAccident.remark=

			updateAccident.setAccidentType(findValue("carAccident.accidentType", str));
			updateAccident.setCarId(findValue("carAccident.carId", str));
			updateAccident.setCarLose(findValue("carAccident.carLose", str));
			updateAccident.setCityName(findValue("carAccident.cityName", str));
			updateAccident.setHandleStatus(findValue("carAccident.handleStatus", str));
			updateAccident.setHappenTime(StringHelper.isRealNull(findValue("carAccident.happenTime", str)) 
					? null : DateUtil.parseDate(findValue("carAccident.happenTime", str), "yyyy-MM-dd"));
			updateAccident.setMemberId(findValue("carAccident.memberId", str));
			updateAccident.setMemberName(findValue("carAccident.memberName", str));
			updateAccident.setNoticeCs(StringHelper.isRealNull(findValue("carAccident.noticeCs", str)) 
					? 0 : Integer.parseInt(findValue("carAccident.noticeCs", str)));
			updateAccident.setNoticeTime(StringHelper.isRealNull(findValue("carAccident.noticeTime", str)) 
					? null : DateUtil.parseDate(findValue("carAccident.noticeTime", str), "yyyy-MM-dd"));
			updateAccident.setOrderCode(findValue("carAccident.orderCode", str));
			updateAccident.setOrderId(findValue("carAccident.orderId", str));
			updateAccident.setPlateNumber(findValue("carAccident.plateNumber", str));
			updateAccident.setRemark(findValue("carAccident.remark", str));
			updateAccident.setAccidentDesc(findValue("carAccident.accidentDesc", str));
			
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static String findValue(String key,String str){
		if(!str.contains(key)){
			return "";
		}
		if(str.indexOf("&", str.indexOf(key)) > 0){
			return str.substring(str.indexOf(key)+key.length()+1, str.indexOf("&", str.indexOf(key)));
		}
		else{
			return str.substring(str.indexOf(key)+key.length()+1);
		}
	}
}
