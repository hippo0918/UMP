package com.um.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.um.service.IUMMenuUMPermissionService;

@Service(IUMMenuUMPermissionService.SERVICE_NAME)
@Transactional(readOnly=true)
public class UMMenuUMPermissionServiceImpl implements
		IUMMenuUMPermissionService {
	
	

}
