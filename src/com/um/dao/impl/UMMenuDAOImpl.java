package com.um.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.easyui.DataGridDAOImpl;
import com.um.dao.IUMMenuDAO;
import com.um.domain.UMMenu;

@Repository(IUMMenuDAO.SERVICE_NAME)
public class UMMenuDAOImpl extends DataGridDAOImpl<UMMenu> implements IUMMenuDAO {

	
}
