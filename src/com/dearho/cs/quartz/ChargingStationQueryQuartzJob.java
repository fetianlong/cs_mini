package com.dearho.cs.quartz;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.dearho.cs.teldintrf.pojo.TeldIntrfPile;
import com.dearho.cs.teldintrf.pojo.TeldIntrfStation;
import com.dearho.cs.teldintrf.service.TeldIntrfPileService;
import com.dearho.cs.teldintrf.service.TeldIntrfStationService;
import com.dearho.cs.teldintrf.util.TeldInterface;
import com.dearho.cs.util.CryptoTools;
import com.dearho.cs.util.JsonTools;

/**
 * 
 * 充电桩信息定时查询
 * @author GaoYunpeng
 *
 */
public class ChargingStationQueryQuartzJob {

	private static  Log log = LogFactory.getLog(ChargingStationQueryQuartzJob.class);
	
	private TeldIntrfStationService teldIntrfStationService;
	private TeldIntrfPileService teldIntrfPileService;
	
	private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private Date logTime;
	
	public void execute(){
		logTime=new Date();
	    log.debug(sdf.format(logTime)+"  更新特来电充电站信息 任务调度start");
	    
	    String resultListInfoStr = TeldInterface.getStations();
		try {
			JSONObject jsonObject = TeldInterface.desResultToJSONObject(resultListInfoStr);
			if(jsonObject == null){
				return;
			}
			List<Map<String,Object>> maps = JsonTools.parseJSON2List(String.valueOf(jsonObject.get("staList")));
			
			for (Map<String, Object> map : maps) {
				Thread.sleep(10000);
				TeldIntrfStation station = teldIntrfStationService.getTeldStationByCode(String.valueOf(map.get("staCode")));
				if(station == null){
					station = new TeldIntrfStation();
					station.setCreateTime(new Date());
				}
				else{
					station.setUpdateTime(new Date());
				}
				station.setAcableNum(Integer.parseInt(String.valueOf(map.get("acableNum"))));
				station.setAcNum(Integer.parseInt(String.valueOf(map.get("acNum"))));
				station.setCity(String.valueOf(map.get("city")));
				station.setDcableNum(Integer.parseInt(String.valueOf(map.get("dcableNum"))));
				station.setDcNum(Integer.parseInt(String.valueOf(map.get("dcNum"))));
				station.setLat(new BigDecimal(String.valueOf(map.get("lat"))));
				station.setLng(new BigDecimal(String.valueOf(map.get("lng"))));
				station.setPrice(new BigDecimal(String.valueOf(map.get("price"))));
				station.setProvince(String.valueOf(map.get("province")));
				station.setRegion(String.valueOf(map.get("region")));
				station.setStaAddress(String.valueOf(map.get("staAddress")));
				station.setStaCode(String.valueOf(map.get("staCode")));
				station.setStaName(String.valueOf(map.get("staName")));
				station.setStaOpstate(String.valueOf(map.get("staOpstate")));
				station.setStaType(String.valueOf(map.get("staType")));
				
				teldIntrfStationService.addOrUpdate(station);
				
				String pileListInfoDes = TeldInterface.getStationDetail(station.getStaCode());
				JSONObject detailJsonObject = null;
				if(pileListInfoDes == null){
					continue;
				}
				try {
					detailJsonObject = TeldInterface.desResultToJSONObject(pileListInfoDes);
				} catch (Exception e) {
					continue;
				}
				
				List<Map<String,Object>> pileMaps = JsonTools.parseJSON2List(String.valueOf(detailJsonObject.get("pileList")));
				
				if(pileMaps != null && pileMaps.size() > 0){
					for (int i = 0; i < pileMaps.size(); i++) {
						Map<String,Object> pileMap = pileMaps.get(i);
						TeldIntrfPile pileInfo = new TeldIntrfPile();
						pileInfo.setStationCode(station.getStaCode());
						pileInfo.setPileCode(String.valueOf(pileMap.get("pileCode")));
						pileInfo.setPileName(String.valueOf(pileMap.get("pileName")));
						pileInfo.setPileType(String.valueOf(pileMap.get("pileType")));
						teldIntrfPileService.addOrUpdate(pileInfo);
					}
					
				}
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public TeldIntrfStationService getTeldIntrfStationService() {
		return teldIntrfStationService;
	}
	public void setTeldIntrfStationService(
			TeldIntrfStationService teldIntrfStationService) {
		this.teldIntrfStationService = teldIntrfStationService;
	}
	public TeldIntrfPileService getTeldIntrfPileService() {
		return teldIntrfPileService;
	}
	public void setTeldIntrfPileService(
			TeldIntrfPileService teldIntrfPileService) {
		this.teldIntrfPileService = teldIntrfPileService;
	}
	
}
