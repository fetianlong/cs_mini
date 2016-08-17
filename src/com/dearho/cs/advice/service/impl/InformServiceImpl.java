package com.dearho.cs.advice.service.impl;

import com.dearho.cs.advice.dao.InformDao;
import com.dearho.cs.advice.dao.InformRecordDao;
import com.dearho.cs.advice.pojo.Inform;
import com.dearho.cs.advice.service.InformService;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.util.StringHelper;

/**
 * 通知服务层接口实现类
 * @author jyt
 * @since 2016年7月20日 下午2:00:19
 */
public class InformServiceImpl implements InformService {

	private InformDao informDao;

	private InformRecordDao informRecordDao;
	
	public InformDao getInformDao() {
		return informDao;
	}

	public void setInformDao(InformDao informDao) {
		this.informDao = informDao;
	}
	
	public InformRecordDao getInformRecordDao() {
		return informRecordDao;
	}

	public void setInformRecordDao(InformRecordDao informRecordDao) {
		this.informRecordDao = informRecordDao;
	}

	@Override
	public Page<Inform> queryInformPage(Page<Inform> page, Inform info) {
		
		StringBuffer sb=new StringBuffer();
		if(info !=null){
			sb.append("SELECT  a.id  "
					+ " FROM Inform a "// LEFT JOIN Subscriber c WITH b.subscriberId = c.id
					+" WHERE 1=1  ");
			 
			/*sb.append("SELECT  new Inform( a.id ,a.informContent,a.informType,a.informSendType,a.ts,b.subscriberId ,c.name)  "
					+ " FROM Inform a ,InformRecord b  ,Subscriber c "// LEFT JOIN Subscriber c WITH b.subscriberId = c.id
					+" WHERE (b is null or c is null or (a.id = b.informId  and b.subscriberId = c.id))  ");
			 */
			if(StringHelper.isNotEmpty(info.getSubscriberName())){
				sb.append(" and c.name like '%"+info.getSubscriberName()+"%'");
			}
			if(StringHelper.isNotEmpty(info.getSubscriberId())){
				sb.append(" and b.subscriberId = '"+info.getSubscriberId()+"'");
			}
			if(StringHelper.isNotEmpty(info.getInformContent())){
				sb.append(" and a.informContent = '"+info.getInformContent()+"'");
			}
			if(null!=info.getInformType()){
				sb.append(" and a.informType = "+info.getInformType());
			}
			if(null!=info.getInformSendType()){
				sb.append(" and a.informSendType = "+info.getInformSendType());
			}
		}
		sb.append(StringHelper.isEmpty(page.getOrderByString()) ? " order by a.ts desc" : "order by a."+page.getOrderByString().replace("order by", ""));
		page=informDao.queryInformByPage(page, sb.toString());
		
		return page;
	}

	@Override
	public void addInform(Inform inform) {
		this.informDao.addInform(inform);
	}

	@Override
	public boolean delInformById(String id) {
		//
		
		 this.informDao.delInfromById(id);
		return false;
	}

	@Override
	public boolean updateInform(Inform inform) {
		this.informDao.updateInform(inform);
		return false;
	}

	@Override
	public boolean delInformByIds(String[] ids) {
		this.informDao.delInformByIds(ids);
		return false;
	}

	@Override
	public Inform getInformById(String id) {
		
		return informDao.queryInformById(id);
	}

	
}
