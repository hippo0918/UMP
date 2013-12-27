package com.um.service;

import java.io.Serializable;
import java.util.List;

import com.common.easyui.datagrid.DataGridDTO;
import com.common.easyui.datagrid.DataGridJsonDTO;
import com.um.domain.model.dto.UMRoleDTO;

public interface IUMRoleService {

	public static final String SERVICE_NAME = "com.um.service.impl.UMRoleServiceImpl";
	
	public Serializable save(UMRoleDTO roleDto);
	
	public List<UMRoleDTO> find();
	
	public DataGridJsonDTO<UMRoleDTO> find(DataGridDTO dg, UMRoleDTO roleDto);

	public UMRoleDTO findByID(String id);
	
	public int update(UMRoleDTO roleDto);
	
	public void delete(UMRoleDTO roleDto);
	
	public UMRoleDTO findRoleByName(UMRoleDTO roleDto);
	
	public UMRoleDTO getRoleMenuPerByRoleId(String roleId);
	
}
