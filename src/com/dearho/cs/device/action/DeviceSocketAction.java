package com.dearho.cs.device.action;

import com.dearho.cs.device.pojo.DeviceBinding;
import com.dearho.cs.device.service.DeviceBindingService;
import com.dearho.cs.resmonitor.pojo.CarLocation;
import com.dearho.cs.resmonitor.pojo.CarRealtimeState;
import com.dearho.cs.resmonitor.service.CarLocationService;
import com.dearho.cs.resmonitor.service.CarRealtimeStateService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.DateUtil;
import com.dearho.cs.util.StringHelper;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.log4j.Logger;

import com.trimble.api.protocol.base.ProtocolService;
import com.trimble.api.protocol.base.command.AuthenticationR;
import com.trimble.api.protocol.base.command.CMDType;
import com.trimble.api.protocol.base.command.IpAndPortSetting;
import com.trimble.api.protocol.base.command.Lock;
import com.trimble.api.protocol.base.command.LoginR;
import com.trimble.api.protocol.base.command.UnLock;
import com.trimble.api.protocol.base.data.BoxConfirm;
import com.trimble.api.protocol.base.data.Message;
import com.trimble.api.protocol.base.data.Packet;
import com.trimble.api.protocol.base.data.device.Authentication;
import com.trimble.api.protocol.base.data.device.BrushCard;
import com.trimble.api.protocol.base.data.device.GeneralIOUnit;
import com.trimble.api.protocol.base.data.device.GeneralStatus;
import com.trimble.api.protocol.base.data.device.Login;
import com.trimble.api.protocol.base.data.device.Track;
import com.trimble.api.protocol.base.data.device.VehicleInfoOnCAN;
import com.trimble.api.protocol.base.data.device.GeneralIOUnit.PropertyKeyId;
import com.trimble.api.protocol.base.data.device.GeneralIOUnit.UnitEvent;
import com.trimble.api.protocol.tap68c.ProtocolServiceImpl;


public class DeviceSocketAction extends AbstractAction{

	private static final long serialVersionUID = -4316311325001801868L;

	public static Map<String, Socket> socketPool = new HashMap<String, Socket>();
	
	private CarLocationService carLocationService;
	private DeviceBindingService deviceBindingService;
	private CarRealtimeStateService carRealtimeStateService;
	
	@SuppressWarnings("resource")
	@Override
	public String process() {
		ServerSocket serverSocket = null;
		
		try {
			serverSocket = new ServerSocket(10080,100);
			serverSocket.setSoTimeout(0);
			while(true){
				Socket socket = serverSocket.accept();
				new Thread(new Handler(socket,carLocationService,deviceBindingService,carRealtimeStateService)).start();         //创建一个工作进程并启动
			}
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public CarLocationService getCarLocationService() {
		return carLocationService;
	}

	public void setCarLocationService(CarLocationService carLocationService) {
		this.carLocationService = carLocationService;
	}

	public DeviceBindingService getDeviceBindingService() {
		return deviceBindingService;
	}

	public void setDeviceBindingService(DeviceBindingService deviceBindingService) {
		this.deviceBindingService = deviceBindingService;
	}
	
	public CarRealtimeStateService getCarRealtimeStateService() {
		return carRealtimeStateService;
	}
	public void setCarRealtimeStateService(
			CarRealtimeStateService carRealtimeStateService) {
		this.carRealtimeStateService = carRealtimeStateService;
	}

}
class Handler extends Thread{
	private CarLocationService carLocationService;
	private DeviceBindingService deviceBindingService;
	private CarRealtimeStateService carRealtimeStateService;
	private Socket socket;
	private Logger log;
	public Handler(Socket socket,CarLocationService carLocationService,DeviceBindingService deviceBindingService,
			CarRealtimeStateService carRealtimeStateService) throws Exception {
		this.carLocationService = carLocationService;
		this.deviceBindingService = deviceBindingService;
		this.carRealtimeStateService = carRealtimeStateService;
		this.socket = socket;
		log = Logger.getLogger(Handler.class ); 
	}
	public void run() {
		
		log.info("[creat new connection] "
				+ socket.getInetAddress()  + " : " + socket.getPort());
		InputStream is = null;
		OutputStream os = null;
		BufferedWriter bw = null;
		try {
			is = socket.getInputStream();  
	        BufferedReader br = new BufferedReader(new InputStreamReader(is));  
	        String line = null;  
	        String outLine = "";
	        while(true){
	        	line=br.readLine();
	        	log.info("[request source:] "
	    				+ socket.getInetAddress()  + " : " + socket.getPort());
		    	log.info("[request msg]:"+line);
		     	outLine = hand(line);
		     	os = socket.getOutputStream();  
		        bw = new BufferedWriter(new OutputStreamWriter(os));
		        log.info("return msg:"+outLine);
		        bw.write(outLine);  
		        bw.flush();
	        }
	    	
		} catch (Exception e) {
			log.error(e.getMessage());
		} finally {
			if(bw != null){
				try {bw.close();} catch (IOException e) {e.printStackTrace();}
			}
			if(os != null){
				try {os.close();} catch (IOException e) {e.printStackTrace();}
			}
			if(is != null){
				try {is.close();} catch (IOException e) {e.printStackTrace();}
			}
		}
		
       
	}
	public String hand(String oriMessage) throws Exception{
		ProtocolService service = new ProtocolServiceImpl();
		if(oriMessage != null && !service.isAccept(oriMessage.getBytes())){
			String deviceNo = oriMessage.split(",")[2];
			String command = oriMessage.split(",")[1];
			log.info("[start send command]...");
			if(DeviceSocketAction.socketPool.get(deviceNo) == null){
            	return "1\n";
            }
			
			Socket deviceSocket = DeviceSocketAction.socketPool.get(deviceNo);
			 //下行指令示例代码
			log.info("[command:]"+command);
			if("lock".equals(command)){
				 //上锁
	            Lock lockCommand = new Lock();
	            lockCommand.setSeqNo("4224");
	            lockCommand.setSerialNo(deviceNo);
	            byte[] lockCommandFinal = service.encode(lockCommand);
	            OutputStream os = null;
	    		BufferedWriter bw = null;
	            os = deviceSocket.getOutputStream();  
		        bw = new BufferedWriter(new OutputStreamWriter(os));
		        log.info("send lock command:"+new String(lockCommandFinal));
		        bw.write(new String(lockCommandFinal));  
		        bw.flush();
	            return "0\n";
			}
			else if("unlock".equals(command)){
				//将编码后的数据发送到设备，上锁指令发送完成后，在上行解析中接收设备对上锁指令的确认
	            //其他业务逻辑
	            UnLock unLockCommand = new UnLock();
	            unLockCommand.setSeqNo("5225");
	            unLockCommand.setSerialNo(deviceNo);
	            byte[] unlockCommandFinal = service.encode(unLockCommand);
	            OutputStream os = null;
	    		BufferedWriter bw = null;
	            os = deviceSocket.getOutputStream();  
		        bw = new BufferedWriter(new OutputStreamWriter(os));
		        log.info("send unlock command:"+new String(unlockCommandFinal));
		        bw.write(new String(unlockCommandFinal));  
		        bw.flush();
	            return "0\n";
			}
			else if("ipportSet".equals(command)){
				log.info("change ip port ...");
				log.info("ip:"+oriMessage.split(",")[3]+";port:"+oriMessage.split(",")[4]);
				IpAndPortSetting set = new IpAndPortSetting();
				set.setIp(oriMessage.split(",")[3]);
				set.setPort(Integer.parseInt(oriMessage.split(",")[4]));
				set.setSeqNo("5226");
				set.setSerialNo(deviceNo);
				byte[] setCommand = service.encode(set);
				OutputStream os = null;
	    		BufferedWriter bw = null;
	            os = deviceSocket.getOutputStream();  
		        bw = new BufferedWriter(new OutputStreamWriter(os));
		        log.info("send change ip port command:"+new String(setCommand));
		        bw.write(new String(setCommand));  
		        bw.flush();
	            return "0\n";
			}
		}
		if (service.isAccept(oriMessage.getBytes())) {
            Packet packet = service.decode(oriMessage.getBytes());
            //设备唯一识别号
            String deviceNo = packet.getSerialNo();
            //流水号
            String seqNo = packet.getSeqNo();
            
            log.info("[deviceNo]："+deviceNo+";[seqNo]："+seqNo);
            log.info("[request message]："+packet.getMessage().getMessageType());
            if(DeviceSocketAction.socketPool.get(deviceNo) == null || DeviceSocketAction.socketPool.get(deviceNo).isClosed()){
        		log.info("save socket to pool...");
            	DeviceSocketAction.socketPool.put(deviceNo, socket);
            }
            
            switch (packet.getMessage().getMessageType()) {
                //设备登陆请求
                case LOGIN:
                    Login loginMessage = (Login) packet.getMessage();
                    //封包中具体参数可根据业务选择
                    //根据具体业务逻辑判断该设备是否符合登陆条件
                    LoginR loginR = new LoginR();
                    //流水号必须与设备请求登陆中的流水号相同
                    loginR.setSeqNo(seqNo);
                    loginR.setSerialNo(deviceNo);
                    //标志登陆成功(true: 允许登陆 false: 禁止登陆)
                    loginR.setSuccess(true);
                    byte[] loginRConfirm = service.encode(loginR);
                    log.info("[return msg]："+new String(loginRConfirm));
                    
                    return new String(loginRConfirm);
                    //将该确认消息发送到设备完成登陆流程
                    //....

                //设备卡认证请求
                case AUTHENTICATION:
                    Authentication authMessage = (Authentication) packet.getMessage();
                    String cardId = authMessage.getCardId();
                    //封包中其他参数可根据业务选择使用
                    //根据业务逻辑判断卡可否认证通过
                    AuthenticationR authR = new AuthenticationR();
                    //流水号必须与设备卡认证请求中的流水号相同
                    authR.setSeqNo(seqNo);
                    authR.setSerialNo(deviceNo);
                    //标志登陆成功(true: 认证通过 false: 认证拒绝)
                    authR.setSuccess(true);
                    byte[] authRConfirm = service.encode(authR);
                    //将该确认消息发送到设备完成卡认证流程
                    //....
                    break;

                //刷卡信息
                case BRUSH_CARD:
                    BrushCard brushCardMessage = (BrushCard) packet.getMessage();
                    String brushCardId = brushCardMessage.getCardId();
                    //处理刷卡信息
                    //....
                    break;

                //轨迹信息
                case LOCATION:
                    Track track = (Track) packet.getMessage();
                    if(!StringHelper.isRealNull(""+track.getLng())){
                    	String sql = "insert into d_device_receive(device_id,device_no,mess_type,mess_text,send_time,receive_time,is_reply,reply_text,reply_state,reply_time,ts) "
                        		+ " values('"+deviceNo+"','"+deviceNo+"','"+Message.MessageType.LOCATION+"','"+oriMessage.replace("'", "\"")+"','"+track.getLocalTime()+
                        		"','"+DateUtil.getChar19DateString(new Date())+"',1,'"+packet.getConfirmResponseMessage()+"',1,'"+DateUtil.getChar19DateString(new Date())+"','"+DateUtil.getChar19DateString(new Date())+"')";
                        //处理封包中具体参数
                        //....
                        int i = DBUtil.insertOrUpdateSql(sql);
                        log.info("d_device_receive has insert："+i);
                        
                        CarLocation cl = new CarLocation();
                        cl.setDeviceNo(deviceNo);
                        cl.setLat(track.getLat());
                        cl.setLng(track.getLng());
                        cl.setSpeed(track.getSpeed());
                        cl.setTs(new Date());
                        
//                        String sql4 = "insert into d_car_location(device_id,device_no,lat,lng,speed,ts) values('"+deviceNo+"','"+deviceNo+"',"
//                        		+ track.getLat() + ","+track.getLng()+","+track.getSpeed()+",'"+DateUtil.getChar19DateString(new Date())+"')";
//                        i = DBUtil.insertOrUpdateSql(sql4);
                        carLocationService.add(cl);
                        log.info("d_car_location has insert;location："+track.getLat()+","+track.getLng());
                        List<DeviceBinding> deviceBindings = deviceBindingService.queryBindByDeviceNo(deviceNo, 1);
//                        String sql2 = "select car_id from c_device_binding where device_no = '"+deviceNo +"' and bind_type=1";
//                        ResultSet rs = DBUtil.querySql(sql2);
                        if(deviceBindings != null && deviceBindings.size() > 0){
                        	String carId = deviceBindings.get(0).getCarId();
                        	CarRealtimeState crs = carRealtimeStateService.getCarRealTimeState(carId);
                        	crs.setSpeed(track.getSpeed());
                        	crs.setLat(track.getLat());
                        	crs.setLng(track.getLng());
                        	crs.setTrackTime(new Date());
                        	crs.setOBDTime(new Date());
                        	crs.setTs(new Date());
                        	
                        	carRealtimeStateService.update(crs);
                        	log.info("c_realtime_state has update");
                        }
//                        if(rs.next()){
//                        	String carId = rs.getString(1);
//                        	String sql3 = "update c_realtime_state set speed="+track.getSpeed()+",lat="+track.getLat()+",lng="+track.getLng()+",track_time='"+DateUtil.getChar19DateString(new Date())
//                        			+"',OBD_time='"+DateUtil.getChar19DateString(new Date())+"',ts='"+DateUtil.getChar19DateString(new Date())+"' where id='"+carId+"'";
//                        	i = DBUtil.insertOrUpdateSql(sql3);
//                        	
//                        	log.info("c_realtime_state has update："+i);
//                        }
                        
                    }
                    if (packet.getConfirmResponseMessage() != null) {
                        return new String(packet.getConfirmResponseMessage());
                    }
                    return "0";
                  //轨迹信息
                case CAN_DATA:
                	VehicleInfoOnCAN can = (VehicleInfoOnCAN)packet.getMessage();
                	List<DeviceBinding> deviceBindings = deviceBindingService.queryBindByDeviceNo(deviceNo, 1);
                	if(deviceBindings != null && deviceBindings.size() > 0){
                		String carId = deviceBindings.get(0).getCarId();
                    	int state = 0;
                    	if(can.isBmsFastChgPlugOn() || can.isBmsSlowChgPlugOn()){
                    		state = 1;
                    	}
                    	else if(can.getSpeed() != null && can.getSpeed() > 0){
                    		state = 2;
                    	}
                    	else{
                    		state = 3;
                    	}
                    	CarRealtimeState crs = carRealtimeStateService.getCarRealTimeState(carId);
                    	crs.setSOC(new Double(can.getBmsPackSOCDisplay()));
                    	crs.setState(state);
                    	crs.setOBDTime(new Date());
                    	carRealtimeStateService.update(crs);
                    	log.info("c_realtime_state has update");
                	}
//                	String sql2 = "select car_id from c_device_binding where device_no = '"+deviceNo +"' and bind_type=1";
//                    ResultSet rs = DBUtil.querySql(sql2);
//                    if(rs.next()){
//                    	String carId = rs.getString(1);
//                    	int state = 0;
//                    	if(can.isBmsFastChgPlugOn() || can.isBmsSlowChgPlugOn()){
//                    		state = 1;
//                    	}
//                    	else if(can.getSpeed() != null && can.getSpeed() > 0){
//                    		state = 2;
//                    	}
//                    	else{
//                    		state = 3;
//                    	}
//                    	String sql3 = "update c_realtime_state set SOC="+can.getBmsPackSOCDisplay()+",state="+state+
//                    			",OBD_time='"+DateUtil.formatDate(new Date(), "yyyy-MM-dd hh:mm:ss")+"' where id='"+carId+"'";
//                    	int i = DBUtil.insertOrUpdateSql(sql3);
//                    	log.info("c_realtime_state has update："+i);
//                    }
                    if (packet.getConfirmResponseMessage() != null) {
                        return new String(packet.getConfirmResponseMessage());
                    }
                    return "0";
                //IOUnit模块数据
                case IO_UNIT:
                    GeneralIOUnit ioUnit = (GeneralIOUnit) packet.getMessage();
                    //获取时间
                    long time = ioUnit.getTime();
                    //获取触发事件(为空表示未触发事件)，具体定义请查看UnitEvent定义说明
                    UnitEvent event = ioUnit.getUnitEvent();
                    //获取车辆通用状态信息，具体定义请查看GeneralStatus定义说明
                    GeneralStatus generalStatus = ioUnit.getGeneralStatus();
                    //获取假设安装到IoUnit模块第7模拟通道的油量传感器剩余油量数据
                    //根据传感器实际安装的通道来获取相应数据
                    Double percentage_of_remaining_oil = ioUnit.getProperty(PropertyKeyId.ANALOG_CHANNEL_7, Double.class);
                    //...
                    break;

                //设置及查询设备返回确认信息
                case BOX_CONFIRM:
                    BoxConfirm boxConfirm = (BoxConfirm) packet.getMessage();
                    CMDType cmdType = boxConfirm.getCmdType();
//                    switch (cmdType) {
//                        //设置预存卡ID
//                        case SET_CARDID:
////                            CardIdSetting cardIdSettingConfirm = (CardIdSetting) boxConfirm.;
//                            //获取卡ID
////                            String savedCardId = cardIdSettingConfirm.getCardId();
//                            //执行其他逻辑
//                            //....
//                            break;
//                    }
                    if (packet.getConfirmResponseMessage() != null) {
                        return new String(packet.getConfirmResponseMessage());
                    }
                    return "0";
                default :
                	if (packet.getConfirmResponseMessage() != null) {
                        return new String(packet.getConfirmResponseMessage());
                    }
                    return "0";
            }
            //如果ConfirmResponseMessage不为空，需要及时回复给设备(平台对设备数据的应答)
            if (packet.getConfirmResponseMessage() != null) {
            	return new String(packet.getConfirmResponseMessage());
            }
            return "0";
         
        }
		else{
			return new String("0000");
		}
		
	}
	
}
