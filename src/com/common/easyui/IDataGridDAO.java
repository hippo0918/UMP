package com.common.easyui;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.hibernate.criterion.MatchMode;

import com.um.dao.IBaseDAO;
import com.um.exception.DaoException;

public interface IDataGridDAO<T>  extends IBaseDAO<T> {

	public DataGridJsonDTO<T> getBeansByBean(T bean, MatchMode mode,
			DataGridDTO datagrid) throws DaoException;
	
	public DataGridJsonDTO<T> getBeansByBeanForPager(T bean, MatchMode mode,
			DataGridDTO datagrid, boolean excludeBlankValue) throws DaoException;
	
	 public Collection<T> getBeansByParamsForPager(String hqlName, ArrayList<String> paraList,
	    		DataGridDTO datagrid)
				throws DaoException;

	public Collection<T> getBeansByParamsForPager(String hqlName, Map<String, Object> paraMap,
		DataGridDTO datagrid) throws DaoException;
}
