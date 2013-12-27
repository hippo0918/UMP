package com.um.dao.impl;

import org.springframework.stereotype.Repository;

import com.common.easyui.DataGridDAOImpl;
import com.um.dao.IUMUserDAO;
import com.um.domain.UMUser;

@Repository(IUMUserDAO.SERVICE_NAME)
public class UMUserDAOImpl extends DataGridDAOImpl<UMUser> implements IUMUserDAO {

}
