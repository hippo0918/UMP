package com.common.easyui;

import javax.servlet.http.HttpServletRequest;

public class DataGridDTOFactory {

	private static DataGridDTOFactory instance = new DataGridDTOFactory();

	private DataGridDTOFactory() {
	}

	public static DataGridDTOFactory getInstance() {
		if (instance == null)
			return new DataGridDTOFactory();
		else
			return instance;
	}
	
	public DataGridDTO getDataGridDTO(HttpServletRequest request) {
		return new DataGridDTO(request);
	}
}
