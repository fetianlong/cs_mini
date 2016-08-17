package com.dearho.cs.sys.service;

import java.util.List;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.sys.pojo.Company;

/**
 * @author GaoYunpeng
 * @Description 公司企业
 * @version 1.0 2015年5月19日 下午3:19:44
 */
public interface CompanyService {
	/**
	 * @Title:searchCompanyByCode
	 * @Description:根据编码查询公司，参数可以为空，则查询所有。
	 * @param code
	 * @return
	 * @throws
	 */
	List<Company> searchCompanyByCode(String code);
	
	/**
	 * @Title:searchCompanyByName
	 * @Description:根据名称模糊查询公司，参数可以为空，则查询所有。
	 * @param name
	 * @return
	 * @throws
	 */
	List<Company> searchCompanyByName(String name);
	
	void addCompany(Company company);
	void updateCompany(Company company);
	void deleteCompany(final String[] checkdel);
	
	Page<Company> searchCompany(Page<Company> page,Company company);

	Company searchCompanyById(String id);
	
	/**
	 * @Title:searchCompanyByBizType
	 * @Description:根据公司类型的字典编码，来查询这一类型的公司
	 * @param bizTypeCode
	 * @return
	 * @throws
	 */
	List<Company> searchCompanyByBizType(String bizTypeCode);
}
