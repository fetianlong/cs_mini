package com.dearho.cs.sys.service.impl;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.dao.companyDAO;
import com.dearho.cs.sys.pojo.Company;
import com.dearho.cs.sys.pojo.Dict;
import com.dearho.cs.sys.service.CompanyService;
import com.dearho.cs.sys.util.DictUtil;
import com.dearho.cs.util.StringHelper;

public class CompanyServiceImpl implements CompanyService {
	
	private companyDAO companyDAO;

	@Override
	public List<Company> searchCompanyByCode(String code) {
		return companyDAO.searchCompanyByCode(code);
	}

	@Override
	public List<Company> searchCompanyByName(String name) {
		return companyDAO.searchCompanyByName(name);
	}

	@Override
	public void addCompany(Company company) {
		companyDAO.addCompany(company);
	}

	@Override
	public void updateCompany(Company company) {
		companyDAO.updateCompany(company);
	}

	@Override
	public void deleteCompany(String[] checkdel) {
		companyDAO.deleteCompany(checkdel);
	}

	@Override
	public Page<Company> searchCompany(Page<Company> page,
			Company company) {
		String hql = "select a.id from Company a where 1=1 ";
		if(company != null){
			if(StringHelper.isNotEmpty(company.getCode())){
				hql += " and a.code = '"+company.getCode()+"'";
			}
			if(StringHelper.isNotEmpty(company.getName())){
				hql += " and a.name like '%"+company.getName()+"%'";
			}
			if(StringHelper.isNotEmpty(company.getBizType())){
				hql += " and a.bizType = '"+company.getBizType()+"'";
			}
		}
		hql += (StringHelper.isEmpty(page.getOrderByString()) ? "" : page.getOrderByString());
		return companyDAO.searchCompany(page, hql);
	}


	@Override
	public Company searchCompanyById(String id) {
		return companyDAO.searchCompanyById(id);
	}

	public companyDAO getCompanyDAO() {
		return companyDAO;
	}

	public void setCompanyDAO(companyDAO companyDAO) {
		this.companyDAO = companyDAO;
	}

	@Override
	public List<Company> searchCompanyByBizType(String bizTypeCode) {
		List<Dict> dicts = DictUtil.getDictsByGroupCode("companyBizType");
		if(dicts == null || dicts.size() <= 0){
			return null;
		}
		for (Dict dict : dicts) {
			if(dict.getCode().equals(bizTypeCode)){
				return companyDAO.searchCompanyByType(dict.getId());
			}
		}
		return null;
		
	}


	
}
