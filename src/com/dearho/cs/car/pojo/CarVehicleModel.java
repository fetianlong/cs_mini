package com.dearho.cs.car.pojo;

import java.io.Serializable;
import java.util.Date;

import org.hibernate.util.StringHelper;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import com.dearho.cs.feestrategy.pojo.StrategyBase;
import com.dearho.cs.feestrategy.service.StrategyBaseService;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.pojo.FieldComment;
import com.dearho.cs.sys.util.DictUtil;

public class CarVehicleModel implements Serializable {

	private static final long serialVersionUID = 1L;
	/**主键Id*/
	private String id;
	
	/**车型名称*/
	@FieldComment("车型名称") 
	private String name;
	
	/**动力类型*/
	private String engineType;
	
	@FieldComment("动力类型") 
	private String engineTypeName;
	
	/**车辆品牌*/
	private String brand;
	
	@FieldComment("车辆品牌") 
	private String brandName;
	
	/**车辆等级*/
	private String level;
	
	@FieldComment("车辆等级") 
	private String levelName;
	
	/**座位数*/
	@FieldComment("座位数") 
	private Integer seatingNum;
	
	/**行李数*/
	@FieldComment("行李数") 
	private Double luggageNum;
	
	/**车辆厢数*/
	@FieldComment("车辆厢数") 
	private Integer casesNum;
	
	/**档类别*/
	private String gearboxType;
	
	@FieldComment("档类别") 
	private String gearboxTypeName;
	
	/**是否有娱乐系统*/
	private Integer entertainmentSystem;
	
	/**标准续航里程*/
	@FieldComment("标准续航里程") 
	private Integer standardMileage;
	
	/**创建人Id*/
	private String creatorId;
	
	/**创建时间*/
	private Date createDate;
	
	/**时间戳*/
	private Date ts;
	
	private String webModelPhoto;
	private String androidModelPhoto;
	private String IOSModelPhoto;
	private String microWebModelPhoto;
	
	@FieldComment("20%续航里程") 
	private Double mileage20;
	@FieldComment("30%续航里程") 
	private Double mileage30;
	@FieldComment("40%续航里程") 
	private Double mileage40;
	@FieldComment("50%续航里程") 
	private Double mileage50;
	@FieldComment("60%续航里程") 
	private Double mileage60;
	@FieldComment("70%续航里程") 
	private Double mileage70;
	@FieldComment("80%续航里程") 
	private Double mileage80;
	@FieldComment("90%续航里程") 
	private Double mileage90;
	@FieldComment("100%续航里程") 
	private Double mileage100;
	
	private Integer hasgps;
	
	@FieldComment("导航仪") 
	private String hasgpsDesc;
	
	private String shizuStrategy;
	private String rizuStrategy;
	
	@FieldComment("即时租策略") 
	private String shizuStrategyName;
	
	@FieldComment("日租策略") 
	private String rizuStrategyName;
	
	public String toString4CarAdd(){
		return id+","+DictUtil.getCnNameByGroupCodeAndDictId("1", level)+","
					+DictUtil.getCnNameByGroupCodeAndDictId("2", engineType)+","
					+seatingNum+","+luggageNum+","+casesNum+","
					+DictUtil.getCnNameByGroupCodeAndDictId("3", gearboxType);
		
	}
	public String getEngineTypeName() {
		return DictUtil.getCnNameByGroupCodeAndDictId("2", engineType);
	}
	public void setEngineTypeName(String engineTypeName) {
		this.engineTypeName = engineTypeName;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEngineType() {
		return engineType;
	}
	public void setEngineType(String engineType) {
		this.engineType = engineType;
	}
	public Integer getSeatingNum() {
		return seatingNum;
	}
	public void setSeatingNum(Integer seatingNum) {
		this.seatingNum = seatingNum;
	}
	public String getGearboxType() {
		return gearboxType;
	}
	public void setGearboxType(String gearboxType) {
		this.gearboxType = gearboxType;
	}
	public String getCreatorId() {
		return creatorId;
	}
	public void setCreatorId(String creatorId) {
		this.creatorId = creatorId;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getTs() {
		return ts;
	}
	public void setTs(Date ts) {
		this.ts = ts;
	}
	
	public String getBrand() {
		return brand;
	}
	public void setBrand(String brand) {
		this.brand = brand;
	}
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public Integer getCasesNum() {
		return casesNum;
	}
	public void setCasesNum(Integer casesNum) {
		this.casesNum = casesNum;
	}
	public Integer getStandardMileage() {
		return standardMileage;
	}
	public void setStandardMileage(Integer standardMileage) {
		this.standardMileage = standardMileage;
	}
	
	public Integer getEntertainmentSystem() {
		return entertainmentSystem;
	}
	public void setEntertainmentSystem(Integer entertainmentSystem) {
		this.entertainmentSystem = entertainmentSystem;
	}
	
	public String getWebModelPhoto() {
		return webModelPhoto;
	}

	public void setWebModelPhoto(String webModelPhoto) {
		this.webModelPhoto = webModelPhoto;
	}

	public String getAndroidModelPhoto() {
		return androidModelPhoto;
	}

	public void setAndroidModelPhoto(String androidModelPhoto) {
		this.androidModelPhoto = androidModelPhoto;
	}

	public String getIOSModelPhoto() {
		return IOSModelPhoto;
	}

	public void setIOSModelPhoto(String iOSModelPhoto) {
		IOSModelPhoto = iOSModelPhoto;
	}

	public String getMicroWebModelPhoto() {
		return microWebModelPhoto;
	}

	public void setMicroWebModelPhoto(String microWebModelPhoto) {
		this.microWebModelPhoto = microWebModelPhoto;
	}

	public CarVehicleModel(String id, String name, String engineType,
			String brand, String level, Integer seatingNum, Double luggageNum,
			Integer casesNum, String gearboxType, Integer entertainmentSystem,
			Integer standardMileage, String creatorId, Date createDate, Date ts) {
		super();
		this.id = id;
		this.name = name;
		this.engineType = engineType;
		this.brand = brand;
		this.level = level;
		this.seatingNum = seatingNum;
		this.luggageNum = luggageNum;
		this.casesNum = casesNum;
		this.gearboxType = gearboxType;
		this.entertainmentSystem = entertainmentSystem;
		this.standardMileage = standardMileage;
		this.creatorId = creatorId;
		this.createDate = createDate;
		this.ts = ts;
	}
	public CarVehicleModel() {
		super();
	}
	public Double getMileage20() {
		return mileage20;
	}
	public void setMileage20(Double mileage20) {
		this.mileage20 = mileage20;
	}
	public Double getMileage30() {
		return mileage30;
	}
	public void setMileage30(Double mileage30) {
		this.mileage30 = mileage30;
	}
	public Double getMileage40() {
		return mileage40;
	}
	public void setMileage40(Double mileage40) {
		this.mileage40 = mileage40;
	}
	public Double getMileage50() {
		return mileage50;
	}
	public void setMileage50(Double mileage50) {
		this.mileage50 = mileage50;
	}
	public Double getMileage60() {
		return mileage60;
	}
	public void setMileage60(Double mileage60) {
		this.mileage60 = mileage60;
	}
	public Double getMileage70() {
		return mileage70;
	}
	public void setMileage70(Double mileage70) {
		this.mileage70 = mileage70;
	}
	public Double getMileage80() {
		return mileage80;
	}
	public void setMileage80(Double mileage80) {
		this.mileage80 = mileage80;
	}
	public Double getMileage90() {
		return mileage90;
	}
	public void setMileage90(Double mileage90) {
		this.mileage90 = mileage90;
	}
	public Double getMileage100() {
		return mileage100;
	}
	public void setMileage100(Double mileage100) {
		this.mileage100 = mileage100;
	}
	

	public Integer getHasgps() {
		return hasgps;
	}
	public void setHasgps(Integer hasgps) {
		this.hasgps = hasgps;
	}
	public String getShizuStrategy() {
		return shizuStrategy;
	}
	public void setShizuStrategy(String shizuStrategy) {
		this.shizuStrategy = shizuStrategy;
	}
	public String getRizuStrategy() {
		return rizuStrategy;
	}
	public void setRizuStrategy(String rizuStrategy) {
		this.rizuStrategy = rizuStrategy;
	}
	public String getShizuStrategyName() {
		WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
		StrategyBaseService strategyBaseService = (StrategyBaseService) webApplicationContext.getBean("strategyBaseService");
		StrategyBase sb = strategyBaseService.searchStrategyBaseById(shizuStrategy);
		return sb.getName();
	}
	public void setShizuStrategyName(String shizuStrategyName) {
		this.shizuStrategyName = shizuStrategyName;
	}
	public String getRizuStrategyName() {
		if(StringHelper.isNotEmpty(rizuStrategy))
		{
			WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
			StrategyBaseService strategyBaseService = (StrategyBaseService) webApplicationContext.getBean("strategyBaseService");
			StrategyBase sb = strategyBaseService.searchStrategyBaseById(rizuStrategy);
			return  sb.getName();
		}
		return null;
	}
	
	public void setRizuStrategyName(String rizuStrategyName) {
		this.rizuStrategyName = rizuStrategyName;
	}
	
	public String getHasgpsDesc() {
		if(hasgps == 1){
			return "有";
		}else{
			return "没有";
		}
	}
	
	public void setHasgpsDesc(String hasgpsDesc) {
		this.hasgpsDesc = hasgpsDesc;
	}
	
	public String getBrandName() {
		Dict brandDict = DictUtil.getDictById(brand);
		return brandDict.getCnName();
	}
	
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	
	public String getLevelName() {
		Dict levelDict = DictUtil.getDictById(brand);
		return levelDict.getCnName();
	}
	
	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}
	
	public String getGearboxTypeName() {
		Dict gearboxTypeDict = DictUtil.getDictById(gearboxType);
		return gearboxTypeDict.getCnName();
	}
	public void setGearboxTypeName(String gearboxTypeName) {
		this.gearboxTypeName = gearboxTypeName;
	}
	public Double getLuggageNum() {
		return luggageNum;
	}
	public void setLuggageNum(Double luggageNum) {
		this.luggageNum = luggageNum;
	}
}
