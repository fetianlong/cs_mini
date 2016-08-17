package com.dearho.cs.sys.dao;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Company;
/**
 * @author GaoYunpeng
 * @Description 公司企业
 * @version 1.0 2015年5月19日 下午3:22:01
 */
public interface companyDAO {
	
	List<Company> searchCompanyByCode(String code);
	
	List<Company> searchCompanyByName(String name);
	
	void addCompany(Company company);
	void updateCompany(Company company);
	void deleteCompany(final String[] checkdel);
	
	Page<Company> searchCompany(Page<Company> page,String hql);
	
	Company searchCompanyById(String id);

	List<Company> searchCompanyByType(String typeId);
}
