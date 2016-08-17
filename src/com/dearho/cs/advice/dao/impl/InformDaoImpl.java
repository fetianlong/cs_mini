package com.dearho.cs.advice.dao.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.dearho.cs.advice.dao.InformDao;
import com.dearho.cs.advice.pojo.Feedback;
import com.dearho.cs.advice.pojo.Inform;
import com.dearho.cs.car.pojo.Car;
import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;

/**
 * 通知Dao接口实现类
 * @author jyt
 * @since 2016年7月20日 下午2:36:47
 */
public class InformDaoImpl  extends AbstractDaoSupport implements InformDao {

	@Override
	public Page<Inform> queryInformByPage(Page<Inform> page, String hql) {
		/*Page<Inform> resultPage=pageCache(Inform.class, page, hql);
		List<Serializable> list = resultPage.getmResults();
		List<Inform >fbList = new ArrayList<> ();
		if(null != list && list.size()>0)
		{
			for(Serializable s: list){
				fbList.add((Inform) s);
			}
		}
		resultPage.setResults( fbList);*/
		Page<Inform> resultPage=pageCache(Inform.class, page, hql);
		 List<Inform> list = this.getList(Inform.class, resultPage.getmResults());
		 resultPage.setResults(list);
		return resultPage;
	}

	@Override
	public Inform queryInformById(String id) {
		return this.get(Inform.class, id);
	}

	@Override
	public void addInform(Inform inform) {
		
		this.addEntity(inform);
	}

	@Override
	public void delInfromById(String id) {
		this.deleteEntity(Inform.class, "",new String[]{id});
	}

	@Override
	public void updateInform(Inform inform) {
		this.updateEntity(inform);
	}

	@Override
	public void delInformByIds(String[] ids) {
		this.deleteEntity(Inform.class, "", ids);
	}

}
