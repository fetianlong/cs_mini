package com.dearho.cs.advice.action;

import java.util.Date;
import java.util.UUID;

import com.dearho.cs.advice.pojo.Inform;
import com.dearho.cs.advice.pojo.InformRecord;
import com.dearho.cs.advice.service.InformRecordService;
import com.dearho.cs.advice.service.InformService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.subscriber.pojo.Subscriber;
import com.dearho.cs.subscriber.service.SubscriberService;
import com.dearho.cs.sys.action.AbstractAction;
import com.dearho.cs.util.Ajax;
import com.dearho.cs.util.Constants;
import com.dearho.cs.util.StringHelper;

/**
 * 通知Action
 * @author jyt
 * @since 2016年7月20日 下午1:53:37
 */
public class InformAction extends AbstractAction{

	private static final long serialVersionUID = 1L;

	//通知id
	private String id;
	//通知id数组
	private String ids[];
	//通知model
	private Inform inform;
	
	private Page<Inform> page;
	
	private InformService informService;
	
	private SubscriberService subscriberService;
	
	private InformRecordService informRecordService;
	/***
	 * queryInforms
	 * 列表查询
	 * 排序按照 创建时间 desc 排序
	 */
	@Override
	public String process() {
		try {
			page =informService.queryInformPage(page,inform);
			return SUCCESS;
			//查询条件 会员ID不为空时
			
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
	}
	
	/**
	 * 
	 * @return
	 */
	public String getInformById(){
		
		try {
			inform = informService.getInformById(id);
			if(StringHelper.isNotEmpty(id) && inform.getInformSendType() == 2) //指定用户发送 获取会员姓名
			{
				String name =informRecordService.getSubscriberNameByInformId(id);
				inform.setSubscriberName(name);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return ERROR;
		}
		return SUCCESS;
	}
	
	/**
	 * 添加通知
	 * @return
	 */
	public String addInform(){
		
		try {
			String id = UUID.randomUUID().toString().replaceAll("-", "");
			System.out.println(id);
			inform.setId(id);
			inform.setTs(new Date());
			informService.addInform(inform);
			String name = inform.getSubscriberName();
			Integer sendType = inform.getInformSendType();
			//Dict dict = DictUtil.getDictByCodes("informSendType", "2");
			//消息为指定用户方式发送要生成一条未读记录到record表中
			if(StringHelper.isNotEmpty(name) & sendType ==2 )
			{
				Subscriber sb = subscriberService.querySubscriberByName(name);
				InformRecord record  =new InformRecord(id,sb.getId(),0,sb.getTs());
				informRecordService.addInformRecord(record);
			}
			 result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"添加成功");
		} catch (Exception e) {
			e.printStackTrace();
			result = Ajax.JSONResult(Constants.RESULT_CODE_FAILED, "添加失败！");
			return ERROR;
		}
		return SUCCESS;
	}

	/**
	 * 删除单个通知
	 * @return
	 */
	public String delInformById(){
		
		try {
			informService.delInformById(inform.getId());
			 result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"删除成功！");
		} catch (Exception e) {
			e.printStackTrace();
			 result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"删除失败！");
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 修改通知
	 * @return
	 */
	public String updateInform(){
		
		try {
			int a=1;
			informService.updateInform(inform);
			 result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新成功！");
		} catch (Exception e) {
			e.printStackTrace();
			 result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"更新失败！");
			return ERROR;
		}
		return SUCCESS;
	}
	/**
	 * 删除通知
	 * @return
	 */
	public String delInformByIds(){
		
		try {
			informService.delInformByIds(ids);
			 result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"批量删除成功");
		} catch (Exception e) {
			e.printStackTrace();
			 result = Ajax.JSONResult(Constants.RESULT_CODE_SUCCESS,"批量删失败！");
			return ERROR;
		}
		return SUCCESS;
	}
	
	
	
	
	public InformAction() {
		super();
		inform = new Inform();
		page = new Page<Inform>();
		page.setCurrentPage(1);
		page.setCountField("a.id");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String[] getIds() {
		return ids;
	}

	public void setIds(String[] ids) {
		this.ids = ids;
	}

	public Inform getInform() {
		return inform;
	}

	public void setInform(Inform inform) {
		this.inform = inform;
	}

	public Page<Inform> getPage() {
		return page;
	}

	public void setPage(Page<Inform> page) {
		this.page = page;
	}

	public InformService getInformService() {
		return informService;
	}

	public void setInformService(InformService informService) {
		this.informService = informService;
	}

	public InformRecordService getInformRecordService() {
		return informRecordService;
	}

	public void setInformRecordService(InformRecordService informRecordService) {
		this.informRecordService = informRecordService;
	}

	public SubscriberService getSubscriberService() {
		return subscriberService;
	}

	public void setSubscriberService(SubscriberService subscriberService) {
		this.subscriberService = subscriberService;
	}
	
}
