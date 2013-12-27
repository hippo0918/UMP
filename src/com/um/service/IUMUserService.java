package com.um.service;

import java.io.Serializable;
import java.util.List;

import com.common.easyui.DataGridDTO;
import com.common.easyui.DataGridJsonDTO;
import com.um.domain.UMUser;
import com.um.domain.model.dto.UMUserDTO;
import com.um.exception.UMUserException;

public interface IUMUserService {

	public static final String SERVICE_NAME = "com.um.service.impl.UMUserServiceImpl";
	
	public Serializable save(UMUserDTO userDto);
	
	public DataGridJsonDTO<UMUserDTO> find(DataGridDTO datagrid, UMUserDTO user);
	
	public DataGridJsonDTO<UMUserDTO> findByRoleIds(DataGridDTO datagrid, String... ids);
	
	public UMUserDTO findByID(String id);
	
	public boolean update(UMUserDTO userDto);
	
	public int delete(String[] ids);
	
	public String findLastUserno();
	
	public List<UMUser> findAll();
	
	public UMUserDTO login(UMUserDTO userDto) throws UMUserException;
	
	public UMUserDTO getUserRoleMenuPer(String userId);
	
	
}
