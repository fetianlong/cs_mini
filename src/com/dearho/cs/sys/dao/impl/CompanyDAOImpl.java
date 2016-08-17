package com.dearho.cs.sys.dao.impl;

import java.util.List;

import com.dearho.cs.core.db.AbstractDaoSupport;
import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.companyDAO;
import com.dearho.cs.sys.pojo.Company;

public class CompanyDAOImpl extends AbstractDaoSupport implements companyDAO{

	@Override
	public List<Company> searchCompanyByCode(String code) {
		String hql = "from Company r where 1=1 ";
		
		if(code != null){
			hql += "and r.code = '"+code+"'";
		}
		return getHibernateTemplate().find(hql);
	}

	@Override
	public List<Company> searchCompanyByName(String name) {
		String hql = "from Company r where 1=1 ";
		
		if(name != null){
			hql += "and r.name like '%"+name+"%'";
		}
		return getHibernateTemplate().find(hql);
	}
	
	@Override
	public void addCompany(Company company) {
		addEntity(company);
	}

	@Override
	public void updateCompany(Company company) {
		updateEntity(company);
	}

	@Override
	public void deleteCompany(String[] checkdel) {
		final String queryString="delete Company where id in (:ids)";
		deleteEntity(Company.class, queryString, checkdel);
	}

	@Override
	public Page<Company> searchCompany(Page<Company> page,
			String hql) {
		Page<Company> resultPage = pageCache(Company.class ,page, hql);
		resultPage.setResults(idToObj(Company.class, resultPage.getmResults()));
		return resultPage;
	}


	@Override
	public Company searchCompanyById(String id) {
		return get(Company.class, id);
	}

	@Override
	public List<Company> searchCompanyByType(String typeId) {
		String hql = "from Company r where 1=1 ";
		
		if(typeId != null){
			hql += "and r.bizType = '"+typeId+"'";
		}
		return getHibernateTemplate().find(hql);
	}

}
