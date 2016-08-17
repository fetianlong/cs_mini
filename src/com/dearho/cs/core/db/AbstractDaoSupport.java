package com.dearho.cs.core.db;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dearho.cs.core.db.page.Page;
import com.dearho.cs.util.Md5Util;

/**
 * @Author wangyx
 * @Description:(此类型的描述)
 * @Version 1.0, 2015-4-15
 */
public abstract class AbstractDaoSupport extends HibernateDaoSupport {
	private static final Log LOG = LogFactory.getLog(AbstractDaoSupport.class);

	protected String mapping = "/hibernate.cfg.xml";
	private int maxResult = 0;
	private Query query = null;
	private String hql = null;
	private boolean naTive = false;

	@SuppressWarnings("unchecked")
	protected <T> List<T> search(Class<T> clazz, String hql) {
		return (List<T>) (getSession().createQuery(hql).list());
	}

	@SuppressWarnings("unchecked")
	protected List<Serializable> queryFList(String hql) {
		return (List<Serializable>) (getSession().createQuery(hql).list());
	}

	@SuppressWarnings("unchecked")
	protected List<Serializable> queryFList(String hql, boolean naTive) {
		if (!naTive)
			return (List<Serializable>) (getSession().createQuery(hql).list());
		else
			return (List<Serializable>) (getSession().createSQLQuery(hql)
					.list());
	}

	protected Query getQuery(String hql) {
		return getSession().createQuery(hql);
	}

	protected <T> List<T> search(Class<T> clazz, String hql, boolean naTive) {
		return null;
	}

	protected <T> T get(Class<T> clazz, Serializable id) {
		return getObject(clazz, id);
	}

	protected <T> T get(Class<T> clazz, Serializable id, String mapping) {
		return getObject(clazz, id);
	}

	protected <T> List<T> getList(Class<T> clazz, Serializable[] ids) {
		return null;
	}

	protected <T> List<T> getList(Class<T> clazz, Serializable[] ids,
			String mapping) {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T> List<T> getList(Class<T> clazz, List<Serializable> idList) {
		List list = new ArrayList();
		if (idList == null || idList.size() == 0) {
			return null;
		} else {
			for (Serializable id : idList) {
				list.add(get(clazz, id));
			}
		}
		return list;
	}

	protected <T> List<T> getList(Class<T> clazz, List<Serializable> idList,
			String mapping) {
		return null;
	}

	protected <T> List<T> idToObj(Class<T> clazz, List<Serializable> idList) {
		if (idList == null || idList.size() == 0)
			return null;
		List<T> list = new ArrayList<T>();
		for (Serializable id : idList) {
			list.add(get(clazz, id));
		}
		return list;
	}

	protected <T> List<T> idToObj(Class<T> clazz, List<Serializable> idList,
			String mapping) {
		return null;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	protected <T> List<T> idToObject(Class<T> clazz, Serializable[] ids) {
		if (ids == null || ids.length == 0)
			return null;
		List list = new ArrayList();
		for (Serializable id : ids) {
			list.add(get(clazz, id));
		}
		return list;
	}

	protected <T> List<T> idToObject(Class<T> clazz, Serializable[] ids,
			String mapping) {
		return null;
	}

	@SuppressWarnings("unchecked")
	private <T> T getObject(Class<T> clazz, Serializable id) {
		if (id == null)
			return null;
		return (T) getDB(clazz, id);
	}

	@SuppressWarnings("unchecked")
	public <T> Page<T> pageCache(Class<T> clazz, Page<T> page, String hql) {
		String key = getKey(page, hql);
		Page<T> cachepage = page(page, hql);
		return cachepage;
	}

	public <T> Page<T> page(Page<T> page, String hql) {
		try {
			this.naTive = false;
			this.hql = hql;
			int froms = hql.toLowerCase().indexOf("from");
			String hqlCount = "select count(" + page.getCountField() + ") "
					+ hql.substring(froms);
			createQuery();
			Query queryCount = createQuery(hqlCount);
			page.initMPage(query, queryCount);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return page;
	}

	public <T> Page<T> page(Page<T> page, String hql, boolean naTive) {
		try {
			this.naTive = naTive;
			this.hql = hql;
			int froms = hql.toLowerCase().indexOf("from");
			String hqlCount = "select count(" + page.getCountField() + ") "
					+ hql.substring(froms);
			createQuery();
			Query queryCount = createQuery(hqlCount);
			page.initPage(query, queryCount);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return page;
	}

	private Query createQuery(String hql) {
		Query query = this.naTive ? getSession().createSQLQuery(hql)
				: getSession().createQuery(hql);
		return query;
	}

	private void createQuery() {
		this.query = createQuery(this.hql);
		if (this.maxResult > 0)
			this.query.setMaxResults(this.maxResult);
	}

	public <T> String getKey(Page<T> page, String hql) {
		StringBuffer sb = new StringBuffer(
				(this.mapping + ":" + hql + ":").toLowerCase());
		if (page != null) {
			sb.append(page.toString());
		}
		return Md5Util.MD5Encode(sb.toString());
	}

	public static String getKey(String mapping, String prefix, String key) {
		String calcKey = Md5Util.MD5Encode((mapping + ":" + prefix + ":" + key)
				.toLowerCase());
		return calcKey;
	}

	@SuppressWarnings({ "unchecked" })
	private <T> T getDB(Class<T> clazz, Serializable id) {
		try {
			Object object = getSession().get(clazz, id);
			return (T) object;
		} catch (Exception e) {
			e.printStackTrace();
			LOG.error("getDB Error!", e);
			return null;
		}
	}

	public String getMapping() {
		return mapping;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public String getObjecttKey(String mapping, Object o) {
		return getKey(mapping, o.getClass().getName(), getSession()
				.getIdentifier(o).toString());
	}

	public void set(Object o) {
		try {
			clearPages(o.getClass());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void remove(Object o) {
		try {
			clearPages(o.getClass());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	@SuppressWarnings("unchecked")
	private <T> void clearPages(Class<T> clazz) {
	
	}

	public <T> void remove(Class<T> clazz, List<String> list) {
		
	}
	
	public <T> void remove(Class<T> clazz, Object[] list) {
		try {
			for(Object idT : list) {
				String id="";
				if(idT instanceof String){
					id=(String)idT;
				}else if(idT instanceof Integer){
					id=(Integer)idT+"";
				}else{
					id=idT.toString();
				}
			}
			clearPages(clazz);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public <T> String getObjecttKey(String mapping, Class<T> clazz, String id) {
		return getKey(mapping, clazz.getName(), id);
	}

	public Query getQuery() {
		return this.query;
	}

	@SuppressWarnings("unchecked")
	public <T> void clearCache(Class<T> clazz) {
		clearPages(clazz);
	}

	public static String getClassKey(String mapping, String prefix) {
		return Md5Util.MD5Encode((mapping + ":" + prefix).toLowerCase());
	}
	
	public <T> void addEntity(T t) {
		getHibernateTemplate().save(t);
		getHibernateTemplate().flush();
		set(t);
	}
	
	public <T> void updateEntity(T t) {
		getHibernateTemplate().update(t);
		getHibernateTemplate().flush();
		set(t);
	}
	
	public <T> void deleteEntity(final Class<T> clazz,final String queryString, final Object[] checkdel) {
		this.getHibernateTemplate().execute(new HibernateCallback<Object>() {
            public Object doInHibernate(Session session) {
                Query query = session.createQuery(queryString);
                query.setParameterList("ids", checkdel);
                return query.executeUpdate();
            }
        });
		remove(clazz, checkdel);
	}
}
