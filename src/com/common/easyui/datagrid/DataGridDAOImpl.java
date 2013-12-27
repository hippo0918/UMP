package com.common.easyui.datagrid;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.Vector;

import org.apache.commons.beanutils.BeanMap;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Example;
import org.hibernate.criterion.Example.PropertySelector;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.metadata.ClassMetadata;
import org.hibernate.type.ComponentType;
import org.hibernate.type.StringType;

import com.um.dao.impl.BaseDAOImpl;
import com.um.exception.DaoException;

public class DataGridDAOImpl<T> extends BaseDAOImpl<T> implements IDataGridDAO<T> {

	private NotNullOrBlankPropertySelector selector = new NotNullOrBlankPropertySelector();

	/**
	 * @param bean
	 * @param mode
	 * @param datagrid
	 * @return
	 * @throws DaoException
	 */
	public DataGridJsonDTO<T> getBeansByBean(T bean, MatchMode mode,
			DataGridDTO datagrid) throws DaoException {
		return this.getBeansByBean(bean, mode, datagrid);
	}

	/**
	 * 
	 * @param bean
	 * @param mode
	 * @param datagrid
	 * @param excludeBlankValue 查询条件中是否忽略空串
	 * @return
	 * @throws DaoException
	 */
	public DataGridJsonDTO<T> getBeansByBeanForPager(T bean, MatchMode mode,
			DataGridDTO datagrid, boolean excludeBlankValue) throws DaoException {

		Collection<T> collection = null;
		DataGridJsonDTO<T> dataGridJsonDTO = new DataGridJsonDTO<T>();
		Session session = this.getCurrentSession();
		try {
			Example example = Example.create(bean).excludeNone()
					.excludeZeroes().enableLike(mode);
			if (excludeBlankValue) {
				/* 忽略空串 */
				example.setPropertySelector(selector);
			}
			Criteria criteria = session.createCriteria(bean.getClass()).add(
					example);
			appendIdToQuery(bean, criteria, mode, excludeBlankValue);
			long totalCount = (Long) criteria.setProjection(Projections.rowCount()).uniqueResult();  
			criteria.setProjection(null);
			criteria.setFirstResult((datagrid.getPage() - 1) * datagrid.getRows()).setMaxResults(datagrid.getRows());
			dataGridJsonDTO.setTotal(totalCount);
			criteria.setProjection(null);
			//遍历排序
			Set<Entry<String, String>> entrySet = datagrid.getOrder().entrySet();
			Iterator<Entry<String, String>> i = entrySet.iterator();
			while(i.hasNext()) {
				Entry<String, String> entry = i.next();
				if(entry.getKey() != null && !"".equals(entry.getKey())) {
					if(entry.getValue().equalsIgnoreCase("DESC")) {
						this.addOrder(org.hibernate.criterion.Order.desc(entry.getKey()));
					} else {
						this.addOrder(org.hibernate.criterion.Order.asc(entry.getKey()));
					}
				}
			}
			Vector<Order> orderVector = getOrderVector();
			for (Iterator<Order> it = orderVector.iterator(); it.hasNext(); criteria.addOrder((Order) it.next()));
			orderVector.clear();
			datagrid.getOrder().clear();
			entrySet.clear();
			collection = criteria.list();
			dataGridJsonDTO.setRows(collection);
		} catch (Exception ex) {
			throw new DaoException("Get match records by bean fail!", ex);
		}
		return dataGridJsonDTO;
	}
	
    /**
     * @param hqlName
     * @param paraList
     * @param datagrid
     * @return
     * @throws DaoException
     */
	public Collection<T> getBeansByParamsForPager(String hqlName, ArrayList<String> paraList,
    		DataGridDTO datagrid)
			throws DaoException {
		Collection<T> collection = null;
		int paraSize = paraList.size();
		try {
			Query q = super.getSessionFactory().getCurrentSession().getNamedQuery(hqlName);
			for (int i = 0; i < paraSize; i++)
				q.setParameter(i, paraList.get(i));
			
			q.setFirstResult((datagrid.getPage() - 1) * datagrid.getRows()).setMaxResults(datagrid.getRows()).list();
			collection = q.list();
			
		} catch (Exception ex) {
			throw new DaoException("Get match records by hqlName fail!", ex);
		}
		return collection;
	}
    

	/**
	 * @param bean
	 * @param criteria
	 * @param mode
	 * @param excludeBlankValue
	 * @throws Exception
	 */
	private void appendIdToQuery(T bean, Criteria criteria,
			MatchMode mode, boolean excludeBlankValue) throws Exception {
		ClassMetadata cm = this.getSessionFactory().getClassMetadata(
				bean.getClass());
		String idName = cm.getIdentifierPropertyName();
		org.hibernate.type.Type idType = cm.getIdentifierType();
		BeanMap beanMap = new BeanMap(bean);
		Object idValue = beanMap.get(idName);
		if (idValue != null) {
			Class<? extends Object> idClass = idValue.getClass();
			if (idType instanceof ComponentType) {
				Method methods[] = idClass.getMethods();
				try {
					for (int i = 0; i < methods.length; i++)
						if (methods[i].getName().toUpperCase().indexOf("GET") != -1
								&& methods[i].invoke(idValue, null) != null
								&& !methods[i].getName().equals("getClass")) {
							String propertyName = methods[i].getName()
									.substring(3);
							propertyName = propertyName.toLowerCase()
									.substring(0, 1)
									+ propertyName.substring(1);
							if (methods[i].getReturnType().getName().equals(
									"java.lang.String")) {
								String propertyValue = methods[i].invoke(idValue, null).toString();
								if (!excludeBlankValue || (null != propertyValue && 0 < propertyValue.length())) {
									criteria.add(Restrictions.like(idName + "." + propertyName, propertyValue, mode));
								}
							} else {
								Object propertyValue = methods[i].invoke(idValue , null);
								if (!excludeBlankValue || null != propertyValue) {
									criteria.add(Restrictions.eq(idName + "." + propertyName, propertyValue));
								}
							}
						}

				} catch (Exception ex) {
					ex.printStackTrace();
					throw ex;
				}
			} else if (idType instanceof StringType) {
				if (!excludeBlankValue || (null != idValue && 0 < idValue.toString().length())) {
					criteria.add(Restrictions.like(idName, idValue.toString(), mode));
				}
			} else {
				if (!excludeBlankValue || null != idValue) {
					criteria.add(Restrictions.eq(idName, idValue));
				}
			}
		}
	}

	static final class NotNullOrBlankPropertySelector implements
			PropertySelector {
		@Override
		public boolean include(Object object, String propertyName, org.hibernate.type.Type type) {
			return object != null
					&& (!(object instanceof String) || !((String) object).equals(""));
		}
	}

	@Override
	public Collection<T> getBeansByParamsForPager(String hqlName,
			Map<String, Object> paraMap, DataGridDTO datagrid)
			throws DaoException {
		Collection<T> collection = null;
		try {
			Query q = super.getSessionFactory().getCurrentSession().getNamedQuery(hqlName);
			setParameter(q, paraMap);
			q.setFirstResult((datagrid.getPage() - 1) * datagrid.getRows()).setMaxResults(datagrid.getRows()).list();
			collection = q.list();
		} catch (Exception ex) {
			throw new DaoException("Get match records by hqlName fail!", ex);
		}
		return collection;
	}
}

